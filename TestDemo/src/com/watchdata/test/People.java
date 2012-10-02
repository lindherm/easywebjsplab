package com.watchdata.test;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class People {
	public String leg;
	public String nose;
	public String hand;
	
	public void walk(){
		System.out.println("walking...");
	}
	
	public static void main(String[] args) {
		String fileName="Wildlife.txt";
		 String str="http://easywebjsplab.googlecode.com/svn/trunk/test/"+fileName;
	        //String filePath="D:\\Wildlife.wmv";
	        
	        try {
	            URL url=new URL(str);
	            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");
	            connection.addRequestProperty("FileName", fileName);
	            connection.setRequestProperty("content-type", "text/html");
	            BufferedOutputStream  out=new BufferedOutputStream(connection.getOutputStream());
	            
	            String test="hello";
	            //读取文件上传到办事器
	            //File file=new File(filePath);
	            //FileInputStream fileInputStream=new FileInputStream(file);
	            //byte[]bytes=new byte[1024];
	            //while(fileInputStream.read(bytes,0,1024)>0)
	            //{
	                out.write(test.getBytes(), 0, test.getBytes().length);
	            //}
	            out.flush();
	            //fileInputStream.close();
	            //读取URLConnection的响应
	            //DataInputStream in=new DataInputStream(connection.getInputStream());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

		
	} 
	
}

