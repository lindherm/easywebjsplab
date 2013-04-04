package com.demo.client;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class testGemen {
	public interface CLibrary extends Library {
		Object libgmnpkcs11 = Native.loadLibrary("./Dll/Gemen/libgmnpkcs11", CLibrary.class); //加载依赖库
		Object gdbm3 = Native.loadLibrary("./Dll/Gemen/gdbm3", CLibrary.class); //加载依赖库
		Object SJL22_API = Native.loadLibrary("./Dll/Gemen/SJL22_API", CLibrary.class); //加载依赖库

		// 加载库文件
		public static CLibrary INSTANCE = (CLibrary) Native.loadLibrary("./Dll/Gemen/GEMEN", CLibrary.class);
		
		int encrypt(int slotId, String pin, String keyName, String libobj, int mech, String in, int inlen, byte[] out, String iv);
	}
	
	public static void main(String[] args) {
		Native.synchronizedLibrary(CLibrary.INSTANCE);

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
