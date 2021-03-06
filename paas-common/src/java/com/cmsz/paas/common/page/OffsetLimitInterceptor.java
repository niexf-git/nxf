/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.cmsz.paas.common.jdbc.dialect.Dialect;
import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.lang.collection.Collections;
import com.cmsz.paas.common.lang.collection.PropertiesHelper;

/**
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件
 * 
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * 配置文件内容:
 * 
 * <pre>
 * 	&lt;plugins>
 * 	&lt;plugin interceptor="cn.org.rapid_framework.ibatis3.plugin.OffsetLimitInterceptor">
 * 		&lt;property name="dialectClass" value="com.cmsz.framework.jdbc.dialect.OracleDialect"/>
 * 	&lt;/plugin>
 * &lt;/plugins>
 * </pre>
 * 
 * @author badqiu(rapid-framework作者),sam
 * 
 */

@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

	Dialect dialect;

	public Object intercept(Invocation invocation) throws Throwable {

		processIntercept(invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(final Object[] queryArgs) {

		// queryArgs = query(MappedStatement ms, Object parameter, RowBounds
		// rowBounds,
		// ResultHandler resultHandler)
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		int offset = rowBounds.getOffset();
		int limit = rowBounds.getLimit();

		if (dialect.supportsLimit()
				&& (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
			BoundSql boundSql = ms.getBoundSql(parameter);
			String sql = boundSql.getSql().trim();
			// 加入默认的排序条件
			if (parameter instanceof PageContext) {
				PageContext pc = (PageContext) parameter;
				if (pc.isUseSort()) {
					List<SortInfo> sorts = pc.getSorts();
					if (Collections.isNotEmpty(sorts)) {
						StringBuilder sb = new StringBuilder();
						sb.append(" order by ");
						sb.append(Strings.join(sorts, ","));
						sql = " select * from ( " + sql
								+ ")  paging_query_table____ " + sb.toString();
					}
				}
			}
			if (dialect.supportsLimitOffset()) {
				sql = dialect.getLimitString(sql, offset, limit);
				offset = RowBounds.NO_ROW_OFFSET;
			} else {
				sql = dialect.getLimitString(sql, 0, limit);
			}

			limit = RowBounds.NO_ROW_LIMIT;

			queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
			BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
					boundSql.getParameterMappings(),
					boundSql.getParameterObject());
			MappedStatement newMs = copyFromMappedStatement(ms,
					new BoundSqlSqlSource(newBoundSql));
			queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
		}
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource) {

		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				ms.getId(), newSqlSource, ms.getSqlCommandType());

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
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {

		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

		String dialectClass = new PropertiesHelper(properties)
				.getRequiredString("dialectClass");
		try {
			dialect = (Dialect) Class.forName(dialectClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(
					"cannot create dialect instance by dialectClass:"
							+ dialectClass, e);
		}
		System.out.println(OffsetLimitInterceptor.class.getSimpleName()
				+ ".dialect=" + dialectClass);
	}

	public static class BoundSqlSqlSource implements SqlSource {

		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {

			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {

			return boundSql;
		}
	}

}
