package com.sessionsocket.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.watchdata.commons.lang.WDByteUtil;

public class TestDemo {
	public void test(Socket socket) throws UnknownHostException, IOException {
		// SessionClient sessionClient = new SessionClient("hello", "127.0.0.1", 9000);
		// TcpConnector tcpConnector = new TcpConnector("127.0.0.1", 3003);

		File file = new File("D:\\ccspace\\Business_WD_CAMS_Prj_Dev\\PayID_Business_VOB\\Business_WD_CAMS\\WD_CAMS\\Product\\应用模板\\湖北农信\\测试数据\\Native\\湖北农信金融社保卡送检数据（20121207）.rar");
		//File file = new File("D:\\eclipse-SDK-4.2.rar");
		FileInputStream fis = new FileInputStream(file);

		int a = fis.available();

		byte[] bs = new byte[a];

		fis.read(bs);
		fis.close();
		String filelength = Integer.toHexString(a);
		while (filelength.length() < 8) {
			filelength = "0" + filelength;
		}
		byte[] lenth = WDByteUtil.HEX2Bytes(filelength);
		byte[] temp = new byte[a + 4];
		System.arraycopy(lenth, 0, temp, 0, 4);
		System.arraycopy(bs, 0, temp, 4, a);
		// System.out.println(new String(temp));
		// sessionClient.send(temp, "hello");

		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
		BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		out.write(temp);
		out.flush();
		// out.close();

		// InputStream in= socket.getInputStream();

		// byte[] tem1p=new byte[1024];
		String www = read.readLine();
		System.out.println(www);

	}
	
	public void test1() throws UnknownHostException, IOException {
		SessionClient sessionClient=new SessionClient("9000", "127.0.0.1", 9000);
		TcpConnector tcpConnector=new TcpConnector("10.0.97.124", 5050);
		sessionClient.addConnector("helo",tcpConnector);
		File file = new File("D:\\ccspace\\Business_WD_CAMS_Prj_Dev\\PayID_Business_VOB\\Business_WD_CAMS\\WD_CAMS\\Product\\应用模板\\湖北农信\\测试数据\\Native\\湖北农信金融社保卡送检数据（20121207）.rar");
		FileInputStream fis = new FileInputStream(file);

		int a = fis.available();

		byte[] bs = new byte[a];

		fis.read(bs);
		fis.close();
		String filelength = Integer.toHexString(a);
		while (filelength.length() < 8) {
			filelength = "0" + filelength;
		}
		byte[] lenth = WDByteUtil.HEX2Bytes(filelength);
		byte[] temp = new byte[a + 4];
		System.arraycopy(lenth, 0, temp, 0, 4);
		System.arraycopy(bs, 0, temp, 4, a);
		
		sessionClient.send(temp, "helo");
		
		String www = sessionClient.recive("helo");
		System.out.println(www);
		
		sessionClient.send(temp, "9000");
		
		System.out.println(sessionClient.recive("9000"));

	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		TestDemo hello = new TestDemo();
		Socket socket = new Socket("127.0.0.1", 9000);
		for(int i=0;i<1000;i++){
			//Socket socket = new Socket("127.0.0.1", 9000);
			hello.test(socket);
		}
		hello.test1();
	}
}
