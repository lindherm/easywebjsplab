package yuki;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * 该类用于处理项目资源的工具类
 * <p>
 * 要注意的是：项目资源必须要放到工程目录src下，也可以应用项目外部资源需指明绝对路径
 */
public class FileUtil {
	//项目文件必须位于src目录下的下列3个子文件夹之一
	private static final String FILE = "resource/file/";//存放普通文件
	private static final String IMAGE = "resource/image/";//存放图片文件
	private static final String AUDIO = "resource/audio/";//存放音频文件
	private static final HashMap<String, String> MAP = new HashMap<String, String>();
	static{
		MAP.put("file", FILE);
		MAP.put("image", IMAGE);
		MAP.put("audio", AUDIO);
	}
	
	/**
	 * 返回资源文件的URL地址
	 * @param isInner 是否为项目内部资源文件
	 * @param type 资源文件类型,包括文件、图片和音频
	 * @param path 资源文件路径 外部文件时要用绝对路径 (如 C:/me.jpg) 如果是内部文件则是文件名称(如：me.jpg)
	 * @return
	 */
	private static URL getURL(boolean isInner,String type,String path){
    	String dir = MAP.get(type);
    	if(!isInner){
    		try {
				return new URL("file:///" + path);
			} catch (Exception e) {
				System.out.println("操作失败：获取资源文件失败！");
				return null;
			}
    	}
    	return URLClassLoader.getSystemClassLoader().getResource(dir+path);
    }
    
    //获取文件资源
    public static File getFile(boolean isInner,String path){
    	URL url = getURL(isInner,"file",path);
    	if(url == null) return null;   	
    	return new File(url.getFile());
    }
    
    //获取图片资源
    public static Image getImage(boolean isInner,String path){
    	URL url = getURL(isInner,"image",path);
    	if(url == null) return null;
    	return Toolkit.getDefaultToolkit().getImage(url);
    }
    
    //获取音频资源
    public static AudioClip getAudio(boolean isInner,String path){
    	URL url = getURL(isInner,"audio",path);
    	if(url == null) return null;
    	return Applet.newAudioClip(url);
    }
    
}
