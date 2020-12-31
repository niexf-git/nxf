package com.cmsz.paas.web.image.dao.impl;

import org.springframework.stereotype.Service;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.image.dao.PrivateImageDao;
import com.cmsz.paas.web.image.dao.PublicImageDao;

/**
 * @author lin.my 2016-4-18
 */
@Service("publicImageDao")
public class PublicImageDaoImpl extends ResponseInfoRestDaoImpl implements PublicImageDao {

}
