package yuki;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ChangeGravityDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 4227082388746806105L;
	
	private DrawCanvas canvas;
	private float gravity;
	
	private JLabel current = new JLabel();
	private JPanel panel = new JPanel();
	private JButton confirm = new JButton("确定");

	private JRadioButton jrb1;
	private JRadioButton jrb2;
	private JRadioButton jrb3;
	private ButtonGroup group = new ButtonGroup();

	private JButton increase_ = new JButton(">");
	private JButton decrease_ = new JButton("<");
	private JTextField gravityInput = new JTextField("", 5);
	private JButton increase = new JButton("Λ");
	private JButton decrease = new JButton("V");
	
	public ChangeGravityDialog(JFrame owner) {
		super(owner, "设置重力加速度", true);
		this.canvas = ((MotionFrame) owner).getCanvas();
		this.gravity = canvas.getGravity();
		
		current.setText("  当前的重力加速度为  " + gravity + "  px/50ms^2。");
		
		jrb1 = new JRadioButton("agravic ( 0.0 px/50ms^2 )", gravity == 0);
		jrb2 = new JRadioButton("nomal ( 9.8 px/50ms^2 )", gravity == 9.8);
		jrb3 = new JRadioButton("", gravity != 0 && gravity != 9.8);
		group.add(jrb1);
		group.add(jrb2);
		group.add(jrb3);
		if (!jrb3.isSelected()) {
			decrease_.setEnabled(false);
			decrease.setEnabled(false);
			gravityInput.setEnabled(false);
			increase.setEnabled(false);
			increase_.setEnabled(false);
		} else {
			gravityInput.setText(gravity + "");
			gravityInput.selectAll();
		}
		jrb1.setBounds(5, 5, 200, 20);
		jrb2.setBounds(5, 35, 200, 20);
		jrb3.setBounds(5, 68, 18, 20);
		
		increase_.setBounds(25, 65, 47, 15);
		decrease_.setBounds(25, 80, 47, 15);
		gravityInput.setBounds(75, 65, 80, 30);
		increase.setBounds(158, 65, 47, 15);
		decrease.setBounds(158, 80, 47, 15);

		jrb1.addActionListener(this);
		jrb2.addActionListener(this);
		jrb3.addActionListener(this);
		increase_.addActionListener(this);
		decrease_.addActionListener(this);
		increase.addActionListener(this);
		decrease.addActionListener(this);
		confirm.addActionListener(this);
		gravityInput.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (jrb1.isSelected()) {
						ChangeGravityDialog.this.canvas.setGravity(0);
					} else if (jrb2.isSelected()) {
						ChangeGravityDialog.this.canvas.setGravity(9.8f);
					} else if (jrb3.isSelected()) {
						ChangeGravityDialog.this.canvas.setGravity(Float.parseFloat(gravityInput.getText()));
					}
					ChangeGravityDialog.this.dispose();
				}
			}
		});
		
		panel.setLayout(null);
		panel.add(jrb1);
		panel.add(jrb2);
		panel.add(jrb3);
		panel.add(increase_);
		panel.add(decrease_);
		panel.add(gravityInput);
		panel.add(increase);
		panel.add(decrease);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add("North", current);
		this.getContentPane().add("Center", panel);
		this.getContentPane().add("South", confirm);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(250, 180);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jrb1 || e.getSource() == jrb2) {
			gravityInput.setText("");
			decrease_.setEnabled(false);
			decrease.setEnabled(false);
			gravityInput.setEnabled(false);
			increase.setEnabled(false);
			increase_.setEnabled(false);
		} else if (e.getSource() == jrb3) {
			gravityInput.setText(gravity + "");
			decrease_.setEnabled(true);
			decrease.setEnabled(true);
			gravityInput.setEnabled(true);
			increase.setEnabled(true);
			increase_.setEnabled(true);
		}
		if (e.getSource() == decrease_) {
			gravity -= 1;
			gravityInput.setText(gravity + "");
		} else if (e.getSource() == increase_) {
			gravity += 1;
			gravityInput.setText(gravity + "");
		} else if (e.getSource() == increase) {
			gravity += 0.1;
			gravity = (float) (((int) (gravity * 10 + 0.1)) / 10.0);
			gravityInput.setText(gravity + "");
		} else if (e.getSource() == decrease) {
			gravity -= 0.1;
			gravity = (float) (((int) (gravity * 10 + 0.1)) / 10.0);
			gravityInput.setText(gravity + "");
		}
		if (e.getSource() == confirm) {
			if (jrb1.isSelected()) {
				canvas.setGravity(0);
			} else if (jrb2.isSelected()) {
				canvas.setGravity(9.8f);
			} else if (jrb3.isSelected()) {
				canvas.setGravity(Float.parseFloat(gravityInput.getText()));
			}
			this.dispose();
		}
	}
}
