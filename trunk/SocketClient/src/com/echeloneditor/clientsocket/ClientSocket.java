package com.echeloneditor.clientsocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	public static ThreadGroup threadGroup = new ThreadGroup("hello");

	public String sendSynMsg(String ipAddr, byte[] datas) {
		// 解析服务器地址和端口号
		int dotPos = ipAddr.indexOf(':');
		String ip = ipAddr.substring(0, dotPos).trim();
		int port = Integer.parseInt(ipAddr.substring(dotPos + 1).trim());
		// InetSocketAddress endpoint = new InetSocketAddress(ip, port);

		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		String result = null;
		try {
			if (socket == null) {
				socket = new Socket(ip, port);
			}
			// 设置发送逗留时间2秒
			socket.setSoLinger(true, 2);
			// 设置InputStream上调用 read()阻塞超时时间2秒
			socket.setSoTimeout(100000);
			// 设置socket发包缓冲为32k；
			socket.setSendBufferSize(32 * 1024);
			// 设置socket底层接收缓冲为32k
			socket.setReceiveBufferSize(32 * 1024);
			// 关闭Nagle算法.立即发包
			socket.setTcpNoDelay(true);
			// 连接服务器
			// socket.connect(endpoint);
			// 获取输出输入流
			out = socket.getOutputStream();
			// 输出请求
			out.write(datas);
			out.flush();
			// 接收应答
			in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			result = br.readLine();

			if (result.equals("") || result == null) {
				System.out.println("result:" + result);
				threadGroup.stop();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			threadGroup.stop();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		// for (int i = 0; i < 100; i++) {
		// Thread thread = new Thread(threadGroup, new Runnable() {

		// public void run() {
		// while (true) {
		String dataue = "10114002400100328E019173E02D7856942963C7680057EA0010032FA4D388B4A81B777C47CBD8087D0F4DA0032FE47870EA7EB10364ED1628065C01201";
		String datatm="1007500251010032000000000000F0030000000000000F030032BDBAC27997687C311B5E891FF402440D";
		String datats="1007700260010032F4A5EF84139E7D0E843EF7F46A22FA7E010032158FC26B00AE19D56B17669D71DDB7ED";
		String data50="10090002710033X8B28258792DC5BA807F74CB6CBD456A000488486AA02DFC34E9EA74BBCED5D700A0FB0A905ED9734C1E0";
		String dataa6="1007800280070033X611FD44000B483F0ECBD9D5E85D8C1A00033X6E4748602C6937E7C3565DA21446A7C8X";
		/*
		 * try { datas=""; //datas = InetAddress.getLocalHost() + Thread.currentThread().getName() + "_3213465413612313213213210"; } catch (UnknownHostException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		ClientSocket clientSocket = new ClientSocket();
		// System.out.println(Thread.currentThread().getName() + " send:" + datas);
		try {
			System.out.println(data50);
			String receive = clientSocket.sendSynMsg("10.0.73.14:5050", dataa6.getBytes());
			// System.out.println(Thread.currentThread().getName() + " receive:" + receive);
			System.out.println(receive);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }
		// }
		// });
		// System.out.println(thread.getName() + "started.");
		// thread.start();
		// }
	}
}
