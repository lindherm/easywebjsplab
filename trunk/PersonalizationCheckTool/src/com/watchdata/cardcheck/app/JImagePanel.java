package com.watchdata.cardcheck.app;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image = null;

	public JImagePanel(String imagePath){
		readImage(imagePath);
	}

	public JImagePanel() {

	}

	public void setBackGround(String imagePath) {
		readImage(imagePath);
	}

	/**
	 * read image
	 * 
	 * @param imagePath
	 */

	public void readImage(String imagePath) {
		try {
			this.image = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}
