/*
 * 
 */
package com.cmsz.paas.web.appservice.model;

/**
 * 环境变量.
 * 
 * @author fubl
 */
public class EnvConfig {

	/** The id. */
	private Long id;

	/** relatedId. */
	private String relatedId;

	/** The config key. */
	private String configKey;

	/** The config value. */
	private String configValue;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the related id.
	 * 
	 * @return the related id
	 */
	public String getRelatedId() {
		return relatedId;
	}

	/**
	 * Sets the related id.
	 * 
	 * @param relatedId
	 *            the new related id
	 */
	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	/**
	 * Gets the config key.
	 * 
	 * @return the config key
	 */
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * Sets the config key.
	 * 
	 * @param configKey
	 *            the new config key
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	/**
	 * Gets the config value.
	 * 
	 * @return the config value
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * Sets the config value.
	 * 
	 * @param configValue
	 *            the new config value
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
