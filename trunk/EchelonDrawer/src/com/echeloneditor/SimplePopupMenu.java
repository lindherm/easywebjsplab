package com.echeloneditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class SimplePopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPopupMenu popupMenu = new JPopupMenu();
	public EchelonDrawer echelonDrawer=null;
	public LayeredPanel layeredPanel=null;

	SimplePopupMenu(final EchelonDrawer echelonDrawer,final LayeredPanel layeredPanel) {
		this.echelonDrawer=echelonDrawer;
		this.layeredPanel=layeredPanel;
		JMenuItem menuItem = new JMenuItem("删除");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				echelonDrawer.getLayeredPane().remove(layeredPanel);
				System.out.println();
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		add(menuItem);
		
		JSeparator separator = new JSeparator();
		add(separator);
		
	}

}
