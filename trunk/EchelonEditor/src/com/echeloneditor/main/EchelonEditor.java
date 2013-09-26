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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.fife.rsta.ac.demo.DemoApp;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.zeroturnaround.zip.ZipUtil;

import com.echeloneditor.actions.DecryptTemplateAction;
import com.echeloneditor.actions.EmvFormatAction;
import com.echeloneditor.actions.FileHander;
import com.echeloneditor.actions.FindAndReplaceAction;
import com.echeloneditor.actions.XmlPreettifyAction;
import com.echeloneditor.listeners.EditorPaneListener;
import com.echeloneditor.listeners.FindDialogListener;
import com.echeloneditor.listeners.SimpleDragFileListener;
import com.echeloneditor.listeners.SimpleFileChooseListener;
import com.echeloneditor.listeners.SimpleJmenuItemListener;
import com.echeloneditor.listeners.TabbedPaneChangeListener;
import com.echeloneditor.utils.Config;
import com.echeloneditor.utils.FontUtil;
import com.echeloneditor.utils.ImageHelper;
import com.echeloneditor.utils.SwingUtils;
import com.echeloneditor.vo.StatusObject;
import com.sun.java.swing.plaf.windows.resources.windows;
import com.watchdata.Generater;
import com.watchdata.commons.lang.WDByteUtil;

public class EchelonEditor {

	public JFrame frmEcheloneditor;
	public static JTabbedPane tabbedPane;
	public StatusObject statusObject;
	public FontWidthRuler ruler;

	FindDialog findDialog = null;
	ReplaceDialog replaceDialog = null;
	public static AssistantToolDialog dialog = null;

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
					// 设置皮肤
					String currentLaf = Config.getValue("CURRENT_THEME", "current_laf");
					String currentTheme = Config.getValue("CURRENT_THEME", "current_theme");
					String lafIndex = Config.getValue("CURRENT_THEME", "current_lafIndex");

					if (!currentTheme.equals("window")) {
						SwingUtils.setTheme(Integer.parseInt(lafIndex), currentTheme);
					}

					UIManager.setLookAndFeel(currentLaf);

					SwingUtils.updateUI();
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
		// frmEcheloneditor.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		frmEcheloneditor.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		panel_1.add(toolBar, BorderLayout.NORTH);

		JButton button = new JButton();
		button.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504112619422_easyicon_net_24.png")));
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
		button_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504112148516_easyicon_net_24.png")));
		toolBar.add(button_1);

		JButton btnNewButton = new JButton();
		btnNewButton.setActionCommand("save");
		btnNewButton.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504114455808_easyicon_net_24.png")));
		toolBar.add(btnNewButton);
		statusObject.setSaveBtn(btnNewButton);
		btnNewButton.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		btnNewButton.setEnabled(false);

		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504111901655_easyicon_net_24.png")));
		toolBar.add(button_2);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String filepath = ((CloseableTabComponent) SwingUtils.getCloseableTabComponent(tabbedPane)).getFilePath();
				try {
					File file = new File(filepath);
					FileInputStream fis = new FileInputStream(file);

					byte[] bytes = new byte[fis.available()];

					fis.read(bytes);

					RSyntaxTextArea textArea = SwingUtils.createTextArea();

					textArea.setSyntaxEditingStyle("text/plain");

					EditorPaneListener editlistener = new EditorPaneListener(tabbedPane, statusObject);
					textArea.addMouseListener(editlistener);
					textArea.addMouseMotionListener(editlistener);
					textArea.addKeyListener(editlistener);
					textArea.getDocument().addDocumentListener(editlistener);

					RTextScrollPane sp = new RTextScrollPane(textArea);
					sp.setFoldIndicatorEnabled(true);

					Gutter gutter = sp.getGutter();
					gutter.setBookmarkingEnabled(true);
					ImageIcon ii = ImageHelper.loadImage("bookmark.png");
					gutter.setBookmarkIcon(ii);

					// 加入标尺
					FontWidthRuler ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, textArea);
					ruler.setPreferredWidth(20000);
					ruler.addSpin(3);
					ruler.NeedPaint = true;
					sp.setColumnHeaderView(ruler);

