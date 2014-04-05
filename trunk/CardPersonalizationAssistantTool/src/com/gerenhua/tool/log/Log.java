package com.gerenhua.tool.log;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

public class Log {
	private static Logger logger = Logger.getLogger(Logger.class);
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	public static JTextPane jTextPane;

	public Log() {
	}

	public void setLogArea(JTextPane textPane) {
		this.jTextPane = textPane;
	}

	public void debug(String info) {
		logger.debug(info);
		out(outStr("DEBUG", info), 4);
	}

	public void debug(String info, int startFlag) {
		//logger.debug(info);
		if (startFlag == 0) {
			out(outStr("DEBUG", info), 0);
		}else {
			out(outStr("DEBUG", info), 4);
		}
	}

	public void info(String info) {
		logger.info(info);
		out(outStrNoDate(info), 3);
	}

	public void warn(String info) {
		logger.warn(info);
		out(outStr("WARN", info), 1);
	}

	public void error(String info) {
		logger.error(info);
		out(outStr("ERROR", info), 2);
	}

	public void error(String info, Exception e) {
		logger.error(info, e);
		out(outStr("ERROR", info), 2);
	}

	public String outStr(String infoType, String info) {
		return sf.format(new Date()) + " " + infoType + " " + info + "\n";
	}

	public String outStrNoDate(String info) {
		return " \t\t\t" + info + "\n";
	}

	private void out(String info, int command) {
		if (jTextPane != null) {
			SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();

			if (command == 0) {
				jTextPane.setText("");
				StyleConstants.setForeground(simpleAttributeSet, Color.BLACK);
			}else if (command == 1) {
				StyleConstants.setForeground(simpleAttributeSet, Color.GREEN);
			} else if (command == 2) {
				StyleConstants.setForeground(simpleAttributeSet, Color.RED);
			} else if (command == 3) {
				StyleConstants.setForeground(simpleAttributeSet, Color.BLUE);
				StyleConstants.setBold(simpleAttributeSet, true);
			} else {
				StyleConstants.setForeground(simpleAttributeSet, Color.BLACK);
			}
			addLog(info, jTextPane, simpleAttributeSet);
		}
	}

	private void addLog(String info, JTextPane textPane, SimpleAttributeSet attr) {
		StyledDocument doc = textPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), info, attr);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		jTextPane.setCaretPosition(doc.getLength());
	}
}
