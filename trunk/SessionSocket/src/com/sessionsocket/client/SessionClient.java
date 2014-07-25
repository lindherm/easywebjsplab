package com.sessionsocket.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SessionClient {
	private static ConcurrentHashMap<String, TcpConnector> session = new ConcurrentHashMap<String, TcpConnector>();

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
		socket.setSoTimeout(0);
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
	public void send(byte[] data, String connectName) throws IOException {
		if (session.containsKey(connectName)) {
			sendData(data, session.get(connectName).getSocket());
		} else {
			throw new IOException("connect is not exists.");
		}
	}

	private void sendData(byte[] data, Socket socket) throws IOException {
		OutputStream sender = socket.getOutputStream();
		sender.write(data);
		sender.flush();
	}

	/**
	 * receive a data
	 * 
	 * @param connectName
	 * @return
	 * @throws IOException
	 */
	public String recive(String connectName) throws IOException {
		if (session.containsKey(connectName)) {
			BufferedReader receiver = new BufferedReader(new InputStreamReader(session.get(connectName).getSocket().getInputStream()));
			return receiver.readLine();
		} else {
			throw new IOException("connect is not exists.");
		}
	}

	public byte[] receive(String connectName, int size) throws IOException {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		if (session.containsKey(connectName)) {
			BufferedInputStream receiver = new BufferedInputStream(session.get(connectName).getSocket().getInputStream());
			byte[] buffer = new byte[1024];// 缓存大小

			int amount = -1;
			int pos = 0;
			while (pos < size) {
				if ((amount = receiver.read(buffer)) > 0) {
					out.write(buffer, 0, amount);
					out.flush();
				}
				pos += amount;
			}
			return out.toByteArray();
		} else {
			throw new IOException("connect is not exists.");
		}

	}

	public void Close(String connectorName) throws IOException {
		synchronized (session) {
			session.get(connectorName).getSocket().close();
			session.remove(connectorName);
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
