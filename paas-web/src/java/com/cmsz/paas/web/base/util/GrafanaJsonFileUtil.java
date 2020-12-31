package com.cmsz.paas.web.base.util;

import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.constant.Constant;
import com.cmsz.paas.web.appserviceinst.model.Instance;
import com.cmsz.paas.web.appserviceinst.service.InstanceService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.ipaasinstance.model.IpaasInstance;


public class GrafanaJsonFileUtil  {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(GrafanaJsonFileUtil.class);
	/** The instance service. */
	@Autowired
	private InstanceService instanceService;

	/**
	 * 获取创建grafana dashboards 的json字符串.
	 */
	public static String getDashboardsJson(String appName,String operType,String serviceName,String serviceType,List<Instance> list,String type) throws PaasWebException{
		serviceType = getServiceType(serviceType);
		operType=getOperType(operType);	
		String namespaceName = appName + "-" + operType;
		String title = serviceType + "_"+appName + "_" + operType+"_"+ serviceName;
		String podName=serviceName+"-";
		String isRun="";
		if (list!=null&&"runing".equals(type)) {
			for (int i = 0; i < list.size(); i++) {
				if (i==0) {
					isRun+=" AND pod_name = '"+list.get(i).getInstanceId()+"'";
				}else{
					isRun+=" OR pod_name = '"+list.get(i).getInstanceId()+"'";
				}
			}
		}
		//json模板文件完整路径
		String path = GrafanaJsonFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String fileInputPath = path + "grafana-template.json";		
		String content = null;
		try {
			// 从json模板文件读取内容
			content = FileUtil.read(fileInputPath);
		} catch (Exception e) {
			logger.error("读grafana的json模板文件错误",e);
			throw new PaasWebException(Constants.READ_JSON_TEMPLATE_FILE_ERROR,e.getMessage());
		}
		// 字符串内容替换
		if (content != null && !"".equals(content)) {
			content = content.replace("${title}", title.toLowerCase().trim());
			content = content.replace("${namespaceName}", namespaceName.toLowerCase().trim());
			content = content.replace("${podName}", podName.toLowerCase().trim());
			content = content.replace("${isRun}", isRun.toLowerCase());
		}
		
		return content;
	}
	
	/**
	 * 获取创建grafana dashboards 的json字符串.
	 */
	public static String getDashboardsJsonByIpaas(String appName,String operType,String serviceName,String serviceType,List<IpaasInstance> list,String type) throws PaasWebException{
		serviceType = getServiceType(serviceType);
		operType=getOperType(operType);	
		String namespaceName = appName + "-" + operType;
		String title = serviceType + "_"+appName + "_" + operType+"_"+ serviceName;
		String podName=serviceName+"-";
		String isRun="";
		if (list!=null&&"runing".equals(type)) {
			for (int i = 0; i < list.size(); i++) {
				if (i==0) {
					isRun+=" AND pod_name = '"+list.get(i).getInstanceId()+"'";
				}else{
					isRun+=" OR pod_name = '"+list.get(i).getInstanceId()+"'";
				}
			}
		}
		//json模板文件完整路径
		String path = GrafanaJsonFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String fileInputPath = path + "grafana-template.json";		
		String content = null;
		try {
			// 从json模板文件读取内容
			content = FileUtil.read(fileInputPath);
		} catch (Exception e) {
			logger.error("读grafana的json模板文件错误",e);
			throw new PaasWebException(Constants.READ_JSON_TEMPLATE_FILE_ERROR,e.getMessage());
		}
		// 字符串内容替换
		if (content != null && !"".equals(content)) {
			content = content.replace("${title}", title.toLowerCase().trim());
			content = content.replace("${namespaceName}", namespaceName.toLowerCase().trim());
			content = content.replace("${podName}", podName.toLowerCase().trim());
			content = content.replace("${isRun}", isRun.toLowerCase());
		}
		
		return content;
	}
	
	
	/**
	 * 获取创建miniongrafana dashboards 的json字符串.
	 */
	public static String getMinionJson(String minionIp) throws PaasWebException{
		String nodeName = minionIp;
		//json模板文件完整路径
		String path = GrafanaJsonFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String fileInputPath = path + "grafana-node.json";		
		String content = null;
		try {
			// 从json模板文件读取内容
			content = FileUtil.read(fileInputPath);
		} catch (Exception e) {
			logger.error("读grafana的json模板文件错误",e);
			throw new PaasWebException(Constants.READ_JSON_TEMPLATE_FILE_ERROR,e.getMessage());
		}
		
		// 字符串内容替换
		if (content != null && !"".equals(content)) {
			content = content.replace("${nodeName}", nodeName.toLowerCase().trim());
		}
		
		return content;
	}
	
	/**
	 * Delete json file.
	 */
	public static void deleteJsonFile(String name) throws PaasWebException{
		String fullFileName = getFullFileName(name);
		try{
			File file = new File(fullFileName);
			if(file.exists()){
				file.delete();
			}
		}catch(Exception e){
			logger.error("删除grafana的json文件错误", e);
			throw new PaasWebException(Constants.DELETE_JSON_FILE_ERROR,e.getMessage());
		}
	}

	/**
	 * 获取目标json文件完整路径.
	 */
	public static String getFullFileName(String name) throws PaasWebException {
		String fileName = name + ".json";
		String tempPath = "";
		String grafanaPath = "";
		try {
			String path=GrafanaJsonFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			tempPath = path.substring(0, path.lastIndexOf(ServletActionContext.getRequest().getContextPath()));
			// 从配置文件读取json文件存放的目录
			grafanaPath = PropertiesUtil.getValue("grafana.path");
		} catch (Exception e) {
			logger.error("获取完整的json文件名错误", e);
			throw new PaasWebException(Constants.GET_FULL_FILE_NAME_ERROR, e.getMessage());
		}
		return tempPath + grafanaPath + fileName;
	}
	
	/**
	 * 获取服务类型编码对应的英文名称.
	 */
	public static String getServiceType(String serviceType) throws PaasWebException {
		if (serviceType.equals("1")) {
			serviceType=Constant.K8S_SERVICE_TYPE.IPAAS.toString().toLowerCase().trim();
		}else if (serviceType.equals("2")) {
			serviceType=Constant.K8S_SERVICE_TYPE.APPSERVICE.toString().toLowerCase().trim();
		}
		return serviceType;
	}
	/**
	 * 获取操作类型对应的英文名称.
	 */
	public static String getOperType(String operType) throws PaasWebException {
		if (operType.equals("1")) {
			operType=Constant.PRIVATE_IMAGE_STATUS.DEV.toString().toLowerCase().trim();
		}else if (operType.equals("2")) {
			operType=Constant.PRIVATE_IMAGE_STATUS.TEST.toString().toLowerCase().trim();
		}else if (operType.equals("3")) {
			operType=Constant.PRIVATE_IMAGE_STATUS.OPRT.toString().toLowerCase().trim();
		}
		return operType;
	}

}
