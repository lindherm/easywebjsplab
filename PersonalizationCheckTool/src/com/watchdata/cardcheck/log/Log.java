package com.watchdata.cardcheck.log;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import org.apache.log4j.Logger;

import com.watchdata.cardcheck.app.OutLogDialog;

public class Log {
	private static Logger logger = Logger.getLogger(Logger.class);
	private static OutLogDialog outLogDialog = null;
	private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

	public Log() {
		outLogDialog = new OutLogDialog();
	}

	public void debug(String info) {
		logger.debug(info);
		out(outStr("DEBUG",info));
	}

	public void info(String info) {
		logger.info(info);
		out(outStr("INFO",info));
	}

	public void warn(String info) {
		logger.warn(info);
		out(outStr("WARN",info));
	}

	public void error(String info) {
		logger.error(info);
		out(outStr("ERROR",info));
	}

	public void error(String info, Exception e) {
		logger.error(info, e);
		out(outStr("ERROR",info));
	}

	public String outStr(String infoType,String info){
		return sf.format(new Date())+" "+infoType+" "+info + "\n";
	}
	
	private void out(String info) {
		if (outLogDialog != null) {
			outLogDialog.setVisible(true);
			Component[] component = outLogDialog.getContentPane().getComponents();
			for (Component component2 : component) {
				
				if (component2 instanceof JPanel) {
					Component[] component3=((JPanel) component2).getComponents();
					for (Component component4 : component3) {
						if (component4 instanceof JScrollPane) {
							JViewport jViewport = ((JScrollPane) component4).getViewport();
							Component component5 = jViewport.getView();

							if (component5 instanceof JTextArea) {
								((JTextArea) component5).append(info);

							}

						}
					}
					
				}
				
			}
		}
	}
}
