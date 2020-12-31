package com.cmsz.paas.common.model.controller.response;

import java.util.List;

public class BranchesList {
	private List<String> branchesList;

	@Override
	public String toString() {
		return "BranchesList [branchesList=" + branchesList + "]";
	}

	public List<String> getBranchesList() {
		return branchesList;
	}

	public void setBranchesList(List<String> branchesList) {
		this.branchesList = branchesList;
	}


}
