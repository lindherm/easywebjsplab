package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.watchdata.cardcheck.configdao.CAInfo;
import com.watchdata.cardcheck.configdao.PublicKeyInfo;
import com.watchdata.cardcheck.dao.ICAPublicKeyConfigDao;
import com.watchdata.cardcheck.dao.pojo.CAPublicKeyConfig;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.FixedSizePlainDocument;
import com.watchdata.cardcheck.utils.PropertiesManager;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @description:CA密钥配置界面
 * 
 * @author: Desheng.Xu March 29, 2012
 * 
 * @version: 1.0.0
 * 
 * @modify:
 * 
 * @Copyright: watchdata
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CAPublicKeyConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
	private ICAPublicKeyConfigDao iCAkeyconfigDao;
	private JTextField ModuletextField;
	private JTextField HashtextField;
	private JTextField algorithmtextField;
	private JTextField EXPtextField;
	private JTextField CardTypetextField;
	public static JComboBox RIDCombox;
	private JComboBox IndextextField;
	Color colorGreen = new Color(192, 255, 192);
	private PropertiesManager pm = new PropertiesManager();
	
	private JTable table;
	private List<PublicKeyInfo> sdList;
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;
	
	private CAInfo caInfo=new CAInfo();
	private PublicKeyInfo publicKeyInfo=new PublicKeyInfo();
	private final String[] COLUMNS = new String[] {"RID","公钥索引","算法","公钥指数","哈希算法","模值" };
	/**
	 * Create the panel
	 */
	public CAPublicKeyConfigPanel() {
		super();
		setLayout(null);
		//setBorder(JTBorderFactory.createTitleBorder("CA公钥管理"));
		init();

		final JLabel caManageLabel = new JLabel();
		caManageLabel.setBounds(-4, 38, 97, 20);
		caManageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		caManageLabel.setFont(new Font("宋体", Font.BOLD, 12));
		caManageLabel.setText("CA公钥管理");
		add(caManageLabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(88, 48, 730, 20);
		add(separator);

		RIDCombox = new JComboBox();
		List<CAInfo> caInfos=caInfo.getCaInfos("CA_Info");
		for (CAInfo caInfo : caInfos) {
			RIDCombox.addItem(caInfo.getRid());
		}

		// CA密钥管理RID组合框消息响应函数，如果选中的RID的值发生改变，则重新显示其他项目的值
		RIDCombox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardTypetextField.setText(Config.getValue("CA_Info", RIDCombox.getSelectedItem().toString()));
			}
		});
		RIDCombox.setBounds(100, 70, 160, 20);
		add(RIDCombox);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mv.capublickeyconfig.banktype"));
		label_1.setBounds(270, 70, 97, 20);
		add(label_1);

		CardTypetextField = new JTextField();
		CardTypetextField.setBounds(382, 70, 160, 20);
		CardTypetextField.setEditable(false);
		add(CardTypetextField);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.capublickeyconfig.index"));
		label_2.setBounds(-4, 100, 97, 20);
		add(label_2);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setText(pm.getString("mv.capublickeyconfig.exp"));
		label_3.setBounds(205, 100, 97, 20);
		add(label_3);

		final JLabel label_4 = new JLabel();
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setText(pm.getString("mv.capublickeyconfig.algo"));
		label_4.setBounds(416, 100, 97, 20);
		add(label_4);

		final JLabel label_5 = new JLabel();
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setText(pm.getString("mv.capublickeyconfig.hashalgo"));
		label_5.setBounds(-4, 130, 97, 20);
		add(label_5);

		EXPtextField = new JTextField();
		EXPtextField.setBounds(312, 100, 140, 20);
		EXPtextField.setDocument(new FixedSizePlainDocument(2));
		EXPtextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
		add(EXPtextField);

		algorithmtextField = new JTextField();
		algorithmtextField.setBounds(523, 100, 120, 20);
		algorithmtextField.setDocument(new FixedSizePlainDocument(2));
		algorithmtextField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		add(algorithmtextField);

		HashtextField = new JTextField();
		HashtextField.setBounds(100, 130, 120, 20);
		HashtextField.setDocument(new FixedSizePlainDocument(2));
		HashtextField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		add(HashtextField);

		final JLabel label_6 = new JLabel();
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setText(pm.getString("mv.capublickeyconfig.module"));
		label_6.setBounds(205, 130, 97, 20);
		add(label_6);

		ModuletextField = new JTextField();
		ModuletextField.setBounds(312, 130, 345, 20);
		ModuletextField.setDocument(new FixedSizePlainDocument(2048));
		ModuletextField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				String c = String.valueOf(e.getKeyChar());
				Pattern pattern = Pattern.compile("[0-9a-fA-F]*");

				Matcher matcher = pattern.matcher(c);
				if (!(matcher.matches()))
					e.consume();
			}
		});
		add(ModuletextField);

		final JButton addButton1 = new JButton();
		addButton1.setText(pm.getString("mv.capublickeyconfig.del"));
		addButton1.setBounds(715, 82, 84, 21);
		add(addButton1);
		// 添加CA密钥按钮消息响应函数
		addButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delConfig();
			}
		});

		final JButton modifyButton = new JButton();
		modifyButton.setText(pm.getString("mv.capublickeyconfig.modify"));
		modifyButton.setBounds(715, 129, 84, 21);
		add(modifyButton);
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//updateConfig();
			}
		});
		String[] indextName = { "01", "02", "03", "04", "05", "06", "07", "08", "09" };
		IndextextField = new JComboBox(indextName);
		IndextextField.setBounds(100, 100, 120, 20);
		IndextextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO
				//showcapkconfig2();
			}
		});
		add(IndextextField);

		final JLabel label = new JLabel();
		label.setBounds(-4, 70, 97, 20);
		add(label);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setText("RID：");
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 160, 789, 527);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSurrendersFocusOnKeystroke(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(true);

		sdList =publicKeyInfo.getPKInfos("CA_Info"); 
		tableDataDisp();
		table.repaint();
		scrollPane.setViewportView(table);
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
		tableData = new Object[rowNum][6];
		for (int i = 0; i < rowNum; i++) {
			tableData[i][0] = sdList.get(i).getRid();
			tableData[i][1] = sdList.get(i).getIndex();
			tableData[i][2] = sdList.get(i).getArith();
			tableData[i][3] = sdList.get(i).getExp();
			tableData[i][4] = sdList.get(i).getHashArith();
			tableData[i][5] = sdList.get(i).getModule();
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
	private void init() {
		setName(pm.getString("mv.capublickeyconfig.name"));
	}

	// 删除配置
	private void delConfig() {
		CAPublicKeyConfig capkconfig = new CAPublicKeyConfig();
		capkconfig = null;
		String err = "";
		if (null == RIDCombox.getSelectedItem())
			err += pm.getString("mv.capublickeyconfig.ridnoexists");

		if (!err.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, err, pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int y = JOptionPane.showConfirmDialog(null, pm.getString("mv.capublickeyconfig.delconfirm"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (y == 1) {
			return;
		}

		capkconfig = iCAkeyconfigDao.getCAPublicKey(RIDCombox.getSelectedItem().toString().trim(), IndextextField.getSelectedItem().toString().trim());
		if (capkconfig != null) {
			if (iCAkeyconfigDao.delCAPublicKeyConfig(RIDCombox.getSelectedItem().toString().trim(), IndextextField.getSelectedItem().toString().trim())) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delsuccess"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);

				List<CAPublicKeyConfig> capkconfigList = iCAkeyconfigDao.getCAPublicKeyList(RIDCombox.getSelectedItem().toString().trim());
				if (capkconfigList.isEmpty()) {
					RIDCombox.removeAllItems();
					capkconfigList = iCAkeyconfigDao.findCAPublicKeyConfig();
					ArrayList<String> tmplist = new ArrayList<String>();
					for (int i = 0; i < capkconfigList.size(); i++) {
						if (!tmplist.contains(capkconfigList.get(i).GetRid()))
							RIDCombox.addItem(capkconfigList.get(i).GetRid());
						tmplist.add(capkconfigList.get(i).GetRid());
					}
				}
				//showcapkconfig();
			} else {
				JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delerr"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			List<CAPublicKeyConfig> capkconfigList = iCAkeyconfigDao.getCAPublicKeyList(RIDCombox.getSelectedItem().toString().trim());
			if (capkconfigList.get(0).GetIndex() != null) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delnoindex"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (iCAkeyconfigDao.delCAPublicKeyConfig(RIDCombox.getSelectedItem().toString().trim())) {
				JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delsuccess"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);

				RIDCombox.removeAllItems();
				capkconfigList = iCAkeyconfigDao.findCAPublicKeyConfig();
				ArrayList<String> tmplist = new ArrayList<String>();
				for (int i = 0; i < capkconfigList.size(); i++) {
					if (!tmplist.contains(capkconfigList.get(i).GetRid()))
						RIDCombox.addItem(capkconfigList.get(i).GetRid());
					tmplist.add(capkconfigList.get(i).GetRid());
				}
				//showcapkconfig();
			} else {
				JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delerr"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	private void getDefaultConfig() {
		List<CAPublicKeyConfig> capkconfigList = iCAkeyconfigDao.findDefaultCAPublicKeyConfig();

		if (capkconfigList.get(0).GetIndex() != null) {

		}

	}
}
