package com.echeloneditor;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class TestDemo extends JFrame {
	public Point point = new Point(0, 0); // 坐标点

	LayerPanel lPanel = new LayerPanel();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestDemo() {
		ImageIcon imageIcon = new ImageIcon(System.getProperty("user.dir") + "/hao.png");
		LayeredPanel mainPanel = new LayeredPanel(imageIcon);
		// this.setUndecorated(true);
		this.setTitle("TWaver Layout Demos");
		JLayeredPane jlp = this.getLayeredPane();

		jlp.add(mainPanel, new Integer(600));
		jlp.add(lPanel, new Integer(900));


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024, 768);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TestDemo().setVisible(true);
			}
		});
	}
}
