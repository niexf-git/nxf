package com.cmsz.ws;

import com.cmsz.vo.ResultVO;

/**
 * 应用系统认证切换开关接口
 * 
 * 输入参数为：flag，开关标志为0表示4a认证功能开启，用户将通过4a认证登录应用。
 * Flag值置为 1表示4a认证功能关闭，用户采用应用本地认证方式登录。
 * 
 * @author 林绵炎
 * @version 创建时间：2016年11月21日 下午3:06:56
 */
public interface LoginSwitchService {

	public ResultVO loginModChg(String flag);
	
	public String getSwitchValue();

}