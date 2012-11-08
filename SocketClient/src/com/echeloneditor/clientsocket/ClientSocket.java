package com.echeloneditor.clientsocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.watchdata.commons.lang.WDByteUtil;

public class ClientSocket {
	String _serverAddres = "127.0.0.1";
	int _serverPort = 6666;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;

	public ClientSocket(String _serverAddress, int _serverPort) {
		this._serverAddres = _serverAddress;
		this._serverPort = _serverPort;

	}

	// 建立连接
	public void connect() throws IOException {
		Socket socket = new Socket();
		
		socket.connect(new InetSocketAddress(_serverAddres, _serverPort), 1000 * 3);

		bis = new BufferedInputStream(socket.getInputStream());
		bos = new BufferedOutputStream(socket.getOutputStream());
	}

	// 发送数据
	public int write(byte[] bs, int pos, int length) throws IOException {
		bos.write(bs, pos, length);
		bos.flush();

		return 0;
	}

	// 接收数据
	public int read(byte[] bs, int pos, int length) throws IOException {
		int i = bis.read(bs, pos, length);
		return i;
	}

	// 释放连接
	public void disconnect() throws IOException {
		if (bos != null) {
			bos.close();
		}

		if (bis != null) {
			bis.close();
		}
	}

	public static void main(String[] args) throws IOException {

		String dataue = "10114002400100328E019173E02D7856942963C7680057EA0010032FA4D388B4A81B777C47CBD8087D0F4DA0032FE47870EA7EB10364ED1628065C01201";
		String datatm = "1007500251010032000000000000F0030000000000000F030032BDBAC27997687C311B5E891FF402440D";
		String datats = "1007700260010032F4A5EF84139E7D0E843EF7F46A22FA7E010032158FC26B00AE19D56B17669D71DDB7ED";
		String data50 = "10090002710033X8B28258792DC5BA807F74CB6CBD456A000488486AA02DFC34E9EA74BBCED5D700A0FB0A905ED9734C1E0";
		String dataa6 = "1007800280070033X611FD44000B483F0ECBD9D5E85D8C1A00033X6E4748602C6937E7C3565DA21446A7C8X";

		ClientSocket cs = new ClientSocket("10.0.73.131", 6666);
		cs.connect();
		byte[] writedata=WDByteUtil.HEX2Bytes("B2030000000000000000");
		//byte[] writedata=WDByteUtil.HEX2Bytes("00");
		byte[] readdata=new byte[1024];
		cs.write(writedata, 0, writedata.length);
		
		int len=cs.read(readdata, 0, readdata.length);
		
		byte[] out=new byte[len];
		System.arraycopy(readdata, 0, out, 0, len);
		System.out.println(WDByteUtil.bytes2HEX(out));
		cs.disconnect();
	}
}
