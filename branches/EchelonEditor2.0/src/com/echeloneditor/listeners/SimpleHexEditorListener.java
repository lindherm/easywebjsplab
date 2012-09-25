package com.echeloneditor.listeners;

import javax.swing.JTabbedPane;

import org.fife.ui.hex.event.HexEditorEvent;
import org.fife.ui.hex.event.HexEditorListener;

import com.echeloneditor.vo.StatusObject;

public class SimpleHexEditorListener implements HexEditorListener {

	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	
	public SimpleHexEditorListener(JTabbedPane tabbedPane,StatusObject statusObject){
		this.tabbedPane=tabbedPane;
		this.statusObject=statusObject;
	}
	@Override
	public void hexBytesChanged(HexEditorEvent e) {
		// TODO Auto-generated method stub
		statusObject.getSaveBtn().setEnabled(true);
	}

}
