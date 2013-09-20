package com.watchdata.cardcheck.panel;

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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.logic.impl.CardInfoThread;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.commons.crypto.WD3DesCryptoUtil;
import com.watchdata.commons.jce.JceBase.Padding;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

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
	private static Log log=new Log();

	public CardInfoDetectPanel() {
		log.setLogArea(textPane_1);
		setName("卡片信息");
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(320, 40, 493, 220);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		tree = new JTree();
		tree.setShowsRootHandles(true);
		DefaultMutableTreeNode RootNode = new DefaultMutableTreeNode("CardInfo");
		DefaultTreeModel TreeModel = new DefaultTreeModel(RootNode);
		tree.setModel(TreeModel);
		scrollPane.setViewportView(tree);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tree, popupMenu);

		JMenuItem mntmCardinfo = new JMenuItem("cardinfo");
		mntmCardinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				commonAPDU = new CommonAPDU();
				CardInfoThread thread=new CardInfoThread(tree, commonAPDU, comboBox.getSelectedItem().toString().trim(), textField_4.getText().trim(), textField_5.getText().trim(), textField.getText().trim(), textField_1.getText().trim(), textField_2.getText().trim(),textPane_1);
				thread.start();
			}
		});
		popupMenu.add(mntmCardinfo);

		JLabel lblNewLabel = new JLabel("Kenc:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, 99, 54, 15);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(64, 96, 240, 21);
		add(textField);
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
		lblKmac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmac.setBounds(0, 130, 54, 15);
		add(lblKmac);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(64, 127, 240, 21);
		textField_1.setText(Config.getValue("CardInfo", "Kmac"));
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
		add(textField_1);

		JLabel lblKdek = new JLabel("Kdek:");
		lblKdek.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKdek.setBounds(0, 158, 54, 15);
		add(lblKdek);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(64, 155, 240, 21);
		textField_2.setText(Config.getValue("CardInfo", "Kdek"));
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
		add(textField_2);

		JLabel lblKmc = new JLabel("KMC:");
		lblKmc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmc.setBounds(0, 43, 54, 15);
		add(lblKmc);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(64, 40, 240, 21);
		textField_3.setText(Config.getValue("CardInfo", "KMC"));
		textField_3.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				String kmc=textField_3.getText().trim();
				commonAPDU = new CommonAPDU();
				HashMap<String, String> res=commonAPDU.reset(Config.getValue("Terminal_Data", "reader"));
				
				try {
					commonAPDU.send("00A4040000");
					String hostRandom = WDStringUtil.getRandomHexString(16);
					String keyVersion=textField_4.getText().trim();
					String keyId=textField_5.getText().trim();
					// initializeUpdate
					String strResp = commonAPDU.apduChannel.send("8050" + keyVersion + keyId + "08" + hostRandom);
					String initResp=strResp.substring(8, 20);
					
					String deriveData=initResp+"F001"+initResp+"0F01";
					String keyEnc=WD3DesCryptoUtil.ecb_encrypt(kmc, deriveData, Padding.NoPadding);
					CommonHelper.updateUI(textField, keyEnc);
					
					deriveData=initResp+"F002"+initResp+"0F02";
					String keyMac=WD3DesCryptoUtil.ecb_encrypt(kmc, deriveData, Padding.NoPadding);
					CommonHelper.updateUI(textField_1, keyMac);
					
					deriveData=initResp+"F003"+initResp+"0F03";
					String keyDek=WD3DesCryptoUtil.ecb_encrypt(kmc, deriveData, Padding.NoPadding);
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
		add(textField_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "GP\u6307\u4EE4", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBounds(0, 270, 684, 252);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_1.add(scrollPane_1);

		textPane = new JTextPane(){
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

		JButton btnNewButton_1 = new JButton("打开脚本");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jFileChooser = new JFileChooser("./prg/");
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
		btnNewButton_1.setBounds(694, 291, 80, 23);
		add(btnNewButton_1);

		JButton button = new JButton("执行");
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(WDAssert.isEmpty(textPane.getText())){
					JOptionPane.showMessageDialog(null,"请先加载脚本！","信息框",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Runnable runnable=new Runnable() {
					public void run() {
						try {
							String prg = textPane.getText().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
							String[] apdus = prg.split("\n");

							for (String apdu : apdus) {
								commonAPDU.send(apdu);
							}
						} catch (Exception e) {
							// TODO: handle exception
							log.error(e.getMessage());
						}
					}
				};
				Thread thread=new Thread(runnable);
				thread.start();
			}
		});
		button.setBounds(694, 323, 80, 23);
		add(button);

		JLabel lblNewLabel_1 = new JLabel("version:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(129, 189, 60, 15);
		add(lblNewLabel_1);

		textField_4 = new JTextField();
		textField_4.setText("00");
		textField_4.setBounds(195, 187, 31, 18);
		add(textField_4);
		textField_4.setColumns(10);

		JLabel lblId = new JLabel("id:");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(232, 189, 31, 15);
		add(lblId);

		textField_5 = new JTextField();
		textField_5.setText("00");
		textField_5.setColumns(10);
		textField_5.setBounds(273, 187, 31, 18);
		add(textField_5);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "LOG", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_2.setBounds(0, 523, 799, 159);
		add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2, BorderLayout.CENTER);
		
		textPane_1 = new JTextPane(){
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
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "03"}));
		comboBox.setBounds(64, 186, 65, 21);
		add(comboBox);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}