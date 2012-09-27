package yuki;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = -1334322116158461416L;
	
	private JLabel javaIcon = new JLabel();
	private JLabel bubblesIcon = new JLabel();
	private JLabel jText = new JLabel("J");
	private JLabel avaText = new JLabel("ava");
	private JLabel name = new JLabel("程序名称：运动的泡泡");
	private JLabel versions = new JLabel("版 本 号 ：Ver 1.0");
	private JLabel editor = new JLabel("程序制作：情却然（QQ: 601625333）");
	private JLabel await = new JLabel("敬请期待下期产品！");
	private JButton confirm = new JButton("确定");

	private Font font = new Font("微软雅黑", Font.PLAIN, 16);
	
	public AboutDialog(JFrame owner) {
		super(owner, "关于“泡泡”", true);
		
		javaIcon.setIcon(new ImageIcon(FileUtil.getImage(true, "java.png")));
		bubblesIcon.setIcon(new ImageIcon(FileUtil.getImage(true, "bubbles.png")));
		
		jText.setFont(new Font("微软雅黑", Font.BOLD, 150));
		avaText.setFont(new Font("微软雅黑", Font.BOLD, 70));
		name.setFont(font);
		versions.setFont(font);
		editor.setFont(font);
		await.setFont(font);
		confirm.setFont(font);
		
		javaIcon.setBounds(30, 20, 130, 130);
		jText.setBounds(180, 10, 80, 140);
		avaText.setBounds(240, 75, 120, 75);
		bubblesIcon.setBounds(30, 180, 61, 60);
		name.setBounds(100, 185, 300, 20);
		versions.setBounds(100, 210, 200, 20);
		editor.setBounds(100, 235, 300, 20);
		await.setBounds(100, 260, 200, 20);
		confirm.setBounds(240, 300, 138, 28);

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.dispose();
			}
		});
		
		this.getContentPane().add(javaIcon);
		this.getContentPane().add(jText);
		this.getContentPane().add(avaText);
		this.getContentPane().add(bubblesIcon);
		this.getContentPane().add(name);
		this.getContentPane().add(versions);
		this.getContentPane().add(editor);
		this.getContentPane().add(await);
		this.getContentPane().add(confirm);

		this.setLayout(null);
		this.setAlwaysOnTop(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 370);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}