package yuki;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

public class ControlFrame extends JWindow implements ActionListener {
	private static final long serialVersionUID = -7266725714124050902L;
	private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	
	private JMenuBar menubar = new JMenuBar();
	private JMenu setBubble = new JMenu("��������");
	private JMenu setEnvironment = new JMenu("���û���");
	private JMenu help = new JMenu("����");
	private JMenuItem exit = new JMenuItem("�˳�");
	private JMenuItem emptyBubbles = new JMenuItem("�������");
	private JMenuItem addBubble = new JMenuItem("���һ������");
	private JMenuItem addBubbles = new JMenuItem("����������");
	private JMenuItem setGravity = new JMenuItem("�����������ٶ�");
	private JMenuItem about = new JMenuItem("���ڡ����ݡ�");
	
	private MotionFrame motionFrame;

	private Rectangle rct = new Rectangle(SCREEN_WIDTH / 2 - 105, 0, 210, 1);
	
	public ControlFrame(JFrame frame){
		
		this.motionFrame = (MotionFrame) frame;
		
		menubar.add(setBubble);
		menubar.add(setEnvironment);
		menubar.add(help);
		menubar.add(exit);

		setBubble.add(emptyBubbles);
		setBubble.add(addBubble);
		setBubble.add(addBubbles);
		
		setEnvironment.add(setGravity);
		help.add(about);
		
		emptyBubbles.addActionListener(this);
		addBubble.addActionListener(this);
		addBubbles.addActionListener(this);
		setGravity.addActionListener(this);
		about.addActionListener(this);
		exit.addActionListener(this);
		
		this.add(menubar);
		this.setLocation(SCREEN_WIDTH / 2 - 105, -24);
		this.setAlwaysOnTop(true);
		this.pack();
		this.setVisible(true);

		new Thread(){
			Point p = ControlFrame.this.getLocation();
			public void run() {
				while (true) {
					if (p.y != 0 && rct.contains(MouseInfo.getPointerInfo().getLocation())) {
						p.y++;
						rct.height++;
					}
					if (p.y != -24 && !rct.contains(MouseInfo.getPointerInfo().getLocation())) {
							p.y--;
							rct.height--;
					}
					ControlFrame.this.setLocation(p);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emptyBubbles) {
			motionFrame.getCanvas().emptyBubbles();
		} else if (e.getSource() == addBubble) {
			new AddBubbleDialog(motionFrame);
		} else if (e.getSource() == addBubbles) {
			String st = JOptionPane.showInputDialog(motionFrame, "���������ݸ�����", "����������", JOptionPane.INFORMATION_MESSAGE);
			if (st != null) {
				motionFrame.getCanvas().addBubbles(Integer.parseInt(st));
			}
		} else if (e.getSource() == setGravity) {
			new ChangeGravityDialog(motionFrame);
		} else if (e.getSource() == about) {
			new AboutDialog(motionFrame);
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}
}
