package com.cmsz.paas.common.model.controller.entity;

public class QueuePrivateImg {

	private long imgId;
	
	private String imgName;
	
	private String tag;

	public long getImgId() {
		return imgId;
	}

	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "QueuePrivateImg [imgId=" + imgId + ", imgName=" + imgName + ", tag=" + tag + "]";
	}
}
