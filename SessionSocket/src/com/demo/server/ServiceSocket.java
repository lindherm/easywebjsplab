package com.demo.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sessionsocket.SessionSocket;

public class ServiceSocket extends SessionSocket {
	private static Logger log = Logger.getLogger(ServiceSocket.class);

	public ServiceSocket(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeConnected(Socket socket) {
		log.debug("======================================>>");
		log.debug("默认的最大线程数是：" + getMAX_THREAD());
		if (ServerListener.max_thread > 0)
			setMAX_THREAD(ServerListener.max_thread);
		log.debug("当前最大线程数是：" + getMAX_THREAD());
		
	}

	@Override
	public void beforeThreadStarted(Thread thread, Socket socket) {
		log.debug("信息:线程启动之前。线程ID：" + thread.getId());
	}

	@Override
	public void onClose(Socket socket, Thread thread) {
		log.debug("注意:连接断开。socketID:" + socket.hashCode()+"["+socket.toString()+"]");
	}

	@Override
	public void onConnected(Socket socket, Thread thread) {
		log.debug("信息:连接成功。socketID:" + socket.hashCode()+"["+socket.toString()+"]");
	}

	@Override
	public void onDataArrived(byte[] data, Socket socket, Thread thread) {
		log.debug("注意:有消息到达:socketID:" + socket.hashCode()+"["+socket.toString()+"]【接收：" + data.length + "字节数据】");
	}

	@Override
	public void onError(Exception e, Socket socket, Thread thread) {
		if (!e.getMessage().equals("Connection reset")) {
			e.printStackTrace();
		}
		log.debug("注意:连接异常["+e.getMessage()+"]。socketID:" + socket.hashCode()+"["+socket.toString()+"]");
	}

	@Override
	public void onMaxThread(Socket socket) {
		try {
			sendMessage("Max connections reached!".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("注意:已经达到最大线程值。当前被拒绝的连接socketID：：" + socket.hashCode()+"["+socket.toString()+"]");
	}

	@Override
	public void onThreadExit(Thread thread, Socket socket) {
		log.debug("信息:线程退出。线程ID：" + thread.getId());
	}

	@Override
	public void onThreadStarted(Thread thread, Socket socket) {
		log.debug("信息:线程启动。线程ID：" + thread.getId());
	}

	public void sendMessageToAll(byte[] message) {
		ArrayList<SessionSocket> sessions = getSessions();
		for (int i = 0; i < sessions.size(); i++) {
			ServiceSocket session = (ServiceSocket) sessions.get(i);
			try {
				sendMessage(message, session.getSocket());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte[] reciveMessage(Socket socket) throws IOException {
		// 获得输入缓冲流
		BufferedInputStream reciver = new BufferedInputStream(socket.getInputStream());
		// 创建缓存文件
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// 读取数据
		byte[] buffer = new byte[getBUFFER_SIZE()];// 缓存大小
		byte[] datalength = new byte[8];
		reciver.read(datalength);
		long dataL = Long.parseLong(new String(datalength), 10);

		int amount = -1;
		int fileLen = 0;

		while (fileLen < dataL) {
			if ((amount = reciver.read(buffer)) != -1) {
				out.write(buffer, 0, amount);
				out.flush();
				fileLen += amount;
			}
		}
		
		sendMessage("done".getBytes(), socket);
		out.close();
		return out.toByteArray();
	}
}
