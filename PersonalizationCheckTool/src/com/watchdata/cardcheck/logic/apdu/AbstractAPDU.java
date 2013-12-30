package com.watchdata.cardcheck.logic.apdu;

import java.util.HashMap;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.utils.Config;
import com.watchdata.cardcheck.utils.TermSupportUtil;
/**
 * 
 * @description: 指令打包解包封装抽象类
 * @author: juan.jiang 2012-3-21
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class AbstractAPDU {
	public static Log log = new Log();
	private static final String SPECIAL_TAG_LIST = "61|6F|A5|BF0C|77|70";
	public static final String P1_ARQC = "80";
	public static final String P1_TC = "40";
	public static final String P1_AAC = "00";
	
	private static HashMap<String,String> paraMap;
	
	public AbstractAPDU(){
		if(paraMap==null){
			paraMap = new HashMap<String,String>();
			paraMap.put("CLA_SELECT", "00");
			paraMap.put("INS_SELECT", "A4");
			paraMap.put("P1_SELECT", "04");
			paraMap.put("P2_SELECT", "00");
			
			paraMap.put("CLA_READ_RECORD", "00");
			paraMap.put("INS_READ_RECORD", "B2");			
			
			paraMap.put("CLA_GET_DATA", "80");
			paraMap.put("INS_GET_DATA", "CA");
						
			paraMap.put("CLA_GPO", "80");
			paraMap.put("INS_GPO", "A8");
			paraMap.put("P1_GPO", "00");
			paraMap.put("P2_GPO", "00");
			
			paraMap.put("CLA_INTERNAL_AUTHENTICATE", "00");
			paraMap.put("INS_INTERNAL_AUTHENTICATE", "88");
			paraMap.put("P1_INTERNAL_AUTHENTICATE", "00");
			paraMap.put("P2_INTERNAL_AUTHENTICATE", "00");
			
			paraMap.put("CLA_EXTERNAL_AUTHENTICATE", "00");
			paraMap.put("INS_EXTERNAL_AUTHENTICATE", "82");
			paraMap.put("P1_EXTERNAL_AUTHENTICATE", "00");
			paraMap.put("P2_EXTERNAL_AUTHENTICATE", "00");
			
			paraMap.put("CLA_GENERATE_AC", "80");
			paraMap.put("INS_GENERATE_AC", "AE");
			paraMap.put("P1_GENERATE_AC", "80");			
			paraMap.put("P2_GENERATE_AC", "00");
			
			paraMap.put("CLA_PUT_DATA", "04");
			paraMap.put("INS_PUT_DATA", "DA");
						
			paraMap.put("CLA_VERIFY", "00");
			paraMap.put("INS_VERIFY", "20");
			paraMap.put("P1_VERIFY", "00");
			paraMap.put("P2_VERIFY", "80");
		}
	}
	/**
	 * apdu命令打包
	 * @param commandType	命令类型
	 * @param data			命令参数数据
	 * @return
	 */
	public String packApdu(String commandType,String data){
		return packApdu(commandType,data,paraMap.get("P1_"+commandType),paraMap.get("P2_"+commandType));
	}
	
	public String packApdu(String commandType,String data,String p1){
		return packApdu(commandType,data,p1,paraMap.get("P2_"+commandType));
	}
	
	/**
	 * apdu命令打包
	 * @param commandType
	 * @param data
	 * @param p1
	 * @param p2
	 * @return
	 */
	public String packApdu(String commandType,String data,String p1,String p2){
		StringBuffer apduStr = new StringBuffer();
		apduStr.append(paraMap.get("CLA_"+commandType));
		apduStr.append(paraMap.get("INS_"+commandType));
		apduStr.append(p1);
		apduStr.append(p2);
		
		apduStr.append( CommonHelper.getLVData(data,1));
		
		return apduStr.toString();
	}
	
	
	
	/**
	 * apdu命令解析
	 * @param apdu
	 * @return
	 */
	public HashMap<String,String> unpackApdu(String apdu){
		HashMap<String,String> resp = new HashMap<String,String>();
		String sw = apdu.substring(apdu.length()-4, apdu.length());
		resp.put("sw", sw);
		log.debug("SW[" + sw + "]");
		if("9000".equalsIgnoreCase(sw)&&apdu.length()>4){
			parseTLV(resp,apdu.substring(0, apdu.length()-4));
		}
		
		return resp;
	}
	
	private void parseTLV(HashMap<String,String> resp,String apduData){
		StringBuffer apdu = new StringBuffer(apduData);
		while(true){
			String tag = apdu.substring(0, 2);
			apdu.delete(0, 2);
			if((Integer.parseInt(tag,16)& 0x1F) == 0x1F) {
				tag += apdu.substring(0, 2);
				apdu.delete(0, 2);
			}
			
			String tagLenStr = apdu.substring(0, 2);
			apdu.delete(0, 2);
			if("81".equals(tagLenStr)){//BER-TLV				
				tagLenStr = apdu.substring(0, 2);
				apdu.delete(0, 2);
			}
			
			int tagLen = Integer.parseInt(tagLenStr,16)*2;			
			String value = apdu.substring(0, tagLen);
			resp.put(tag, value);
			//deal gp log
			printGpLog(tag,tagLenStr,value);
			//递归解析TLV
			if(isComleteTag(tag)) parseTLV(resp,value);
			
			apdu.delete(0, tagLen);
			if(apdu.length()<=0) break;			
		}
	}
	//gp log
	private void printGpLog(String tag,String tagLen,String value){
		//print log with tag comment
		String tagComment=Config.getValue("TAG", tag);
		log.info(tag + "\t"+tagLen+" "+ value+"【"+tagComment+"】");
		if (tag.equalsIgnoreCase("8E")) {
			TermSupportUtil.parse8E(value);
		}
	}
	
	private boolean isComleteTag(String tag){
		if(SPECIAL_TAG_LIST.indexOf(tag)<0) return false;
		return true;
	}
	
	public static void main(String args[]){
		AbstractAPDU apdu = new AbstractAPDU();
		HashMap<String,String> r = apdu.unpackApdu("7081C59F4681B093D0B9BF08D9CE59C3966B6A3D1C7E53C1BEDFF351235EC5039B803DF05C3E43FCD5179C0F3B43E67316BC71B62E5F05D81AB20A4B84AFCB75E6B03B1821363ED7B70DAAEDE2C2B6ED69EA9DE0D2560EA410752C077EE6772FB50D508CE8381069DB5FCB70CD5DF2A91FEBC64C94E48B4B4917D4D07A30BE6EFBD73AE54381C10DF79AB057B186A06319B413E574BC898C81379D218AD03A33C1F5A7E000D7B2E2C0570A960B81C8C3D6B78FA85C613C9F4701039F480A450549787C351479462F9000");
		System.out.println(r.size());
		
	}
}
