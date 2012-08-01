package com.echeloneditor.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.listeners.SimpleDragFileListener;
import com.echeloneditor.listeners.SimpleFileChooseListener;
import com.echeloneditor.listeners.SimpleJmenuItemListener;
import com.echeloneditor.listeners.TabbedPaneChangeListener;
import com.echeloneditor.utils.Config;
import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;

public class EchelonEditor {

	public JFrame frmEcheloneditor;
	public static JTabbedPane tabbedPane;
	public StatusObject statusObject;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		startApp(args);
	}

	public static void startApp(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Config config = new Config();
					// 设置皮肤
					String currentLaf = config.getValue("current_laf", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					String currentTheme = config.getValue("current_theme", "Default");
					String lafIndex = config.getValue("current_lafIndex", "0");

					if (!currentTheme.equals("window")) {
						SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
					}

					UIManager.setLookAndFeel(currentLaf);
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SwingUtils.updateUI();
						}
					});
					// 初始化窗体
					EchelonEditor window = new EchelonEditor();
					// 框体屏幕居中显示
					window.frmEcheloneditor.setLocationRelativeTo(null);
					// 显示窗体
					window.frmEcheloneditor.setVisible(true);
					// window.frmEcheloneditor.pack();
					new DropTarget(window.frmEcheloneditor, DnDConstants.ACTION_COPY_OR_MOVE, new SimpleDragFileListener(window.tabbedPane, window.statusObject), true);

					if (args.length > 0) {
						for (int i = 0; i < args.length; i++) {
							new FileHander(window.tabbedPane, window.statusObject).openFileWithFilePath(args[i]);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EchelonEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// 主窗体声明及初始化
		frmEcheloneditor = new JFrame();
		frmEcheloneditor.setIconImage(ImageHelper.loadImage("logo.jpg").getImage());
		frmEcheloneditor.setTitle("EchelonEditor");
		frmEcheloneditor.setSize(800, 600);

		frmEcheloneditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 窗口容器
		Container container = frmEcheloneditor.getContentPane();

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frmEcheloneditor.getContentPane().add(panel, BorderLayout.SOUTH);

		JLabel charNmLabel = new JLabel("字符数：");
		charNmLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(charNmLabel);

		JLabel fileSizeLabel = new JLabel("文件大小：");
		fileSizeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(fileSizeLabel);

		JLabel fileEncodeLabel = new JLabel("文件编码：");
		fileEncodeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		panel.add(fileEncodeLabel);

		statusObject = new StatusObject();
		statusObject.setCharNum(charNmLabel);
		statusObject.setFileEncode(fileEncodeLabel);
		statusObject.setFileSize(fileSizeLabel);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addMouseListener(new TabbedPaneChangeListener(tabbedPane, statusObject));
		frmEcheloneditor.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		frmEcheloneditor.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		panel_1.add(toolBar, BorderLayout.NORTH);

		JButton button = new JButton();
		button.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Add24.gif")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHander fileHander = new FileHander(tabbedPane, statusObject);
				fileHander.newFile();
			}
		});
		toolBar.add(button);

		JButton button_1 = new JButton();
		button_1.setActionCommand("open");
		button_1.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		button_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
		toolBar.add(button_1);

		JButton btnNewButton = new JButton();
		btnNewButton.setActionCommand("save");
		btnNewButton.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
		toolBar.add(btnNewButton);
		statusObject.setSaveBtn(btnNewButton);
		btnNewButton.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		btnNewButton.setEnabled(false);

		JToggleButton btnH = new JToggleButton("");
		btnH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileHander fileHander = new FileHander(tabbedPane, statusObject);
				if (((JToggleButton) e.getSource()).isSelected()) {
					fileHander.openHexFile();
					tabbedPane.remove(tabbedPane.getSelectedIndex() - 1);
				} else {
					fileHander.openFileWithFilePath(SwingUtils.getCloseableTabComponent(tabbedPane).getFilePath());
					tabbedPane.remove(tabbedPane.getSelectedIndex() - 1);
				}

			}
		});
		btnH.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/hex.PNG")));
		toolBar.add(btnH);

		JMenuBar menuBar = new JMenuBar();
		frmEcheloneditor.setJMenuBar(menuBar);

		JMenu menu = new JMenu("文件");
		menu.setMnemonic('F');
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("新建");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(new SimpleJmenuItemListener(tabbedPane, statusObject));
		menuItem.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Add24.gif")));
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("打开");
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuItem_1.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		menuItem_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Open24.gif")));
		menu.add(menuItem_1);

		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);

		JMenuItem menuItem_2 = new JMenuItem("保存");
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItem_2.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Save24.gif")));
		menu.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("另存为");
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		menuItem_3.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/SaveAs24.gif")));
		menu.add(menuItem_3);

		JSeparator separator_2 = new JSeparator();
		menu.add(separator_2);

		JMenuItem menuItem_4 = new JMenuItem("退出");
		menuItem_4.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Remove24.gif")));
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuItem_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuItem_4);

		JMenu menu_3 = new JMenu("格式");
		menuBar.add(menu_3);

		JMenuItem menuItem_5 = new JMenuItem("字体");
		menuItem_5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.ALT_MASK));
		menuItem_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() <= 0) {
					return;
				}
				RSyntaxTextArea textArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				Font font = textArea.getFont();

				FontChooserDialog fontset = new FontChooserDialog(frmEcheloneditor, font, textArea);
				fontset.setLocationRelativeTo(frmEcheloneditor);
				fontset.setVisible(true);
			}
		});
		menu_3.add(menuItem_5);

		JMenu menu_4 = new JMenu("编辑");
		menuBar.add(menu_4);

		JMenuItem menuItem_6 = new JMenuItem("查找");
		menuItem_6.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/find.png")));
		menuItem_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItem_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() <= 0) {
					return;
				}
				RSyntaxTextArea textArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				FindAndReplaceDialog findAndReplaceDialog = new FindAndReplaceDialog(textArea);
				findAndReplaceDialog.setVisible(true);
			}
		});

		JMenuItem menuItem_13 = new JMenuItem("撤销");
		menuItem_13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuItem_13.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/undo.png")));
		menu_4.add(menuItem_13);

		JMenuItem menuItem_14 = new JMenuItem("重做");
		menuItem_14.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/redo.png")));
		menuItem_14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_14);

		JSeparator separator_5 = new JSeparator();
		menu_4.add(separator_5);

		JMenuItem menuItem_8 = new JMenuItem("剪切");
		menuItem_8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItem_8.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/cut-to-clipboard.png")));
		menu_4.add(menuItem_8);

		JMenuItem menuItem_9 = new JMenuItem("复制");
		menuItem_9.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/copy-to-clipboard.png")));
		menuItem_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_9);

		JMenuItem menuItem_10 = new JMenuItem("粘贴");
		menuItem_10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuItem_10.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/paste-from-clipboard.png")));
		menu_4.add(menuItem_10);

		JMenuItem menuItem_11 = new JMenuItem("删除");
		menuItem_11.setIcon(new ImageIcon(EchelonEditor.class.getResource("/toolbarButtonGraphics/general/Delete16.gif")));
		menuItem_11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menu_4.add(menuItem_11);

		JSeparator separator_4 = new JSeparator();
		menu_4.add(separator_4);

		JMenuItem menuItem_12 = new JMenuItem("全选");
		menuItem_12.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/select-all.png")));
		menuItem_12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_12);

		JSeparator separator_3 = new JSeparator();
		menu_4.add(separator_3);
		menu_4.add(menuItem_6);

		JMenu menu_1 = new JMenu("工具");
		menuBar.add(menu_1);

		JMenu menu_5 = new JMenu("皮肤");
		menuBar.add(menu_5);

		JMenuItem menuItem_7 = new JMenuItem("皮肤");
		menuItem_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionevent) {
				// TODO Auto-generated method stub
				new FaceDialog(frmEcheloneditor);
			}
		});
		menu_5.add(menuItem_7);

		JMenu menu_2 = new JMenu("帮助");
		menuBar.add(menu_2);

		container.doLayout();
	}

}
