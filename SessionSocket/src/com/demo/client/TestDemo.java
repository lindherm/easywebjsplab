package com.demo.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import com.watchdata.commons.lang.WDStringUtil;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		SessionClient sessionClient = new SessionClient("hello", "127.0.0.1", 9000);
		// TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);
		File file = new File("D:/data/420000000_20120502_CCBS.zip");
		FileInputStream fis = new FileInputStream(file);

		int a = fis.available();

		byte[] bs = new byte[a];

		fis.read(bs);
		
		String filelength=WDStringUtil.paddingHeadZero(String.valueOf(a), 8);
		byte[] lenth=filelength.getBytes();
		byte[] temp=new byte[a+8];
		System.arraycopy(lenth, 0, temp, 0, 8);
		System.arraycopy(bs, 0, temp, 8, a);
		//System.out.println(new String(temp));
		sessionClient.send(temp, "hello");
		System.out.println(new String(sessionClient.recive("hello")));
	}
}
