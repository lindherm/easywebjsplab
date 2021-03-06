package com.gerenhua.tool.logic.apdu.pcsc;

import java.util.ArrayList;
import java.util.List;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.apdu.IAPDUChannel;
import com.gerenhua.tool.utils.Config;
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
		//logger.setLogArea(TradePanel.textPane);
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
	
	public List<String> getReaderList(){
		List<String> list=new ArrayList<String>();
		String[] termList=cardPcsc.getReaderList();
		
		if(termList != null){
			for (String term : termList) {
				list.add(term);
			}
		}
		
		for (String board : Config.getItems("BoardList")) {
			list.add(Config.getValue("BoardList", board));
		}
		
		for (String usb : Config.getItems("USBreaderList")) {
			list.add(Config.getValue("USBreaderList", usb));
		}
		
		return list;
	}
	
	public void close(){
		cardPcsc.disConnectReader();
	}
}
