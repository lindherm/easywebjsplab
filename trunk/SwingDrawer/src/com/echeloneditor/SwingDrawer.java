package com.echeloneditor;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingDrawer extends JFrame {  
	static SwingDrawer sd=new SwingDrawer();
	static int index=0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingDrawer(){
		//this.setUndecorated(true);
		this.setTitle("SwingDrawer");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(800, 600);
    	this.setLocationRelativeTo(null);
    	
    	JToolBar toolBar = new JToolBar();
    	getContentPane().add(toolBar, BorderLayout.NORTH);
    	
    	JButton button = new JButton("打开图片");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent actionevent) {
    			ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/hao.png");
    			LayeredPanel layeredPanel = new LayeredPanel(imageIcon);
    			JLayeredPane jlp=sd.getLayeredPane();
    			jlp.add(layeredPanel, index++);
    		}
    	});
    	toolBar.add(button);
    	
    	JButton button_1 = new JButton("创建文字");
    	button_1.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent actionevent) {
    			JLabel cLabel=new JLabel("你好");
    			LayeredPanel layeredPanel = new LayeredPanel(cLabel);
    			JLayeredPane jlp=sd.getLayeredPane();
    			jlp.add(layeredPanel, index++);
    		}
    	});
    	toolBar.add(button_1);
    	
    	JButton button_2 = new JButton("画横线");
    	toolBar.add(button_2);
    	
    	JButton button_3 = new JButton("自定义图像");
    	toolBar.add(button_3);
	}
	
	public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			sd.setVisible(true);
    		}     
    	}); 
	}
}
