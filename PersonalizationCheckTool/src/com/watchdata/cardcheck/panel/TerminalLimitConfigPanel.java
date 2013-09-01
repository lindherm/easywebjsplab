package com.watchdata.cardcheck.panel;

import java.awt.Color;
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

import com.watchdata.cardcheck.app.Application;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;

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

		final JLabel TermCodelabel = new JLabel();
		TermCodelabel.setBounds(0, 50, 97, 20);
		TermCodelabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermCodelabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermCodelabel.setText(pm.getString("mv.termlimit.termact"));
		add(TermCodelabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		final JLabel tacRejectLabel = new JLabel();
		tacRejectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacRejectLabel.setBounds(10, 80, 130, 20);
		tacRejectLabel.setText(pm.getString("mv.termlimit.tac_refuse"));
		add(tacRejectLabel);

		tacRejectField.setBounds(145, 80, 180, 20);
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
		tacOnlineLabel.setBounds(10, 110, 130, 20);
		add(tacOnlineLabel);

		tacOnlineField.setBounds(145, 110, 180, 20);
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
		tacAccessLabel.setBounds(10, 140, 130, 20);
		add(tacAccessLabel);

		tacAccessField.setBounds(145, 140, 180, 20);

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

		final JLabel TermLimitLabel = new JLabel();
		TermLimitLabel.setBounds(0, 170, 97, 20);
		TermLimitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermLimitLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermLimitLabel.setText(pm.getString("mv.termlimit.termlimit"));
		add(TermLimitLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 180, 730, 20);
		add(separator_1);

		final JLabel lowLimitLabel = new JLabel();
		lowLimitLabel.setBounds(0, 210, 140, 20);
		lowLimitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lowLimitLabel.setText(pm.getString("mv.termlimit.lowlimit"));
		add(lowLimitLabel);
		String PROMPTlIMIT = pm.getString("mv.termlimit.promotelimit");
		lowLimitField.setBounds(145, 210, 180, 20);
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

		final JLabel TermIDLabel = new JLabel();
		TermIDLabel.setBounds(0, 250, 97, 20);
		TermIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermIDLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermIDLabel.setText(pm.getString("mv.termlimit.termid"));
		add(TermIDLabel);

		final JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(79, 260, 730, 20);
		add(separator_2);

		final JLabel termIdLabel = new JLabel();
		termIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		termIdLabel.setBounds(10, 280, 130, 20);
		termIdLabel.setText(pm.getString("mv.termlimit.idcontent"));
		add(termIdLabel);
		String PROMPTTERM = pm.getString("mv.termlimit.promoteid");
		termIdField.setBounds(145, 280, 180, 20);
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
		saveButton.setBounds(145, 334, 84, 21);
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
