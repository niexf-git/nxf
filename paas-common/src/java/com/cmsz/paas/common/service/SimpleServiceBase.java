/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.cmsz.paas.common.lang.reflect.Reflections;
import com.cmsz.paas.common.ibatis3.IBatis3Dao;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;

/**
 * 简单服务类，直接使用Ibatis3Dao简化不必要的dao类
 * 
 * @author sam
 * 
 * @param <E> 实体类
 * @param <ID>实体类的主键类
 */
public abstract class SimpleServiceBase<E, ID extends Serializable> implements
        EntityService<E, ID>, InitializingBean {
    
    protected IBatis3Dao<E, ID> ibatisDao;
    protected Class<E> entityClass;
    
    public SimpleServiceBase() {

        this.entityClass = Reflections.getSuperClassGenricType(getClass());
    }
    
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {

        this.ibatisDao = new IBatis3Dao<E, ID>(sqlSessionFactory, getEntityClass());
    }
    
    public int delete(E... entities) {

        return ibatisDao.delete(entities);
    }
    
    public int deleteById(ID... ids) {

        return ibatisDao.deleteById(ids);
    }
    
    public List<E> findAll() {

        return ibatisDao.findAll();
    }
    
    public List<E> findBy(Map<?, ?> map) {

        return ibatisDao.findBy(map);
    }
    
    public E findById(ID id) {

        return ibatisDao.findById(id);
    }
    
    public Page<E> findPage(PageContext pageContext) {

        return ibatisDao.findPage(pageContext);
    }
    
    public Class<E> getEntityClass() {

        return entityClass;
    }
    
    public int insert(E entity) {

        return ibatisDao.insert(entity);
    }
    
    public int update(E entity) {

        return ibatisDao.update(entity);
    }
    
    public final void afterPropertiesSet() throws Exception {

        Assert.notNull(ibatisDao.getSqlSessionFactory(), "sqlSessionFactory can not be null!");
    }
}
