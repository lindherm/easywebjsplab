package com.gerenhua.tool.panel;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.gerenhua.tool.tlv.TLV;
import com.gerenhua.tool.tlv.TLVList;
import com.gerenhua.tool.utils.CollectionUtil;
import com.gerenhua.tool.utils.FileUtil;
import com.gerenhua.tool.utils.StringUtil;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDByteUtil;
import com.watchdata.commons.lang.WDStringUtil;

public class ConfigTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String[] COLUMNS = new String[] { "字段名", "数据类型", "起始索引（前置）", "结束索引（LEN[长度]）", "解密/算法", "转码一", "转码二", "值" };
	// private Vector<Vector<String>> data = FileUtil.getConfigFromFile();
	private Vector<Vector<String>> data = new Vector<Vector<String>>();

	private TextPaneMenu logTextArea;
	private String lineSep = System.getProperty("line.separator");

	public ConfigTableModel(TextPaneMenu logTextArea) {
		this.logTextArea = logTextArea;
	}

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
	
	public void delRows(int[] rows) {
		for (int i = rows.length-1; i >=0; i--) {
			data.removeElementAt(rows[i]);
		}
		FileUtil.printVector(data);
		// FileUtil.writeVector(data);
		Arrays.sort(rows);
		fireTableRowsDeleted(rows[0], rows[rows.length-1]);
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
		return true;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		/*
		 * if (row >= data.size()) { fireTableDataChanged(); return; }
		 */
		String col0 = data.get(row).get(0);
		String col1 = data.get(row).get(1);
		String col2 = data.get(row).get(2);
		String col3 = data.get(row).get(3);
		String col4 = data.get(row).get(4);
		String col5 = data.get(row).get(5);
		String col6 = data.get(row).get(6);
		String col7 = data.get(row).get(7);

		String tempValue = String.valueOf(value).trim();
		if (WDAssert.isEmpty(tempValue)) {
			return;
		}
		if (col == 3) {
			if (!StringUtil.isNumeric(col2)) {
				JOptionPane.showMessageDialog(null, "只能输入数字！", "消息", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!StringUtil.isNumeric(tempValue)) {
				JOptionPane.showMessageDialog(null, "只能输入数字！", "消息", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (Integer.valueOf(tempValue) <= Integer.valueOf(col2)) {
				JOptionPane.showMessageDialog(null, "结束值应大于起始值！", "消息", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (col1.equals("定长记录")) {
				int startPos = Integer.parseInt(col2);
				int endPos = Integer.parseInt(tempValue);

				String target = logTextArea.getText();
				target = target.replaceAll(lineSep, "");
				target = target.substring(startPos * 2, endPos * 2).trim();
				logTextArea.setText(StringUtil.getRemainBytes(startPos, endPos, logTextArea.getText()));
				data.get(row).setElementAt(target, col + 4);
			} else if (col1.equals("变长记录[DGI]")) {
				int startPos = Integer.parseInt(col2);
				int endPos = Integer.parseInt(tempValue);

				String target = logTextArea.getText();
				target = target.replaceAll(lineSep, "");
				target = target.substring(startPos * 2, endPos * 2).trim();

				byte[] bytes = WDByteUtil.HEX2Bytes(target);
				TLVList tlvList = new TLVList(bytes, TLV.EMV);
				for (TLV tlv : tlvList.tlvList) {
					TLVList dgiList = new TLVList(tlv.getValue(), TLV.DGI);
					TLV dgi = dgiList.index(0);
					String dgiName = WDStringUtil.paddingHeadZero(Integer.toHexString(dgi.getTag()), 4);
					Vector<String> vector = new Vector<String>();
					vector.add(dgiName);
					vector.add("变长记录[DGI]");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add(WDByteUtil.bytes2HEX(dgi.getValue()));
					addRow(vector);

					target = logTextArea.getText();
					target = target.replaceAll(lineSep, "");

					logTextArea.setText(StringUtil.getRemainBytes(startPos, startPos + tlv.size, target));
				}

			}else if (col1.equals("变长记录[DGI2]")) {
				int startPos = Integer.parseInt(col2);
				int endPos = Integer.parseInt(tempValue);

				String target = logTextArea.getText();
				target = target.replaceAll(lineSep, "");
				target = target.substring(startPos * 2, endPos * 2).trim();

				byte[] bytes = WDByteUtil.HEX2Bytes(target);
				TLVList tlvList = new TLVList(bytes, TLV.DGI2);
				for (TLV tlv : tlvList.tlvList) {
					String dgiName = WDStringUtil.paddingHeadZero(Integer.toHexString(tlv.getTag()), 4);
					Vector<String> vector = new Vector<String>();
					vector.add(dgiName);
					vector.add("变长记录[DGI2]");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add("----");
					vector.add(WDByteUtil.bytes2HEX(tlv.getValue()));
					addRow(vector);

					target = logTextArea.getText();
					target = target.replaceAll(lineSep, "");

					logTextArea.setText(StringUtil.getRemainBytes(startPos, startPos + tlv.size, target));
				}

			}
		}
		data.get(row).setElementAt(value.toString(), col);
		// FileUtil.writeVector(data);
		// 打印log
		FileUtil.printVector(data);
		fireTableCellUpdated(row, col);
		fireTableCellUpdated(row, col + 4);
	}

}