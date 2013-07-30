package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import com.watchdata.cardcheck.dao.IIssuerKeyConfigDao;
import com.watchdata.cardcheck.dao.pojo.IssuerKeyInfo;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class IssuerKeyConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField;
	private IIssuerKeyConfigDao issuerkeyconfigdao;
	private IssuerKeyInfo issuerkeyinfo = new IssuerKeyInfo();
	public ApplicationContext ctx = new FileSystemXmlApplicationContext(
			"classpath:applicationContext.xml");
	private PropertiesManager pm = new PropertiesManager();
	public static JRadioButton pbocRadioButton;

	Color colorYellow = new Color(255, 255, 192);

	/**
	 * Create the panel
	 */
	public IssuerKeyConfigPanel() {
		super();
		/* setBackground(colorYellow); */
		setLayout(null);
		setBorder(JTBorderFactory.createTitleBorder(pm
				.getString("mv.issuerkeyconfig.issuerkey")));
		init();

		/*
		 * final JSplitPane splitPane = new JSplitPane();
		 * splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		 * splitPane.setDividerLocation(300); splitPane.setDividerSize(0);
		 * add(splitPane, BorderLayout.CENTER);
		 * 
		 * final JPanel panel = new JPanel(); panel.setLayout(new
		 * BorderLayout()); splitPane.setLeftComponent(panel);
		 */

		/*
		 * final JPanel panel_3 = new JPanel(); panel_3.setLayout(null);
		 * panel_3.setBackground(colorYellow); //
		 * panel_3.setBorder(JTBorderFactory.createTitleBorder("发卡行密钥"));
		 * panel.add(panel_3, BorderLayout.CENTER);
		 */

		final JLabel issuerKeyLabel = new JLabel();
		issuerKeyLabel.setBounds(0, 50, 97, 20);
		issuerKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		issuerKeyLabel.setFont(new Font("宋体", Font.BOLD, 12));
		issuerKeyLabel.setText(pm.getString("mv.issuerkeyconfig.keymgr"));
		add(issuerKeyLabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setText(pm.getString("mv.issuerkeyconfig.mackey"));
		label.setBounds(30, 140, 97, 20);
		add(label);

		textField = new JTextField();
		textField.setBounds(132, 140, 160, 20);
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
		add(textField);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mv.issuerkeyconfig.enckey"));
		label_1.setBounds(30, 185, 97, 20);
		add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(132, 185, 160, 20);
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
		add(textField_1);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.issuerkeyconfig.ackey"));
		label_2.setBounds(20, 230, 107, 20);
		add(label_2);

		textField_2 = new JTextField();
		textField_2.setBounds(132, 230, 160, 20);
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
		add(textField_2);

		issuerkeyconfigdao = (IIssuerKeyConfigDao) ctx
				.getBean("issuerKeyConfigDao");
		issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("PBOC");
		if (issuerkeyinfo != null) {
			textField.setText(issuerkeyinfo.getMacKey() == null ? ""
					: issuerkeyinfo.getMacKey());
			textField_1.setText(issuerkeyinfo.getEncKey() == null ? ""
					: issuerkeyinfo.getEncKey());
			textField_2.setText(issuerkeyinfo.getAcKey() == null ? ""
					: issuerkeyinfo.getAcKey());
		}
		pbocRadioButton = new JRadioButton();
		pbocRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		pbocRadioButton.setText("PBOC");
		/* pbocRadioButton.setBackground(colorYellow); */
		pbocRadioButton.setBounds(10, 95, 120, 20);
		pbocRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("PBOC");
				if (issuerkeyinfo != null) {
					textField.setText(issuerkeyinfo.getMacKey() == null ? ""
							: issuerkeyinfo.getMacKey());
					textField_1.setText(issuerkeyinfo.getEncKey() == null ? ""
							: issuerkeyinfo.getEncKey());
					textField_2.setText(issuerkeyinfo.getAcKey() == null ? ""
							: issuerkeyinfo.getAcKey());
				} else {
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
				}

			}
		});
		add(pbocRadioButton);

		final JRadioButton mastercardRadioButton = new JRadioButton();
		mastercardRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		mastercardRadioButton.setText("MasterCard");
		/* mastercardRadioButton.setBackground(colorYellow); */
		mastercardRadioButton.setBounds(140, 95, 120, 20);
		mastercardRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("MasterCard");
				if (issuerkeyinfo != null) {
					textField.setText(issuerkeyinfo.getMacKey() == null ? ""
							: issuerkeyinfo.getMacKey());
					textField_1.setText(issuerkeyinfo.getEncKey() == null ? ""
							: issuerkeyinfo.getEncKey());
					textField_2.setText(issuerkeyinfo.getAcKey() == null ? ""
							: issuerkeyinfo.getAcKey());
				} else {
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
				}
			}
		});
		add(mastercardRadioButton);

		final JRadioButton visaRadioButton = new JRadioButton();
		visaRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		visaRadioButton.setText("Visa");
		/* visaRadioButton.setBackground(colorYellow); */
		visaRadioButton.setBounds(270, 95, 120, 20);
		visaRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("Visa");
				if (issuerkeyinfo != null) {
					textField.setText(issuerkeyinfo.getMacKey() == null ? ""
							: issuerkeyinfo.getMacKey());
					textField_1.setText(issuerkeyinfo.getEncKey() == null ? ""
							: issuerkeyinfo.getEncKey());
					textField_2.setText(issuerkeyinfo.getAcKey() == null ? ""
							: issuerkeyinfo.getAcKey());
				} else {
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
				}
			}
		});
		add(visaRadioButton);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(pbocRadioButton);
		buttonGroup.add(mastercardRadioButton);
		buttonGroup.add(visaRadioButton);
		pbocRadioButton.setSelected(true);

		final JButton button = new JButton();
		button.setDefaultCapable(true);
		button.setText(pm.getString("mv.issuerkeyconfig.save"));
		button.setBounds(28, 275, 84, 21);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				String err = "";
				if (0 == textField.getText().trim().length())
					err += pm.getString("mv.issuerkeyconfig.fullmackey");
				if (0 == textField_1.getText().trim().length())
					err += pm.getString("mv.issuerkeyconfig.fullenckey");
				if (0 == textField_2.getText().trim().length())
					err += pm.getString("mv.issuerkeyconfig.fullackey");

				if (!err.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, err, pm
							.getString("mv.issuerkeyconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
					return;

				} else {
					String data = textField.getText() + "|"
							+ textField_1.getText() + "|"
							+ textField_2.getText();
					int ret = checkData(data);

					switch (ret) {
					case 0: {
						// 保存/更新页面数据
						IssuerKeyInfo issuer = new IssuerKeyInfo();
						issuer.setMacKey(textField.getText());
						issuer.setEncKey(textField_1.getText());
						issuer.setAcKey(textField_2.getText());
						if (pbocRadioButton.isSelected()) {
							issuer.setIssuerType("PBOC");

						} else if (mastercardRadioButton.isSelected()) {
							issuer.setIssuerType("MasterCard");

						} else if (visaRadioButton.isSelected()) {
							issuer.setIssuerType("Visa");

						}

						if (issuerkeyinfo == null) {
							if (issuerkeyconfigdao.insertIssuerkey(issuer)) {
								issuerkeyinfo = issuerkeyconfigdao
										.findIssuerKey(issuer.getIssuerType());
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.savesuccess"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.saveerr"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.ERROR_MESSAGE);
								return;
							}
						} else {
							if (issuerkeyconfigdao.updateIssuerKey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.updatesuccess"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.updateerr"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					case 1: {
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.issuerkeyconfig.mackeyerr"), pm
								.getString("mv.issuerkeyconfig.infoWindow"),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 2: {
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.issuerkeyconfig.enckeyerr"), pm
								.getString("mv.issuerkeyconfig.infoWindow"),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
					case 3: {
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.issuerkeyconfig.ackeyerr"), pm
								.getString("mv.issuerkeyconfig.infoWindow"),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
					default: {
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.issuerkeyconfig.unknowerr"), pm
								.getString("mv.issuerkeyconfig.infoWindow"),
								JOptionPane.ERROR_MESSAGE);
						break;
					}
					}

				}

			}
		});
		add(button);

		final JButton button_1 = new JButton();
		button_1.setText(pm.getString("mv.issuerkeyconfig.import"));
		button_1.setBounds(158, 275, 84, 21);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				int ret = JOptionPane.showConfirmDialog(null, pm
						.getString("mv.issuerkeyconfig.deleteBeforeImport"), pm
						.getString("mv.issuerkeyconfig.infoWindow"),
						JOptionPane.OK_CANCEL_OPTION);
				if (ret == JOptionPane.CANCEL_OPTION) {
					return;
				}

				JFileChooser chooser = new JFileChooser(".");
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(pm
						.getString("mv.issuerkeyconfig.txtfile"), "txt");
				chooser.addChoosableFileFilter(filter);
				chooser.setFileFilter(filter);
				ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File f = chooser.getSelectedFile();
				BufferedReader reader = null;
				Vector<String> veckey = new Vector<String>();
				try {
					reader = new BufferedReader(new FileReader(f
							.getAbsolutePath()));
					String tmpstr = null;
					while ((tmpstr = reader.readLine()) != null) {
						veckey.add(tmpstr);
					}
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e2) {
						}
					}
				}

				if (!issuerkeyconfigdao.deleteIssuerKey()) {
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.issuerkeyconfig.deleteerr"), pm
							.getString("mv.issuerkeyconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
					return;

				}

				IssuerKeyInfo issuer = new IssuerKeyInfo();
				String MacKey = null;
				String EncKey = null;
				String AcKey = null;
				boolean pbocsuccess = false;
				boolean mcsuccess = false;
				boolean visasuccess = false;

				for (Iterator<String> cit = veckey.iterator(); cit.hasNext();) {
					String key = cit.next();
					if (key.equalsIgnoreCase("PBOC")) {
						issuer.setIssuerType("PBOC");
						issuerkeyinfo = issuerkeyconfigdao.findIssuerKey(issuer
								.getIssuerType());
						MacKey = (cit.hasNext() ? cit.next() : "");
						EncKey = (cit.hasNext() ? cit.next() : "");
						AcKey = (cit.hasNext() ? cit.next() : "");
						String data = MacKey + "|" + EncKey + "|" + AcKey;
						if (checkData(data) != 0) {
							JOptionPane
									.showMessageDialog(
											null,
											pm
													.getString("mv.issuerkeyconfig.pbocerr1"),
											pm
													.getString("mv.issuerkeyconfig.infoWindow"),
											JOptionPane.INFORMATION_MESSAGE);
							continue;

						}
						issuer.setMacKey(MacKey);
						issuer.setEncKey(EncKey);
						issuer.setAcKey(AcKey);
						if (issuerkeyinfo == null) {
							if (!issuerkeyconfigdao.insertIssuerkey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.pbocerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;

							}
						} else {
							if (!issuerkeyconfigdao.updateIssuerKey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.pbocerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;

							}
						}
						if (pbocRadioButton.isSelected()) {
							textField.setText(issuer.getMacKey());
							textField_1.setText(issuer.getEncKey());
							textField_2.setText(issuer.getAcKey());
						}
						pbocsuccess = true;
					}
					if (key.equalsIgnoreCase("MasterCard")) {
						issuer.setIssuerType("MasterCard");
						issuerkeyinfo = issuerkeyconfigdao.findIssuerKey(issuer
								.getIssuerType());
						MacKey = (cit.hasNext() ? cit.next() : "");
						EncKey = (cit.hasNext() ? cit.next() : "");
						AcKey = (cit.hasNext() ? cit.next() : "");
						String data = MacKey + "|" + EncKey + "|" + AcKey;
						if (checkData(data) != 0) {
							JOptionPane
									.showMessageDialog(
											null,
											pm
													.getString("mv.issuerkeyconfig.mcerr1"),
											pm
													.getString("mv.issuerkeyconfig.infoWindow"),
											JOptionPane.INFORMATION_MESSAGE);
							continue;

						}
						issuer.setMacKey(MacKey);
						issuer.setEncKey(EncKey);
						issuer.setAcKey(AcKey);
						if (issuerkeyinfo == null) {
							if (!issuerkeyconfigdao.insertIssuerkey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.mcerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;

							}
						} else {
							if (!issuerkeyconfigdao.updateIssuerKey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.mcerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;
							}
						}
						if (mastercardRadioButton.isSelected()) {
							textField.setText(issuer.getMacKey());
							textField_1.setText(issuer.getEncKey());
							textField_2.setText(issuer.getAcKey());
						}
						mcsuccess = true;
					}
					if (key.equalsIgnoreCase("Visa")) {
						issuer.setIssuerType("Visa");
						issuerkeyinfo = issuerkeyconfigdao.findIssuerKey(issuer
								.getIssuerType());
						MacKey = (cit.hasNext() ? cit.next() : "");
						EncKey = (cit.hasNext() ? cit.next() : "");
						AcKey = (cit.hasNext() ? cit.next() : "");
						String data = MacKey + "|" + EncKey + "|" + AcKey;
						if (checkData(data) != 0) {
							JOptionPane
									.showMessageDialog(
											null,
											pm
													.getString("mv.issuerkeyconfig.visaerr1"),
											pm
													.getString("mv.issuerkeyconfig.infoWindow"),
											JOptionPane.INFORMATION_MESSAGE);
							continue;

						}
						issuer.setMacKey(MacKey);
						issuer.setEncKey(EncKey);
						issuer.setAcKey(AcKey);
						if (issuerkeyinfo == null) {
							if (!issuerkeyconfigdao.insertIssuerkey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.visaerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;
							}
						} else {
							if (!issuerkeyconfigdao.updateIssuerKey(issuer)) {
								JOptionPane
										.showMessageDialog(
												null,
												pm
														.getString("mv.issuerkeyconfig.visaerr2"),
												pm
														.getString("mv.issuerkeyconfig.infoWindow"),
												JOptionPane.INFORMATION_MESSAGE);
								continue;
							}
						}
						if (visaRadioButton.isSelected()) {
							textField.setText(issuer.getMacKey());
							textField_1.setText(issuer.getEncKey());
							textField_2.setText(issuer.getAcKey());
						}
						visasuccess = true;
					}
				}
				String msg = "";
				if (pbocsuccess)
					msg += pm.getString("mv.issuerkeyconfig.pbocsuc");
				if (mcsuccess)
					msg += pm.getString("mv.issuerkeyconfig.mcsuc");
				if (visasuccess)
					msg += pm.getString("mv.issuerkeyconfig.visasuc");
				if (msg.equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.issuerkeyconfig.importfinish")
							+ pm.getString("mv.issuerkeyconfig.nokeyimport"),
							pm.getString("mv.issuerkeyconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.issuerkeyconfig.importfinish")
							+ msg, pm
							.getString("mv.issuerkeyconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);

			}

		});
		add(button_1);

		final JButton button_2 = new JButton();
		button_2.setText(pm.getString("mv.issuerkeyconfig.export"));
		button_2.setBounds(288, 275, 84, 21);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				MyChooser chooser = new MyChooser(".");
				chooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(pm
						.getString("mv.issuerkeyconfig.txtfile"), "txt");
				chooser.addChoosableFileFilter(filter);
				chooser.setFileFilter(filter);
				int ret = chooser.showSaveDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File f = chooser.getSelectedFile();
				String filePathStr = f.getAbsolutePath();
				if (!filePathStr.endsWith("txt")) {
					filePathStr = filePathStr + ".txt";
				}
				try {
					FileWriter fw = new FileWriter(filePathStr, false);
					BufferedWriter bw = new BufferedWriter(fw);
					issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("PBOC");
					if (issuerkeyinfo != null) {
						bw.write("PBOC");
						bw.newLine();
						bw.write(issuerkeyinfo.getMacKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getEncKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getAcKey());
						bw.newLine();
					}
					issuerkeyinfo = issuerkeyconfigdao
							.findIssuerKey("MasterCard");
					if (issuerkeyinfo != null) {
						bw.write("MasterCard");
						bw.newLine();
						bw.write(issuerkeyinfo.getMacKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getEncKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getAcKey());
						bw.newLine();
					}
					issuerkeyinfo = issuerkeyconfigdao.findIssuerKey("Visa");
					if (issuerkeyinfo != null) {
						bw.write("Visa");
						bw.newLine();
						bw.write(issuerkeyinfo.getMacKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getEncKey());
						bw.newLine();
						bw.write(issuerkeyinfo.getAcKey());
						bw.newLine();
					}
					bw.flush();
					bw.close();
					fw.close();
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.issuerkeyconfig.exportsuccess"), pm
							.getString("mv.issuerkeyconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});

		add(button_2);

		/*
		 * final JPanel panel_1 = new JPanel();
		 * splitPane.setRightComponent(panel_1);
		 */

		//
	}

	private void init() {
		setName(pm.getString("mv.issuerkeyconfig.name"));
		/*
		 * initModel(); initControls();
		 */
		/* initListeners(); */
	}

	private static class MyChooser extends JFileChooser {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private PropertiesManager pm = new PropertiesManager();

		MyChooser(String path) {
			super(path);
		}

		public void approveSelection() {
			File file = this.getSelectedFile();
			if (file.exists()) {
				int copy = JOptionPane
						.showConfirmDialog(null, pm
								.getString("mv.issuerkeyconfig.override"), pm
								.getString("mv.issuerkeyconfig.save"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (copy == JOptionPane.YES_OPTION)
					super.approveSelection();
			} else
				super.approveSelection();
		}
	}

	public static int checkData(String data) {
		Pattern pattern = Pattern.compile("[0-9a-fA-F]*");
		String result[] = data.split("[|]");
		if (result.length != 3)
			return -1;
		for (int i = 0; i < result.length; i++) {
			if ((result[i].length() != 32)
					|| !pattern.matcher(result[i]).matches()) {
				return i + 1;
			}
		}
		return 0;

	}

}
