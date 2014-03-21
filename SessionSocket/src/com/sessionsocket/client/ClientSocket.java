package com.sessionsocket.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sessionsocket.SessionSocket;

public class ClientSocket extends SessionSocket {
	public static String ip;
	public static String port;
	public ClientSocket(Socket socket) {
		super(socket);
	}

	public static void main(String args[]) {
		while (true) {
			String commandList=getInput();
			String com[]=commandList.split(" ");
			String command=com[0];
			if (command.equalsIgnoreCase("connect")) {
				if (com.length<3) {
					System.out.println("param is error.");
					//continue;
				}else {
					ip=com[1];
					port=com[2];
				}
				
				if (ip==""||port=="") {
					Socket socket;
					try {
						socket = new Socket(ip, Integer.parseInt(port));
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
			}else {
				System.out.println("command is not support.");
			}
		}
		
	}

	public void onClose(Socket socket, Thread thread) {
		System.out.println("与服务器断开连接。");
	}

	public void onConnected(Socket socket, Thread thread) {
		System.out.println("连接服务器成功！");
	}

	public void onDataArrived(byte[] data, Socket socket, Thread thread) {
		System.out.println("<<" + new String(data));
	}

	public void onError(Exception e, Socket socket, Thread thread) {
		System.out.println("与服务器连接异常，断开连接。");
	}

	class InputMonitor implements Runnable {
		String command = null;

		public void run() {

			while (true) {
				System.out.println(">>");
				command = getInput();
				try {
					sendMessage(command.getBytes());
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
/*
		public String getInput() {
			InputStream inputStream = System.in;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String inputString = reader.readLine();
				return inputString;
			} catch (Exception e) {
				return null;
			}
		}*/

	}

	@Override
	public byte[] reciveMessage(Socket socket, Thread thread) {
		BufferedInputStream reciver = null;
		ByteArrayOutputStream out = null;
		try {
			// 获得输入缓冲流
			reciver = new BufferedInputStream(socket.getInputStream());
			// 创建缓存文件
			out = new ByteArrayOutputStream();

			// 读取数据
			byte[] buffer = new byte[getBUFFER_SIZE() * 1024];// 缓存大小

			int amount = -1;

			if ((amount = reciver.read(buffer)) != -1) {
				out.write(buffer, 0, amount);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			errorHandle(e, socket, thread);
		}

		return out.toByteArray();
	}
	public static String getInput() {
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
