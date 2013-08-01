package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.dao.pojo.StaticData;
import com.watchdata.cardcheck.logic.apdu.pcsc.PcscChannel;
import com.watchdata.cardcheck.logic.impl.AppData;
import com.watchdata.cardcheck.logic.impl.StaticDataDetectHandler;
import com.watchdata.cardcheck.utils.Configuration;
import com.watchdata.cardcheck.utils.FileUtil;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.cardcheck.utils.SpringUtil;

public class StaticDataCalibrationResultsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StaticDataCalibrationResultsPanel.class);
	private PcscChannel apduChannel = (PcscChannel) SpringUtil.getBean("apduChannel");
	private JTable table;
	public static JButton checkButton;
	private final String NOT = "否";
	private final JButton stopButton;
	private PropertiesManager pm = new PropertiesManager();
	private Configuration config;
	private StaticDataDetectHandler staDataDetect = new StaticDataDetectHandler();
	DefaultTableModel tableModel;
	final JProgressBar progressBar = new JProgressBar();
	private boolean isStop = false;
	private Thread check = null;
	private JButton reportButton;
	String[] columnTitle;
	Object[][] columnData = null;
	private static Object lock = new Object();
	private String checkReportPath = null;
	private static boolean isSuccess = false;// 是否成功生成检测报告
	private int nowRow = -1;
	private int nowColumn = -1;

	JDialog dlg = new JDialog();
	JTextArea area = new JTextArea();
	JScrollPane dlgscrollPane = new JScrollPane(area);

	public static int status = 0;

	/**
	 * Create the panel
	 */
	public StaticDataCalibrationResultsPanel() {
		super();
		setLayout(null);
		setName("检测");
		dlg.setUndecorated(true);
		// setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.checkResult")));

		checkButton = new JButton("开始");
		checkButton.addActionListener(checkActionListener);
		checkButton.setBounds(719, 94, 93, 23);
		add(checkButton);

		final JLabel checkResultLabel = new JLabel();
		checkResultLabel.setBounds(0, 50, 97, 20);
		checkResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		checkResultLabel.setFont(new Font("宋体", Font.BOLD, 12));
		checkResultLabel.setText("检测结果");
		add(checkResultLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(79, 60, 730, 20);
		add(separator_1);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 95, 637, 445);

		columnTitle = new String[] { "应用类型", "标签", "原值", "卡值", "描述", "匹配" };
		columnData = null;

		table = new JTable(new DefaultTableModel(null, columnTitle)) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// 设置每一列不可移动
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 3) {
					Object ob = table.getValueAt(nowRow, nowColumn);
					area.setText(ob.toString());
					dlg.setLocationRelativeTo(table);
					Point p = e.getPoint();
					Point p2 = table.getLocationOnScreen();
					p.setLocation(p.getX() + p2.getX(), p.getY() + p2.getY());
					dlg.setLocation(p);
					dlg.setVisible(true);
					area.setLineWrap(true);
				}
			}

		});
		table.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				if (table != null) {

					int newRow = table.rowAtPoint(e.getPoint());
					int newColum = table.columnAtPoint(e.getPoint());
					if (newRow != nowRow || newColum != nowColumn) {
						nowColumn = newColum;
						nowRow = newRow;
						dlg.setVisible(false);
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		add(scrollPane);
		setBorder(JTBorderFactory.createTitleBorder("静态数据校验结果"));

		progressBar.setBounds(20, 580, 785, 20);
		add(progressBar);

		stopButton = new JButton();
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				int y = JOptionPane.showConfirmDialog(null, "确定要停止当前检测吗！", pm.getString("mv.testdata.InfoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 0) {
					isStop = true;
					staDataDetect.setStop(true);
					stopButton.setEnabled(false);
					progressBar.setVisible(false);
					if (status == 1) {
						disable(true);
					}
				}
			}
		});
		stopButton.setText("停止");
		stopButton.setBounds(719, 140, 93, 23);
		stopButton.setEnabled(false);
		add(stopButton);

		reportButton = new JButton();
		reportButton.addActionListener(checkReportListener);
		reportButton.setText("检测报告");
		reportButton.setBounds(719, 185, 93, 23);
		reportButton.setEnabled(false);
		add(reportButton);
		progressBar.setVisible(false);

		dlg.setSize(200, 100);
		dlg.setResizable(false);
		dlg.add(dlgscrollPane);
	}

	private ActionListener checkReportListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {

			String filePath = checkReportPath;
			String currentPath = checkReportPath.substring(checkReportPath.lastIndexOf("//") + 1, checkReportPath.lastIndexOf("."));
			File file = new File(filePath);
			if (file.exists()) {
				Object[] options = { "打开", "保存" };
				int ret = JOptionPane.showOptionDialog(null, "静态数据检测报告", "提示框", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				if (ret == JOptionPane.YES_OPTION) {
					// word
					try {
						Runtime.getRuntime().exec("cmd /c start winword \"\" \"" + filePath + "\"");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "打开文件失败，位置：" + filePath + "请手动操作！", "提示框", JOptionPane.ERROR_MESSAGE);
						return;
					}

				} else if (ret == JOptionPane.NO_OPTION) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("."));
					fileChooser.setFileFilter(new FileFilter() {

						@Override
						public String getDescription() {
							return "mcrosoft office word 文档";
						}

						@Override
						public boolean accept(File f) {
							if (f.getName().endsWith(".doc") || f.isDirectory()) {
								return true;
							} else {
								return false;
							}
						}
					});
					fileChooser.setSelectedFile(new File(currentPath + ".doc"));
					ret = fileChooser.showSaveDialog(null);
					if (ret == JFileChooser.APPROVE_OPTION) {
						FileUtil.copyFile(filePath, fileChooser.getSelectedFile().getAbsolutePath());
						JOptionPane.showMessageDialog(null, "保存成功！", "提示框", JOptionPane.INFORMATION_MESSAGE);
					} else {
						return;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "检测报告不存在！", "提示框", JOptionPane.ERROR_MESSAGE);
			}
		}

	};

	private ActionListener checkActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			synchronized (lock) {
				CheckStaticDataThread chkStaticData = new CheckStaticDataThread();
				check = new Thread(chkStaticData);
				check.start();
			}
		}
	};

	public void disable(boolean flag) {

		checkButton.setEnabled(flag);
		stopButton.setEnabled(!flag);
		progressBar.setVisible(!flag);
	}

	public void paintRow() {
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowRenderer());
		}
	}

	private class RowRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			// 设置奇偶行的背景色，可在此根据需要进行修改
			if (NOT.equals((String) table.getValueAt(row, 5))) {
				setBackground(new Color(238, 233, 233));
			} else {
				setBackground(null);
			}

			return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
		}
	}

	class CheckStaticDataThread implements Runnable {

		@Override
		public void run() {
			boolean panFlag = false;
			status = 0;
			isStop = false;
			staDataDetect.setStop(false);
			checkButton.setEnabled(false);
			reportButton.setEnabled(false);
			progressBar.setVisible(false);
			progressBar.setValue(0);
			progressBar.setString("检测中...");
			for (int i = ((DefaultTableModel) table.getModel()).getRowCount() - 1; i >= 0; i--) {
				((DefaultTableModel) table.getModel()).removeRow(i);
			}
			table.repaint();

			config = new Configuration();
			String reader = config.getValue("cardreader");
			log.error("the reader is " + reader);
			if (reader == null || "".equals(reader.trim())) {
				JOptionPane.showMessageDialog(null, "请先配置读卡器!", "提示框", JOptionPane.INFORMATION_MESSAGE);
				log.error("can't find the reader,the reader is " + reader);
				disable(true);
				return;
			}
			if (!apduChannel.init(reader)) {// 连不上卡和读卡器区分
				JOptionPane.showMessageDialog(null, "连不上读卡器!", "提示框", JOptionPane.INFORMATION_MESSAGE);
				disable(true);
				return;
			}
			staDataDetect.setCardReader(reader);

			// 从数据库中读取数据
			Map<String, Map<String, StaticData>> staticOriDataMap = staDataDetect.getCardDataFromDatabase();
			if (staticOriDataMap == null) {
				JOptionPane.showMessageDialog(null, "数据库不可用！", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
				disable(true);
				return;
			}

			if (staticOriDataMap.size() == 0) {
				log.error("DataBase does't have staticData");
				int y = JOptionPane.showConfirmDialog(null, "没有配置检测数据!继续将只读取卡片静态数据！", pm.getString("mv.testdata.InfoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					disable(true);
					return;
				}

			}

			stopButton.setEnabled(true);
			progressBar.setVisible(true);
			progressBar.setStringPainted(true); // 显示提示信息
			progressBar.setIndeterminate(true); // 不确定进度的进度条

			try {

				log.debug("-------------开始获取appData");

				List<AppData> allAppData = staDataDetect.getAllAppData();

				log.debug("------------开始检测");
				if (isStop) {
					return;
				}
				if (allAppData == null || allAppData.size() == 0) {
					JOptionPane.showMessageDialog(null, "卡片应用数据为空！", pm.getString("mv.testdata.InfoWindow"), JOptionPane.ERROR_MESSAGE);
					disable(true);
					return;
				}

				Map<String, Map<String, StaticData>> staticDataMap = staDataDetect.check(allAppData, staticOriDataMap);

				if (isStop) {
					return;
				}
				if (staticDataMap == null || staticDataMap.size() == 0) {
					disable(true);
					return;
				}
				log.debug("------------检测结果");
				Iterator<String> it = staticDataMap.keySet().iterator();
				List<StaticData> staticDataList = new ArrayList<StaticData>();
				String pan = "";

				while (it.hasNext()) {
					if (isStop) {// stop
						disable(true);
						return;
					}
					String key = (String) it.next();
					Map<String, StaticData> tagValue = staticDataMap.get(key);
					Iterator<String> tagIt = tagValue.keySet().iterator();
					while (tagIt.hasNext()) {// 对比每一个tag和value
						if (isStop) {// stop
							disable(true);
							return;
						}
						String tagKey = (String) tagIt.next();
						StaticData stacData = (StaticData) tagValue.get(tagKey);
						Object[] data = new Object[] { stacData.getAppType(), stacData.getTag(), stacData.getOriValue(), stacData.getCardValue(), stacData.getDscrpt(), stacData.getIsMatch() };

						((DefaultTableModel) table.getModel()).addRow(data);//
						paintRow();
						staticDataList.add(stacData);
						if ("5A".equalsIgnoreCase(stacData.getTag()) && !panFlag) {
							pan = stacData.getCardValue();
							if (pan == null) {
								pan = "";
								panFlag = false;
							} else if (pan.endsWith("F")) {
								pan = pan.substring(0, pan.indexOf("F"));
								panFlag = true;
							} else if (pan.endsWith("f")) {
								pan = pan.substring(0, pan.indexOf("f"));
								panFlag = true;
							}
							log.info("pan" + pan);
						}

					}

				}

				isSuccess = staDataDetect.generateReport(staticDataList, pan);// 生成报告文件
				if (isSuccess) {
					checkReportPath = staDataDetect.getCheckReportPath();
					reportButton.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "生成检测报告失败!", "提示框", JOptionPane.ERROR_MESSAGE);
					disable(true);
				}

				progressBar.setValue(100);
				progressBar.setString("检测完成");
				progressBar.setIndeterminate(false);

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "检测出错!", "提示框", JOptionPane.ERROR_MESSAGE);
				progressBar.setVisible(false);
			} finally {
				status = 1;
				log.info("card disconnect");
				apduChannel.destroy();
				checkButton.setEnabled(true);
				stopButton.setEnabled(false);
				if (isStop) {
					progressBar.setVisible(false);
				}
				panFlag = false;
			}

		}

	}

}