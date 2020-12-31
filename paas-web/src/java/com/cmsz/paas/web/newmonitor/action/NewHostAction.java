package com.cmsz.paas.web.newmonitor.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.GrafanaJsonFileUtil;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.RestResult;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.grafana.service.GrafanaService;
import com.cmsz.paas.web.newmonitor.model.NewHost;
import com.cmsz.paas.web.newmonitor.service.NewHostService;

/**
 * 普通用户主机列表Action.
 * 
 * @author ccl
 * @date 2018-5-7
 */
public class NewHostAction extends AbstractAction {


	private static final long serialVersionUID = 6649002815468610957L;

	private static final Logger logger = LoggerFactory
			.getLogger(NewHostAction.class);

	@Autowired
	private NewHostService newHostService;
	@Autowired
	private GrafanaService grafanaService;


	// 集群id
	private String clusterId;


	
	/** 节点Ip */
	private String nodeIp;
	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;
	
	/** grafana url */
	private String url;
	



	/**
	 * 查询主机列表
	 */
	@UnLogging
	public void queryHostList() throws Exception {
		logger.info("开始执行查询主机列表，集群id：" + clusterId);
		@SuppressWarnings("unchecked")
		Page<NewHost> page = this.getJqGridPage("createTime");
		try {
			List<NewHost> list = newHostService.queryHostList(clusterId);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询主机列表完成，主机条数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询主机列表错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}


	@UnLogging
	public void queryGrafana() {
		logger.info("开始执行查询Grafana路径");
		try {
			RestResult result = grafanaService.queryDashboard(nodeIp.replace(
					".", "-"));
			if (!(result.getRetCode().equals(Constants.REST_CODE_SUCCESS))) {
				String json = GrafanaJsonFileUtil.getMinionJson(nodeIp);
				grafanaService.createDashboard(json);
			}
			url = RestUtils.grafanaUrl("queryGrafanaDashboardsUrl",
					nodeIp.toLowerCase().replace(".", "-"));
			sendSuccessReslult(url);
		} catch (PaasWebException ex) {
			logger.error("[" + ex.getKey() + "]查询Grafana路径出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
			errorCode = ex.getErrorCode();
			errorMsg = ex.toString();
		}
	}


	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}




	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	

}
