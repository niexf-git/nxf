package com.cmsz.paas.web.log.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.response.ContainerExitUrl;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.model.response.StdlogList;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.log.dao.StdLogDao;
import com.cmsz.paas.web.log.service.StdLogService;

/**
 * 标准输出日志获取URL实现类
 * @author li.lv 
 * 2016-4-26
 */
@Service("stdLogService")
public class StdLogServiceImpl implements StdLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(StdLogServiceImpl.class);

	@Autowired
	private StdLogDao stdLogDao;

	/** 返回码         */
	private String retCode = "";
	/** 消息      */
	private String msg = "";

	@Override
	public String queryAppServiceStdLogUrl(String appServiceId, String instanceId,String intType, String hostIp, String since) throws PaasWebException {
		try {
			String url = RestUtils.restUrlTransform("queryAppServiceStdLogUrl", appServiceId, instanceId,intType, hostIp, since);
			logger.info("开始调用Rest接口：" + url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = stdLogDao.get(url, StdlogList.class);
			
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			// 对返回码进行判断，正确的会对数据进行解析
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				StdlogList stdlogList = (StdlogList) responseInfo.getData();
				return stdlogList.getUrl();
			} else {
				logger.error("应用服务标准输出日志的url获取错误");
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("应用服务标准输出日志的url获取错误",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_APPSERVICE_STD_LOG_ERROR,
						ex.getMessage());
			}
		}
	}
	
	@Override
	public String queryIpaasServiceStdLog(String ipaasServiceId,
			String instanceId, String hostIp, long since) throws Exception {
		try {
			String time=Long.toString(since);
			String url = RestUtils.restUrlTransform("queryIpaasServiceStdLogUrl",
					ipaasServiceId,instanceId,hostIp,time);
			logger.debug("Rest url:"+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = stdLogDao.get(url, com.cmsz.paas.common.model.response.StdlogList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("Rest responseInfo:"+retCode+msg);
			// 对返回码进行判断，正确的会对数据进行解析
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				com.cmsz.paas.common.model.response.StdlogList stdlogList = (com.cmsz.paas.common.model.response.StdlogList) responseInfo.getData();
				return stdlogList.getUrl();
			} else {
				logger.error("标准输出日志查询异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (PaasWebException ex) {
			logger.error("标准输出日志查询异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						ex.getMessage());
			}
		}
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String queryChLogUrl(String namespace, String instanceName, 	String line) throws Exception{
		try {
			String url = RestUtils.restUrlTransform("queryChLogUrl",
					namespace,instanceName,line);
			logger.debug("Rest url:"+url);
			// 调用接口，获取数据
			ResponseInfo responseInfo = stdLogDao.get(url, ContainerExitUrl.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("Rest responseInfo:"+retCode+msg);
			// 对返回码进行判断，正确的会对数据进行解析
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				return ((ContainerExitUrl) responseInfo.getData()).getUrl();
			} else {
				logger.error("容器历史日志查询异常");
				throw new PaasWebException(retCode, msg);
			}
		} catch (PaasWebException ex) {
			logger.error("容器历史日志查询异常",ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_CH_STD_LOG_ERROR,
						ex.getMessage());
			}
		}
	}

}
