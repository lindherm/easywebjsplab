package com.watchdata.cardcheck.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import com.watchdata.cardcheck.utils.PropertiesManager;

/**
 * @title MainMenuBar.java
 * @description 菜单栏
 * @author pei.li 2012-3-15
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = -5799472967403105005L;
	private PropertiesManager pm = new PropertiesManager();
	/*
	 * private static final ImageIcon newIcon = ImageHelper.loadImage("new.png"); private static final ImageIcon openIcon = ImageHelper.loadImage("open.png"); private static final ImageIcon saveIcon = ImageHelper.loadImage("save.png"); private static final ImageIcon filterIcon = ImageHelper.loadImage("filter.png");
	 */

	private ButtonGroup plafGroup = null;

	public MainMenuBar() {
		plafGroup = new ButtonGroup();

		JMenu menu = new JMenu(pm.getString("mv.left.config"));
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItem = new JMenuItem(pm.getString("mv.menu.AIDConfig"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.aidConfigPanel.getName(), RightPanel.aidConfigPanel);
			}
		});
		/*
		 * menuItem.setMnemonic(KeyEvent.VK_N); menuItem.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_MASK));
		 */
		menu.add(menuItem);
		menuItem = new JMenuItem(pm.getString("mv.menu.CAConfig"));
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.caPublicKeyConfigPanel.getName(), RightPanel.caPublicKeyConfigPanel);
			}
		});
		/*
		 * menuItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_MASK));
		 */
		menu.add(menuItem);
		menuItem = new JMenuItem(pm.getString("mv.menu.issuerConfig"));
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.issuerKeyConfigPanel.getName(), RightPanel.issuerKeyConfigPanel);
			}
		});
		/*
		 * menuItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK));
		 */
		menu.add(menuItem);
		/* menu.addSeparator(); */
		/* menu.add(subMenu); */
		/* menu.addSeparator(); */
		menuItem = new JMenuItem(pm.getString("mv.menu.termPerm"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.terminalPerformanceConfigPanel.getName(), RightPanel.terminalPerformanceConfigPanel);
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem(pm.getString("mv.menu.termType"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.terminalTypeConfigPanel.getName(), RightPanel.terminalTypeConfigPanel);
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem(pm.getString("mv.menu.termLimit"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.terminalLimitConfigPanel.getName(), RightPanel.terminalLimitConfigPanel);
			}
		});
		menu.add(menuItem);
		add(menu);

		JMenu tradeMenu = new JMenu(pm.getString("mv.left.trade"));
		tradeMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem tradeMenuItem = new JMenuItem(pm.getString("mv.left.trade"));
		tradeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.tradePanel.getName(), RightPanel.tradePanel);
			}
		});
		tradeMenu.add(tradeMenuItem);
		add(tradeMenu);

		JMenu testDataMenu = new JMenu(pm.getString("mv.left.check"));
		testDataMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem testDataMenuItem = new JMenuItem(pm.getString("mv.menu.dataConfig"));
		testDataMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.add(RightPanel.testDataConfigPanel.getName(), RightPanel.testDataConfigPanel);
			}
		});
		testDataMenu.add(testDataMenuItem);
		testDataMenuItem.setMnemonic(KeyEvent.VK_S);
		testDataMenu.add(testDataMenuItem);
		testDataMenuItem = new JMenuItem(pm.getString("mv.menu.checkResult"));
		testDataMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
			}
		});
		testDataMenu.add(testDataMenuItem);
		add(testDataMenu);

		menu = new JMenu(pm.getString("mv.menu.skin"));
		menu.setMnemonic(KeyEvent.VK_L);

		JRadioButtonMenuItem radioMenuItem = new JRadioButtonMenuItem("Metal");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_METAL);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isMetalLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		/*
		 * radioMenuItem = new JRadioButtonMenuItem("Nimbus"); radioMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { Application.frame.updateLookAndFeel(GUIProperties.PLAF_NIMBUS); } }); radioMenuItem .setSelected(Application.frame.getGuiProps().isAcrylLook()); plafGroup.add(radioMenuItem); menu.add(radioMenuItem);
		 * 
		 * radioMenuItem = new JRadioButtonMenuItem("WINDOWS"); radioMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { Application.frame.updateLookAndFeel(GUIProperties.PLAF_WINDOWS); } }); radioMenuItem .setSelected(Application.frame.getGuiProps().isAcrylLook()); plafGroup.add(radioMenuItem); menu.add(radioMenuItem);
		 * 
		 * radioMenuItem = new JRadioButtonMenuItem("MOTIF"); radioMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { Application.frame.updateLookAndFeel(GUIProperties.PLAF_MOTIF); } }); radioMenuItem .setSelected(Application.frame.getGuiProps().isAcrylLook()); plafGroup.add(radioMenuItem); menu.add(radioMenuItem);
		 * 
		 * radioMenuItem = new JRadioButtonMenuItem("MAC"); radioMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { Application.frame.updateLookAndFeel(GUIProperties.PLAF_MAC); } }); radioMenuItem .setSelected(Application.frame.getGuiProps().isAcrylLook()); plafGroup.add(radioMenuItem); menu.add(radioMenuItem);
		 */

		radioMenuItem = new JRadioButtonMenuItem("Acryl");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_ACRYL);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isAcrylLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Aero");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_AERO);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isAeroLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Aluminium");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_ALUMINIUM);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isAluminiumLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Bernstein");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_BERNSTEIN);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isBernsteinLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Fast");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_FAST);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isFastLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Graphite");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_GRAPHITE);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isGraphiteLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("HiFi");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_HIFI);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isHiFiLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Luna");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_LUNA);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isLunaLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem(pm.getString("mv.menu.smart"));
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_SMART);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isSmartLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem(pm.getString("mv.menu.mcwin"));
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_MCWIN);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isMcWinLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem(pm.getString("mv.menu.mint"));
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_MINT);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isMintLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		radioMenuItem = new JRadioButtonMenuItem("Noire");
		radioMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Application.frame.updateLookAndFeel(GUIProperties.PLAF_NOIRE);
			}
		});
		radioMenuItem.setSelected(Application.frame.getGuiProps().isNoireLook());
		plafGroup.add(radioMenuItem);
		menu.add(radioMenuItem);

		/*
		 * radioMenuItem = new JRadioButtonMenuItem("Smart"); radioMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent event) { Application.frame.updateLookAndFeel(GUIProperties.PLAF_SMART); } }); radioMenuItem .setSelected(Application.frame.getGuiProps().isSmartLook()); plafGroup.add(radioMenuItem); menu.add(radioMenuItem);
		 */

		if (GUIProperties.isCustomEnabled()) {
			menu.addSeparator();
			radioMenuItem = new JRadioButtonMenuItem("Custom");
			radioMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Application.frame.updateLookAndFeel(GUIProperties.PLAF_CUSTOM);
				}
			});
			radioMenuItem.setSelected(Application.frame.getGuiProps().isCustomLook());
			plafGroup.add(radioMenuItem);
			menu.add(radioMenuItem);
		}

		add(menu);

	}

}
