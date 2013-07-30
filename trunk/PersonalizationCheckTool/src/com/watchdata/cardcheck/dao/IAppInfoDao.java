package com.watchdata.cardcheck.dao;

import java.util.List;

import com.watchdata.cardcheck.dao.pojo.AppInfo;

public interface IAppInfoDao {
	public List<AppInfo> findAppInfo();
	public String getAppId();
	public boolean insertAppinfo(AppInfo appinfo);
	public AppInfo findAppinfo(String appid);
	public boolean deleteAppinfo();
	public boolean updateAppinfo(AppInfo appinfo);		
	public boolean batchDel(List<String> delDatas);
	public boolean batchSaveUpdate(final List<AppInfo> importDatas);
}
