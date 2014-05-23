package com.gerenhua.tool.logic.apdu.readerx;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.apdu.IAPDUChannel;
import com.gerenhua.tool.logic.apdu.readerx.ReaderXOperate.CLibrary;
import com.watchdata.commons.lang.WDByteUtil;

public class ReaderXChannel implements IAPDUChannel {
	private static Log logger = new Log();
	private static CLibrary handle = null;

	public ReaderXChannel() {
		handle = CLibrary.INSTANCE;
	}

	@Override
	public String send(String commandApdu) {
		logger.debug("send[" + commandApdu.toUpperCase() + "]");
		byte len=(byte)(commandApdu.length()/2);
		String recv = handle.Send(len, WDByteUtil.HEX2Bytes(commandApdu));
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
		IAPDUChannel bo = new ReaderXChannel();
		bo.init("USB1");
		bo.reset();
		bo.close();
	}
}
