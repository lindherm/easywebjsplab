package com.echeloneditor;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import twaver.TWaverUtil;

public class SwingDrawer extends JFrame {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingDrawer(){
		//this.setUndecorated(true);
    	this.setTitle("TWaver Layout Demos");
    	Container container=this.getContentPane();
    	container.setLayout(new BorderLayout());
    	container.add(new LayerDemo(), BorderLayout.CENTER);    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(1024, 768);
    	//this.setLocationRelativeTo(null);
    	TWaverUtil.centerWindow(this);		
	}
	
	public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			new SwingDrawer().setVisible(true);
    		}     
    	}); 
	}
}
