package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

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
			// 设置皮肤
			String currentLaf = Config.getValue("CURRENT_THEME", "current_laf");
			String currentTheme = Config.getValue("CURRENT_THEME", "current_theme");
			String lafIndex = Config.getValue("CURRENT_THEME", "current_lafIndex");

			if (!currentTheme.equals("window")) {
				SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
			}

			UIManager.setLookAndFeel(currentLaf);
			JFrame.setDefaultLookAndFeelDecorated(true);
			
			Font commonFont = new Font("微软雅黑", Font.PLAIN, 12);
			Font titleFont = new Font("微软雅黑", Font.PLAIN, 14);
			UIManager.getDefaults().put("CheckBox.font", commonFont);
			UIManager.getDefaults().put("Tree.font", commonFont);
			UIManager.getDefaults().put("Viewport.font", commonFont);
			UIManager.getDefaults().put("ProgressBar.font", commonFont);
			UIManager.getDefaults().put("RadioButtonMenuItem.font", commonFont);
			UIManager.getDefaults().put("FormattedTextField.font", commonFont);
			UIManager.getDefaults().put("ToolBar.font", commonFont);
			UIManager.getDefaults().put("ColorChooser.font", commonFont);
			UIManager.getDefaults().put("ToggleButton.font", commonFont);
			UIManager.getDefaults().put("Panel.font", commonFont);
			UIManager.getDefaults().put("TextArea.font", commonFont);
			UIManager.getDefaults().put("Menu.font", commonFont);
			UIManager.getDefaults().put("RadioButtonMenuItem.acceleratorFont", commonFont);
			UIManager.getDefaults().put("Spinner.font", commonFont);
			UIManager.getDefaults().put("Menu.acceleratorFont", commonFont);
			UIManager.getDefaults().put("CheckBoxMenuItem.acceleratorFont", commonFont);
			UIManager.getDefaults().put("TableHeader.font", commonFont);
			UIManager.getDefaults().put("TextField.font", commonFont);
			UIManager.getDefaults().put("OptionPane.font", commonFont);
			UIManager.getDefaults().put("MenuBar.font", commonFont);
			UIManager.getDefaults().put("Button.font", commonFont);
			UIManager.getDefaults().put("Label.font", commonFont);
			UIManager.getDefaults().put("PasswordField.font", commonFont);
			UIManager.getDefaults().put("InternalFrame.titleFont", titleFont);
			UIManager.getDefaults().put("OptionPane.buttonFont", commonFont);
			UIManager.getDefaults().put("ScrollPane.font", commonFont);
			UIManager.getDefaults().put("MenuItem.font", commonFont);
			UIManager.getDefaults().put("ToolTip.font", commonFont);
			UIManager.getDefaults().put("List.font", commonFont);
			UIManager.getDefaults().put("OptionPane.messageFont", commonFont);
			UIManager.getDefaults().put("EditorPane.font", commonFont);
			UIManager.getDefaults().put("Table.font", commonFont);
			UIManager.getDefaults().put("TabbedPane.font", commonFont);
			UIManager.getDefaults().put("RadioButton.font", commonFont);
			UIManager.getDefaults().put("CheckBoxMenuItem.font", commonFont);
			UIManager.getDefaults().put("TextPane.font", commonFont);
			UIManager.getDefaults().put("PopupMenu.font", commonFont);
			UIManager.getDefaults().put("TitledBorder.font", commonFont);
			UIManager.getDefaults().put("ComboBox.font", commonFont);

			SwingUtils.updateUI();
		} catch (Exception e) {
			log.error("LookAndFeel unsupported.");
		}
		frame = new Application();
		frame.setVisible(true);
	}

	public Application() {
		setTitle(titleStr);
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
