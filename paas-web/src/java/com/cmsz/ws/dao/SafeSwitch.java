package com.cmsz.ws.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.util.JasypUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;

/** 
 * 获取认证开关
 * @author lin.my
 * @version 创建时间：2017年4月7日 下午2:24:44
 */
@UnLogging
public class SafeSwitch {
	
	/** 打印日志对象. */
	private static final Logger logger = LoggerFactory.getLogger(SafeSwitch.class);

	private static String URL = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String DIRVER = "";
	
	static{
		URL = PropertiesUtil.getValue("db.url");
		USERNAME = PropertiesUtil.getValue("db.username");
		PASSWORD = JasypUtil.decrypt(PropertiesUtil.getValue("db.password"));
		DIRVER = PropertiesUtil.getValue("db.driver");
		try {
			Class.forName(DIRVER);
		} catch (ClassNotFoundException e) {
			logger.error("无法找到指定的类异常", e);
		}
	}
	
	public static int getSwitchValue() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		int switchValue = -1;
		try {
			sql = "select switch_value as sv from identify_switch where state=0";
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				switchValue = rs.getInt("sv");
			} else {
				// 默认从PAAS系统登录
				switchValue = 1;
			}
			logger.info("4A认证开关值（0表示4A认证开启，1表示4A认证关闭）：" + switchValue);
		} catch (Exception e) {
			logger.error("获取认证开关值失败", e);
		} finally {
			try {
				if(rs != null){
					rs.close();
					rs = null;
				}
				if(pstmt != null){
					pstmt.close();
					pstmt = null;
				}
				if(conn != null && !conn.isClosed()){
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				logger.error("关闭资源失败", ex);
			}
		}
		return switchValue;
	}
	
	/**
	 * 获取数据库链接
	 * @return
	 * @throws SQLException 
	 */
	private static Connection getConn() throws SQLException{
		
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
}
