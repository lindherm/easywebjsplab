package com.watchdata.cardcheck.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.watchdata.cardcheck.dao.IStaticDataDao;
import com.watchdata.cardcheck.dao.pojo.StaticData;
import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.FileOpers;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;
import com.watchdata.commons.lang.WDAssert;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TestDataConfigPanel.java
 * 
 * @description: 静态数据配置界面
 * 
 * @author: pei.li 2012-3-28
 * 
 * @version:1.0.0
 * 
 * @modify：
 * 
 * @Copyright：watchdata
 */
public class TestDataConfigPanel extends JPanel {

	private static final long serialVersionUID = -4287626568370654541L;
	public static JTable table;
	private JLabel tagLabel;
	private JLabel appTypeLabel;
	public static JComboBox appTypeComboBox;
	public static JTextField tagTextField;
	private JButton addButton;
	public static JButton delButton;
	public JComboBox comboBox;
	private JButton saveButton;
	private IStaticDataDao staticDataDao;
	private StaticData staticData;
	private PropertiesManager pm = new PropertiesManager();
	public ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
	private final String[] COLUMNS = new String[] {"DGI", "TAG", "值", "结果" };
	private List<StaticData> sdList = new ArrayList<StaticData>();
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;
	private FileOpers fileOpers = new FileOpers();
	private String filePath = pm.getString("mv.testdata.exportFilepath");
	private String[] comboData = { pm.getString("mv.testdata.appType"), pm.getString("mv.testdata.appType2"), pm.getString("mv.testdata.appType3") };
	public static JProgressBar progressBar;
	public CommonAPDU apduHandler;
	public static Log log=new Log();

	public TestDataConfigPanel() {
		super();
		setLayout(null);
		setName(pm.getString("mv.testdata.name"));
		//setBorder(JTBorderFactory.createTitleBorder(pm.getString("mv.menu.dataConfig")));

		apduHandler=new CommonAPDU();
		staticData = new StaticData();
		staticDataDao = (IStaticDataDao) ctx.getBean("staticDataDao");

		tagLabel = new JLabel();
		tagLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tagLabel.setBounds(238, 128, 40, 20);
		tagLabel.setText(pm.getString("mv.testdata.tag"));
		add(tagLabel);

		tagTextField = new JTextField();
		tagTextField.setBounds(288, 128, 183, 20);
		tagTextField.setDocument(new FixedSizePlainDocument(4));
		tagTextField.setToolTipText(pm.getString("mv.testdata.tagTip"));
		tagTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(c >= 'A' && c <= 'F') && Character.isLetter(c)) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		add(tagTextField);

		appTypeLabel = new JLabel();
		appTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		appTypeLabel.setText(pm.getString("mv.testdata.appTypeName"));
		appTypeLabel.setBounds(288, 40, 80, 20);
		add(appTypeLabel);

		appTypeComboBox = new JComboBox();
		ComboBoxModel comboBoxModel = new DefaultComboBoxModel(getAppTypeData());
		appTypeComboBox.setModel(comboBoxModel);
		appTypeComboBox.setBounds(376, 40, 135, 20);
		add(appTypeComboBox);

		addButton = new JButton();
		addButton.setText("增加");
		addButton.setBounds(725, 127, 84, 21);
		add(addButton);

		/*
		 * final JPanel panel_1 = new JPanel(); panel_1.setBorder(JTBorderFactory.createTitleBorder(pm .getString("mv.testdata.editData"))); panel_1.setLayout(new BorderLayout()); splitPane.setRightComponent(panel_1);
		 * 
		 * final JSplitPane splitPane_1 = new JSplitPane(); splitPane_1.setVisible(true); splitPane_1.addComponentListener(new ComponentAdapter() { public void componentResized(ComponentEvent e) { splitPane_1.setDividerLocation(0.7); } }); panel_1.add(splitPane_1, BorderLayout.CENTER);
		 * 
		 * buttonPanel = new JPanel(); buttonPanel.setLayout(null); splitPane_1.setRightComponent(buttonPanel);
		 */

