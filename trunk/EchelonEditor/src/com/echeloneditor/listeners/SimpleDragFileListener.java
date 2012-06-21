package com.echeloneditor.listeners;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.JTabbedPane;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.vo.StatusObject;

public class SimpleDragFileListener implements DropTargetListener {

	public JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public FileHander fileHander;

	public SimpleDragFileListener(JTabbedPane tabbedPane, StatusObject statusObject) {
		this.tabbedPane = tabbedPane;
		this.statusObject = statusObject;
		fileHander = new FileHander(this.tabbedPane, this.statusObject);
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub
		DataFlavor[] dataFlavors = dtde.getCurrentDataFlavors();
		if (dataFlavors[0].match(DataFlavor.javaFileListFlavor)) {
			try {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				Transferable tr = dtde.getTransferable();
				Object obj = tr.getTransferData(DataFlavor.javaFileListFlavor);
				List<File> files = (List<File>) obj;
				for (File file : files) {
					fileHander.openFileWithFilePath(file.getPath());
				}
				dtde.dropComplete(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
