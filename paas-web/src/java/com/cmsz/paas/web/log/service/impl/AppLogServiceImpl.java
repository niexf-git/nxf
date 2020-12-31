package com.cmsz.paas.web.log.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.FtpClientUtil;
import com.cmsz.paas.web.base.util.JasypUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.log.model.AppLogInfo;
import com.cmsz.paas.web.log.service.AppLogService;

/**
 * 应用日志服务类
 * 
 * @author li.lv 2015-4-20
 */
@Service("appLogService")
public class AppLogServiceImpl implements AppLogService {

	private static final Logger logger = LoggerFactory
			.getLogger(AppLogServiceImpl.class);
	/** 应用日志服务器ip */
	// private String ip = PropertiesUtil.getValue("logServerIp");
	/** 应用日志服务器端口 */
	private int port = Integer.parseInt(PropertiesUtil
			.getValue("logServerPort"));
	/** 应用服务器用户名 */
	private String user = PropertiesUtil.getValue("logServerUser");
	/** 应用日志服务器密码 */
	private String pwd = JasypUtil.decrypt(PropertiesUtil.getValue("logServerPWD"));
	/** 应用日志路径 */
	private String basePath = PropertiesUtil.getValue("logServerFileUrl");

	private FtpClientUtil ftpClientUtil;

	@Override
	public List<AppLogInfo> listAppLog(String path, String logServerIp)
			throws Exception {
		List<AppLogInfo> list = new ArrayList<AppLogInfo>();
		// String address = "";
		// try {
		// //根据别名返回【别名/ip】
		// address = InetAddress.getByName("flumeServer").toString();
		// address=address.split("/")[1];
		// ip = ip.replaceAll("flumeServer", address);
		// } catch (UnknownHostException e) {
		// logger.error("获取ip异常", e);
		// }
		try {
			ftpClientUtil = new FtpClientUtil();
			// ftpClientUtil.connectServer(ip, port, user, pwd, basePath);
			ftpClientUtil.connectServer(logServerIp, port, user, pwd, basePath);
			logger.info("连接应用日志服务器成功");
			list = ftpClientUtil.getFileList(path);
			logger.info("获取应用日志列表成功");
			return list;
		} catch (PaasWebException ex) {
			logger.error("获取应用日志文件列表异常", ex);
			throw new PaasWebException(Constants.QUERY_APP_LOG_ERROR,
					ex.getMessage());
		} finally {
			ftpClientUtil.closeServer();
		}
	}

	// @Override
	// public List<AppLogInfo> listAppLog(String path) throws Exception {
	// List<AppLogInfo> retList = new ArrayList<AppLogInfo>();
	// try {
	// File file=new File(path);//获取某个目录下的所有文件名
	// File [] f=file.listFiles();
	// SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd HH:mm:ss");
	// if (f!=null) {
	// for (int i = 0; i < f.length; i++) {
	// AppLogInfo info = new AppLogInfo();
	// info.setFileName(f[i].getName());
	// info.setFileTime(sdf.format(new java.util.Date(f[i].lastModified())));
	// info.setDownloadPath(path+"@"+f[i].getName());
	// info.setFilePath(path);
	// if (f[i].isDirectory()) {
	// info.setFileSize("");
	// info.setFileType("1");// 文件夹
	// }
	// if (f[i].isFile()) {
	// info.setFileSize(String.valueOf(f[i].length()/1024)+"kb");
	// info.setFileType("0");// 文件
	// }
	// retList.add(info);
	// }
	// }
	// ListSort<AppLogInfo> listSort= new ListSort<AppLogInfo>();
	// listSort.Sort(retList, "getFileTime", "desc");
	// logger.debug("获取应用日志文件列表成功");
	// return retList;
	// } catch (PaasWebException ex) {
	// logger.error("获取应用日志文件列表异常",ex);
	// throw new PaasWebException(Constants.QUERY_APP_LOG_ERROR,
	// ex.getMessage());
	// }
	// }

	@Override
	public InputStream downloadFile(String filePath, String fileName,
			String logServerIp) throws Exception {
		InputStream is = null;
		// String address = "";
		// try {
		// //根据别名返回【别名/ip】
		// address = InetAddress.getByName("flumeServer").toString();
		// address=address.split("/")[1];
		// ip = ip.replaceAll("flumeServer", address);
		// } catch (UnknownHostException e) {
		// logger.error("获取ip异常", e);
		// }
		try {
			ftpClientUtil = new FtpClientUtil();
			// ftpClientUtil.connectServer(ip, port, user, pwd, basePath);
			ftpClientUtil.connectServer(logServerIp, port, user, pwd, basePath);
			logger.debug("连接应用日志服务器成功");
			is = ftpClientUtil.downFile(filePath + "/" + fileName);
			logger.debug("下载应用日志成功");
			return is;
		} catch (PaasWebException ex) {
			logger.error("下载应用日志文件异常", ex);
			throw new PaasWebException(Constants.DOWNLOAD_APP_LOG_ERROR,
					ex.getMessage());
		} finally {
			ftpClientUtil.closeServer();
		}
	}

	/**
	 * 
	 删除FTP制定目录下的文件
	 * 
	 * @param filePath
	 *            文件在FTP存储的路径
	 * 
	 @param fileName
	 *            要删除的文件名称
	 * 
	 @return 是否删除成功
	 * @throws IOException
	 * @throws SocketException
	 */
	@Override
	public void deleteFile(String filePath, String fileName,
			String logServerIp) throws SocketException, IOException {
		try {
			ftpClientUtil = new FtpClientUtil();
			// ftpClientUtil.connectServer(ip, port, user, pwd, basePath);
			ftpClientUtil.connectServer(logServerIp, port, user, pwd, basePath);
			logger.debug("连接应用日志服务器成功");
			ftpClientUtil.changeDirectory(filePath);
			ftpClientUtil.deleteFile(fileName);
			logger.debug("删除应用日志成功");
		} catch (PaasWebException ex) {
			logger.error("删除应用日志文件异常", ex);
			throw new PaasWebException(Constants.DELETE_APP_LOG_ERROR,
					ex.getMessage());
		} finally {
			ftpClientUtil.closeServer();
		}
	}

	// public String getIp() {
	// return ip;
	// }
	//
	// public void setIp(String ip) {
	// this.ip = ip;
	// }

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
