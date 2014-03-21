
package com.sessionsocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sessionsocket.SessionSocket;


public class ClientSocket extends SessionSocket{


	public ClientSocket(Socket socket) {
		super(socket);
	}
    public static void main(String args[]) {
    	Socket socket;
		try {
			socket = new Socket("10.0.97.120",9000);
			ClientSocket clientSocket=new ClientSocket(socket);
			//启动ClientSocket线程
			clientSocket.start();
			Thread iMonitor=new Thread(clientSocket.new InputMonitor());
			iMonitor.setDaemon(true);
			//启动输入
			iMonitor.start();
			
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void onClose(Socket socket, Thread thread) {
		System.out.println("与服务器断开连接。");
	}
	public void onConnected(Socket socket, Thread thread) {
		System.out.println("连接服务器成功！");
	}
	public void onDataArrived(byte[] data, Socket socket, Thread thread) {
		if (data!=null) {
			System.out.println(new String(data));
		}
	}
	public void onError(Exception e, Socket socket, Thread thread) {
		System.out.println("与服务器连接异常，断开连接。");
	}
	class InputMonitor implements Runnable{
		String command=null;
		public void run() {
			
		    while (true) {
		    	System.out.println(">");
		    	command=getInput();
		    	try {
					sendMessage(command.getBytes());
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
		public String getInput(){
			InputStream inputStream=System.in;
			 BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
			try {
				String inputString=reader.readLine();
				return inputString;
			} catch (Exception e) {
				return null;
			}
		}
		
	}
	@Override
	public byte[] reciveMessage(Socket socket, Thread thread) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
