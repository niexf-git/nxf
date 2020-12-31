package com.cmsz.paas.web.ipaasservice.model;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;

/**
 * 基础服务model，页面需要
 * 
 * @author fubl
 */
public class IpaasService implements Serializable {// to-do
	/**
	 * 
	 */
	private static final long serialVersionUID = 358369667747593799L;
	/**
	 * 基础服务id
	 */
	private String id;
	/**
	 * 
	 * 基础服务名称
	 */
	private String name;

	/**
	 * 基础服务状态
	 */
	private String status;

	/**
	 * 实例数
	 */
	private String instance_num;

	/**
	 * 处于运行状态的实例数
	 */
	private String running_Inst_num;

	/**
	 * 服务类型
	 */
	private String service_type;

	/**
	 * 镜像id
	 */
	private String image_id;

	/**
	 * 镜像名称
	 */
	private String image_name;
	
	/**
	 * 镜像版本id
	 */
	private String image_version_id;
	
	/**
	 * 镜像版本
	 */
	private String image_version;

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
	 * 基础服务缺省配置信息
	 */
	private String config;

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
	 * 外部服务地址
	 */
	private String service_url;

	/**
	 * 外部服务连接密码
	 */
	private String service_pwd;

	/**
	 * 应用服务列表
	 */
	private List<AppServices> appServices;

	/**
	 * 应用服务列表配置
	 */
	private String appService_configJson;
	
	/** 实例列表 */
	private List<Instance> instances;
	//activemq
	private String user;
	
	private Long clusterId;
	
	//日志服务器（flumeServer）地址
	private String logServerIp;
	
	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	private String clusterName;
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

	//activemq
	private String pwd;

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getRunning_Inst_num() {
		return running_Inst_num;
	}

	public void setRunning_Inst_num(String running_Inst_num) {
		this.running_Inst_num = running_Inst_num;
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

	public String getImage_version() {
		return image_version;
	}

	public void setImage_version(String image_version) {
		this.image_version = image_version;
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

	public String getService_pwd() {
		return service_pwd;
	}

	public void setService_pwd(String service_pwd) {
		this.service_pwd = service_pwd;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public List<AppServices> getAppServices() {
		return appServices;
	}

	public void setAppServices(List<AppServices> appServices) {
		this.appServices = appServices;
	}

	public String getAppService_configJson() {
		return appService_configJson;
	}

	public void setAppService_configJson(String appService_configJson) {
		this.appService_configJson = appService_configJson;
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

	public String getService_url() {
		return service_url;
	}

	public void setService_url(String service_url) {
		this.service_url = service_url;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImage_version_id() {
		return image_version_id;
	}

	public void setImage_version_id(String image_version_id) {
		this.image_version_id = image_version_id;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public String getLogServerIp() {
		return logServerIp;
	}

	public void setLogServerIp(String logServerIp) {
		this.logServerIp = logServerIp;
	}

}