package com.demo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.socketUtility.SessionSocket;
public class ClientSocket extends SessionSocket {

	public ClientSocket(Socket socket) {
		super(socket);
	}

	public static void main(String args[]) {
		Socket socket;
		try {
			socket = new Socket("10.0.73.14", 5050);
			ClientSocket clientSocket = new ClientSocket(socket);
			// 启动ClientSocket线程
			clientSocket.start();
			Thread iMonitor = new Thread(clientSocket.new InputMonitor());
			iMonitor.setDaemon(true);
			// 启动输入
			iMonitor.start();

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public void onClose(Socket socket, Thread thread) {
		System.out.println("与服务器断开连接。");
	}

	public void onConnected(Socket socket, Thread thread) {
		System.out.println("连接服务器成功！");
	}

	public void onDataArrived(byte[] data, Socket socket, Thread thread) {
		System.out.println(socket.getRemoteSocketAddress());
		System.out.println(new String(data));
	}

	public void onError(Exception e, Socket socket, Thread thread) {
		System.out.println("与服务器连接异常，断开连接。");
	}

	class InputMonitor implements Runnable {
		String nickName = null;

		public void run() {
			while (true) {
				System.out.println("请输入报文>>:");
				nickName=getInput();
				try {
					if (nickName!="") {
						sendMessage(nickName.getBytes());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		public String getInput() {
			InputStream inputStream = System.in;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String inputString = reader.readLine();
				return inputString;
			} catch (Exception e) {
				return null;
			}
		}

	}
}
