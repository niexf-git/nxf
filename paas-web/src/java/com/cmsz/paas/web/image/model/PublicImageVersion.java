package com.cmsz.paas.web.image.model;

/**
 * 公共镜版本
 * 
 * @author lin.my 2016-4-19
 */
public class PublicImageVersion {

	private String id;

	private String publicImageId;

	private String tag;

	private String version;

	private String status;

	private String url;

	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublicImageId() {
		return publicImageId;
	}

	public void setPublicImageId(String publicImageId) {
		this.publicImageId = publicImageId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PublicImageVersion [id=" + id + ", publicImageId="
				+ publicImageId + ", tag=" + tag + ", version=" + version
				+ ", status=" + status + ", url=" + url + ", description="
				+ description + "]";
	}

}
