package com.demo.client;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class testEracom {
	public interface CLibrary extends Library {
		Object libgmnpkcs11 = Native.loadLibrary("./Dll/Eracom/ctutil", CLibrary.class); //加载依赖库
		Object gdbm3 = Native.loadLibrary("./Dll/Eracom/cryptoki", CLibrary.class); //加载依赖库
		Object pthreadVC2 = Native.loadLibrary("./Dll/Eracom/pthreadVC2", CLibrary.class); //加载依赖库

		// 加载库文件
		public static CLibrary INSTANCE = (CLibrary) Native.loadLibrary("./Dll/Eracom/EracomDll", CLibrary.class);
		
		int encrypt(int slotId, String pin, String keyName, String libobj, int mech, String in, int inlen, byte[] out, String iv);
	}
	
	public static void main(String[] args) {
		Native.synchronizedLibrary(CLibrary.INSTANCE);

		for (int i = 0; i < 100000; i++) {
			int slotId=0;
			String pin="9999";
			String keyName="A000000000008363";
			int mech=5;
			String in="0000000000000000";
			byte[] out=new byte[32];
			String iv="0000000000000000";
			
			int ret=CLibrary.INSTANCE.encrypt(slotId, pin, keyName, "", mech, in, in.length(), out, iv);
			System.out.println(i+":"+ret);
			System.out.println(new String(out));
		}
		
	}
}
