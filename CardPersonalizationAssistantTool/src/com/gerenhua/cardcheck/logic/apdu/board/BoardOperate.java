package com.gerenhua.cardcheck.logic.apdu.board;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class BoardOperate {
	static {
		System.loadLibrary("wdcrwv");
	}
	public interface CLibrary extends Library {
	    CLibrary INSTANCE = (CLibrary)Native.loadLibrary("BoardCard", CLibrary.class);
	   
	    int Open(String boardName,int devModel,byte nouse);
	    String Reset();
	    String Send(int len,String comm);
		int Close();
	}
	
	public static void main(String[] args) {
		int handle=CLibrary.INSTANCE.Open("USB1", 1, (byte)1);
		System.out.println(handle);
		
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Send(38, "00a404000e315041592e5359532e4444463031"));
		System.out.println(CLibrary.INSTANCE.Reset());
		System.out.println(CLibrary.INSTANCE.Close());
	}
}
