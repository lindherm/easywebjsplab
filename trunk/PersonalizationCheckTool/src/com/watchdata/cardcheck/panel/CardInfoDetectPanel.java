package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.watchdata.cardcheck.utils.Config;

public class CardInfoDetectPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public CardInfoDetectPanel() {
		setName("卡片信息");
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(330, 40, 460, 140);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTree tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("blue"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		scrollPane.setViewportView(tree);
		
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
		panel_1.setBorder(new TitledBorder(null, "\u811A\u672C", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		panel_1.setBounds(22, 190, 623, 252);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		
		JEditorPane editorPane = new JEditorPane();
		scrollPane_1.setViewportView(editorPane);
		
		JButton btnNewButton_1 = new JButton("打开脚本");
		btnNewButton_1.setBounds(655, 213, 93, 23);
		add(btnNewButton_1);
		
		JButton button = new JButton("执行");
		button.setBounds(655, 248, 93, 23);
		add(button);
	}
}
