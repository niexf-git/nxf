package com.cmsz.paas.web.ipaasinstance.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.response.AppServiceInstDiagnoseMsg;
import com.cmsz.paas.common.model.response.InstanceList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.common.utils.DateUtil;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.ipaasinstance.dao.IpaasInstanceDao;
import com.cmsz.paas.web.ipaasinstance.model.IpaasInstance;
import com.cmsz.paas.web.ipaasinstance.service.IpaasInstanceService;
import com.cmsz.paas.web.ipaasservice.service.impl.IpaasServiceServiceImpl;
/**
 * 
 * @author ccl
 * 
 */
@Service("ipaasInstanceService")
public class IpaasInstanceServiceImpl implements IpaasInstanceService{
	private static final Logger logger = LoggerFactory
			.getLogger(IpaasServiceServiceImpl.class);

	@Autowired
	private IpaasInstanceDao ipaasInstanceDao;
	/*
	 * 查询基础服务实例列表
	 */
	@Override
	public List<IpaasInstance> queryIpaasServiceInstsById(String ipaasServiceId)
			throws PaasWebException {
		List<IpaasInstance> list = new ArrayList<IpaasInstance>();
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"queryIpaasServiceInstsByIdUrl", ipaasServiceId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasInstanceDao.get(url,
					InstanceList.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			InstanceList instList = (InstanceList) responseInfo.getData();

			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (instList != null && instList.getInstanceList() != null) {
					for (int i = 0; i < instList.getInstanceList().size(); i++) {
						com.cmsz.paas.common.model.entity.Instance instance = instList
								.getInstanceList().get(i);
						IpaasInstance inst = translateInstance(instance);
						list.add(inst);
					}
				}
				//按时间排序
				Collections.sort(list,new Comparator<IpaasInstance>() {
					@Override
					public int compare(IpaasInstance o1, IpaasInstance o2) {
						// TODO Auto-generated method stub
						return o2.getCreateTime().compareTo(o1.getCreateTime());
					}
				});
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception ex) {
			logger.error("查询实例错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_IPAAINST_ERROR,
						ex.getMessage());
			}
		}
		return list;
	}

	/*
	 * 诊断实例
	 */
	@Override
	public String diagnosisIpass(String ipaasServiceId) throws PaasWebException {
		String result = ""; // 返回诊断的错误信息
		String retCode = "";
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"diagnosisIpaasServiceInstsUrl", ipaasServiceId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = ipaasInstanceDao.update(url,
					AppServiceInstDiagnoseMsg.class);

			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppServiceInstDiagnoseMsg appServiceInstDiagnose = (AppServiceInstDiagnoseMsg) responseInfo
					.getData();

			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			} else {
				if (appServiceInstDiagnose != null) {
					result = appServiceInstDiagnose.getResult();
				}
			}
		} catch (Exception ex) {
			logger.error("诊断方法错误", ex);
			if (ex instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DIAGNOSIS_IPAAINST_ERROR,
						ex.getMessage());
			}
		}
		return result;
	}
	/*
	 * 把控制中心的实例bean转为前台用的bean
	 */

	public IpaasInstance translateInstance(
			com.cmsz.paas.common.model.entity.Instance instance) {
		IpaasInstance inst = new IpaasInstance();

		inst.setInstanceId(instance.getId());
		inst.setContainerId(instance.getContainerId());
		inst.setStatus(instance.getStatus());
		inst.setCreateTime(DateUtil.tranformDate(instance.getCreateTime()
				.toString()));
		if (instance.getStartedAt().getTime() != 0) {
			inst.setLastTime(DateUtil.tranformDate(instance.getStartedAt()
					.toString()));
		} else {
			inst.setLastTime("-");
		}
		inst.setHostIP(instance.getHostIp());
		inst.setRestartNum(instance.getRestartCount() + "");
		inst.setSuggestMsg(instance.getSuggestMsg());

		return inst;
	}
}
