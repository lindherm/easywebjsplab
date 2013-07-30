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
import com.watchdata.cardcheck.utils.Configuration;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.SpringUtil;

/**
 * TerminalTypeConfigPanel.java
 * 
 * @description: 读卡器配置界面
 * 
 * @author: pei.li 2012-4-26
 * 
 *@version:1.0.0
 * 
 *@modify：
 * 
 *@Copyright：watchdata
 */

public class CardReaderPanel extends JPanel {

	
	private static final long serialVersionUID = -6360462745055001746L;
	private PcscChannel apduChannel = (PcscChannel) SpringUtil.getBean("apduChannel");
	public static JComboBox comboBox;
	private Configuration configuration = new Configuration();
	private String[] cardReaderList;
	private DefaultComboBoxModel comboBoxModel;
	private PropertiesManager pm = new PropertiesManager();
 
	/**
	 * Create the panel
	 */
	public CardReaderPanel() {
		super();
		setLayout(null);
		setBorder(JTBorderFactory.createTitleBorder(pm.getString("mc.cardreaderpanel.cardReaderConfig")));
		/*setBackground(new Color(248,248,184));
		setBackground(new Color(164, 217, 190));*/
		
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
		
		if(cardReaderList != null && cardReaderList.length > 0 ){
			comboBoxModel = new DefaultComboBoxModel(cardReaderList);
			comboBox.setModel(comboBoxModel);
			if(containCRConfig(configuration.getValue("cardreader"), cardReaderList)){
				comboBox.setSelectedItem(configuration.getValue("cardreader"));
			}
		}
		comboBox.setBounds(100, 95, 300, 20);
	/*	comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("out");
				
			}
		});*/
		comboBox.addPopupMenuListener(new PopupMenuListener(){

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
				cardReaderList = apduChannel.getReaderList();
				if(cardReaderList != null && cardReaderList.length > 0 ){
					comboBoxModel = new DefaultComboBoxModel(cardReaderList);
					comboBox.setModel(comboBoxModel);
					comboBox.repaint();
				}else{
					comboBoxModel = new DefaultComboBoxModel();
					comboBox.setModel(comboBoxModel);
					comboBox.repaint();
				}	
			}
		});
		add(comboBox);
		

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				boolean success = true;
				if(comboBox.getSelectedItem() != null){
					success = configuration.setValue("cardreader", comboBox.getSelectedItem().toString());
				    if(success){
				    	JOptionPane.showMessageDialog(null, pm
								.getString("mc.cardreaderpanel.configsuccess"), pm
								.getString("mv.testdata.InfoWindow"),
								JOptionPane.INFORMATION_MESSAGE);
				    }else{
				    	JOptionPane.showMessageDialog(null, pm
								.getString("mc.cardreaderpanel.configfail"), pm
								.getString("mv.testdata.InfoWindow"),
								JOptionPane.INFORMATION_MESSAGE);
				    }
				}
			}
		});
		button.setText(pm.getString("mc.cardreaderpanel.save"));
		button.setBounds(502, 95, 84, 21);
		add(button);
		//
	}
	
	public boolean containCRConfig(String cardReaderConfig, String[] cardReaderList){
		boolean exist = false;
		for(String str : cardReaderList){
			if(cardReaderConfig.equals(str)){
				exist = true;
				return exist;
			}
		}
		return exist;
	}

}
