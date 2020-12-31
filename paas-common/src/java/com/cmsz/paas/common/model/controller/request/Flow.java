package com.cmsz.paas.common.model.controller.request;

import java.io.Serializable;
import java.util.List;

import com.cmsz.paas.common.model.controller.entity.StepEntity;

/**
 * 
 * @author guyj
 * @time 2017-8-24
 */
public class Flow  implements Serializable {
	
	/**
	 * @author guyj
	 * @time 2017-8-24
	 */
	private static final long serialVersionUID = 1L;

	private String name; //流水名
	private String appId; //应用名
	private int roleType; //操作类型，1-开发，2-测试
	private String createBy; //创建人
	private String desc; //流水描述
	private String token; //模糊查询
	
	//引用步骤实体
	private List<StepEntity> stepList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<StepEntity> getStepList() {
		return stepList;
	}

	public void setStepList(List<StepEntity> stepList) {
		this.stepList = stepList;
	}

	@Override
	public String toString() {
		return "Flow [name=" + name + ", appId=" + appId + ", roleType="
				+ roleType + ", createBy=" + createBy + ", desc=" + desc
				+ ", token=" + token + ", stepList=" + stepList + "]";
	}
	
	

}
