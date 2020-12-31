package com.cmsz.paas.web.monitoroperation.model;

/**
 * 数据中心
 * 
 * @author lin.my
 * @version 创建时间：2016年12月20日 上午9:57:17
 */
public class DataCenter {

	private String id; // 数据中心Id
	private String name; // 数据中心名称
	private String isMainDataCenter; // 是否主数据中心
	private String ipaasHaproxy; // ipaasHaproxy地址
	private String appHaproxy; // apaasHaproxy地址
	private String harborUrl; // harbor地址
	private String registryUrl; // registry地址
	private String harborUser; // harbor用户名
	private String harborPasswd; // 密码
	private String harborId;
	private String description; // 数据中心描述
	private String insertTime; // 创建时间

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

	public String getIsMainDataCenter() {
		return isMainDataCenter;
	}

	public void setIsMainDataCenter(String isMainDataCenter) {
		this.isMainDataCenter = isMainDataCenter;
	}

	public String getIpaasHaproxy() {
		return ipaasHaproxy;
	}

	public void setIpaasHaproxy(String ipaasHaproxy) {
		this.ipaasHaproxy = ipaasHaproxy;
	}

	public String getAppHaproxy() {
		return appHaproxy;
	}

	public void setAppHaproxy(String appHaproxy) {
		this.appHaproxy = appHaproxy;
	}

	public String getHarborUrl() {
		return harborUrl;
	}

	public void setHarborUrl(String harborUrl) {
		this.harborUrl = harborUrl;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public String getHarborUser() {
		return harborUser;
	}

	public void setHarborUser(String harborUser) {
		this.harborUser = harborUser;
	}

	public String getHarborPasswd() {
		return harborPasswd;
	}

	public void setHarborPasswd(String harborPasswd) {
		this.harborPasswd = harborPasswd;
	}

	public String getHarborId() {
		return harborId;
	}

	public void setHarborId(String harborId) {
		this.harborId = harborId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
