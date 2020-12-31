package com.cmsz.paas.web.appservice.model;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;

/**
 * 应用服务model，页面需要
 * 
 * @author fubl
 */
public class AppService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 358369667747593799L;
	/**
	 * 应用服务id
	 */
	private String id;
	
	/**
	 * 流水ID 
	 */
	private String flowId;
	
	/**
	 * 
	 * 应用名称
	 */
	private String name;

	/**
	 * 应用服务状态
	 */
	private String status;

	/**
	 * 实例数
	 */
	private String instance_num;
	
	/**
	 * 灰度实例数
	 */
	private int grayInstanceNum;

	/**
	 * 处于运行状态的实例数
	 */
	private String running_Inst_num;

	/**
	 * 镜像类型
	 */
	private String image_type;

	/**
	 * 镜像id
	 */
	private String image_id;

	/**
	 * 镜像名称
	 */
	private String image_name;

	/**
	 * 镜像版本
	 */
	private String image_version;

	/**
	 * 镜像版本id
	 */
	private String image_version_id;

	/**
	 * 所属应用id
	 */
	private String app_id;

	/**
	 * 所属应用名称
	 */
	private String app_name;

	/**
	 * 操作类型
	 */
	private String oper_type;

	/**
	 * CPU
	 */
	private String cpu;

	/**
	 * 最大CPU
	 */
	private String maxCpu;

	/**
	 * 内存
	 */
	private String mem;

	/**
	 * 最大内存
	 */
	private String maxMem;

	/**
	 * 所属集群id
	 */
	private String cluster_id;

	/**
	 * 所属集群名称
	 */
	private String cluster_name;

	/**
	 * 日志绝对路径
	 */
	private String log_dir;

	/**
	 * 创建时间
	 */
	private String create_time;

	/**
	 * 用户ID，创建人
	 */
	private String user_id;
	/**
	 * 应用服务描述
	 */
	private String description;

	/**
	 * 应用配置是否生效
	 */
	private String config_effect;

	/**
	 * 环境变量配置
	 */
	private List<EnvConfig> envConfig;
	private String configJson;
	private String envsConfFileName;// 环境变量文件导入名字
	/** 配置的keys */
	private String[] appConfKey;
	/** 配置的values */
	private String[] appConfValue;

	/**
	 * 是否服务标志
	 */
	private String serviceFlag;

	/***
	 * 是否指定主机IP
	 */
	private String hostIpFlag;
	/**
	 * 是否外部服务type=1表示内部服务，type=2表示外部服务
	 */
	private String extServiceType;

	/**
	 * 容器端口
	 */
	private String containerPort;

	/**
	 * 外部服务地址
	 */
	private String service_url;
	
	/***
	 * 主机IP
	 */
	private String hostIp;

	/**
	 * 实例伸缩类型，固定-1，动态-2
	 */
	private String inst_scale_type;

	/**
	 * cpu目标值
	 */
	private String cpu_target;

	/**
	 * 最小实例数
	 */
	private String inst_min;

	/**
	 * 最大实例数
	 */
	private String inst_max;
	
	/***
	 * 协议类型
	 */
	private int protocolType;

	// /**
	// * ipaas服务id
	// */
	// private String ipaas_service_id;
	//
	// /**
	// * ipaas服务名称
	// */
	// private String ipaas_service_name;
	//
	// /**
	// * ipaas服务绑定串
	// */
	// private String ipaas_service_bindstr;

	/**
	 * ipaas服务列表
	 */
	private List<Ipaas> ipaas;
	/** ipaas服务名称 */
	private String[] ipaasName;
	/** ipaas服务绑定名称 */
	private String[] ipaasBindStr;

	/**
	 * ipaas配置
	 */
	private String ipaas_configJson;
	/** 配置文件完整路径 */
	private String configFilePath;
	/** 配置文件内容 */
	private String configFileInfo;
	
	/** 实例集合,监控运维通过ip查询服务会包括服务下的实例列表 */
	private List<Instance> instances;
	
	//是否灰度版本
	private int existGray;
	
	//灰度-镜像类型
	private String grayImageType;
	
	//灰度-镜像id
	private String grayImageId;
		
	//灰度-镜像版本id
	private String grayImageVersionId;
	
	//日志服务器（flumeServer）地址
	private String logServerIp;
	
	/**
	 * 部署类型
	 */
	private int deployType;
	
	

	public int getDeployType() {
		return deployType;
	}

	public void setDeployType(int deployType) {
		this.deployType = deployType;
	}

	public int getExistGray() {
		return existGray;
	}

	public void setExistGray(int existGray) {
		this.existGray = existGray;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getImage_version_id() {
		return image_version_id;
	}

	public void setImage_version_id(String image_version_id) {
		this.image_version_id = image_version_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInstance_num() {
		return instance_num;
	}

	public void setInstance_num(String instance_num) {
		this.instance_num = instance_num;
	}

	public String getRunning_Inst_num() {
		return running_Inst_num;
	}

	public void setRunning_Inst_num(String running_Inst_num) {
		this.running_Inst_num = running_Inst_num;
	}

	public String getImage_type() {
		return image_type;
	}

	public void setImage_type(String image_type) {
		this.image_type = image_type;
	}

	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String[] getAppConfKey() {
		return appConfKey;
	}

	public void setAppConfKey(String[] appConfKey) {
		this.appConfKey = appConfKey;
	}

	public String[] getAppConfValue() {
		return appConfValue;
	}

	public void setAppConfValue(String[] appConfValue) {
		this.appConfValue = appConfValue;
	}

	public String getImage_version() {
		return image_version;
	}

	public void setImage_version(String image_version) {
		this.image_version = image_version;
	}

	public List<EnvConfig> getEnvConfig() {
		return envConfig;
	}

	public String getEnvsConfFileName() {
		return envsConfFileName;
	}

	public String[] getIpaasName() {
		return ipaasName;
	}

	public void setIpaasName(String[] ipaasName) {
		this.ipaasName = ipaasName;
	}

	public String[] getIpaasBindStr() {
		return ipaasBindStr;
	}

	public void setIpaasBindStr(String[] ipaasBindStr) {
		this.ipaasBindStr = ipaasBindStr;
	}

	public void setEnvsConfFileName(String envsConfFileName) {
		this.envsConfFileName = envsConfFileName;
	}

	public void setEnvConfig(List<EnvConfig> envConfig) {
		this.envConfig = envConfig;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getCpu() {
		return cpu;
	}

	public String getInst_scale_type() {
		return inst_scale_type;
	}

	// public String getIpaas_service_id() {
	// return ipaas_service_id;
	// }
	//
	// public void setIpaas_service_id(String ipaas_service_id) {
	// this.ipaas_service_id = ipaas_service_id;
	// }

	// public String getIpaas_service_name() {
	// return ipaas_service_name;
	// }
	//
	// public void setIpaas_service_name(String ipaas_service_name) {
	// this.ipaas_service_name = ipaas_service_name;
	// }
	//
	// public String getIpaas_service_bindstr() {
	// return ipaas_service_bindstr;
	// }

	// public void setIpaas_service_bindstr(String ipaas_service_bindstr) {
	// this.ipaas_service_bindstr = ipaas_service_bindstr;
	// }

	public List<Ipaas> getIpaas() {
		return ipaas;
	}

	public void setIpaas(List<Ipaas> ipaas) {
		this.ipaas = ipaas;
	}

	public void setInst_scale_type(String inst_scale_type) {
		this.inst_scale_type = inst_scale_type;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMaxCpu() {
		return maxCpu;
	}

	public void setMaxCpu(String maxCpu) {
		this.maxCpu = maxCpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getMaxMem() {
		return maxMem;
	}

	public void setMaxMem(String maxMem) {
		this.maxMem = maxMem;
	}

	public String getCluster_id() {
		return cluster_id;
	}

	public void setCluster_id(String cluster_id) {
		this.cluster_id = cluster_id;
	}

	public String getCluster_name() {
		return cluster_name;
	}

	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}

	public String getLog_dir() {
		return log_dir;
	}

	public void setLog_dir(String log_dir) {
		this.log_dir = log_dir;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfig_effect() {
		return config_effect;
	}

	public void setConfig_effect(String config_effect) {
		this.config_effect = config_effect;
	}

	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public String getExtServiceType() {
		return extServiceType;
	}

	public void setExtServiceType(String extServiceType) {
		this.extServiceType = extServiceType;
	}

	public String getContainerPort() {
		return containerPort;
	}

	public void setContainerPort(String containerPort) {
		this.containerPort = containerPort;
	}

	public String getService_url() {
		return service_url;
	}

	public void setService_url(String service_url) {
		this.service_url = service_url;
	}

	public String getCpu_target() {
		return cpu_target;
	}

	public void setCpu_target(String cpu_target) {
		this.cpu_target = cpu_target;
	}

	public String getInst_min() {
		return inst_min;
	}

	public void setInst_min(String inst_min) {
		this.inst_min = inst_min;
	}

	public String getInst_max() {
		return inst_max;
	}

	public void setInst_max(String inst_max) {
		this.inst_max = inst_max;
	}

	public String getIpaas_configJson() {
		return ipaas_configJson;
	}

	public void setIpaas_configJson(String ipaas_configJson) {
		this.ipaas_configJson = ipaas_configJson;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(int protocolType) {
		this.protocolType = protocolType;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	public String getConfigFileInfo() {
		return configFileInfo;
	}

	public void setConfigFileInfo(String configFileInfo) {
		this.configFileInfo = configFileInfo;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public String getGrayImageVersionId() {
		return grayImageVersionId;
	}

	public void setGrayImageVersionId(String grayImageVersionId) {
		this.grayImageVersionId = grayImageVersionId;
	}

	public String getGrayImageType() {
		return grayImageType;
	}

	public void setGrayImageType(String grayImageType) {
		this.grayImageType = grayImageType;
	}

	public String getGrayImageId() {
		return grayImageId;
	}

	public void setGrayImageId(String grayImageId) {
		this.grayImageId = grayImageId;
	}

	public String getHostIpFlag() {
		return hostIpFlag;
	}

	public void setHostIpFlag(String hostIpFlag) {
		this.hostIpFlag = hostIpFlag;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getLogServerIp() {
		return logServerIp;
	}

	public void setLogServerIp(String logServerIp) {
		this.logServerIp = logServerIp;
	}

	public int getGrayInstanceNum() {
		return grayInstanceNum;
	}

	public void setGrayInstanceNum(int grayInstanceNum) {
		this.grayInstanceNum = grayInstanceNum;
	}


	

}