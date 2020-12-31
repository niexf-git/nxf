package com.cmsz.paas.web.application.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cmsz.paas.common.model.controller.entity.MigrationRecordEntity;

public class ApplicationInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//应用Id
	private long id;
	
	//应用名称
	private String appName;
	
	//仓库用户名和密码
	private String userPwd;
	
	//源数据中心名称
	private String originalDataName;
	
	//源集群
	private String originalCluster;
	
	//目标数据中心名称
	private String migrationDataName;

	//目标(迁移)集群
	private String migrationCluster;
	
	//描述
	private String desc;
	
	//创建人
	private String createBy;
	
	//创建时间
	private String createTime;
	
	//是否管理员
	private boolean isAdmin;
	
	//是否容灾
	private int isDisasterTolerance;
	
	private List<MigrationRecordEntity> migrationRecords;

	public List<MigrationRecordEntity> getMigrationRecords() {
		return migrationRecords;
	}

	public void setMigrationRecords(List<MigrationRecordEntity> migrationRecords) {
		this.migrationRecords = migrationRecords;
	}

	public int getIsDisasterTolerance() {
		return isDisasterTolerance;
	}

	public void setIsDisasterTolerance(int isDisasterTolerance) {
		this.isDisasterTolerance = isDisasterTolerance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getOriginalCluster() {
		return originalCluster;
	}

	public void setOriginalCluster(String originalCluster) {
		this.originalCluster = originalCluster;
	}

	public String getMigrationCluster() {
		return migrationCluster;
	}

	public void setMigrationCluster(String migrationCluster) {
		this.migrationCluster = migrationCluster;
	}

	public String getOriginalDataName() {
		return originalDataName;
	}

	public void setOriginalDataName(String originalDataName) {
		this.originalDataName = originalDataName;
	}

	public String getMigrationDataName() {
		return migrationDataName;
	}

	public void setMigrationDataName(String migrationDataName) {
		this.migrationDataName = migrationDataName;
	}
	
}
