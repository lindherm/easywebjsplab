package com.gerenhua.tool.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.apdu.CommonAPDU;
import com.gerenhua.tool.logic.apdu.CommonHelper;
import com.gerenhua.tool.logic.impl.CardInfoThread;
import com.gerenhua.tool.logic.impl.DeleteObjThread;
import com.gerenhua.tool.logic.impl.LoadCapThead;
import com.gerenhua.tool.utils.Config;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;
import com.watchdata.kms.kmsi.IKms;

public class CardInfoDetectPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTree tree;
	private JTextField textField_4;
	private JTextField textField_5;
	public static CommonAPDU commonAPDU;
	public JTextPane textPane;
	public JTextPane textPane_1;
	public JComboBox comboBox;
	private static Log log = new Log();
	private static ConfigIpDialog dialog = null;

	private static JMenuItem mntmCardinfo;
	private static JMenuItem mntmLoad;
	private static JMenuItem deleteObj;

	public CardInfoDetectPanel() {
		log.setLogArea(textPane_1);
		setName("卡片信息");
		DefaultMutableTreeNode RootNode = new DefaultMutableTreeNode("CardInfo");
		DefaultTreeModel TreeModel = new DefaultTreeModel(RootNode);

		mntmCardinfo = new JMenuItem("CARD INFO");
		mntmCardinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				commonAPDU = new CommonAPDU();
				CardInfoThread thread = new CardInfoThread(tree, commonAPDU, comboBox.getSelectedItem().toString().trim(), textField_4.getText().trim(), textField_5.getText().trim(), textField.getText().trim(), textField_1.getText().trim(), textField_2.getText().trim(), textPane_1);
				thread.start();
			}
		});

		mntmLoad = new JMenuItem("LOAD CAP");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser("./resources/cap");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("cap package", "cap");
				jFileChooser.setFileFilter(fileNameExtensionFilter);
				jFileChooser.setMultiSelectionEnabled(true);

				int i = jFileChooser.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					File[] file = jFileChooser.getSelectedFiles();
					LoadCapThead loadCapThead = new LoadCapThead(file, commonAPDU, textPane_1);
					loadCapThead.start();
				}
			}
		});
		deleteObj = new JMenuItem("Remove");
		deleteObj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteObjThread deleteObjThread = new DeleteObjThread(tree, commonAPDU);
				deleteObjThread.start();
			}
		});
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "CARD_INFO", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		tree = new JTree();
		tree.setVisibleRowCount(0);
		tree.setShowsRootHandles(true);
		tree.setModel(TreeModel);
		scrollPane.setViewportView(tree);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tree, popupMenu);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "KEY_INFO", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_3.setLayout(null);

		comboBox = new JComboBox();
		comboBox.setBounds(74, 142, 54, 21);
		panel_3.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "00", "01", "03" }));

		JLabel lblId = new JLabel("id:");
		lblId.setBounds(138, 146, 39, 15);
		panel_3.add(lblId);
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_5 = new JTextField();
		textField_5.setBounds(182, 143, 66, 21);
		panel_3.add(textField_5);
		textField_5.setText("00");
		textField_5.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("version:");
		lblNewLabel_1.setBounds(248, 146, 64, 15);
		panel_3.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_4 = new JTextField();
		textField_4.setBounds(322, 144, 66, 21);
		panel_3.add(textField_4);
		textField_4.setText("00");
		textField_4.setColumns(10);

		JLabel lblNewLabel = new JLabel("Kenc:");
		lblNewLabel.setBounds(10, 57, 54, 15);
		panel_3.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		textField = new JTextField();
		textField.setBounds(74, 54, 314, 21);
		panel_3.add(textField);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Config.setValue("CardInfo", "Kenc", textField.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		textField.setText(Config.getValue("CardInfo", "Kenc"));

		JLabel lblKmac = new JLabel("Kmac:");
		lblKmac.setBounds(10, 88, 54, 15);
		panel_3.add(lblKmac);
		lblKmac.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_1 = new JTextField();
		textField_1.setBounds(74, 85, 314, 21);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(Config.getValue("CardInfo", "Kmac"));

		JLabel lblKdek = new JLabel("Kdek:");
		lblKdek.setBounds(10, 114, 54, 15);
		panel_3.add(lblKdek);
		lblKdek.setHorizontalAlignment(SwingConstants.RIGHT);

		textField_2 = new JTextField();
		textField_2.setBounds(74, 111, 314, 21);
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(Config.getValue("CardInfo", "Kdek"));

		textField_3 = new JTextField();
		textField_3.setBounds(73, 23, 315, 21);
		panel_3.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText(Config.getValue("CardInfo", "KMC"));

		JLabel lblKmc = new JLabel("KMC:");
		lblKmc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (dialog == null) {
					dialog = new ConfigIpDialog(null);
				}
				if (dialog.isVisible()) {
					dialog.toFront();
				} else {
					dialog.setVisible(true);
				}
			}
		});
		lblKmc.setBounds(16, 28, 48, 15);
		panel_3.add(lblKmc);
		lblKmc.setHorizontalAlignment(SwingConstants.RIGHT);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.5);
		splitPane.setLeftComponent(panel_3);
		splitPane.setRightComponent(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "GP\u6307\u4EE4", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_1.add(scrollPane_1);

		textPane = new JTextPane() {
			/**
							 * 
							 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		scrollPane_1.setViewportView(textPane);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "LOG", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2, BorderLayout.CENTER);

		textPane_1 = new JTextPane() {
			/**
													 * 
													 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		scrollPane_2.setViewportView(textPane_1);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);

		JButton btnNewButton_1 = new JButton("脚本");
		btnNewButton_1.setBounds(0, 24, 120, 23);
		panel_4.add(btnNewButton_1);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorderPainted(false);

		JButton button = new JButton("执行");
		button.setBounds(0, 60, 120, 23);
		panel_4.add(button);
		button.setFocusPainted(false);
		button.setBorderPainted(false);

		JButton btnPutkey = new JButton("PutKey");
		btnPutkey.setBounds(0, 92, 120, 23);
		panel_4.add(btnPutkey);
		btnPutkey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String keyVersion = textField_4.getText().trim();
				String keyId = textField_5.getText().trim();

				String keyInfo = textPane.getText().trim();
				String[] keys = keyInfo.split("\r\n");

				String newEnc = null;
				String newMac = null;
				String newDek = null;
				String newKeyVersion = null;

				if (keys.length == 4) {
					newEnc = keys[0];
					newMac = keys[1];
					newDek = keys[2];
					newKeyVersion = keys[3];
				} else if (keys.length == 2) {
					String newKey = keys[0];
					newKeyVersion = keys[1];

					String initResp = commonAPDU.getInitResp();
					initResp = initResp.substring(8, 20);

					String deriveData = initResp + "F001" + initResp + "0F01";
					newEnc = WD3DesCryptoUtil.ecb_encrypt(newKey, deriveData, Padding.NoPadding);

					deriveData = initResp + "F002" + initResp + "0F02";
					newMac = WD3DesCryptoUtil.ecb_encrypt(newKey, deriveData, Padding.NoPadding);

					deriveData = initResp + "F003" + initResp + "0F03";
					newDek = WD3DesCryptoUtil.ecb_encrypt(newKey, deriveData, Padding.NoPadding);
				} else {
					JOptionPane.showMessageDialog(null, "参数个数错误！");
					return;
				}
				try {
					commonAPDU.putKey(keyVersion, keyId, newKeyVersion, newEnc, newMac, newDek);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPutkey.setFocusPainted(false);
		btnPutkey.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (WDAssert.isEmpty(textPane.getText())) {
					JOptionPane.showMessageDialog(null, "请先加载脚本！", "信息框", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Runnable runnable = new Runnable() {
					public void run() {
						try {
							String prg = textPane.getText().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
							String[] apdus = prg.split("\n");

							for (String apdu : apdus) {
								if (!apdu.startsWith("//") && WDAssert.isNotEmpty(apdu.trim())) {
									apdu = apdu.trim();
									int commentPos = apdu.indexOf("//");
									int swPos = apdu.indexOf("SW");
									int pos = calPos(commentPos, swPos);
									if (pos != -1) {
										apdu = apdu.substring(0, pos).trim();
										commonAPDU.send(apdu);
									} else {
										// apdu=apdu.substring(0, commentPos);
										commonAPDU.send(apdu);
									}
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
							log.error(e.getMessage());
						}
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser("./resources/scripts");
				FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("prg files", "prg");
				jFileChooser.setFileFilter(fileNameExtensionFilter);

				int i = jFileChooser.showOpenDialog(null);
				if (i == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();

					FileInputStream fis;
					try {
						fis = new FileInputStream(file);
						byte[] fileContent = new byte[fis.available()];
						fis.read(fileContent);

						textPane.setText(new String(fileContent));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e.getMessage());
					}

				}
			}
		});
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setEnabled(false);
		splitPane_2.setResizeWeight(0.85);
		splitPane_2.setLeftComponent(panel_1);
		splitPane_2.setRightComponent(panel_4);
		// splitPane_2.setBounds(0, 0, 125, 27);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setEnabled(false);
		// add(splitPane_4, BorderLayout.CENTER);
		splitPane_4.setResizeWeight(0.6);
		splitPane_4.setTopComponent(splitPane_2);
		splitPane_4.setBottomComponent(panel_2);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setEnabled(false);
		splitPane_1.setResizeWeight(0.3);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setTopComponent(splitPane);
		splitPane_1.setBottomComponent(splitPane_4);

		add(splitPane_1, BorderLayout.CENTER);
		textField_3.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				String kmc = textField_3.getText().trim();
				commonAPDU = new CommonAPDU();
				HashMap<String, String> res = commonAPDU.reset();
				try {
					commonAPDU.send("00A4040000");
					String hostRandom = WDStringUtil.getRandomHexString(16);
					String keyVersion = textField_4.getText().trim();
					String keyId = textField_5.getText().trim();
					// initializeUpdate
					String strResp = commonAPDU.send("8050" + keyVersion + keyId + "08" + hostRandom);
					String initResp = strResp.substring(8, 20);

					String deriveDataKeyEnc = initResp + "F001" + initResp + "0F01";
					String deriveDataKeyMac = initResp + "F002" + initResp + "0F02";
					String deriveDataKeyDek = initResp + "F003" + initResp + "0F03";
					String keyEnc = null;
					String keyMac = null;
					String keyDek = null;
					if (kmc.length() == 16) {
						IKms iKms = IKms.getInstance();
						keyEnc = iKms.encrypt(kmc, IKms.DES_ECB, deriveDataKeyEnc, "pct");
						keyMac = iKms.encrypt(kmc, IKms.DES_ECB, deriveDataKeyMac, "pct");
						keyDek = iKms.encrypt(kmc, IKms.DES_ECB, deriveDataKeyDek, "pct");
					} else {
						keyEnc = WD3DesCryptoUtil.ecb_encrypt(kmc, deriveDataKeyEnc, Padding.NoPadding);
						keyMac = WD3DesCryptoUtil.ecb_encrypt(kmc, deriveDataKeyMac, Padding.NoPadding);
						keyDek = WD3DesCryptoUtil.ecb_encrypt(kmc, deriveDataKeyDek, Padding.NoPadding);
					}
					CommonHelper.updateUI(textField, keyEnc);
					CommonHelper.updateUI(textField_1, keyMac);
					CommonHelper.updateUI(textField_2, keyDek);

					Config.setValue("CardInfo", "KMC", kmc);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void removeUpdate(DocumentEvent e) {
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});
		textField_2.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Config.setValue("CardInfo", "Kdek", textField_2.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		textField_1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				Config.setValue("CardInfo", "Kmac", textField_1.getText().trim());
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Component com = e.getComponent();
					if (com instanceof JTree) {
						JTree tree = (JTree) com;
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

						String nodeName = (node != null) ? node.toString() : null;
						if (WDAssert.isNotEmpty(nodeName)) {
							if (node.isLeaf() && !nodeName.equalsIgnoreCase("CardInfo") && !nodeName.equalsIgnoreCase("Application Instances") && !nodeName.equalsIgnoreCase("Load Files and Modules") && !nodeName.equalsIgnoreCase("Load Files")) {
								addMenu(deleteObj, e);
							} else {
								if (nodeName.equalsIgnoreCase("CardInfo")) {
									addMenu(mntmCardinfo, e);
								} else if (nodeName.equalsIgnoreCase("Load Files")) {
									addMenu(mntmLoad, e);
								}

							}
						}
					}
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}

			private void addMenu(JMenuItem menuItem, MouseEvent e) {
				popup.removeAll();
				popup.add(menuItem);
				showMenu(e);
			}
		});

	}

	public int calPos(int pos1, int pos2) {
		int pos = -1;
		if (pos1 != -1 && pos2 != -1) {
			pos = Math.min(pos1, pos2);
		} else if (pos1 == -1 && pos2 != -1) {
			pos = pos2;
		} else if (pos1 != -1 && pos2 == -1) {
			pos = pos1;
		}
		return pos;
	}
}
