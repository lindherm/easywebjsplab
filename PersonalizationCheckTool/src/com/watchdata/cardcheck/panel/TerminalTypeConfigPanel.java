package com.watchdata.cardcheck.panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.utils.PropertiesManager;

/**
 * TerminalTypeConfigPanel.java
 * 
 * @description: 终端类型配置界面
 * 
 * @author: pei.li 2012-3-23
 * 
 *@version:1.0.0
 * 
 *@modify：
 * 
 *@Copyright：watchdata
 */
public class TerminalTypeConfigPanel extends JPanel {

	private static final long serialVersionUID = 4197596742651268659L;
	public static JComboBox counCodeComboBox;
	private JTextField counCodeValTextField;
	private JLabel counCodeLabel;
	private JLabel counCodeValLabel;
	private JLabel tradeCodeLabel;
	private JComboBox tradeCodeComboBox;
	private JLabel tradeCodeValLabel;
	private JTextField tradeCodeValTextField;
	private JLabel terTypeLabel;
	private JComboBox terTypeComboBox;
	private JLabel terTypeValLabel;
	private JTextField terTypeValTextField;
	private JLabel tradeTypeLabel;
	private JComboBox tradeTypeComboBox;
	private JTextField tradeTypeValTextField;
	private JLabel tradeTypeValLabel;
	private JButton saveButton;
	private static Logger log = Logger.getLogger(TerminalTypeConfigPanel.class);
	private PropertiesManager pm = new PropertiesManager();

