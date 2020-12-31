package com.cmsz.paas.web.log.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.appservice.model.AppService;
import com.cmsz.paas.web.appservice.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.DateUtil;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.ipaasservice.model.IpaasService;
import com.cmsz.paas.web.ipaasservice.service.IpaasServiceService;
import com.cmsz.paas.web.log.model.AppLogInfo;
import com.cmsz.paas.web.log.service.AppLogService;

/**
 * 应用日志管理类
 * 
 * @author li.lv 2015-4-20
 */
public class AppLogManageAction extends AbstractAction {

	private static final long serialVersionUID = -8683113477644954859L;

	private static final Logger logger = LoggerFactory
			.getLogger(AppLogManageAction.class);

	@Autowired
	private AppLogService appLogService;

	/** The application service. */
	@Autowired
	private ApplicationService appServiceService;
	/** The application service. */
	@Autowired
	private IpaasServiceService ipaasServiceService;
	/** 输出文件流 */
	private InputStream file;
	/** 应用ID */
	private String appId;
	/** 服务ID */
	private String serviceId;
	/** 基础服务ID */
	private String ipaasServiceId;
	/** 下载路径 */
	private String downloadPath;
	/** 删除路径 */
	private String deletePath;
	/** 文件名称 */
	private String fileName;
	/** 子目录路径 */
	private String subPath;
	/** 子目录路径 */
	private String filePath;
	/** 名称 */
	private String name;
	/** 起始时间 */
	private String sdate;
	/** 结束时间 */
	private String edate;
	/** 应用日志路径 */
	private String basePath = PropertiesUtil.getValue("logServerFileUrl");

	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 获取应用日志文件夹下的文件列表.
	 * 
	 * @throws Exception
	 */
	public void queryAppLog() throws Exception {
		try {
			// if ((null == sdate || "".equals(sdate)) && null == edate
			// || "".equals(edate)) {
			// Date date = new Date();
			// edate = com.cmsz.paas.common.utils.DateUtil.tranformDate(date
			// .toString());
			// Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(
			// date, -3);
			// sdate = com.cmsz.paas.common.utils.DateUtil
			// .tranformDate(preDate.toString());
			// }
			String path = "";
			logger.info("获取应用日志文件列表，serviceId：" + serviceId);
			@SuppressWarnings("unchecked")
			Page<AppLogInfo> page = this.getJqGridPage("fileTime");
			AppService appService = appServiceService
					.queryAppServiceById(serviceId);
			if (null != subPath && !"".equals(subPath)) {
				path = subPath.replaceAll("%2F", "/");
			} else if (null != serviceId && !"".equals(serviceId)) {
				// path = basePath + appInfo.getName();
				path = genLogPath(appService);
				getSessionMap().put("LogPath", path);
			} else {
				path = filePath;
			}
			List<AppLogInfo> list = appLogService.listAppLog(path, appService.getLogServerIp());
			
			getSessionMap().put("logServerIp", appService.getLogServerIp());
			
			// 根据应用名称获取应用文件夹下的日志文件列表
			List<AppLogInfo> appLogList = new ArrayList<AppLogInfo>();
			if (null != subPath
					&& !"".equals(subPath)
					&& !getSessionMap().get("LogPath").equals(
							subPath.replaceAll("%2F", "/"))) {
				AppLogInfo info = new AppLogInfo();
				info.setFileName("...");
				info.setFileType("2");
				info.setFilePath(subPath.replaceAll("%2F", "/"));
				appLogList.add(info);
			}
			List<AppLogInfo> templist = new ArrayList<AppLogInfo>();
			if (null != name && !"".equals(name)) {
				name = name.replace("^", "\\^").replace("$", "\\$")
						.replace("*", "\\*").replace("(", "\\(")
						.replace(")", "\\)").replace("+", "\\+")
						.replace("?", "\\?").replace("[", "\\[")
						.replace("{", "\\{").replace(".", "\\.");
				Pattern pattern = Pattern.compile(name.trim());
				for (AppLogInfo app : list) {
					Matcher matcher = pattern.matcher(app.getFileName());
					if (matcher.find()) {
						templist.add(app);
					}
				}
				list = templist;
				templist = new ArrayList<AppLogInfo>();
			}
			if (null != sdate && !"".equals(sdate) && null != edate
					&& !"".equals(edate)) {
				for (AppLogInfo app : list) {
					if (DateUtil.toDate(sdate).before(
							DateUtil.toDate(app.getFileTime()))
							&& DateUtil.toDate(edate).after(
									DateUtil.toDate(app.getFileTime()))) {
						templist.add(app);
					}
				}
				list = templist;
			}
			for (AppLogInfo app : list) {
				if ("1".equals(app.getFileType())) {
					appLogList.add(app);
				}
			}
			for (AppLogInfo app : list) {
				if ("0".equals(app.getFileType())) {
					appLogList.add(app);
				}
			}

			page.setResult(appLogList);
			page.setTotalCount(appLogList.size());
			logger.info("获取应用日志文件列表成功，查询结果：" + JackJson.fromObjectToJson(page));
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException ex) {
			logger.error("获取应用日志文件列表异常", ex);
			// this.sendFailResult(ex.getKey(),ex.toString());
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 获取应用日志文件夹下的文件列表.
	 * 
	 * @throws Exception
	 */
	public void queryIpaasLog() throws Exception {
		try {
			// if ((null == sdate || "".equals(sdate)) && null == edate
			// || "".equals(edate)) {
			// Date date = new Date();
			// edate = com.cmsz.paas.common.utils.DateUtil.tranformDate(date
			// .toString());
			// Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(
			// date, -3);
			// sdate = com.cmsz.paas.common.utils.DateUtil
			// .tranformDate(preDate.toString());
			// }
			String path = "";
			logger.info("获取应用日志文件列表，serviceId：" + serviceId);
			@SuppressWarnings("unchecked")
			Page<AppLogInfo> page = this.getJqGridPage("fileTime");
			IpaasService ipaasService = ipaasServiceService
					.queryIpaasServiceById(serviceId);
			if (null != subPath && !"".equals(subPath)) {
				path = subPath.replaceAll("%2F", "/");
			} else if (null != serviceId && !"".equals(serviceId)) {
				// path = basePath + appInfo.getName();
				path = genIpaasLogPath(ipaasService);
				getSessionMap().put("LogPath", path);
			} else {
				path = filePath;
			}
			// 根据应用名称获取应用文件夹下的日志文件列表
			List<AppLogInfo> list = appLogService.listAppLog(path, ipaasService.getLogServerIp());
			List<AppLogInfo> appLogList = new ArrayList<AppLogInfo>();
			if (null != subPath
					&& !"".equals(subPath)
					&& !getSessionMap().get("LogPath").equals(
							subPath.replaceAll("%2F", "/"))) {
				AppLogInfo info = new AppLogInfo();
				info.setFileName("...");
				info.setFileType("2");
				info.setFilePath(subPath.replaceAll("%2F", "/"));
				appLogList.add(info);
			}
			List<AppLogInfo> templist = new ArrayList<AppLogInfo>();
			if (null != name && !"".equals(name)) {
				name = name.replace("^", "\\^").replace("$", "\\$")
						.replace("*", "\\*").replace("(", "\\(")
						.replace(")", "\\)").replace("+", "\\+")
						.replace("?", "\\?").replace("[", "\\[")
						.replace("{", "\\{").replace(".", "\\.");
				Pattern pattern = Pattern.compile(name.trim());
				for (AppLogInfo app : list) {
					Matcher matcher = pattern.matcher(app.getFileName());
					if (matcher.find()) {
						templist.add(app);
					}
				}
				list = templist;
				templist = new ArrayList<AppLogInfo>();
			}
			if (null != sdate && !"".equals(sdate) && null != edate
					&& !"".equals(edate)) {
				for (AppLogInfo app : list) {
					if (DateUtil.toDate(sdate).before(
							DateUtil.toDate(app.getFileTime()))
							&& DateUtil.toDate(edate).after(
									DateUtil.toDate(app.getFileTime()))) {
						templist.add(app);
					}
				}
				list = templist;
			}
			for (AppLogInfo app : list) {
				if ("1".equals(app.getFileType())) {
					appLogList.add(app);
				}
			}
			for (AppLogInfo app : list) {
				if ("0".equals(app.getFileType())) {
					appLogList.add(app);
				}
			}

			page.setResult(appLogList);
			page.setTotalCount(appLogList.size());
			logger.info("获取应用日志文件列表成功，查询结果：" + JackJson.fromObjectToJson(page));
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException ex) {
			logger.error("获取应用日志文件列表异常", ex);
			// this.sendFailResult(ex.getKey(),ex.toString());
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}

	public void queryLogPath() throws Exception {
		try {
			String path = "";
			if (null != serviceId && !"".equals(serviceId)) {
				// 根据应用id查询应用名称
				AppService appService = appServiceService
						.queryAppServiceById(serviceId);

				path = genLogPath(appService);
				// path = basePath +
				// appInfo.getClusterLabel()+"-"+appInfo.getAppGroupName()+"-"+appInfo.getName();
			}
			if (null != subPath && !"".equals(subPath)) {
				path = subPath.replaceAll("%2F", "/");
			}
			sendSuccessReslult(JackJson.fromObjectToJson(path));
		} catch (PaasWebException ex) {
			logger.error("查询日志路径异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	public void queryIpaasLogPath() throws Exception {
		try {
			String path = "";
			if (null != serviceId && !"".equals(serviceId)) {
				// 根据应用id查询应用名称
				IpaasService ipaasService = ipaasServiceService
						.queryIpaasServiceById(serviceId);

				path = genIpaasLogPath(ipaasService);
				// path = basePath +
				// appInfo.getClusterLabel()+"-"+appInfo.getAppGroupName()+"-"+appInfo.getName();
			}
			if (null != subPath && !"".equals(subPath)) {
				path = subPath.replaceAll("%2F", "/");
			}
			sendSuccessReslult(JackJson.fromObjectToJson(path));
		} catch (PaasWebException ex) {
			logger.error("查询日志路径异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	private String genLogPath(AppService appService) {
		String type = "";
		if ("1".equals(appService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.DEV.toString().toLowerCase();
		} else if ("3".equals(appService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.OPRT.toString().toLowerCase();
		} else if ("2".equals(appService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.TEST.toString().toLowerCase();
		} else {
			type = appService.getOper_type();
		}
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(basePath);
		strBuf.append(appService.getApp_name());
		strBuf.append("/");
		strBuf.append(type);
		strBuf.append("/");
		strBuf.append(Constant.K8S_SERVICE_TYPE.APPSERVICE.toString()
				.toLowerCase());
		strBuf.append("/");
		strBuf.append(appService.getName());

		return strBuf.toString();
	}

	private String genIpaasLogPath(IpaasService ipaasService) {
		String type = "";
		if ("1".equals(ipaasService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.DEV.toString().toLowerCase();
		} else if ("3".equals(ipaasService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.OPRT.toString().toLowerCase();
		} else if ("2".equals(ipaasService.getOper_type())) {
			type = Constant.PRIVATE_IMAGE_STATUS.TEST.toString().toLowerCase();
		} else {
			type = ipaasService.getOper_type();
		}
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(basePath);
		strBuf.append(ipaasService.getApp_name());
		strBuf.append("/");
		strBuf.append(type);
		strBuf.append("/");
		strBuf.append(Constant.K8S_SERVICE_TYPE.IPAAS.toString().toLowerCase());
		strBuf.append("/");
		strBuf.append(ipaasService.getName());

		return strBuf.toString();
	}

	/**
	 * 下载日志文件.
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String downloadFile() throws Exception {
		try {
			logger.info("下载应用日志文件");
			// HttpServletResponse response =
			// ServletActionContext.getResponse();
			String a[] = downloadPath.split("@");
			// String paths = a[0] + "/" + a[1];
			// // 读到流中
			// file = new FileInputStream(paths);// 文件的存放路径
			// // 设置输出的格式
			// response.reset();
			// String ext = a[1].substring(a[1].lastIndexOf(".") + 1)
			// .toUpperCase();
			// response.setContentType(ext);
			// response.addHeader("Content-Disposition",
			// "attachment;filename=\""
			// + a[1] + "\"");
			// // 循环取出流中的数据
			// byte[] b = new byte[1024];
			// int len;
			// try {
			// while ((len = file.read(b)) > 0)
			// response.getOutputStream().write(b, 0, len);
			// file.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			String logIp="";
			if (serviceId!=null&&!serviceId.equals("")) {
				AppService appService = appServiceService
						.queryAppServiceById(serviceId);
				logIp=appService.getLogServerIp();
			}else if(ipaasServiceId!=null&&!ipaasServiceId.equals("")){
				IpaasService ipaasService = ipaasServiceService
						.queryIpaasServiceById(ipaasServiceId);
				logIp=ipaasService.getLogServerIp();
			}
//			String logServerIp = getSessionMap().get("logServerIp")+"";
			file = appLogService.downloadFile(a[0], a[1], logIp);
			fileName = a[1];
		} catch (PaasWebException ex) {
			logger.error("下载应用日志文件异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
		return SUCCESS;
	}
	
	/**
	 * 删除日志文件.
	 * 
	 * @return String
	 * @throws Exception
	 */
	public void deleteFile() throws Exception {
		try {
			logger.info("删除应用日志文件");
			String a[] = deletePath.split("@");
			String logIp="";
			if (serviceId!=null&&!serviceId.equals("")) {
				AppService appService = appServiceService
						.queryAppServiceById(serviceId);
				logIp=appService.getLogServerIp();
			}else if(ipaasServiceId!=null&&!ipaasServiceId.equals("")){
				IpaasService ipaasService = ipaasServiceService
						.queryIpaasServiceById(ipaasServiceId);
				logIp=ipaasService.getLogServerIp();
			}
//			String logServerIp = getSessionMap().get("logServerIp")+"";
			// 删除应用日志
			appLogService.deleteFile(a[0], a[1], logIp);
			logger.info("删除应用日志文件成功!");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("删除应用日志文件异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查看应用日志
	 * 
	 * @return
	 * @throws Exception
	 */
	public void viewAppLog() throws Exception {
		RandomAccessFile randomFile = null;
		try {
			logger.info("查看应用日志文件");
			// String a[] = downloadPath.split("@");
			// appLog = appLogService.viewAppLog(a[0],a[1]);
			// sendSuccessReslult(JackJson.fromObjectToJson(appLog));
			// String fileName = "D:/用户目录/下载/catalina.2016-02-01.log";
			// //catalina.2016-02-01.log test.log
			// randomFile = new RandomAccessFile(fileName, "r");
			String path[] = downloadPath.split("@");
			randomFile = new RandomAccessFile(path[0] + "/" + path[1], "r");
			// long fileLength = randomFile.length();
			int lines = 0;
			int lines2 = 50;
			if (flag) {
				randomFile.seek((Long) getSessionMap().get("filePointer"));
				lines = (Integer) getSessionMap().get("lines");
				lines2 = lines + lines2;
			}
			// System.out.println((Long)getSessionMap().get("filePointer"));
			// System.out.println(randomFile.getFilePointer());
			String str = randomFile.readLine();
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");// 编码转换(解决中文乱码问题)
			StringBuffer sb = new StringBuffer();
			while (str != null) {
				if (lines == lines2) {
					str = "";
					break;
				}
				lines++;

				if (sb.length() == 0) {
					sb.append(lines + "、" + str);
				} else {
					str = randomFile.readLine();
					if (str != null) {
						str = new String(str.getBytes("ISO-8859-1"), "UTF-8");// 编码转换(解决中文乱码问题)
						sb.append("\n" + lines + "、" + str);
					} else {
						break; // 跳出执行sendSuccessReslult(JackJson.fromObjectToJson(sb));返回数据到页面
					}
				}
			}
			sendSuccessReslult(JackJson.fromObjectToJson(sb));
			// System.out.println(lines); //这个要返回或者记录
			// System.out.println(randomFile.getFilePointer()); //这个要返回或者记录
			getSessionMap().put("filePointer", randomFile.getFilePointer()); // session保存文件标记
			getSessionMap().put("lines", lines); // session保存行数记录
		} catch (PaasWebException ex) {
			logger.error("查看应用日志文件异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		} finally {
			if (randomFile != null) {
				randomFile.close();
			}
		}

	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDeletePath() {
		return deletePath;
	}

	public void setDeletePath(String deletePath) {
		this.deletePath = deletePath;
	}

	public String getIpaasServiceId() {
		return ipaasServiceId;
	}

	public void setIpaasServiceId(String ipaasServiceId) {
		this.ipaasServiceId = ipaasServiceId;
	}
	
	

}
