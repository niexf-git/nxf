package com.cmsz.paas.web.base.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.spring.Springs;
import com.cmsz.paas.web.base.log.dao.OperationLogDao;
import com.cmsz.paas.web.base.util.syslog4jUtil;

public class OperationLogUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(OperationLogUtil.class);

	private static OperationLogDao operationLogDao = Springs
			.getBean("operationLogDao");

	public static String ipAddress;

	/**
	 * 添加审计日志
	 * 
	 * @param operator
	 * @param operatePath
	 * @param operFunc
	 * @param operateType
	 * @param status
	 */
	public static void insertOperationLog(String operator, String operatePath,
			String operFunc, String operateType, String status,
			String serviceId, String errorMsg, Object primary_user) {
		try {
			if (!operateType.equals("查询")) {
				MyOperationLog log = new MyOperationLog();

				log.setOperator(operator);
				log.setClientIp(ipAddress);
				// 操作时间
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String operateTime = df.format(new Date());
				log.setOperateTime(operateTime);

				log.setOperatePath(operatePath);
				log.setOperateType(operateType);
				log.setFunc(operFunc);
				log.setStatus(status);
				log.setOperateDesc(errorMsg);
				if (serviceId != null && !("".equals(serviceId))) {
					log.setDetail(serviceId);
				} else {
					log.setDetail("");
				}
				operationLogDao.insert(log);
				syslog4jUtil.sendSyslog4(log, operator, primary_user);
			}
		} catch (Exception e) {
		}

	}

	/**
	 * 获取IP地址
	 * 
	 * @return
	 */
	public static String getIpAddress() {
		String ip = "";
		try {
			InetAddress ia = InetAddress.getLocalHost();
			ip = ia.getHostAddress();// 获取IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	// /**
	// * 查询详情
	// *
	// * @param appId
	// * @return 集群，应用组，应用
	// */
	// public static String getDetail(String appId) {
	// String detail = "";
	// if (appId != null && !"".equals(appId)) {
	// try {
	// Application app = applicationService.queryOneApplication(appId);
	// detail = app.getClusterName() + "," + app.getAppGroupName()
	// + "," + app.getName();
	// } catch (PaasWebException e) {
	// logger.error("根据应用id查询应用失败(审计日志详情)");
	// }
	// }
	// return detail;
	// }

}
