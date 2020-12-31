package com.cmsz.paas.common.service;



import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.cmsz.paas.common.ibatis3.IBatis3Dao;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;

public class DefaultEntityFinder implements EntityFinder {
    
    protected IBatis3Dao ibatisDao;
    
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {

        this.ibatisDao = new IBatis3Dao();
        this.ibatisDao.setSqlSessionFactory(sqlSessionFactory);
    }
    
    @Override
    public List findList(String statementId, Object parameter) {

        return ibatisDao.getSqlSessionTemplate().selectList(statementId, parameter);
    }
    
    @Override
    public Object findOne(String statementId, Object parameter) {

        return ibatisDao.getSqlSessionTemplate().selectOne(statementId, parameter);
    }
    
    @Override
    public List findList(String statementId) {

        return ibatisDao.getSqlSessionTemplate().selectList(statementId);
    }
    
    @Override
    public Object findOne(String statementId) {

        return ibatisDao.getSqlSessionTemplate().selectOne(statementId);
    }
    
    @Override
    public <T> Page<T> findPage(String statementId, PageContext pageContext, Class<T> entityClass) {

        return ibatisDao.findPage(statementId, pageContext, entityClass);
    }
    
}
