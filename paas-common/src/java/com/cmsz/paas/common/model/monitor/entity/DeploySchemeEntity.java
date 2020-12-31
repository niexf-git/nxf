package com.cmsz.paas.common.model.monitor.entity;
/**
 * @author liping
 * 2016-12-22
 */
public class DeploySchemeEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String schemeId;
	private String components;
	private long hainfo;
	private String description;

	private SchemeEntity schemeEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SchemeEntity getSchemeEntity() {
		return schemeEntity;
	}

	public void setSchemeEntity(SchemeEntity schemeEntity) {
		this.schemeEntity = schemeEntity;
	}

	public long getHainfo() {
		return hainfo;
	}

	public void setHainfo(long hainfo) {
		this.hainfo = hainfo;
	}

	@Override
	public String toString() {
		return "DeploySchemeEntity [id=" + id + ", schemeId=" + schemeId
				+ ", components=" + components + ", hainfo=" + hainfo
				+ ", description=" + description + ", schemeEntity="
				+ schemeEntity + "]";
	}

	

	

	
	
}
