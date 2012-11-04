package com.echelondownloader.state;

import java.util.Timer;

import javax.swing.ImageIcon;


import com.echelondownloader.ContextHolder;
import com.echelondownloader.object.Resource;
import com.echelondownloader.util.FileUtil;
import com.echelondownloader.util.ImageUtil;

public class Finished extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.FINISHED_IMAGE;
	}

	public String getState() {
		return "finished";
	}

	public void init(Resource resource) {
		//ɾ����ʱ�ļ�
		FileUtil.deletePartFiles(resource);
		//��Դ������ɺ�ȡ������
		ContextHolder.dh.stopTimer(resource);
	}
	
	
}
