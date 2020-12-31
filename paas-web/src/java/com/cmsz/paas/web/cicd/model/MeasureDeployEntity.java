package com.cmsz.paas.web.cicd.model;

import java.util.List;

/***
 * 概览-构建指标
 * @author jiangwei
 *
 */
public class MeasureDeployEntity {

	/***
	 * 镜像名称
	 */
	private String imageName;
	
	/***
	 * 总部署次数
	 */
	private String totalCount;
	
	/***
	 * 部署信息列表
	 */
	private List<DeployInfo> deployList;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<DeployInfo> getDeployList() {
		return deployList;
	}

	public void setDeployList(List<DeployInfo> deployList) {
		this.deployList = deployList;
	}
	
	
	
	
}
