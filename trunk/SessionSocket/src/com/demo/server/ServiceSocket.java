package com.demo.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.demo.utility.Debug;
import com.socketUtility.SessionSocket;

public class ServiceSocket extends SessionSocket {

	public ServiceSocket(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeConnected(Socket socket) {
		Debug.print("默认的最大线程数是：" + getMAX_THREAD());
		if (ServerListener.max_thread > 0)
			setMAX_THREAD(ServerListener.max_thread);
		Debug.print("当前最大线程数是：" + getMAX_THREAD());
		Debug.print("================================\n信息:连接之前。");
	}

	@Override
	public void beforeThreadStarted(Thread thread, Socket socket) {
		Debug.print("信息:线程启动之前。线程ID：" + thread.getId());
	}

	@Override
	public void onClose(Socket socket, Thread thread) {
		Debug.print("注意:连接断开。socketID:" + socket.hashCode());

	}

	@Override
	public void onConnected(Socket socket, Thread thread) {
		Debug.print("信息:连接成功。socketID:" + socket.hashCode());

	}

	@Override
	public void onDataArrived(byte[] data,Socket socket, Thread thread) {
		Debug.print("注意:有消息到达。socketID:" + socket.hashCode());
		try {
			BufferedInputStream reciver = new BufferedInputStream(socket.getInputStream());
			FileOutputStream outputStream=new FileOutputStream(new File("D:/data/mt1dddd.rar"),true);
			//outputStream.write(data);
			byte[] buffer = new byte[getBUFFER_SIZE() * 1024 * 2];// 缓存大小，1*1024*1024*2是1M
			int amount;
			int a=0;
			while ((amount = reciver.read(buffer)) != -1) {
				outputStream.write(buffer, 0, amount);
				a++;
				System.out.println(a+":"+amount);
				System.out.println(new String(buffer,0,amount));
			}
			// 关闭流
			reciver.close();
			outputStream.close();
			//connection.close();
		} catch (Exception e) {
			errorHandle(e, socket, thread);
		}

	}

	@Override
	public void onError(Exception e, Socket socket, Thread thread) {
		Debug.print("注意:连接异常。socketID:" + socket.hashCode());

	}

	@Override
	public void onMaxThread(Socket socket) {
		try {
			sendMessage("Max connections reached!".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Debug.info("注意:已经达到最大线程值。当前被拒绝的连接socketID：：" + socket.hashCode());
	}

	@Override
	public void onThreadExit(Thread thread, Socket socket) {
		Debug.print("信息:线程退出。线程ID：" + thread.getId());

	}

	@Override
	public void onThreadStarted(Thread thread, Socket socket) {
		Debug.print("信息:线程启动。线程ID：" + thread.getId());
	}

	public void sendMessageToAll(byte[] message) {
		ArrayList<SessionSocket> sessions = getSessions();
		for (int i = 0; i < sessions.size(); i++) {
			ServiceSocket session = (ServiceSocket) sessions.get(i);
			try {
				sendMessage(message, session.getSocket());
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}
}
