package com.echeloneditor.concurrentsocketserver;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class PooledRemoteFileServer {
	protected int maxConnections;
	protected int listenPort;
	protected ServerSocket serverSocket;

	public PooledRemoteFileServer(int aListenPort, int maxConnections) {
		listenPort = aListenPort;
		this.maxConnections = maxConnections;
	}

	public void acceptConnections() {
		try {
			ServerSocket server = new ServerSocket(listenPort);
			System.out.println("listen on "+listenPort+"...");
			Socket incomingConnection = null;
			while (true) {
				incomingConnection = server.accept();
				incomingConnection.setKeepAlive(true);
				incomingConnection.setSoTimeout(0);
				handleConnection(incomingConnection);
			}
		} catch (BindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void handleConnection(Socket connectionToHandle) {
		PooledConnectionHandler.processRequest(connectionToHandle);
	}

	public void setUpHandlers() {
		for (int i = 0; i < maxConnections; i++) {
			PooledConnectionHandler currentHandler = new PooledConnectionHandler();
			new Thread(currentHandler, "Handler " + i).start();
		}
	}

	public static void main(String args[]) {
		PooledRemoteFileServer server = new PooledRemoteFileServer(8, 1);
		server.setUpHandlers();
		server.acceptConnections();
	}
}
