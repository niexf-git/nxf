package com.cmsz.ws.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.vo.IdentifySwitch;
import com.cmsz.vo.ResultVO;
import com.cmsz.ws.LoginSwitchService;
import com.cmsz.ws.dao.LoginSwitchDao;

/**
 * 应用系统认证切换开关接口实现
 * 
 * @author 林绵炎
 * @version 创建时间：2016年11月21日 下午3:12:15
 */
@Service("loginSwitchService")
public class LoginSwitchServiceImpl implements LoginSwitchService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginSwitchServiceImpl.class);

	@Autowired
	private LoginSwitchDao loginSwitchDao;
	
	/**
	 * 切换认证开关
	 * 
	 * 输入参数为：flag，开关标志为0表示4a认证功能开启，用户将通过4a认证登录应用。
	 * flag值为 1表示4a认证功能关闭，用户采用应用本地认证方式登录。
	 */
	public ResultVO loginModChg(String flag) {
		
		logger.info("切换认证开关，开关标志：" + flag);
		
		ResultVO result = new ResultVO();
		
		String sv = "-1";
		String switchValue = "2";
		
		try {
			if (flag == null || flag == "" || flag.equals("")) {
				result.setResult("1");
				result.setResultDesc("传入的开关值非法,只允许0或者1");
				return result;
			}
			
			switchValue = flag;
			if (!switchValue.equals("0")) {
				if (!switchValue.equals("1")) {
					result.setResult("1");
					result.setResultDesc("传入的开关值非法,只允许0或者1");
					return result;
				}
			}
			
			// 状态（0表示有效、1表示无效）
			Map<String, String> map = new HashMap<String, String>();
			map.put("state", "0");
			List<IdentifySwitch> list = loginSwitchDao.findBy(map);
			for(IdentifySwitch is : list){
				sv = is.getSwitchValue()+""; //取到状态为有效的开关值
				
				if (sv.equals(switchValue)) {
					// 如果传入的开关参数与此记录的开关值一致
					result.setResult("1");
					result.setResultDesc("目前已经是此开关值,不必设置");
				} else if (sv.equals("-1")) {
					// 数据库中无状态为0的值
					result.setResult("1");
					result.setResultDesc("数据库中无状态为有效的数据,请联系系统管理员");
				} else {
					// 如果传入的开关参数与此记录的开关值不一致
					IdentifySwitch vo = new IdentifySwitch();
					vo.setId(is.getId());
					vo.setSwitchValue(is.getSwitchValue());
					// 将此条记录状态值修改为无效
					vo.setState(1);
					vo.setCreateDate(is.getCreateDate());
					loginSwitchDao.update(vo);
					
					// 新插入一条状态为有效的数据
					IdentifySwitch isvo = new IdentifySwitch();
					isvo.setSwitchValue(Integer.parseInt(switchValue));
					isvo.setState(0);
					loginSwitchDao.insert(isvo);
					
					// 处理结果
					result.setResult("0");
					result.setResultDesc("操作成功");
					logger.info("切换认证开关，操作成功");
				}
			}
		} catch (Exception e) {
			result.setResult("1");
			result.setResultDesc(e.getMessage());
			logger.error("切换认证开关，操作异常：" + e);
		}
		return result;
	}
	
	/*
	 * 获取认证开关
	 */
	public String getSwitchValue(){
		String switchValue = "";
		try {
			// 状态（0表示有效、1表示无效）
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("state", 0);
			List<IdentifySwitch> list = loginSwitchDao.findBy(map);
			
			for(IdentifySwitch is : list){
				switchValue = is.getSwitchValue()+""; //取到状态为有效的开关值
			}
			logger.info("获取认证开关值(0代表从4A平台登录，1代表从原来的方式登录)：" + switchValue);
		} catch (Exception e) {
			logger.error("获取认证开关值，操作异常：" + e);
		}
		return switchValue;
	}

}
