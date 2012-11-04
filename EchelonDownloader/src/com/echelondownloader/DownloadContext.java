package com.echelondownloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.echelondownloader.navigation.DownloadNode;
import com.echelondownloader.navigation.DownloadingNode;
import com.echelondownloader.navigation.FailNode;
import com.echelondownloader.navigation.FinishNode;
import com.echelondownloader.object.Resource;
import com.echelondownloader.state.Connecting;
import com.echelondownloader.state.Downloading;
import com.echelondownloader.state.Failed;
import com.echelondownloader.state.Finished;
import com.echelondownloader.state.Pause;
import com.echelondownloader.state.TaskState;

public class DownloadContext implements Serializable {

	//最大线程数
	public static final int MAX_THREAD_COUNT = 5;
	
	//下载任务状态的各个实现类
	public static Connecting CONNECTION = new Connecting();
	public static Downloading DOWNLOADING = new Downloading();
	public static Failed FAILED = new Failed();
	public static Finished FINISHED = new Finished();
	public static Pause PAUSE = new Pause();
	
	//当前下载程序中所有的任务
	public List<Resource> resources = new ArrayList<Resource>();
	
	public List<Resource> getFaileds() {
		return getResources(FAILED);
	}
	
	public List<Resource> getDownloadings() {
		return getResources(DOWNLOADING);
	}
	
	public List<Resource> getFinisheds() {
		return getResources(FINISHED);
	}
	
	private List<Resource> getResources(TaskState state) {
		List<Resource> result = new ArrayList<Resource>();
		for (Resource r : resources) {
			if (state.getState().equals(r.getState().getState())) {
				result.add(r);
			}
		}
		return result;
	}
	
	/**
	 * 根据ID去当前的资源集合中查找相应的资源
	 * @param id
	 * @return
	 */
	public Resource getResource(String id) {
		for (Resource r : this.resources) {
			if (r.getId().equals(id)) return r;
		}
		return null;
	}
	
	public List<Resource> getResources(DownloadNode currentNode) {
		if (currentNode instanceof FinishNode) {
			return getFinisheds();
		} else if (currentNode instanceof FailNode) {
			return getFaileds();
		} else if (currentNode instanceof DownloadingNode) {
			return getDownloadings();
		} else {
			return resources;
		}
	}
}
