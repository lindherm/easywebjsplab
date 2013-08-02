package com.watchdata.cardcheck.panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.watchdata.cardcheck.configdao.IssuerKeyInfo;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;

public class IssuerKeyConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField;
	private PropertiesManager pm = new PropertiesManager();

	private IssuerKeyInfo issuerKeyInfo=new IssuerKeyInfo();

	/**
	 * Create the panel
	 */
	public IssuerKeyConfigPanel() {
		super();
		setLayout(null);
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.issuerkeyconfig.issuerkey")));
		init();

		final JLabel issuerKeyLabel = new JLabel();
		issuerKeyLabel.setBounds(0, 50, 97, 20);
		issuerKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		issuerKeyLabel.setFont(new Font("宋体", Font.BOLD, 12));
		issuerKeyLabel.setText(pm.getString("mv.issuerkeyconfig.keymgr"));
		add(issuerKeyLabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		final JLabel lblUdkac = new JLabel();
		lblUdkac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUdkac.setText("UDKac：");
		lblUdkac.setBounds(20, 90, 97, 20);
		add(lblUdkac);

		IssuerKeyInfo ikInfo=issuerKeyInfo.getIssuerKeyInfo("ApplicationKey");
		
		textField = new JTextField();
		textField.setBounds(122, 90, 400, 20);
		textField.setDocument(new FixedSizePlainDocument(32));
		textField.setToolTipText(pm.getString("mv.issuerkeyconfig.fullkey"));
		textField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				String c = String.valueOf(e.getKeyChar());
				Pattern pattern = Pattern.compile("[0-9a-fA-F]*");

				Matcher matcher = pattern.matcher(c);
				if (!(matcher.matches()))
					e.consume();
			}
		});
		textField.setText(ikInfo.getAcKey());
		add(textField);

		final JLabel lblUdkmac = new JLabel();
		lblUdkmac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUdkmac.setText("UDKmac：");
		lblUdkmac.setBounds(20, 135, 97, 20);
		add(lblUdkmac);

		textField_1 = new JTextField();
		textField_1.setBounds(122, 135, 400, 20);
		textField_1.setDocument(new FixedSizePlainDocument(32));
		textField_1.setToolTipText(pm.getString("mv.issuerkeyconfig.fullkey"));
		textField_1.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				String c = String.valueOf(e.getKeyChar());
				Pattern pattern = Pattern.compile("[0-9a-fA-F]*");

				Matcher matcher = pattern.matcher(c);
				if (!(matcher.matches()))
					e.consume();
			}
		});
		textField_1.setText(ikInfo.getMacKey());
		add(textField_1);

		final JLabel lblUdkenc = new JLabel();
		lblUdkenc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUdkenc.setText("UDKenc：");
		lblUdkenc.setBounds(10, 180, 107, 20);
		add(lblUdkenc);

		textField_2 = new JTextField();
		textField_2.setBounds(122, 180, 400, 20);
		textField_2.setDocument(new FixedSizePlainDocument(32));
		textField_2.setToolTipText(pm.getString("mv.issuerkeyconfig.fullkey"));
		textField_2.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				String c = String.valueOf(e.getKeyChar());
				Pattern pattern = Pattern.compile("[0-9a-fA-F]*");

				Matcher matcher = pattern.matcher(c);
				if (!(matcher.matches()))
					e.consume();
			}
		});
		textField_2.setText(ikInfo.getEncKey());
		add(textField_2);
		
		final JButton button_1 = new JButton();
		button_1.setText("保存");
		button_1.setBounds(260, 230, 84, 21);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Config.setValue("ApplicationKey", "UDKac", textField.getText().trim());
				Config.setValue("ApplicationKey", "UDKmac", textField_1.getText().trim());
				Config.setValue("ApplicationKey", "UDKenc", textField_2.getText().trim());
				Config.write();
				
				JOptionPane.showMessageDialog(null, "保存成功！", "提示框", JOptionPane.INFORMATION_MESSAGE);
			}

		});
		add(button_1);

		JButton btnNewButton = new JButton("导入");
		btnNewButton.setBounds(142, 230, 84, 21);
		add(btnNewButton);

	}

	private void init() {
		setName(pm.getString("mv.issuerkeyconfig.name"));
	}
}
