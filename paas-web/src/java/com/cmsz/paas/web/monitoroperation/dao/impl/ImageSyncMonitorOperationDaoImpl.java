package com.cmsz.paas.web.monitoroperation.dao.impl;

import org.springframework.stereotype.Service;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoMonitorOperationImpl;
import com.cmsz.paas.web.monitoroperation.dao.ImageSyncMonitorOperationDao;

/**
 * 为加载公共镜像功能而添加的，
 * 调用监控运维的接口实现
 * @author lin.my
 * @version 创建时间：2016年12月22日 上午10:53:23
 */
@Service("monitorOperationDao")
public class ImageSyncMonitorOperationDaoImpl extends ResponseInfoRestDaoMonitorOperationImpl implements ImageSyncMonitorOperationDao {

}
