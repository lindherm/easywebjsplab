package com.watchdata.cardcheck.logic.issuer;
/**
 * 
 * @description: 发卡行数据访问接口
 * @author: juan.jiang 2012-3-19
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public interface IIssuerDao {
	/**
	 * 验证ARQC密文
	 * @param pan
	 * @param panSerial
	 * @param cdolData
	 * @param aip
	 * @param atc
	 * @param iad
	 * @param arqc
	 * @return
	 */	 
	public boolean validateArqc(String pan, String panSerial, String cdolData, String aip, String atc, String iad, String arqc);
	
	/**
	 * 请求ARPC密文
	 * @param pan
	 * @param panSerial
	 * @param cdolData
	 * @param aip
	 * @param atc
	 * @param iad
	 * @param arqc
	 * @return
	 * @throws Exception
	 */
	public String requestArpc(String pan, String panSerial, String cdolData, String aip, String atc, String iad, String arqc)throws Exception;
	
	/**
	 * 生成圈存的发卡行脚本
	 * @param pan
	 * @return
	 */
	public String[] generateLoadIssuerScript(String pan, String panSerial, String atc, String arqc, String balance,int tradeAmount)throws Exception;
	
}
