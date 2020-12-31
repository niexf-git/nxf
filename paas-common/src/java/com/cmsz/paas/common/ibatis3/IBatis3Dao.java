/**
 * 淇敼鍘嗗彶锛�<br/>
 * =================================================================<br/>
 * 淇敼浜�? 淇敼鏃堕棿 淇敼鍘熷洜/鍐呭�?<br/>
 * =================================================================<br/>
 * sam 20100111 澧炲姞淇敼鍘嗗彶娉ㄩ噴<br/>
 */

package com.cmsz.paas.common.ibatis3;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.cmsz.paas.common.page.OffsetLimitInterceptor.BoundSqlSqlSource;
import com.cmsz.paas.common.lang.collection.Lists;
import com.cmsz.paas.common.lang.reflect.ClassWrapper;
import com.cmsz.paas.common.lang.reflect.Reflections;
import com.cmsz.paas.common.orm.id.IdHelper;
import com.cmsz.paas.common.page.Bound;
import com.cmsz.paas.common.page.Page;
import com.cmsz.paas.common.page.PageContext;
import com.cmsz.paas.common.page.Paginations;
import com.cmsz.paas.common.page.OffsetLimitInterceptor.BoundSqlSqlSource;

/**
 * 基于ibatis3的数据访问类，不继承DaoSupport，可直接在Service层或者其他Dao类直接用此类，不需要继承此类
 * <p>
 * 用法一：<br>
 * 
 * <pre>
 * private IBatis31Dao IBatis31Dao = new IBatis31Dao<MyEntityType,MyEntityIdType>(sqlSessionFactory,myentityClass); 
 * </pre>
 * </p>
 * <p>
 * 用法二:<br>
 * 
 * <pre>
 * private IBatis31Dao IBatis31Dao = new IBatis31Dao<MyEntityType,MyEntityIdType>();
 * IBatis31Dao.setSqlSessionFactory(sqlSessionFactory);
 * </pre>
 * </p>
 * 
 * @author Sam
 * 
 * @param <E>操作的实体类型
 * @param <ID>实体ID类型
 */
public class IBatis3Dao<E, ID> {
    
    protected SqlSessionTemplate sqlSessionTemplate;
    protected SqlSessionFactory sqlSessionFactory;
    protected Class<E> entityClass;
    
    public IBatis3Dao() {

        this.entityClass = Reflections.getSuperClassGenricType(getClass());
    }
    
    public IBatis3Dao(final SqlSessionFactory sqlSessionFactory, final Class<E> entityClass) {

        setSqlSessionFactory(sqlSessionFactory);
        this.entityClass = entityClass;
    }
    
    @SuppressWarnings("unchecked")
	public List<E> findBy(String sqlId,Map<?, ?> parameter){
    	 return getSqlSessionTemplate().selectList(sql(sqlId), parameter);
    }
     
    
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {

        this.sqlSessionFactory = sqlSessionFactory;
        this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    }
    
