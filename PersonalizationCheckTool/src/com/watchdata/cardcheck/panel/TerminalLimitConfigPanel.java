package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
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

import com.watchdata.cardcheck.configdao.TermInfo;
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
		super(new BorderLayout());
		setLayout(null);
		setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.termlimit.termlimitconfig")));
		init();

		tacRejectField.setDocument(new FixedSizePlainDocument(10));
		tacOnlineField.setDocument(new FixedSizePlainDocument(10));
		tacAccessField.setDocument(new FixedSizePlainDocument(10));
		lowLimitField.setDocument(new FixedSizePlainDocument(8));
		termIdField.setDocument(new FixedSizePlainDocument(8));

		/*
		 * termDao = (ITermInfoDao) ctx.getBean("termInfoDao"); termLimit = termDao.findTermLimt(); if (termLimit != null) { tacRejectField.setText(termLimit.getTacReject() == null ? "" : termLimit.getTacReject()); tacOnlineField.setText(termLimit.getTacOnline() == null ? "" : termLimit.getTacOnline()); tacAccessField.setText(termLimit.getTacAccess() == null ? "" : termLimit.getTacAccess()); lowLimitField.setText(termLimit.getLowLimitation() == null ? "" : termLimit.getLowLimitation()); termIdField.setText(termLimit.getTermCode() == null ? "" : termLimit.getTermCode()); }
		 */

		final JLabel TermCodelabel = new JLabel();
		TermCodelabel.setBounds(0, 50, 97, 20);
		TermCodelabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermCodelabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermCodelabel.setText(pm.getString("mv.termlimit.termact"));
		add(TermCodelabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		/*
		 * final JSplitPane splitPane = new JSplitPane(); splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT); splitPane.setDividerLocation(160); add(splitPane);
		 * 
		 * final JPanel terminalActPanel = new JPanel(); terminalActPanel.setLayout(null); terminalActPanel.setBorder(JTBorderFactory .createTitleBorder("终端行为代码")); splitPane.setLeftComponent(terminalActPanel);
		 */

		final JLabel tacRejectLabel = new JLabel();
		tacRejectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacRejectLabel.setBounds(10, 95, 130, 20);
		tacRejectLabel.setText(pm.getString("mv.termlimit.tac_refuse"));
		add(tacRejectLabel);

		tacRejectField.setBounds(145, 95, 160, 20);
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

		final JLabel tacOnlineLabel = new JLabel();
		tacOnlineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacOnlineLabel.setText(pm.getString("mv.termlimit.tac_online"));
		tacOnlineLabel.setBounds(10, 145, 130, 20);
		add(tacOnlineLabel);

		tacOnlineField.setBounds(145, 145, 160, 20);
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

		final JLabel tacAccessLabel = new JLabel();
		tacAccessLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tacAccessLabel.setText(pm.getString("mv.termlimit.tac_access"));
		tacAccessLabel.setBounds(10, 195, 130, 20);
		add(tacAccessLabel);

		tacAccessField.setBounds(145, 195, 160, 20);

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
		add(tacAccessField);

		/*
		 * final JPanel panel_1 = new JPanel(); panel_1.setLayout(new BorderLayout()); splitPane.setRightComponent(panel_1);
		 * 
		 * final JSplitPane splitPane_1 = new JSplitPane(); splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT); splitPane_1.setDividerLocation(100); panel_1.add(splitPane_1, BorderLayout.CENTER);
		 * 
		 * final JPanel terminalLimitPanel = new JPanel(); terminalLimitPanel.setLayout(null); terminalLimitPanel.setBorder(JTBorderFactory .createTitleBorder("终端限制")); splitPane_1.setLeftComponent(terminalLimitPanel);
		 */

		final JLabel TermLimitLabel = new JLabel();
		TermLimitLabel.setBounds(0, 250, 97, 20);
		TermLimitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermLimitLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermLimitLabel.setText(pm.getString("mv.termlimit.termlimit"));
		add(TermLimitLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 260, 730, 20);
		add(separator_1);

		final JLabel lowLimitLabel = new JLabel();
		lowLimitLabel.setBounds(0, 295, 140, 20);
		lowLimitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lowLimitLabel.setText(pm.getString("mv.termlimit.lowlimit"));
		add(lowLimitLabel);
		String PROMPTlIMIT = pm.getString("mv.termlimit.promotelimit");
		lowLimitField.setBounds(145, 295, 160, 20);
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
		add(lowLimitField);

		/*
		 * final JPanel panel_3 = new JPanel(); panel_3.setLayout(new BorderLayout()); splitPane_1.setRightComponent(panel_3);
		 * 
		 * final JSplitPane splitPane_2 = new JSplitPane(); splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT); splitPane_2.setDividerLocation(100); splitPane_2.setDividerSize(0); panel_3.add(splitPane_2);
		 * 
		 * final JPanel terminalIdPanel = new JPanel(); terminalIdPanel.setLayout(null); terminalIdPanel.setBorder(JTBorderFactory.createTitleBorder("终端ID")); splitPane_2.setLeftComponent(terminalIdPanel);
		 */

		final JLabel TermIDLabel = new JLabel();
		TermIDLabel.setBounds(0, 350, 97, 20);
		TermIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TermIDLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		TermIDLabel.setText(pm.getString("mv.termlimit.termid"));
		add(TermIDLabel);

		final JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(79, 360, 730, 20);
		add(separator_2);

		final JLabel termIdLabel = new JLabel();
		termIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		termIdLabel.setBounds(10, 395, 130, 20);
		termIdLabel.setText(pm.getString("mv.termlimit.idcontent"));
		add(termIdLabel);
		String PROMPTTERM = pm.getString("mv.termlimit.promoteid");
		termIdField.setBounds(145, 395, 160, 20);
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

		add(termIdField);

		final JButton saveButton = new JButton();
		saveButton.setText(pm.getString("mv.termlimit.save"));
		saveButton.setBounds(359, 395, 84, 21);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				/*if (tacRejectField.getText().isEmpty() || tacOnlineField.getText().isEmpty() || tacAccessField.getText().isEmpty() || lowLimitField.getText().isEmpty() || termIdField.getText().isEmpty()) {
					if (tacRejectField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.tac_refusenull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;
					} else if (tacOnlineField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.tac_onlinenull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;

					} else if (tacAccessField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.tac_accessnull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;

					} else if (lowLimitField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.lowlimitnull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;

					} else if (termIdField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.termidnull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.infonull"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						return;
					}

				} else {
					String data = tacRejectField.getText() + "|" + tacOnlineField.getText() + "|" + tacAccessField.getText() + "|" + lowLimitField.getText() + "|" + termIdField.getText();
					int ret = checkData(data);

					switch (ret) {
					case 0: {
						// 保存更新页面数据
						TermInfo term = new TermInfo();
						term.setTacReject(tacRejectField.getText());
						term.setTacOnline(tacOnlineField.getText());
						term.setTacAccess(tacAccessField.getText());
						term.setLowLimitation(lowLimitField.getText());
						term.setTermCode(termIdField.getText());
						if (termLimit == null) {
							if (termDao.insertTermLimit(term)) {
								termLimit = termDao.findTermLimt();
								JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.saveSuccess"), pm.getString("mv.termlimit.info"), JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.saveFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
								return;
							}
						} else {
							if (termDao.updateTermLimit(term)) {
								JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.saveSuccess"), pm.getString("mv.termlimit.info"), JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.saveFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					case 1: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.refuseFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 2: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.onlineFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 3: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.accessFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 4: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.limitFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 5: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.idFailed"), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					default: {
						JOptionPane.showMessageDialog(null, pm.getString("mv.termlimit.unknown "), pm.getString("mv.termlimit.info"), JOptionPane.ERROR_MESSAGE);
						break;
					}
					}

				}*/

			}
		});
		add(saveButton);

		/*
		 * final JPanel panel_5 = new JPanel(); splitPane_2.setRightComponent(panel_5);
		 */

	}

	private void init() {
		setName(pm.getString("mv.termlimit.termlimitconfig"));
		/*
		 * initModel(); initControls();
		 */
		/* initListeners(); */
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
