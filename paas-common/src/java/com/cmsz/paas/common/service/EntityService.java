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

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;

public interface EntityService<E, ID extends Serializable> {
    
    public int insert(E entity);
    
    public int update(E entity);
    
    public int delete(E... entity);
    
    public int deleteById(ID... ids);
    
    public E findById(ID id);
    
    public Page<E> findPage(PageContext pageContext);
    
    public List<E> findBy(Map<?, ?> map);
    
    public List<E> findAll();
    
    public Class<E> getEntityClass();
}
