package com.gerenhua.tool.panel;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.gerenhua.tool.logic.apdu.CommonAPDU;
import com.gerenhua.tool.logic.apdu.pcsc.PcscChannel;
import com.gerenhua.tool.utils.Config;
import com.gerenhua.tool.utils.FileUtil;
import com.gerenhua.tool.utils.PropertiesManager;

/**
 * TerminalTypeConfigPanel.java
 * 
 * @description: 读卡器配置界面
 * 
 * @author: pei.li 2012-4-26
 * 
 * @version:1.0.0
 * 
 * @modify：
 * 
 * @Copyright：watchdata
 */

public class CardReaderPanel extends JPanel {

	private static final long serialVersionUID = -6360462745055001746L;
	public static JComboBox comboBox;
	private List<String> cardReaderList;
	private DefaultComboBoxModel comboBoxModel;
	private PropertiesManager pm = new PropertiesManager();
	public PcscChannel apduChannel = new PcscChannel();
	public static CommonAPDU commonAPDU;

	/**
	 * Create the panel
	 */
	public CardReaderPanel() {
		super();
		setLayout(null);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mc.cardreaderpanel.cardReader"));
		label_1.setBounds(-30, 11, 97, 20);
		add(label_1);

		comboBox = new JComboBox();
		comboBoxModel = new DefaultComboBoxModel();
		cardReaderList = apduChannel.getReaderList();
		// cardReaderList.add("10.0.97.248:5000");

		if (cardReaderList != null && cardReaderList.size() > 0) {
			comboBoxModel = new DefaultComboBoxModel(cardReaderList.toArray());
			comboBox.setModel(comboBoxModel);
			String reader = Config.getValue("Terminal_Data", "reader");
			if (cardReaderList.contains(reader)) {
				comboBox.setSelectedItem(reader);
			}
		}
		comboBox.setBounds(70, 11, 365, 20);
		comboBox.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				// cardReaderList = apduChannel.getReaderList();
				if (cardReaderList != null && cardReaderList.size() > 0) {
					comboBoxModel = new DefaultComboBoxModel(cardReaderList.toArray());
					comboBox.setModel(comboBoxModel);
					comboBox.repaint();
				} else {
					comboBoxModel = new DefaultComboBoxModel();
					comboBox.setModel(comboBoxModel);
					comboBox.repaint();
				}
			}
		});
		add(comboBox);

		final JButton button = new JButton();
		button.setText("复位");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				if (comboBox.getSelectedItem() != null) {
					String reader = comboBox.getSelectedItem().toString();
					HashMap<String, String> res = commonAPDU.reset(reader);
					StringSelection atr = new StringSelection(res.get("atr"));
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

					String atrS = "";

					if ("9000".equals(res.get("sw"))) {
						clipboard.setContents(atr, null);
						atrS = res.get("atr");
					} else {
						atrS = "";
					}
					JOptionPane.showMessageDialog(null, atrS + res.get("sw"));
					Config.setValue("Terminal_Data", "reader", reader);
				}
			}
		});
		button.setBounds(534, 11, 60, 21);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		add(button);

		final JButton btnNewButton = new JButton("打开");
		final JButton btnNewButton_1 = new JButton("关闭");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String reader = comboBox.getSelectedItem().toString();
				if (reader.indexOf(':') > 0) {
					String[] board = reader.split(":");
					FileUtil.updateBoradFile(board[0], board[1]);
				}
				commonAPDU = new CommonAPDU();
				boolean flag = commonAPDU.init(reader);
				if (flag) {
					((JButton) e.getSource()).setEnabled(false);
					btnNewButton_1.setEnabled(true);
				}
			}
		});
		btnNewButton.setBounds(464, 11, 60, 21);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		add(btnNewButton);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commonAPDU.close();
				((JButton) e.getSource()).setEnabled(false);
				btnNewButton.setEnabled(true);
			}
		});
		btnNewButton_1.setBounds(604, 11, 60, 21);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorderPainted(false);
		add(btnNewButton_1);
	}

}
