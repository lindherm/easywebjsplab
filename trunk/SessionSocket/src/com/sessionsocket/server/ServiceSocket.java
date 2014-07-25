package com.sessionsocket.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sessionsocket.SessionSocket;
import com.watchdata.commons.lang.WDByteUtil;

public class ServiceSocket extends SessionSocket {
	private static Logger log = Logger.getLogger(ServiceSocket.class);

	public ServiceSocket(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beforeConnected(Socket socket) {
		log.debug("=============================================");
		log.debug("默认的最大线程数是：" + getMAX_THREAD());
		if (ServerListener.max_thread > 0)
			setMAX_THREAD(ServerListener.max_thread);
		setBUFFER_SIZE(10);// 10*1024
		log.debug("当前最大线程数是：" + getMAX_THREAD());

	}

	@Override
	public void beforeThreadStarted(Thread thread, Socket socket) {
		log.debug("信息:线程启动之前。线程ID：" + thread.getId());
	}

	@Override
	public void onClose(Socket socket, Thread thread) {
		log.debug("注意:连接断开。socketID:" + socket.hashCode() + "[" + socket.toString() + "]");
	}

	@Override
	public void onConnected(Socket socket, Thread thread) {
		log.debug("信息:连接成功。socketID:" + socket.hashCode() + "[" + socket.toString() + "]");
	}

	@Override
	public void onDataArrived(byte[] data, Socket socket, Thread thread) {
		if (data != null && data.length > 0) {

			FileOutputStream out = null;
			try {
				out = new FileOutputStream("d:/test/hello.rar");
				out.write(data);
				out.flush();

				log.debug("注意:有消息到达:socketID:" + socket.hashCode() + "[" + socket.toString() + "]【接收：" + data.length + "字节数据】");
				sendMessage("success.\n".getBytes());

				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorHandle(e, socket, thread);
			}
		}
	}

	@Override
	public void onError(Exception e, Socket socket, Thread thread) {
		log.error("注意:连接异常[" + e.getMessage() + "|" + e.getStackTrace()[0] + "]socketID:" + socket.hashCode() + "[" + socket.toString() + "]");
	}

	@Override
	public void onMaxThread(Socket socket) {
		try {
			sendMessage("Max connections reached!".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("注意:已经达到最大线程值。当前被拒绝的连接socketID：：" + socket.hashCode() + "[" + socket.toString() + "]");
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
	public byte[] reciveMessage(Socket socket, Thread thread) {
		BufferedInputStream reciver = null;
		ByteArrayOutputStream out = null;
		try {
			// 获得输入缓冲流
			reciver = new BufferedInputStream(socket.getInputStream());
			// 创建缓存文件
			out = new ByteArrayOutputStream();

			// 读取数据
			byte[] msgHeader = new byte[4];// 缓存大小
			byte[] buffer = new byte[getBUFFER_SIZE() * 1024];// 缓存大小

			int amount = reciver.read(msgHeader);
			long headLen = Long.parseLong(WDByteUtil.bytes2HEX(msgHeader), 16);

			long pos = 0;
			while (pos < headLen) {
				amount = reciver.read(buffer);
				out.write(buffer, 0, amount);
				pos += amount;
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			errorHandle(e, socket, thread);
		}
		return out.toByteArray();
	}
}
