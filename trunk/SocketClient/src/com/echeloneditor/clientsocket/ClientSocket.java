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
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(threadGroup, new Runnable() {

				@Override
				public void run() {
					while (true) {
						String datas = null;
						try {
							datas = InetAddress.getLocalHost() + Thread.currentThread().getName() + "_3213465413612313213213210";
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ClientSocket clientSocket = new ClientSocket();
						System.out.println(Thread.currentThread().getName() + " send:" + datas);
						try {
							String receive = clientSocket.sendSynMsg("10.0.73.14:1001", datas.getBytes());
							System.out.println(Thread.currentThread().getName() + " receive:" + receive);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			System.out.println(thread.getName() + "started.");
			thread.start();
		}
	}
}
