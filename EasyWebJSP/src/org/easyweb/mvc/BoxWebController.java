package org.easyweb.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easyweb.mvc.datapipe.DataPipe;
import org.easyweb.mvc.event.ActionErrorEvent;
import org.easyweb.mvc.event.AfterActionEvent;
import org.easyweb.mvc.event.BeforeActionEvent;
import org.easyweb.mvc.event.LoadingActionEvent;
import org.easyweb.mvc.event.RegisteringCustomEditorEvent;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * MVC框架控制器
 * 
 * @author TeedyWang
 * 
 */
public class BoxWebController implements Controller, ApplicationContextAware {

	private ApplicationContext applicationContext;
	/**
	 * 调用方法缓存
	 */
	private final static Map<String, FastMethod> fastMethodCache = new HashMap<String, FastMethod>();
	/**
	 * 默认动作
	 */
	private String defaultMethod = "action";
	/**
	 * null值预设缓存
	 */
	private static Map<String, List<String>> prebindMap = new HashMap<String, List<String>>();
	private static final PrebindComparator prebindComparator = new PrebindComparator();

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataPipe.getPipe().setContext(applicationContext);
		DataPipe.getPipe().setHttpSession(request.getSession());
		Object domainAction = null;
		try {
			String requri = request.getRequestURI();
			int p = 0;
			String action = requri.substring(requri.lastIndexOf('/') + 1, requri.length());
			p = action.lastIndexOf('.');
			if (p > 0) {
				action = action.substring(0, p);
			}

			String beanName = null;
			String methodName = null;
			p = action.lastIndexOf('_');
			if (p < 0) {
				beanName = action;
				methodName = defaultMethod;
			} else {
				beanName = action.substring(0, p);
				methodName = action.substring(p + 1, action.length());
			}

			if (!applicationContext.containsBean(beanName)) {
				response.sendError(404, "访问错误！您可能访问了一个不存在的页面文件！");
				response.flushBuffer();
				return null;
			}
			domainAction = applicationContext.getBean(beanName);

			// 发布LoadingActionEvent事件
			ModelAndView modelAndView = new ModelAndView();
			publishEvent(new LoadingActionEvent(this, applicationContext, request, response, domainAction, modelAndView, beanName, methodName));
			if (modelAndView.hasView()) {
				return modelAndView;
			}

			ActionMessage actionMessage = new ActionMessage();
			BoxServletRequestDataBinder binder = new BoxServletRequestDataBinder(domainAction);

			// 注册自定义属性编辑器事件
			publishEvent(new RegisteringCustomEditorEvent(this, applicationContext, request, response, domainAction, binder));

			// 处理action属性中的null值
			prebind(prebindMap.get(beanName), binder);
			// 扫描尚未识别的null值的属性路径
			while (true) {
				try {
					binder.bind(request);
					break;
				} catch (NullValueInNestedPathException e) {
					getPrebindSet(beanName).add(e.getPropertyName());
					Collections.sort(getPrebindSet(beanName), prebindComparator);
					prebind(e.getPropertyName(), binder);
				}
			}

			binder.bindAdditionalData(request, response, actionMessage);

			FastMethod method = getActionFastMethod(domainAction, methodName);
			if (method == null) {
				response.sendError(404, "访问错误！您可能访问了一个不存在的页面服务！");
				response.flushBuffer();
				return null;
			}
			// 准备执行动作事件
			publishEvent(new BeforeActionEvent(this, applicationContext, request, response, domainAction, method.getJavaMethod()));
			ModelAndView actionResult = (ModelAndView) method.invoke(domainAction, null);
			// 动作执行完毕事件
			publishEvent(new AfterActionEvent(this, applicationContext, request, response, domainAction, method.getJavaMethod(), actionResult));
			if (actionResult != null && !CollectionUtils.isEmpty(actionMessage.getMessageMap())) {
				actionResult.addObject("message", actionMessage.getMessageMap());
			}
			if (request.getHeader("x-requested-with") != null) {
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setHeader("Pragma", "no-cache");
			}
			return actionResult;
		} catch (Throwable t) {
			ModelAndView modelAndView = new ModelAndView();
			publishEvent(new ActionErrorEvent(this, applicationContext, request, response, domainAction, modelAndView, t));
			if (!modelAndView.hasView()) {
				throw new RuntimeException(t);
			}
			return modelAndView;
		}
	}

	/**
	 * 预绑定null值
	 * 
	 * @param set
	 * @param binder
	 */
	private void prebind(List<String> set, BoxServletRequestDataBinder binder) {
		if (set == null) {
			return;
		}
		Object obj = binder.getTarget();
		BeanWrapper bw = new BeanWrapperImpl(obj);

		for (String attrName : set) {
			Class<?> type = bw.getPropertyType(attrName);
			try {
				bw.setPropertyValue(attrName, type.newInstance());
			} catch (BeansException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 预绑定null值
	 * 
	 * @param attrName
	 * @param binder
	 */
	private void prebind(String attrName, BoxServletRequestDataBinder binder) {
		List<String> set = new ArrayList<String>(1);
		set.add(attrName);
		prebind(set, binder);
	}

	private List<String> getPrebindSet(String beanName) {
		List<String> pathList = prebindMap.get(beanName);
		if (pathList == null) {
			pathList = new ArrayList<String>();
			prebindMap.put(beanName, pathList);
		}
		return pathList;
	}

	private void publishEvent(ApplicationEvent event) {
		applicationContext.publishEvent(event);
	}

	private FastMethod getActionFastMethod(Object obj, String methodName) {
		String key = obj.getClass().getName() + "#" + methodName;
		return findSomeMethod(obj, methodName, key);
	}

	private FastMethod findSomeMethod(Object obj, String methodName, String cacheKey) {
		FastMethod reval = fastMethodCache.get(cacheKey);
		if (reval == null) {
			Method[] methods = obj.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().equals(methodName)) {
					reval = FastClass.create(obj.getClass()).getMethod(method);
					fastMethodCache.put(cacheKey, reval);
					break;
				}
			}
		}
		return reval;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setDefaultActionMethodName(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}
}
