package com.cmsz.paas.common.log.sqlmonitor;



import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.cmsz.paas.common.lang.Lang;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.log.OperationLog;
import com.cmsz.paas.common.log.OperationSql;
import com.cmsz.paas.common.log.defaults.DefaultOperationSql;
import com.cmsz.paas.common.log.defaults.LogHolder;
/**
 * 基于IBatis3实现的SQL监视器，实现对象SQL拦截，以便可以将每个操作SQL记入日志中
 * @author Sam
 *
 */
@Intercepts( {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
                Object.class }),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class,
                Object.class, RowBounds.class, ResultHandler.class }) })
public class IBatis3SqlMonitorPlugin implements Interceptor {
    
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    
    private static final Logger logger = Logger.getLogger(IBatis3SqlMonitorPlugin.class);
    
    private Properties properties;
    
    @Override
    public Object intercept(Invocation invoker) throws Throwable {

        Object[] queryArgs = invoker.getArgs();
        MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameterObject = queryArgs[PARAMETER_INDEX];
        // 插入日志本身不拦截SQL
        if (Strings.matches(".*insert.*", ms.getId().toLowerCase())
                && (parameterObject instanceof OperationLog || parameterObject instanceof OperationSql)) {
            return invoker.proceed();
        }
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //根据IBatis3中配置的SQL和传进来的参数进行处理生成可在pl/sql中运行的SQL
        if (parameterMappings != null) {
            MetaObject metaObject = parameterObject == null ? null : MetaObject
                    .forObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(
                            parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = MetaObject.forObject(value).getValue(
                                    propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    if (value != null) {
                    	boolean valueIsString = value instanceof String;
                    	if (valueIsString && Strings.containsAny(value.toString(), "$")) {
                    		value = ((String)value).replaceAll("\\$","\\\\\\$");
                    	}
                        sql = sql.replaceFirst("\\?", valueIsString ? "'" + value + "'" : value.toString());
                    } else {
                        sql = sql.replaceFirst("\\?", "null");
                        
                    }
                }
            }
            
            if (properties != null && 
            		Strings.equals(properties.getProperty("printSql", "false"),"true")) {
            	if(!"User.findById".equals(ms.getId()) || !"AlarmDetail.findByMap".equals(ms.getId())) {
            		Lang.printlnf("当前运行的SQL[%s]:%s",ms.getId(),sql);
            	}
            }
        }
        
        OperationLog log = LogHolder.getLog();
        if (log != null) {
            String sqlType = ms.getSqlCommandType().name();
            DefaultOperationSql operationSql = new DefaultOperationSql(ms.getId(), sqlType, sql,
                    null, log);
            log.addOperationSql(operationSql);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("current module:%s\ncurrent sql:%s", log
                        .getOperatePath(), sql));
            }
        }
        
        return invoker.proceed();
    }
    
    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }
    
    @Override
    public void setProperties(Properties p) {
    	this.properties = p;
    }
    
}
