package com.echelondownloader.state;

import java.io.Serializable;

import javax.swing.ImageIcon;

import com.echelondownloader.object.Resource;

public interface TaskState extends Serializable {

	/**
	 * ���ظ�״̬�µ�ͼƬ
	 * @return
	 */
	ImageIcon getIcon();
	
	/**
	 * ����״̬���ַ���
	 * @return
	 */
	String getState();
	
	/**
	 * ��״̬��ʼ��ִ�еķ���
	 */
	void init(Resource resource);
	
	/**
	 * ��״̬����ʱִ�еķ���
	 */
	void destory(Resource resouse);
}
