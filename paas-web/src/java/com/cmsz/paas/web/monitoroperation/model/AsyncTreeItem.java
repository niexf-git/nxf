package com.cmsz.paas.web.monitoroperation.model;

/**
 * 集群树bean.
 * 
 * @author liaohw
 * @date 2016-12-20
 */
public class AsyncTreeItem {

	/** 唯一编号 */
	private String id;

	/** 父编号 */
	private String pId;

	/** 显示的名称 */
	private String name;

	/** 是否是父节点 */
	private int isParent = 1;

	/** 是否打开子节点 */
	private int open = 0;

	/** 显示的图标 */
	private String icon;

	/** 点击后请求的地址 */
	private String url;

	/** 刷新的区域 */
	private String target = "right";

	/** 数据中心类型 */
	private String dataCenterType;

	/** 服务类型 */
	private String serviceType;
	
	/** 集群类型 */
	private String clusterType;

	public String getClusterType() {
		return clusterType;
	}

	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDataCenterType() {
		return dataCenterType;
	}

	public void setDataCenterType(String dataCenterType) {
		this.dataCenterType = dataCenterType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
