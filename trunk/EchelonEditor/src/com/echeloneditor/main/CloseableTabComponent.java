package com.echeloneditor.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.vo.StatusObject;

public class CloseableTabComponent extends JPanel {
	private static final long serialVersionUID = 1L;

	public String filePath = "";
	public String fileEncode = "UTF-8";
	public String fileSzie = "0";
	public boolean modify = false;

	private static ImageIcon closerImage = ImageHelper.loadImage("closer.gif");
	private static ImageIcon closerRolloverImage = ImageHelper.loadImage("closer_rollover.gif");
	private static ImageIcon closerPressedImage = ImageHelper.loadImage("closer_pressed.gif");
	public JLabel titleLabel = null;
	private JButton closeButton = null;
	private JTabbedPane tabbedPane = null;

	public CloseableTabComponent(JTabbedPane aTabbedPane, final StatusObject statusObject) {
		super(new BorderLayout());
		tabbedPane = aTabbedPane;

		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));

		titleLabel = new JLabel("New Panel  ");
		titleLabel.setOpaque(false);

		Dimension closerD = new Dimension(closerImage.getIconWidth(), closerImage.getIconHeight());

		closeButton = new JButton(closerImage);
		closeButton.setRolloverIcon(closerRolloverImage);
		closeButton.setPressedIcon(closerPressedImage);
		closeButton.setBorderPainted(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setFocusPainted(false);
		closeButton.setRolloverEnabled(true);
		closeButton.setOpaque(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setPreferredSize(closerD);
		closeButton.setSize(closerD);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});

		add(titleLabel, BorderLayout.CENTER);
		add(closeButton, BorderLayout.EAST);
	}

	public boolean isModify() {
		return modify;
	}

	public void setModify(boolean modify) {
		this.modify = modify;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileEncode() {
		return fileEncode;
	}

	public void setFileEncode(String fileEncode) {
		this.fileEncode = fileEncode;
	}

	public String getFileSzie() {
		return fileSzie;
	}

	public void setFileSzie(String fileSzie) {
		this.fileSzie = fileSzie;
	}
}
