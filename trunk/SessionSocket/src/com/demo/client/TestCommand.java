package com.demo.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestCommand {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket("192.168.0.101", 9000);
		OutputStream out=socket.getOutputStream();
		out.write("00000009helloname".getBytes());
		out.flush();
	}
}
