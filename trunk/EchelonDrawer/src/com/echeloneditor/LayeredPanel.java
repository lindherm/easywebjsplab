package com.echeloneditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class LayeredPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SimplePopupMenu simplePopupMenu=null;
	
	public String charText="";
	public Font font=null;
	
	
	public Point point = new Point(0, 0); // 坐标点
	
	public int layerType = 0;
	
	public static final int LAYERED_TYPE_IMAGE=0;
	public static final int LAYERED_TYPE_CHAR=1;
	public static final int LAYERED_TYPE_LINE=2;

	int x1, y1;

	private JLabel northWest = null;

	private JLabel north = null;

	private JLabel northEast = null;

	private JLabel east = null;

	private JLabel southEast = null;

	private JLabel south = null;

	private JLabel southWest = null;

	private JLabel west = null;

	ImageIcon imageIcon = null;

	boolean isSelected = false;

	public LayeredPanel(ImageIcon imageIcon) {
		layerType = LAYERED_TYPE_IMAGE;
		this.imageIcon = imageIcon;
		int imageWidth = imageIcon.getIconWidth();
		int imageHeight = imageIcon.getIconHeight();

		if (imageIcon.getIconWidth() > 800) {
			imageWidth = 800;
		}
		if (imageIcon.getIconHeight() > 600) {
			imageHeight = 600;
		}
		this.setBounds(0, 50, imageWidth, imageHeight);

		x1 = this.getWidth();
		y1 = this.getHeight();
		
		//创建模块边框
		createPanelBorder();
	}
	public LayeredPanel(String charText,Font font) {
		layerType = LAYERED_TYPE_CHAR;
		this.charText=charText;
		this.font=font;
		FontMetrics fontMetrics=this.getFontMetrics(font);
		
		int charWidth=fontMetrics.charWidth('H');
		int charHeight=fontMetrics.getHeight();
		
		int strWidth=charWidth*charText.getBytes().length;
		
		
		this.setBounds(50, 50, strWidth+10, charHeight+10);

		x1 = this.getWidth();
		y1 = this.getHeight();
		
		//创建模块边框
		createPanelBorder();
	}
	/**
	 * 创建模块边框
	 */
	public void createPanelBorder(){
		northWest = new JLabel();
		northWest.setText("");
		northWest.setBorder(new LineBorder(Color.BLACK, 1));
		northWest.addMouseListener(this);
		northWest.addMouseMotionListener(this);
		northWest.setVisible(false);

		north = new JLabel();
		north.setText("");
		north.setBorder(new LineBorder(Color.BLACK, 1));
		north.addMouseListener(this);
		north.addMouseMotionListener(this);
		north.setVisible(false);

		northEast = new JLabel();
		northEast.setText("");
		northEast.setBorder(new LineBorder(Color.BLACK, 1));
		northEast.addMouseListener(this);
		northEast.addMouseMotionListener(this);
		northEast.setVisible(false);

		east = new JLabel();
		east.setText("");
		east.setBorder(new LineBorder(Color.BLACK, 1));
		east.addMouseListener(this);
		east.addMouseMotionListener(this);
		east.setVisible(false);

		southEast = new JLabel();
		southEast.setText("");
		southEast.setBorder(new LineBorder(Color.BLACK, 1));
		southEast.addMouseListener(this);
		southEast.addMouseMotionListener(this);
		southEast.setVisible(false);

		south = new JLabel();
		south.setText("");
		south.setBorder(new LineBorder(Color.BLACK, 1));
		south.addMouseListener(this);
		south.addMouseMotionListener(this);
		south.setVisible(false);

		southWest = new JLabel();
		southWest.setText("");
		southWest.setBorder(new LineBorder(Color.BLACK, 1));
		southWest.addMouseListener(this);
		southWest.addMouseMotionListener(this);
		southWest.setVisible(false);

		west = new JLabel();
		west.setText("");
		west.setBorder(new LineBorder(Color.BLACK, 1));
		west.addMouseListener(this);
		west.addMouseMotionListener(this);
		west.setVisible(false);

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
		this.addKeyListener(this);

		this.add(northWest, null);
		this.add(north, null);
		this.add(northEast, null);
		this.add(east, null);
		this.add(southEast, null);
		this.add(south, null);
		this.add(southWest, null);
		this.add(west, null);
	}

	public void mouseClicked(MouseEvent e) {
		Component component = this.getParent();
		if (component instanceof JLayeredPane) {
			JLayeredPane jlp = ((JLayeredPane) component);
			jlp.moveToFront(this);
		}
		isSelected = true;
		northWest.setVisible(true);
		north.setVisible(true);
		northEast.setVisible(true);
		east.setVisible(true);
		southEast.setVisible(true);
		south.setVisible(true);
		southWest.setVisible(true);
		west.setVisible(true);
		this.repaint();
		this.requestFocus();
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		point = SwingUtilities.convertPoint(this, e.getPoint(), this.getParent()); // 得到当前坐标点
		this.requestFocus();
	}

	public void mouseReleased(MouseEvent e) {
		if (isSelected) {
			if (getCursor().getType() != Cursor.DEFAULT_CURSOR) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			Component component = (Component) e.getSource();
			Component parentComponent = SwingUtilities.getRoot(component);
			EchelonDrawer echelonDrawer = (EchelonDrawer) parentComponent;
			simplePopupMenu=new SimplePopupMenu(echelonDrawer,(LayeredPanel)component);
			simplePopupMenu.show(this, e.getX(), e.getY());
		}
	}

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
			}
		}
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

		if (isSelected) {
			int type = getCursor().getType();
			int x, y, w, h;
			x = this.getX();
			y = this.getY();
			w = this.getWidth();
			h = this.getHeight();
			switch (type) {
			case Cursor.NW_RESIZE_CURSOR:
				if (w - e.getX() > 20 && h - e.getY() > 20) {
					w = w - e.getX();
					h = h - e.getY();
					this.setBounds(x + e.getX(), y + e.getY(), w, h);
				}
				break;
			case Cursor.N_RESIZE_CURSOR:
				if (h - e.getY() > 20) {
					h = h - e.getY();
					this.setBounds(x, y + e.getY(), w, h);
				}
				break;
			case Cursor.NE_RESIZE_CURSOR:
				if (w + e.getX() > 20 && h - e.getY() > 20) {
					w = w + e.getX();
					h = h - e.getY();
					this.setBounds(x, y + e.getY(), w, h);
				}
				break;
			case Cursor.E_RESIZE_CURSOR:
				if (w + e.getX() > 20) {
					w = w + e.getX();
					this.setBounds(x, y, w, h);
				}
				break;
			case Cursor.SE_RESIZE_CURSOR:
				if (w + e.getX() > 20 && h + e.getY() > 20) {
					w = w + e.getX();
					h = h + e.getY();
					this.setBounds(x, y, w, h);
				}
				break;
			case Cursor.S_RESIZE_CURSOR:
				if (h + e.getY() > 20) {
					h = h + e.getY();
					this.setBounds(x, y, w, h);
				}
				break;
			case Cursor.SW_RESIZE_CURSOR:
				if (w - e.getX() > 20 && h + e.getY() > 20) {
					w = w - e.getX();
					h = h + e.getY();
					this.setBounds(x + e.getX(), y, w, h);
				}
				break;
			case Cursor.W_RESIZE_CURSOR:
				if (w - e.getX() > 20) {
					w = w - e.getX();
					this.setBounds(x + e.getX(), y, w, h);
				}
				break;
			case Cursor.DEFAULT_CURSOR:
				if (e.getComponent() == this) {
					Point newPoint = SwingUtilities.convertPoint(this, e.getPoint(), this.getParent()); // 转换坐标系统
					this.setLocation(this.getX() + (newPoint.x - point.x), this.getY() + (newPoint.y - point.y)); // 设置标签图片的新位置
					point = newPoint; // 更改坐标点
				}
			default:
				break;
			}

			northWest.setBounds(0, 0, 5, 5);
			north.setBounds((w - 3) / 2, 0, 5, 5);
			northEast.setBounds(w - 5, 0, 5, 5);
			east.setBounds(w - 5, (h - 3) / 2, 5, 5);
			southEast.setBounds(w - 5, h - 5, 5, 5);
			south.setBounds((w - 3) / 2, h - 5, 5, 5);
			southWest.setBounds(0, h - 5, 5, 5);
			west.setBounds(0, (h - 3) / 2, 5, 5);

			this.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g) {
		super.paint(g);
		if (layerType == LayeredPanel.LAYERED_TYPE_IMAGE) {
			g.drawImage(this.imageIcon.getImage(), 5, 5, this.getWidth() - 10, this.getHeight() - 10, this);
		} else if (layerType == LayeredPanel.LAYERED_TYPE_CHAR) {
			g.setFont(font);
			g.drawString(charText, 5, 25);
		}

	}

	@Override
	public void keyPressed(KeyEvent keyevent) {
		if (keyevent.getComponent() == this) {
			int keyCode = keyevent.getKeyCode();
			Point newPoint = new Point();
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				newPoint = new Point((int) point.getX() - 1, (int) point.getY());
				break;
			case KeyEvent.VK_RIGHT:
				newPoint = new Point((int) point.getX() + 1, (int) point.getY());
				break;
			case KeyEvent.VK_UP:
				newPoint = new Point((int) point.getX(), (int) point.getY() - 1);
				break;
			case KeyEvent.VK_DOWN:
				newPoint = new Point((int) point.getX(), (int) point.getY() + 1);
				break;

			default:
				newPoint = point;
				break;
			}
			this.setLocation(this.getX() + (newPoint.x - point.x), this.getY() + (newPoint.y - point.y)); // 设置标签图片的新位置
			point = newPoint; // 更改坐标点
		}
		
	}

	@Override
	public void keyReleased(KeyEvent keyevent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent keyevent) {
		// TODO Auto-generated method stub

	}
	public void hideLayer(){
		if (isSelected) {
			northWest.setVisible(false);
			north.setVisible(false);
			northEast.setVisible(false);
			east.setVisible(false);
			southEast.setVisible(false);
			south.setVisible(false);
			southWest.setVisible(false);
			west.setVisible(false);
			this.repaint();
		}
	}
}