package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.watchdata.cardcheck.panel.CheckTabbedPanel;
import com.watchdata.cardcheck.panel.ConfigTabbedPanel;
import com.watchdata.cardcheck.panel.TradeTabbedPanel;

/**
 * @title LeftPanel.java
 * @description
 * @author pei.li 2012-3-16
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class LeftPanel extends JPanel {

	private static final long serialVersionUID = 3433561501023042680L;
	private JTabbedPane tabbedPane;
	private ConfigTabbedPanel configTabbedPanel = new ConfigTabbedPanel();
	private TradeTabbedPanel tradeTabbedPanel = new TradeTabbedPanel();
	private CheckTabbedPanel checkTabbedPanel = new CheckTabbedPanel();

	/* private JScrollPane treePanel = null; */

	public LeftPanel() {
		super(new BorderLayout());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		/*panel.setLayout(null);*/
		add(panel);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add(configTabbedPanel.getName(), configTabbedPanel);
		panel.add(tabbedPane);
		tabbedPane.add(tradeTabbedPanel.getName(), tradeTabbedPanel);
		tabbedPane.add(checkTabbedPanel.getName(), checkTabbedPanel);
		tabbedPane.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if("配置".equals(tabbedPane.getSelectedComponent().getName())){
					Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
					RightPanel.aidConfigPanel.setVisible(true);
					RightPanel.logoPanel.setVisible(false);	
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.testDataConfigPanel.setVisible(false);
				}else if("交易".equals(tabbedPane.getSelectedComponent().getName())){
					Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
					RightPanel.tradePanel.setVisible(true);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);	
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
				}else if("检测".equals(tabbedPane.getSelectedComponent().getName())){
					Application.rightPanel.add(RightPanel.testDataConfigPanel,BorderLayout.CENTER);
					RightPanel.testDataConfigPanel.setVisible(true);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);	
					RightPanel.tradePanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
				}	
			}
		});
		

		/*final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane
						.remove(RightPanel.staticDataCalibrationResultsPanel);
				RightPanel.tabbedPane.add(RightPanel.aidConfigPanel.getName(),
						RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.caPublicKeyConfigPanel
						.getName(), RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.issuerKeyConfigPanel
						.getName(), RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.terminalLimitConfigPanel
						.getName(), RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane.add(
						RightPanel.terminalPerformanceConfigPanel.getName(),
						RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.terminalTypeConfigPanel
						.getName(), RightPanel.terminalTypeConfigPanel);
				 RightPanel.aidConfigPanel.setVisible(false); 
			}
		});
		button.setText(pm.getString("mv.left.config"));
		button.setBounds(0, 20, 180, 21);
		panel.add(button);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				RightPanel.tabbedPane.remove(RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.staticDataCalibrationResultsPanel);
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.tradePanel.getName(),
						RightPanel.tradePanel);
			}
		});
		button_1.setText(pm.getString("mv.left.trade"));
		button_1.setBounds(0, 41, 180, 21);
		panel.add(button_1);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				RightPanel.tabbedPane.remove(RightPanel.tradePanel);
				RightPanel.tabbedPane.remove(RightPanel.aidConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.caPublicKeyConfigPanel);
				RightPanel.tabbedPane.remove(RightPanel.issuerKeyConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalLimitConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalPerformanceConfigPanel);
				RightPanel.tabbedPane
						.remove(RightPanel.terminalTypeConfigPanel);
				RightPanel.tabbedPane.add(RightPanel.testDataConfigPanel
						.getName(), RightPanel.testDataConfigPanel);
				RightPanel.tabbedPane.add(
						RightPanel.staticDataCalibrationResultsPanel.getName(),
						RightPanel.staticDataCalibrationResultsPanel);
			}
		});
		button_2.setText(pm.getString("mv.left.check"));
		button_2.setBounds(0, 62, 180, 21);
		panel.add(button_2);*/
		
	}

	/*
	 * private void init() { initControls(); }
	 */

	/*
	 * private void initControls() { treePanel = createTree();
	 * treePanel.setMinimumSize(new Dimension(80, 60)); add(treePanel); }
	 */

	/*
	 * private JScrollPane createTree() { JTree tree = new JTree() { public
	 * Insets getInsets() { return new Insets(5,5,5,5); } }; String[] strs = {""
	 * ,// 0 "配置", // 1 "AID配置", "CA公钥配置", // 2 "发卡行密钥配置", // 3 "终端类型配置",
	 * "终端限制配置", "终端性能配置", "交易" , "PBOC交易", "qPBOC交易", "电子现金交易", "圈存", "消费",
	 * "静态数据监测", "检测数据配置" , "检测结果" }; // 4
	 * 
	 * DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[strs.length];
	 * for (int i = 0; i < strs.length; i++) { nodes[i] = new
	 * DefaultMutableTreeNode(strs[i]); } nodes[0].add(nodes[1]);
	 * nodes[1].add(nodes[2]); nodes[1].add(nodes[3]); nodes[1].add(nodes[4]);
	 * nodes[1].add(nodes[5]); nodes[1].add(nodes[6]); nodes[1].add(nodes[7]);
	 * nodes[0].add(nodes[8]); nodes[8].add(nodes[9]); nodes[8].add(nodes[10]);
	 * nodes[8].add(nodes[11]); nodes[11].add(nodes[12]);
	 * nodes[11].add(nodes[13]); nodes[0].add(nodes[14]);
	 * nodes[14].add(nodes[15]); nodes[14].add(nodes[16]);
	 * 
	 * final JTree tree = new JTree(nodes[0]); tree.setEditable(false);
	 * tree.addTreeSelectionListener(new TreeSelectionListener(){
	 * 
	 * @Override public void valueChanged(TreeSelectionEvent e) { // TODO
	 * Auto-generated method stub String selectedTreeNote =
	 * ((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).toString();
	 * RightPanel.tabbedPane.setSelectedIndex(RightPanel.tabbedPane.indexOfTab(
	 * selectedTreeNote)); } }); JScrollPane scrollPane = new JScrollPane(tree);
	 * scrollPane
	 * .setBorder(JTBorderFactory.createTitleBorder(ImageHelper.loadImage
	 * ("tree.gif"), "", 0, 0)); return scrollPane; }
	 */

}