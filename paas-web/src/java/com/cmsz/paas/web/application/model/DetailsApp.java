package com.cmsz.paas.web.application.model;

public class DetailsApp {

	private String id;
	/***
	 * 原apaas集群
	 */
	private String originalApaasClusterName;
	/***
	 * 原ipaas集群
	 */
	private String originalIpaasClusterName;

	/***
	 * 迁移apaas集群
	 */
	private String migrationApaasClusterName;
	/***
	 * 迁移apaas集群
	 */
	private String migrationIpaasClusterName;

	/***
	 * 原数据中心名称
	 */
	private String originalDataCenterName;

	/***
	 * 迁移数据中心名称
	 */
	private String migrationDataCenterName;

	/***
	 * 最后迁移时间
	 */
	private String migrationDate;

	/***
	 * 状态
	 */
	private Long recordType;

	public Long getRecordType() {
		return recordType;
	}

	public void setRecordType(Long recordType) {
		this.recordType = recordType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriginalApaasClusterName() {
		return originalApaasClusterName;
	}

	public void setOriginalApaasClusterName(String originalApaasClusterName) {
		this.originalApaasClusterName = originalApaasClusterName;
	}

	public String getOriginalIpaasClusterName() {
		return originalIpaasClusterName;
	}

	public void setOriginalIpaasClusterName(String originalIpaasClusterName) {
		this.originalIpaasClusterName = originalIpaasClusterName;
	}

	public String getMigrationApaasClusterName() {
		return migrationApaasClusterName;
	}

	public void setMigrationApaasClusterName(String migrationApaasClusterName) {
		this.migrationApaasClusterName = migrationApaasClusterName;
	}

	public String getMigrationIpaasClusterName() {
		return migrationIpaasClusterName;
	}

	public void setMigrationIpaasClusterName(String migrationIpaasClusterName) {
		this.migrationIpaasClusterName = migrationIpaasClusterName;
	}

	public String getOriginalDataCenterName() {
		return originalDataCenterName;
	}

	public void setOriginalDataCenterName(String originalDataCenterName) {
		this.originalDataCenterName = originalDataCenterName;
	}

	public String getMigrationDataCenterName() {
		return migrationDataCenterName;
	}

	public void setMigrationDataCenterName(String migrationDataCenterName) {
		this.migrationDataCenterName = migrationDataCenterName;
	}

	public String getMigrationDate() {
		return migrationDate;
	}

	public void setMigrationDate(String migrationDate) {
		this.migrationDate = migrationDate;
	}

}
