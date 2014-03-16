/*
 * ListPane.java
 *
 * Created on June 8, 2007, 9:17 PM
 */

package com.watchdata.cardcheck.utils.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.watchdata.cardcheck.app.Application;
import com.watchdata.cardcheck.app.RightPanel;
import com.watchdata.cardcheck.panel.AIDConfigPanel;
import com.watchdata.cardcheck.panel.AtmPanel;
import com.watchdata.cardcheck.panel.CardReaderPanel;
import com.watchdata.cardcheck.panel.FaceDialog;
import com.watchdata.cardcheck.panel.TerminalLimitConfigPanel;
import com.watchdata.cardcheck.panel.TerminalPerformanceConfigPanel;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.PropertiesManager;

/**
 * 模拟Windows文件浏览器左边折叠面板的一个面板组件 其实就是一些JLabel
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class ListPane extends JPanel {
	private static final int HORZ_PAD = 12;
	private static final int VERT_PAD = 6;
	private PropertiesManager pm = new PropertiesManager();

	/**
	 * Creates new form ListPane
	 */
	public ListPane() {
		initComponents();
		Border b = BorderFactory.createCompoundBorder(new MyBorder(Color.WHITE), BorderFactory.createEmptyBorder(VERT_PAD, HORZ_PAD, VERT_PAD, HORZ_PAD));
		setBorder(b);

	}

	private void initComponents() {
		setLayout(new GridLayout(0, 1));
		setBackground(new Color(214, 223, 247));
	}

	// 添加一项，按照标签添加
	public void addItem(final String text, String iconURL) {
		JLabel lblItem = new JLabel();
		if (iconURL != null) {
			lblItem.setIcon(new ImageIcon(getClass().getResource(iconURL)));
			lblItem.setForeground(new Color(33, 93, 198));
		} else
			lblItem.setForeground(Color.BLACK);
		lblItem.setText(text);

		lblItem.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Container container = e.getComponent().getParent().getParent().getParent();
				String folderName = container.getName();
				if (folderName.equals("设置")) {
					if (text.equals("AID设置")) {
						Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
						RightPanel.aidConfigPanel.setVisible(true);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						AIDConfigPanel.textFieldAid.requestFocus();
					} else if (text.equals("CA管理")) {
						Application.rightPanel.add(RightPanel.caPublicKeyConfigPanel, BorderLayout.CENTER);
						RightPanel.caPublicKeyConfigPanel.setVisible(true);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
					} else if (text.equals("应用密钥")) {
						Application.rightPanel.add(RightPanel.issuerKeyConfigPanel, BorderLayout.CENTER);
						RightPanel.issuerKeyConfigPanel.setVisible(true);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
					} else if (text.equals("终端限制")) {
						Application.rightPanel.add(RightPanel.terminalLimitConfigPanel, BorderLayout.CENTER);
						RightPanel.terminalLimitConfigPanel.setVisible(true);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						TerminalLimitConfigPanel.lowLimitField.requestFocus();
					} else if (text.equals("终端性能")) {
						Application.rightPanel.add(RightPanel.terminalPerformanceConfigPanel, BorderLayout.CENTER);
						RightPanel.terminalPerformanceConfigPanel.setVisible(true);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						TerminalPerformanceConfigPanel.checkBox.requestFocus();
					} else if (text.equals("终端类型")) {
						Application.rightPanel.add(RightPanel.terminalTypeConfigPanel, BorderLayout.CENTER);
						RightPanel.terminalTypeConfigPanel.setVisible(true);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
					} else if (text.equals("读卡器")) {
						Application.rightPanel.add(RightPanel.cardReaderPanel, BorderLayout.CENTER);
						RightPanel.cardReaderPanel.setVisible(true);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);
						// RightPanel.cardInfoScanPanel.setVisible(false);
						CardReaderPanel.comboBox.requestFocus();
					} else if (text.equals("皮肤设置")) {
						Application.rightPanel.add(RightPanel.aidConfigPanel, BorderLayout.CENTER);
						RightPanel.aidConfigPanel.setVisible(false);
						RightPanel.logoPanel.setVisible(true);
						RightPanel.caPublicKeyConfigPanel.setVisible(false);
						RightPanel.issuerKeyConfigPanel.setVisible(false);
						RightPanel.terminalLimitConfigPanel.setVisible(false);
						RightPanel.terminalTypeConfigPanel.setVisible(false);
						RightPanel.terminalPerformanceConfigPanel.setVisible(false);
						RightPanel.tradePanel.setVisible(false);
						RightPanel.testDataConfigPanel.setVisible(false);
						RightPanel.cardReaderPanel.setVisible(false);
						RightPanel.cardInfoDetectPanel.setVisible(false);

						new FaceDialog(Application.frame);
					}

				} else if (folderName.equals("交易")) {
					Application.rightPanel.add(RightPanel.tradePanel, BorderLayout.CENTER);
					RightPanel.tradePanel.setVisible(true);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.cardInfoDetectPanel.setVisible(false);
					RightPanel.faceConfiTabbedPanel.setVisible(false);
					AtmPanel.tradeType = pm.getString("mv.tradepanel.lend");
					AtmPanel.setTradeType(AtmPanel.tradeType);
					// 更新交易状态
					Config.setValue("Terminal_Data", "currentTradeType", pm.getString("mv.tradepanel.lend"));
				} else if (folderName.equals("检测")) {
					Application.rightPanel.add(RightPanel.testDataConfigPanel, BorderLayout.CENTER);
					RightPanel.testDataConfigPanel.setVisible(true);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.cardInfoDetectPanel.setVisible(false);
					RightPanel.faceConfiTabbedPanel.setVisible(false);
				} else if (folderName.equals("卡片")) {
					Application.rightPanel.add(RightPanel.cardInfoDetectPanel, BorderLayout.CENTER);
					RightPanel.cardInfoDetectPanel.setVisible(true);
					RightPanel.testDataConfigPanel.setVisible(false);
					RightPanel.terminalTypeConfigPanel.setVisible(false);
					RightPanel.terminalPerformanceConfigPanel.setVisible(false);
					RightPanel.terminalLimitConfigPanel.setVisible(false);
					RightPanel.issuerKeyConfigPanel.setVisible(false);
					RightPanel.caPublicKeyConfigPanel.setVisible(false);
					RightPanel.aidConfigPanel.setVisible(false);
					RightPanel.logoPanel.setVisible(false);
					RightPanel.tradePanel.setVisible(false);
					RightPanel.cardReaderPanel.setVisible(false);
					RightPanel.faceConfiTabbedPanel.setVisible(false);
				}

			}
		});
		add(lblItem);
	}

	// 为了模仿的相似，自定义的一个Border
	class MyBorder extends LineBorder {
		public MyBorder(Color color) {
			super(color, 1, false);
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			Color oldColor = g.getColor();
			int i;
			g.setColor(lineColor);
			for (i = 0; i < thickness; i++) {
				g.drawLine(x + i, y + i, x + i, height - i - i - 1);
				g.drawLine(x + i, height - i - i - 1, width - i - i - 1, height - i - i - 1);
				g.drawLine(width - i - i - 1, y + i, width - i - i - 1, height - i - i - 1);
			}
			g.setColor(oldColor);
		}
	}
}