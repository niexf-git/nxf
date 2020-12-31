package com.cmsz.paas.web.cicd.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.request.DepScan;
import com.cmsz.paas.common.model.controller.response.StepDetail;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.BasePropertiesUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.cicd.dao.DeploymentDao;
import com.cmsz.paas.web.cicd.model.DepScanEntity;
import com.cmsz.paas.web.cicd.service.DeploymentService;
import com.cmsz.paas.web.constants.Constants;

/***
 * 部署service实现类
 * 
 * @author jiangwei
 * 
 */

@Service("deploymentService")
public class DeploymentServiceImpl implements DeploymentService {

	private static final Logger logger = LoggerFactory
			.getLogger(DeploymentServiceImpl.class);

	@Autowired
	private DeploymentDao deploymentDao;

	@Override
	public void saveCheckSecurity(String flowId, int isCheck) {
		logger.info("收到保存安全状态请求，flowId：" + flowId.toString() + ",修改状态为:"
				+ isCheck);
		String url = RestUtils.restUrlTransform("saveCheckSecurityUrl", flowId,
				isCheck);
		ResponseInfo responseInfo;
		String retCode = "";
		;
		String msg = "";
		try {
			logger.info("开始调用保存安全状态的rest接口：" + url);
			responseInfo = deploymentDao.update(url, ResponseInfo.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.SAVE_CHECK_ERROR,
						"无法访问保存安全状态的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.info("调用保存安全状态rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用保存安全状态的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]保存安全状态出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.SAVE_CHECK_ERROR + "]保存安全状态出错", ex);
				throw new PaasWebException(Constants.SAVE_CHECK_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public DepScanEntity queryDepScanEntity(String flowId, String type,
			String url) {
		DepScanEntity entity = null;
		logger.info("收到查询部署" + (!type.equals("dep") ? "&扫描" : "")
				+ "详情请求，flowId：" + flowId.toString());
		ResponseInfo responseInfo;
		String retCode = "";
		;
		String msg = "";
		try {
			logger.info("开始调用查询部署" + (!type.equals("dep") ? "&扫描" : "")
					+ "的rest接口：" + url);
			responseInfo = deploymentDao.get(url, StepDetail.class);
			if (responseInfo == null) {
				throw new PaasWebException(
						Constants.QUERY_DEPSCAN_ENTITY_ERROR, "无法访问查询部署"
								+ (!type.equals("dep") ? "&扫描" : "")
								+ "的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			StepDetail stepDetail = (StepDetail) responseInfo.getData();
			logger.info("调用查询部署" + (!type.equals("dep") ? "&扫描" : "")
					+ "rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (stepDetail != null && stepDetail.getDepScan() != null) {
					entity = new DepScanEntity();
					entity.setServiceID(stepDetail.getDepScan().getAppSvcId());
					entity.setAppSvcName(stepDetail.getDepScan()
							.getAppSvcName());
					entity.setIsCheck(stepDetail.getDepScan().getIsCheck() + "");
					entity.setType(stepDetail.getDepScan().getType() + "");
					entity.setWebUrl(stepDetail.getDepScan().getScanUrl());
					entity.setGray(!stepDetail.getDepScan().getIsGrayVer());//true是可以灰度 加!是因为前台页面的值绑定
					entity.setGrayNum(stepDetail.getDepScan().getInstanceNum());
					entity.setTotalNum(stepDetail.getDepScan().getTotalInstanceNum());
					entity.setRunVersion(stepDetail.getDepScan().getServiceRunVersion());
					entity.setRunVersionId(stepDetail.getDepScan().getServiceRunVersionId()+"");
					entity.setInstanceNum(stepDetail.getDepScan().getInstanceNum()+"");
					entity.setState(stepDetail.getDepScan().getServiceState()+"");
					if(null != stepDetail.getDepScan().getDescribe()){
						entity.setGrayInfo(BasePropertiesUtil.getValue(stepDetail.getDepScan().getDescribe()));
					}else{
						entity.setGrayInfo("");
					}
					return entity;
				} else {
					return null;
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			if (ex instanceof PaasWebException) {
				logger.error("[" + retCode + "]查询部署"
						+ (!type.equals("dep") ? "&扫描" : "") + "出错", ex);
				throw new PaasWebException(retCode, msg);
			} else {
				logger.error("[" + Constants.QUERY_DEPSCAN_ENTITY_ERROR
						+ "]查询部署" + (!type.equals("dep") ? "&扫描" : "") + "出错",
						ex);
				throw new PaasWebException(
						Constants.QUERY_DEPSCAN_ENTITY_ERROR, ex.getMessage());
			}
		}

	}

	@Override
	public void updateDepScan(DepScanEntity depScanEntity, String type,
			String url) throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			DepScan depScan = translateToControllerDepScan(depScanEntity);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = deploymentDao.update(url, depScan,
					ResponseInfo.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error(
					"修改部署" + (!type.equals("dep") ? "&扫描" : "") + "配置信息错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_DEPSCAN_ERROR,
						ex.getMessage());
			}
		}
	}

	@Override
	public void modifyDeploymentType(String appServiceId,
			String deploymentType)
			throws PaasWebException {
		String retCode = "";
		String msg = "";
		try {
			DepScan depScan = genDepScan(appServiceId, deploymentType);
			String url = RestUtils.restUrlTransform(
					"queryOrSvaeDepScanEntityUrl", "0");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = deploymentDao.update(url, depScan,
					ResponseInfo.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();

			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("修改部署方式错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.MODIFY_DEPLOYMENTTYPE_ERROR, ex.getMessage());
			}
		}

	}

	private DepScan translateToControllerDepScan(DepScanEntity depScanEntity) {
		DepScan depScan = new DepScan();
		depScan.setAppSvcId(depScanEntity.getServiceID());
		depScan.setAppSvcName(depScanEntity.getAppSvcName());
		depScan.setIsCheck(Integer.valueOf(depScanEntity.getIsCheck()));
		depScan.setType(Integer.valueOf(depScanEntity.getType()));
		depScan.setScanUrl(depScanEntity.getWebUrl());
		depScan.setInstanceNum(Integer.valueOf(depScanEntity.getGrayNum()));
		depScan.setTotalInstanceNum(Integer.valueOf(depScanEntity.getTotalNum()));
		return depScan;
	}

	private DepScan genDepScan(String appServiceId, String deploymentType) {
		DepScan depScan = new DepScan();
		if (appServiceId != null && !"".equals(appServiceId)) {
			depScan.setAppSvcId(appServiceId);
		}
		if (deploymentType != null && !"".equals(deploymentType)) {
			depScan.setType(Integer.valueOf(deploymentType));
		}
//		if (grayInstanceNum != null && !"".equals(grayInstanceNum)) {
//			depScan.setInstanceNum(Integer.valueOf(grayInstanceNum));
//		}
//		if (totalInstanceNum != null && !"".equals(totalInstanceNum)) {
//			depScan.setTotalInstanceNum(Integer.valueOf(totalInstanceNum));
//		}
		return depScan;
	}

}
