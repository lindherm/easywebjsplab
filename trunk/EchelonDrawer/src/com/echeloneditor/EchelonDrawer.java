package com.echeloneditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.echeloneditor.utils.ImageHelper;

public class EchelonDrawer extends JFrame implements MouseListener {
	static int index = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EchelonDrawer() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(ImageHelper.loadImage("logo.png").getImage());
		this.setTitle("EchelonDrawer");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton button = new JButton("打开图片");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				//打开选择对话框
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(false);
				FileFilter filter = new FileNameExtensionFilter("image file", "jpg", "jpeg", "png", "bmp", "gif");
				fileChooser.addChoosableFileFilter(filter);
				//获取事件源的根组件
				Component component = (Component) actionevent.getSource();
				Component parentComponent = SwingUtilities.getRoot(component);

				int ret = fileChooser.showOpenDialog(parentComponent);
				if (ret == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
					LayeredPanel layeredPanel = new LayeredPanel(imageIcon);
					JLayeredPane jlp = ((EchelonDrawer) parentComponent).getLayeredPane();
					jlp.add(layeredPanel, index++);
				}
			}
		});
		toolBar.add(button);

		JButton button_1 = new JButton("创建文字");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				Component component = (Component) actionevent.getSource();
				Component parentComponent = SwingUtilities.getRoot(component);
				//JLabel cLabel = new JLabel("你好");
				LayeredPanel layeredPanel = new LayeredPanel("zhongguoren",new Font("宋体", Font.BOLD, 28));
				JLayeredPane jlp = ((EchelonDrawer) parentComponent).getLayeredPane();
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
				new EchelonDrawer().setVisible(true);
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent mouseevent) {
		// TODO Auto-generated method stub
		Component component = mouseevent.getComponent();
		if (component instanceof EchelonDrawer) {
			EchelonDrawer swingDrawer = (EchelonDrawer) component;
			Component[] component2 = swingDrawer.getLayeredPane().getComponents();
			for (Component component3 : component2) {
				if (component3 instanceof LayeredPanel) {
					((LayeredPanel) component3).hideLayer();
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
