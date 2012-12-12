/**
 * @Title Server.java
 * @Package com.test
 * @Description 用一句话描述该文件做什么
 * @author 狂奔的蜗牛 672308444@163.com  
 * @date 2011-9-13 下午09:23:11
 * @version V1.0  
*/
package com.demo.server;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * @ClassName Server
 * @Description 服务器监听类
 * @author 狂奔的蜗牛 672308444@163.com  
 * @date 2011-9-13 下午09:23:11
 *
 */
public class ServerListener{
	   private static boolean IS_STOP=false;
	   private ServerSocket listener;
	   public static int max_thread=1000;
	   /**
	    * @Description 获取服务状态
	    * @return 返回类型 boolean true服务正在运行，false服务已经停止
	    */
	   public static boolean isIS_STOP() {
			return IS_STOP;
		}
       public static void main(String args[]){
    	   //if(args.length>1){max_thread=Integer.valueOf(args[1]);}
    	   new ServerListener().startService(3002);
       }
       
       public void startService(int port){
		 try {
			 IS_STOP=false;
		     listener=new ServerSocket(port);
		     System.err.println("Service started.");
    	     while (!IS_STOP&&!listener.isClosed()) {
			 new ServiceSocket(listener.accept()).start();
    	     }
    	     System.err.println("Service stopped.");
		   } catch (IOException e) {
			e.printStackTrace();
	      }
       }
}
