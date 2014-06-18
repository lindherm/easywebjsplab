package com.gerenhua.tool.tlv;

import java.util.ArrayList;
import java.util.Iterator;

import org.bouncycastle.util.encoders.Hex;

import com.watchdata.commons.lang.WDByteUtil;

public class TLVList {
	public byte encodingMode;
	public int length;

	private byte[] tlvStream = null;

	public ArrayList<TLV> tlvList = new ArrayList<TLV>();

	public static int byteToInt(byte num) {
		byte[] b = new byte[1];
		b[0] = num;
		String strTag = new String(Hex.encode(b));
		Integer intTag = Integer.valueOf(strTag, 16);
		return intTag.intValue();
	}

	/**
	 * Parses tlvStream,breaking it down into individual TLVS
	 * 
	 * @param tlvStream
	 *            The combined TLV. This value may be empty in which case an empty list is created
	 * @param encoding
	 *            Value representing the encoding method.
	 * @return Return error code 0 OK -1 tlvStream is empty -2 Invalid tag for the encoding method -3 value to large for given encode method -4 cannot decode tlvStream using the specified encoding method -5 unsupported encoding method -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
	 */
	public static int parseParameters(byte[] tlvStream, byte encoding) {

		TLV tlv = null;
		int pos = 0, tlvCount = 0;
		ArrayList tlvList = new ArrayList();

		if (tlvStream == null || tlvStream.length == 0)
			return -1;

		if (encoding != TLV.DGI && encoding != TLV.EMV)
			return -5;

		while (pos < tlvStream.length) {
			if (encoding == TLV.EMV)
				pos = getOneTLV_EMV(tlvStream, pos, encoding, tlvList);
			else if (encoding == TLV.DGI)
				pos = getOneTLV_DGI(tlvStream, pos, encoding, tlvList);
			if (pos < 0)
				break;
			++tlvCount;
		}

		tlvList = null;

		if (pos < 0)
			return pos;

		return 0;

	}

	/**
	 * Constructor Function. Parses tlvStream,breaking it down into individual TLVS
	 * 
	 * @param tlvStream
	 *            The combined TLV. This value may be empty in which case an empty list is created
	 * @param encoding
	 *            Value representing the encoding method.
	 */
	public TLVList(byte[] tlvBuffer, byte encoding) {

		TLV tlv = null;
		int pos = 0, tlvCount = 0;

		tlvStream = tlvBuffer;
		encodingMode = encoding;

		if (tlvBuffer != null) {

			while (pos < tlvStream.length) {
				if (encoding == TLV.EMV)
					pos = getOneTLV_EMV(tlvStream, pos, encoding, tlvList);
				else if (encoding == TLV.DGI)
					pos = getOneTLV_DGI(tlvStream, pos, encoding, tlvList);
				else if (encoding == TLV.DGI2)
					pos = getOneTLV_DGI2(tlvStream, pos, encoding, tlvList);
				if (pos < 0)
					break;
				++tlvCount;
			}

			/*if (pos != tlvStream.length) {
				tlvList.subList(0, tlvCount).clear();
				tlvCount = 0;
			} else {*/
				length = tlvCount;
			//}
		}
	}

	/*
	 * public TLVList(byte[] tlvBuffer,byte encoding) {
	 * 
	 * TLV tlv = null; int pos = 0,tlvCount=0;
	 * 
	 * tlvStream = tlvBuffer; encodingMode = encoding;
	 * 
	 * while(pos < tlvStream.length) { if(encoding == TLV.EMV) pos = getOneTLV_EMV(tlvStream,pos,encoding,tlvList); else if(encoding == TLV.DGI) pos = getOneTLV_DGI(tlvStream,pos,encoding,tlvList); if(pos < 0) break; ++tlvCount; }
	 * 
	 * if(pos != tlvStream.length) { tlvList.subList(0,tlvCount).clear(); tlvCount = 0; } else length = tlvCount;
	 * 
	 * }
	 */

