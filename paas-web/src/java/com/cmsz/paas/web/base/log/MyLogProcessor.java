package com.cmsz.paas.web.base.log;


import java.lang.reflect.Method;

import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.log.OperationLog;
import com.cmsz.paas.common.log.annotations.Logging;
import com.cmsz.paas.common.log.defaults.DefaultLogProcessor;
import com.cmsz.paas.common.log.defaults.LogHolder;

public class MyLogProcessor extends DefaultLogProcessor {
	 @Override
	protected MyOperationLog createOperationLog() {

        if (logCreator != null) {
            return (MyOperationLog) logCreator.create();
        }
        OperationLog log = LogHolder.getLog();
        if (log != null) {
            return (MyOperationLog) log;
        }
        return new MyOperationLog();
    }
    
    /**
     * 初始化日志信息
     */
    @Override
    public void init(Class type, Method method) {

        if (isNeedLogging(type, method)) {
            MyOperationLog log = createOperationLog();
            initLog(type, method, log);
        }
    }

    
    
    /**
     * 如果方法注释了@Logging()的话，会将其信息复盖默认的信息
     * 
     * @param target
     * @param method
     * @param log
     */
    protected void initLog(Class target, Method method, MyOperationLog log) {

        if (logInitialization != null) {
            logInitialization.init(log);
        }
        
        //
        // log.setOperator(Securitys.getCurrentUserName());
        // 如果被拦截的方法有注解了Logging的话，会重设OperationLog的信息
        Logging li = method.getAnnotation(Logging.class);
        if (li != null) {
            if (Strings.isNotBlank(li.sensitivity())) {
				log.setSensitivity(li.sensitivity());
			}
            if (Strings.isNotBlank(li.moduleCode())) {
				log.setModuleCode(li.moduleCode());
			}
            if (Strings.isNotBlank(li.moduleName())) {
				log.setModuleName(li.moduleName());
			}
            if (Strings.isNotBlank(li.operationType())) {
				log.setOperateType(li.operationType());
			}
            if (Strings.isNotBlank(li.operationDesc())) {
				log.setOperateDesc(li.operationDesc());
			}
        }
        
        LogHolder.setLog(log);
    }
}
