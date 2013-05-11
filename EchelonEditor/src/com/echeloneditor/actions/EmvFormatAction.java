package com.echeloneditor.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import com.echeloneditor.utils.Config;

public class EmvFormatAction {
	public static Map<String, String> tag2 = new HashMap<String, String>();
	public static Map<String, String> tag4 = new HashMap<String, String>();
	private static int pos = 0;
	public static StringBuilder sb = new StringBuilder();

	// static data
	static {
		Collection<String> tag2cCollection = Config.getItems("TAG2");
		Collection<String> tag4cCollection = Config.getItems("TAG4");

		for (String tag : tag2cCollection) {
			if (!tag2.containsKey(tag.trim())) {
				tag2.put(tag.trim(), Config.getValue("TAG2", tag.trim()));
			}
		}

		for (String tag : tag4cCollection) {
			if (!tag4.containsKey(tag.trim())) {
				tag4.put(tag.trim(), Config.getValue("TAG4", tag.trim()));
			}
		}

	}

	/**
	 * tlv format
	 * 
	 * @param rSyntaxTextArea
	 * @return
	 * @throws BadLocationException
	 */
	public static boolean format(RSyntaxTextArea rSyntaxTextArea) throws BadLocationException {

		String hexLen = rSyntaxTextArea.getSelectedText();
		int intlen = Integer.valueOf(hexLen, 16);

		int pos = rSyntaxTextArea.getSelectionEnd();

		JOptionPane.showMessageDialog(null, intlen + "|" + pos);

		String tempStr = rSyntaxTextArea.getText(pos, 2 * intlen);
		tempStr = tempStr.replaceAll("\r\n", "").replaceAll("\n", "");
		System.out.println("temp:" + tempStr);
		sb = new StringBuilder();
		String helo = TLV(tempStr);
		JOptionPane.showMessageDialog(null, helo);
		System.out.println("helo:" + helo);
		// rSyntaxTextArea.replaceRange(helo, pos, pos+2*intlen);
		return true;
	}

	/**
	 * str to tlv
	 * 
	 * @param str
	 */
	public static String TLV(String tlv) {

		String tagName = tlv.substring(0, 2);
		if (tag2.containsKey(tagName)) {
			sb.append(tagName).append("\n");
			String hexLen = tlv.substring(2, 4);

			if (hexLen.equals("81")) {
				hexLen = tlv.substring(4, 6);
				sb.append("81" + hexLen).append("\n");
				int intlen = Integer.valueOf(hexLen, 16);
				sb.append(tlv.substring(6, 6 + 2 * intlen)).append("\n");
				pos += 6 + 2 * intlen;

				String tlvson = tlv.substring(6 + 2 * intlen);
				if (tlvson.length() > 0) {
					return TLV(tlvson);
				} else {
					return sb.toString();
				}
			} else {
				sb.append(hexLen).append("\n");
				int intlen = Integer.valueOf(hexLen, 16);
				sb.append(tlv.substring(4, 4 + 2 * intlen)).append("\n");
				pos += 4 + 2 * intlen;

				String tlvson = tlv.substring(4 + 2 * intlen);
				if (tlvson.length() > 0) {
					return TLV(tlvson);
				} else {
					return sb.toString();
				}
			}

		} else {
			tagName = tlv.substring(0, 4);
			if (tag4.containsKey(tagName)) {
				sb.append(tagName).append("\n");
				String hexLen = tlv.substring(4, 6);

				if (hexLen.equals("81")) {
					hexLen = tlv.substring(6, 8);
					sb.append("81" + hexLen).append("\n");
					int intlen = Integer.valueOf(hexLen, 16);
					sb.append(tlv.substring(8, 8 + 2 * intlen)).append("\n");
					pos += 8 + 2 * intlen;
					String tlvson = tlv.substring(8 + 2 * intlen);
					if (tlvson.length() > 0) {
						return TLV(tlvson);
					} else {
						return sb.toString();
					}
				} else {
					sb.append(hexLen).append("\n");
					int intlen = Integer.valueOf(hexLen, 16);
					sb.append(tlv.substring(6, 6 + 2 * intlen)).append("\n");
					pos += 6 + 2 * intlen;
					String tlvson = tlv.substring(6 + 2 * intlen);
					if (tlvson.length() > 0) {
						return TLV(tlvson);
					} else {
						return sb.toString();
					}
				}
			}

		}
		return sb.toString();
	}
}
