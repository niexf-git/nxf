package com.cmsz.paas.web.permission.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * 操作权限类
 * @author zhouyunxia
 *
 */
public class OperPermission implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**操作权限id*/
	@ID
	private String id;
	/** 操作权限名称 */
	private String name;
	/**操作权限父类*/
	private String pId;
	/** 菜单级别 */
	private int level;
	/**路径*/
	private String url;
	/**图片*/
	private String img;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "OperPermission [id=" + id + ", name=" + name + ", pId=" + pId
				+ ", level=" + level + ", url=" + url + ", img=" + img + "]";
	}
}
