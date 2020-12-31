package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PrivateImage.java
 * @Description: 
 *
 *
 * @author zhongmg
 * @date: 2017年11月23日 上午9:33:26
 * @version: v1.0
 */
public class PrivateImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	  /**部署次数*/
      private Integer deployNum;
      
      /**镜像名称*/
      private String name;
      
      private List<PrivateImageVersionList> privateImageVersionListList;
      
	public Integer getDeployNum() {
		return deployNum;
	}
	public void setDeployNum(Integer deployNum) {
		this.deployNum = deployNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PrivateImageVersionList> getPrivateImageVersionListList() {
		return privateImageVersionListList;
	}
	public void setPrivateImageVersionListList(
			List<PrivateImageVersionList> privateImageVersionListList) {
		this.privateImageVersionListList = privateImageVersionListList;
	}
      
}
