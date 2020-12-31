package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.CodeCheckEntity;
import com.cmsz.paas.common.model.controller.entity.CodeCheckStatementEntity;

public class CodeCheckList {
	private List<CodeCheckStatementEntity> codeCheckList;

	public List<CodeCheckStatementEntity> getCodeCheckList() {
		return codeCheckList;
	}

	public void setCodeCheckList(List<CodeCheckStatementEntity> codeCheckList) {
		this.codeCheckList = codeCheckList;
	}

	@Override
	public String toString() {
		return "CodeCheckList [codeCheckList=" + codeCheckList + "]";
	}

	
}
