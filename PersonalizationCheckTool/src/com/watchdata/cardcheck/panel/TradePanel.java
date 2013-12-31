package com.watchdata.cardcheck.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.Constants;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.logic.apdu.CommonHelper;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class TradePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextPane textPane;
	public static JTextPane textPane1;
	private static Log logger = new Log();

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public TradePanel() throws IOException {
		setLayout(null);
		
		textPane =new JTextPane(){
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
		
		textPane1 =new JTextPane(){
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
		
		AtmPanel atmPanel = new AtmPanel(textPane,textPane1);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "TRADE LOG", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		
		
		scrollPane.setViewportView(textPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "READ TRADE LOG", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 70, 213)));
		panel_1.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		scrollPane_1.setViewportView(textPane1);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textPane1, popupMenu);
		
		JMenuItem menuItem = new JMenuItem("读交易记录");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread=new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						showReadTradeLog();
					}
				});
				thread.start();
			}
		});
		popupMenu.add(menuItem);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.48);
		splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(atmPanel);
		splitPane.setOneTouchExpandable(true);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.8);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setTopComponent(splitPane);
		splitPane_1.setBottomComponent(panel_1);
		splitPane_1.setBounds(0, 0, 1100, 680);
		splitPane_1.setOneTouchExpandable(true);
		add(splitPane_1);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public void showReadTradeLog(){
		// TODO Auto-generated method stub
		logger.setLogArea(textPane1);
		logger.debug("read trade log",0);
		CommonAPDU apduHandler=new CommonAPDU();
		// 为了保证卡片和读卡器的正确性，交易开始前务必先复位
		logger.debug("=============================reset===================================");
		HashMap<String, String> res = apduHandler.reset();
		if (!"9000".equals(res.get("sw"))) {
			logger.error("card reset falied");
		}
		logger.debug("atr:" + res.get("atr"));

		logger.debug("============================select PSE=================================");
		HashMap<String, String> result = apduHandler.select(Constants.PSE);
		if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
			logger.error("select PSE error,card return:" + result.get("sw"));
		}

		if (WDAssert.isNotEmpty(result.get("88"))) {
			// read dir, begin from 01
			logger.debug("==============================read dir================================");
			List<HashMap<String, String>> readDirList = apduHandler.readDir(result.get("88"));

			// select aid
			String aid = readDirList.get(0).get("4F");
			logger.debug("===============================select aid==============================");
			if (WDAssert.isEmpty(aid)) {
				logger.error("select aid is null");
			}
			result = apduHandler.select(aid);
			
			if (!Constants.SW_SUCCESS.equalsIgnoreCase(result.get("sw"))) {
				logger.error("select aid error,card return:" + result.get("sw"));
			}
			String tag9f4d=result.get("9F4D");
			if (WDAssert.isEmpty(tag9f4d)) {
				logger.error("logentry is not exists.:" + result.get("sw"));
			}
			result=apduHandler.getData("9F4F");
			String strLog=result.get("res");
			strLog=strLog.substring(6,strLog.length()-4);
			List<String> tlList=CommonHelper.parseTLDataCommon(strLog);
			
			String sfi=tag9f4d.substring(0,2);
			String logCount=tag9f4d.substring(2);
			for (int i = 1; i <=Integer.parseInt(logCount, 16); i++) {
				logger.debug("===============================readlog=============================="+sfi+WDStringUtil.paddingHeadZero(Integer.toHexString(i),2));
				HashMap<String, String> readList= apduHandler.readDirCommon(sfi,WDStringUtil.paddingHeadZero(Integer.toHexString(i), 2));
				if (!readList.get("sw").equalsIgnoreCase("9000")) {
					break;
				}
			}
			//apduHandler.close();
		}
	}
}