					int tabCount = tabbedPane.getTabCount();
					CloseableTabComponent closeableTabComponent = new CloseableTabComponent(tabbedPane, statusObject);

					tabbedPane.add("New Panel", sp);
					tabbedPane.setTabComponentAt(tabCount, closeableTabComponent);

					tabbedPane.setSelectedComponent(sp);
					// 设置选项卡title为打开文件的文件名
					SwingUtils.setTabbedPaneTitle(tabbedPane, file.getName() + "_hex");
					String sb = WDByteUtil.bytes2HEX(bytes);

					int pos = 0;
					int len = sb.length();

					while (pos < len) {
						if (len - pos < 1024) {
							textArea.append(sb.substring(pos, pos + len - pos));
							pos += len - pos;
						} else {
							textArea.append(sb.substring(pos, pos + 1024));
							pos += 1024;
						}
						textArea.append("\n");
					}

					String res = Config.getValue("CURRENT_THEME", "current_font");

					textArea.setFont(FontUtil.getFont(res));
					statusObject.getSaveBtn().setEnabled(false);

					closeableTabComponent.setModify(false);
					fis.close();
					// textArea.setCaretPosition(0);
					textArea.requestFocusInWindow();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		button_3.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504111819570_easyicon_net_24.png")));
		toolBar.add(button_3);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filepath = ((CloseableTabComponent) SwingUtils.getCloseableTabComponent(tabbedPane)).getFilePath();
				try {
					File file = new File(filepath);
					FileInputStream fis = new FileInputStream(file);

					byte[] bytes = new byte[fis.available()];

					fis.read(bytes);

					RSyntaxTextArea textArea = SwingUtils.createTextArea();

					textArea.setSyntaxEditingStyle("text/xml");
					
					EditorPaneListener editlistener=new EditorPaneListener(tabbedPane, statusObject);
					textArea.addMouseListener(editlistener);
					textArea.addMouseMotionListener(editlistener);
					textArea.addKeyListener(editlistener);
					textArea.getDocument().addDocumentListener(editlistener);
					
					RTextScrollPane sp = new RTextScrollPane(textArea);
					sp.setFoldIndicatorEnabled(true);

					Gutter gutter = sp.getGutter();
					gutter.setBookmarkingEnabled(true);
					ImageIcon ii = ImageHelper.loadImage("bookmark.png");
					gutter.setBookmarkIcon(ii);

					InputStream in = getClass().getResourceAsStream("/com/echeloneditor/resources/templates/eclipse.xml");
					try {
						Theme theme = Theme.load(in);
						theme.apply(textArea);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					// 加入标尺
					ruler = new FontWidthRuler(FontWidthRuler.HORIZONTAL, 10, textArea);
					ruler.setPreferredWidth(20000);
					ruler.addSpin(3);
					ruler.NeedPaint = true;
					sp.setColumnHeaderView(ruler);

					int tabCount = tabbedPane.getTabCount();
					CloseableTabComponent closeableTabComponent = new CloseableTabComponent(tabbedPane, statusObject);
					closeableTabComponent.setFilePath(file.getPath());

					tabbedPane.add("New Panel", sp);
					tabbedPane.setTabComponentAt(tabCount, closeableTabComponent);

					tabbedPane.setSelectedComponent(sp);
					// 设置选项卡title为打开文件的文件名
					SwingUtils.setTabbedPaneTitle(tabbedPane, file.getName() + "_t");
					
					DecryptTemplateAction.doAction(textArea,new String(bytes));
					
					String res = Config.getValue("CURRENT_THEME", "current_font");

					textArea.setFont(FontUtil.getFont(res));
					statusObject.getSaveBtn().setEnabled(false);

					closeableTabComponent.setModify(false);
					fis.close();
					// textArea.setCaretPosition(0);
					textArea.requestFocusInWindow();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130721025254649_easyicon_net_24.png")));
		toolBar.add(btnNewButton_1);

		JMenuBar menuBar = new JMenuBar();
		frmEcheloneditor.setJMenuBar(menuBar);

		JMenu menu = new JMenu("文件");
		menu.setMnemonic('F');
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("新建");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(new SimpleJmenuItemListener(tabbedPane, statusObject));
		menuItem.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504112619422_easyicon_net_24.png")));
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("打开");
		menuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		menuItem_1.setActionCommand("open");
		menuItem_1.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		menuItem_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504112148516_easyicon_net_24.png")));
		menu.add(menuItem_1);

		JSeparator separator_1 = new JSeparator();
		menu.add(separator_1);

		JMenuItem menuItem_2 = new JMenuItem("保存");
		menuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuItem_2.setActionCommand("save");
		menuItem_2.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));
		menuItem_2.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504114455808_easyicon_net_24.png")));
		menu.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("另存为");
		menuItem_3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		menuItem_3.setActionCommand("saveas");
		menuItem_3.addActionListener(new SimpleFileChooseListener(tabbedPane, statusObject));

