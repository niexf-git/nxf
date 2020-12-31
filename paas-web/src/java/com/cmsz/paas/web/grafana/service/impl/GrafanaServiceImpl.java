package com.cmsz.paas.web.grafana.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JasypUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.grafana.dao.GrafanaDao;
import com.cmsz.paas.web.grafana.service.GrafanaService;

/**
 * grafana管理 Service实现.
 * 
 * @author liaohw
 */
@Service("grafanaServiceImpl")
public class GrafanaServiceImpl implements GrafanaService {

	private static final Logger logger = LoggerFactory
			.getLogger(GrafanaServiceImpl.class);

	@Autowired
	private GrafanaDao grafanaDao;

	@Override
	public RestResult createDashboard(String json) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.grafanaRestUrl("createGrafanaDashboards");
			logger.info("开始调用grafana Rest接口：" + url);

			ResponseInfo responseInfo = grafanaDao.create(
					PropertiesUtil.getValue("grafana.username"),
					JasypUtil.decrypt(PropertiesUtil.getValue("grafana.password")), url, json);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.GRAFANA_REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(Constants.REST_CODE_SUCCESS);
				logger.info("创建grafana dashboards成功");
			} else {
				throw new PaasWebException(Constants.CREATE_GRAFANA_ERROR, msg);
			}
		} catch (Exception ex) {
			logger.error("创建grafana dashboard错误", ex);
			if (ex instanceof PaasWebException) {
				throw (PaasWebException) ex;
			} else {
				throw new PaasWebException(Constants.CREATE_GRAFANA_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public RestResult deleteDashboard(String title) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.grafanaRestUrl("deleteGrafanaDashboards",
					title.toLowerCase());
			logger.info("开始调用grafana Rest接口：" + url);

			ResponseInfo responseInfo = grafanaDao.delete(
					PropertiesUtil.getValue("grafana.username"),
					JasypUtil.decrypt(PropertiesUtil.getValue("grafana.password")), url);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.GRAFANA_REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(Constants.REST_CODE_SUCCESS);
				logger.info("删除grafana dashboards成功");
			} else {
				throw new PaasWebException(Constants.DELETE_GRAFANA_ERROR, msg);
			}
		} catch (Exception ex) {
			logger.error("删除grafana dashboard错误", ex);
			if (ex instanceof PaasWebException) {
				throw (PaasWebException) ex;
			} else {
				throw new PaasWebException(Constants.DELETE_GRAFANA_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}

	@Override
	public RestResult queryDashboard(String title) throws PaasWebException {
		RestResult result = new RestResult();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.grafanaRestUrl("queryGrafanaDashboards",
					title.toLowerCase());
			logger.info("开始调用grafana Rest接口：" + url);

			ResponseInfo responseInfo = grafanaDao.get(
					PropertiesUtil.getValue("grafana.username"),
					JasypUtil.decrypt(PropertiesUtil.getValue("grafana.password")), url);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (Constants.GRAFANA_REST_CODE_SUCCESS.equals(retCode)) {
				result.setRetCode(Constants.REST_CODE_SUCCESS);
				logger.info("查询grafana dashboards成功");
			} else {
				result.setRetCode(Constants.QUERY_GRAFANA_ERROR);
				result.setMsg(msg);
			}
		} catch (Exception ex) {
			logger.error("查询grafana dashboard错误", ex);
			if (ex instanceof PaasWebException) {
				throw (PaasWebException) ex;
			} else {
				throw new PaasWebException(Constants.QUERY_GRAFANA_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}

}