	/**
	 * Parses tlvStream,Get one TLV from tlvStream
	 * 
	 * @param tlvStream
	 *            The combined TLV. This value may be empty in which case an empty list is created
	 * @param pos
	 *            Current position on tlvStream
	 * @param encoding
	 *            Value representing the encoding method.
	 * @param tlvList
	 *            Holding individual TLV
	 * @return Return error code 0 OK -1 tlvStream is empty -2 Invalid tag for the encoding method -3 value to large for given encode method -4 cannot decode tlvStream using the specified encoding method -5 unsupported encoding method -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
	 */
	public static int getOneTLV_EMV(byte[] tlvStream, int pos, byte encoding, ArrayList tlvList) {
		int tag = 0, j = 0;
		byte[] arrayTag = null;
		byte[] len = null;
		byte[] value = null;
		int valueLen = 0;

		// At least one byte Tag and one byte Len
		if (pos + 1 > tlvStream.length)
			return -1;

		// TAG
		if ((tlvStream[pos] & 0x1f) == 0x1f) {
			if (pos + 1 < tlvStream.length) {
				if (tlvStream[pos + 1] > 0x80)
					return -2;
				arrayTag = new byte[2];
				arrayTag[0] = tlvStream[pos];
				arrayTag[1] = tlvStream[++pos];
			}
		} else {
			arrayTag = new byte[1];
			arrayTag[0] = tlvStream[pos];
		}

		String strTag = new String(Hex.encode(arrayTag));
		Integer intTag = Integer.valueOf(strTag, 16);
		tag = intTag.intValue();

		// LEN
		if (++pos >= tlvStream.length)
			return -4;

		if ((tlvStream[pos] & 0x80) == 0x80) {
			int lenByteCount = tlvStream[pos] & 0x7f;
			if (lenByteCount == 0 || lenByteCount > 3)
				return -3;

			len = new byte[lenByteCount + 1];
			len[0] = tlvStream[pos];

			if (pos + lenByteCount + 1 >= tlvStream.length)
				return -4;
			for (int i = 0; i < lenByteCount; i++) {
				len[i + 1] = tlvStream[++pos];
				valueLen = valueLen * 256 + byteToInt(len[i + 1]);
			}
		} else {
			len = new byte[1];
			len[0] = tlvStream[pos];
			valueLen = byteToInt(tlvStream[pos]);
		}

		// VALUE
		if (pos + valueLen >= tlvStream.length)
			return -4;

		value = new byte[valueLen];
		for (int i = 0; i < valueLen; i++)
			value[i] = tlvStream[++pos];
		pos++;

		// if (find(tlvList, tag) == 0) {
		tlvList.add(new TLV(tag, value, encoding));
		// } else
		// return -6;

		return pos;
	}

	/**
	 * Parses DGI encoding tlvStream,Get one TLV from tlvStream
	 * 
	 * @param tlvStream
	 *            The combined TLV. This value may be empty in which case an empty list is created
	 * @param pos
	 *            Current position on tlvStream
	 * @param encoding
	 *            Value representing the encoding method.
	 * @param tlvList
	 *            Holding individual TLV
	 * @return Return error code 0 OK -1 tlvStream is empty -2 Invalid tag for the encoding method -3 value to large for given encode method -4 cannot decode tlvStream using the specified encoding method -5 unsupported encoding method -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
	 */
	public static int getOneTLV_DGI(byte[] tlvStream, int pos, byte encoding, ArrayList tlvList) {
		int tag = 0, j = 0;
		byte[] arrayTag = null;
		byte[] len = null;
		byte[] value = null;
		int valueLen = 0;

		// Tag: two bytes,Len: one byte
		if (pos + 3 > tlvStream.length)
			return -4;

		// TAG
		arrayTag = new byte[2];
		arrayTag[0] = tlvStream[pos];
		arrayTag[1] = tlvStream[++pos];

		String strTag = new String(Hex.encode(arrayTag));
		Integer intTag = Integer.valueOf(strTag, 16);
		tag = intTag.intValue();

		// LEN
		len = new byte[1];
		len[0] = tlvStream[++pos];
		//valueLen = tlvStream[pos];
		valueLen = Integer.parseInt(WDByteUtil.byte2HEX(tlvStream[pos]), 16);

		// VALUE
		if (pos + valueLen >= tlvStream.length)
			return -4;

		value = new byte[valueLen];
		for (int i = 0; i < valueLen; i++)
			value[i] = tlvStream[++pos];
		pos++;

		// if (find(tlvList, tag) == 0) {
		tlvList.add(new TLV(tag, value, encoding));
		// } else
		// return -6;

		return pos;
	}
	
