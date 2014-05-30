package com.gerenhua.tool.panel;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.springframework.context.ApplicationContext;
/**
 * 
 * @description: 抽象功能模板
 * @author: juan.jiang 2012-2-22
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class AbstractPanle extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
	
	protected ApplicationContext appContext;
	protected TextPaneMenu logTextArea;	
	protected JScrollPane jsPan;
	
	private static int color = 0;
	
	private SimpleAttributeSet attrSetOdd;   //奇数行属性
	private SimpleAttributeSet attrSetEven;  //偶数行属性
	private SimpleAttributeSet attrSetError; //错误日志属性
	
	
	public AbstractPanle(){
		super();
		logTextArea = new TextPaneMenu();		
		//logTextArea.setEditable(false);
	
		jsPan = new JScrollPane(logTextArea);
		jsPan.setViewportView(logTextArea);
		
		attrSetOdd = new SimpleAttributeSet();
		attrSetEven = new SimpleAttributeSet();		
		attrSetError = new SimpleAttributeSet();		
		StyleConstants.setForeground(attrSetOdd, Color.blue);
		StyleConstants.setForeground(attrSetEven, Color.black);
		StyleConstants.setForeground(attrSetError, Color.red);
		StyleConstants.setBold(attrSetError, true);
		
	}
	
	public AbstractPanle(ApplicationContext appContext){
		this();			
		this.appContext = appContext;
	}
	
	/**
	 * 增加一行普通日志，隔行隔色
	 * @param logStr
	 */
	public void addLog(String logStr){		
		String info = sf.format(new Date())+logStr +"\n";	
		if(AbstractPanle.color==0){
			addLog(info,logTextArea,attrSetEven);
			AbstractPanle.color = 1;
		}else{
			addLog(info,logTextArea,attrSetOdd);
			AbstractPanle.color = 0;
		}
		
	}
	/**
	 * 增加一行错误日志，显示红色粗体
	 * @param logStr
	 */
	public void addErrorLog(String logStr){		
		String info = sf.format(new Date())+logStr +"\n";			
		addLog(info,logTextArea,attrSetError);
		
	}
	
	private void addLog(String text, JTextPane textPane, SimpleAttributeSet attr) {
		StyledDocument doc = textPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), text, attr);
		} catch (BadLocationException e) {			
			e.printStackTrace();
		}
	}
}
