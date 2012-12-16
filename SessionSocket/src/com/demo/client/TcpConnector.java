package com.demo.client;

import java.net.Socket;

public class TcpConnector {
	public String ip;
	public int port;
	public Socket socket;

	public TcpConnector(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
