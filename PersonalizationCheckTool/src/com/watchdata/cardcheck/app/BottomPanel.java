package com.watchdata.cardcheck.app;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.watchdata.cardcheck.utils.PropertiesManager;
/**
 * @title BottomPanel.java
 * @description 程序底部界面
 * @author pei.li 2012-3-15
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class BottomPanel extends JPanel {

	private static final long serialVersionUID = -6944067066093744254L;
	private PropertiesManager pm = new PropertiesManager();
	

	/**
	 * Create the panel
	 */
	public BottomPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel label = new JLabel();
		label.setText(pm.getString("mv.bottom.version"));
		panel.add(label);

		final JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel wwwwatchdatacomLabel = new JLabel();
		wwwwatchdatacomLabel.setText(pm.getString("mv.bottom.url"));
		panel_1.add(wwwwatchdatacomLabel);

		final JPanel panel_2 = new JPanel();
		add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		final JLabel label_1 = new JLabel();
		label_1.setText(pm.getString("mv.bottom.right"));
		panel_2.add(label_1);
	}

}
