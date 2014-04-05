package com.gerenhua.tool.logic.apdu.board;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.apdu.IAPDUChannel;
import com.gerenhua.tool.logic.apdu.board.BoardOperate.CLibrary;

public class BoardChannel implements IAPDUChannel {
	private static Log logger = new Log();
	private static CLibrary handle = null;

	public BoardChannel() {
		handle = CLibrary.INSTANCE;
	}

	@Override
	public String send(String commandApdu) {
		logger.debug("send[" + commandApdu.toUpperCase() + "]");
		String recv = handle.Send(commandApdu.length(), commandApdu);
		String sw = recv.substring(recv.length() - 4, recv.length());
		String sw2=sw.substring(sw.length() - 2, sw.length());
		if (sw.equalsIgnoreCase("9000")) {
			logger.debug("recv[" + recv + "]");
		} else if (sw.startsWith("61")) {
			logger.debug("recv[" + recv + "]");
			recv=send("00C00000" + sw2);
		} else if (sw.toUpperCase().startsWith("6C")) {
			logger.debug("recv[" + recv + "]");
			recv=send(commandApdu.substring(0,commandApdu.length()-2) + sw2);
		} else {
			logger.error("recv[" + recv + "]");
		}
		return recv;
	}

	@Override
	public boolean init(String readName) {
		// TODO Auto-generated method stub
		int ret = handle.Open("USB1", 1, (byte) 1);
		if (ret == 0) {
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
		IAPDUChannel bo = new BoardChannel();
		bo.init("USB1");
		bo.reset();
		bo.close();
	}
}
