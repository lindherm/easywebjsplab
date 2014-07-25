package com.gerenhua.tool.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.utils.Config;
import com.gerenhua.tool.utils.HexConfig;
import com.gerenhua.tool.utils.WindowsExcuter;

public class PrgToolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public JTextPane textPane_1;
	private static Log log = new Log();

	public PrgToolPanel() {
		setLayout(null);
		JLabel lblNewLabel = new JLabel("HexGhost:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(19, 100, 84, 20);
		add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(114, 100, 491, 20);
		textField.setText(Config.getValue("PrgTool", "HexGhostPath"));
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("设置");
		btnNewButton.setFocusPainted(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile(textField);
				Config.setValue("PrgTool", "HexGhostPath", textField.getText().trim());
			}
		});
		btnNewButton.setBounds(615, 100, 84, 21);
		add(btnNewButton);

		JLabel lblGenflashprg = new JLabel("GenFlashPrg:");
		lblGenflashprg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGenflashprg.setBounds(19, 130, 84, 20);
		add(lblGenflashprg);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setText(Config.getValue("PrgTool", "GenFlashPrgPath"));
		textField_1.setBounds(114, 130, 491, 20);
		add(textField_1);

		JButton button = new JButton("设置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile(textField_1);
				Config.setValue("PrgTool", "GenFlashPrgPath", textField_1.getText().trim());
			}
		});
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setBounds(615, 130, 84, 21);
		add(button);

		JLabel lblCos = new JLabel("COS_HEX:");
		lblCos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCos.setBounds(19, 10, 84, 20);
		add(lblCos);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setText(Config.getValue("PrgTool", "COS_HEXPath"));
		textField_2.setBounds(114, 10, 491, 20);
		add(textField_2);

		JButton button_1 = new JButton("设置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile(textField_2);
				Config.setValue("PrgTool", "COS_HEXPath", textField_2.getText().trim());
			}
		});
		button_1.setFocusPainted(false);
		button_1.setBorderPainted(false);
		button_1.setBounds(615, 10, 84, 21);
		add(button_1);

		JLabel lblApphex = new JLabel("APP_HEX:");
		lblApphex.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApphex.setBounds(19, 40, 84, 20);
		add(lblApphex);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setText(Config.getValue("PrgTool", "APP_HEXPath"));
		textField_3.setBounds(114, 40, 491, 20);
		add(textField_3);

		JButton button_2 = new JButton("设置");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile(textField_3);
				Config.setValue("PrgTool", "APP_HEXPath", textField_3.getText().trim());
			}
		});
		button_2.setFocusPainted(false);
		button_2.setBorderPainted(false);
		button_2.setBounds(615, 40, 84, 21);
		add(button_2);

		JButton btnhex = new JButton("合并HEX");
		btnhex.setFocusPainted(false);
		btnhex.setBorderPainted(false);
		btnhex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							String hexGhostPath = textField.getText().trim();
							File file = new File(hexGhostPath);
							String parentPath = file.getParentFile().getAbsolutePath();
							HexConfig config = new HexConfig(parentPath + "/HexGhost.conf");
							List<String> cmdList = new ArrayList<String>();
							cmdList.clear();
							cmdList.add("cmd.exe");
							cmdList.add("/c");
							cmdList.add("copy");
							cmdList.add(textField_2.getText().trim());
							cmdList.add(parentPath);
							WindowsExcuter.excute(file.getParentFile(), cmdList);

							cmdList.clear();
							cmdList.add("cmd.exe");
							cmdList.add("/c");
							cmdList.add("copy");
							cmdList.add(textField_3.getText().trim());
							cmdList.add(parentPath);

							WindowsExcuter.excute(file.getParentFile(), cmdList);
							HexConfig.setValue("HexFiles", "cosHex", new File(textField_2.getText().trim()).getName());
							HexConfig.setValue("HexFiles", "targetHex", new File(textField_3.getText().trim()).getName());

							cmdList.clear();
							cmdList.add("cmd.exe");
							cmdList.add("/c");
							cmdList.add("start");
							cmdList.add(hexGhostPath);

							WindowsExcuter.excute(file.getParentFile(), cmdList);
							if (WindowsExcuter.p.exitValue() == 0) {
								log.debug("hexpading succed.");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		});
		btnhex.setBounds(114, 160, 93, 23);
		add(btnhex);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "LOG", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 413, 815, 207);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		textPane_1 = new JTextPane() {
			/**
													 * 
													 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			@Override
			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				super.setSize(d);
			}
		};
		scrollPane.setViewportView(textPane_1);
		log.setLogArea(textPane_1);
		JButton btnNewButton_1 = new JButton("GenFlashPrg");
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								List<String> cmdList = new ArrayList<String>();

								String hexGhostPath = textField_1.getText().trim();
								File file = new File(hexGhostPath);
								cmdList.add("cmd.exe");
								cmdList.add("/c");
								cmdList.add("start");
								cmdList.add(hexGhostPath);

								WindowsExcuter.excute(file.getParentFile(), cmdList);
								if (WindowsExcuter.p.exitValue() == 0) {
									log.debug("GenFlashPrg succed.");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					thread.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(217, 160, 110, 23);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("打开目录");
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileDir(textField);
			}
		});
		btnNewButton_2.setBounds(709, 100, 93, 23);
		add(btnNewButton_2);

		JButton button_3 = new JButton("打开目录");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileDir(textField_1);
			}
		});
		button_3.setFocusPainted(false);
		button_3.setBorderPainted(false);
		button_3.setBounds(709, 130, 93, 23);
		add(button_3);

		JButton button_4 = new JButton("打开目录");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileDir(textField_2);
			}
		});
		button_4.setFocusPainted(false);
		button_4.setBorderPainted(false);
		button_4.setBounds(709, 10, 93, 23);
		add(button_4);

		JButton button_5 = new JButton("打开目录");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileDir(textField_3);
			}
		});
		button_5.setFocusPainted(false);
		button_5.setBorderPainted(false);
		button_5.setBounds(709, 40, 93, 23);
		add(button_5);
	}

	public void selectFile(JTextField target) {
		JFileChooser jFileChooser = new JFileChooser(getFile(target.getText().trim()));
		int ret = jFileChooser.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			String filePath = jFileChooser.getSelectedFile().getAbsolutePath();
			target.setText(filePath);
		}
	}

	public void openFileDir(JTextField target) {
		try {
			WindowsExcuter.excute(new File("."), "cmd.exe /c start " + getFile(target.getText().trim()).getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public File getFile(String name) {
		File file = new File(name);
		if (file.exists()) {
			if (file.isFile()) {
				file = file.getParentFile();
			}
		}
		return file;
	}
}
