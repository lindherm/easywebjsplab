package com.echeloneditor.concurrentsocketserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class PooledConnectionHandler implements Runnable {
	protected Socket connection;
	protected static List<Socket> pool = new LinkedList<Socket>();

	public PooledConnectionHandler() {
	}

	public void handleConnection() {
		try {
			OutputStream out = connection.getOutputStream();
			InputStream in = connection.getInputStream();
			byte[] bytes = new byte[1024];

			if (in.read(bytes) > 0) {
				String receive = new String(bytes);
				int pos = receive.lastIndexOf('0');
				receive = receive.substring(0, pos);
				out.write(receive.getBytes());
				out.flush();
				System.out.println(Thread.currentThread().getName() + ":" + receive);
			}
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("");
		} catch (IOException e) {
			System.out.println("" + e);
		}
	}

	public static void processRequest(Socket requestToHandle) {
		synchronized (pool) {
			pool.add(pool.size(), requestToHandle);
			pool.notifyAll();
		}
	}

	public void run() {
		while (true) {
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				connection = (Socket) pool.remove(0);
			}
			handleConnection();
		}
	}
}