    public SqlSessionFactory getSqlSessionFactory() {

        return sqlSessionFactory;
    }
    
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {

        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    /**
     * 实体新增，会检测实体中是否有注解了@ID的Field有的话就会为根据设置注入ID值
     * 
     * @param entity
     */
    public int insert(E entity) {

        IdHelper.setId(entity);
        return getSqlSessionTemplate().insert(sql(INSERT), entity);
    }
    
    public int insert(String sqlId,E entity){
    	IdHelper.setId(entity);
    	return getSqlSessionTemplate().insert(sql(sqlId),entity);
    }
    
    public int insert(List<E> entity){
    	int i = 0;
    	SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		try {
			for (E e: entity){
				IdHelper.setId(e);
				session.insert(sql(INSERT), e);
				i ++;
			}
			session.commit();
			session.clearCache();
		} catch (Throwable ex) {
			session.rollback();
		} finally {
			if (session != null)
				session.close();
		}
    	return i;
    }
    
    public int update(E entity) {

        return getSqlSessionTemplate().update(sql(UPDATE), entity);
    }
    
    public int update(String sqlId,E entity) {

        return getSqlSessionTemplate().update(sql(sqlId), entity);
    }
    
    public int delete(E... entities) {

        int i = 0;
        for (E entity : entities) {
            getSqlSessionTemplate().delete(sql(DELETE), entity);
            i++;
        }
        return i;
    }
    
    public int deleteById(ID... ids) {

        int i = 0;
        for (ID id : ids) {
            getSqlSessionTemplate().delete(sql(DELETE_BY_ID), id);
            i++;
        }
        return i;
    }
    
    @SuppressWarnings("unchecked")
    public E findById(ID id) {

        return (E) getSqlSessionTemplate().selectOne(sql(FIND_BY_ID), id);
    }
    
    @SuppressWarnings("unchecked")
    public List<E> findBy(Map<?, ?> parameter) {

        return getSqlSessionTemplate().selectList(sql(FIND_BY_MAP), parameter);
    }
    
    
    @SuppressWarnings("unchecked")
    public List<E> findBy(E entity) {

        return getSqlSessionTemplate().selectList(sql(FIND_BY_ENTITY), entity);
    }
    
    public Page<E> findPage(PageContext parameter) {
        return pagingQuery(sql(FIND_Page), parameter);
    }
    
    @SuppressWarnings("unchecked")
    public List<E> findAll() {

        return getSqlSessionTemplate().selectList(sql(FIND_ALL));
    }
    
    /**
     * 分页查询，必须在Mapper文件定义 Count(*) SQL
     * 
     * @param <T>
     * @param countSqlId
     * @param sqlId
     * @param pageContext
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> Page<T> pagingQuery(String countSqlId, String sqlId, PageContext pageContext) {

        return (Page<T>) pagingQuery(countSqlId, sqlId, pageContext, entityClass);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> Page<T> pagingQuery(String countSqlId, String findSqlId, PageContext pageContext,
        Class<T> entityClass) {

        if (!pageContext.isUsePagination()) {
            List<T> allResult = getSqlSessionTemplate().selectList(findSqlId, pageContext);
            return Paginations.createPage(allResult.size(), null, allResult);
        }
        Long totalCount = (Long) getSqlSessionTemplate().selectOne(countSqlId, pageContext);
        Bound range = pageContext.getBound();
        if (totalCount == null || totalCount < 1) {
            return Paginations.createPage(0, range, new ArrayList<T>(0));
        }
        int offset = (int) range.getOffset();
        int limit = (int) range.getLimit();
        List<T> result = getSqlSessionTemplate().selectList(findSqlId, pageContext, offset, limit);
        return Paginations.createPage(totalCount, range, result);
    }
    
    /**
     * 分页查询,执行时会动态生成Count(*) sql
     * 
     * @param <T>
     * @param findSqlId
     * @param pageContext
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> Page<T> pagingQuery(String findSqlId, PageContext pageContext) {

        return (Page<T>) pagingQuery(findSqlId, pageContext, entityClass);
    }
    
    public <T> Page<T> findPage(String findSqlId, PageContext pageContext, Class<T> entityClass) {

        return pagingQuery(findSqlId, pageContext, entityClass);
    }
    
    @SuppressWarnings("unchecked")
    protected <T> Page<T> pagingQuery(String findSqlId, PageContext pageContext,
        Class<T> entityClass) {

        if (!pageContext.isUsePagination()) {
            List<T> allResult = getSqlSessionTemplate().selectList(findSqlId, pageContext);
            return Paginations.createPage(allResult.size(), null, allResult);
        }
        Long totalCount = dynamicCount(findSqlId, pageContext);
        Bound range = pageContext.getBound();
        if (totalCount == null || totalCount < 1) {
            return Paginations.createPage(0, range, new ArrayList<T>(0));
        }
        int offset = (int) range.getOffset();
        int limit = (int) range.getLimit();
        List<T> result = getSqlSessionTemplate().selectList(findSqlId, pageContext, offset, limit);
        return Paginations.createPage(totalCount, range, result);
    }
    
    /**
     * 根据指定的查询Sql动态生成count sql
     * 
     * @param sqlId
     * @param parameter
     * @return
     */
    public Long dynamicCount(String sqlId, Object parameter) {
    	try {
    	long currentTime = System.currentTimeMillis();
    	removeCountMappedStatement(currentTime);
    	createCountMappedStatement(sqlId,parameter,currentTime);
        Long count;
			count = (Long) getSqlSessionTemplate().selectOne(countMappedId(currentTime), parameter);
			removeCountMappedStatement(currentTime);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
    }
    
    public SqlSessionTemplate getSqlSessionTemplate() {

        return sqlSessionTemplate;
    }
    
    protected String sql(String sqlIdPrefix) {

        return entityClass.getSimpleName() + "." + sqlIdPrefix;
    }
    
    private String countMappedId(long currentTime) {
    	return currentTime + "-count";
    }
    
    @SuppressWarnings("unchecked")
	private void removeCountMappedStatement(long currentTime) {
    	 
		Configuration config = sqlSessionFactory.getConfiguration();
		ClassWrapper cw = ClassWrapper.wrap(config);   
		Field field = cw.getField("mappedStatements");		
		if (field != null) {
			Map<String, MappedStatement> mappedStatements = (Map<String, MappedStatement>) cw.getValue(config, field);
			mappedStatements.remove(countMappedId(currentTime));
		}
    	 
    }
    
    private void createCountMappedStatement(String sqlId,Object parameter,long currentTime) {
    	MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(sqlId);
    	BoundSql boundSql = ms.getBoundSql(parameter);
        String countSqlId = countMappedId(currentTime);
        String sql = "select count(*) from ( " + boundSql.getSql().trim()
                + " ) count_table___ ";
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql
                .getParameterMappings(), boundSql.getParameterObject());
        
        // 创建statement 的 return type，等同于在xml中定义:<select returnType="long"...../>
        ResultMap.Builder countResultBuilder = new ResultMap.Builder(ms.getConfiguration(),
                ms.getId() + "-Inline", Long.class, new ArrayList<ResultMapping>());
        List<ResultMap> resultMaps = Lists.of(countResultBuilder.build());
        
        // 构建statement
        Builder builder = new MappedStatement.Builder(ms.getConfiguration(), countSqlId,
                new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(ms.getKeyProperty());
        
        // setStatementTimeout()
        builder.timeout(ms.getTimeout());
        
        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());
        
        // setStatementResultMap()
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        
        /*
         * count(*) sql 不需要cache builder.cache(ms.getCache());
         * builder.flushCacheRequired(ms.isFlushCacheRequired());
         * builder.useCache(ms.isUseCache());
         */
        sqlSessionFactory.getConfiguration().addMappedStatement(builder.build());
  
    }
    
//    /**
//     * 根据某个<select> statement 动态生成对应的count(*) statement
//     * 
//     * @param msSqlId 要生成count(*) statement的 select statement id
//     * @param parameter 传给statement的参数
//     */
//    private void parpredCountMappedStatement(String msSqlId, Object parameter) {
//
//        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(msSqlId);
//        String countSqlId = convertMappedId(ms.getId()) + "_count";
//        MappedStatement countMs = null;
//        
//        try {
//            countMs = sqlSessionFactory.getConfiguration().getMappedStatement(countSqlId);
//        } catch (Exception ex) {
//            
//            // 防止并发重复创建count statement
//            lock.lock();
//            
//            try {
//                BoundSql boundSql = ms.getBoundSql(parameter);
//                
//                String sql = "select count(*) from ( " + boundSql.getSql().trim()
//                        + " ) count_table___ ";
//                BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql
//                        .getParameterMappings(), boundSql.getParameterObject());
//                
//                // 创建statement 的 return type，等同于在xml中定义:<select returnType="long"...../>
//                ResultMap.Builder countResultBuilder = new ResultMap.Builder(ms.getConfiguration(),
//                        ms.getId() + "-Inline", Long.class, new ArrayList<ResultMapping>());
//                List<ResultMap> resultMaps = Lists.of(countResultBuilder.build());
//                
//                // 构建statement
//                Builder builder = new MappedStatement.Builder(ms.getConfiguration(), countSqlId,
//                        new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType());
//                builder.resource(ms.getResource());
//                builder.fetchSize(ms.getFetchSize());
//                builder.statementType(ms.getStatementType());
//                builder.keyGenerator(ms.getKeyGenerator());
//                builder.keyProperty(ms.getKeyProperty());
//                
//                // setStatementTimeout()
//                builder.timeout(ms.getTimeout());
//                
//                // setStatementResultMap()
//                builder.parameterMap(ms.getParameterMap());
//                
//                // setStatementResultMap()
//                builder.resultMaps(resultMaps);
//                builder.resultSetType(ms.getResultSetType());
//                
//                /*
//                 * count(*) sql 不需要cache builder.cache(ms.getCache());
//                 * builder.flushCacheRequired(ms.isFlushCacheRequired());
//                 * builder.useCache(ms.isUseCache());
//                 */
//                countMs = builder.build();
//                sqlSessionFactory.getConfiguration().addMappedStatement(countMs);
//            } finally {
//                lock.unlock();
//            }
//        }
//    }
    
//    private static final ReentrantLock lock = new ReentrantLock();
    
    public final static String COUNT = "count";
    public final static String INSERT = "insert";
    public final static String UPDATE = "update";
    public final static String DELETE = "delete";
    public final static String DELETE_BY_ID = "deleteById";
    public final static String FIND_Page = "findPage";
    public final static String FIND_ALL = "findAll";
    public final static String FIND_BY_MAP = "findByMap";
    public final static String FIND_BY_ID = "findById";
    public final static String FIND_BY_ENTITY = "findByEntity";
    public final static String FIND_BY_ = "findBy";
    
}