package com.cmsz.paas.common.model.controller.response;

import java.util.List;

import com.cmsz.paas.common.model.controller.entity.CodeAccountStatEntity;

public class CodeStatList {
	
	private boolean checkstat;
	
	private List<CodeAccountStatEntity> codeStat;

	public boolean isCheckstat() {
		return checkstat;
	}

	public void setCheckstat(boolean checkstat) {
		this.checkstat = checkstat;
	}

	public List<CodeAccountStatEntity> getCodeStat() {
		return codeStat;
	}

	public void setCodeStat(List<CodeAccountStatEntity> codeStat) {
		this.codeStat = codeStat;
	}

	@Override
	public String toString() {
		return "CodeStatList [codeStat=" + codeStat.toString() + "]";
	}	

}
