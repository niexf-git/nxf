package com.cmsz.paas.common.model.controller.response;

import java.io.Serializable;

/**
 * @ClassName: DepRecordList.java
 * @Description: 
 *
 *
 * @author zhongmg
 * @date: 2017年11月22日 下午5:32:55
 * @version: v1.0
 */
public class DepRecord implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private PrivateImage privateImage;

	public PrivateImage getPrivateImage() {
		return privateImage;
	}

	public void setPrivateImage(PrivateImage privateImage) {
		this.privateImage = privateImage;
	}

}
