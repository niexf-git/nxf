package com.cmsz.paas.common.service;



import java.util.List;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;

/**
 * 实体查找器，可以直接在Action引用此类进行查询，避免有些查询模块还要开发service,dao层，简化开发
 * 
 * @author Sam
 * 
 */
public interface EntityFinder {
    
    public Object findOne(String statementId, Object parameter);
    
    public Object findOne(String statementId);
    
    public List findList(String statementId, Object parameter);
    
    public List findList(String statementId);
    
    public <T> Page<T> findPage(String statementId, PageContext pageContext, Class<T> entityClass);
}
