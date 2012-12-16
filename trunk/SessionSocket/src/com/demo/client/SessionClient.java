package com.demo.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SessionClient {
	public static ConcurrentHashMap<String, TcpConnector> session = new ConcurrentHashMap<String, TcpConnector>();

	public static ConcurrentHashMap<String, TcpConnector> getSession() {
		return session;
	}

	public SessionClient(String connectorName, String ip, int port) throws UnknownHostException, IOException {
		TcpConnector tcpConnector = new TcpConnector(ip, port);
		addConnector(connectorName, tcpConnector);
	}

	/**
	 * add a tcp client
	 * 
	 * @param connectorName
	 * @param tcpConnector
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void addConnector(String connectorName, TcpConnector tcpConnector) throws UnknownHostException, IOException {
		Socket socket = new Socket(tcpConnector.getIp(), tcpConnector.getPort());
		tcpConnector.setSocket(socket);
		session.putIfAbsent(connectorName, tcpConnector);
	}

	/**
	 * send data use a connect
	 * 
	 * @param data
	 * @param connectName
	 * @return
	 * @throws IOException
	 */
	public byte[] send(byte[] data, String connectName) throws IOException {
		if (session.containsKey(connectName)) {
			return sendData(data, session.get(connectName).getSocket());
		} else {
			throw new IOException();
		}
	}

	private byte[] sendData(byte[] data, Socket socket) throws IOException {
		OutputStream sender = socket.getOutputStream();
		sender.write(data);
		sender.flush();

		return reciveData(socket);
	}

	private byte[] reciveData(Socket socket) throws IOException {
		BufferedInputStream reciver = new BufferedInputStream(socket.getInputStream());
		byte[] buffer = new byte[5 * 1024 * 2];// 缓存大小，1*1024*1024*2是1M
		int len = reciver.read(buffer);
		if (len > 0) {
			return new String(buffer, 0, len).getBytes();
		}
		{
			throw new IOException();
		}
	}

	/**
	 * close all
	 * 
	 * @throws IOException
	 */
	public void closeAll() throws IOException {
		Iterator<Entry<String, TcpConnector>> iterator = session.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<String, TcpConnector> entry = (Entry<String, TcpConnector>) iterator.next();
			session.get(entry.getKey()).getSocket().close();
		}
	}
}