	public static int getOneTLV_DGI2(byte[] tlvStream, int pos, byte encoding, ArrayList tlvList) {
		int tag = 0, j = 0;
		byte[] arrayTag = null;
		byte[] len = null;
		byte[] value = null;
		int valueLen = 0;

		// Tag: two bytes,Len: one byte
		if (pos + 4 > tlvStream.length)
			return -4;

		// TAG
		arrayTag = new byte[2];
		arrayTag[0] = tlvStream[pos];
		arrayTag[1] = tlvStream[++pos];

		String strTag = new String(Hex.encode(arrayTag));
		Integer intTag = Integer.valueOf(strTag, 16);
		tag = intTag.intValue();

		// LEN
		len = new byte[2];
		len[0] = tlvStream[++pos];
		len[1] = tlvStream[++pos];
		//valueLen = tlvStream[pos];
		valueLen = Integer.parseInt(WDByteUtil.bytes2HEX(len), 16);

		// VALUE
		if (pos + valueLen >= tlvStream.length)
			return -4;

		value = new byte[valueLen];
		for (int i = 0; i < valueLen; i++)
			value[i] = tlvStream[++pos];
		pos++;

		// if (find(tlvList, tag) == 0) {
		tlvList.add(new TLV(tag, value, TLV.DGI));
		// } else
		// return -6;

		return pos;
	}

	/**
	 * Search the specified list for a specified tag,and returns a TLV object
	 * 
	 * @param tlvList
	 *            the specified TLV list for searching
	 * @param tag
	 *            Value represents the Tag
	 * @return Return error code 0 found -6 tlvStream contained two or more TLV's with the same Tag value (at the top level)
	 */
	public static int find(ArrayList tlvList, int tag) {

		Iterator iter = tlvList.iterator();
		TLV tlv;
		while (iter.hasNext()) {
			tlv = (TLV) iter.next();
			if (tlv.getTag() == tag)
				return -6;
		}
		return 0;
	}

	/**
	 * Search the specified list for a specified tag,and returns a TLV object
	 * 
	 * @param tag
	 *            Value represents the Tag
	 * @return A TLV object which contains the data stored for that Tag
	 */
	public TLV find(int tag) {

		Iterator iter = tlvList.iterator();
		TLV tlv;
		while (iter.hasNext()) {
			tlv = (TLV) iter.next();
			if (tlv.getTag() == tag)
				return tlv;
		}

		return null;
	}

	/**
	 * Search the specified list for a specified tag,and returns a zero based index for that TLV * @param tag Value represents the Tag
	 * 
	 * @return Index that indicates the location of the TLV within the list
	 */
	public int findIndex(int tag) {

		Iterator iter = tlvList.iterator();
		TLV tlv;
		while (iter.hasNext()) {
			tlv = (TLV) iter.next();
			if (tlv.getTag() == tag) {
				return tlvList.indexOf(tlv);
			}
		}
		return -1;
	}

	/**
	 * Search the specified list for a specified index,and returns a TLV object
	 * 
	 * @param Index
	 *            Zero based index into the list of stored TLV items
	 * @return A TLV object which contains the data stored at that index.
	 */
	public TLV index(int index) {
		TLV tlv = (TLV) tlvList.get(index);
		return tlv;
	}

	/**
	 * Delete the the specified TLV from the list by tag value
	 * 
	 * @param Index
	 *            Zero based index number that represents the tag that will be deleted from list
	 * @return none
	 */
	public void deleteByIndex(int index) {
		tlvList.remove(index);
	}

	/**
	 * Delete the the specified TLV from the list by tag value
	 * 
	 * @param tag
	 *            Value represents the tag that will be deleted from list
	 * @return none
	 */
	public void deleteByTag(int tag) {
		Iterator iter = tlvList.iterator();
		TLV tlv;
		while (iter.hasNext()) {
			tlv = (TLV) iter.next();
			if (tlv.getTag() == tag) {
				tlvList.remove(tlvList.indexOf(tlv));
				if (tlvList.size() == 0)
					break;
				iter = tlvList.iterator();
			}
		}
	}

	/**
	 * Appends the specified TLV data to the end of the existing list of TLVs
	 * 
	 * @param tag
	 *            Tag to be used to be added to the list.
	 * @param value
	 *            Value to be used to be added to the list
	 * @return return code the same as function parseParameters's return code
	 */
	public int append(int tag, byte[] value) {
		TLV myTLV;
		int errorCode = TLV.parseParameters(tag, value, encodingMode);
		if (errorCode != 0)
			switch (errorCode) {
			case -1:
				return -3;
			case -2:
				return -1;
			case -3:
				return -2;
			case -4:
				return -5;
			}

		myTLV = new TLV(tag, value, encodingMode);

		if (find(tag) == null) {
			tlvList.add(myTLV);
			length++;
		} else {
			return -6;
		}
		return 0;
	}

