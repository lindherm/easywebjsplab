package com.gerenhua.tool.panel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gerenhua.tool.utils.Config;
import com.gerenhua.tool.utils.SwingUtils;
import com.jtattoo.plaf.AbstractLookAndFeel;

public class FacePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4181913088649787998L;
	private static final String[] lafNames = new String[] { "Acryl", "Aero", "Aluminium", "Bernstein", "Fast", "Graphite", "HiFi", "Luna", "McWin", "Mint", "Noire", "Smart", "Texture" };

	// The one and only instance of the sample application
	private JList lafList = null;
	private int selectedLaf = 0;
	private ListSelectionListener lafListener = null;

	private JList themeList = new JList();
	private int selectedTheme = 0;
	private ListSelectionListener themeListener = null;

	public FacePanel() {
		//super(frame, "皮肤", true);
		//setIconImage(null);
		//getContentPane().
		setLayout(null);
		lafListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (lafList.getSelectedIndex() != -1) {
						if (selectedLaf != lafList.getSelectedIndex()) {
							selectedLaf = lafList.getSelectedIndex();
							// We change the look and feel after all pending events are dispatched,
							// otherwise there will be some serious redrawing problems.
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									setLookAndFeel(true);
								}
							});
						}
					} else {
						// We don't want the list to be unselected, so if user unselects the list
						// we just select the last selected entry
						lafList.setSelectedIndex(selectedLaf);
					}
				}
			}
		};
		fillThemeList();

		themeListener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (themeList.getSelectedIndex() != -1) {
						if (selectedTheme != themeList.getSelectedIndex()) {
							selectedTheme = themeList.getSelectedIndex();
							// We change the look and feel after all pending events are dispatched,
							// otherwise there will be some serious redrawing problems.
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									setLookAndFeel(false);
								}
							});
						}
					} else {
						// We don't want the list to be unselected, so if user unselects the list
						// we just select the last selected entry
						themeList.setSelectedIndex(selectedTheme);
					}
				}
			}
		};

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "皮肤", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(22, 10, 220, 300);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		lafList = new JList(lafNames);

		lafList.setSelectedIndex(0);
		lafList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lafList.addListSelectionListener(lafListener);
		scrollPane.setViewportView(lafList);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "主题", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(280, 10, 220, 300);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		themeList = new JList();
		scrollPane_1.setViewportView(themeList);

		JButton btnNewButton = new JButton("保存设置");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String select = lafList.getSelectedValue().toString();

				String selectFace = "com.jtattoo.plaf." + select.toLowerCase() + "." + select + "LookAndFeel";
				String selectTheme = themeList.getSelectedValue().toString();

				Config.setValue("CURRENT_THEME","current_laf", selectFace);
				Config.setValue("CURRENT_THEME","current_theme", selectTheme);
				Config.setValue("CURRENT_THEME","current_lafIndex", String.valueOf(lafList.getSelectedIndex()));
				//System.out.println(String.valueOf(lafList.getSelectedIndex()));
				try {
					SwingUtils.restart("CardPAT");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(291, 349, 93, 23);
		add(btnNewButton);

		JButton button = new JButton("恢复默认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config.setValue("CURRENT_THEME","current_laf", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				Config.setValue("CURRENT_THEME","current_theme", "window");
				try {
					SwingUtils.restart("CardPAT");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		button.setBounds(394, 349, 93, 23);
		add(button);
	}

	private void setLookAndFeel(boolean loadThemes) {
		try {
			String theme = "Default";
			if (!loadThemes) {
				theme = (String) themeList.getSelectedValue();
			}
			switch (selectedLaf) {
			case 0:
				// First set the theme of the look and feel. This must be done first because there
				// is some static initializing (color values etc.) when calling setTheme.
				// Another reason is that the theme variables are shared with all look and feels, so
				// without calling the setTheme method the look and feel will look ugly (wrong colors).
				com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme(theme);
				// Now we can set the look and feel
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
				break;
			case 1:
				com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
				break;
			case 2:
				com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
				break;
			case 3:
				com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
				break;
			case 4:
				com.jtattoo.plaf.fast.FastLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
				break;
			case 5:
				com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
				break;
			case 6:
				com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
				break;
			case 7:
				com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
				break;
			case 8:
				com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				break;
			case 9:
				com.jtattoo.plaf.mint.MintLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
				break;
			case 10:
				com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
				break;
			case 11:
				com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
				break;
			case 12:
				com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme(theme);
				UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
				break;
			}

			SwingUtils.updateUI();
			if (loadThemes) {
				fillThemeList();
			}
			scrollSelectedToVisible(lafList);
			scrollSelectedToVisible(themeList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} // end setLookAndFeel

	private void fillThemeList() {
		// We don't want to get changed events while setup the new theme
		// so we remove the selection listener
		themeList.removeListSelectionListener(themeListener);
		// Setup the theme list with data from the look and feel classes
		LookAndFeel laf = UIManager.getLookAndFeel();
		if (laf instanceof com.jtattoo.plaf.acryl.AcrylLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.acryl.AcrylLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.aero.AeroLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.aero.AeroLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.aluminium.AluminiumLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.bernstein.BernsteinLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.fast.FastLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.fast.FastLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.graphite.GraphiteLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.graphite.GraphiteLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.hifi.HiFiLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.hifi.HiFiLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.luna.LunaLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.luna.LunaLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.mcwin.McWinLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.mcwin.McWinLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.mint.MintLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.mint.MintLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.noire.NoireLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.noire.NoireLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.smart.SmartLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.smart.SmartLookAndFeel.getThemes().toArray());
		} else if (laf instanceof com.jtattoo.plaf.texture.TextureLookAndFeel) {
			themeList.setListData(com.jtattoo.plaf.texture.TextureLookAndFeel.getThemes().toArray());
		} else {
			themeList.setListData((Object[]) null);
		}
		if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
			themeList.setSelectedValue("Default", true);
		}
		selectedTheme = themeList.getSelectedIndex();
		// Add the selection listener we have removed above
		themeList.addListSelectionListener(themeListener);
	} // end fillThemeList

	private void scrollSelectedToVisible(JList list) {
		// Because of the different font size the selected item
		// maybe out of the visible area. So we correct this.
		int idx = list.getLeadSelectionIndex();
		Rectangle rect = list.getCellBounds(idx, idx);
		if (rect != null) {
			list.scrollRectToVisible(rect);
		}
	} // end scrollSelectedToVisible
}
