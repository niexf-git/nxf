package com.cmsz.paas.web.log.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmsz.paas.common.spring.Springs;
import com.cmsz.paas.web.base.action.GetHttpSessionConfigurator;
import com.cmsz.paas.web.base.util.DateUtil;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.log.service.StdLogService;

@ServerEndpoint(value = "/websocket/stdLog",configurator=GetHttpSessionConfigurator.class)
public class StdLogWebSocket {
	
	private static final Logger logger = LoggerFactory.getLogger(StdLogWebSocket.class);
	
	private StdLogService stdLogService = Springs.getBean("stdLogService");
	
	/** 记录日志行数 */
	private int count;
	
	/** 页面Session */
	private HttpSession httpSession;
	
	/** 登录用户名 */
	private String userLoginName ;
	
	@OnMessage(maxMessageSize=600000)
    public void onMessage(String message, Session session) 
    	throws IOException, InterruptedException, ParseException {
		logger.info("接收到的字符串："+message);
		String serviceId ="";
		String instanceId ="";
		String hostIp = "";
		String intType = "";
		long time = 0;
		try{
			if (StringUtils.isNotBlank(message)) {
				String lastTime = "";//上一次暂停时的时间
				String[] split = message.split("_");
				////第一次建立连接
				if("start".equals(split[0])){
					Date date = DateUtil.toDate(split[1], "yyyy-MM-dd HH:mm:ss");
					time = date.getTime()/1000;
				}else{//暂停后恢复连接
					count = Integer.parseInt(split[0]);
					lastTime = split[1];
					String date = lastTime.substring(0, lastTime.lastIndexOf("."));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					format.setTimeZone(TimeZone.getTimeZone( "UTC"));
					time = format.parse(date).getTime()/1000;
				}
				serviceId = split[2];
				instanceId = split[3];
			    hostIp = split[4];
			    intType = split[5];
				//调用rest接口，拿到url
				String url = stdLogService.queryAppServiceStdLogUrl(serviceId, instanceId,intType, hostIp, time+"")+"&follow=true";
				//读取日志数据,并发送到页面
				readStreamData(url,lastTime,session);
			} else {
				session.getBasicRemote().sendText("接收的参数为空 ！");
			}
		} catch (Exception e) {
			logger.error("标准输出日志长连接错误", e);
			this.onClose();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 读取日志数据
	 * @param url
	 * @param lastTime
	 * @param session
	 * @throws Exception
	 */
	public void readStreamData(String url, String lastTime, Session session) throws Exception{
		BufferedReader br = null;
		try {
			URL httpUrl = new URL(url);
			InputStream inputStream = httpUrl.openStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			br = new BufferedReader(isr);
			String line = null;
			int fixCount = Integer.parseInt(PropertiesUtil.getValue("stdlog.line"));
			
			//新启一个线程发送数据
			String intervalTime = PropertiesUtil.getValue("stdlog.time");
			SendDataTimerTask task = new SendDataTimerTask(session);
        	Timer timer = new Timer();
    		timer.schedule(task, 0, Integer.parseInt(intervalTime));
    		
			//第一次加载日志（页面没有日志输出时）
			if("".equals(lastTime)){
				while((line = br.readLine()) != null){
					count++;
					//处理每行前面的乱码
		        	if(line.length() > 8){
		        		line = line.substring(8);
		        	}
		        	task.setData(count,line);
		        	if(count % fixCount == 0){
		        		task.sendData();
		        	}
		        	
		        	if(task.isException()){
		        		timer.cancel();
		        		task.setException(false);
		        		logger.info("timer close!");
		        		break;
		        	}
				}
			}else{
				boolean isSendMsg = false;
				while((line = br.readLine()) != null){
					if(isSendMsg){
						count++;
						if(line.length() > 8){
			        		line = line.substring(8);
						}
						task.setData(count,line);
						if(count % fixCount == 0){
			        		task.sendData();
			        	}
						
						if(task.isException()){
			        		timer.cancel();
			        		task.setException(false);
			        		logger.info("timer close!");
			        		break;
			        	}
					}
					if(line.contains(lastTime)){
						isSendMsg = true;
					}
				}
			}
		} finally{
			if(br != null){
				br.close();
			}
		}
	}
	
	
	/**
	 * On open.
	 */
	@OnOpen
	public void onOpen (Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        this.userLoginName =(String) httpSession.getAttribute("loginName");       
        System.out.println("Websocket Client connected by "+userLoginName);
	}

    /**
     * On close.
     */
    @OnClose
    public void onClose () {
    	System.out.println("Websocket Connection closed by "+userLoginName);
    }
    
}
