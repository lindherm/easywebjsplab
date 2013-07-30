package com.watchdata.cardcheck.app;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class JImagePanel extends JPanel {
private static final long serialVersionUID = 1L;
private ImageIcon image = null;


public JImagePanel(ImageIcon image) throws Exception {
  
  JImagePanelInit();
  this.image = image;
}

public void setBackGround(ImageIcon image){
	this.image = image;
}
public ImageIcon getBackGround(){
	return image;
}



private void JImagePanelInit() throws Exception {
}


protected void paintComponent(Graphics g) {
  setOpaque(true);
  super.paintComponent(g);
  Dimension d = getSize();
  setLayout(null);
  for (int x = 0; x < d.width; x += image.getIconWidth())
   for (int y = 0; y < d.height; y += image.getIconHeight())
    g.drawImage(image.getImage(), x, y, null, null);
}
}