		final JLabel editDataLabel = new JLabel();
		editDataLabel.setBounds(0, 101, 97, 20);
		editDataLabel.setHorizontalAlignment(SwingConstants.LEFT);
		editDataLabel.setFont(new Font(pm.getString("mv.applicaiton.font"), Font.BOLD, 12));
		editDataLabel.setText(pm.getString("mv.testdata.editData"));
		add(editDataLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(53, 112, 730, 20);
		add(separator_1);

		delButton = new JButton();
		delButton.setText(pm.getString("mv.testdata.delete"));
		delButton.setBounds(725, 199, 84, 21);
		add(delButton);

		saveButton = new JButton();
		saveButton.setText(pm.getString("mv.testdata.edit"));
		saveButton.setBounds(725, 230, 84, 21);
		add(saveButton);

		/*
		 * final JPanel panel_2 = new JPanel(); panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS)); splitPane_1.setLeftComponent(panel_2);
		 */

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 171, 710, 510);
		add(scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		sdList = staticDataDao.searchStaticData();
		tableDataDisp();
		scrollPane.setViewportView(table);
		
		JLabel lblAid = new JLabel();
		lblAid.setText("AID：");
		lblAid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAid.setBounds(16, 40, 64, 20);
		add(lblAid);
		
		comboBox = new JComboBox();
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.setLogDialogOff();
				String reader=Config.getValue("Terminal_Data", "reader");
				HashMap<String, String> res = apduHandler.reset(reader);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(res.get("sw"))) {
					JOptionPane.showMessageDialog(null,res.get("sw"));
				}
				
