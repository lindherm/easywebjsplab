package com.demo.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestDemo {
	public void test(Socket socket) throws UnknownHostException, IOException{
		//SessionClient sessionClient = new SessionClient("hello", "127.0.0.1", 9000);
		// TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);
		
		
		File file = new File("D:\\ccspace\\Business_WD_CAMS_Prj_Dev\\PayID_Business_VOB\\Business_WD_CAMS\\WD_CAMS\\Product\\应用模板\\湖北农信\\测试数据\\Native\\湖北农信金融社保卡送检数据（20121207）.rar");
		FileInputStream fis = new FileInputStream(file);

		int a = fis.available();

		byte[] bs = new byte[a];

		fis.read(bs);
		
		String filelength=String.valueOf(a);
		while (filelength.length()<8) {
			filelength="0"+filelength;
		}
		byte[] lenth=filelength.getBytes();
		byte[] temp=new byte[a+8];
		System.arraycopy(lenth, 0, temp, 0, 8);
		System.arraycopy(bs, 0, temp, 8, a);
		//System.out.println(new String(temp));
		//sessionClient.send(temp, "hello");
		socket.getOutputStream().write(temp);
		socket.getOutputStream().flush();
		byte[] tem1p=new byte[1024];
		socket.getInputStream().read(tem1p);
		System.out.println(new String(tem1p));
		//socket.getOutputStream().close();
		///socket.getInputStream().close();
		
	}
	public static void main(String[] args) throws UnknownHostException, IOException{
		TestDemo hello=new TestDemo();
		Socket socket = new Socket( "127.0.0.1", 9000);
		for (int i = 0; i <2000; i++) {
			hello.test(socket);
			//new TestDemo().test();
		}
		socket.shutdownOutput();
		socket.shutdownInput();
		socket.close();
		
	}
}
