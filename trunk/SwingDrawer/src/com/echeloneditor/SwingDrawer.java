package com.echeloneditor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class SwingDrawer extends JFrame {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingDrawer(){
		//this.setUndecorated(true);
		this.setTitle("SwingDrawer");
		ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/hao.png");
		ImageLayerPanel mainPanel = new ImageLayerPanel(imageIcon);
		// this.setUndecorated(true);
		JLayeredPane jlp = this.getLayeredPane();

		jlp.add(mainPanel, new Integer(1));

    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(800, 600);
    	this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			new SwingDrawer().setVisible(true);
    		}     
    	}); 
	}
}
