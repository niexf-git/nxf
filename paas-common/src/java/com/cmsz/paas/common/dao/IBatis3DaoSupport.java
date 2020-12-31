/**
 * �޸���ʷ��<br/>
 * =================================================================<br/>
 * �޸��� �޸�ʱ�� �޸�ԭ��/����<br/>
 * =================================================================<br/>
 * sam 20100111 �����޸���ʷע��<br/>
 */

package com.cmsz.paas.common.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.cmsz.paas.common.ibatis3.IBatis3Dao;
import com.cmsz.paas.common.page.PageContext;

/**
 * ����Ibatis3��crud dao����
 * 
 * @author sam
 * 
 * @param <E>
 * @param <ID>
 */
public class IBatis3DaoSupport<E, ID extends Serializable> extends
		IBatis3Dao<E, ID> implements EntityDao<E, ID>, InitializingBean {

	public List<E> findBy(PageContext fp) {

		throw new UnsupportedOperationException();
	}

	public final void afterPropertiesSet() throws Exception {

		checkDaoConfig();
	}

	protected void checkDaoConfig() throws IllegalArgumentException {

		Assert.notNull(sqlSessionFactory, "sqlSessionFactory can not be null!");
	}

}
