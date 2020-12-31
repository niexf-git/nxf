package com.cmsz.paas.web.base.util;

import java.net.InetAddress;
import java.net.URLDecoder;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

import com.cmsz.paas.web.base.log.MyOperationLog;

public class syslog4jUtil {
	public static void sendSyslog4(MyOperationLog mlog,Object secondary_user,Object primary_user) {
		try {
			// 获取syslog的操作类，使用udp协议。syslog支持"udp", "tcp", "unix_syslog",
			// "unix_socket"协议
			SyslogIF syslog = Syslog.getInstance(PropertiesUtil
					.getValue("syslogAgreement"));
			// 设置syslog服务器端地址
			syslog.getConfig().setHost(PropertiesUtil.getValue("syslogIp"));
			// 设置syslog接收端口，默认514
			syslog.getConfig().setPort(
					Integer.parseInt(PropertiesUtil.getValue("syslogPort")));
			// 拼接syslog日志
			StringBuffer buffer = new StringBuffer();
			String classs = "PAAS";
			String address = "";
			InetAddress ia=null;
	        try {
	            ia=ia.getLocalHost();	             
	            address=ia.getHostAddress();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			buffer.append("class=\"" + classs + "\" ");
			buffer.append("type=\"" + "3" + "\" ");
			buffer.append("time=\"" + mlog.getOperateTime() + "\" ");
			buffer.append("src_ip=\"" + mlog.getClientIp() + "\" ");
			buffer.append("dst_ip=\"" + address + "\" ");
			buffer.append("src_port=\"\" ");
			buffer.append("dst_port=\"\" ");
			buffer.append("src_mac=\"\" ");
			buffer.append("dst_mac=\"\" ");
			buffer.append("protocol=\"" + "HTTP" + "\" ");
			buffer.append("start_time=\"" + mlog.getOperateTime() + "\" ");
			buffer.append("end_time=\"" + mlog.getOperateTime() + "\" ");
			buffer.append("primary_user=\"" + primary_user + "\" ");
			buffer.append("secondary_user=\"" + secondary_user + "\" ");
			buffer.append("operation=\"" + mlog.getOperateType() + "\" ");
			buffer.append("content=\"" + mlog.getFunc() + "\" ");
			buffer.append("dev_ip=\"" + address + "\" ");
			buffer.append("dev_port=\"\" ");
			buffer.append("dev_mac=\"\" ");
			buffer.append("authen_status=\"\" ");
			if ("查询".equals(mlog.getOperateType())) {
				buffer.append("log_level=\"" + "1" + "\" ");
			} else {
				buffer.append("log_level=\"" + "2" + "\" ");
			}
			buffer.append("session_id=\"\" ");
			buffer.append("param_len=\"\" ");
			buffer.append("param=\"\" ");

			syslog.log(0, URLDecoder.decode(buffer.toString(), "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
