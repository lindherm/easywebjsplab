package com.gp.gpscript.keymgr.util.encoders;

import com.watchdata.commons.lang.WDByteUtil;

/**
 * Converters for going from hex to binary and back.
 * <p>
 * Note: this class assumes ASCII processing.
 */
public class Hex {
	private static HexTranslator encoder = new HexTranslator();

	private static final byte[] hexTable = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f' };

	// used for change code
	protected final static String[] hexChars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static byte[] encode(byte[] array) {
		return encode(array, 0, array.length);
	}

	public static byte[] encode(byte[] array, int off, int length) {
		byte[] enc = new byte[length * 2];
		if (encoder != null)
			encoder.encode(array, off, length, enc, 0);

		return enc;
	}

	public static byte[] decode(String string) {
		byte[] bytes = new byte[string.length() / 2];
		String buf = string.toLowerCase();

		for (int i = 0; i < buf.length(); i += 2) {
			char left = buf.charAt(i);
			char right = buf.charAt(i + 1);
			int index = i / 2;

			if (left < 'a') {
				bytes[index] = (byte) ((left - '0') << 4);
			} else {
				bytes[index] = (byte) ((left - 'a' + 10) << 4);
			}
			if (right < 'a') {
				bytes[index] += (byte) (right - '0');
			} else {
				bytes[index] += (byte) (right - 'a' + 10);
			}
		}

		return bytes;
	}

	public static byte[] decode(byte[] array) {
		byte[] bytes = new byte[array.length / 2];

		encoder.decode(array, 0, array.length, bytes, 0);

		return bytes;
	}

	public static String getOddString(String str) {
		String oddStr = "";
		if (str.length() != 16 && str.length() != 32) {
			return oddStr;
		} else {
			String temp = "";
			int hexNum = 0;
			for (int i = 0; i < str.length(); i = i + 2) {
				temp = str.substring(i, i + 2);
				hexNum = Integer.valueOf(temp, 16).intValue();
				if (!isOdd(hexNum)) {
					hexNum = hexNum ^ 1;
					temp = Integer.toHexString(hexNum);
					if (temp.length() == 1)
						temp = "0" + temp;
				}
				oddStr += temp;
			}
		}
		return oddStr;
	}

	public static boolean isOdd(int num) {

		int number = 0;
		boolean flag = true;
		String str = Integer.toBinaryString(num);
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).equals("1")) {
				number += 1;
			}
		}
		if (number == 0 || number == 2 || number == 4 || number == 6 || number == 8) {
			flag = false;
		}
		return flag;
	}

	/**
	 * conform hexString to Int for example, "0100"->256
	 * 
	 * @param str
	 * @return
	 */
	public static int hexStrToInt(String str) {
		byte[] buf = null;
		buf = Hex.decode(str);
		double value = 0;
		for (int i = 0; i < buf.length; i++) {
			value = value + ByteToUnsigned(buf[i]) * (int) (Math.pow((double) 256, (double) (buf.length - i - 1)));
		}
		return (int) value;
	}

	/**
	 * ByteToUnsigned byte(-128~127)---->unsigned int(0~255)
	 * 
	 * @param value
	 * @return
	 */
	public static int ByteToUnsigned(byte value) {
		Byte ss = new Byte(value);
		if (value >= 0)
			return ss.intValue();
		else
			return ss.intValue() + 256;
	}

	/**
	 * byteToString conform a byte to String(1bytes) data a byte data
	 */
	public static String byteToString(byte data) {
		// if(data == null) return "null";
		StringBuffer out = new StringBuffer(256);
		out.append(hexChars[(data >> 4) & 0x0f]);
		out.append(hexChars[data & 0x0f]);
		return out.toString();
	}

	public static String HexToASC(byte[] bb, int startIndex, int endIndex) {
		String result = "";
		Character temp = null;
		for (int i = startIndex; i < endIndex; i++) {
			temp = new Character((char) bb[i]);
			result = result.concat(temp.toString());
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new String(encode("9999".getBytes())));
		System.out.println(WDByteUtil.bytes2HEX("9999".getBytes()));

	}
}
