package com.cmsz.paas.web.log.action;

import java.util.TimerTask;

import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 定时发送长连接数据
 * 
 * @author liaohw
 * @date 2016-4-29
 */
public class SendDataTimerTask extends TimerTask {

	// 长连接发送数据Session
	private Session session;

	// 最后一行日志
	private String lastLine;

	// 存储日志记录
	private StringBuffer logData = new StringBuffer();

	private boolean isException;

	public SendDataTimerTask(Session session) {
		super();
		this.session = session;
	}

	@Override
	public void run() {
		this.sendData();
	}

	/**
	 * 设置数据
	 * 
	 * @param lastLine
	 * @param logData
	 */
	public synchronized void setData(int count, String line) {
		lastLine = count + " " + line;
		logData.append(count).append(" ").append(line).append("<br>");
	}

	/**
	 * 长连接发送数据给页面
	 */
	public synchronized void sendData() {
		try {
			if (logData != null && logData.length() > 0) {
				session.getBasicRemote().sendText(
						changeToJsonStr(lastLine, logData.toString()));
				logData.setLength(0); // 清空数据
			}
		} catch (Exception e) {
			isException = true;
		}
	}

	private String changeToJsonStr(String lastLine, String logData)
			throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("resultCode", lastLine);
		obj.put("resultMessage", logData);
		return obj.toString();
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

}
