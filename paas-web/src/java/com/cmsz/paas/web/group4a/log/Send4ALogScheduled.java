package com.cmsz.paas.web.group4a.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.group4a.entity.TaskTimeEntity;
import com.cmsz.paas.web.group4a.service.TaskTimeService;
import com.cmsz.paas.web.group4a.tool.SftpUtils;
import com.cmsz.paas.web.log.entity.SysLogEntity;
import com.cmsz.paas.web.log.service.SysLogService;

/**
 * 审计接口
 * 
 * @date 2020年12月9日
 * @param
 * @return
 */
@Component
public class Send4ALogScheduled {
	private String monitorDir = "./logs/upload/";
	private String uploadTime = "15";// 15分钟上传一次

	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory.getLogger(Send4ALogScheduled.class);

	@Autowired
	private SftpUtils sftpUts;

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private TaskTimeService taskTimeService;

	private static SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostConstruct
	// 初始化96个时间段，1天 每15分钟一个时间段，一共96*15min=24h
	public void init() {
		try {
			ininTime(96);// 初始化时间
		} catch (Exception e) {
		}
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void updateTime() {
		try {
			ininTime(192);// 每天0点执行一次更新时间

		} catch (Exception e) {
		}
	}

	@Scheduled(cron = "0 */15 * * * ?")
	@Async
	public void upload() {// 数据生成及文件上传
		try {
			logger.info("线程号-" + Thread.currentThread().getName() + ",执行定时任务时间：" + new Date());
			String startTime = "";
			Date toDay = new Date();
			Map<String, String> map = getDateSection(96);
			for (Entry<String, String> set : map.entrySet()) {
				Date curDate = sdfs.parse(set.getKey());
				Date nextDate = sdfs.parse(set.getValue());
				if (toDay.getTime() >= curDate.getTime() && toDay.getTime() <= nextDate.getTime()) {
					// 查询当前时间属于哪个时间段
					startTime = set.getKey();
				}
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("time", sdfs.parse(startTime));
			paramsMap.put("status", "0");// 查找未生成的文件
			List<TaskTimeEntity> taskList = taskTimeService.findByMap(paramsMap);
			if (taskList.size() > 0) {
				for (TaskTimeEntity task : taskList) {
					//应用日志
					String logXml = generateXmlLog(task.getStartTime(), task.getEndTime());// 生成XML
					boolean bool = createFileNameAndUpload(task.getStartTime(), logXml,
							PropertiesUtil.getValue("serviceId") + "_YY_");// 文件写入在本地并上传
					//接口日志
					String logXml1 = generateXmlLog1(task.getStartTime(), task.getEndTime());
					boolean bool1 = createFileNameAndUpload1(task.getStartTime(), logXml1,
							PropertiesUtil.getValue("serviceId") + "_JK_");// 文件写入在本地并上传
					// 上传成功后更新状态
					if (bool&&bool1) {
						task.setStatus("1");
						taskTimeService.update(task);
					}
				}
			}
		} catch (Exception e) {
			logger.error("上传审计日志至4A失败" + e.toString());
		}
	}

	private boolean createFileNameAndUpload1(String date, String xml, String code) {
		// 将字符串转换成document操纵dom树
		try {
			Document document = DocumentHelper.parseText(xml);
			// 判断文件夹,不存在则创建
			File files = new File(monitorDir);
			if (!files.isDirectory()) {
				files.mkdirs();
			}
			String fileName;
			fileName = code + new SimpleDateFormat("yyyyMMddHHmmss").format(sdfs.parse(date)) + ".xml";
			String filePath = monitorDir + fileName;
			File file = new File(filePath);
			writerOutFile(document, file);// 将文件写入本地工程目录
			logger.info("createFile  succcess fileName = " + fileName);
			boolean bool = sftpUts.UploadFile(PropertiesUtil.getValue("sftp.path"), fileName, file.getPath());
			if (bool) {
				logger.info("上传日志文件文件到4a sftp success" + fileName);
			} else {
				logger.info("上传日志文件文件到4a sftp fail" + fileName);
			}
			return bool;
		} catch (Exception e) {
			logger.error("createFile  fail:" + e.toString());
			return false;
		}
	}

	private String generateXmlLog1(String startTimeStr, String endTimeStr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", sdfs.parse(startTimeStr));
		map.put("endTime", sdfs.parse(endTimeStr));
		List<SysLogEntity> list = sysLogService.findByMap(map);
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buffer.append("<ROOT>");
		if (list.size() > 0) {
			for (SysLogEntity operLog : list) {
				buffer.append("<LOG4A>");
				buffer.append("<UNIQUE_ID>"+PropertiesUtil.getValue("serviceId")+"_"+operLog.getId()+"</UNIQUE_ID>");// 日志唯一id
				buffer.append("<SESSION_ID>"+operLog.getSessionId()+"</SESSION_ID>");// 会话ID,关联接口日志的标识
				buffer.append("<IF_INVOKE_TIME>"+ sdfs.format(operLog.getOperateTime()) +"</IF_INVOKE_TIME>");// 接口调用时间
				String str="";
				if(operLog.getOperatePath() != null &&  operLog.getOperatePath() !=""){
					if (operLog.getOperatePath().toString().contains(".action")) {
						String substring = operLog.getOperatePath().substring(0, operLog.getOperatePath().indexOf(".action"));
						String[] split = substring.split("/");
						for (int i = 0; i < split.length; i++) {
							if(i == split.length-1){
								str=split[i].toString();
							}
						}
					}else{
						String[] split = operLog.getOperatePath().split("/");
						for (int i = 0; i < split.length; i++) {
							if(i == split.length-1){
								str=split[i].toString();
							}
						}
					}
				}
				buffer.append("<IF_INVOKE_NAME>" + str + "</IF_INVOKE_NAME>");// 调用发起方信息 
				buffer.append("<IF_INVOKE_IP>" + operLog.getClientIp() + "</IF_INVOKE_IP>");// 调用发起方IP
				buffer.append("<IF_NAME>" + str + "</IF_NAME>");// 被调用接口名称
				buffer.append("<IF_IP>" + operLog.getClientIp() + "</IF_IP>");// 被调用接口的IP
				buffer.append("<AUTHEN_RESULT>2</AUTHEN_RESULT>");// 调用过程认证结果 0:认证通过；1：认证不通过；2：无认证
				buffer.append("<IF_INPUT_ARGS>" + operLog.getInputArgs() + "</IF_INPUT_ARGS>");// 输入参数
				buffer.append("<IF_OUTPUT_ARGS>" + operLog.getOutputArgs() + "</IF_OUTPUT_ARGS>");// 输出参数
				if (operLog.getOperateResult().equals("1")) {
					buffer.append("<IF_INVOKE_RESULT>0</IF_INVOKE_RESULT>");// 调用成功与否 0：成功；1：不成功
				} else {
					buffer.append("<IF_INVOKE_RESULT>1</IF_INVOKE_RESULT>");// 调用成功与否 0：成功；1：不成功
				}
				buffer.append("<IF_INVOKE_SOURCE>" + PropertiesUtil.getValue("serviceId") + "</IF_INVOKE_SOURCE>");// 接口调用端业务系统标识字段
				buffer.append("<IF_BEINVOKE_SOURCE>" + PropertiesUtil.getValue("serviceId") + "</IF_BEINVOKE_SOURCE>");// 接口被调用端业务系统标识字段
				buffer.append("</LOG4A>");
			}
		}
		buffer.append("</ROOT>");
		return buffer.toString();
	}

	private void ininTime(int num) throws Exception {// 初始化时间
		Map<String, String> map = getDateSection(num);
		for (Entry<String, String> set : map.entrySet()) {

			Date startTime = sdfs.parse(set.getKey());
			Date endTime = sdfs.parse(set.getValue());
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("startTime", startTime);
			paramsMap.put("endTime", endTime);
			List<TaskTimeEntity> taskList = taskTimeService.findByMap(paramsMap);
			if (taskList.size() == 0) {// 如果没有这条记录就Insert
				TaskTimeEntity ttEntity = new TaskTimeEntity();
				ttEntity.setStartTime(sdfs.format(startTime));
				ttEntity.setEndTime(sdfs.format(endTime));
				ttEntity.setStatus("0");
				try {
					taskTimeService.insert(ttEntity);
				} catch (Exception e) {
					logger.info(e.toString());
				}
			}
		}
		// 将过期的日期删除,当前日期的前一天的 0点前算过期时间
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date());
		cd.add(Calendar.DATE, -1);
		String time = new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime()) + " 00:00:00";
		Date overTime = sdfs.parse(time);
		TaskTimeEntity entity = new TaskTimeEntity();
		entity.setStartTime(sdfs.format(overTime));
		taskTimeService.delete(entity);
	}

