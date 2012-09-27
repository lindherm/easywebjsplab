package yuki;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddBubbleDialog extends JDialog {
	private static final long serialVersionUID = -5899316428342179060L;

	private DrawCanvas canvas;
	
	private JPanel panel = new JPanel();
	private JButton confirm = new JButton("确定");
	
	private JPanel panelR = new JPanel();
	private JPanel panelD = new JPanel();
	private JPanel panelV = new JPanel();

	private JLabel labelR = new JLabel("半径:");
	private JLabel labelD = new JLabel("密度:");
	private JLabel labelV = new JLabel("速度:");
	private JLabel label_V = new JLabel(", ");
	private JLabel labelR_ = new JLabel("(px)            ");
	private JLabel labelD_ = new JLabel("(mg/px^3)");
	private JLabel labelV_ = new JLabel("(px/50ms)" );
	
	private JTextField jtfR = new JTextField("130.0", 10);
	private JTextField jtfD = new JTextField("100.0", 10);
	private JTextField jtfVX = new JTextField("10.0", 4);
	private JTextField jtfVY = new JTextField("10.0", 4);
	
	public AddBubbleDialog(JFrame owner) {
		super(owner, "添加一个泡泡", true);
		this.canvas = ((MotionFrame) owner).getCanvas();
		
		panelR.add(labelR);
		panelR.add(jtfR);
		panelR.add(labelR_);
		
		panelD.add(labelD);
		panelD.add(jtfD);
		panelD.add(labelD_);
		
		panelV.add(labelV);
		panelV.add(jtfVX);
		panelV.add(label_V);
		panelV.add(jtfVY);
		panelV.add(labelV_);
		
		panel.setLayout(new BorderLayout());
		panel.add("North", panelR);
		panel.add("Center", panelD);
		panel.add("South", panelV);
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBubbleDialog.this.canvas.addBubble(Float.parseFloat(jtfR.getText()), Float.parseFloat(jtfD.getText()), getVelocity());
				AddBubbleDialog.this.dispose();
			}
		});
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add("Center", panel);
		this.getContentPane().add("South", confirm);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private Point2D.Float getVelocity() {
		Point2D.Float temp = new Point2D.Float();
		temp.x = Float.parseFloat(jtfVX.getText());
		temp.y = Float.parseFloat(jtfVY.getText()); 
		return temp;
	}
}
