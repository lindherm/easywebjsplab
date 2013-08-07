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

import com.watchdata.cardcheck.configdao.AIDInfo;
import com.watchdata.cardcheck.utils.Config;
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

    private PropertiesManager pm = new PropertiesManager();
	private final String[] COLUMNS = new String[] { pm.getString("mv.aidconfig.aidtitle"), pm.getString("mv.aidconfig.discriptiontitle"), pm.getString("mv.aidconfig.versiontitle") };
	private List<AIDInfo> sdList;
	private AIDInfo aidInfo = new AIDInfo();
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;

	/**
	 * Create the panel
	 */
	public AIDConfigPanel() {
		super(null);
		setName(pm.getString("mv.aidconfig.name"));
		//setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.AIDConfig")));

		final JLabel label_3 = new JLabel();
		label_3.setBounds(-10, 49, 97, 20);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("宋体", Font.BOLD, 12));

		label_3.setText(pm.getString("mv.aidconfig.addaid"));
		add(label_3);

		final JSeparator separator = new JSeparator();
		separator.setBounds(69, 59, 730, 20);
		add(separator);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setText(pm.getString("mv.aidconfig.aid"));
		label.setBounds(-10, 96, 97, 20);
		add(label);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mv.aidconfig.discription"));
		label_1.setBounds(196, 96, 97, 20);
		add(label_1);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.aidconfig.version"));
		label_2.setBounds(406, 96, 97, 20);
		add(label_2);

		textFieldAid = new JTextField();
		textFieldAid.setBounds(90, 96, 120, 20);
		textFieldAid.setDocument(new FixedSizePlainDocument(32));
		add(textFieldAid);

		textFieldDscrpt = new JTextField();
		textFieldDscrpt.setBounds(296, 96, 120, 20);
		textFieldDscrpt.setDocument(new FixedSizePlainDocument(128));
		add(textFieldDscrpt);

		textFieldVersion = new JTextField();
		textFieldVersion.setBounds(506, 96, 120, 20);
		textFieldVersion.setDocument(new FixedSizePlainDocument(32));
		add(textFieldVersion);

		buttonAdd = new JButton();
		buttonAdd.setText(pm.getString("mv.aidconfig.add"));
		buttonAdd.setBounds(658, 96, 84, 21);
		add(buttonAdd);

		final JLabel addAIDLabel = new JLabel();
		addAIDLabel.setBounds(-10, 138, 97, 20);
		addAIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addAIDLabel.setFont(new Font("宋体", Font.BOLD, 12));
		addAIDLabel.setText(pm.getString("mv.aidconfig.editaid"));
		add(addAIDLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(69, 148, 730, 20);
		add(separator_1);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 180, 690, 499);
		add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(true);

		sdList = aidInfo.getAidInfos("SupAID");
		tableDataDisp();
		scrollPane.setViewportView(table);

		buttonDel = new JButton();
		buttonDel.setText(pm.getString("mv.aidconfig.del"));
		buttonDel.setBounds(710, 205, 84, 21);

		add(buttonDel);

		buttonSave = new JButton();
		buttonSave.setText(pm.getString("mv.aidconfig.edit"));
		buttonSave.setBounds(710, 236, 84, 21);
		add(buttonSave);
		buttonAdd.addActionListener(addActionListener);
		buttonDel.addActionListener(delActionListener);
		buttonSave.addActionListener(saveActionListener);
	}

	public static int checkData(String data) {
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
		String result[] = data.split("[|]");
		for (int i = 0; i < result.length; i++) {
			if ((result[i].length() > 32 || result[i].trim().length() == 0) || !pattern.matcher(result[i]).matches()) {
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
				return f(num - 1, row, selectedIndex) || isTrue(selectedIndex[num - 1], row);
			}
		}

		public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (f(selectedIndex.length, row, selectedIndex))
				setBackground(Color.green);
			else {
				setBackground(Color.white);
			}
			return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
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
				JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.addInfo"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (checkData(textFieldAid.getText().trim()) != 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.aiderr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				AIDInfo aidInfo = new AIDInfo();
				if (Config.getItem("SupAID", textFieldAid.getText().trim())!=null) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.aidexists"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				aidInfo.setAid(textFieldAid.getText().trim());
				aidInfo.setDscrpt(textFieldDscrpt.getText().trim());
				aidInfo.setVersion(textFieldVersion.getText().trim());

				try {
					if (aidInfo.add("SupAID", aidInfo)) {
						// 将添加的数据从数据库中取出来在table中显示出来
						sdList = aidInfo.getAidInfos("SupAID");
						tableDataDisp();
						table.repaint();

						JOptionPane.showMessageDialog(null, "添加成功!", "提示框", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.saveerr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"添加aid出错", e.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	// 删除按钮监听事件
	private ActionListener delActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.saveBefoeDelete"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int selectedNum = table.getSelectedRows().length;

			int[] selectIndex = table.getSelectedRows();
			if (selectedNum == 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.deleteInfo"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else if (selectedNum >= 1) {
				int y = JOptionPane.showConfirmDialog(null, pm.getString("mv.aidconfig.deleteInfoconfirm"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					return;
				}
				List<String> delDatas = new ArrayList<String>();
				for (int i = 0; i < selectedNum; i++) {
					String idStr = sdList.get(selectIndex[i]).getAid();
					delDatas.add(idStr);
				}
				if (aidInfo.del("SupAID", delDatas)) {
					sdList = aidInfo.getAidInfos("SupAID");
					tableDataDisp();
					table.repaint();
					JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.deletesuccess"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.deleteerr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				}
			}

		}
	};
	
	private ActionListener saveActionListener = new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			int selectedNum = table.getSelectedRows().length;
			if (pm.getString("mv.aidconfig.edit").equals(buttonSave.getText())) {
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.editInfo"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
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
								return f(num - 1, row, selectedIndex) || isTrue(selectedIndex[num - 1], row);
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
			} else if (pm.getString("mv.aidconfig.save").equals(buttonSave.getText())) {
				if (table.isEditing()) {
					int row = table.getEditingRow();
					int col = table.getEditingColumn();
					table.getCellEditor(row, col).stopCellEditing();
				}
				List<Integer> indexRow = getEditableRow();
				List<AIDInfo> editDatas = new ArrayList<AIDInfo>();

				if (indexRow.size() > 0) {
					for (int i = 0; i < indexRow.size(); i++) {
						AIDInfo aidInfo = new AIDInfo();

						if (table.getValueAt(indexRow.get(i), 1).toString().trim().length() > 128) {
							JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.discriptionerr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (table.getValueAt(indexRow.get(i), 2).toString().trim().length() > 32) {
							JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.versionerr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						aidInfo.setAid(sdList.get(indexRow.get(i)).getAid());
						aidInfo.setDscrpt(table.getValueAt(indexRow.get(i), 1) == null ? null : table.getValueAt(indexRow.get(i), 1).toString());
						aidInfo.setVersion(table.getValueAt(indexRow.get(i), 2) == null ? null : table.getValueAt(indexRow.get(i), 2).toString());

						editDatas.add(aidInfo);
					}
					if (aidInfo.update("SupAID", editDatas)) {
						sdList = aidInfo.getAidInfos("SupAID");
						tableDataDisp();
						table.repaint();
						JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.savesuccess"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.saveerr"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}
					buttonSave.setText(pm.getString("mv.aidconfig.edit"));
				}
			}

		}
	};

}
