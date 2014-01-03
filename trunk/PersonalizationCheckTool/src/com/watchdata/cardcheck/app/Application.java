package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.apache.log4j.Logger;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
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
	public static GUIProperties guiProps = new GUIProperties();
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
			// props.put("textShadow", "off");
			props.put("systemTextFont", "宋体 PLAIN 13");
			props.put("controlTextFont", "宋体 PLAIN 13");
			props.put("menuTextFont", "宋体 PLAIN 13");
			props.put("userTextFont", "宋体 PLAIN 13");
			props.put("subTextFont", "宋体 PLAIN 12");
			props.put("windowTitleFont", "宋体 BOLD 16");

			// 设置皮肤
			String currentLaf = Config.getValue("CURRENT_THEME", "current_laf");
			String currentTheme = Config.getValue("CURRENT_THEME", "current_theme");
			String lafIndex = Config.getValue("CURRENT_THEME", "current_lafIndex");

			if (!currentTheme.equals("window")) {
				SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
			}

			UIManager.setLookAndFeel(currentLaf);

			SwingUtils.updateUI();
		} catch (Exception e) {
			log.error("LookAndFeel unsupported.");
		}
		frame = new Application();
		frame.setVisible(true);
	}

	public Application() {
		super(titleStr);
		initContentPane();
		// setFont(new Font("", Font.BOLD, 14));
		setSize(new Dimension(800, 600));
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocationRelativeTo(null);
		// getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		// setAlwaysOnTop(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// UIManager.put("JRootPane.font ", new FontUIResource("宋体 ", 0, 20));
	}

	private void initContentPane() {
		try {
			Properties props = new Properties();
			props.put("logoString", "watchdata");
			// props.put("textShadow", "off");
			props.put("systemTextFont", "宋体 PLAIN 13");
			props.put("controlTextFont", "宋体 PLAIN 13");
			props.put("menuTextFont", "宋体 PLAIN 13");
			props.put("userTextFont", "宋体 PLAIN 13");
			props.put("subTextFont", "宋体 PLAIN 12");
			props.put("windowTitleFont", "宋体 BOLD 16");

			// 设置皮肤
			String currentLaf = Config.getValue("CURRENT_THEME", "current_laf");
			String currentTheme = Config.getValue("CURRENT_THEME", "current_theme");
			String lafIndex = Config.getValue("CURRENT_THEME", "current_lafIndex");

			if (!currentTheme.equals("window")) {
				SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
			}

			UIManager.setLookAndFeel(currentLaf);

			SwingUtils.updateUI();
		} catch (Exception e) {
			log.error("LookAndFeel unsupported.");
		}
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
		splitPane.setDividerLocation(150);
		splitPane.setOneTouchExpandable(true);
		contentPanel.add(splitPane, BorderLayout.CENTER);
		bottomPanel = new BottomPanel();
		contentPanel.add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * @Title: updateLookAndFeel
	 * @Description 更新界面皮肤
	 * @param lf
	 *            LookAndFeel名称
	 * @return
	 * @throws LookAndFeel不支持异常
	 *             ，产生异常时会采用默认的LookAndFeel，更新皮肤功能不生效
	 */
	public void updateLookAndFeel(String lf) {

		try {
			// reset to default theme
			if (lf.equals(GUIProperties.PLAF_METAL))
				javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
			else if (lf.equals(GUIProperties.PLAF_FAST))
				com.jtattoo.plaf.fast.FastLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_SMART))
				com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_ACRYL))
				com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_AERO))
				com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_BERNSTEIN))
				com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_ALUMINIUM))
				com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_MCWIN))
				com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_MINT))
				com.jtattoo.plaf.mint.MintLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_HIFI))
				com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_NOIRE))
				com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Default");
			else if (lf.equals(GUIProperties.PLAF_LUNA))
				com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme("Default");

			guiProps.setTheme("Default");
			guiProps.setLookAndFeel(lf);
			UIManager.setLookAndFeel(guiProps.getLookAndFeel());
			// Update the application
			getRootPane().updateUI();
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception ex) {
			System.out.println("Failed loading L&F: " + guiProps.getLookAndFeel() + " Exception: " + ex.getMessage());
		}
	}

	public GUIProperties getGuiProps() {
		return guiProps;
	}

	public static void initGlobalFontSetting(String fontName, int style, int size) {
		Font fnt = new Font(fontName, style, size);
		FontUIResource fontRes = new FontUIResource(fnt);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, fontRes);
		}
	}
}
