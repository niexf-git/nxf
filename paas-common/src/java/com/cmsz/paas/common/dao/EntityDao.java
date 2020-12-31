package com.cmsz.paas.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;


/**
 * 实体CRUD DAO接口
 * 
 * @author sam
 * 
 * @param <E>实体类型
 * @param <ID>实体ID类型
 */
public interface EntityDao<E, ID extends Serializable> {
    
    /**
     * 新增一个实体
     * 
     * @param entity
     */
    public int insert(E entity);
    
    /**
     * 批量插入
     * @param entity
     * @return
     */
    public int insert(List<E> entity);
    
    /**
     * 根据ID和参数
     */
    public int update(String sqlId,E entity);
    
    /**
     * 更新一个实体
     * 
     * @param entity
     */
    public int update(E entity);
    
    /**
     * 删除一个或多个实体
     * 
     * @param entities
     */
    public int delete(E... entities);
    
    /**
     * 删除一个或多个实体
     * 
     * @param ids 实体ID集
     */
    public int deleteById(ID... ids);
    
    /**
     * 根据实体ID查找实体
     * 
     * @param id
     * @return
     */
    public E findById(ID id);
    
    /**
     * 分页查询
     * 
     * @param fp 分页请求参数
     * @return
     */
    public Page<E> findPage(PageContext fp);
    
    /**
     * 查找所有实体
     * 
     * @return
     */
    public List<E> findAll();
    
    /**
     * 根据分页请求参数查询实体集
     * 
     * @param fp
     * @return
     */
    public List<E> findBy(PageContext fp);
    
    /**
     * 根据Map参数查询实体
     * 
     * @param parameter
     * @return
     */
    public List<E> findBy(Map<?, ?> parameter);
    
    /**
     * 根据SQLID和参数map查询实体列表
     * @param sqlId
     * @param parameter
     * @return
     */
    public List<E> findBy(String sqlId,Map<?, ?> parameter);
    
    
    
}
