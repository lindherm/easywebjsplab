package com.echelondownloader;

import com.echelondownloader.thread.DownloadHandler;

public class ContextHolder {
	//下载工具上下文
	public static DownloadContext ctx = new DownloadContext();
	//下载处理类
	public static DownloadHandler dh = new DownloadHandler();
}
