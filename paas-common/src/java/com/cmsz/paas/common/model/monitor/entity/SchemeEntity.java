package com.cmsz.paas.common.model.monitor.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * Scheme
 *
 * @author lixiaofu
 *
 * @date 2016-12-20
 */
public class SchemeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ID
	private long id;
	private String schemeName;
	private int isMain;
	private int isPlatform;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public int getIsMain() {
		return isMain;
	}

	public void setIsMain(int isMain) {
		this.isMain = isMain;
	}

	public int getIsPlatform() {
		return isPlatform;
	}

	public void setIsPlatform(int isPlatform) {
		this.isPlatform = isPlatform;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	@Override
	public String toString() {
		return "SchemeEntity [id=" + id + ", schemeName=" + schemeName
				+ ", isMain=" + isMain + ", isPlatform=" + isPlatform
				+ ", description=" + description + "]";
	}

		

}
