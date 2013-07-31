package com.watchdata.cardcheck.utils.reportutil;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.watchdata.commons.lang.WDAssert;

public class GenReportUtil {
	private static Document document;
	private static Table table;

	// 默认构造方法出始话数据
	public GenReportUtil() {
		try {
			table = new Table(3);
			table.setWidth(100f);// 表格宽度
			table.setBorderWidth(1);
			table.setBorderColor(Color.red);
			table.setPadding(5);
			table.setSpacing(0);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 打开日志文档流
	 * 
	 * @throws FileNotFoundException
	 * @throws BadElementException
	 */
	public void open(String transactionName) {
		// 创建word文档,并设置纸张的大小
		document = new Document(PageSize.A4);
		// document.setMargins(2.54f, 2.54f, 3.18f, 3.18f);

		try {
			RtfWriter2.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/report/transaction/" + transactionName + ".doc"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 添加页眉
		HeaderFooter header = new HeaderFooter(new Phrase("《卡片个人化检测工具》                            版权所有：北京握奇数据系统有限公司", new Font(Font.NORMAL, 10, Font.NORMAL, Color.BLACK)), false);
		header.setAlignment(Element.ALIGN_RIGHT);
		document.setHeader(header);
		// 添加页脚
		HeaderFooter footer = new HeaderFooter(new Phrase("页码：", new Font(Font.NORMAL, 10, Font.NORMAL, Color.BLACK)), true);
		footer.setBorder(Rectangle.NO_BORDER);
		footer.setAlignment(Element.ALIGN_CENTER);
		document.setFooter(footer);

		document.open();
	}

	/**
	 * 往日志word中写入标题
	 * 
	 * @param fileTitile
	 * @throws DocumentException
	 */
	public void addFileTitle(String fileTitile) {
		// 标题
		Paragraph pTitle = new Paragraph(fileTitile, new Font(Font.NORMAL, 18, Font.BOLDITALIC, Color.RED));
		pTitle.setAlignment(Paragraph.ALIGN_LEFT);
		try {
			document.add(pTitle);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 往word日志中写入交易名称，一行表格包裹
	 * 
	 * @param transactionName
	 * @throws BadElementException
	 */
	public void addTransactionName(String transactionName) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 表头
		Paragraph ptName = new Paragraph(transactionName, new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
		Paragraph pDate = new Paragraph("交易时间：" + sf.format(new Date()), new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
		try {
			Cell cell = new Cell(ptName);
			// 单元格
			cell.setColspan(3);
			table.addCell(cell);

			Cell cell2 = new Cell(pDate);
			cell2.setColspan(3);
			table.addCell(cell2);
		} catch (BadElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 将对象写入日志，用表格包裹
	 * 
	 * @param apduSendANDRes
	 */
	public void add(APDUSendANDRes apduSendANDRes) {
		try {

			if (WDAssert.isNotEmpty(apduSendANDRes.getSendAPDU())) {
				Paragraph apduFront = new Paragraph("send:" + apduSendANDRes.getSendAPDU(), new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
				// 内容
				Cell cell2 = new Cell(apduFront);
				cell2.setColspan(2);
				cell2.setBackgroundColor(new Color(253, 228, 208));
				table.addCell(cell2);
			}

			if (WDAssert.isNotEmpty(apduSendANDRes.getDes())) {
				Paragraph apduDES = new Paragraph(apduSendANDRes.getDes(), new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
				Cell cell3 = new Cell(apduDES);
				cell3.setBackgroundColor(new Color(253, 228, 208));
				table.addCell(cell3);
			}

			if (WDAssert.isNotEmpty(apduSendANDRes.getResponseAPDU())) {
				Paragraph apduRes = new Paragraph("recv:" + apduSendANDRes.getResponseAPDU(), new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
				Cell cell5 = new Cell(apduRes);
				cell5.setColspan(3);
				// cell5.setBackgroundColor(new Color(253, 228, 208));
				table.addCell(cell5);

			}

			if (!apduSendANDRes.getTagDes().isEmpty()) {
				apduSendANDRes.getTagDes().remove("sw");
				apduSendANDRes.getTagDes().remove("apdu");
				apduSendANDRes.getTagDes().remove("res");
				Paragraph apduresdes = new Paragraph(convertMap2Str(apduSendANDRes.getTagDes()), new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
				Cell cell6 = new Cell(apduresdes);
				cell6.setColspan(3);
				table.addCell(cell6);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * 往word日志中添加一个完整的发送和接收APDU，以及返回apdu解析到的tag列表
	 * 
	 * @param sendAPDU
	 * @param des
	 * @param responseAPDU
	 * @param tagDes
	 * @return
	 */
	public void add(String sendAPDU, String des, String responseAPDU, HashMap<String, String> tagDes) {
		APDUSendANDRes apduSendANDRes = new APDUSendANDRes();
		apduSendANDRes.setSendAPDU(sendAPDU);
		apduSendANDRes.setDes(des);
		apduSendANDRes.setResponseAPDU(responseAPDU);
		apduSendANDRes.setTagDes(tagDes);
		// 调用add将对象属性写进表格
		add(apduSendANDRes);
	}

	/**
	 * 往word日志中添加一行信息用一行表格包裹
	 * 
	 * @param content
	 */
	public void add(String content) {
		if (WDAssert.isNotEmpty(content)) {
			Paragraph apduRes = new Paragraph(content, new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLACK));
			try {
				Cell cell = new Cell(apduRes);
				cell.setColspan(3);
				// cell5.setBackgroundColor(new Color(253, 228, 208));
				table.addCell(cell);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 将map转换成字符串
	 * 
	 * @return
	 */
	private String convertMap2Str(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		Set<String> key = map.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			sb.append("Tag:" + s + "\n" + map.get(s)).append("\n\n");
		}
		return sb.toString();
	}

	/**
	 * 关闭文档流
	 */
	public void close() {
		try {
			document.add(table);
			document.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		APDUSendANDRes apduSendANDRes = new APDUSendANDRes();
		apduSendANDRes.setSendAPDU("00 A4 00 00 08");
		// apduSendANDRes.setSendRearAPDU("89 90 78 65 43 56  45  5  5 5 ");
		apduSendANDRes.setDes("取随机数");
		apduSendANDRes.setResponseAPDU("09 89 78 67 56 56 67 00");
		Map<String, String> beanMap = new HashMap<String, String>();
		beanMap.put("9F79", "00000000000000000000000");
		beanMap.put("5A", "111111111111111111111111111");
		beanMap.put("9C", "111111111111");
		beanMap.put("5F2A", "11111111111111111");
		beanMap.put("82", "1111111111111111111111111111");
		apduSendANDRes.setTagDes(beanMap);

		GenReportUtil genWordUtil = new GenReportUtil();
		genWordUtil.open("圈存");
		genWordUtil.addFileTitle("交易检测报告");
		genWordUtil.addTransactionName("圈存");
		genWordUtil.add(apduSendANDRes);
		genWordUtil.addTransactionName("圈存交易结束");
		apduSendANDRes.setTagDes(new HashMap<String, String>());
		genWordUtil.addTransactionName("pboc");
		genWordUtil.add(apduSendANDRes);
		genWordUtil.addTransactionName("标准借贷记");
		genWordUtil.add(apduSendANDRes);
		genWordUtil.addTransactionName("qpboc");
		genWordUtil.add(apduSendANDRes);
		genWordUtil.close();
	}
}
