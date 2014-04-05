package com.gerenhua.cardcheck.panel;

import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.gerenhua.cardcheck.app.Application;
import com.gerenhua.cardcheck.utils.Config;
import com.gerenhua.cardcheck.utils.FixedSizePlainDocument;
import com.gerenhua.cardcheck.utils.PropertiesManager;

/**
 * @author: HeYa
 * 
 * @version: 2012-3-20 上午09:56:04
 * 
 * @description: 终端限制配置界面
 * 
 * @Copyright: watchdata
 */
public class TerminalLimitConfigPanel extends JPanel {

	private static final long serialVersionUID = 8464687872814772188L;
	private PropertiesManager pm = new PropertiesManager();
	public static JTextField termIdField = new JTextField();
	public static JTextField lowLimitField = new JTextField();
	private JTextField tacAccessField = new JTextField();
	private JTextField tacOnlineField = new JTextField();
	public static JTextField tacRejectField = new JTextField();

	Color colorYellow = new Color(255, 255, 192);

	/**
	 * Create the panel
	 */
	public TerminalLimitConfigPanel() {
		setLayout(null);
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.termlimit.termlimitconfig")));
		init();

		tacRejectField.setDocument(new FixedSizePlainDocument(10));
		tacOnlineField.setDocument(new FixedSizePlainDocument(10));
		tacAccessField.setDocument(new FixedSizePlainDocument(10));
		lowLimitField.setDocument(new FixedSizePlainDocument(8));
		termIdField.setDocument(new FixedSizePlainDocument(8));
		setLayout(null);

		final JLabel tacRejectLabel = new JLabel();
		tacRejectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacRejectLabel.setBounds(10, 10, 130, 20);
		tacRejectLabel.setText(pm.getString("mv.termlimit.tac_refuse"));
		add(tacRejectLabel);

		tacRejectField.setBounds(145, 10, 180, 20);
		String PROMPT = pm.getString("mv.termlimit.promotetac");

		tacRejectField.setToolTipText(PROMPT);
		tacRejectField.addKeyListener(new KeyListener() {
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
		add(tacRejectField);

		tacRejectField.setText(Config.getValue("Terminal_Data", "TAC_ADJECT"));

		final JLabel tacOnlineLabel = new JLabel();
		tacOnlineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacOnlineLabel.setText(pm.getString("mv.termlimit.tac_online"));
		tacOnlineLabel.setBounds(10, 40, 130, 20);
		add(tacOnlineLabel);

		tacOnlineField.setBounds(145, 40, 180, 20);
		tacOnlineField.setToolTipText(PROMPT);
		tacOnlineField.addKeyListener(new KeyListener() {
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
		add(tacOnlineField);

		tacOnlineField.setText(Config.getValue("Terminal_Data", "TAC_ONLINE"));

		final JLabel tacAccessLabel = new JLabel();
		tacAccessLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacAccessLabel.setText(pm.getString("mv.termlimit.tac_access"));
		tacAccessLabel.setBounds(10, 70, 130, 20);
		add(tacAccessLabel);

		tacAccessField.setBounds(145, 70, 180, 20);

		tacAccessField.setToolTipText(PROMPT);
		tacAccessField.addKeyListener(new KeyListener() {
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

		tacAccessField.setText(Config.getValue("Terminal_Data", "TAC_DEFAULT"));
		add(tacAccessField);

		final JLabel lowLimitLabel = new JLabel();
		lowLimitLabel.setBounds(0, 100, 140, 20);
		lowLimitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lowLimitLabel.setText(pm.getString("mv.termlimit.lowlimit"));
		add(lowLimitLabel);
		String PROMPTlIMIT = pm.getString("mv.termlimit.promotelimit");
		lowLimitField.setBounds(145, 100, 180, 20);
		lowLimitField.setToolTipText(PROMPTlIMIT);
		lowLimitField.addKeyListener(new KeyListener() {
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
		lowLimitField.setText(Config.getValue("Terminal_Data", "9F1B"));
		add(lowLimitField);

		final JLabel termIdLabel = new JLabel();
		termIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		termIdLabel.setBounds(10, 130, 130, 20);
		termIdLabel.setText(pm.getString("mv.termlimit.idcontent"));
		add(termIdLabel);
		String PROMPTTERM = pm.getString("mv.termlimit.promoteid");
		termIdField.setBounds(145, 130, 180, 20);
		termIdField.setToolTipText(PROMPTTERM);
		termIdField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				String c = String.valueOf(e.getKeyChar());
				Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
				Matcher matcher = pattern.matcher(c);
				if (!(matcher.matches()))
					e.consume();
			}
		});

		termIdField.setText(Config.getValue("Terminal_Data", "9F1C"));
		add(termIdField);

		final JButton saveButton = new JButton();
		saveButton.setText(pm.getString("mv.termlimit.save"));
		saveButton.setBounds(145, 160, 84, 21);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				Config.setValue("Terminal_Data", "TAC_ADJECT", tacRejectField.getText().trim());
				Config.setValue("Terminal_Data", "TAC_ONLINE", tacOnlineField.getText().trim());
				Config.setValue("Terminal_Data", "TAC_DEFAULT", tacAccessField.getText().trim());
				Config.setValue("Terminal_Data", "9F1B", lowLimitField.getText().trim());
				Config.setValue("Terminal_Data", "9F1C", termIdField.getText().trim());
				
				JOptionPane.showMessageDialog(Application.frame, "保存成功！");
			}
		});
		saveButton.setFocusPainted(false);
		saveButton.setBorderPainted(false);
		add(saveButton);

	}

	private void init() {
		setName(pm.getString("mv.termlimit.termlimitconfig"));
	}

	// 判断输入框的数据输入是否合法
	public static int checkData(String data) {
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
		String result[] = data.split("[|]");
		for (int i = 0; i < result.length; i++) {
			int num[] = { 10, 10, 10, 8, 8 };
			if (result[i].isEmpty() || !pattern.matcher(result[i]).matches() || result[i].length() != num[i]) {
				return i + 1;
			}
		}
		return 0;

	}

}
