package com.gerenhua.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.gerenhua.cardcheck.configdao.CAInfo;
import com.gerenhua.cardcheck.configdao.PublicKeyInfo;
import com.gerenhua.cardcheck.utils.Config;
import com.gerenhua.cardcheck.utils.FixedSizePlainDocument;
import com.gerenhua.cardcheck.utils.PropertiesManager;

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
public class CAPublicKeyConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ModuletextField;
	private JTextField HashtextField;
	private JTextField algorithmtextField;
	private JTextField EXPtextField;
	private JTextField CardTypetextField;
	public static JComboBox RIDCombox;
	Color colorGreen = new Color(192, 255, 192);
	private PropertiesManager pm = new PropertiesManager();

	private JTable table;
	private List<PublicKeyInfo> sdList;
	private DefaultTableModel testDataTableModel = null;
	private Object[][] tableData = null;

	private CAInfo caInfo = new CAInfo();
	private PublicKeyInfo publicKeyInfo = new PublicKeyInfo();
	private final String[] COLUMNS = new String[] { "RID", "公钥索引", "算法", "公钥指数", "哈希算法", "模值" };
	private JTextField textField;

	private JDialog dialog = new JDialog();
	private JEditorPane ep = new JEditorPane();
	private JScrollPane dlgscrollPane = new JScrollPane(ep);

	/**
	 * Create the panel
	 */
	public CAPublicKeyConfigPanel() {
		super();
		setLayout(null);
		//setBorder(JTBorderFactory.createTitleBorder("CA公钥管理"));
		init();

		RIDCombox = new JComboBox();
		List<CAInfo> caInfos = caInfo.getCaInfos("CA_Info");
		for (CAInfo caInfo : caInfos) {
			RIDCombox.addItem(caInfo.getRid());
		}

		// CA密钥管理RID组合框消息响应函数，如果选中的RID的值发生改变，则重新显示其他项目的值
		RIDCombox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardTypetextField.setText(Config.getValue("CA_Info", RIDCombox.getSelectedItem().toString()));
			}
		});
		RIDCombox.setBounds(104, 10, 160, 20);
		add(RIDCombox);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setText(pm.getString("mv.capublickeyconfig.banktype"));
		label_1.setBounds(274, 10, 97, 20);
		add(label_1);

		CardTypetextField = new JTextField();
		CardTypetextField.setBounds(386, 10, 160, 20);
		CardTypetextField.setEditable(false);
		add(CardTypetextField);

		final JLabel label_2 = new JLabel();
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setText(pm.getString("mv.capublickeyconfig.index"));
		label_2.setBounds(0, 40, 97, 20);
		add(label_2);

		final JLabel label_3 = new JLabel();
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setText(pm.getString("mv.capublickeyconfig.exp"));
		label_3.setBounds(209, 40, 97, 20);
		add(label_3);

		final JLabel label_4 = new JLabel();
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setText(pm.getString("mv.capublickeyconfig.algo"));
		label_4.setBounds(420, 40, 97, 20);
		add(label_4);

		final JLabel label_5 = new JLabel();
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setText(pm.getString("mv.capublickeyconfig.hashalgo"));
		label_5.setBounds(0, 70, 97, 20);
		add(label_5);

		EXPtextField = new JTextField();
		EXPtextField.setBounds(316, 40, 140, 20);
		EXPtextField.setDocument(new FixedSizePlainDocument(6));
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
		algorithmtextField.setBounds(527, 40, 120, 20);
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
		HashtextField.setBounds(104, 70, 120, 20);
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
		label_6.setBounds(209, 70, 97, 20);
		add(label_6);

		ModuletextField = new JTextField();
		ModuletextField.setBounds(316, 70, 345, 20);
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

		final JButton delButton1 = new JButton();
		delButton1.setText(pm.getString("mv.capublickeyconfig.del"));
		delButton1.setBounds(719, 40, 84, 21);
		delButton1.setFocusPainted(false);
		delButton1.setBorderPainted(false);
		add(delButton1);
		// 删除
		delButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedNum = table.getSelectedRows().length;
				int[] selectIndex = table.getSelectedRows();
				if (selectedNum == 0) {
					JOptionPane.showMessageDialog(null, pm.getString("mv.aidconfig.deleteInfo"), pm.getString("mv.aidconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
				} else if (selectedNum >= 1) {

					List<String> delDatas = new ArrayList<String>();
					for (int i = 0; i < selectedNum; i++) {
						String rid = sdList.get(selectIndex[i]).getRid();
						String index = sdList.get(selectIndex[i]).getIndex();
						String sectionName = rid + "_CA_" + index;
						delDatas.add(sectionName);
					}
					int y = JOptionPane.showConfirmDialog(null, "确定要删除" + delDatas.toString() + "?", pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (y == JOptionPane.NO_OPTION) {
						return;
					}
					if (publicKeyInfo.del(delDatas)) {
						sdList = publicKeyInfo.getPKInfos("CA_Info");
						tableDataDisp();
						table.repaint();
						JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delsuccess"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, pm.getString("mv.capublickeyconfig.delerr"), pm.getString("mv.capublickeyconfig.infoWindow"), JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		final JButton modifyButton = new JButton();
		modifyButton.setText(pm.getString("mv.capublickeyconfig.modify"));
		modifyButton.setBounds(719, 69, 84, 21);
		modifyButton.setFocusPainted(false);
		modifyButton.setBorderPainted(false);
		add(modifyButton);
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PublicKeyInfo publicKeyInfo = new PublicKeyInfo();
				publicKeyInfo.setRid(RIDCombox.getSelectedItem().toString());
				publicKeyInfo.setArith(algorithmtextField.getText());
				publicKeyInfo.setExp(EXPtextField.getText());
				publicKeyInfo.setHashArith(HashtextField.getText());
				publicKeyInfo.setIndex(textField.getText());
				publicKeyInfo.setModule(ModuletextField.getText());

				String sectionName = publicKeyInfo.getRid() + "_CA_" + publicKeyInfo.getIndex();
				if (Config.ini.hasSection(sectionName)) {
					JOptionPane.showMessageDialog(null, sectionName + "已经存在,保存失败！", "提示框", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					publicKeyInfo.add(sectionName, publicKeyInfo);
					sdList = publicKeyInfo.getPKInfos("CA_Info");
					tableDataDisp();
					table.repaint();
					JOptionPane.showMessageDialog(null, sectionName + "保存成功！", "提示框", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		final JLabel label = new JLabel();
		label.setBounds(0, 10, 97, 20);
		add(label);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setText("RID：");

		JPanel panel = new JPanel();
		panel.setBounds(10, 100, 789, 597);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (e.getClickCount() == 2) {
						int row = table.rowAtPoint(e.getPoint());
						int colum = table.columnAtPoint(e.getPoint());
						Object ob = table.getValueAt(row, colum);
						Point p = e.getLocationOnScreen();
						dialog.setLocation(p);
						ep.setText(ob.toString());
						dialog.setVisible(true);
					}
				}
			}
		});
		sdList = publicKeyInfo.getPKInfos("CA_Info");
		tableDataDisp();
		table.repaint();
		scrollPane.setViewportView(table);

		textField = new JTextField();
		textField.setDocument(new FixedSizePlainDocument(2));
		textField.setBounds(104, 40, 120, 20);
		add(textField);
		textField.setColumns(10);

		dialog.setSize(400, 200);
		dialog.getContentPane().add(dlgscrollPane);
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
}
