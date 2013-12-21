package com.demo.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sessionsocket.SessionSocket;

public class ClientSocket extends SessionSocket {

	public ClientSocket(Socket socket) {
		super(socket);
	}

	public static void main(String args[]) {
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", 3002);
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
	}

	public void onError(Exception e, Socket socket, Thread thread) {
		System.out.println("与服务器连接异常，断开连接。");
	}

	class InputMonitor implements Runnable {
		String nickName = null;

		public void run() {
			while (true) {
				System.out.println("请输入报文>>:");
				nickName = getInput();
				try {
					if (nickName != "") {
						sendMessage(nickName.getBytes());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		public String getInput() {
			InputStream inputStream = System.in;
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			try {
				String inputString = reader.readLine();
				return inputString;
			} catch (Exception e) {
				return null;
			}
		}

	}

	@Override
	/**
	 * @Description 从指定的socket中读取一次数据
	 * @param socket
	 *            : Socket
	 * @throws IOException
	 *             抛出IO异常,说明网络异常
	 * @return 返回类型 String,即接收到的数据
	 */
	public byte[] reciveMessage(Socket socket) throws IOException {
		BufferedInputStream reciver = new BufferedInputStream(
				socket.getInputStream());
		byte[] buffer = new byte[getBUFFER_SIZE() * 1024 * 2];// 缓存大小，1*1024*1024*2是1M
		int len = reciver.read(buffer);
		if (len > 0) {
			return new String(buffer, 0, len).getBytes();
		}
		{
			throw new IOException();
		}
	}
}
