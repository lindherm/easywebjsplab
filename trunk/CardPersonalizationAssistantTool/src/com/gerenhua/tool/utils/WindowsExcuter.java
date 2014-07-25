package com.gerenhua.tool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.gerenhua.tool.log.Log;

public class WindowsExcuter {
	public final static Log log = new Log();
	public static Process p;
	public static ProcessBuilder pb;

	/**
	 * 
	 * @param dir
	 * @param windowCommand
	 * @throws Exception
	 */
	public static void excute(File dir, String windowCommand) throws Exception {
		List<String> cmdList = new ArrayList<String>();
		for (String cmd : windowCommand.split(" ")) {
			cmdList.add(cmd);
		}
		excute(dir, cmdList);
	}

	/**
	 * 
	 * @param dir
	 * @param cmdList
	 * @throws Exception
	 */
	public static void excute(File dir, List<String> cmdList) throws Exception {
		pb = new ProcessBuilder(cmdList);
		pb.directory(dir);

		p = pb.start();

		InputStream is = p.getInputStream();
		InputStream isErr = p.getErrorStream();
		out(is, isErr);
		
		p.waitFor();
		p.destroy();
	}

	/**
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public static void writeCommand(String cmd) throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		bw.write(cmd);
		bw.flush();

		InputStream is = p.getInputStream();
		InputStream isErr = p.getErrorStream();
		out(is, isErr);

		p.waitFor();
	}

	private static void printLog(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is,Charset.forName("GBK")));
		String line = null;
		while ((line = br.readLine()) != null) {
			log.debug(line);
		}
		is.close();
		br.close();
	}

	private static void out(InputStream in, InputStream err) throws IOException {
		printLog(in);
		printLog(err);
	}

	public static void main(String[] args) throws Exception {
		/*List<String> cmdList = new ArrayList<String>();
		cmdList.add("ipconfig");
		cmdList.add("/all");
		WindowsExcuter.excute(new File("."), cmdList);

		WindowsExcuter.excute(new File("."), "cmd /c ipconfig/all");*/
		List<String> cmdList = new ArrayList<String>();

		File file = new File("D:\\SLE二代母卡工具HexGhostV0.4");
		cmdList.add("cmd.exe");
		cmdList.add("/c");
		cmdList.add("start");
		cmdList.add("HexGhost.exe");
		
		WindowsExcuter.excute(file, cmdList);
	}
}
