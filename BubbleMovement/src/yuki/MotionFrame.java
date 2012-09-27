package yuki;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

public class MotionFrame extends JFrame {
	private static final long serialVersionUID = 4597945690537561311L;
	
	private DrawCanvas canvas = new DrawCanvas();
	
	public MotionFrame(){
		new ControlFrame(this);
		this.setTitle("тк╤╞╣дещещ");
		this.getContentPane().add(canvas);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(FileUtil.getImage(true, "bubbles.png"));
		this.setVisible(true);
	}
	
	public DrawCanvas getCanvas() {
		return canvas;
	}
	
	public static void main(String[] args) {
        new MotionFrame();
	}
}
