package com.cmsz.paas.web.monitoroperation.model;

import java.util.List;

import com.cmsz.paas.web.appserviceinst.model.Instance;







public class IpaasServiceDetail {
	/** 所属应用id */
	private String id;

	/** 所属应用名称 */
	private String appName;

	/** 操作类型1-开发，2-测试，3-运维 */
	private String operateType;
	/** 服务类型1-zk、2-Redis */
	private String serviceType;
	/** 节点数目 */
	private String nodeNum;
	/** 状态 */
	private String status;
	/** 运行的节点 */
	private String runningNode;
	/** 服务名称 */
	private String name;
	/** 创建人 */
	private String createBy;

	/** 创建时间 */
	private String createTime;

	/** 实例列表 */
	private List<Instance> insts;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}

	public String getRunningNode() {
		return runningNode;
	}

	public void setRunningNode(String runningNode) {
		this.runningNode = runningNode;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public List<Instance> getInsts() {
		return insts;
	}

	public void setInsts(List<Instance> insts) {
		this.insts = insts;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
