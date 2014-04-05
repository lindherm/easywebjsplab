package com.gerenhua.cardcheck.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 398058549462872103L;
	private BufferedImage image = null;

	public JImagePanel(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getBackGroudImage() {
		return image;
	}

	public void setBackGroudImage(BufferedImage image) {
		this.image = image;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}
