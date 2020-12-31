package com.cmsz.paas.web.monitoroperation.action;

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
import com.cmsz.paas.web.monitoroperation.model.DeployComponentInfo;
import com.cmsz.paas.web.monitoroperation.model.DeploySchemeInfo;
import com.cmsz.paas.web.monitoroperation.model.Host;
import com.cmsz.paas.web.monitoroperation.model.HostHA;
import com.cmsz.paas.web.monitoroperation.service.HostService;

/**
 * 主机管理Action.
 * 
 * @author liaohw
 * @date 2016-12-23
 */
public class HostAction extends AbstractAction {

	public List<HostHA> getHostListHA() {
		return hostListHA;
	}

	public void setHostListHA(List<HostHA> hostListHA) {
		this.hostListHA = hostListHA;
	}

	private static final long serialVersionUID = 6649002815468610957L;

	private static final Logger logger = LoggerFactory
			.getLogger(HostAction.class);

	@Autowired
	private HostService hostService;
	@Autowired
	private GrafanaService grafanaService;

	// 数据中心类型
	private String dataCenterType;

	// 集群id
	private String clusterId;

	// 集群类型
	private String clusterType;

	// 主机id
	private String hostId;

	// 新增主机使用
	private List<Host> hostList;
	
	//新增HA使用
	
	private List<HostHA> hostListHA;

	// 部署方案id
	private String deploySchemeId;

	// 0:新增node主机，1：新增paas平台主机
	private String flag;
	
	/** 节点Ip */
	private String nodeIp;
	/** 错误信息. */
	private String errorMsg;

	/** 错误编码. */
	private String errorCode;
	
	/** grafana url */
	private String url;
	
	/** 组件名称. */
	private String compName;


	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * 查询主机列表
	 */
	public void queryHostList() throws Exception {
		logger.info("开始执行查询主机列表，集群id：" + clusterId);
		@SuppressWarnings("unchecked")
		Page<Host> page = this.getJqGridPage("createTime");
		try {
			List<Host> list = hostService.queryHostList(clusterId);
			page.setResult(list);
			page.setTotalCount(list.size());
			this.renderText(JackJson.fromObjectToJson(page));
			logger.info("查询主机列表完成，主机条数：" + list.size());
		} catch (PaasWebException ex) {
			logger.error("查询主机列表错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 查询主机，组件操作详情
	 */
	public void queryHostAndCompDetails() throws Exception {
		logger.info("开始执行查询主机，组件操作详情 ip：" + nodeIp);
		try {
			String str = hostService.queryHostAndCompDetails(nodeIp,compName);
			this.renderText(str);
			logger.info("查询主机，组件操作详情完成"+str);
		} catch (PaasWebException ex) {
			logger.error("查询主机，组件操作详情错误 ", ex);
			this.sendFailResult2jqGrid(ex.getKey(), ex.toString());
		}
	}
	/**
	 * 新增主机
	 */
	public void createHost() throws Exception {
		logger.info("开始执行新增主机，集群id：" + clusterId);
		try {
			RestResult result = hostService.createHost(clusterId, hostList,hostListHA);
			sendSuccessReslult(result.getData());
			logger.info("新增主机成功！");
		} catch (PaasWebException e) {
			logger.error("新增主机错误 ", e);
			this.sendFailResult(e.getKey(), e.toString());
		}
	}

	/**
	 * 删除主机
	 */
	public void deleteHost() throws Exception {
		logger.info("开始执行删除主机，主机ID：" + hostId);
		try {
			hostService.deleteHost(hostId);
			sendSuccessReslult("SUCCESS");
			logger.info("删除主机成功！");
		} catch (PaasWebException ex) {
			logger.error("删除主机错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/**
	 * 删除集群下的所有主机
	 */
	public void deleteAllHost() throws Exception {
		logger.info("开始执行删除所有主机，集群ID：" + clusterId);
		try {
			hostService.deleteAllHost(clusterId);
			sendSuccessReslult("SUCCESS");
			logger.info("删除所有主机成功！");
		} catch (PaasWebException ex) {
			logger.error("删除所有主机错误", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}

	/**
	 * 查询部署方案列表
	 */
	public void queryDeploySchemeList() throws Exception {
		logger.info("开始执行查询部署方案列表，数据中心类型：" + dataCenterType + "，集群类型："
				+ clusterType);
		try {
			List<DeploySchemeInfo> deploySchemeList = hostService
					.queryDeploySchemeList(dataCenterType, clusterType);
			// 转成json串
			String deploySchemeListJson = JackJson
					.fromObjectToJson(deploySchemeList);
			sendSuccessReslult(deploySchemeListJson);
			logger.info("查询部署方案列表完成");
		} catch (PaasWebException ex) {
			logger.error("查询部署方案列表错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 查询部署组件列表， 集群类型（1.ipaas集群，2.apaas集群）
	 */
	public void queryDeployComponentList() throws Exception {
		logger.info("开始执行查询部署组件列表，"
				+ ("0".equals(flag) ? "集群类型：" + clusterType : "部署方案id："
						+ deploySchemeId));
		try {
			List<DeployComponentInfo> deployComponentList = hostService
					.queryDeployComponentList("0".equals(flag) ? clusterType
							: deploySchemeId, flag);
			// 转成json串
			String deployComponentListJson = JackJson
					.fromObjectToJson(deployComponentList);
			sendSuccessReslult(deployComponentListJson);
			logger.info("根据" + ("0".equals(flag) ? "集群类型" : "部署方案id")
					+ "查询部署组件列表完成");
		} catch (PaasWebException ex) {
			logger.error("根据" + ("0".equals(flag) ? "集群类型" : "部署方案id")
					+ "查询部署组件列表错误 ", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
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

	public String getDataCenterType() {
		return dataCenterType;
	}

	public void setDataCenterType(String dataCenterType) {
		this.dataCenterType = dataCenterType;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterType() {
		return clusterType;
	}

	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public List<Host> getHostList() {
		return hostList;
	}

	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}

	public String getDeploySchemeId() {
		return deploySchemeId;
	}

	public void setDeploySchemeId(String deploySchemeId) {
		this.deploySchemeId = deploySchemeId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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
