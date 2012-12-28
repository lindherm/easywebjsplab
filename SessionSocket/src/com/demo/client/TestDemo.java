package com.demo.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestDemo {
	public static void main(String[] args) throws UnknownHostException, IOException {
		SessionClient sessionClient=new SessionClient("nihao", "10.0.73.14", 9000);
		sessionClient.send("100000023".getBytes(), "nihao");
		
		System.out.println(new String(sessionClient.recive("nihao")));
	}
}
