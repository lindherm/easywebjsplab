package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.watchdata.cardcheck.configdao.StaticDataInfo;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
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
	public static JTable table;
	private JLabel tagLabel;
	private JLabel appTypeLabel;
	public static JComboBox appTypeComboBox;
	private JButton addButton;
	public static JButton delButton;
	public JComboBox comboBox;
	private PropertiesManager pm = new PropertiesManager();
	private final String[] COLUMNS = new String[] { "DGI", "TAG","LEN", "值", "检测结果" };
	private List<StaticDataInfo> sdList = new ArrayList<StaticDataInfo>();
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;
	public static JProgressBar progressBar;
	public CommonAPDU apduHandler;
	public static Log log = new Log();
	public StaticDataInfo staticDataInfo = new StaticDataInfo();

	public TableColumnModel tcm;
	public TableColumn tc;
	public RowRenderer rowRenderer;

	public JComboBox comboBox_1;
	
	private JDialog dialog = new JDialog();
	private JEditorPane ep = new JEditorPane();
	private JScrollPane dlgscrollPane = new JScrollPane(ep);

	public TestDataConfigPanel() {
		super();
		setLayout(null);
		setName(pm.getString("mv.testdata.name"));
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.dataConfig")));

		apduHandler = new CommonAPDU();

		tagLabel = new JLabel();
		tagLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tagLabel.setBounds(238, 128, 40, 20);
		tagLabel.setText(pm.getString("mv.testdata.tag"));
		add(tagLabel);

		appTypeLabel = new JLabel();
		appTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		appTypeLabel.setText(pm.getString("mv.testdata.appTypeName"));
		appTypeLabel.setBounds(265, 71, 80, 20);
		add(appTypeLabel);

		appTypeComboBox = new JComboBox();
		appTypeComboBox.addItem("借贷记");
		appTypeComboBox.addItem("电子现金");
		appTypeComboBox.addItem("QPBOC");
		appTypeComboBox.setBounds(353, 71, 140, 20);
		add(appTypeComboBox);

		addButton = new JButton();
		addButton.setText("增加");
		addButton.setBounds(725, 127, 84, 21);
		add(addButton);

		final JLabel editDataLabel = new JLabel();
		editDataLabel.setBounds(0, 101, 97, 20);
		editDataLabel.setHorizontalAlignment(SwingConstants.LEFT);
		editDataLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		editDataLabel.setText(pm.getString("mv.testdata.editData"));
		add(editDataLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(53, 112, 730, 20);
		add(separator_1);

		delButton = new JButton();
		delButton.setText(pm.getString("mv.testdata.delete"));
		delButton.setBounds(725, 199, 84, 21);
		add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 171, 710, 510);
		add(scrollPane);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int row = table.rowAtPoint(e.getPoint());
						int colum = table.columnAtPoint(e.getPoint());
						Object ob = table.getValueAt(row, colum);
						Point p = e.getLocationOnScreen();
						dialog.setLocation(p);
						if (table.getValueAt(row, colum-2).equals("8E")) {
							ep.setText(parse8E(ob.toString()));
						}else {
							ep.setText(ob.toString());
						}
						
						dialog.setVisible(true);
					}
				}
			}
		});
		
		sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
		tableDataDisp();
		setTableWidth(table);
		table.repaint();
		scrollPane.setViewportView(table);

		JLabel lblAid = new JLabel();
		lblAid.setText("AID：");
		lblAid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAid.setBounds(16, 71, 64, 20);
		add(lblAid);

		comboBox = new JComboBox();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.setLogDialogOff();
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
		comboBox.setBounds(88, 71, 180, 20);
		add(comboBox);

		JLabel lblDgi = new JLabel();
		lblDgi.setText("DGI：");
		lblDgi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDgi.setBounds(16, 128, 64, 20);
		add(lblDgi);

		JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "请选择aid");
					return;
				}

				if (appTypeComboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "请选择应用类型");
					return;
				}

				for (int i = 0; i < table.getRowCount(); i++) {
					String aid = comboBox.getSelectedItem().toString().trim();
					String dgi = table.getValueAt(i, 0).toString();
					String tag = table.getValueAt(i, 1).toString();

					String p1 = dgi.substring(2);
					String p2 = dgi.substring(0, 2);

					log.setLogDialogOff();
					String reader = Config.getValue("Terminal_Data", "reader");
					HashMap<String, String> res = apduHandler.reset(reader);
					if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
						JOptionPane.showMessageDialog(null, res.get("sw"));
					}

					HashMap<String, String> result = apduHandler.select(aid);
					if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
						JOptionPane.showMessageDialog(null, res.get("sw"));
					}

					int b = Integer.parseInt(p2);
					b = (b << 3) + 4;
					HashMap<String, String> readRes = apduHandler.readRecord(WDStringUtil.paddingHeadZero(Integer.toHexString(b), 2), WDStringUtil.paddingHeadZero(Integer.toHexString(Integer.parseInt(p1)), 2));

					if (Constants.SW_SUCCESS.equalsIgnoreCase(readRes.get("sw"))) {
						TLVList tlvList = new TLVList(WDByteUtil.HEX2Bytes(readRes.get("res")), TLV.EMV);
						tlvList = new TLVList(tlvList.find(0x70).getValue(), TLV.EMV);

						int tagHex = Integer.parseInt(tag, 16);
						TLV tlv = tlvList.find(tagHex);
						if (tlv != null) {
							String value = WDByteUtil.bytes2HEX(tlv.getValue());
							String len=WDByteUtil.bytes2HEX(tlv.getL());
							table.setValueAt(len, i, 2);
							table.setValueAt(value, i, 3);
							table.setValueAt(1, i, 4);
						} else {
							table.setValueAt(2, i, 4);
						}
					} else {
						table.setValueAt(2, i, 4);
					}
				}
				tcm = table.getColumnModel();
				tc = tcm.getColumn(4);
				rowRenderer = new RowRenderer();
				tc.setCellRenderer(rowRenderer);
				table.repaint();
				apduHandler.close();
			}
		});
		button.setText("检测");
		button.setBounds(725, 261, 84, 21);
		add(button);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(88, 128, 140, 20);
		add(comboBox_1);

		Collection<String> dgiCollections = Config.getItems("DGI");
		for (String dgi : dgiCollections) {
			comboBox_1.addItem(dgi);
		}
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(286, 128, 140, 20);
		add(comboBox_2);

		JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
				tableDataDisp();
				setTableWidth(table);
				table.repaint();
			}
		});
		button_1.setText("刷新");
		button_1.setBounds(725, 230, 84, 21);
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
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.addInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			StaticDataInfo staticDataInfo = new StaticDataInfo();
			staticDataInfo.setDgi(comboBox_1.getSelectedItem().toString());
			staticDataInfo.setTag(comboBox_2.getSelectedItem().toString());

			if (Config.getItem("StaticDataTemplate", staticDataInfo.getTag()) == null) {
				staticDataInfo.add("StaticDataTemplate", staticDataInfo);

				JOptionPane.showMessageDialog(null, "添加数据成功！","提示框",JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "数据项已经存在！","提示框",JOptionPane.ERROR_MESSAGE);
				return;
			}

			sdList = staticDataInfo.getStaticDataInfos("StaticDataTemplate");
			tableDataDisp();
			setTableWidth(table);
			table.repaint();
		}
	};

	// 删除按钮监听事件
	private ActionListener delActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.saveBefoeDelete"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
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
					tableDataDisp();
					setTableWidth(table);
					table.repaint();

					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	};

	private JComboBox comboBox_2;

	/**
	 * @Title: tableDataDisp
	 * @Description 将从数据库中查出的数据显示在table中
	 * @param
	 * @return
	 * @throws
	 */
	public void tableDataDisp() {
		int rowNum = sdList.size();
		tableData = new Object[rowNum][5];
		for (int i = 0; i < rowNum; i++) {
			tableData[i][0] = sdList.get(i).getDgi();
			tableData[i][1] = sdList.get(i).getTag();
			tableData[i][2] ="";
			tableData[i][3] = sdList.get(i).getValue();
			tableData[i][4] = sdList.get(i).getResult();
		}
		testDataTableModel = new DefaultTableModel(tableData, COLUMNS) {
			private static final long serialVersionUID = -9082031840487910439L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(testDataTableModel);
	}

	/**
	 * @Title: getEditableRow
	 * @Description 获取当前处于可编辑状态的行号
	 * @param
	 * @return 表格中可编辑的行号
	 * @throws
	 */
	public List<Integer> getEditableRow() {
		List<Integer> selectedRowNum = new ArrayList<Integer>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.isCellEditable(i, 0)) {
				selectedRowNum.add(i);
			}
		}
		return selectedRowNum;
	}

	/**
	 * @title TestDataConfigPanel.java
	 * @description table的行渲染器，用来设置行背景色
	 * @author pei.li 2012-3-28
	 * @version 1.0.0
	 * @modify
	 * @copyright watchdata
	 */
	private class RowRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -9128946524399930570L;

		public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (column == 4 && value.equals(1))
				setBackground(Color.green);
			else if (column == 4 && value.equals(2)) {
				setBackground(Color.red);
			} else {
				setBackground(Color.white);
			}
			return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
		}
	}

	public void setTableWidth(JTable jt) {
		TableColumnModel dd = jt.getColumnModel();
		dd.getColumn(0).setPreferredWidth(80);
		dd.getColumn(1).setPreferredWidth(80);
		dd.getColumn(2).setPreferredWidth(40);
		dd.getColumn(3).setPreferredWidth(350);
		dd.getColumn(4).setPreferredWidth(160);
	}
	
	public String parse8E(String str8E){
		StringBuilder sb=new StringBuilder();
		String x=str8E.substring(0,8);
		String y=str8E.substring(8,16);
		String cvmCode="";
		String cvmType="";
		
		x=x+"------金额X（二进制）";
		y=y+"------金额Y（二进制）";
		sb.append(x).append("\n").append(y).append("\n");
		sb.append("---------------------------------------\n");
		int i=16;
		while (i<str8E.length()) {
			cvmCode=str8E.substring(i, i+2);
			cvmType=str8E.substring(i+2,i+4);
			
			String binary=Integer.toBinaryString(Integer.parseInt(cvmCode, 16));
			binary=WDStringUtil.paddingHeadZero(binary, 8);
			
			cvmCode=cvmCode+"------"+Config.getValue("CVM_CODE", binary.substring(0, 2));
			cvmType=cvmType+"------"+Config.getValue("CVM_TYPE", binary.substring(2, 8));
			i+=4;
			sb.append(cvmCode).append("\n").append(cvmType).append("\n");
			sb.append("---------------------------------------\n");
		}
		
		System.out.println(sb.toString());
		return sb.toString();
	}
}
