package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.SwingUtils;

/**
 * @title Application.java
 * @description 应用主程序
 * @author pei.li 2012-3-15
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class Application extends JFrame {

	private static PropertiesManager pm = new PropertiesManager();
	private static final long serialVersionUID = -1077236347297286235L;
	private static Logger log = Logger.getLogger(Application.class);
	public static Application frame;
	private static final String titleStr = pm.getString("mv.application.appTitle");
	private static Container contentPanel;
	private static LeftPanel leftPanel;
	public static RightPanel rightPanel;
	private static JSplitPane splitPane;
	private static BottomPanel bottomPanel;

	/**
	 * @Title: main
	 * @Description 程序入口函数
	 * @param args
	 * @return
	 * @throws LookAndFeel不支持异常
	 *             ， 产生异常时会采用默认的LookAndFeel不会影响程序功能实现
	 */
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.put("logoString", "watchdata");
			//props.put("textShadow", "off");
			props.put("systemTextFont", "微软雅黑 PLAIN 14");
			props.put("controlTextFont", "微软雅黑 PLAIN 14");
			props.put("menuTextFont", "微软雅黑 PLAIN 14");
			props.put("userTextFont", "微软雅黑 PLAIN 14");
			props.put("subTextFont", "微软雅黑 PLAIN 14");
			props.put("windowTitleFont", "微软雅黑 BOLD 18");

			// 设置皮肤
			String currentLaf = Config.getValue("CURRENT_THEME", "current_laf");
			String currentTheme = Config.getValue("CURRENT_THEME", "current_theme");
			String lafIndex = Config.getValue("CURRENT_THEME", "current_lafIndex");

			Class<?> c = ClassLoader.getSystemClassLoader().loadClass(currentLaf);
			Method m = c.getMethod("setCurrentTheme", Properties.class);
			m.invoke(c.newInstance(), props);
			
			if (!currentTheme.equals("window")) {
				SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
			}

			UIManager.setLookAndFeel(currentLaf);
			JFrame.setDefaultLookAndFeelDecorated(true);
			SwingUtils.updateUI();
		} catch (Exception e) {
			log.error("LookAndFeel unsupported.");
		}
		frame = new Application();
		frame.setVisible(true);
	}

	public Application() {
		super(titleStr);
		setIconImage(new ImageIcon(Application.class.getResource("/com/watchdata/cardcheck/resources/images/logo_48.png")).getImage());
		initContentPane();
		setSize(new Dimension(1024, 768));
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initContentPane() {
		contentPanel = getContentPane();
		contentPanel.setLayout(new BorderLayout());
		leftPanel = new LeftPanel();
		try {
			rightPanel = new RightPanel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftPanel, rightPanel);
		splitPane.setDividerLocation(170);
		splitPane.setOneTouchExpandable(true);
		contentPanel.add(splitPane, BorderLayout.CENTER);
		bottomPanel = new BottomPanel();
		contentPanel.add(bottomPanel, BorderLayout.SOUTH);
	}
}
