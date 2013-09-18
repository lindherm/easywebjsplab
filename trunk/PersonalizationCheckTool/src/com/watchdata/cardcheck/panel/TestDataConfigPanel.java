package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;

import com.watchdata.cardcheck.configdao.StaticDataInfo;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

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
	public static JXTreeTable table;
	private JLabel tagLabel;
	private JButton addButton;
	public static JButton delButton;
	public JComboBox comboBox;
	private PropertiesManager pm = new PropertiesManager();
	private List<StaticDataInfo> sdList = new ArrayList<StaticDataInfo>();
	public static JProgressBar progressBar;
	public CommonAPDU apduHandler;
	public static Log log = new Log();
	public StaticDataInfo staticDataInfo = new StaticDataInfo();

	public TableColumnModel tcm;
	public TableColumn tc;

	private JDialog dialog = new JDialog();
	private JEditorPane ep = new JEditorPane();
	private JScrollPane dlgscrollPane = new JScrollPane(ep);
	public JRadioButton pse ;
	public JRadioButton ppse ;
	public JTextPane textPane_1;
	public TestDataConfigPanel() {
		super();
		log.setLogArea(textPane_1);
		setLayout(null);
		setName(pm.getString("mv.testdata.name"));
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.dataConfig")));

		apduHandler = new CommonAPDU();

		tagLabel = new JLabel();
		tagLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tagLabel.setBounds(237, 44, 40, 20);
		tagLabel.setText(pm.getString("mv.testdata.tag"));
		add(tagLabel);

		addButton = new JButton();
		addButton.setText("增加");
		addButton.setBounds(730, 105, 84, 21);
		addButton.setFocusPainted(false);
		addButton.setBorderPainted(false);
		add(addButton);

		delButton = new JButton();
		delButton.setText(pm.getString("mv.testdata.delete"));
		delButton.setBounds(730, 136, 84, 21);
		delButton.setFocusPainted(false);
		delButton.setBorderPainted(false);
		add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 87, 720, 376);

		table = new JXTreeTable();
		table.setName("componentTreeTable");
		sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
		refreshModel(sdList);
		scrollPane.setViewportView(table);
		setTableWidth(table);
		add(scrollPane);

		JLabel lblAid = new JLabel();
		lblAid.setText("AID：");
		lblAid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAid.setBounds(10, 44, 40, 20);
		add(lblAid);

		comboBox = new JComboBox();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.setLogArea(null);
				comboBox.removeAllItems();
				String reader = Config.getValue("Terminal_Data", "reader");
				HashMap<String, String> res = apduHandler.reset(reader);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
					JOptionPane.showMessageDialog(null, res.get("sw"));
				}

				HashMap<String, String> result = apduHandler.select(Constants.PSE);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
					JOptionPane.showMessageDialog(null, res.get("sw"));
				}

				if (WDAssert.isNotEmpty(result.get("88"))) {
					List<HashMap<String, String>> readDirList = apduHandler.readDir(result.get("88"));
					comboBox.removeAllItems();
					for (HashMap<String, String> readdir : readDirList) {
						String aid = readdir.get("4F");
						if (!WDAssert.isEmpty(aid)) {
							comboBox.addItem(aid);
						}
					}
				}
				apduHandler.close();
			}
		});
		comboBox.setBounds(47, 44, 180, 20);
		add(comboBox);

		JButton button = new JButton();
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						logger.setLogArea(null);
						TreeTableModel treeTableModel=table.getTreeTableModel();
						JXTreeNode root=(JXTreeNode)treeTableModel.getRoot();
						
						if (comboBox.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "请选择aid");
							return;
						}

						String aid = comboBox.getSelectedItem().toString().trim();
						String reader = Config.getValue("Terminal_Data", "reader");
						HashMap<String, String> res = apduHandler.reset(reader);
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("apdu")+"="+res.get("sw"),"提示框",JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (pse.isSelected()) {
							res = apduHandler.select(Constants.PSE);
						}else if (ppse.isSelected()) {
							res = apduHandler.select(Constants.PPSE);
						}
						
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("apdu")+"="+res.get("sw"),"提示框",JOptionPane.ERROR_MESSAGE);
							return;
						}

						for (int i = 0; i < table.getRowCount(); i++) {
							String tag = table.getValueAt(i, 0).toString();
							if (res.containsKey(tag)) {
								JXTreeNode jxTreeNode = (JXTreeNode)table.getTreeTableModel().getChild(root, i);
								jxTreeNode.getChildren().add(new JXTreeNode("", "PSE", res.get(tag),""));
							}
						}
						
						HashMap<String, String> aidResult = apduHandler.select(aid);
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(aidResult.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("apdu")+"="+res.get("sw"),"提示框",JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						for (int i = 0; i < table.getRowCount(); i++) {
							String tag = table.getValueAt(i, 0).toString();
							if (res.containsKey(tag)) {
								JXTreeNode jxTreeNode = (JXTreeNode)table.getTreeTableModel().getChild(root, i);
								jxTreeNode.getChildren().add(new JXTreeNode("", "SELECT AID", res.get(tag),""));
							}
						}
						
						for (int sfi = 1; sfi <= 31; sfi++) {
							for (int rec = 1; rec <= 16; rec++) {
								int sfi1 = (sfi << 3) | 4;
								HashMap<String, String> readRes = apduHandler.readRecordCommon(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2), WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2));
								if (Constants.SW_SUCCESS.equalsIgnoreCase(readRes.get("sw"))) {
									for (int i = 0; i < table.getRowCount(); i++) {
										String tag = table.getValueAt(i, 0).toString();
										if (readRes.containsKey(tag)) {
											JXTreeNode jxTreeNode = (JXTreeNode)table.getTreeTableModel().getChild(root, i);
											jxTreeNode.getChildren().add(new JXTreeNode("", CommonHelper.getDgiHead(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2)) + WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2), readRes.get(tag),""));
										}
									}
								}
							}
						}
						expandAll();
						apduHandler.close();
					}
				});
				thread.start();
			}
		});
		button.setText("检测");
		button.setBounds(730, 198, 84, 21);
		add(button);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(285, 44, 69, 20);
		add(comboBox_2);

		JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
				refreshModel(sdList);
				setTableWidth(table);
				table.repaint();
			}
		});
		button_1.setText("刷新");
		button_1.setBounds(730, 167, 84, 21);
		button_1.setFocusPainted(false);
		button_1.setBorderPainted(false);
		add(button_1);
		
		ButtonGroup buttonGroup=new ButtonGroup();
		
		pse = new JRadioButton("PSE");
		pse.setSelected(true);
		pse.setBounds(383, 43, 59, 23);
		add(pse);
		
		ppse = new JRadioButton("PPSE");
		ppse.setBounds(444, 43, 59, 23);
		add(ppse);

		buttonGroup.add(pse);
		buttonGroup.add(ppse);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 473, 720, 238);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2);
		
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
		
		Collection<String> tagCollections = Config.getItems("TAG");
		for (String tag : tagCollections) {
			comboBox_2.addItem(tag);
		}

		addButton.addActionListener(addActionListener);
		delButton.addActionListener(delActionListener);

		dialog.setSize(450, 350);
		dialog.getContentPane().add(dlgscrollPane);
	}

	// 添加按钮监听事件
	private ActionListener addActionListener = new ActionListener() {
		public void actionPerformed(final ActionEvent arg0) {
			StaticDataInfo staticDataInfo = new StaticDataInfo();
			staticDataInfo.setTag(comboBox_2.getSelectedItem().toString());

			if (Config.getItem("StaticDataTemplate", staticDataInfo.getTag()) == null) {
				staticDataInfo.add("StaticDataTemplate", staticDataInfo);

				JOptionPane.showMessageDialog(null, "添加数据成功！", "提示框", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "数据项已经存在！", "提示框", JOptionPane.ERROR_MESSAGE);
				return;
			}

			sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
			refreshModel(sdList);
			setTableWidth(table);
			table.repaint();
		}
	};

	// 删除按钮监听事件
	private ActionListener delActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			int selectedNum = table.getSelectedRows().length;

			int[] selectIndex = table.getSelectedRows();
			if (selectedNum == 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else if (selectedNum >= 1) {
				int y = JOptionPane.showConfirmDialog(null, pm.getString("mv.testdata.deleteInfoconfirm"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					return;
				}
				List<String> delDatas = new ArrayList<String>();
				for (int i = 0; i < selectedNum; i++) {
					String tag = sdList.get(selectIndex[i]).getTag();
					delDatas.add(tag);
				}
				if (staticDataInfo.del("StaticDataTemplate", delDatas)) {
					sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
					table.repaint();

					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	};

	private JComboBox comboBox_2;

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

	public void refreshModel(List<StaticDataInfo> sdList) {
		table.setTreeTableModel(new MyTreeTableModel(sdList));
		expandAll();
		table.repaint();
		table.packColumn(table.getHierarchicalColumn(), -1);
	}

	public void expandAll() {
		table.expandAll();
	}

	public void collapseAll() {
		table.collapseAll();
	}

	public void setTableWidth(JTable jt) {
		TableColumnModel dd = jt.getColumnModel();
		dd.getColumn(0).setPreferredWidth(80);
		dd.getColumn(1).setPreferredWidth(80);
		dd.getColumn(2).setPreferredWidth(400);
		dd.getColumn(3).setPreferredWidth(120);
	}
}
