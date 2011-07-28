package org.easyweb.mvc.datapipe;

import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

public abstract class DataPipe {
	private static ThreadLocal<Pipe> dataHolder = new ThreadLocal<Pipe>() {
		protected Pipe initialValue() {
			return new Pipe();
		}
	};

	public static Pipe getPipe() {
		return dataHolder.get();
	}

	/**
	 * clear datas in the pipe
	 */
	public static void clear() {
		dataHolder.set(new Pipe());
	}

	public static class Pipe {
		/**
		 * 工作流名称
		 */
		private String workflowName;

		/**
		 * Springframework应用程序容器
		 */
		private ApplicationContext context;
		/**
		 * 当前http会话
		 */
		private HttpSession httpSession;

		public String getWorkflowName() {
			return workflowName;
		}

		public void setWorkflowName(String workflowName) {
			this.workflowName = workflowName;
		}

		public ApplicationContext getContext() {
			return context;
		}

		public void setContext(ApplicationContext context) {
			this.context = context;
		}

		public HttpSession getHttpSession() {
			return httpSession;
		}

		public void setHttpSession(HttpSession httpSession) {
			this.httpSession = httpSession;
		}

	}
}
