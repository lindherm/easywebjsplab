package com.watchdata.cardcheck.panel;

import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.watchdata.cardcheck.dao.IAppInfoDao;
import com.watchdata.cardcheck.dao.pojo.AppInfo;
import com.watchdata.cardcheck.utils.PropertiesManager;

public class AIDConfigTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private PropertiesManager pm = new PropertiesManager();
	private final String[] COLUMNS = new String[] {
			pm.getString("mv.aidconfig.aidtitle"),
			pm.getString("mv.aidconfig.discriptiontitle"),
			pm.getString("mv.aidconfig.versiontitle") };
	private Vector<Vector<String>> data = getAppListFromDB();

	public static Vector<Vector<String>> getAppListFromDB() {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		IAppInfoDao appinfodao;
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"classpath:applicationContext.xml");
		appinfodao = (IAppInfoDao) ctx.getBean("appInfoDao");

		final List<AppInfo> appList = appinfodao.findAppInfo();
		for (int i = 0; i < appList.size(); i++) {
			Vector<String> ruleList = new Vector<String>();
			ruleList.add(appList.get(i).getAid());
			ruleList.add(appList.get(i).getDscrpt());
			ruleList.add(appList.get(i).getVersion());
			data.add(ruleList);
		}
		return data;
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
		fireTableDataChanged();
	}

	/**
	 * 删除一行
	 * 
	 * @param row
	 */
	public void upordown(int index, int upordown) {

		fireTableDataChanged();
	}

	public void delRow(int row) {

		data.removeElementAt(row);
		fireTableRowsDeleted(row, row);
	}

	public int getColumnCount() {
		return COLUMNS.length;
	}

	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than a check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return data.get(row).size() > column ? data.get(row).get(column) : "0";
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;

	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (row > data.size()) {
			fireTableDataChanged();
			return;
		}

		String tempValue = String.valueOf(value).trim();
		data.get(row).setElementAt(tempValue, col);
		fireTableCellUpdated(row, col);
	}
}