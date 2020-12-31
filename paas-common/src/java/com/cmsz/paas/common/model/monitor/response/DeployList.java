package com.cmsz.paas.common.model.monitor.response;

import java.util.List;

import com.cmsz.paas.common.model.monitor.entity.DeployEntity;

/**
 * Scheme
 * 
 * @author lixiaofu
 * 
 * @date 2016-12-20
 */

public class DeployList {

	private List<DeployEntity> deploylist;

	public List<DeployEntity> getDeploylist() {
		return deploylist;
	}

	public void setDeploylist(List<DeployEntity> deploylist) {
		this.deploylist = deploylist;
	}

}
