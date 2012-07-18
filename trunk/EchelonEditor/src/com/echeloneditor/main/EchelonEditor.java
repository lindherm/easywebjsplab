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
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.actions.FileHander;
import com.echeloneditor.listeners.SimpleDragFileListener;
import com.echeloneditor.listeners.SimpleFileChooseListener;
import com.echeloneditor.listeners.SimpleJmenuItemListener;
import com.echeloneditor.listeners.TabbedPaneChangeListener;
import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class EchelonEditor {

	public JFrame frmEcheloneditor;
	public static JTabbedPane tabbedPane;
	public StatusObject statusObject;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 设置皮肤
					UIManager.setLookAndFeel(new McWinLookAndFeel());
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
		button.setIcon(ImageHelper.loadImage("new.png"));
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
		button_1.setIcon(ImageHelper.loadImage("open.png"));
		toolBar.add(button_1);

		JButton btnNewButton = new JButton();
		btnNewButton.setActionCommand("save");
		btnNewButton.setIcon(ImageHelper.loadImage("save.png"));
		toolBar.add(btnNewButton);
		statusObject.setSaveBtn(btnNewButton);
		btnNewButton.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		btnNewButton.setEnabled(false);

		JMenuBar menuBar = new JMenuBar();
		frmEcheloneditor.setJMenuBar(menuBar);

		JMenu menu = new JMenu("文件");
		menu.setMnemonic('F');
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("新建");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(new SimpleJmenuItemListener(tabbedPane, statusObject));
		menuItem.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/new.png")));
		menu.add(menuItem);

		JSeparator separator = new JSeparator();
		menu.add(separator);

		JMenuItem menuItem_1 = new JMenuItem("打开");
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuItem_1.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		menuItem_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/open.png")));
		menu.add(menuItem_1);

		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);

		JMenuItem menuItem_2 = new JMenuItem("保存");
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItem_2.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/save.png")));
		menu.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("另存为");
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		menuItem_3.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/save.png")));
		menu.add(menuItem_3);

		JSeparator separator_2 = new JSeparator();
		menu.add(separator_2);

		JMenuItem menuItem_4 = new JMenuItem("退出");
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
		menuItem_5.setEnabled(false);
		menuItem_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea textArea = SwingUtils.getSyntaxArea(tabbedPane);
				Font font = textArea.getFont();

				FontChooserDialog fontset = new FontChooserDialog(frmEcheloneditor, font, textArea);
				fontset.setLocationRelativeTo(frmEcheloneditor);
				fontset.setVisible(true);
			}
		});
		menu_3.add(menuItem_5);
		statusObject.setFontItem(menuItem_5);

		JMenu menu_4 = new JMenu("编辑");
		menuBar.add(menu_4);

		JMenuItem menuItem_6 = new JMenuItem("查找");
		menuItem_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItem_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() <= 0) {
					return;
				}
				RSyntaxTextArea textArea = SwingUtils.getSyntaxArea(tabbedPane);
				FindAndReplaceDialog findAndReplaceDialog = new FindAndReplaceDialog(textArea);
				findAndReplaceDialog.setVisible(true);
			}
		});
		menu_4.add(menuItem_6);

		JMenu menu_1 = new JMenu("工具");
		menuBar.add(menu_1);

		JMenu menu_2 = new JMenu("帮助");
		menuBar.add(menu_2);

		container.doLayout();
	}

}
