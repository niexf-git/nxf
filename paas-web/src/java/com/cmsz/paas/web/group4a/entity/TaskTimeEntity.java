package com.cmsz.paas.web.group4a.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cmsz.paas.common.orm.id.ID;


/**
 * @author jiayz
 * @date 2019年11月6日
 */
public class TaskTimeEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ID
	private Integer id;
	
	private String startTime;
	private String endTime;
	/** 状态  0.未上传文件,1.文件成功上传**/
	private String status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