				HashMap<String, String> result = apduHandler.select(Constants.PSE);
				if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
					JOptionPane.showMessageDialog(null,res.get("sw"));
				}

				if (WDAssert.isNotEmpty(result.get("88"))) {
					List<HashMap<String, String>>  readDirList = apduHandler.readDir(result.get("88"));
					comboBox.removeAllItems();
					for (HashMap<String, String> readdir : readDirList) {
						String aid = readdir.get("4F");
						if (!WDAssert.isEmpty(aid)) {
							comboBox.addItem(aid);
						}
					}
				}
			}
		});
		comboBox.setBounds(88, 40, 183, 20);
		add(comboBox);
		
		JLabel lblDgi = new JLabel();
		lblDgi.setText("DGI：");
		lblDgi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDgi.setBounds(16, 128, 64, 20);
		add(lblDgi);
		
		textField = new JTextField();
		textField.setToolTipText("请输入两位或四位16进制字符！");
		textField.setBounds(88, 128, 135, 20);
		add(textField);
		
		JButton button = new JButton();
		button.setText("检测");
		button.setBounds(725, 261, 84, 21);
		add(button);
		
		JLabel label = new JLabel("数据模板：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setBounds(0, 70, 80, 21);
		add(label);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(88, 71, 600, 21);
		add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("打开");
		btnNewButton.setBounds(725, 71, 84, 21);
		add(btnNewButton);

		addButton.addActionListener(addActionListener);
		delButton.addActionListener(delActionListener);
		saveButton.addActionListener(saveActionListener);

		/*
		 * progressBar = new JProgressBar(); progressBar.setBounds(0, 360, 195, 14); buttonPanel.add(progressBar); progressBar.setValue(0); progressBar.setVisible(false);
		 */
	}

	// 添加按钮监听事件
	private ActionListener addActionListener = new ActionListener() {
		public void actionPerformed(final ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.addInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			boolean success = true;
			if (pm.getString("mv.testdata.select").equals(appTypeComboBox.getSelectedItem().toString())) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.appTypeEmpty"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else if (tagTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.tagEmpty"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			}/*
			 * else if (valueTextField.getText().isEmpty()) { JOptionPane.showMessageDialog(null, pm .getString("mv.testdata.tagValueEmpty"), pm .getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE); }
			 */else {
				// 将数据添加到数据库中
				if (tagCheck(tagTextField.getText())) {
					staticData.setTag(tagTextField.getText());
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.tableTagInfo"));
					return;
				}
				/*if (!valueTextField.getText().isEmpty()) {
					staticData.setLength(getTagLen(valueTextField.getText()));
					staticData.setOriValue(valueTextField.getText());
				} else {
					staticData.setLength(0);
					staticData.setOriValue(null);
				}*/
				//staticData.setDscrpt(dscrptTextField.getText().isEmpty() ? null : dscrptTextField.getText());
				staticData.setAppType(appTypeComboBox.getSelectedItem().toString());
				/*
				 * List<String> tag = staticDataDao.searchTagByAppType(appTypeComboBox .getSelectedItem() .toString()); ArrayList<String> tagList = new ArrayList<String>(); for(Object tg : tag){ tagList.add((String) ((Map) tg).get("TAG")); }
				 */
				boolean containSame = false;
				for (StaticData sdData : sdList) {
					if (sdData.getAppType().equals(staticData.getAppType()) && sdData.getTag().equals(staticData.getTag())) {
						containSame = true;
					}
				}
				if (!containSame) {
					success = staticDataDao.insertTag(staticData);
					if (success) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.insertSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						// 将添加的数据从数据库中取出来在table中显示出来
						sdList = staticDataDao.searchStaticData();
						tableDataDisp();
						table.repaint();
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.insertFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.tagReapted"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				}

			}
		}
	};

	// 删除按钮监听事件
	private ActionListener delActionListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent arg0) {
			/*
			 * DelThread delThread = new DelThread(); delThread.addListener(new DelListener(){
			 * 
			 * @Override public void del(){
			 */
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.saveBefoeDelete"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int selectedNum = table.getSelectedRows().length;

			int[] selectIndex = table.getSelectedRows();
			if (selectedNum == 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else if (selectedNum >= 1) {
				int y = JOptionPane.showConfirmDialog(null, pm.getString("mv.testdata.deleteInfoconfirm"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					return;
				}
				List<Integer> delDatas = new ArrayList<Integer>();
				for (int i = 0; i < selectedNum; i++) {
					int idStr = sdList.get(selectIndex[i]).getId();
					delDatas.add(idStr);
				}
				if (staticDataDao.batchDel(delDatas)) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					sdList = staticDataDao.searchStaticData();
					tableDataDisp();
					table.repaint();
				} else {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				}
			}
			appTypeComboBox.setModel(new DefaultComboBoxModel(getAppTypeData()));
			appTypeComboBox.repaint();
			tagTextField.setText("");
		//	valueTextField.setText("");
			//dscrptTextField.setText("");
		}
	};

	// 保存按钮监听事件
	private ActionListener saveActionListener = new ActionListener() {
		@Transactional
		public void actionPerformed(final ActionEvent e) {
			int selectedNum = table.getSelectedRows().length;
			if (pm.getString("mv.testdata.edit").equals(saveButton.getText())) {
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.editInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else if (selectedNum >= 1) {
					final int[] selectedIndex = table.getSelectedRows();
					table.setModel(new DefaultTableModel(tableData, COLUMNS) {
						private static final long serialVersionUID = -3564556245515260000L;

						// 将选中行设为可编辑状态
						public boolean isTrue(int rowNum, int row) {
							return row == rowNum;
						}

						public boolean f(int num, int row, int[] selectedIndex) {
							if (num == 1) {
								return isTrue(selectedIndex[num - 1], row);
							} else {
								return f(num - 1, row, selectedIndex) || isTrue(selectedIndex[num - 1], row);
							}
						}

						@Override
						public boolean isCellEditable(int row, int col) {
							if (f(selectedIndex.length, row, selectedIndex)) {
								return true;
							} else {
								return false;
							}
						}
					});
					paintColorRow(selectedIndex);
					saveButton.setText(pm.getString("mv.testdata.save"));
				}
			} else if (pm.getString("mv.testdata.save").equals(saveButton.getText())) {
				if (table.isEditing()) {
					int row = table.getEditingRow();
					int col = table.getEditingColumn();
					table.getCellEditor(row, col).stopCellEditing();
				}
				List<Integer> indexRow = getEditableRow();
				List<StaticData> editDatas = new ArrayList<StaticData>();
				if (indexRow.size() > 0) {
					for (int i = 0; i < indexRow.size(); i++) {
						StaticData staticData = new StaticData();
						staticData.setId(sdList.get(indexRow.get(i)).getId());
						String a = table.getValueAt(indexRow.get(i), 0).toString();
						if (table.getValueAt(indexRow.get(i), 0) == null || "".equals(table.getValueAt(indexRow.get(i), 0).toString())) {
							JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.apptypeBlank"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						} else if (table.getValueAt(indexRow.get(i), 0).toString().length() > 32) {
							JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.apptypeTooLong"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							staticData.setAppType(table.getValueAt(indexRow.get(i), 0).toString());
						}

						if (tagCheck(table.getValueAt(indexRow.get(i), 1).toString())) {
							staticData.setTag(table.getValueAt(indexRow.get(i), 1) == null ? null : table.getValueAt(indexRow.get(i), 1).toString());
						} else {
							JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.tableTagInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (table.getValueAt(indexRow.get(i), 2) == null) {
							staticData.setOriValue(null);
						} else {
							if (table.getValueAt(indexRow.get(i), 2).toString().length() > 2048) {
								JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.valueTooLong"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								staticData.setOriValue(table.getValueAt(indexRow.get(i), 2).toString());
							}
						}

						staticData.setLength(table.getValueAt(indexRow.get(i), 2) == null ? 0 : getTagLen(table.getValueAt(indexRow.get(i), 2).toString()));

						if (table.getValueAt(indexRow.get(i), 3) == null) {
							staticData.setDscrpt(null);
						} else {
							if (table.getValueAt(indexRow.get(i), 3).toString().length() > 32) {
								JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.descTooLong"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								staticData.setDscrpt(table.getValueAt(indexRow.get(i), 3).toString());
							}

						}
						editDatas.add(staticData);
					}
					// 对于处于编辑状态的每行数据进行判断，判断数据库中是否有与之id不同但是tag和appType相同的记录，若有则不符合规则终止保存；
					// 若全部通过判断则可保存；由于数据库查询会使得操作速度变慢，且sdList中的数据始终与数据库中的记录保持一致，因此在此与sdList中的数据进行了比较
					for (StaticData esd : editDatas) {
						/*
						 * if(staticDataDao.searchByTagAndAppType(esd.getAppType( ), esd.getTag()) >= 1 && staticDataDao.searchByTagAndAppTypeID (esd.getAppType(), esd.getTag(), esd.getId()) == 0){ JOptionPane.showMessageDialog(null, pm .getString("mv.testdata.tagReapted"), pm .getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE); return; }
						 */

						List<StaticData> sameAppTypeTag = new ArrayList<StaticData>();
						for (StaticData sd : sdList) {
							if (sd.getAppType().equals(esd.getAppType()) && sd.getTag().equals(esd.getTag()) && sd.getId() != esd.getId()) {
								sameAppTypeTag.add(sd);
							}
						}
						if (sameAppTypeTag.size() > 0) {
							JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.tagReapted"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					/* if(!tagList.contains(tagTextField.getText())){ */
					if (staticDataDao.batchSaveUpdate(editDatas)) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.saveEditedSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						sdList = staticDataDao.searchStaticData();
						tableDataDisp();
						table.repaint();
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.saveEditedFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}
					saveButton.setText(pm.getString("mv.testdata.edit"));
				}
			}
			appTypeComboBox.setModel(new DefaultComboBoxModel(getAppTypeData()));
			appTypeComboBox.repaint();
			tagTextField.setText("");
			//valueTextField.setText("");
			//dscrptTextField.setText("");
		}
	};

	// 导入按钮监听事件
	private ActionListener importActionListner = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.importInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (table.getRowCount() != 0) {
				int ret = JOptionPane.showConfirmDialog(null, pm.getString("mv.testdata.deleteBeforeImport"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.OK_CANCEL_OPTION);
				if (ret == JOptionPane.OK_OPTION) {
					importFile();
				} else if (ret == JOptionPane.CANCEL_OPTION) {
					return;
				}
			} else {
				importFile();
			}
		}
	};

	// 导出按钮监听事件
	private ActionListener exportActionListner = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getEditableRow().size() > 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.exportInfo"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int selectedNum = table.getSelectedRows().length;
			int[] selectedIndex = table.getSelectedRows();
			List<StaticData> exportDatas = new ArrayList<StaticData>();
			if (selectedNum == 0) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.selectExport"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else if (table.getSelectedRows().length > 0) {
				for (int i = 0; i < selectedNum; i++) {
					StaticData staticData = new StaticData();
					staticData.setAppType(table.getValueAt(selectedIndex[i], 0) == null ? null : table.getValueAt(selectedIndex[i], 0).toString());
					staticData.setTag(table.getValueAt(selectedIndex[i], 1) == null ? null : table.getValueAt(selectedIndex[i], 1).toString());

					staticData.setOriValue(table.getValueAt(selectedIndex[i], 2) == null ? null : table.getValueAt(selectedIndex[i], 2).toString());
					staticData.setLength(table.getValueAt(selectedIndex[i], 2) == null ? 0 : getTagLen(table.getValueAt(selectedIndex[i], 2).toString()));
					staticData.setDscrpt(table.getValueAt(selectedIndex[i], 3) == null ? null : table.getValueAt(selectedIndex[i], 3).toString());

					exportDatas.add(staticData);
				}
				Collections.sort(exportDatas);
				filePath = fileOpers.getFilePath();
				if (filePath != null) {
					if (!filePath.endsWith("txt")) {
						filePath = filePath + ".txt";
					}
					File f = new File(filePath);
					if (f.exists()) {
						int y = JOptionPane.showConfirmDialog(null, pm.getString("mv.testdata.fileExist"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (y == 1) {
							return;
						}
					}
					if (fileOpers.writeFile(filePath, exportDatas)) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.fileExportSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.fileExportFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		}
	};
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * @Title: getLenFromField
	 * @Description 根据Tag的Value值获得其长度
	 * @param
	 * @return le Tag的value值的长度
	 * @throws
	 */
	public int getTagLen(String tagValue) {
		int le = 0;
		String matchStr = "[\\da-fA-F]{" + tagValue.length() + "}";
		if (tagValue.length() % 2 == 0 && tagValue.matches(matchStr)) {
			le = tagValue.length() / 2;
		} else {
			le = tagValue.length();
		}
		return le;
	}

	/**
	 * @Title: tableDataDisp
	 * @Description 将从数据库中查出的数据显示在table中
	 * @param
	 * @return
	 * @throws
	 */
	public void tableDataDisp() {
		int rowNum = sdList.size();
		tableData = new Object[rowNum][4];
		for (int i = 0; i < rowNum; i++) {
			tableData[i][0] = sdList.get(i).getAppType();
			tableData[i][1] = sdList.get(i).getTag();
			tableData[i][2] = sdList.get(i).getOriValue();
			tableData[i][3] = sdList.get(i).getDscrpt();
		}
		testDataTableModel = new DefaultTableModel(tableData, COLUMNS) {
			private static final long serialVersionUID = -9082031840487910439L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(testDataTableModel);
	}

	/**
	 * @Title: getEditableRow
	 * @Description 获取当前处于可编辑状态的行号
	 * @param
	 * @return 表格中可编辑的行号
	 * @throws
	 */
	public List<Integer> getEditableRow() {
		List<Integer> selectedRowNum = new ArrayList<Integer>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.isCellEditable(i, 0)) {
				selectedRowNum.add(i);
			}
		}
		return selectedRowNum;
	}

	/**
	 * @Title: getAppTypeData
	 * @Description 获取应用类型的全部选项，主要是为了显示通过导入方式添加进数据库的应用类型
	 * @param
	 * @return 所有应用类型种类
	 * @throws
	 */
	public String[] getAppTypeData() {
		Set<String> comboSet = new HashSet<String>();
		if (sdList.size() != 0) {
			for (StaticData sdData : sdList) {
				comboSet.add(sdData.getAppType());
			}
		}
		/* Set<String> comboSet = staticDataDao.searchAppType(); */
		for (int i = 0; i < comboData.length; i++) {
			comboSet.add(comboData[i]);
		}
		int i = 0;
		String[] comboDataAll = new String[comboSet.size() + 1];

		comboDataAll[i] = pm.getString("mv.testdata.select");
		Iterator<String> iterator = comboSet.iterator();
		while (iterator.hasNext()) {
			comboDataAll[++i] = iterator.next().toString();
		}
		return comboDataAll;
	}

	/**
	 * @title TestDataConfigPanel.java
	 * @description table的行渲染器，用来设置行背景色
	 * @author pei.li 2012-3-28
	 * @version 1.0.0
	 * @modify
	 * @copyright watchdata
	 */
	private class RowRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -9128946524399930570L;
		private int[] selectedIndex;

		public boolean isTrue(int rowNum, int row) {
			return row == rowNum;
		}

		public boolean f(int num, int row, int[] selectedIndex) {
			if (num == 1) {
				return isTrue(selectedIndex[num - 1], row);
			} else {
				return f(num - 1, row, selectedIndex) || isTrue(selectedIndex[num - 1], row);
			}
		}

		public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (f(selectedIndex.length, row, selectedIndex))
				setBackground(Color.green);
			else {
				setBackground(Color.white);
			}
			return super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
		}

		public void setSelectedIndex(int[] selectedIndex) {
			this.selectedIndex = selectedIndex;
		}

	}

	/**
	 * @Title: paintColorRow
	 * @Description 为table的特定行设置背景颜色
	 * @param selectedIndex选中行索引
	 *            ，即需要设置背景颜色的行索引
	 * @return
	 * @throws
	 */
	public void paintColorRow(int[] selectedIndex) {
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			RowRenderer rowRenderer = new RowRenderer();
			rowRenderer.setSelectedIndex(selectedIndex);
			tc.setCellRenderer(rowRenderer);
		}
	}

	/**
	 * @Title: tagCheck
	 * @Description 检查tag是否为2或4位16进制字符
	 * @param tag
	 *            tag字符串
	 * @return true表示符合约束条件，false表示不符合
	 * @throws
	 */
	public static boolean tagCheck(String tag) {
		boolean isTrue = true;
		isTrue = (tag.matches("[0-9a-fA-F]{2}") || tag.matches("[0-9A-F]{4}")) ? true : false;
		return isTrue;
	}

	/**
	 * @Title: importFile
	 * @Description 获取将要的导入文件路径，将文件内容导入
	 * @param
	 * @return
	 * @throws
	 */
	public void importFile() {
		boolean success = true;
		String filePath = fileOpers.getFilePath();
		if (filePath != null) {
			if (!filePath.endsWith("txt")) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.fileTypeUnmatch"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
			} else {
				success = staticDataDao.delAllTag();
				sdList = staticDataDao.searchStaticData();
				tableDataDisp();
				table.repaint();
				if (!success) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.deleteFailedBimport"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
					return;
				}/*
				 * else{ sdList = staticDataDao.searchStaticData(); tableDataDisp(); table.repaint(); }
				 */
				final List<StaticData> importDatas = fileOpers.readFile(filePath);
				if (importDatas != null) {
					success = staticDataDao.batchInsert(importDatas);
					if (success) {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.importSuccess"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						sdList = staticDataDao.searchStaticData();
						tableDataDisp();
						table.repaint();
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.testdata.importFailed"), pm.getString("mv.testdata.InfoWindow"), JOptionPane.INFORMATION_MESSAGE);
						sdList = staticDataDao.searchStaticData();
						tableDataDisp();
						table.repaint();
					}
				}
				appTypeComboBox.setModel(new DefaultComboBoxModel(getAppTypeData()));
				appTypeComboBox.repaint();
				tagTextField.setText("");
				//dscrptTextField.setText("");
				//valueTextField.setText("");
			}
		}
	}
}
