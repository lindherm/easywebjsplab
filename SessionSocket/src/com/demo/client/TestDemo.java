package com.demo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		SessionClient sessionClient = new SessionClient("hello", "127.0.0.1", 3002);
		TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);

		byte[] getmsg = sessionClient.send("gohello".getBytes(), "hello");
		System.out.println(new String(getmsg));

		sessionClient.addConnector("go", tcpConnector);

		getmsg = sessionClient.send("1111".getBytes(), "go");

		System.out.println(new String(getmsg));

		getmsg = sessionClient.send("1111".getBytes(), "hello");

		System.out.println(new String(getmsg));
		
		System.out.println(SessionClient.getSession().size());
		
		sessionClient.closeAll();
		
		getmsg = sessionClient.send("test".getBytes(), "go");

		System.out.println(new String(getmsg));
	}
}
