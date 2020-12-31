package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.DeploymentDao;

/***
 * 部署Dao实现类
 * @author jiangwei
 *
 */
@Repository("deploymentDao")
public class DeploymentDaoImpl  extends ResponseInfoRestDaoImpl implements DeploymentDao{

}
