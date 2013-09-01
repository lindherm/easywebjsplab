package com.watchdata.cardcheck.panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.watchdata.cardcheck.logic.apdu.pcsc.PcscChannel;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardpcsc.CardPcsc;
import com.watchdata.commons.lang.WDByteUtil;

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
	private String[] cardReaderList;
	private DefaultComboBoxModel comboBoxModel;
	private PropertiesManager pm = new PropertiesManager();
	private CardPcsc cardPcsc = new CardPcsc();
	PcscChannel apduChannel=new PcscChannel();

	/**
	 * Create the panel
	 */
	public CardReaderPanel() {
		super();
		setLayout(null);
		//setBorder(JTBorderFactory.createTitleBorder(pm.getString("mc.cardreaderpanel.cardReaderConfig")));

		final JLabel label = new JLabel();
		label.setBounds(0, 50, 97, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		label.setText(pm.getString("mc.cardreaderpanel.chooseReader"));
		add(label);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mc.cardreaderpanel.cardReader"));
		label_1.setBounds(0, 95, 97, 20);
		add(label_1);

		comboBox = new JComboBox();
		comboBoxModel = new DefaultComboBoxModel();
		cardReaderList = apduChannel.getReaderList();

		if (cardReaderList != null && cardReaderList.length > 0) {
			comboBoxModel = new DefaultComboBoxModel(cardReaderList);
			comboBox.setModel(comboBoxModel);
			String reader=Config.getValue("Terminal_Data", "reader");
			if (containCRConfig(reader,cardReaderList)) {
				comboBox.setSelectedItem(reader);
			}
		}
		comboBox.setBounds(100, 95, 300, 20);
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
				//cardReaderList = apduChannel.getReaderList();
				if (cardReaderList != null && cardReaderList.length > 0) {
					comboBoxModel = new DefaultComboBoxModel(cardReaderList);
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
					String resetStr = WDByteUtil.bytes2HEX(cardPcsc.resetCard());
					JOptionPane.showMessageDialog(null, resetStr.substring(0, resetStr.length() - 4));
					Config.setValue("Terminal_Data", "reader", comboBox.getSelectedItem().toString());
				}
			}
		});
		button.setBounds(507, 92, 84, 21);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		add(button);

		final JButton btnNewButton = new JButton("打开端口");
		final JButton btnNewButton_1 = new JButton("关闭端口");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cardPcsc.connectReader(comboBox.getSelectedItem().toString())) {
					((JButton) e.getSource()).setEnabled(false);
					btnNewButton_1.setEnabled(true);
				}
			}
		});
		btnNewButton.setBounds(413, 92, 84, 21);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		add(btnNewButton);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardPcsc.disConnectReader();
				((JButton) e.getSource()).setEnabled(false);
				btnNewButton.setEnabled(true);
			}
		});
		btnNewButton_1.setBounds(601, 92, 84, 21);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorderPainted(false);
		add(btnNewButton_1);
	}

	public boolean containCRConfig(String cardReaderConfig, String[] cardReaderList) {
		boolean exist = false;
		for (String str : cardReaderList) {
			if (cardReaderConfig.equals(str)) {
				exist = true;
				return exist;
			}
		}
		return exist;
	}
}
