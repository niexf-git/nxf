/**
 * 修改历史�?<br/>
 * =================================================================<br/>
 * 修改�? 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.ibatis3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;

import com.cmsz.paas.common.lang.Lang;

@SuppressWarnings("unchecked")
public class SqlSessionFactoryFactoryBean implements FactoryBean, InitializingBean {
    
    Log logger = LogFactory.getLog(SqlSessionFactoryFactoryBean.class);
    private Resource configLocation;
    private Resource[] mapperLocations;
    private DataSource dataSource;
    private boolean useTransactionAwareDataSource = true;
    
    SqlSessionFactory sqlSessionFactory;
    
    public void afterPropertiesSet() throws Exception {

        Assert.notNull(configLocation, "configLocation must be not null");
        sqlSessionFactory = createSqlSessionFactory();
    }
    
    public SqlSessionFactory getSqlSessionFactory() {

        return sqlSessionFactory;
    }
    
    private SqlSessionFactory createSqlSessionFactory() throws IOException {

        Reader reader = new InputStreamReader(getConfigLocation().getInputStream());
        try {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            Configuration conf = sqlSessionFactory.getConfiguration();
            if (dataSource != null) {
                DataSource dataSourceToUse = this.dataSource;
                if (this.useTransactionAwareDataSource
                        && !(this.dataSource instanceof TransactionAwareDataSourceProxy)) {
                    dataSourceToUse = new TransactionAwareDataSourceProxy(this.dataSource);
                }
                
                conf.setEnvironment(new Environment("development", new ManagedTransactionFactory(),
                        dataSourceToUse));
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(conf);
            }
            
            if (mapperLocations != null) {
                Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
                for (Resource r : mapperLocations) {
                    Lang.println("Loading iBatis3 mapper xml from file["
                            + r.getFile().getAbsolutePath() + "]");
                    Reader mapperReader = new InputStreamReader(r.getInputStream());
                    try {
                        XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(mapperReader, conf, r
                                .getFile().getAbsolutePath(), sqlFragments);
                        mapperBuilder.parse();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("Loading  mapper xml from file["
                                + r.getFile().getAbsolutePath() + "] is Error...", ex);
                    } finally {
                        mapperReader.close();
                    }
                }
            }
            return sqlSessionFactory;
        } finally {
            reader.close();
        }
    }
    
    public Object getObject() throws Exception {

        return sqlSessionFactory;
    }
    
    public DataSource getDataSource() {

        return dataSource;
    }
    
    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }
    
    public Class getObjectType() {

        return SqlSessionFactory.class;
    }
    
    public boolean isSingleton() {

        return true;
    }
    
    public Resource getConfigLocation() {

        return configLocation;
    }
    
    public void setConfigLocation(Resource configurationFile) {

        this.configLocation = configurationFile;
    }
    
    public void setMapperLocations(Resource[] mapperLocations) {

        this.mapperLocations = mapperLocations;
    }
    
}
