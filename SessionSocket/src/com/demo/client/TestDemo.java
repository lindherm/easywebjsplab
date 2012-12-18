package com.demo.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		SessionClient sessionClient = new SessionClient("hello", "10.0.73.14", 3002);
		// TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);
		File file = new File("D:/data/mt1.rar");
		FileInputStream fis = new FileInputStream(file);

		int a = fis.available();

		byte[] bs = new byte[a];

		fis.read(bs);
		sessionClient.send(bs, "hello");
		//sessionClient.closeAll();
	}
}
