package com.cmsz.paas.web.log.service;

import java.io.InputStream;
import java.util.List;

import com.cmsz.paas.web.log.model.AppLogInfo;

/**
 * 应用日志服务接口
 * @author li.lv
 * 2015-4-20	
 */
public interface AppLogService {
	/**
	 * 获取应用日志文件列表
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<AppLogInfo> listAppLog(String path, String logServerIp) throws Exception;
	/**
	 * 下载应用日志文件
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public InputStream downloadFile(String filePath,String fileName, String logServerIp) throws Exception;
	
	/**
	 * 删除应用日志文件
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public void deleteFile(String filePath,String fileName, String logServerIp) throws Exception;

}
