package com.gerenhua.tool.logic.apdu.readerx;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.watchdata.commons.lang.WDByteUtil;

public class ReaderXOperate {
	static {
		System.loadLibrary("wdcrwx");
	}
	public interface CLibrary extends Library {
	    CLibrary INSTANCE = (CLibrary)Native.loadLibrary("ReaderX", CLibrary.class);
	   
	    int Open(String boardName,int devModel,byte nouse);
	    String Reset();
	    String Send(byte len,byte[] comm);
		int Close();
	}
	
	public static void main(String[] args) {
		int handle=CLibrary.INSTANCE.Open("USB1", 1, (byte)0);
		System.out.println(handle);
		
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Send((byte)5, WDByteUtil.HEX2Bytes("00a4040000")));
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Close());
	}
}
