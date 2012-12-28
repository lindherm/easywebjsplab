package com.demo.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerListener {
	private static boolean IS_STOP = false;
	private ServerSocket listener;
	public static int max_thread = 1000;

	public static boolean isIS_STOP() {
		return IS_STOP;
	}

	public static void main(String args[]) {
		new ServerListener().startService(9000);
	}

	public void startService(int port) {
		try {
			IS_STOP = false;
			listener = new ServerSocket(port);
			System.out.println("Service started on port "+port+"...");
			while (!IS_STOP && !listener.isClosed()) {
				new ServiceSocket(listener.accept()).start();
			}
			System.err.println("Service stopped.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