		menuItem_3.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504112319418_easyicon_net_24.png")));
		menu.add(menuItem_3);

		JSeparator separator_2 = new JSeparator();
		menu.add(separator_2);

		JMenuItem menuItem_4 = new JMenuItem("退出");
		menuItem_4.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504114639245_easyicon_net_24.png")));
		menuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		menuItem_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuItem_4);

		JMenu menu_4 = new JMenu("编辑");
		menuBar.add(menu_4);

		JMenuItem menuItem_6 = new JMenuItem("查找");
		menuItem_6.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508110036317_easyicon_net_24.png")));
		menuItem_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		menuItem_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() <= 0) {
					return;
				}
				RSyntaxTextArea textArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				FindDialogListener findDialogListener = new FindDialogListener(textArea);
				findDialog = new FindDialog(frmEcheloneditor, findDialogListener);
				if (replaceDialog != null && replaceDialog.isVisible()) {
					replaceDialog.setVisible(false);
				}
				findDialog.setVisible(true);
			}
		});

		JMenuItem menuItem_13 = new JMenuItem("撤销");
		menuItem_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.undoLastAction();
			}
		});
		menuItem_13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		menuItem_13.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508104933496_easyicon_net_24.png")));
		menu_4.add(menuItem_13);

		JMenuItem menuItem_14 = new JMenuItem("重做");
		menuItem_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.redoLastAction();
			}
		});
		menuItem_14.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508104655987_easyicon_net_24.png")));
		menuItem_14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_14);

		JSeparator separator_5 = new JSeparator();
		menu_4.add(separator_5);

		JMenuItem menuItem_8 = new JMenuItem("剪切");
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.cut();
			}
		});
		menuItem_8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		menuItem_8.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508105503688_easyicon_net_24.png")));
		menu_4.add(menuItem_8);

		JMenuItem menuItem_9 = new JMenuItem("复制");
		menuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.copy();
			}
		});
		menuItem_9.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508112701510_easyicon_net_24.png")));
		menuItem_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_9);

		JMenuItem menuItem_10 = new JMenuItem("粘贴");
		menuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.paste();
			}
		});
		menuItem_10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		menuItem_10.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508105319839_easyicon_net_24.png")));
		menu_4.add(menuItem_10);

		JMenuItem menuItem_11 = new JMenuItem("删除");
		menuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				if (rSyntaxTextArea == null) {
					return;
				}
				int selectStart = rSyntaxTextArea.getSelectionStart();
				if (selectStart < 0) {
					return;
				}
				if (rSyntaxTextArea.getSelectedText() == null) {
					return;
				}
				int len = rSyntaxTextArea.getSelectedText().length();
				try {
					rSyntaxTextArea.getDocument().remove(selectStart, len);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuItem_11.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508105713413_easyicon_net_24.png")));
		menuItem_11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		menu_4.add(menuItem_11);

		JSeparator separator_4 = new JSeparator();
		menu_4.add(separator_4);

		JMenuItem menuItem_12 = new JMenuItem("全选");
		menuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				rSyntaxTextArea.selectAll();
			}
		});
		menuItem_12.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508105939375_easyicon_net_24.png")));
		menuItem_12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_12);
		JSeparator separator_3 = new JSeparator();
		menu_4.add(separator_3);
		menu_4.add(menuItem_6);

		JMenuItem mntmNewMenuItem = new JMenuItem("快速查找");
		mntmNewMenuItem.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508112417300_easyicon_net_24.png")));
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextComponent com = SwingUtils.getRSyntaxTextArea(tabbedPane);
				String targetStr = com.getSelectedText();
				FindAndReplaceAction.find(com, targetStr, true, true, false);
			}
		});

		JMenuItem menuItem_15 = new JMenuItem("替换");
		menuItem_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabbedPane.getTabCount() <= 0) {
					return;
				}
				RSyntaxTextArea textArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				FindDialogListener findDialogListener = new FindDialogListener(textArea);
				replaceDialog = new ReplaceDialog(frmEcheloneditor, findDialogListener);
				if (findDialog != null && findDialog.isVisible()) {
					// replaceDialog.setSearchContext(findDialog.getSearchContext());
					findDialog.setVisible(false);
				}
				replaceDialog.setVisible(true);
			}
		});
		menuItem_15.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508110228832_easyicon_net_24.png")));
		menuItem_15.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		menu_4.add(menuItem_15);
		menu_4.add(mntmNewMenuItem);

		JSeparator separator = new JSeparator();
		menu_4.add(separator);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("格式化");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);

				CloseableTabComponent ctc = SwingUtils.getCloseableTabComponent(tabbedPane);
				int pos = ctc.getFilePath().lastIndexOf(".");
				String fileExt = ctc.getFilePath().substring(pos + 1);
				if (fileExt.equals("xml")) {
					boolean Success = XmlPreettifyAction.format(rSyntaxTextArea);
					if (!Success) {
						JOptionPane.showMessageDialog(null, "格式化失败");
						return;
					}
				}

			}
		});
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmNewMenuItem_1.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130508011341649_easyicon_net_24.png")));
		menu_4.add(mntmNewMenuItem_1);

		JMenuItem mntmTlv = new JMenuItem("TLV");
		mntmTlv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSyntaxTextArea rSyntaxTextArea = SwingUtils.getRSyntaxTextArea(tabbedPane);
				try {
					EmvFormatAction.format(rSyntaxTextArea);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTlv.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130509034342785_easyicon_net_24.png")));
		mntmTlv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
		menu_4.add(mntmTlv);

		JMenu menu_3 = new JMenu("格式");
		menuBar.add(menu_3);

		JMenuItem menuItem_5 = new JMenuItem("字体");
		menuItem_5.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504114104911_easyicon_net_24.png")));
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

		JMenu menu_1 = new JMenu("工具");
		menuBar.add(menu_1);

		JMenuItem menuItem_16 = new JMenuItem("辅助工具");
		menuItem_16.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504114154800_easyicon_net_24.png")));
		menuItem_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dialog == null) {
					dialog = new AssistantToolDialog();
				}
				if (dialog.isVisible()) {
					dialog.toFront();
				} else {
					dialog.setVisible(true);
				}

			}
		});
		menu_1.add(menuItem_16);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("密钥成分生成工具");
		mntmNewMenuItem_2.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130729071051684_easyicon_net_24.png")));
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Generater window = new Generater();
				window.frame.setVisible(true);
			}
		});
		menu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("ZIP打包");
		mntmNewMenuItem_3.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130926111642678_easyicon_net_24.png")));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = fileChooser.showOpenDialog(frmEcheloneditor);

				if (ret == JFileChooser.APPROVE_OPTION) {
					File selectedFile=fileChooser.getSelectedFile();
					String filePath = selectedFile.getPath();
					filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
					File zipFile=new File(filePath + "/" + selectedFile.getName() + ".zip");
					ZipUtil.pack(fileChooser.getSelectedFile(), zipFile);
				}
			}
		});
		menu_1.add(mntmNewMenuItem_3);

		JMenu menu_5 = new JMenu("皮肤");
		menuBar.add(menu_5);

		JMenuItem menuItem_7 = new JMenuItem("皮肤");
		menuItem_7.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/20130504113002740_easyicon_net_24.png")));
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

		JMenuItem menuItem_17 = new JMenuItem("帮助");
		menuItem_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmEcheloneditor, "EchelonEditor_赫本", "帮助", JOptionPane.WARNING_MESSAGE);
			}
		});
		menuItem_17.setIcon(new ImageIcon(EchelonEditor.class.getResource("/com/echeloneditor/resources/images/2013050411485151_easyicon_net_24.png")));
		menu_2.add(menuItem_17);

		frmEcheloneditor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		container.doLayout();
	}

}
