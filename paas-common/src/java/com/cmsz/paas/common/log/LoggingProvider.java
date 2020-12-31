package com.cmsz.paas.common.log;



/**
 * 日志提供者，将日志信息保存至数据库的处理方式，可以是jdbc,ibatis,hibernate，可由具体项目去实现， 默认会提供基于Ibatis和jdbc的实现
 * 
 * @author Sam
 * 
 */
public interface LoggingProvider {
    /**
     * 持久化日志信息
     * @param log
     */
    public void logging(OperationLog log);
}
