package com.cmsz.paas.common.log.defaults;



import java.util.List;

import com.cmsz.paas.common.log.OperationLog;
import com.cmsz.paas.common.log.LoggingProvider;
import com.cmsz.paas.common.log.OperationSql;
import com.cmsz.paas.common.orm.id.IdHelper;
import com.cmsz.paas.common.service.SimpleServiceBase;

/**
 * 日志记录器的IBatis3实现
 * 
 * @author Sam
 * 
 */
public class Ibastis3LoggingProvider extends SimpleServiceBase<OperationLog, String> implements
        LoggingProvider {
    
    protected Boolean hasDetail;
    
    protected String logSqlId;
    
    protected String detailSqlId;
    
    @Override
    public void logging(OperationLog log) {

        IdHelper.setId(log);
        ibatisDao.getSqlSessionTemplate().insert(logSqlId, log);
        if (isHasDetail()) {
            List<OperationSql> sqls = log.getOperationSqls();
            for (OperationSql sql : sqls) {
                IdHelper.setId(sql);
                ibatisDao.getSqlSessionTemplate().insert(detailSqlId, sql);
            }
        }
    }
    
    public Boolean isHasDetail() {

        return hasDetail;
    }
    
    public void setHasDetail(Boolean hasDetail) {

        this.hasDetail = hasDetail;
    }
    
    public String getLogSqlId() {

        return logSqlId;
    }
    
    public void setLogSqlId(String logSqlId) {

        this.logSqlId = logSqlId;
    }
    
    public String getDetailSqlId() {

        return detailSqlId;
    }
    
    public void setDetailSqlId(String detailSqlId) {

        this.detailSqlId = detailSqlId;
    }
    
}
