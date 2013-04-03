package com.demo.client;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class testGemen {
	public interface CLibrary extends Library {
		// 加载库文件
		public static CLibrary INSTANCE = (CLibrary) Native.loadLibrary("GEMEN", CLibrary.class);

		// 打开设备
		int encrypt(int slotId, String pin, String keyName, String libobj, int mech, String in, int inlen, byte[] out, String iv);
	}
	
	public static void main(String[] args) {
		int slotId=1;
		String pin="9999";
		String keyName="1";
		int mech=5;
		String in="0000000000000000";
		byte[] out=new byte[32];
		String iv="0000000000000000";
		
		int ret=CLibrary.INSTANCE.encrypt(slotId, pin, keyName, "", mech, in, in.length(), out, iv);
		System.out.println(ret);
		System.out.println(new String(out));
	}
}
