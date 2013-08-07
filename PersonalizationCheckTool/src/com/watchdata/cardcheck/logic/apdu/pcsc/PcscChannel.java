package com.watchdata.cardcheck.logic.apdu.pcsc;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.apdu.IAPDUChannel;
import com.watchdata.cardpcsc.CardPcsc;
import com.watchdata.commons.lang.WDByteUtil;
/**
 * 
 * @description: PCSC通道
 * @author: juan.jiang Apr 10, 2012
 * @version: 1.0.0
 * @modify:
 * @Copyright: watchdata
 */
public class PcscChannel implements IAPDUChannel{
	private static Log logger = new Log();	
	private static CardPcsc cardPcsc;
	public PcscChannel(){
		if(cardPcsc==null){
			cardPcsc = new CardPcsc();
		}
	}
	
	public boolean init(String reader) {
		return cardPcsc.connectReader(reader);
	}
	public String reset(){
		return WDByteUtil.bytes2HEX(cardPcsc.resetCard());
	}
	
	public String send(String commandApdu) {
		logger.debug("send[" + commandApdu.toUpperCase() + "]");
		String recv = cardPcsc.SendApdu(WDByteUtil.HEX2Bytes(commandApdu));
		if (recv.substring(recv.length() - 4,recv.length()).equalsIgnoreCase("9000")) {
			logger.debug("recv[" + recv + "]");
		}else {
			logger.error("recv[" + recv + "]");
		}
		
		return recv;
	}
	public String[] getReaderList(){
		if(cardPcsc.getReaderList() != null){
			return cardPcsc.getReaderList();
		}else{
			return null;
		}
	}
	
	public void destroy(){
		cardPcsc.disConnectReader();
	}
}
