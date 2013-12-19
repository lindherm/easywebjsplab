package com.watchdata.cardcheck.logic.apdu.board;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.apdu.IAPDUChannel;
import com.watchdata.cardcheck.logic.apdu.board.BoardOperate.CLibrary;

public class BoardChannel implements IAPDUChannel {
	private static Log logger = new Log();	
	CLibrary handle=null;
	public BoardChannel(){
		handle=CLibrary.INSTANCE;
	}

	@Override
	public String send(String commandApdu) {
		logger.debug("send[" + commandApdu.toUpperCase() + "]");
		String recv = handle.Send(commandApdu.length(), commandApdu);
		if (recv.substring(recv.length() - 4,recv.length()).equalsIgnoreCase("9000")) {
			logger.debug("recv[" + recv + "]");
		}else {
			logger.error("recv[" + recv + "]");
		}
		
		return recv;
	}

	@Override
	public boolean init(String readName) {
		// TODO Auto-generated method stub
		int ret=handle.Open("USB1", 1, (byte)1);
		if (ret==0) {
			return true;
		}
		return false;
	}

	@Override
	public String reset() {
		// TODO Auto-generated method stub
		return handle.Reset();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		handle.Close();
	}

	public static void main(String[] args) {
		IAPDUChannel bo=new BoardChannel();
		bo.init("USB1");
		bo.reset();
		bo.close();
	}
}
