package com.cmsz.paas.web.grafana.service;

import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestResult;

/**
 * grafana管理 Service.
 * 
 * @author liaohw
 */
public interface GrafanaService {

	/**
	 * 创建 dashboard
	 * 
	 * @param json
	 * @throws PaasWebException
	 */
	public RestResult createDashboard(String json) throws PaasWebException;

	/**
	 * 删除 dashboard
	 * 
	 * @param title
	 * @throws PaasWebException
	 */
	public RestResult deleteDashboard(String title) throws PaasWebException;

	/**
	 * 查询 dashboard
	 * 
	 * @param title
	 * @throws PaasWebException
	 */
	public RestResult queryDashboard(String title) throws PaasWebException;

}
