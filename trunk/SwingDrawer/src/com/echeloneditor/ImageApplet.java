package com.echeloneditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ImageApplet extends JApplet {

	 private JPanel jContentPane = null;

	 private JPanel jPanel = null;

	 

	 ImageIcon imageIcon = new ImageIcon("D:/test.jpg");

	 /**
	  * This is the default constructor
	  */
	 public ImageApplet() {
	  super();
	 }

	 /**
	  * This method initializes this
	  * 
	  * @return void
	  */
	 public void init() {
	  this.setSize(640, 480);
	  this.setContentPane(getJContentPane());
	 }

	 /**
	  * This method initializes jContentPane
	  * 
	  * @return javax.swing.JPanel
	  */
	 private JPanel getJContentPane() {
	  if (jContentPane == null) {
	   jContentPane = new JPanel();
	   jContentPane.setLayout(null);
	   jContentPane.add(new MainPanel(imageIcon), null);
	  }
	  return jContentPane;
	 }

	 /**
	  * This method initializes jPanel
	  * 
	  * @return javax.swing.JPanel
	  */
	 class MainPanel extends JPanel implements MouseListener,
	   MouseMotionListener {
	  int x1, y1;
	//用来表示向东、南、西、北、东、南、东、北、西南、西北八个方向缩放点
	  private JLabel northWest = null;

	  private JLabel north = null;

	  private JLabel northEast = null;

	  private JLabel east = null;

	  private JLabel southEast = null;

	  private JLabel south = null;

	  private JLabel southWest = null;

	  private JLabel west = null;
	//图片
	  ImageIcon imageIcon = null;
	//是否可缩放标记  
	  boolean isSelected = false;
	  
	  public MainPanel(ImageIcon imageIcon) {
	   this.imageIcon = imageIcon;
	   this.setBounds(256,107,400,300);
	   x1 = this.getWidth();
	   y1 = this.getHeight();
	   //初始化东、南、西、北、东、南、东、北、西南、西北八个方向缩放点
	   northWest = new JLabel();
	   northWest.setText("");
	   northWest.setBorder(new LineBorder(Color.WHITE, 1));
	   northWest.addMouseListener(this);
	   northWest.addMouseMotionListener(this);
	   northWest.setVisible(false);
	   
	   north = new JLabel();
	   north.setText("");
	   north.setBorder(new LineBorder(Color.WHITE, 1));
	   north.addMouseListener(this);
	   north.addMouseMotionListener(this);
	   north.setVisible(false);
	   
	   northEast = new JLabel();
	   northEast.setText("");
	   northEast.setBorder(new LineBorder(Color.WHITE, 1));
	   northEast.addMouseListener(this);
	   northEast.addMouseMotionListener(this);
	   northEast.setVisible(false);

	   east = new JLabel();
	   east.setText("");
	   east.setBorder(new LineBorder(Color.WHITE, 1));
	   east.addMouseListener(this);
	   east.addMouseMotionListener(this);
	   east.setVisible(false);

	   southEast = new JLabel();
	   southEast.setText("");
	   southEast.setBorder(new LineBorder(Color.WHITE, 1));
	   southEast.addMouseListener(this);
	   southEast.addMouseMotionListener(this);
	   southEast.setVisible(false);

	   south = new JLabel();
	   south.setText("");
	   south.setBorder(new LineBorder(Color.WHITE, 1));
	   south.addMouseListener(this);
	   south.addMouseMotionListener(this);
	   south.setVisible(false);

	   southWest = new JLabel();
	   southWest.setText("");
	   southWest.setBorder(new LineBorder(Color.WHITE, 1));
	   southWest.addMouseListener(this);
	   southWest.addMouseMotionListener(this);
	   southWest.setVisible(false);

	   west = new JLabel();
	   west.setText("");
	   west.setBorder(new LineBorder(Color.WHITE, 1));
	   west.addMouseListener(this);
	   west.addMouseMotionListener(this);
	   west.setVisible(false);
	//东、南、西、北、东、南、东、北、西南、西北八个方向缩放点的坐标
	   northWest.setBounds(0, 0, 5, 5);
	   north.setBounds((x1 - 3) / 2, 0, 5, 5);
	   northEast.setBounds(x1 - 5, 0, 5, 5);
	   east.setBounds(x1 - 5, (y1 - 3) / 2, 5, 5);
	   southEast.setBounds(x1 - 5, y1 - 5, 5, 5);
	   south.setBounds((x1 - 3) / 2, y1 - 5, 5, 5);
	   southWest.setBounds(0, y1 - 5, 5, 5);
	   west.setBounds(0, (y1 - 3) / 2, 5, 5);

	   this.setLayout(null);
	   this.setMinimumSize(new Dimension(20, 20));
	   this.addMouseListener(this);
	   this.addMouseMotionListener(this);
	//加入缩放点到jpanel中   
	   this.add(northWest, null);
	   this.add(north, null);
	   this.add(northEast, null);
	   this.add(east, null);
	   this.add(southEast, null);
	   this.add(south, null);
	   this.add(southWest, null);
	   this.add(west, null);
	  }
	//单击后可以进行缩放处理
	  public void mouseClicked(MouseEvent e) {
	   isSelected = true;
	   this.setBorder(new LineBorder(Color.BLACK, 4));
	   northWest.setVisible(true);
	   north.setVisible(true);
	   northEast.setVisible(true);
	   east.setVisible(true);
	   southEast.setVisible(true);
	   south.setVisible(true);
	   southWest.setVisible(true);
	   west.setVisible(true);
	   this.repaint();
	  }

	  public void mousePressed(MouseEvent e) {
	   // TODO Auto-generated method stub

	  }
	//防止鼠标在缩放时移出了方向点区域，图片的缩放不能进行
	  public void mouseReleased(MouseEvent e) {
	   if(isSelected){
	    if(getCursor().getType()!=Cursor.DEFAULT_CURSOR){
	     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    }
	   }

	  }
	//鼠标进入哪个缩放点区域
	  public void mouseEntered(MouseEvent e) {   
	   if (isSelected) {
	    if (e.getComponent() == northWest) {
	     setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
	    } else if (e.getComponent() == north) {
	     setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
	    } else if (e.getComponent() == northEast) {
	     setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
	    } else if (e.getComponent() == east) {
	     setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
	    } else if (e.getComponent() == southEast) {
	     setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
	    } else if (e.getComponent() == south) {
	     setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
	    } else if (e.getComponent() == southWest) {
	     setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
	    } else if (e.getComponent() == west) {
	     setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
	    } /*else if(e.getComponent() == this){     
	     setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    }*/
	   }
	  }

	  public void mouseExited(MouseEvent e) {
	  }
	//缩放及拖动的操作
	  public void mouseDragged(MouseEvent e) {
	   if (isSelected) {
	    int type = getCursor().getType();
	    int x, y, w, h;
	    x = this.getX();
	    y = this.getY();    
	    w = this.getWidth();
	    h = this.getHeight();
	    switch (type) {
	    case Cursor.NW_RESIZE_CURSOR://西北方向缩放
	     if(w - e.getX() >20&& h - e.getY()>20){
	     w = w - e.getX();
	     h = h - e.getY();
	       this.setBounds(x + e.getX(), y+ e.getY(), w, h);
	     }    
	     break;
	    case Cursor.N_RESIZE_CURSOR://北
	     if(h-e.getY()>20){
	     h = h - e.getY();
	     this.setBounds(x, y + e.getY(),w, h);
	     }
	     break;
	    case Cursor.NE_RESIZE_CURSOR://东北
	     if(w + e.getX()>20 && h - e.getY()>20){
	     w = w + e.getX();
	     h = h - e.getY();
	     this.setBounds(x, y + e.getY(),w, h);
	     }
	     break;
	    case Cursor.E_RESIZE_CURSOR://东
	     if(w + e.getX()>20){
	     w = w + e.getX();
	     this.setBounds(x, y, w, h);
	     }
	     break;
	    case Cursor.SE_RESIZE_CURSOR://东南
	     if(w + e.getX()>20&& h + e.getY()>20){
	     w = w + e.getX();
	     h = h + e.getY();
	     this.setBounds(x, y, w, h);
	     }
	     break;
	    case Cursor.S_RESIZE_CURSOR://南
	     if(h+e.getY()>20){
	     h = h + e.getY();
	     this.setBounds(x, y, w, h);
	     }
	     break;
	    case Cursor.SW_RESIZE_CURSOR://西南
	     if(w-e.getX()>20&& h+e.getY()>20){
	     w = w - e.getX();
	     h = h + e.getY();
	     this.setBounds(x + e.getX(), y,w, h);
	     }
	     break;
	    case Cursor.W_RESIZE_CURSOR://西
	     if(w-e.getX()>20){
	     w = w - e.getX();
	     this.setBounds(x + e.getX(), y,w, h);
	     }
	     break;
	    case Cursor.DEFAULT_CURSOR://拖动操作
	     if(e.getComponent()==this){
	     this.setBounds(x+e.getX(),y+e.getY(),w,h);
	     }
	    default:     
	     break;
	    }
	    //重定位各缩放点坐标位置
	    northWest.setBounds(0, 0, 5, 5);
	    north.setBounds((w - 3) / 2, 0, 5, 5);
	    northEast.setBounds(w - 5, 0, 5, 5);
	    east.setBounds(w - 5, (h - 3) / 2,5, 5);
	    southEast.setBounds(w - 5, h - 5, 5, 5);
	    south.setBounds((w - 3) / 2, h - 5,5, 5);
	    southWest.setBounds(0, h - 5, 5, 5);
	    west.setBounds(0, (h - 3) / 2, 5, 5);
	    //重绘
	    this.repaint();    
	   }
	  }

	  public void mouseMoved(MouseEvent e) {
	   // TODO Auto-generated method stub

	  }

	  public void paint(Graphics g) {
	   super.paint(g);
	   //绘制图片在中央
	   g.drawImage(this.imageIcon.getImage(), 5, 5, this.getWidth() - 10,
	     this.getHeight() - 10, this);
	  }
	 }
	}

