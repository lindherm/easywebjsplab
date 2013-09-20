package com.gp.gpscript.engine;

public class IPAndPortDataPrepareUtil {

	private static String ip;
	private static int port;
	private static String kmsId;
	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		IPAndPortDataPrepareUtil.ip = ip;
	}
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		IPAndPortDataPrepareUtil.port = port;
	}
	public static String getKmsId() {
		return kmsId;
	}
	public static void setKmsId(String kmsId) {
		IPAndPortDataPrepareUtil.kmsId = kmsId;
	}
	
}
