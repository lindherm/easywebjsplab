package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.watchdata.cardcheck.dao.IAppInfoDao;
import com.watchdata.cardcheck.dao.pojo.AppInfo;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;

/**
 * 
 * @description:AID配置
 * 
 * @author: jia.hu 2012-3-26
 * 
 * @version: 1.0.0
 * 
 * @modify:
 * 
 * @Copyright: watchdata
 */
public class AIDConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 204749686729006658L;
	private JTable table;
	public static JTextField textFieldAid;
	public static JTextField textFieldDscrpt;
	public static JTextField textFieldVersion;
	public static JButton buttonAdd;
	public static JButton buttonDel;
	public static JButton buttonSave;

	private IAppInfoDao appinfodao;
	public ApplicationContext ctx = new FileSystemXmlApplicationContext(
			"classpath:applicationContext.xml");
	private PropertiesManager pm = new PropertiesManager();
	private final String[] COLUMNS = new String[] {
			pm.getString("mv.aidconfig.aidtitle"),
			pm.getString("mv.aidconfig.discriptiontitle"),
			pm.getString("mv.aidconfig.versiontitle") };
	private List<AppInfo> sdList;
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;

	/**
	 * Create the panel
	 */
	public AIDConfigPanel() {
		super(null);
		setName(pm.getString("mv.aidconfig.name"));
		setBorder(JTBorderFactory.createTitleBorder(pm
				.getString("mv.menu.AIDConfig")));

		/*
		 * final JSplitPane splitPane = new JSplitPane();
		 * splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		 * splitPane.setDividerLocation(180);
		 * splitPane.setOneTouchExpandable(true); add(splitPane,
		 * BorderLayout.CENTER);
		 */

		/*
		 * final JPanel panel = new JPanel(); panel.setLayout(new
		 * BorderLayout()); splitPane.setLeftComponent(panel);
		 */
		/*
		 * textField.setBorder(null); textField.setBackground(new
		 * Color(176,196,236));
		 */
		/*
		 * textField_1.setBorder(null); textField_1.setBackground(new
		 * Color(176,196,236));
		 */
		/* gridBagConstraints_4.ipady = 20; */
		/*
		 * textField_2.setBorder(null); textField_2.setBackground(new
		 * Color(176,196,236));
		 */

		/*
		 * final JPanel panel_6 = new JPanel(); panel_6.setLayout(null);
		 * panel.add(panel_6, BorderLayout.NORTH);
		 */

		final JLabel label_3 = new JLabel();
		label_3.setBounds(0, 50, 97, 20);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("宋体", Font.BOLD, 12));

		label_3.setText(pm.getString("mv.aidconfig.addaid"));
		add(label_3);

		final JSeparator separator = new JSeparator();
		separator.setBounds(79, 60, 730, 20);
		add(separator);

		/*
		 * final JPanel panel_10 = new JPanel(); panel_10.setLayout(null);
		 * add(panel_10);
		 */

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setText(pm.getString("mv.aidconfig.aid"));
		label.setBounds(0, 95, 97, 20);
		add(label);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mv.aidconfig.discription"));
		label_1.setBounds(220, 95, 97, 20);
		add(label_1);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.aidconfig.version"));
		label_2.setBounds(445, 95, 97, 20);
		add(label_2);

		textFieldAid = new JTextField();
		textFieldAid.setBounds(100, 95, 120, 20);
		textFieldAid.setDocument(new FixedSizePlainDocument(32));
		add(textFieldAid);

		textFieldDscrpt = new JTextField();
		textFieldDscrpt.setBounds(320, 95, 120, 20);
		textFieldDscrpt.setDocument(new FixedSizePlainDocument(128));
		add(textFieldDscrpt);

		textFieldVersion = new JTextField();
		textFieldVersion.setBounds(545, 95, 120, 20);
		textFieldVersion.setDocument(new FixedSizePlainDocument(32));
		add(textFieldVersion);

		buttonAdd = new JButton();
		buttonAdd.setText(pm.getString("mv.aidconfig.add"));
		buttonAdd.setBounds(715, 95, 84, 21);
		appinfodao = (IAppInfoDao) ctx.getBean("appInfoDao");

		// buttonAdd.addActionListener(new ActionListener() {
		// public void actionPerformed(final ActionEvent e) {
		//
		// if (0 == textFieldAid.getText().trim().length()) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.fullInfo"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// } else {
		// AIDConfigTableModel configTableModel = (AIDConfigTableModel) table
		// .getModel();
		// Vector<Vector<String>> data = configTableModel.getData();
		//
		// for (int i = 0; i < data.size(); i++) {
		// if (data.get(i).get(0).equalsIgnoreCase(
		// textFieldAid.getText())) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.aidexists"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		// }
		//
		// Vector<String> vector = new Vector<String>();
		// vector.add(textFieldAid.getText());
		// vector.add(textFieldDscrpt.getText());
		// vector.add(textFieldVersion.getText());
		// configTableModel.addRow(vector);
		//
		// }
		//
		// }
		// });

		add(buttonAdd);

		/*
		 * final JPanel panel_1 = new JPanel(); panel_1.setLayout(new
		 * BorderLayout()); splitPane.setRightComponent(panel_1);
		 */

		/*
		 * final JSplitPane splitPane_1 = new JSplitPane();
		 * splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		 * splitPane_1.setDividerSize(0); splitPane_1.setDividerLocation(400);
		 * panel_1.add(splitPane_1);
		 */

		/*
		 * final JPanel panel_2 = new JPanel(); panel_2.setLayout(new
		 * BorderLayout()); splitPane_1.setLeftComponent(panel_2);
		 */

		/*
		 * final JPanel panel_4 = new JPanel(); panel_4.setLayout(new
		 * BoxLayout(panel_4, BoxLayout.X_AXIS)); panel_2.add(panel_4,
		 * BorderLayout.NORTH);
		 */

		final JLabel addAIDLabel = new JLabel();
		addAIDLabel.setBounds(0, 150, 97, 20);
		addAIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addAIDLabel.setFont(new Font("宋体", Font.BOLD, 12));
		addAIDLabel.setText(pm.getString("mv.aidconfig.editaid"));
		add(addAIDLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 160, 730, 20);
		add(separator_1);

		/*
		 * final JPanel panel_5 = new JPanel(); panel_5.setLayout(new
		 * BoxLayout(panel_5, BoxLayout.X_AXIS)); panel_2.add(panel_5,
		 * BorderLayout.CENTER);
		 */

		/*
		 * final JSplitPane splitPane_2 = new JSplitPane();
		 * splitPane_2.setVisible(true); splitPane_2.addComponentListener(new
		 * ComponentAdapter() { public void componentResized(ComponentEvent e) {
		 * splitPane_2.setDividerLocation(0.7); } }); panel_5.add(splitPane_2);
		 */

		/*
		 * final JPanel panel_7 = new JPanel(); panel_7.setLayout(new
		 * BoxLayout(panel_7, BoxLayout.X_AXIS));
		 * splitPane_2.setLeftComponent(panel_7);
		 */

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 180, 620, 473);
		add(scrollPane);

		// Object[][] columnData = {};
		// String[] columnTitle = {};
		// table = new JTable(columnData, columnTitle);
		// AIDConfigTableModel configTableModel = new AIDConfigTableModel();
		//
		// table.setModel(configTableModel);
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		// table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		sdList = appinfodao.findAppInfo();
		tableDataDisp();
		scrollPane.setViewportView(table);

		/*
		 * final JPanel panel_8 = new JPanel(); panel_8.setLayout(null);
		 * splitPane_2.setRightComponent(panel_8);
		 */

		buttonDel = new JButton();
		buttonDel.setText(pm.getString("mv.aidconfig.del"));
		buttonDel.setBounds(715, 330, 84, 21);

		// buttonDel.addActionListener(new ActionListener() {
		// public void actionPerformed(final ActionEvent e) {
		// int delIndex[] = table.getSelectedRows();
		// if (delIndex.length != 0) {
		// if (1 == JOptionPane.showConfirmDialog(null, pm
		// .getString("mv.aidconfig.deleteInfoconfirm"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.YES_NO_OPTION,
		// JOptionPane.QUESTION_MESSAGE))
		// return;
		//
		// AIDConfigTableModel tableModel = (AIDConfigTableModel) table
		// .getModel();
		// for (int i = delIndex.length - 1; i >= 0; i--) {
		// tableModel.delRow(delIndex[i]);
		// }
		// } else {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.deleteInfo"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		// }
		//
		// });
		add(buttonDel);

		buttonSave = new JButton();
		buttonSave.setText(pm.getString("mv.aidconfig.edit"));
		buttonSave.setBounds(715, 425, 84, 21);
		// buttonSave.addActionListener(new ActionListener() {
		// public void actionPerformed(final ActionEvent e) {
		// AIDConfigTableModel configTableModel = (AIDConfigTableModel) table
		// .getModel();
		// Vector<Vector<String>> data = configTableModel.getData();
		// if (table.isEditing()) {
		// int row = table.getEditingRow();
		// int col = table.getEditingColumn();
		// table.getCellEditor(row, col).stopCellEditing();
		// }
		// ArrayList<String> alaid = new ArrayList<String>();
		// for (int i = 0; i < data.size(); i++) {
		// if (alaid.contains(data.get(i).get(0))) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.aidexists"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		// alaid.add(data.get(i).get(0));
		// if(checkData(data.get(i).get(0).trim().toString())!=0){
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.index")
		// + String.valueOf(i + 1)
		// + pm.getString("mv.aidconfig.row")
		// + pm.getString("mv.aidconfig.fullInfo"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		//
		// if (data.get(i).get(1).length() > 128) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.index")
		// + String.valueOf(i + 1)
		// + pm.getString("mv.aidconfig.row")
		// + pm.getString("mv.aidconfig.discriptionerr"),
		// pm.getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		//
		// if (data.get(i).get(2).length() > 32) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.index")
		// + String.valueOf(i + 1)
		// + pm.getString("mv.aidconfig.row")
		// + pm.getString("mv.aidconfig.versionerr"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		//
		// }
		//
		// AppInfo appinfo = new AppInfo();
		// if (!appinfodao.deleteAppinfo()) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.deleteerr"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// return;
		//
		// }
		//
		// for (int i = 0; i < data.size(); i++) {
		//					
		// appinfo.setAid(data.get(i).get(0));
		// appinfo.setDscrpt(data.get(i).get(1));
		// appinfo.setVersion(data.get(i).get(2));
		// if (!appinfodao.insertAppinfo(appinfo)) {
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.index")
		// + String.valueOf(i + 1)
		// + pm.getString("mv.aidconfig.saveerr"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.ERROR_MESSAGE);
		// continue;
		// }
		// }
		// JOptionPane.showMessageDialog(null, pm
		// .getString("mv.aidconfig.savesuccess"), pm
		// .getString("mv.aidconfig.infoWindow"),
		// JOptionPane.INFORMATION_MESSAGE);
		// }
		//
		// });
		add(buttonSave);
		buttonAdd.addActionListener(addActionListener);
		buttonDel.addActionListener(delActionListener);
		buttonSave.addActionListener(saveActionListener);
		/*
		 * final JPanel panel_3 = new JPanel();
		 * splitPane_1.setRightComponent(panel_3);
		 */
	}

	public static int checkData(String data) {
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
		String result[] = data.split("[|]");
		for (int i = 0; i < result.length; i++) {
			if ((result[i].length() > 32 || result[i].trim().length() == 0)
					|| !pattern.matcher(result[i]).matches()) {
				return i + 1;
			}
		}
		return 0;

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
		private int[] selectedIndex;

		public boolean isTrue(int rowNum, int row) {
			return row == rowNum;
		}

		public boolean f(int num, int row, int[] selectedIndex) {
			if (num == 1) {
				return isTrue(selectedIndex[num - 1], row);
			} else {
				return f(num - 1, row, selectedIndex)
						|| isTrue(selectedIndex[num - 1], row);
			}
		}

		public Component getTableCellRendererComponent(JTable t, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			if (f(selectedIndex.length, row, selectedIndex))
				setBackground(Color.green);
			else {
				setBackground(Color.white);
			}
			return super.getTableCellRendererComponent(t, value, isSelected,
					hasFocus, row, column);
		}

		public void setSelectedIndex(int[] selectedIndex) {
			this.selectedIndex = selectedIndex;
		}

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
			if (table.isCellEditable(i, 1)) {
				selectedRowNum.add(i);
			}
		}
		return selectedRowNum;
	}

	/**
	 * @Title: tableDataDisp
	 * @Description 将从数据库中查出的数据显示在table中
	 * @param
	 * @return
	 * @throws
	 */
	public void tableDataDisp() {
		int rowNum = sdList.size();
		tableData = new Object[rowNum][4];
		for (int i = 0; i < rowNum; i++) {
			tableData[i][0] = sdList.get(i).getAid();
			tableData[i][1] = sdList.get(i).getDscrpt();
			tableData[i][2] = sdList.get(i).getVersion();
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
	 * @Title: paintColorRow
	 * @Description 为table的特定行设置背景颜色
	 * @param selectedIndex选中行索引
	 *            ，即需要设置背景颜色的行索引
	 * @return
	 * @throws
	 */
	public void paintColorRow(int[] selectedIndex) {
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			RowRenderer rowRenderer = new RowRenderer();
			rowRenderer.setSelectedIndex(selectedIndex);
			tc.setCellRenderer(rowRenderer);
		}
	}

	// 添加按钮监听事件
	private ActionListener addActionListener = new ActionListener() {
		public void actionPerformed(final ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.aidconfig.addInfo"), pm
						.getString("mv.aidconfig.infoWindow"),
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (checkData(textFieldAid.getText().trim()) != 0) {
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.aidconfig.aiderr"), pm
						.getString("mv.aidconfig.infoWindow"),
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				AppInfo appinfo = new AppInfo();
				if (appinfodao.findAppinfo(textFieldAid.getText().trim()) != null) {
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.aidconfig.aidexists"), pm
							.getString("mv.aidconfig.infoWindow"),
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				appinfo.setAid(textFieldAid.getText().trim());
				appinfo.setDscrpt(textFieldDscrpt.getText().trim());
				appinfo.setVersion(textFieldVersion.getText().trim());
				if (!appinfodao.insertAppinfo(appinfo)) {
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.aidconfig.saveerr"), pm
							.getString("mv.aidconfig.infoWindow"),
							JOptionPane.ERROR_MESSAGE);
				}

				// 将添加的数据从数据库中取出来在table中显示出来
				sdList = appinfodao.findAppInfo();
				tableDataDisp();
				table.repaint();
			}
		}
	};

	// 删除按钮监听事件
	private ActionListener delActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			/*
			 * DelThread delThread = new DelThread(); delThread.addListener(new
			 * DelListener(){
			 * 
			 * @Override public void del(){
			 */
			AppInfo appinfo = new AppInfo();
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.aidconfig.saveBefoeDelete"), pm
						.getString("mv.aidconfig.infoWindow"),
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int selectedNum = table.getSelectedRows().length;

			int[] selectIndex = table.getSelectedRows();
			if (selectedNum == 0) {
				JOptionPane.showMessageDialog(null, pm
						.getString("mv.aidconfig.deleteInfo"), pm
						.getString("mv.aidconfig.infoWindow"),
						JOptionPane.INFORMATION_MESSAGE);
			} else if (selectedNum >= 1) {
				int y = JOptionPane
						.showConfirmDialog(null, pm
								.getString("mv.aidconfig.deleteInfoconfirm"),
								pm.getString("mv.aidconfig.infoWindow"),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					return;
				}
				List<String> delDatas = new ArrayList<String>();
				for (int i = 0; i < selectedNum; i++) {
					String idStr = sdList.get(selectIndex[i]).getAid();
					delDatas.add(idStr);
				}
				if (appinfodao.batchDel(delDatas)) {
					sdList = appinfodao.findAppInfo();
					tableDataDisp();
					table.repaint();
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.aidconfig.deletesuccess"), pm
							.getString("mv.aidconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.aidconfig.deleteerr"), pm
							.getString("mv.aidconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

			/*
			 * } }); delThread.start();
			 */

		}
	};

	// 保存按钮监听事件
	private ActionListener saveActionListener = new ActionListener() {
		@Transactional
		public void actionPerformed(final ActionEvent e) {
			int selectedNum = table.getSelectedRows().length;
			if (pm.getString("mv.aidconfig.edit").equals(buttonSave.getText())) {
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(null, pm
							.getString("mv.aidconfig.editInfo"), pm
							.getString("mv.aidconfig.infoWindow"),
							JOptionPane.INFORMATION_MESSAGE);
				} else if (selectedNum >= 1) {
					final int[] selectedIndex = table.getSelectedRows();
					table.setModel(new DefaultTableModel(tableData, COLUMNS) {
						private static final long serialVersionUID = -3564556245515260000L;

						// 将选中行设为可编辑状态
						public boolean isTrue(int rowNum, int row) {
							return row == rowNum;
						}

						public boolean f(int num, int row, int[] selectedIndex) {
							if (num == 1) {
								return isTrue(selectedIndex[num - 1], row);
							} else {
								return f(num - 1, row, selectedIndex)
										|| isTrue(selectedIndex[num - 1], row);
							}
						}

						@Override
						public boolean isCellEditable(int row, int col) {
							if (0 == col)
								return false;
							if (f(selectedIndex.length, row, selectedIndex)) {
								return true;
							} else {
								return false;
							}
						}
					});
					paintColorRow(selectedIndex);
					buttonSave.setText(pm.getString("mv.aidconfig.save"));
				}
			} else if (pm.getString("mv.aidconfig.save").equals(
					buttonSave.getText())) {
				if (table.isEditing()) {
					int row = table.getEditingRow();
					int col = table.getEditingColumn();
					table.getCellEditor(row, col).stopCellEditing();
				}
				List<Integer> indexRow = getEditableRow();
				List<AppInfo> editDatas = new ArrayList<AppInfo>();

				if (indexRow.size() > 0) {
					for (int i = 0; i < indexRow.size(); i++) {
						AppInfo appinfo = new AppInfo();

						if (table.getValueAt(indexRow.get(i), 1).toString()
								.trim().length() > 128) {
							JOptionPane.showMessageDialog(null, pm
									.getString("mv.aidconfig.discriptionerr"),
									pm.getString("mv.aidconfig.infoWindow"),
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (table.getValueAt(indexRow.get(i), 2).toString()
								.trim().length() > 32) {
							JOptionPane.showMessageDialog(null, pm
									.getString("mv.aidconfig.versionerr"), pm
									.getString("mv.aidconfig.infoWindow"),
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						appinfo.setAid(sdList.get(indexRow.get(i)).getAid());
						appinfo
								.setDscrpt(table.getValueAt(indexRow.get(i), 1) == null ? null
										: table.getValueAt(indexRow.get(i), 1)
												.toString());
						appinfo
								.setVersion(table
										.getValueAt(indexRow.get(i), 2) == null ? null
										: table.getValueAt(indexRow.get(i), 2)
												.toString());

						editDatas.add(appinfo);
					}
					if (appinfodao.batchSaveUpdate(editDatas)) {
						sdList = appinfodao.findAppInfo();
						tableDataDisp();
						table.repaint();
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.aidconfig.savesuccess"), pm
								.getString("mv.aidconfig.infoWindow"),
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, pm
								.getString("mv.aidconfig.saveerr"), pm
								.getString("mv.aidconfig.infoWindow"),
								JOptionPane.INFORMATION_MESSAGE);
					}
					buttonSave.setText(pm.getString("mv.aidconfig.edit"));
				}
			}

		}
	};

}
