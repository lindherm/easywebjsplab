package com.demo.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import com.watchdata.commons.lang.WDStringUtil;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		SessionClient sessionClient=new SessionClient("nihao", "10.0.73.14", 9000);
		File file=new File("D:/data/420000000_20121019_CCBS.zip");
		FileInputStream fis=new FileInputStream(file);
		byte[] data=new byte[fis.available()];
		fis.read(data);
		System.out.println(data.length);
		byte[] len=WDStringUtil.paddingHeadZero("25511", 8).getBytes();
		System.out.println("len:"+new String(len));
		
		byte[] send=new byte[25519];
		System.arraycopy(len, 0, send, 0, 8);
		System.arraycopy(data, 0, send, 8, data.length);
		sessionClient.send(send, "nihao");
		
		//System.out.println(new String(sessionClient.recive("nihao")));
	}
}
