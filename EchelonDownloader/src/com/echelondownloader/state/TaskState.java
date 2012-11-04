package com.echelondownloader.state;

import java.io.Serializable;

import javax.swing.ImageIcon;

import com.echelondownloader.object.Resource;

public interface TaskState extends Serializable {

	/**
	 * 返回该状态下的图片
	 * @return
	 */
	ImageIcon getIcon();
	
	/**
	 * 返回状态的字符串
	 * @return
	 */
	String getState();
	
	/**
	 * 该状态初始化执行的方法
	 */
	void init(Resource resource);
	
	/**
	 * 该状态结束时执行的方法
	 */
	void destory(Resource resouse);
}
