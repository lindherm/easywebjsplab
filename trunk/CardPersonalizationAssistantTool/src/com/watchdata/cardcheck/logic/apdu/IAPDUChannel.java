package com.watchdata.cardcheck.logic.apdu;
/**
 * 
 * @description: APDU指令通道
 * @author: juan.jiang Apr 10, 2012
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public interface IAPDUChannel {
	/**
	 * 指令发送
	 * @param commandApdu
	 * @return
	 */
	public String send(String commandApdu) ;
	
	/**
	 * 通道初始化
	 * @param readName
	 */
	public boolean init(String readName);
	
	public String reset();
	
	public void close();

}
