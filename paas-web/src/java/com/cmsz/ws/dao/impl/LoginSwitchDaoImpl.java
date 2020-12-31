package com.cmsz.ws.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.dao.IBatis3DaoSupport;
import com.cmsz.vo.IdentifySwitch;
import com.cmsz.ws.dao.LoginSwitchDao;

/** 
 * @author 林绵炎
 * @version 创建时间：2016年11月24日 上午9:33:38
 */
@Repository("loginSwitchDao")
public class LoginSwitchDaoImpl extends IBatis3DaoSupport<IdentifySwitch, Long> implements LoginSwitchDao {

}
