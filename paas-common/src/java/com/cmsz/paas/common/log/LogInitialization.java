package com.cmsz.paas.common.log;


/**
 * 日志信息初始化器，当创建了日志信息对象后，日志处理器会使用此接口实现类进行初始化
 * @author Sam
 *
 */
public interface LogInitialization {
    /**
     * 初始化日志信息
     */
    public void init(OperationLog log);
}