	/**
	 * Appends the specified TLV data to the end of the existing list of TLVs
	 * 
	 * @param tlvStream
	 *            the combined TLV data in single stream of bytes
	 * @return return code the same as function parseParameters's return code
	 */
	public int append(byte[] tlvStream) {

		int pos = 0, tlvCount = 0;

		if ((parseParameters(tlvStream, encodingMode)) == 0) {
			while (pos < tlvStream.length) {
				if (encodingMode == TLV.EMV)
					pos = getOneTLV_EMV(tlvStream, pos, encodingMode, tlvList);
				else if (encodingMode == TLV.DGI)
					pos = getOneTLV_DGI(tlvStream, pos, encodingMode, tlvList);
				if (pos < 0)
					break;
				++tlvCount;
			}

			if (pos != tlvStream.length) {
				if (tlvCount > 0)
					tlvList.subList(length, length + tlvCount).clear();
				return pos;
			} else
				length += tlvCount;
		}

		return 0;
	}

	/**
	 * Append the data to the existing data for the specified tag
	 * 
	 * @param tag
	 *            Tag to be used to be added to the list
	 * @param data
	 *            Data to be added to the existing tag
	 * @return -1 tag is not existing -2 resultant data value to large for given encoding method
	 */

	public int appendValue(int tag, byte[] data) {
		TLV newTLV = null, tlv = find(tag);
		byte[] value = null;
		int index = 0;

		if (tlv == null)
			return -1;

		if (encodingMode == TLV.EMV) {
			if (tlv.getValue().length + data.length > 65535)
				return -2;
		} else {
			if (tlv.getValue().length + data.length > 252)
				return -2;
		}

		value = new byte[tlv.getValue().length + data.length];
		System.arraycopy(tlv.getValue(), 0, value, 0, tlv.getValue().length);
		System.arraycopy(data, 0, value, tlv.getValue().length, data.length);

		newTLV = new TLV(tag, value, encodingMode);

		index = findIndex(tag);

		tlv = (TLV) tlvList.set(index, newTLV);
		tlv = null;

		return 0;
	}

	/**
	 * Append the data to the existing data for the specified zero-based index into the TLVStream
	 * 
	 * @param index
	 *            Zero-based index into the TLVStream
	 * @param data
	 *            Data to be added to the existing tag
	 * @return -1 tag is not existing -2 resultant data value to large for given encoding method
	 */
	public int appendValueIndex(int index, byte[] data) {
		TLV newTLV = null, tlv = index(index);
		byte[] value = null;

		if (tlv == null)
			return -1;

		if (encodingMode == TLV.EMV) {
			if (tlv.getValue().length + data.length > 65535)
				return -2;
		} else {
			if (tlv.getValue().length + data.length > 252)
				return -2;
		}

		value = new byte[tlv.getValue().length + data.length];
		System.arraycopy(tlv.getValue(), 0, value, 0, tlv.getValue().length);
		System.arraycopy(data, 0, value, tlv.getValue().length, data.length);

		newTLV = new TLV(tlv.getTag(), value, encodingMode);

		tlv = (TLV) tlvList.set(index, newTLV);
		tlv = null;

		return 0;
	}

	/**
	 * Returns the content of the entire list,including T,L and V values in a TLV format as a ByteString object
	 * 
	 * @return the content of the entire list
	 */
	public byte[] toByteString() {
		Iterator iter = tlvList.iterator();
		Iterator iter1 = tlvList.iterator();
		TLV tlv;
		int totalLen = 0;

		while (iter.hasNext()) {
			tlv = (TLV) iter.next();
			totalLen += tlv.getTLV().length;
		}

		if (totalLen == 0)
			return null;

		byte[] buffer = new byte[totalLen];
		int offset = 0;

		while (iter1.hasNext()) {
			tlv = (TLV) iter1.next();
			System.arraycopy(tlv.getTLV(), 0, buffer, offset, tlv.getTLV().length);
			offset += tlv.getTLV().length;
		}

		return buffer;
	}

