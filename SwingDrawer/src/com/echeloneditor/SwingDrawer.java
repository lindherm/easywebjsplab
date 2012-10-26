package com.echeloneditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SwingDrawer extends JFrame implements MouseListener{
	static SwingDrawer sd = new SwingDrawer();
	static int index = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingDrawer() {
		// this.setUndecorated(true);
		this.setTitle("SwingDrawer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton button = new JButton("打开图片");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				JFileChooser fileChooser=new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(false);
				FileFilter filter = new FileNameExtensionFilter("image file", "jpg", "jpeg","png","bmp","gif");
				fileChooser.addChoosableFileFilter(filter);

				int ret=fileChooser.showOpenDialog(sd);
				if (ret==JFileChooser.APPROVE_OPTION) {
					File file=fileChooser.getSelectedFile();
					ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
					LayeredPanel layeredPanel = new LayeredPanel(imageIcon);
					JLayeredPane jlp = sd.getLayeredPane();
					jlp.add(layeredPanel, index++);
				}
			}
		});
		toolBar.add(button);

		JButton button_1 = new JButton("创建文字");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				JLabel cLabel = new JLabel("你好");
				LayeredPanel layeredPanel = new LayeredPanel(cLabel);
				JLayeredPane jlp = sd.getLayeredPane();
				jlp.add(layeredPanel, index++);
			}
		});
		toolBar.add(button_1);

		JButton button_2 = new JButton("画横线");
		toolBar.add(button_2);

		JButton button_3 = new JButton("自定义图像");
		toolBar.add(button_3);
		
		this.addMouseListener(this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				sd.setVisible(true);
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		Component component=mouseevent.getComponent();
		if (component instanceof SwingDrawer) {
			SwingDrawer swingDrawer=(SwingDrawer)component;
			Component[] component2=swingDrawer.getLayeredPane().getComponents();
			for (Component component3 : component2) {
				if (component3 instanceof LayeredPanel) {
					((LayeredPanel)component3).hideLayer();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		
	}
}
