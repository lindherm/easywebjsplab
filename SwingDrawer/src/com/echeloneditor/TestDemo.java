package com.echeloneditor;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TestDemo extends JFrame {  
	public Point point=new Point(0,0); //坐标点
	public Point point2=new Point(0,0); //坐标点

	LayerPanel lPanel=new LayerPanel();
	
	LayerPanel lPanel2=new LayerPanel();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestDemo(){
		
		//this.setUndecorated(true);
    	this.setTitle("TWaver Layout Demos");
    	JLayeredPane jlp=this.getLayeredPane();
    	
    	
    	JLabel jLabel=new JLabel("中国");
    	JLabel jLabel2=new JLabel("dddd");
    	jLabel.setBackground(Color.BLUE);
    	jlp.add(jLabel, new Integer(300));
    	jlp.add(jLabel2, new Integer(100));
    	jlp.add(lPanel, new Integer(900));
    	jlp.add(lPanel2, new Integer(9009));
    	jLabel.setBounds(new Rectangle(100, 100, 100, 100));
    	jLabel.setVisible(true);
    	jLabel2.setBounds(new Rectangle(50, 50, 100, 100));
    	jLabel2.setVisible(true);
    	
    	lPanel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				 point=SwingUtilities.convertPoint(lPanel,e.getPoint(),lPanel.getParent()); //得到当前坐标点
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	lPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				// TODO Auto-generated method stub
				//System.out.println("move:"+e.getX()+"|"+e.getY());
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				 Point newPoint=SwingUtilities.convertPoint(lPanel,e.getPoint(),lPanel.getParent()); //转换坐标系统
				 lPanel.setLocation(lPanel.getX()+(newPoint.x-point.x),lPanel.getY()+(newPoint.y-point.y)); //设置标签图片的新位置
		         point=newPoint; //更改坐标点
			}
		});
    	
    	
lPanel2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				 point2=SwingUtilities.convertPoint(lPanel2,e.getPoint(),lPanel2.getParent()); //得到当前坐标点
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	lPanel2.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				// TODO Auto-generated method stub
				//System.out.println("move:"+e.getX()+"|"+e.getY());
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				 Point newPoint=SwingUtilities.convertPoint(lPanel2,e.getPoint(),lPanel2.getParent()); //转换坐标系统
				 lPanel2.setLocation(lPanel2.getX()+(newPoint.x-point2.x),lPanel2.getY()+(newPoint.y-point2.y)); //设置标签图片的新位置
		         point2=newPoint; //更改坐标点
			}
		});
    	
    	//Container container=this.getContentPane();
    	//container.setLayout(new BorderLayout());
    	//container.add(new LayerDemo(), BorderLayout.CENTER);    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setSize(1024, 768);
    	//this.setLocationRelativeTo(null);
    	//TWaverUtil.centerWindow(this);		
	}
	
	public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			new TestDemo().setVisible(true);
    		}     
    	}); 
	}
}
