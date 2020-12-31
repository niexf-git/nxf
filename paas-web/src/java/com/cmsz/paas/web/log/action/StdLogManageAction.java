package com.cmsz.paas.web.log.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.model.SelectType;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.log.service.StdLogService;

/**
 * 标准输出日志管理类.
 *
 * @author li.lv 
 * 2016-4-10
 */
@UnLogging
public class StdLogManageAction extends AbstractAction {

	private static final long serialVersionUID = 1477984974312042436L;
	
	private static final Logger logger = LoggerFactory.getLogger(StdLogManageAction.class);
	
	private final String TEMP_FILE_PATH = "templogfile/";
	
	/** 输出文件流 */
	private InputStream file;
	
	/** 文件名称 */
	private String fileName;
	
	/** The instance service. */
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private StdLogService stdLogService;
	/** 实例ID */
	private String instanceId;
	/** 服务ID */
	private String serviceId;
	/** 主机IP */
	private String hostIp;
	/** 开始时间 */
	private String startTime;
	/** 类型 */
	private String intType;

	/** 命名空间 */
	private String namespace;
	
	/** 实例名称  */
	private String instName;


	/**
	 * 初始化实例下拉框
	 * 
	 * @throws Exception
	 */
	public void queryInstance() throws Exception {
		List<SelectType> list = new ArrayList<SelectType>();
		List<Instance> instances = instanceService.queryInstByAppServiceId(serviceId);
		for (int i = 0; i < instances.size(); i++) {
			Instance instance = instances.get(i);
			//实例id，容器id，hostIP都不为空时才加入实例下拉框
			if (StringUtils.isNotBlank(instance.getInstanceId())
					&& StringUtils.isNotBlank(instance.getContainerId())
					&& StringUtils.isNotBlank(instance.getHostIP())) {
				SelectType selectType = new SelectType();
				selectType.setValue(instance.getInstanceId() + "_" + instance.getHostIP()+"_"+instance.getType());
				selectType.setText(instance.getInstanceId());
				list.add(selectType);
			}
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		logger.info("实例下拉框数据初始化完成：" + jsonArray.toString());
		sendSuccessReslult(jsonArray.toString());
	}
	
	
	/**
	 * 初始化时间区间 
	 */
	public void initDate(){
		Date preDate = com.cmsz.paas.web.base.util.DateUtil.plusDays(new Date(), -1);
		String minDate = DateUtil.tranformDate(preDate.toString());
		String str="{\"startTime\":\""+minDate+"\"}";
		logger.info("开始时间初始化为：" + minDate);
		sendSuccessReslult(str);
	}
	
	/**
	 * 下载应用服务标准输出日志文件
	 * @return
	 * @throws Exception
	 */
	public String downloadStdFile() throws Exception{
		//删除某个时间点以前的文件
		String path = getFilePath();
		deleteFile(path);
		Date date = com.cmsz.paas.web.base.util.DateUtil.toDate(startTime, "yyyy-MM-dd HH:mm:ss");
		long time = date.getTime()/1000;
		//调用rest接口，拿到url
		String url = stdLogService.queryAppServiceStdLogUrl(serviceId, instanceId,intType, hostIp, time+"") + "&follow=false";
		//String url = "http://192.168.59.54:2375/containers/e6c7591de28a/logs?stderr=1&stdout=1&timestamps=1&follow=false&since="+time;
		logger.info("标准输出日志下载地址："+url);
		String fileName = Long.valueOf(System.currentTimeMillis()) + "_" + getInstanceId() + ".txt";
		//根据url生成文件到前台服务器
		downloadFromUrl(url, path + fileName);
		//返回流下载到本地
		file = ServletActionContext.getServletContext().getResourceAsStream(TEMP_FILE_PATH + fileName);	
		this.fileName = fileName;
		return SUCCESS;
	}
	
	public String downloadChLogFile() throws Exception {
		try {
			String path = getFilePath();
			String url = stdLogService.queryChLogUrl(namespace, instName, "0");
			String fileName = Long.valueOf(System.currentTimeMillis()) + "_" + instName + "_chLog.txt";
			logger.info("历史容器日志下载地址："+url);
			downloadFromUrl(url,path + fileName,true);
			logger.info("历史容器日志下载本地成功！");
			//返回流下载到本地
			file = ServletActionContext.getServletContext().getResourceAsStream(TEMP_FILE_PATH + fileName);	
			this.fileName = fileName;
			logger.info("历史容器日志下载完成！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 下载基础服务标准输出日志文件
	 * @return
	 * @throws Exception
	 */
	public String downloadIppasStdFile() throws Exception{
		//删除某个时间点以前的文件
		String path = getFilePath();
		deleteFile(path);
		//调用rest接口，拿到url
		Date date = com.cmsz.paas.web.base.util.DateUtil.toDate(startTime, "yyyy-MM-dd HH:mm:ss");
		long since = date.getTime()/1000;
		String url=stdLogService.queryIpaasServiceStdLog(serviceId, instanceId, hostIp, since);
		logger.info("标准输出日志下载地址："+url);
		String fileName = Long.valueOf(System.currentTimeMillis()) + "_" + getInstanceId() + ".txt";
		//根据url生成文件到前台服务器
		downloadFromUrl(url, path + fileName);
		//返回流下载到本地
		file = ServletActionContext.getServletContext().getResourceAsStream(TEMP_FILE_PATH + fileName);	
		this.fileName = fileName;
		return SUCCESS;
	}
	
	/**
	 * 获取完整的文件名，文件放在项目部署路径/tempfile文件夹下
	 * 文件名组成:时间+实例id
	 * @return
	 */
	private String getFilePath(){
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(0, path.lastIndexOf("WEB-INF")) + TEMP_FILE_PATH;
		File f = new File(path);
		//创建目录
		if(!f.exists()){
			f.mkdirs();
		}
		return path;
	}
	/**
	 * 删除文件
	 * @param path
	 */
	private void deleteFile(String path){
		String fileName = null;
		Long fileCreateTime = Long.MAX_VALUE;
		Long currentTime = System.currentTimeMillis();
		//保护时间
		String guardTime = PropertiesUtil.getValue("guardTime");
		Long guardTimeMillis = 60 *60 * 1000L;//1小时
		if(StringUtils.isNotBlank(guardTime) && !"[]".equals(guardTime)){
			guardTimeMillis = Long.valueOf(guardTime) * guardTimeMillis;
		}
		File folder = new File(path);
		File[] files = folder.listFiles();
		if(files == null){
			return;
		}
		for(File file : files){
			fileName = file.getName();
			int index = fileName.indexOf("_");
			if(index > -1){
				fileCreateTime = Long.valueOf(fileName.substring(0, index));
			}
			//当前时间与文件生成时间的差大于某个配置值就删除该文件
			if(currentTime - fileCreateTime > guardTimeMillis){
				logger.info("删除日志文件的名称:" + file.getName());
				file.delete();
			}
		}
	}
	/**
	 * 根据url下载文件到前台服务器
	 * @param url
	 * @param fullFileName
	 * @throws Exception 
	 */
	private void downloadFromUrl(String url, String fullFileName,boolean flag) {
		BufferedReader br = null;
		PrintStream ps = null;
		try {
			URL httpUrl = new URL(url);
			//输入流
			InputStream inputStream = httpUrl.openStream();
			InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
			logger.info("开启流读取URL中的日志信息！");
			br = new BufferedReader(isr);
			String s = br.readLine();
			//输出到文件
			File file = new File(fullFileName);
			ps = new PrintStream(new FileOutputStream(file));
			while (s != null) {
				// 处理每行前面的乱码
				if (s.length() > 8 && !flag) {
					s = s.substring(8);
				}
				ps.println(s);
				// 读取下一行数据
				s = br.readLine();
			}
			logger.info("日志信息读取完成！");
		} catch (Exception e) {
			if(!flag) {
				e.printStackTrace();
			}else {
				File file = new File(fullFileName);
				try {
					ps = new PrintStream(new FileOutputStream(file));
					ps.println("暂无日志信息！");
				} catch (FileNotFoundException e1) {
					
				}
				
			}
			//如果日志为空访问URL会抛出异常，默认为空内容！
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ps != null){
				ps.close();
			}
		}
	}
	
	/**
	 * 方法重构
	 * @param url
	 * @param fullFileName
	 */
	private void downloadFromUrl(String url, String fullFileName) {
		downloadFromUrl(url,fullFileName,false);
	}
	
	public void queryChLogs() throws Exception {
		try {
			String url = stdLogService.queryChLogUrl(namespace, instName, "1000");
			String log = readStreamData(url);
			sendSuccessReslult(log);
		} catch (PaasWebException e) {
			logger.error("查询容器历史日志异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	
	private String readStreamData(String url) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			URL httpUrl = new URL(url);
			InputStream inputStream = httpUrl.openStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			br = new BufferedReader(isr);
			String line = null;
			
			while((line = br.readLine()) != null){
				sb.append(line);
				sb.append("<br/>");
			}
		}catch (Exception e) {
			// 默认为空
		}finally{
			if(br != null){
				br.close();
			}
			if("".equals(sb.toString())) {
				sb.append("暂无日志信息！");
			}
		}
		return sb.toString();
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getHostIp() {
		return hostIp;
	}


	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
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


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}


	public String getNamespace() {
		return namespace;
	}


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String getInstName() {
		return instName;
	}


	public void setInstName(String instName) {
		this.instName = instName;
	}

	
}
