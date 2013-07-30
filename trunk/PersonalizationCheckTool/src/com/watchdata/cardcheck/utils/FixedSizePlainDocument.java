package com.watchdata.cardcheck.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/** 
 * @author landon E-mail:landonyongwen@126.com 
 * @version 创建时间：2012-2-23 下午02:22:29 
 * 类说明 
 */

public class FixedSizePlainDocument extends PlainDocument {
	
	private static final long serialVersionUID = 4994109533439140195L;
	int maxSize;

	public FixedSizePlainDocument(int limit) {
		maxSize = limit;
	}

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if ((getLength() + str.length()) <= maxSize) {
			super.insertString(offs, str, a);
		} else {
			throw new BadLocationException(
					"Insertion   exceeds   max   size   of   document ",
					offs);
		}
	}
}
