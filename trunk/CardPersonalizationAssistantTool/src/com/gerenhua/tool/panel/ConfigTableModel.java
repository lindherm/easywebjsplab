package com.gerenhua.tool.panel;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.gerenhua.tool.utils.CollectionUtil;
import com.gerenhua.tool.utils.FileUtil;

public class ConfigTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] COLUMNS = new String[] { "字段名", "数据类型", "起始索引（前置）", "结束索引（LEN[长度]）","解密/算法","转码", "值" };
	//private Vector<Vector<String>> data = FileUtil.getConfigFromFile();
	private Vector<Vector<String>> data = new Vector<Vector<String>>();

	public Vector<Vector<String>> getData() {
		return data;
	}

	/**
	 * 添加一行
	 * 
	 * @param row
	 */
	public void addRow(Vector<String> row) {
		data.add(row);
		FileUtil.printVector(data);
		fireTableDataChanged();
	}

	/**
	 * 删除一行
	 * 
	 * @param row
	 */
	public void upordown(int index, int upordown) {
		data = CollectionUtil.moveElement(data, index, upordown);
		FileUtil.printVector(data);
		// FileUtil.writeVector(data);
		fireTableDataChanged();
		// fireTableStructureChanged();
	}

	public void delRow(int row) {
		data.removeElementAt(row);
		FileUtil.printVector(data);
		// FileUtil.writeVector(data);
		fireTableRowsDeleted(row, row);
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return COLUMNS.length;
	}

	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	public Object getValueAt(int row, int column) {
		// return data.get(row).size() > column ? data.get(row).get(column) : (column + " - " + row);
		return data.get(row).size() > column ? data.get(row).get(column) : "0";
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for each cell. If we didn't implement this method, then the last column would contain text ("true"/"false"), rather than a check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		/*String col0 = data.get(row).get(0);
		if (col == 0) {
			return true;
		} else {
			// 单元格状态
			
			 * if (col0.equals("校检位")) { return false; } else
			 
			if (col0.equals("随机数")) {
				if (col != 1) {
					return false;
				}
			} else if (col0.equals("固定值")) {
				if (col == 3) {
					return false;
				}
			}*/
			return true;
		//}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		/*if (row >= data.size()) {
			fireTableDataChanged();
			return;
		}*/
		/*String col0 = data.get(row).get(0);
		String col1 = data.get(row).get(1);
		String col2 = data.get(row).get(2);

		String tempValue = String.valueOf(value).trim();
		if (!StringUtils.hasText(tempValue)) {
			return;
		}
		if (col == 0) {
			if (tempValue.equals("随机数")) {
				data.get(row).setElementAt("1", 1);
				data.get(row).setElementAt("----", 2);
				data.get(row).setElementAt("----", 3);
			} else if (tempValue.equals("固定值")) {
				data.get(row).setElementAt("1", 1);
				data.get(row).setElementAt("0", 2);
				data.get(row).setElementAt("----", 3);
			} else if (tempValue.equals("动态增长数")) {
				data.get(row).setElementAt("1", 1);
				data.get(row).setElementAt("1", 2);
				data.get(row).setElementAt("9", 3);
			}
		} else if (col == 1) {
			BigInteger bigInteger = new BigInteger(tempValue);
			if (col0.equals("固定值")) {
				if (!StringUtil.isNumeric(tempValue) || bigInteger.longValue() > 50 || bigInteger.longValue() <= 0) {
					JOptionPane.showMessageDialog(null, "只能输入1-50的数字！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
			}
			if (col0.equals("动态增长数") || col0.equals("随机数")) {
				if (!StringUtil.isNumeric(tempValue) || bigInteger.longValue() > 9 || bigInteger.longValue() <= 0) {
					JOptionPane.showMessageDialog(null, "只能输入1-9的数字！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
			}
		} else if (col == 2) {// "固定值", "动态增长数", "校检位", "随机数"
			if (col0.equals("固定值")) {
				int count = Integer.valueOf(col1);
				if (tempValue.length() != count) {
					JOptionPane.showMessageDialog(null, "输入数据长度错误！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
				if (!StringUtil.isNumericOrLetter(tempValue)) {
					JOptionPane.showMessageDialog(null, "只能输入数字和字母！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
			}
			if (col0.equals("动态增长数")) {
				int count = Integer.valueOf(col1);
				if (!StringUtil.isNumeric(tempValue)) {
					JOptionPane.showMessageDialog(null, "只能输入数字！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
				if (tempValue.length() > count || tempValue.length() <= 0) {
					JOptionPane.showMessageDialog(null, "输入数据长度错误！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
			}

		} else if (col == 3) {
			if (col0.equals("动态增长数")) {
				if (!StringUtil.isNumeric(tempValue)) {
					JOptionPane.showMessageDialog(null, "只能输入数字！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
				int count = Integer.valueOf(col1);
				if (tempValue.length() != count) {
					JOptionPane.showMessageDialog(null, "输入数据长度错误！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}

				if (Integer.valueOf(tempValue) <= Integer.valueOf(col2)) {
					JOptionPane.showMessageDialog(null, "结束值应大于起始值！", "消息", JOptionPane.ERROR_MESSAGE);
					// errorCode=row+1;
					// return;
				}
			}
		}*/
		data.get(row).setElementAt(value.toString(), col);
		// FileUtil.writeVector(data);
		// 打印log
		FileUtil.printVector(data);
		fireTableCellUpdated(row, col);
	}

}