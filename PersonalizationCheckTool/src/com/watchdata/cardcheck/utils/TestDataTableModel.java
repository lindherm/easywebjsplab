package com.watchdata.cardcheck.utils;

import javax.swing.table.DefaultTableModel;


public class TestDataTableModel extends DefaultTableModel{
	
	@Override 
	public boolean isCellEditable(int row, int column) { 
	return false; 
	}
	
	private static final long serialVersionUID = -1775988690420597235L;
	
	private final String[] COLUMNS = new String[] { "TAG", "值", "描述", "应用类型" };
	
	public TestDataTableModel(){
		super();
		addTitle(COLUMNS);
	}
	
	/**
	 * 添加列名
	 */
	public void addTitle(String[] columns){
		for(String col:columns){
			this.addColumn(col);
		}
	}

}
