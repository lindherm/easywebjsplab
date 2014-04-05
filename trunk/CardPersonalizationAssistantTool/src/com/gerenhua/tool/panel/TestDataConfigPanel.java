package com.gerenhua.tool.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.gerenhua.tool.configdao.AIDInfo;
import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.Constants;
import com.gerenhua.tool.logic.apdu.CommonAPDU;
import com.gerenhua.tool.logic.apdu.CommonHelper;
import com.gerenhua.tool.utils.Config;
import com.gerenhua.tool.utils.PropertiesManager;
import com.watchdata.commons.lang.WDStringUtil;
import javax.swing.JSplitPane;

/**
 * TestDataConfigPanel.java
 * 
 * @description: 静态数据配置界面
 * 
 * @author: pei.li 2012-3-28
 * 
 * @version:1.0.0
 * 
 * @modify：
 * 
 * @Copyright：watchdata
 */
public class TestDataConfigPanel extends JPanel {

	private static final long serialVersionUID = -4287626568370654541L;
	public static Log logger = new Log();
	public static JTree tree;
	private PropertiesManager pm = new PropertiesManager();
	public static JProgressBar progressBar;
	public CommonAPDU apduHandler;
	public static Log log = new Log();
	public static DefaultTreeModel model;
	public DefaultMutableTreeNode rootNode;

	public TableColumnModel tcm;
	public TableColumn tc;

	private JDialog dialog = new JDialog();
	private JEditorPane ep = new JEditorPane();
	private JScrollPane dlgscrollPane = new JScrollPane(ep);
	public JTextPane textPane_1;

	public TestDataConfigPanel() {
		super();
		log.setLogArea(textPane_1);
		setName(pm.getString("mv.testdata.name"));
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.dataConfig")));

		apduHandler = new CommonAPDU();
		setLayout(new BorderLayout(0, 0));

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

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2);

		scrollPane_2.setViewportView(textPane_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "SCAN EMV CARD", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		final JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);

		tree = new JTree(new DefaultMutableTreeNode("card data"));
		tree.setRootVisible(true);
		scrollPane.setViewportView(tree);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tree, popupMenu);

		JMenuItem menuItem = new JMenuItem("扫描");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						logger.setLogArea(textPane_1);
						logger.debug("scan data....", 0);
						// root node
						model = (DefaultTreeModel) tree.getModel();
						rootNode = (DefaultMutableTreeNode) model.getRoot();
						rootNode.removeAllChildren();

						//String reader = Config.getValue("Terminal_Data", "reader");
						//apduHandler.init(reader);
						// reset
						HashMap<String, String> res = apduHandler.reset();
						if (Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
							addNode(rootNode, "ATR  " + res.get("atr"));
						}
						scanAid(Constants.PSE);
						scanAid(Constants.PPSE);
						
						AIDInfo aidInfo=new AIDInfo();
						List<AIDInfo> aidInfoList=aidInfo.getAidInfos("SupAID");
						for (AIDInfo aidInfo2 : aidInfoList) {
							scanAid(aidInfo2.getAid());
						}
						//apduHandler.close();
					}
				});
				thread.start();
			}
		});
		popupMenu.add(menuItem);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(panel_1);
		splitPane.setBottomComponent(panel);
		add(splitPane, BorderLayout.CENTER);
		dialog.setSize(450, 350);
		dialog.getContentPane().add(dlgscrollPane);
	}

	public String parse8E(String str8E) {
		StringBuilder sb = new StringBuilder();
		String x = str8E.substring(0, 8);
		String y = str8E.substring(8, 16);
		String cvmCode = "";
		String cvmCondtionCode = "";

		x = x + "------金额X（二进制）";
		y = y + "------金额Y（二进制）";
		sb.append(x).append("\n").append(y).append("\n");
		sb.append("---------------------------------------\n");
		int i = 16;
		while (i < str8E.length()) {
			cvmCode = str8E.substring(i, i + 2);
			cvmCondtionCode = str8E.substring(i + 2, i + 4);

			String binary = Integer.toBinaryString(Integer.parseInt(cvmCode, 16));
			binary = WDStringUtil.paddingHeadZero(binary, 8);

			cvmCode = cvmCode + "------" + Config.getValue("CVM_CODE", binary.substring(0, 2)) + ";" + Config.getValue("CVM_TYPE", binary.substring(2, 8));
			cvmCondtionCode = cvmCondtionCode + "------" + Config.getValue("CVM_Condition_Code", cvmCondtionCode);
			i += 4;
			sb.append(cvmCode).append("\n").append(cvmCondtionCode).append("\n");
			sb.append("---------------------------------------\n");
		}

		System.out.println(sb.toString());
		return sb.toString();
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

	// add node and show it in treelist
	public void addNode(DefaultMutableTreeNode parentNode, String nodeName) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
		parentNode.add(newNode);
		TreeNode[] nodes = model.getPathToRoot(newNode);
		TreePath path = new TreePath(nodes);
		tree.scrollPathToVisible(path);
		tree.updateUI();
	}

	public void scanAid(String aid) {
		// ppse node
		HashMap<String, String> res = apduHandler.select(aid);
		if (Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
			DefaultMutableTreeNode newNode;
			if (aid.equalsIgnoreCase(Constants.PSE)) {
				newNode = new DefaultMutableTreeNode("PSE  " + Constants.PSE);
			}else if (aid.equalsIgnoreCase(Constants.PPSE)) {
				newNode = new DefaultMutableTreeNode("PPSE  " + Constants.PPSE);
			}else {
				newNode = new DefaultMutableTreeNode("AID  "+aid);
			}
			rootNode.add(newNode);
			TreeNode[] nodes = model.getPathToRoot(newNode);
			TreePath path = new TreePath(nodes);
			tree.scrollPathToVisible(path);
			tree.updateUI();

			for (int sfi = 1; sfi <= 31; sfi++) {
				for (int rec = 1; rec <= 16; rec++) {
					int sfi1 = (sfi << 3) | 4;
					HashMap<String, String> readRes = apduHandler.readRecordCommon(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2), WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2));
					if (Constants.SW_SUCCESS.equalsIgnoreCase(readRes.get("sw"))) {
						String dgiName = CommonHelper.getDgiHead(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2)) + WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2);

						addNode(newNode, dgiName + " " + readRes.get("res"));
					}
				}
			}
		}
	}
}
