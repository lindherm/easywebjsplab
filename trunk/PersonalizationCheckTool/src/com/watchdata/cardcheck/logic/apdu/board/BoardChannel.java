package com.watchdata.cardcheck.logic.apdu.board;

import com.watchdata.cardcheck.logic.apdu.IAPDUChannel;
import com.watchdata.cardcheck.logic.apdu.board.BoardOperate.CLibrary;
import com.watchdata.commons.lang.WDByteUtil.BOM;

public class BoardChannel implements IAPDUChannel {
	CLibrary handle=null;
	public BoardChannel(){
		handle=CLibrary.INSTANCE;
	}

	@Override
	public String send(String commandApdu) {
		// TODO Auto-generated method stub
		return handle.Send(commandApdu.length(), commandApdu);
	}

	@Override
	public boolean init(String readName) {
		// TODO Auto-generated method stub
		int ret=handle.Open(readName, 1, (byte)1);
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
