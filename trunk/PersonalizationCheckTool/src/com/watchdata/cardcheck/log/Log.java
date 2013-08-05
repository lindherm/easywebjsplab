package com.watchdata.cardcheck.log;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import org.apache.log4j.Logger;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.watchdata.cardcheck.app.OutLogDialog;

public class Log {
	private static Logger logger = Logger.getLogger(Logger.class);
	private static OutLogDialog outLogDialog = null;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	private static boolean showDialog=false;

	public Log() {
		outLogDialog = new OutLogDialog();
	}

	public void setLogDialogOn(){
		showDialog=true;
	}
	
	public void setLogDialogOff(){
		showDialog=false;
	}
	
	public void debug(String info) {
		logger.debug(info);
		if (showDialog) {
			out(outStr("DEBUG", info), 1);
		}
	}

	public void debug(String info, int startFlag) {
		logger.debug(info);
		if (showDialog) {
			if (startFlag == 0) {
				out(outStr("DEBUG", info), 0);
			}
			out(outStr("DEBUG", info), 1);
		}
	}

	public void info(String info) {
		logger.info(info);
		if (showDialog) {
			out(outStr("INFO", info), 1);
		}
	}

	public void warn(String info) {
		logger.warn(info);
		if (showDialog) {
			out(outStr("WARN", info), 1);
		}
	}

	public void error(String info) {
		logger.error(info);
		if (showDialog) {
			out(outStr("ERROR", info), 1);
		}
	}

	public void error(String info, Exception e) {
		logger.error(info, e);
		if (showDialog) {
			out(outStr("ERROR", info), 1);
		}
	}

	public String outStr(String infoType, String info) {
		return sf.format(new Date()) + " " + infoType + " " + info + "\n";
	}

	private void out(String info, int command) {
		if (outLogDialog != null) {
			outLogDialog.setVisible(true);
			Component[] component = outLogDialog.getContentPane().getComponents();
			for (Component component2 : component) {

				if (component2 instanceof JPanel) {
					Component[] component3 = ((JPanel) component2).getComponents();
					for (Component component4 : component3) {
						if (component4 instanceof JScrollPane) {
							JViewport jViewport = ((JScrollPane) component4).getViewport();
							Component component5 = jViewport.getView();

							if (component5 instanceof RSyntaxTextArea) {
								if (command == 0) {
									((JTextArea) component5).setText("");
								} else {
									((JTextArea) component5).append(info);
								}
							}

						}
					}

				}

			}
		}
	}
}