	private boolean createFileNameAndUpload(String date, String xml, String code) {
		// 将字符串转换成document操纵dom树
		try {
			Document document = DocumentHelper.parseText(xml);
			// 判断文件夹,不存在则创建
			File files = new File(monitorDir);
			if (!files.isDirectory()) {
				files.mkdirs();
			}
			String fileName;
			fileName = code + new SimpleDateFormat("yyyyMMddHHmmss").format(sdfs.parse(date)) + ".xml";
			String filePath = monitorDir + fileName;
			File file = new File(filePath);
			writerOutFile(document, file);// 将文件写入本地工程目录
			logger.info("createFile  succcess fileName = " + fileName);
			boolean bool = sftpUts.UploadFile(PropertiesUtil.getValue("sftp.path"), fileName, file.getPath());
			if (bool) {
				logger.info("上传日志文件文件到4a sftp success" + fileName);
			} else {
				logger.info("上传日志文件文件到4a sftp fail" + fileName);
			}
			return bool;
		} catch (Exception e) {
			logger.error("createFile  fail:" + e.toString());
			return false;
		}
	}

	// 写
	private void writerOutFile(Document document, File file) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer = null;
		// 直接写入本地
		try {
			writer = new XMLWriter(new FileWriter(file), format);
			writer.write(document);
		} catch (IOException e) {
			logger.error("写文件 失败 " + e.toString());
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("写文件 失败 " + e.toString());
			}
		}
	}

	private String generateXmlLog(String startTimeStr, String endTimeStr) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", sdfs.parse(startTimeStr));
		map.put("endTime", sdfs.parse(endTimeStr));
		List<SysLogEntity> list = sysLogService.findByMap(map);
		StringBuilder buffer = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buffer.append("<ROOT>");
		if (list.size() > 0) {
			for (SysLogEntity operLog : list) {
				buffer.append("<LOG4A>");
				buffer.append("<UNIQUE_ID>"+PropertiesUtil.getValue("serviceId")+"_"+operLog.getId()+"</UNIQUE_ID>");// 日志唯一id
				buffer.append("<SESSION_ID>"+operLog.getSessionId()+"</SESSION_ID>");// 会话ID,关联接口日志的标识
				buffer.append("<RESOURCE_KIND>1</RESOURCE_KIND>");// 资源类别
				buffer.append("<IDR_CREATION_TIME>" + sdfs.format(new Date()) + "</IDR_CREATION_TIME>");// 日志发送时间
				buffer.append("<IDENTITY_NAME>" + PropertiesUtil.getValue("identityName") + "</IDENTITY_NAME>");// 实体名
				buffer.append("<RESOURCE_CODE>" + PropertiesUtil.getValue("serviceId") + "</RESOURCE_CODE>");// 资源编码
				buffer.append("<MAIN_ACCOUNT_NAME></MAIN_ACCOUNT_NAME>");// 主账号名称
				buffer.append("<SUB_ACCOUNT_NAME>" + operLog.getOperator() + "</SUB_ACCOUNT_NAME>");// 从账号
				buffer.append("<OPERATE_TIME>" + sdfs.format(operLog.getOperateTime()) + "</OPERATE_TIME>");
				buffer.append("<OP_TYPE_ID>1-" + PropertiesUtil.getValue("serviceId") + "-"
						+ OperLogEnum.getNameByCode(operLog.getOperateFunc() + operLog.getOperateType())
						+ "</OP_TYPE_ID>");// 操作编码
				buffer.append("<OP_TYPE_NAME>" + operLog.getOperateType() + "</OP_TYPE_NAME>");// 操作类型
				buffer.append("<OP_LEVEL_ID>1</OP_LEVEL_ID>");// 操作级别
				String operateResult = "";
				if (operLog.getOperateResult().equals("1")) {
					operateResult = "成功";
					buffer.append("<OPERATE_RESULT>" + "0" + "</OPERATE_RESULT>");// 操作结果
				} else {
					operateResult = "失败";
					buffer.append("<OPERATE_RESULT>" + "1" + "</OPERATE_RESULT>");// 操作结果
				}
				buffer.append("<OPERATE_CONTENT>" + operLog.getOperator() + "," + sdfs.format(operLog.getOperateTime())
						+ "," + operLog.getOperateFunc() + operLog.getOperateType() + "," + operateResult
						+ "</OPERATE_CONTENT>");// 操作内容
				buffer.append("<OBJECT_ID>2</OBJECT_ID>");// 对象类型ID
				buffer.append("<OBJECT_NAME>库表</OBJECT_NAME>");// 对象名称
				buffer.append("<CLIENT_ADDRESS>" + operLog.getClientIp() + "</CLIENT_ADDRESS>");// 客户端IP地址
				buffer.append("<SERVER_ADDRESS>" + PropertiesUtil.getValue("foura.log.ip") + "</SERVER_ADDRESS>");// 目的IP
				
				//buffer.append("<DB_INFO_CODE>PAAS4_DB</DB_INFO_CODE>");// 库
				//buffer.append("<SQL_TABLES></SQL_TABLES>");// 表
				//buffer.append("<SQL_WHERE_CONDITIONS></SQL_WHERE_CONDITIONS>");// sql语句
				
				buffer.append("<CLIENT_PORT>" + PropertiesUtil.getValue("clientPort") + "</CLIENT_PORT>");// 客户端端口 paas平台
				buffer.append("<SERVER_PORT>" + PropertiesUtil.getValue("serverPort") + "</SERVER_PORT>");// 服务器端端口 4a平台
//				String config = PropertiesUtil.getValue("logging.operateFunc");
//				int MODULE_ID=0;
//				if (Strings.isNotBlank(config)) {
//					String [] strs = config.split(";");
//					for (int i = 0; i < strs.length; i++) {
//						String[] str = strs[i].split(":");
//						while (str[0].toString().equals(operLog.getOperateFunc())) {
//							MODULE_ID=i+1;
//							break;
//						}
//					}
//				}
//				buffer.append("<MODULE_ID>"+MODULE_ID+"</MODULE_ID>");// 模块ID
				buffer.append("<MODULE_ID>"+operLog.getOperateFunc() +"</MODULE_ID>");// 模块ID
				buffer.append("<MODULE_NAME>" + operLog.getOperateFunc() + "</MODULE_NAME>");// 模块名称
				buffer.append("<BANKAPPROVE>" + "" + "</BANKAPPROVE>");// 金库业务标识
				buffer.append("<BANKFLAG>" + "" + "</BANKFLAG>");// 金库验证过程标识
				buffer.append("</LOG4A>");
			}
		}
		buffer.append("</ROOT>");
		return buffer.toString();
	}

	// 获取时间段，每间隔一次(根据配置项生成15mi 30mi 60mi)
	private Map<String, String> getDateSection(int num) {
		Map<String, String> map = new TreeMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String toDayStr = sdf.format(new Date());
		toDayStr = toDayStr + "000000";
		if (num > 96) {
			Calendar cd = Calendar.getInstance();
			cd.setTime(new Date());
			cd.add(Calendar.DATE, -1);
			Date lastMonth = cd.getTime();
			toDayStr = sdf.format(lastMonth);
			toDayStr = toDayStr + "000000";// 每天的0点
		}
		try {
			Date toDay = new SimpleDateFormat("yyyyMMddHHmmss").parse(toDayStr);
			for (int i = 0; i < num; i++) {
				Calendar c = Calendar.getInstance();
				c.setTime(toDay); // 设置时间)
				c.add(Calendar.MINUTE, Integer.parseInt(uploadTime)); // 日期分钟按照配置加
				Date curDate = toDay;
				Date nextDate = c.getTime();// 结果
				map.put(sdfs.format(curDate), sdfs.format(nextDate));
				toDay = nextDate;
			}
		} catch (ParseException e) {
		}
		return map;
	};
}
