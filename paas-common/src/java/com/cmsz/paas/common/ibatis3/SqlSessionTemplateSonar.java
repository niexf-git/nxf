/**
 * 修改历史�?<br/>
 * =================================================================<br/>
 * 修改�? 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.ibatis3;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import com.cmsz.paas.common.exception.ApplicationException;

public class SqlSessionTemplateSonar {

	SqlSessionFactory sqlSessionFactorySonar;
	SqlSessionFactoryFactoryBeanSonar sqlMapSessionFactorySonar;

	public SqlSessionTemplateSonar() {

	}

	public void setSqlMapSessionFactory(
			SqlSessionFactoryFactoryBeanSonar sqlMapSessionFactory) {

		this.sqlMapSessionFactorySonar = sqlMapSessionFactory;
		this.sqlSessionFactorySonar = sqlMapSessionFactory.sqlSessionFactorySonar;
	}

	public SqlSessionFactory getSqlSessionFactory() {

		return sqlSessionFactorySonar;
	}

	public SqlSessionTemplateSonar(SqlSessionFactory sqlSessionFactorySonar) {

		this.sqlSessionFactorySonar = sqlSessionFactorySonar;
	}

	public Object execute(SqlSessionCallback action) {

		SqlSession session = null;
		try {
			session = sqlSessionFactorySonar.openSession();
			Object result = action.doInSession(session);
			return result;
		} catch (Throwable ex) {
			if (ex.getCause() instanceof SQLException)
				throw getExceptionTranslator().translate(
						"SqlSession operation", null,
						(SQLException) ex.getCause());
			ApplicationException appEx = new ApplicationException(
					ex.getMessage(), ex);
			appEx.setErrorCode("00003001");
			throw appEx;
		} finally {
			if (session != null)
				session.close();
		}
	}

	private SQLExceptionTranslator getExceptionTranslator() {

		DataSource dataSource = sqlSessionFactorySonar.getConfiguration()
				.getEnvironment().getDataSource();
		if (dataSource != null) {
			return new SQLErrorCodeSQLExceptionTranslator(dataSource);
		}
		return new SQLStateSQLExceptionTranslator();
	}

	public Object selectOne(final String statement, final Object parameter) {

		return execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.selectOne(statement, parameter);
			}
		});
	}

	public Object selectOne(final String statement) {

		return execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.selectOne(statement);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List selectList(final String statement) {

		return (List) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.selectList(statement);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List selectList(final String statement, final Object parameter) {

		return (List) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.selectList(statement, parameter);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List selectList(final String statement, final Object parameter,
			final int offset, final int limit) {

		return (List) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.selectList(statement, parameter, new RowBounds(
						offset, limit));
			}
		});
	}

	public int delete(final String statement, final Object parameter) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.delete(statement, parameter);
			}
		});
	}

	public int delete(final String statement) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.delete(statement);
			}
		});
	}

	public int update(final String statement, final Object parameter) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.update(statement, parameter);
			}
		});
	}

	public int update(final String statement) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.update(statement);
			}
		});
	}

	public int insert(final String statement, final Object parameter) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.insert(statement, parameter);
			}
		});
	}

	public int insert(final String statement) {

		return (Integer) execute(new SqlSessionCallback() {

			public Object doInSession(SqlSession session) {

				return session.insert(statement);
			}
		});
	}
}
