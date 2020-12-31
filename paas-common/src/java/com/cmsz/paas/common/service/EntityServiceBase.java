package com.cmsz.paas.common.service;

/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.dao.EntityDao;
import com.cmsz.paas.common.lang.reflect.Reflections;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;

public abstract class EntityServiceBase<E, ID extends Serializable> implements EntityService<E, ID> {
    
    public abstract EntityDao<E, ID> getEntityDao();
    
    protected Class<E> entityClass;
    
    public EntityServiceBase() {

        this.entityClass = Reflections.getSuperClassGenricType(getClass());
    }
    
    public int delete(E... entities) {

        return getEntityDao().delete(entities);
    }
    
    public int deleteById(ID... ids) {

        return getEntityDao().deleteById(ids);
    }
    
    public List<E> findAll() {

        return getEntityDao().findAll();
    }
    
    public List<E> findBy(Map<?, ?> map) {

        return getEntityDao().findBy(map);
    }
    
    public E findById(ID id) {

        return getEntityDao().findById(id);
    }
    
    public Page<E> findPage(PageContext pageContext) {

        return getEntityDao().findPage(pageContext);
    }
    
    public Class<E> getEntityClass() {

        return entityClass;
    }
    
    public int insert(E entity) {

        return getEntityDao().insert(entity);
    }
    
    public int update(E entity) {

        return getEntityDao().update(entity);
    }
    
}
