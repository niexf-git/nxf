package com.cmsz.paas.web.cicd.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.cicd.dao.CodeDetailsDao;
/**
 * 代码详情dao实现
 * @author ccl
 * 
 */
@Repository("codeDetailsDao")
public class CodeDetailsDaoImpl extends ResponseInfoRestDaoImpl implements CodeDetailsDao {

}
