package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import org.jdesktop.swingx.demos.tree.TreeDemoIconValues.LazyLoadingIconValue;
import org.jdesktop.swingx.demos.tree.XTreeDemo;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.util.PaintUtils;
import org.jdesktop.swingxset.util.DemoUtils;

import com.watchdata.cardcheck.configdao.StaticDataInfo;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.cardcheck.tlv.TLV;
import com.watchdata.cardcheck.tlv.TLVList;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDByteUtil;
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
	private AbstractHighlighter mouseOverHighlighter;

	public TestDataConfigPanel() {
		super();
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
		add(addButton);

		delButton = new JButton();
		delButton.setText(pm.getString("mv.testdata.delete"));
		delButton.setBounds(730, 136, 84, 21);
		add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 710, 562);

		/*
		 * table = new JTable(); table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 * 
		 * table.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { if (SwingUtilities.isLeftMouseButton(e)) { if (e.getClickCount() == 2) { int row = table.rowAtPoint(e.getPoint()); int colum = table.columnAtPoint(e.getPoint()); Object ob = table.getValueAt(row, colum); Point p = e.getLocationOnScreen(); dialog.setLocation(p); if (table.getValueAt(row, colum - 2).equals("8E")) { ep.setText(parse8E(ob.toString())); } else { ep.setText(ob.toString()); }
		 * 
		 * dialog.setVisible(true); } } } });
		 */
		table = new JXTreeTable();
		table.setName("componentTreeTable");
		configureComponents();
		sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
		refreshModel(sdList);
		scrollPane.setViewportView(table);
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
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						logger.setLogArea(null);
						if (comboBox.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "请选择aid");
							return;
						}

						String aid = comboBox.getSelectedItem().toString().trim();
						String reader = Config.getValue("Terminal_Data", "reader");
						HashMap<String, String> res = apduHandler.reset(reader);
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("sw"));
							return;
						}

						HashMap<String, String> pseResult = apduHandler.select(Constants.PSE);
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(pseResult.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("sw"));
							return;
						}

						HashMap<String, String> aidResult = apduHandler.select(aid);
						if (!Constants.SW_SUCCESS.equalsIgnoreCase(aidResult.get("sw"))) {
							JOptionPane.showMessageDialog(null, res.get("sw"));
							return;
						}

						for (int sfi = 1; sfi <= 31; sfi++) {
							for (int rec = 1; rec <= 16; rec++) {
								int sfi1 = (sfi << 3) | 4;
								HashMap<String, String> readRes = apduHandler.readRecord(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2), WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2));
								if (Constants.SW_SUCCESS.equalsIgnoreCase(readRes.get("sw"))) {
									for (int i = 0; i < table.getRowCount(); i++) {
										String tag = table.getValueAt(i, 0).toString();
										if (readRes.containsKey(tag)) {
											JXTreeNode jxTreeNode = (JXTreeNode) table.getTreeTableModel().getChild(table.getTreeTableModel().getRoot(), i);
											jxTreeNode.getChildren().add(new JXTreeNode(tag, CommonHelper.getDgiHead(WDStringUtil.paddingHeadZero(Integer.toHexString(sfi1), 2)) + WDStringUtil.paddingHeadZero(Integer.toHexString(rec), 2), readRes.get(tag), Config.getValue("TAG", tag)));
											
										}
									}
								}
							}
						}
						apduHandler.close();
						expandAll();
					}
				});
				thread.start();
			}
		});
		button.setText("检测");
		button.setBounds(730, 198, 84, 21);
		add(button);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(285, 44, 140, 20);
		add(comboBox_2);

		JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
				refreshModel(sdList);
				table.repaint();
			}
		});
		button_1.setText("刷新");
		button_1.setBounds(730, 167, 84, 21);
		add(button_1);

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

	private void configureComponents() {
		StringValue sv = new StringValue() {

			@Override
			public String getString(Object value) {
				if (value instanceof Component) {
					Component component = (Component) value;
					String simpleName = component.getClass().getSimpleName();
					if (simpleName.length() == 0) {
						// anonymous class
						simpleName = component.getClass().getSuperclass().getSimpleName();
					}
					return simpleName;
				}
				return StringValues.TO_STRING.getString(value);
			}
		};
		StringValue keyValue = new StringValue() {

			@Override
			public String getString(Object value) {
				if (value == null)
					return "";
				String simpleClassName = value.getClass().getSimpleName();
				if (simpleClassName.length() == 0) {
					// anonymous class
					simpleClassName = value.getClass().getSuperclass().getSimpleName();
				}
				return simpleClassName + ".png";
			}
		};
		// <snip> JXTreeTable rendering
		// IconValue provides node icon (same as in XTreeDemo)
		IconValue iv = new LazyLoadingIconValue(XTreeDemo.class, keyValue, "fallback.png");
		// create and set a tree renderer using the custom Icon-/StringValue
		table.setTreeCellRenderer(new DefaultTreeRenderer(iv, sv));
		// string representation for use of Dimension/Point class
		StringValue locSize = new StringValue() {

			@Override
			public String getString(Object value) {
				int x;
				int y;
				if (value instanceof Dimension) {
					x = ((Dimension) value).width;
					y = ((Dimension) value).height;
				} else if (value instanceof Point) {
					x = ((Point) value).x;
					y = ((Point) value).y;
				} else {
					return StringValues.TO_STRING.getString(value);
				}
				return "(" + x + ", " + y + ")";
			}
		};
		table.setDefaultRenderer(Point.class, new DefaultTableRenderer(locSize, JLabel.CENTER));
		table.setDefaultRenderer(Dimension.class, table.getDefaultRenderer(Point.class));
		mouseOverHighlighter = new ColorHighlighter(HighlightPredicate.NEVER, PaintUtils.setSaturation(Color.MAGENTA, 0.3f), null);
		table.addHighlighter(mouseOverHighlighter);

		table.setColumnControlVisible(true);
		DemoUtils.setSnippet("JXTreeTable rendering", table);
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
}
