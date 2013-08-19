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

import javax.swing.JButton;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.impl.CardInfoThread;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.commons.lang.WDAssert;

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
	private JTextField textField_6;
	public static CommonAPDU commonAPDU;
	public JTextPane textPane;
	public JTextPane textPane_1;

	public CardInfoDetectPanel() {
		setName("卡片信息");
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(330, 40, 493, 220);
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
				CardInfoThread thread=new CardInfoThread(tree, commonAPDU, textField_6.getText().trim(), textField_4.getText().trim(), textField_5.getText().trim(), textField.getText().trim(), textField_1.getText().trim(), textField_2.getText().trim(),textPane_1);
				thread.start();
			}
		});
		popupMenu.add(mntmCardinfo);

		JLabel lblNewLabel = new JLabel("Kenc:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 71, 54, 15);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(74, 68, 240, 21);
		add(textField);
		textField.setColumns(10);
		textField.setText(Config.getValue("CardInfo", "Kenc"));

		JLabel lblKmac = new JLabel("Kmac:");
		lblKmac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmac.setBounds(10, 102, 54, 15);
		add(lblKmac);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(74, 99, 240, 21);
		textField_1.setText(Config.getValue("CardInfo", "Kmac"));
		add(textField_1);

		JLabel lblKdek = new JLabel("Kdek:");
		lblKdek.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKdek.setBounds(10, 130, 54, 15);
		add(lblKdek);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(74, 127, 240, 21);
		textField_2.setText(Config.getValue("CardInfo", "Kdek"));
		add(textField_2);

		JLabel lblKmc = new JLabel("KMC:");
		lblKmc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKmc.setBounds(10, 43, 54, 15);
		add(lblKmc);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(74, 40, 240, 21);
		textField_3.setText(Config.getValue("CardInfo", "KMC"));
		add(textField_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "GP\u6307\u4EE4", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBounds(24, 270, 684, 252);
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
		btnNewButton_1.setBounds(718, 289, 93, 23);
		add(btnNewButton_1);

		JButton button = new JButton("执行");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(WDAssert.isEmpty(textPane.getText())){
					JOptionPane.showMessageDialog(null,"请先加载脚本！","信息框",JOptionPane.ERROR_MESSAGE);
					return;
				}
				Runnable runnable=new Runnable() {
					public void run() {
						String prg = textPane.getText().replaceAll("\r\n", "\n").replaceAll("\r", "\n");
						String[] apdus = prg.split("\n");

						for (String apdu : apdus) {
							commonAPDU.send(apdu);
						}
					}
				};
				Thread thread=new Thread(runnable);
				thread.start();
			}
		});
		button.setBounds(718, 321, 93, 23);
		add(button);

		JLabel lblNewLabel_1 = new JLabel("version:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(103, 165, 68, 15);
		add(lblNewLabel_1);

		textField_4 = new JTextField();
		textField_4.setText("00");
		textField_4.setBounds(181, 163, 47, 18);
		add(textField_4);
		textField_4.setColumns(10);

		JLabel lblId = new JLabel("id:");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(223, 165, 34, 15);
		add(lblId);

		textField_5 = new JTextField();
		textField_5.setText("00");
		textField_5.setColumns(10);
		textField_5.setBounds(267, 162, 47, 18);
		add(textField_5);

		textField_6 = new JTextField();
		textField_6.setText("01");
		textField_6.setBounds(37, 162, 56, 18);
		add(textField_6);
		textField_6.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "LOG", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_2.setBounds(24, 520, 799, 159);
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