	public static void main(String[] args) {
		String temp = "860D88880A4F08A000000333010101864B0101487046572844B3E2DDC7E153E979BD37CF2ECF72EDB351948789AA282B252B398C26125E2E8084D6609EB554235F200200009F6110DE8D55142B18DEB74AF8724232CB4AE49F620100864F02014C704A5F24031712315A0A6230704111500000873F5F3401019F0702FF008E100000000000000000420341031E031F009F0D05D8609CA8009F0E050010";
		temp += "0000009F0F05D8689CF8005F280201568681EB0202E87081E59F4681B08BEF2615601B7EA84D3602CBC2A4536B2C4626B9284CEAC5C2EB626F3FBF09C943D29329C271A070D8A24FF76628AF325D246BD8B70D7CD425B683333735712C6FF9DFF4DC936BD01A288AB97715378833890419FCAE3CB53DBDEAC0A6B7029162C5FEDBBCDA722FC977FBDEFA74CE39B3B15857A229D74D75C157630AAC4EED8CDE5E01FE42CD19B7286DE961780A99B51BD3FFA3FF80EE876B88E217F03022FBFEC104A6CFA83A55A3480106A2303E9F4701039F482ACD8990322285E491566966925ECF77F0AC3E7CE4FF6280A9114E0CE1D862E05D2656023421D1026E1C578681C90203C67081C39381B08E4A3868297C7715559F36C399C4497014196169EA64273B60024B23ABF995A3AD7128241E1419563DE2CC9BA841C5DC05548B044893B72E287A47A84D96F8BE5AEC9264795FF9D73678DE36FE347D6132BDD00FED685EA154DB5BEECD6592FF253934A3D72BBD893DFE31502DB5F38C7A8F061425E0830B4AF63E13B0E2FE7FC96CB8D33AF556800303C68119B179C91D7FC84E782D811F7E4AE1A33E5760E48DE0AE0A85AB7A2FD800D6F91172CE3D9F49039F37045F25031212039F4A0182864702044470425F24031712315A0A6230704111500000873F9F0702FF008E0C00000000000000001E031F009F0D05D8603CA8009F0E";
		temp += "0500108000009F0F05D8683CF8005F280201568681EB0205E87081E59F4681B09603A758BED7FB9BF653F3C7FDC6C20151BF6743FDDF3DF2F87E982C149C456461420B4AACED080DD813109189741553CBEBC2C5B4759A6D8D71F11C1E50214B1AB5026C3B6616031279EDAFFD880A69EE1EC98991ACA539250BC08ED08AB0209832345BEEB1C71B13C3945FC0A066B58F427B7A3A2414802E8146B8C16BD69D1F9DC8CC9A1489F82C7B97787B4EB9EF41C127CF4F9DA91595BDF41F201158521A4EB4349A3BCFE41DE7A37715BC02F89F4701039F482ACD8990322285E491566966925ECF77F0AC3E7CE4FF6280A9114E0CE1D862E05D2656023421D1026E1C578681C90206C67081C39381B084E0879E27E7B5EFE13D6DCD3A4C1881C7CED3646173F60F47270D7813CF51436FB38DCE71DAF38A746EC5D0D2F9A18EAB50115034D5BDD0CC6F2F5030D522D21518B4FFA7B6C821C7035257199F19282C10817C334CEF2C055CA3E56BEC3CDBF60EA5E48A322FDE353240D3FAA355D5853C0A40940FE6242FF8D8C0E93AA41D71E3CC86BF566C3A5663F6724D657E082CA34C002A14FAECC9235F9898C8A39B431BC7C4609BEBB215166444C9A815089F49039F37045F25031212039F4A0182860902070670045F3401018681E60301E37081E09081B02A053A5F449B3C9988E8840CE82E26C07E2F64B5C778126748501E90";
		temp += "4115311EA903BA0EBBA4C80557161AAA42987B9349656134E9C70D1A0EF1F7CE31E5C4D42E4F9A9DA2F57AD42F61FE1134C92F4629473411D249147DA64EA2AE56E25A06A1B17AB18BF575A411B45C9114C51038E03962CE8E7FF3868C1FA414D4B64705E71CA0EC60A5CBD4E839484BCEC76EFBEC6696CCCCACF0D9620FD4893D4C31C1B298A745F614481D269F94D6CB66BD99922493403FDD618B73BFF96271F25FD0D8A88E8FA3E2118FC4D080E1E317264B1F5770ED02999F3201038F0109865103024E704C8C1B9F02069F03069F1A0295055F2A029A039C019F37049F21039F4E148D1A8A029F02069F03069F1A0295055F2A029A039C019F37049F21039F631000000000000000000000000000000000861203030F700D9F080200205F300202209F1F00860D04010A70089F1401009F230100860E05010B70099F740645434330303186790D01769F510201569F77060000001000009F78060000001000009F6B060000009999999F6D060000000000009F6C0200009F5D060000000000019F68049900F0009F570201569F7906000000000000572844B3E2DDC7E153E979BD37CF2ECF72EDB351948789AA282B252B398C26125E2E8084D6609EB5542386460E01439F5601809F4F199A039F21039F02069F03069F1A025F2A029F4E149C019F36029F5301009F54060000000000009F5C060000000000009F5801";
		temp += "009F5901009F5202C0008633800030918B00F0E5A834BBE76929236BB277F756A64EBC0F50FAEB24BFE9049A0292912F5B27D365E4164FB87AA2F0892054E7860C9000092C9FB69FA5381CD273860B801008B7C22021835E2BFD86059010020303865B82015829BEE814A35F073F8D9C645D42E8AD4E29C0794D378FD26ADA0626D0EC6070DB4BFEF8C270A807248281A7D32BA5C56286EA6C2E80D879BE8D774BEC0AA4FD1CF662C338A228C5DA25FE9A3DD893F47AF6353C2583CA28FA865B8202589710B80D72644620D16BE25F606FD9BCD3AB02CAD01DAB9C46C81E37021DFC59CF59A35A403A09DA1BDD4B26253F8276BC5C61DB97B70734ADF1209847DE66F129606B0FB0D079AB4AE334FC3600FB9FC8A58A8E47EB8103865B8203587F1427441D48EE5168EB100AE49D4BBA303C7DC2DF2B84AE6EDEC376BBEE17EAC3FF47A77CAF7CCEC236B6FEB333D7445586EA7DDBBCAD1F002D0D3E22EFBFE47FA80B82A5FB041193495A4CFC21ABAAE81804F8B55C66A2865B8204589840473F5B06FFB3B8664215323518A015493A4C344A692B6A780B0513C443927F866E9F58494BD3E41BE12C7D64C979909FDB41E1A386163C3158223B0DB1F0FAC45B64E7F84A45DF50E82F087BC585A95ED67268AB8BD7865B8205580FFD7B02761733413C4CFF55BC014F843EF5D9837738A65972EEDEC19B69570830EDB2C6F610";
		temp += "2860AAEF506B00E6A32A06246522B323688862B465FD5B67D6E43E56C0FDAB32D5E9CFCCA4549404A3720BD1E209991C49AB863E91023BA539500A50424F432044454249548701019F38099F7A019F02065F2A025F2D027A689F1101019F120A50424F43204445424954BF0C059F4D020B0A8645910342A540500A50424F432044454249548701019F38189F66049F02069F03069F1A0295055F2A029A039C019F37045F2D027A689F1101019F120A50424F43204445424954861991041682027C00941008010100100103011801030020010100862292071F82027C00940C1004060118010100280101009F100A07010103000000010A01861092000D9F100A07010103000000010A01861D92031A82027C0094140801010010040701180103002001010028010100861B777718E040BEBFD281BB507448F84FE715C9F70A70644347F119B7861191020EA50C8801015F2D027A689F110101862001011D701B61194F08A000000333010101500A50424F432044454249548701018623910220A51EBF0C1B61194F08A000000333010101500A50424F432044454249548701018603999900";

		TLVList tlvList = new TLVList(WDByteUtil.HEX2Bytes(temp), TLV.EMV);
		System.out.println(WDByteUtil.bytes2HEX(tlvList.toByteString()));
		System.out.println(WDByteUtil.bytes2HEX(tlvList.find(0x86).getLV()));

		System.out.println(tlvList.length);
		
		for (TLV tlv : tlvList.tlvList) {
			System.out.println(WDByteUtil.bytes2HEX(tlv.getTLV()));
		}
		/*
		 * String testVec = "313133313133313133313133"; String testVec1 = "81828384858687"; byte[] value1 = Hex.decode(testVec); byte[] value2 = Hex.decode(testVec1);
		 * 
		 * byte[] value = new byte[200];
		 * 
		 * for(int i=0;i<200;i++) value[i] =(byte)i;
		 * 
		 * TLV MyTLV = new TLV(0x03,value1,TLV.EMV); TLV MyTLV2 = new TLV(0x9f03,value,TLV.EMV);
		 * 
		 * byte tlvStream[] = new byte[MyTLV.getTLV().length + MyTLV2.getTLV().length]; System.arraycopy(MyTLV.getTLV(),0,tlvStream,0,MyTLV.getTLV().length); System.arraycopy(MyTLV2.getTLV(),0,tlvStream,MyTLV.getTLV().length,MyTLV2.getTLV().length);
		 * 
		 * 
		 * log.debug("parseParameters"); log.debug(TLVList.parseParameters(tlvStream,TLV.EMV));
		 * 
		 * 
		 * TLVList myList = new TLVList(tlvStream,TLV.EMV);
		 * 
		 * myList.appendValue(0x03,value2);
		 * 
		 * log.debug("toByteString()"); log.debug( new String(Hex.encode(myList.toByteString())));
		 */

		/*
		 * MyTLV = new TLV(0x05,value,TLV.EMV); MyTLV2 = new TLV(0x9f04,value1,TLV.EMV);
		 * 
		 * byte tlvStream1[] = new byte[MyTLV.getTLV().length + MyTLV2.getTLV().length]; System.arraycopy(MyTLV.getTLV(),0,tlvStream1,0,MyTLV.getTLV().length); System.arraycopy(MyTLV2.getTLV(),0,tlvStream1,MyTLV.getTLV().length,MyTLV2.getTLV().length);
		 * 
		 * myList.append(tlvStream1); System.out.println("after append the length is"); System.out.println(myList.length);
		 */

		/*
		 * MyTLV = myList.find(0x3);
		 * 
		 * if(MyTLV != null) { log.debug(new String(Hex.encode(MyTLV.getL()))); log.debug(new String(Hex.encode(MyTLV.getLV()))); log.debug(MyTLV.getTag() ); log.debug(new String(Hex.encode(MyTLV.getTLV()))); log.debug(new String(Hex.encode(MyTLV.getTV()))); log.debug(new String(Hex.encode(MyTLV.getValue()))); } else log.error("not found tag");
		 */

		/*
		 * myList.append(0x9f44,value); System.out.println("after append 0x9f44 the length is"); System.out.println(myList.length);
		 * 
		 * MyTLV = myList.find(0x9f44);
		 * 
		 * if(MyTLV != null) { System.out.println(new String(Hex.encode(MyTLV.getL()))); System.out.println(new String(Hex.encode(MyTLV.getLV()))); System.out.println(MyTLV.getTag() ); System.out.println(new String(Hex.encode(MyTLV.getTLV()))); System.out.println(new String(Hex.encode(MyTLV.getTV()))); System.out.println(new String(Hex.encode(MyTLV.getValue()))); } else System.out.println("not found tag");
		 * 
		 * MyTLV = myList.find(0x9f05); if(MyTLV != null) { System.out.println(new String(Hex.encode(MyTLV.getL()))); System.out.println(new String(Hex.encode(MyTLV.getLV()))); System.out.println(MyTLV.getTag() ); System.out.println(new String(Hex.encode(MyTLV.getTLV()))); System.out.println(new String(Hex.encode(MyTLV.getTV()))); System.out.println(new String(Hex.encode(MyTLV.getValue()))); } else System.out.println("not found tag");
		 */
		/*
		 * MyTLV = myList.index(0); System.out.println(new String(Hex.encode(MyTLV.getL()))); System.out.println(new String(Hex.encode(MyTLV.getLV()))); System.out.println(MyTLV.getTag() ); System.out.println(new String(Hex.encode(MyTLV.getTLV()))); System.out.println(new String(Hex.encode(MyTLV.getTV()))); System.out.println(new String(Hex.encode(MyTLV.getValue())));
		 * 
		 * System.out.println(myList.findIndex(0x03)); System.out.println(myList.findIndex(0x9f05)); System.out.println(myList.findIndex(0x9f01));
		 * 
		 * myList.deleteByTag(0x9f05);
		 * 
		 * System.out.println(myList.findIndex(0x03)); System.out.println(myList.findIndex(0x9f05)); System.out.println(myList.findIndex(0x9f01));
		 */
	}

}