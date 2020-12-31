package com.cmsz.paas.common.log.defaults;



import com.cmsz.paas.common.log.OperationLog;

/**
 * 日志对象持有器
 * @author Sam
 *
 */
public class LogHolder {
    
    @SuppressWarnings("unchecked")
    private static final ThreadLocal logTL = new ThreadLocal();
    
    @SuppressWarnings("unchecked")
    public static void setLog(OperationLog log) {

        logTL.set(log);
    }
    
    public static OperationLog getLog() {

        return (OperationLog) logTL.get();
    }
    
}
