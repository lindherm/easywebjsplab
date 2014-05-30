package com.gerenhua.tool.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import com.gerenhua.tool.utils.CollectionUtil;
import com.gerenhua.tool.utils.FileUtil;
//import com.watchdata.numanag.dao.INumberDao;

/**
 * @author landon E-mail:landonyongwen@126.com
 * @version 创建时间：2012-2-14 下午05:13:01 类说明
 */

public class ConfigPanel extends AbstractPanle {

	// private JTextPane logTextArea;
	private static Logger log = Logger.getLogger(ConfigPanel.class);
	private JTable table;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5031435350526969832L;

	public ConfigPanel() {
		super();
		setPreferredSize(new Dimension(843, 460));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setBackground(new Color(193, 210, 240));
		// logTextArea = new JTextPane();
		final JButton importButton = new JButton();
		final JButton generateButton = new JButton();

		generateButton.setEnabled(false);
		// String[] ruleArray = new String[] { "固定值", "动态增长数", "校检位", "随机数" };
		// final SimpleDateFormat sf=new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		final BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(10);
		borderLayout.setVgap(10);
		final JPanel ruleListPanel = new JPanel();
		ruleListPanel.setSize(780, 200);
		ruleListPanel.setPreferredSize(new Dimension(780, 220));
		//ruleListPanel.setBackground(new Color(193, 210, 240));
		add(ruleListPanel);

		JPanel tablePanel;
		ruleListPanel.setLayout(new BorderLayout(0, 0));
		tablePanel = new JPanel();
		ruleListPanel.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setPreferredSize(new Dimension(680, 180));
		//tablePanel.setBackground(new Color(193, 210, 240));
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));

		final JScrollPane scrollPane = new JScrollPane();
		tablePanel.add(scrollPane);

		ConfigTableModel configTableModel = new ConfigTableModel();
		table = new JTable();
		table.setModel(configTableModel);
		table.setRowHeight(25);

		JComboBox comboBoxTable = new JComboBox();
		final String[] comboxInTable = new String[] { "定长记录", "变长记录", "固定值","密钥"};
		comboBoxTable = new JComboBox(comboxInTable);
		comboBoxTable.setToolTipText("数据类型");
		comboBoxTable.setSelectedIndex(0);

		TableColumn tableColumn = table.getColumn("数据类型");
		tableColumn.setCellEditor(new DefaultCellEditor(comboBoxTable));

		table.setShowGrid(true);
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);

		JPanel buttonPanel;
		buttonPanel = new JPanel();
		ruleListPanel.add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setToolTipText("");
		buttonPanel.setPreferredSize(new Dimension(100, 180));

		final JPanel add_btn_panel = new JPanel();
		final FlowLayout flowLayout_addPanel = new FlowLayout();
		flowLayout_addPanel.setAlignment(FlowLayout.LEFT);
		add_btn_panel.setLayout(flowLayout_addPanel);
		//add_btn_panel.setBackground(new Color(193, 210, 240));
		buttonPanel.add(add_btn_panel);

		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (table.isEditing()) {
					// 通知表格的活动单元格退出编辑状态
					table.getCellEditor().stopCellEditing();
				}
				ConfigTableModel configTableModel = (ConfigTableModel) table.getModel();
				Vector<String> vector = new Vector<String>();
				vector.add("----");
				vector.add(comboxInTable[0]);
				vector.add("----");
				vector.add("----");
				vector.add("----");
				vector.add("----");
				vector.add("----");
				configTableModel.addRow(vector);
				//addLog("添加一行成功！");
			}
		});
		addButton.setText("添加");
		add_btn_panel.add(addButton);

		final JPanel del_btn_panel = new JPanel();
		final FlowLayout flowLayout_delPanel = new FlowLayout();
		flowLayout_delPanel.setAlignment(FlowLayout.LEFT);
		del_btn_panel.setLayout(flowLayout_delPanel);
		//del_btn_panel.setBackground(new Color(193, 210, 240));
		buttonPanel.add(del_btn_panel);

		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (table.isEditing()) {
					// 通知表格的活动单元格退出编辑状态
					table.getCellEditor().stopCellEditing();
				}
				int delIndex = table.getSelectedRow();
				if (delIndex != -1) {
					int i = JOptionPane.showConfirmDialog(null, "你确定要删除选中的表格行吗？", "消息", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (i == 1) {
						return;
					}
					ConfigTableModel tableModel = (ConfigTableModel) table.getModel();
					tableModel.delRow(delIndex);
				} else {
					JOptionPane.showMessageDialog(null, "请选择要删除的表格行！", "消息", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//addLog("删除一行成功！");
				// logTextArea.append(sf.format(new Date())+":删除一行成功！\n");
			}
		});
		delButton.setText("删除");
		del_btn_panel.add(delButton);

		final JPanel up_btn_Panel = new JPanel();
		final FlowLayout flowLayout_upPanel = new FlowLayout();
		flowLayout_upPanel.setAlignment(FlowLayout.LEFT);
		up_btn_Panel.setLayout(flowLayout_upPanel);
		//up_btn_Panel.setBackground(new Color(193, 210, 240));
		buttonPanel.add(up_btn_Panel);

		final JButton upButton = new JButton();
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (table.isEditing()) {
					// 通知表格的活动单元格退出编辑状态
					table.getCellEditor().stopCellEditing();
				}
				ConfigTableModel tableModel = (ConfigTableModel) table.getModel();
				int delIndex = table.getSelectedRow();
				if (delIndex != -1) {
					if (delIndex - 1 >= 0) {
						tableModel.upordown(delIndex, 0);
						table.changeSelection(delIndex - 1, 0, false, false);
					} else {
						JOptionPane.showMessageDialog(null, "已经是第一行！");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "请选择要移动的行！");
					return;
				}
				//addLog("上移至位置：" + delIndex);
				// logTextArea.append(sf.format(new
				// Date())+":上移至位置："+delIndex+"\n");
			}
		});
		upButton.setText("上移");
		up_btn_Panel.add(upButton);

		final JPanel down_btn_panel = new JPanel();
		final FlowLayout flowLayout_downPanel = new FlowLayout();
		flowLayout_downPanel.setAlignment(FlowLayout.LEFT);
		down_btn_panel.setLayout(flowLayout_downPanel);
		//down_btn_panel.setBackground(new Color(193, 210, 240));
		buttonPanel.add(down_btn_panel);

		final JButton downButton = new JButton();
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				ConfigTableModel tableModel = (ConfigTableModel) table.getModel();
				if (table.isEditing()) {
					// 通知表格的活动单元格退出编辑状态
					table.getCellEditor().stopCellEditing();
				}
				int delIndex = table.getSelectedRow();
				if (delIndex != -1) {

					if (delIndex + 1 <= table.getRowCount() - 1) {
						tableModel.upordown(delIndex, 1);
						table.changeSelection(delIndex + 1, 0, false, false);
					} else {
						JOptionPane.showMessageDialog(null, "已经是最后一行！");
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "请选择要移动的行！");
					return;
				}
				//addLog("下移至位置：" + (delIndex + 2));
				// logTextArea.append(sf.format(new
				// Date())+":下移至位置："+(delIndex+2)+"\n");
			}
		});
		downButton.setText("下移");
		down_btn_panel.add(downButton);

		final JPanel save_btn_panel = new JPanel();
		final FlowLayout flowLayout_save = new FlowLayout();
		flowLayout_save.setAlignment(FlowLayout.LEFT);
		save_btn_panel.setLayout(flowLayout_save);
		//save_btn_panel.setBackground(new Color(193, 210, 240));
		buttonPanel.add(save_btn_panel);

		final JButton saveButton = new JButton();
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (table.isEditing()) {
					// 通知表格的活动单元格退出编辑状态
					table.getCellEditor().stopCellEditing();
				}
				ConfigTableModel configTableModel = (ConfigTableModel) table.getModel();
				Vector<Vector<String>> data = configTableModel.getData();
				if (data.isEmpty()) {
					JOptionPane.showMessageDialog(null, "请设置生成规则！", "消息", JOptionPane.ERROR_MESSAGE);
					// JOptionPane.showMessageDialog(null, "请设置生成规则！");
					return;
				}
				/*int ret = CollectionUtil.checkData(data);
				// System.out.println("ret:"+ret);
				if (ret == -2) {
					addLog("保存失败: 动态增长数规则项有且只能有一项！");
					JOptionPane.showMessageDialog(null, "动态增长数规则项有且只能有一项！", "消息", JOptionPane.ERROR_MESSAGE);
					// JOptionPane.showMessageDialog(null, "动态增长数规则项有且只能有一项！");
					FileUtil.printVector(data);
					//configTableModel.setErrorCode(0);
					return;
				} else if (ret == 0) {
					FileUtil.writeVector(data);
					addLog("保存成功！");
					JOptionPane.showMessageDialog(null, "保存成功！", "消息", JOptionPane.INFORMATION_MESSAGE);
					generateButton.setEnabled(true);
					// logTextArea.append(sf.format(new Date())+":保存成功！\n");
				}
				 * else if (ret == 0 && configTableModel.getErrorCode() !=0) { JOptionPane.showMessageDialog(null, "第" + configTableModel.getErrorCode() + "行规则错误！", "消息", JOptionPane.ERROR_MESSAGE); // 选择新的行 table.changeSelection(configTableModel.getErrorCode()-1, 0, false, false); FileUtil.printVector(data); //System.out.println("cod:"+configTableModel.getErrorCode()); addLog("保存失败:"+"第" + configTableModel.getErrorCode() + "行规则错误！"); //JOptionPane.showMessageDialog(null, "保存失败！", "消息", JOptionPane.INFORMATION_MESSAGE); configTableModel.setErrorCode(0); return; }
				 else {
					addLog("保存失败:" + "第" + ret + "行规则错误！");
					JOptionPane.showMessageDialog(null, "第" + ret + "行规则错误！", "消息", JOptionPane.ERROR_MESSAGE);
					// 选择新的行
					table.changeSelection(ret - 1, 0, false, false);
					FileUtil.printVector(data);
					// JOptionPane.showMessageDialog(null, "保存失败！", "消息", JOptionPane.INFORMATION_MESSAGE);
					//configTableModel.setErrorCode(0);
					return;
				}*/
			}
		});
		saveButton.setText("保存");
		save_btn_panel.add(saveButton);
		
		JPanel deal_btn_Panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) deal_btn_Panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		buttonPanel.add(deal_btn_Panel);
		
		JButton dealButton = new JButton("应用");
		dealButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				
			}
		});
		deal_btn_Panel.add(dealButton);

		final JPanel operationPanel_wrap = new JPanel();
		operationPanel_wrap.setPreferredSize(new Dimension(800, 40));
		operationPanel_wrap.setLayout(new BorderLayout(0, 0));
		final JPanel operationPanel = new JPanel();
		final FlowLayout flowLayout_Opera = new FlowLayout();
		operationPanel.setLayout(flowLayout_Opera);
		operationPanel.setPreferredSize(new Dimension(795, 40));
		operationPanel_wrap.add(operationPanel);
		add(operationPanel_wrap);

		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NDC.push("[IMPORT DATA]");
				int retvar = JOptionPane.showConfirmDialog(operationPanel, "导入数据会清空原有数据，确定导入?", "消息", JOptionPane.YES_NO_OPTION);
				if (retvar == JOptionPane.YES_OPTION) {

					JFileChooser chooser = new JFileChooser(".");
					chooser.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件(*.txt)", "txt");
					chooser.addChoosableFileFilter(filter);
					chooser.setFileFilter(filter);
					int ret = chooser.showOpenDialog(null);

					if (ret != JFileChooser.APPROVE_OPTION) {
						return;
					}

					File f = chooser.getSelectedFile();
					addLog("正在导入数据，请等待！");
					//ImportNm importNm = new ImportNm(generateButton, importButton);
					//importNm.setFileName(f.getAbsolutePath());

					//new Thread(importNm).start();

				}
			}
		});
		importButton.setText("导入数据");
		operationPanel.add(importButton);

		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NDC.push("[GENERATE DATA]");
				int retvar = JOptionPane.showConfirmDialog(operationPanel, "生成数据会清空原有数据，确定生成?", "消息", JOptionPane.YES_NO_OPTION);
				if (retvar == JOptionPane.YES_OPTION) {
					GenerateNm gn = new GenerateNm(saveButton, importButton, generateButton);
					addLog("正在生成数据，请等待！");
					new Thread(gn).start();
				}

			}
		});
		generateButton.setText("生成数据");
		operationPanel.add(generateButton);

		final JPanel logPanel_wrap = new JPanel();
		logPanel_wrap.setPreferredSize(new Dimension(800, 200));
		logPanel_wrap.setLayout(new BorderLayout(0, 0));
		//logPanel_wrap.setBackground(new Color(193, 210, 240));
		final JPanel logPanel = new JPanel();
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.X_AXIS));
		logPanel.setPreferredSize(new Dimension(800, 180));
		logPanel.setBorder(new TitledBorder(null, "数据", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		//logPanel.setBackground(new Color(193, 210, 240));
		logPanel_wrap.add(logPanel);
		add(logPanel_wrap);

		JScrollPane jScrollPane = new JScrollPane(logTextArea);
		logPanel.add(jScrollPane);
	}

	public class GenerateNm implements Runnable {
		//private INumberDao numberDao;
		//public ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
		JButton saveButton;
		JButton importButton;
		JButton generateButton;
		boolean insertFlag = false;

		GenerateNm(JButton inSaveButton, JButton inImportButton, JButton inGenerateButton) {
			this.saveButton = inSaveButton;
			this.importButton = inImportButton;
			this.generateButton = inGenerateButton;
		}

		public void run() {

			/*// 删除数据
			try {
				saveButton.setEnabled(false);
				importButton.setEnabled(false);
				generateButton.setEnabled(false);
				//numberDao = (INumberDao) ctx.getBean("numberDao");
				//boolean flag = numberDao.deleteNumberResource();
				//if (!flag) {
					//addLog("删除数据失败，请检查！");
					//log.info("删除数据失败，请检查！");
					return;
				//}

				long beginIndex = 0;
				long endIndex = 0;
				String ruleValue = "";
				int Pos = 0;
				int count = 0;
				String totleStr = "";
				// 生成数据
				//RulesBean bean = new RulesBean();
				List<String> rulesList = FileUtil.readeFile();
				if (rulesList.isEmpty()) {
					addLog("规则没有定义，请检查！");
					log.info("规则没有定义，请检查！");
					return;
				}
				for (int i = 0; i < rulesList.size(); i++) {
					String[] temp = rulesList.get(i).split("[|]");

					if ("1".equals(temp[0])) {
						beginIndex = Integer.valueOf(temp[2]);
						endIndex = Integer.valueOf(temp[3]);
						count++;

					}

				}
				if (count != 1) {
					addLog("请配置动态增长数！");
					return;
				}
				long totleLen = endIndex - beginIndex;
				List dataList = new ArrayList();
				String[] temp = null;
				for (int k = 0; k <= totleLen; k++) {
					totleStr = "";
					for (int i = 0; i < rulesList.size(); i++) {

						ruleValue = "";
						temp = rulesList.get(i).split("[|]");
						int idx = Integer.valueOf(temp[0]);
						switch (idx) {
						case 0:// 固定值
							ruleValue = temp[2];
							break;
						case 1:// 动态增长数
							Pos = Integer.valueOf(temp[1]);
							ruleValue = String.format("%0" + Pos + "d", beginIndex);
							beginIndex = beginIndex + 1;
							break;

						case 3:// 随机数
							Pos = Integer.valueOf(temp[1]);
							ruleValue = getRandomNumber(Pos);
							break;
						default:
							break;
						}

						totleStr = totleStr + ruleValue;
					}
					dataList.add(totleStr);
					if ((k + 1) % 10000 == 0) {
						insertFlag = numberDao.insertNumberResource(dataList);
						dataList.clear();
					}
				}
				if (!dataList.isEmpty()) {
					insertFlag = numberDao.insertNumberResource(dataList);
					dataList.clear();
				}
				if (insertFlag) {
					addLog("生成数据成功！");
					log.info("生成数据成功！共生成" + (totleLen + 1) + "条数据");
				} else {
					addLog("生成数据失败！");
					log.info("生成数据失败！");
				}

			} catch (Exception e) {

				addLog("生成数据失败！");
				log.info("生成数据失败！出错原因" + e.getMessage());
			} finally {
				saveButton.setEnabled(true);
				importButton.setEnabled(true);
				generateButton.setEnabled(true);
				if (!insertFlag) {
					numberDao.deleteNumberResource();
				}
				NDC.pop();
				NDC.remove();
			}*/
		}

		/**
		 * 获取随机数
		 * 
		 * @param length
		 * @return
		 */
		public String getRandomNumber(int length) {
			Random random = new Random();
			StringBuffer buffer = new StringBuffer();

			for (int i = 0; i < length; i++) {
				buffer.append(random.nextInt(10));
			}
			return buffer.toString();
		}

	}

	//public class ImportNm implements Runnable {
	/*	private INumberDao numberDao;
		public ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
		JButton generateButton;
		JButton importButton;
		boolean insertFlag = false;

		ImportNm(JButton inGenButton, JButton inImportButton) {
			this.generateButton = inGenButton;
			this.importButton = inImportButton;
		}

		public void run() {

			try {
				// 删除数据

				numberDao = (INumberDao) ctx.getBean("numberDao");
				boolean flag = numberDao.deleteNumberResource();
				if (!flag) {
					addLog("删除数据失败，请检查！");
					return;
				}

				generateButton.setEnabled(false);
				importButton.setEnabled(false);
				long count = 1;
				List list = new ArrayList();
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(fileName));
					String temp = null;

					while ((temp = reader.readLine()) != null) {
						count++;
						list.add(temp);
						if (count % 10000 == 0) {
							insertFlag = numberDao.insertNumberResource(list);
							list.clear();
						}
					}

					if (!list.isEmpty()) {
						insertFlag = numberDao.insertNumberResource(list);
						list.clear();
					}
				} catch (Exception e) {
					log.info("导入数据失败！出错原因:" + e.getMessage());
					addLog("导入数据失败！");
					return;
				} finally {
					reader.close();
				}

				if (insertFlag) {
					addLog("导入数据成功！");
					log.info("导入数据成功！共生成" + (count - 1) + "条数据");
				} else {
					addLog("导入数据失败！");
				}
			} catch (Exception e) {
				addLog("导入数据失败！");

				log.info("导入数据失败！出错原因:" + e.getMessage());
			} finally {
				generateButton.setEnabled(true);
				importButton.setEnabled(true);
				if (!insertFlag) {
					numberDao.deleteNumberResource();
				}
				NDC.pop();
				NDC.remove();
			}*/

		//}

		String fileName;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

	//}

}
