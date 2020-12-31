package com.cmsz.paas.web.base.log;


import java.util.List;

import com.cmsz.paas.common.log.OperationSql;
import com.cmsz.paas.common.log.defaults.DefaultOperationLog;
import com.cmsz.paas.common.log.defaults.DefaultOperationSql;
import com.cmsz.paas.common.orm.id.ID;

public class MyOperationLog extends DefaultOperationLog{
	
	@ID
    protected String id;
    protected String clientIp = "";
    //protected String serverIp = "";
    //protected String systemCode = "";
   // protected String subSys = "";
    //protected String component = "";
    protected String func = "";
    //protected String moduleCode = "";
   // protected String moduleName = "";
    protected String operateType = "";
    protected String operateTime = "";
    protected String operateDesc = "";
    protected String operator = "";
    //protected String sensitivity = "1";
    protected String status = "1";
    protected String operatePath = "";
    
   // protected String systemName = "";
   // protected String subSysName = "";
  //  protected String componentName = "";
    
   // protected List<OperationSql> sqls = Lists.newList();
    protected String detail;
    protected String sessionId;
    protected String inputArgs; //输入参数
    protected String outputArgs;//输出参数
    
    public String getInputArgs() {
		return inputArgs;
	}

	public void setInputArgs(String inputArgs) {
		this.inputArgs = inputArgs;
	}

	public String getOutputArgs() {
		return outputArgs;
	}

	public void setOutputArgs(String outputArgs) {
		this.outputArgs = outputArgs;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private static final long serialVersionUID = -7513201195800952006L;
    
    @Override
	public void addOperationSql(OperationSql sql) {

        if (sql instanceof DefaultOperationSql) {
            ((DefaultOperationSql) sql).setLog(this);
        }
        sqls.add(sql);
    }
    
    @Override
	public String getClientIp() {

        return clientIp;
    }
    
    public String getDetail() {
		return detail;
	}
    
    public String getFunc() {
		return func;
	}
    
    @Override
	public String getId() {

        return id;
    }
    
    @Override
	public String getModuleCode() {

        return moduleCode;
    }
    
    @Override
	public String getModuleName() {

        return moduleName;
    }
    
    @Override
	public String getOperateDesc() {

        return operateDesc;
    }
    
    @Override
	public String getOperatePath() {

        return operatePath;
    }
    
    @Override
	public String getOperateTime() {

        return operateTime;
    }
    
    @Override
	public String getOperateType() {

        return operateType;
    }
    
    @Override
	public List<OperationSql> getOperationSqls() {

        return sqls;
    }
    
    @Override
	public String getOperator() {

        return operator;
    }
    
    @Override
	public String getSensitivity() {

        return sensitivity;
    }
    
    @Override
	public String getServerIp() {

        return serverIp;
    }
    
    @Override
	public List<OperationSql> getSqls() {

        return sqls;
    }
    
    @Override
	public String getStatus() {

        return status;
    }
    
    @Override
	public String getSystemCode() {

        return systemCode;
    }
    
    @Override
	public void setClientIp(String clientIp) {

        this.clientIp = clientIp;
    }
    
    public void setDetail(String detail) {
		this.detail = detail;
	}
    
    public void setFunc(String func) {
		this.func = func;
	}
    
    @Override
	public void setId(String id) {

        this.id = id;
    }
    
    @Override
	public void setModuleCode(String moduleCode) {

        this.moduleCode = moduleCode;
    }
    
    @Override
	public void setModuleName(String moduleName) {

        this.moduleName = moduleName;
    }
    
    @Override
	public void setOperateDesc(String operateDesc) {

        this.operateDesc = operateDesc;
    }
    
    @Override
	public void setOperatePath(String operatePath) {

        this.operatePath = operatePath;
    }
    
    @Override
	public void setOperateTime(String operateTime) {

        this.operateTime = operateTime;
    }
    
    @Override
	public void setOperateType(String operateType) {

        this.operateType = operateType;
    }
    
    @Override
	public void setOperator(String operator) {

        this.operator = operator;
    }
    
    @Override
	public void setSensitivity(String sensitivity) {

        this.sensitivity = sensitivity;
    }
    
  
	@Override
	public void setServerIp(String serverIp) {

        this.serverIp = serverIp;
    }
	@Override
	public void setSqls(List<OperationSql> sqls) {

        this.sqls = sqls;
    }

	@Override
	public void setStatus(String status) {

        this.status = status;
    }

	@Override
	public void setSystemCode(String systemCode) {

        this.systemCode = systemCode;
    }
    
}
