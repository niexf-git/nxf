/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File SvnAccountDetail.java
 */
package com.cmsz.paas.common.model.controller.response;

import com.cmsz.paas.common.model.controller.entity.CodeAccountEntity;

/**
 * @author hehm
 * 2016-3-23
 */
public class CodeAccountDetail {

	private CodeAccountEntity codeAccountDetail;

	public void setCodeAccountDetail(CodeAccountEntity codeAccountDetail) {
		this.codeAccountDetail = codeAccountDetail;
	}

	public CodeAccountEntity getCodeAccountDetail() {
		return codeAccountDetail;
	}
}
