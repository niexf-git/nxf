package com.cmsz.paas.common.model.agent.request;

import java.util.List;

import com.cmsz.paas.common.model.agent.entity.ComponentEntity;
import com.cmsz.paas.common.model.agent.entity.LabelEntity;

public class LabelList {

	private List<LabelEntity> labelList;

	public List<LabelEntity> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelEntity> labelList) {
		this.labelList = labelList;
	}

	@Override
	public String toString() {
		return "LabelList [labelList=" + labelList +  "]";
	}

}
