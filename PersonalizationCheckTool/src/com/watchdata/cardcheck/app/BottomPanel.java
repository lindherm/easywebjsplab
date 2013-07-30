package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
		setLayout(new BorderLayout());

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		add(panel, BorderLayout.WEST);

		final JLabel label = new JLabel();
		label.setText(pm.getString("mv.bottom.version"));
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 100, 10, 0);
		panel.add(label, gridBagConstraints);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		add(panel_1, BorderLayout.EAST);

		final JLabel wwwwatchdatacomLabel = new JLabel();
		wwwwatchdatacomLabel.setText(pm.getString("mv.bottom.url"));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(0, 0, 0, 100);
		panel_1.add(wwwwatchdatacomLabel, gridBagConstraints_2);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new GridBagLayout());
		add(panel_2, BorderLayout.CENTER);

		final JLabel label_1 = new JLabel();
		label_1.setText(pm.getString("mv.bottom.right"));
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 0;
		panel_2.add(label_1, gridBagConstraints_1);
	}

}
