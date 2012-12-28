package com.demo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		/*SessionClient sessionClient = new SessionClient("hello", "10.0.73.14", 3002);
		// TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);
		File file = new File("D:/data/mt1.rar");
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
		System.out.println(new String(sessionClient.recive("hello")));*/
	}
}
