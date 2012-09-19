package com.echeloneditor.concurrentsocketserver;

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

			while (in.read(bytes) > 0) {
				String receive = new String(bytes);
				int pos = receive.lastIndexOf('0');
				receive = receive.substring(0, pos);
				String msg = "12345678510000303033383632313436323239333430303030303034353444323131313232303136353030303030313046303030303030";
				out.write(msg.getBytes());
				out.flush();
				System.out.println(Thread.currentThread().getName() + ":" + receive);
			}
		/*	out.close();
			in.close();*/

			/*
			 * PrintWriter streamWriter = new PrintWriter(connection.getOutputStream()); BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //String fileToRead = streamReader.readLine(); //BufferedReader fileReader = new BufferedReader(new FileReader(fileToRead)); String line = null; while (true) { if ((line = streamReader.readLine()) != null){ streamWriter.println(line); streamWriter.flush(); } }
			 * 
			 * //fileReader.close(); //streamWriter.close(); //streamReader.close();
			 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