	/**
	 * Create the panel
	 */
	public TerminalTypeConfigPanel() {
		super();
		setLayout(null);
		setBorder(null);
		init();
		final JLabel label_3 = new JLabel();
		label_3.setBounds(0, 50, 97, 20);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		label_3.setText(pm.getString("mv.termtype.typeconfig"));
		add(label_3);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		// 终端国家代码配置
		counCodeLabel = new JLabel();
		counCodeLabel.setBounds(29, 95, 130, 20);
		counCodeLabel.setText("国家代码(9F1A)");
		add(counCodeLabel);

		String counCode[] = { pm.getString("mv.termtype.select"), pm.getString("mv.termtype.countryCode")};
		counCodeComboBox = new JComboBox(counCode);
		counCodeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if ( pm.getString("mv.termtype.countryCode").equals(counCodeComboBox.getSelectedItem()
						.toString())) {
					counCodeValTextField.setText(pm.getString("mv.termtype.countryCodeValue"));
				} else if (pm.getString("mv.termtype.select").equals(
						counCodeComboBox.getSelectedItem().toString())) {
					counCodeValTextField.setText("");
				}
			}
		});
		counCodeComboBox.setBounds(29, 140, 150, 20);
		add(counCodeComboBox);

		counCodeValLabel = new JLabel();
		counCodeValLabel.setText(pm.getString("mv.termtype.value"));
		counCodeValLabel.setBounds(220, 95, 64, 20);
		add(counCodeValLabel);

		counCodeValTextField = new JTextField();
		counCodeValTextField.setEditable(false);
		counCodeValTextField.setBounds(220, 140, 120, 20);
		add(counCodeValTextField);

		// 交易代码配置
		tradeCodeLabel = new JLabel();
		tradeCodeLabel.setText(pm.getString("mv.termtype.tradeCode"));
		tradeCodeLabel.setBounds(29, 185, 130, 20);
		add(tradeCodeLabel);

		String tradeCode[] = { pm.getString("mv.termtype.select"), pm.getString("mv.termtype.currCode") };
		tradeCodeComboBox = new JComboBox(tradeCode);
		tradeCodeComboBox.setBounds(29, 230, 150, 20);
		tradeCodeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (pm.getString("mv.termtype.currCode").equals(tradeCodeComboBox.getSelectedItem()
						.toString())) {
					tradeCodeValTextField.setText(pm.getString("mv.termtype.currCodeValue"));
				} else if (pm.getString("mv.termtype.select").equals(
						tradeCodeComboBox.getSelectedItem().toString())) {
					tradeCodeValTextField.setText("");
				}
			}
		});
		add(tradeCodeComboBox);

		tradeCodeValLabel = new JLabel();
		tradeCodeValLabel.setText(pm.getString("mv.termtype.value"));
		tradeCodeValLabel.setBounds(220, 185, 64, 20);
		add(tradeCodeValLabel);

		tradeCodeValTextField = new JTextField();
		tradeCodeValTextField.setEditable(false);
		tradeCodeValTextField.setBounds(220, 230, 120, 20);
		add(tradeCodeValTextField);

		// 终端类型配置
		terTypeLabel = new JLabel();
		terTypeLabel.setText(pm.getString("mv.termtype.termType"));
		terTypeLabel.setBounds(29, 275, 130, 20);
		add(terTypeLabel);

		String terType[] = { pm.getString("mv.termtype.select"),pm.getString("mv.termtype.termTypeItem")};
		terTypeComboBox = new JComboBox(terType);
		terTypeComboBox.setBounds(29, 320, 150, 20);
		terTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (pm.getString("mv.termtype.termTypeItem").equals(terTypeComboBox.getSelectedItem().toString())) {
					terTypeValTextField.setText(pm.getString("mv.termtype.termTypeValue"));
				} else if (pm.getString("mv.termtype.select").equals(
						terTypeComboBox.getSelectedItem().toString())) {
					terTypeValTextField.setText("");
				}
			}
		});
		add(terTypeComboBox);

		terTypeValLabel = new JLabel();
		terTypeValLabel.setText(pm.getString("mv.termtype.value"));
		terTypeValLabel.setBounds(220, 275, 64, 20);
		add(terTypeValLabel);

		terTypeValTextField = new JTextField();
		terTypeValTextField.setEditable(false);
		terTypeValTextField.setBounds(220, 320, 120, 20);
		add(terTypeValTextField);

		// 交易类型配置
		tradeTypeLabel = new JLabel();
		tradeTypeLabel.setText(pm.getString("mv.termtype.tradeType"));
		tradeTypeLabel.setBounds(29, 365, 130, 20);
		add(tradeTypeLabel);

		String tradeType[] = { pm.getString("mv.termtype.select"),
				pm.getString("mv.termtype.tradeTypeItem")};
		tradeTypeComboBox = new JComboBox(tradeType);
		tradeTypeComboBox.setBounds(29, 410, 150, 20);
		tradeTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (pm.getString("mv.termtype.tradeTypeItem").equals(tradeTypeComboBox
						.getSelectedItem().toString())) {
					tradeTypeValTextField.setText(pm.getString("mv.termtype.tradeTypeValue"));
				} else if (pm.getString("mv.termtype.select").equals(
						tradeTypeComboBox.getSelectedItem().toString())) {
					tradeTypeValTextField.setText("");
				}
			}
		});
		add(tradeTypeComboBox);

		tradeTypeValLabel = new JLabel();
		tradeTypeValLabel.setText(pm.getString("mv.termtype.value"));
		tradeTypeValLabel.setBounds(220, 365, 64, 20);
		add(tradeTypeValLabel);

		tradeTypeValTextField = new JTextField();
		tradeTypeValTextField.setEditable(false);
		tradeTypeValTextField.setBounds(220, 410, 120, 20);
		add(tradeTypeValTextField);
		
		/*if(termInfo != null){
			counCodeValTextField.setText(termInfo.getCountryCode() == null ? "" : termInfo.getCountryCode());
			counCodeComboBox.setSelectedItem(pm.getString("mv.termtype.countryCodeValue").equals(termInfo.getCountryCode()) ?  pm.getString("mv.termtype.countryCode") : "");
			tradeCodeValTextField.setText(termInfo.getTradeType() == null ? "" : termInfo.getTradeType());
			tradeCodeComboBox.setSelectedItem(pm.getString("mv.termtype.currCodeValue").equals(termInfo.getCurrCode()) ? pm.getString("mv.termtype.currCode") : "");
			terTypeValTextField.setText(termInfo.getTermType() == null ? "" : termInfo.getTermType());
			terTypeComboBox.setSelectedItem(pm.getString("mv.termtype.termTypeValue").equals(termInfo.getTermType()) ? pm.getString("mv.termtype.termTypeItem") : "");
			tradeTypeValTextField.setText(termInfo.getTradeType() == null ? "" : termInfo.getTradeType());
			tradeTypeComboBox.setSelectedItem(pm.getString("mv.termtype.tradeTypeValue").equals(termInfo.getTradeType()) ? pm.getString("mv.termtype.tradeTypeItem") : "");
		}
*/
		// 保存配置
		saveButton = new JButton();
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				/*if (getTermID() != null) {
					saveTermType(getTermID());
				}*/
			}
		});
		saveButton.setText(pm.getString("mv.termtype.save"));
		saveButton.setBounds(386, 475, 84, 21);
		add(saveButton);
	}

	// 将终端类型保存到数据库中
	public void saveTermType(String termId) {
		Object[] param = new Object[5];
		param[0] = counCodeValTextField.getText();
		param[1] = tradeCodeValTextField.getText();
		param[2] = terTypeValTextField.getText();
		param[3] = tradeTypeValTextField.getText();
		param[4] = termId;

		/*if (param[0].toString().isEmpty()){
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.termtype.counCodeEmpty"), pm
					.getString("mv.termtype.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
		}else if(param[1].toString().isEmpty()){ 
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.termtype.tradeCodeEmpty"), pm
					.getString("mv.termtype.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
		}else if(param[2].toString().isEmpty()){ 
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.termtype.terTypeEmpty"), pm
					.getString("mv.termtype.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
		}else if(param[3].toString().isEmpty()){ 
			JOptionPane.showMessageDialog(null, pm
					.getString("mv.termtype.tradeTypeEmpty"), pm
					.getString("mv.termtype.infoWindow"),
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			if (termInfoDao.updateTermType(param)) {
				log.info("update success.");
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.termtype.addSuccess"), pm
						.getString("mv.termtype.infoWindow"),
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				log.info("update failure.");
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.termtype.addFailed"), pm
						.getString("mv.termtype.infoWindow"),
						JOptionPane.ERROR_MESSAGE);
			}
		}*/
	}

	private void init() {
		setName(pm.getString("mv.termtype.name"));
	}

}
