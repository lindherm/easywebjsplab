package com.sessionsocket.server;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

public class ServerListener {
	private static Logger log = Logger.getLogger(ServerListener.class);
	private static boolean IS_STOP = false;
	private ServerSocket listener;
	public static int max_thread = 0;

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
			log.info("Service started on port " + port + "...");
			while (!IS_STOP && !listener.isClosed()) {
				new ServiceSocket(listener.accept()).start();
			}
			log.error("Service stopped.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
