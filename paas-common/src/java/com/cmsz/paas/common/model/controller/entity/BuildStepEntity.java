/**
 * Copyright (c) 2016 cmsz, Inc. All Rights Reserved
 * File BuildStepEntity.java
 */
package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;

import com.cmsz.paas.common.orm.id.ID;

/**
 * @author hehm
 * 2016-3-23
 */
public class BuildStepEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ID
	private long id;

	private long buildId;

	private int step;

	private String cmd;

	@Override
	public String toString() {
		return "BuildStepEntity "
				+ "[id=" + id 
				+ ", buildId=" + buildId 
				+ ", step=" + step 
				+ ", cmd=" + cmd + "]";
	}
	
	@Override  
    public boolean equals(Object obj){  
        if(obj == null){  
            return false;  
        }else {           
            if(this.getClass() == obj.getClass()){  
            	BuildStepEntity u = (BuildStepEntity) obj;  
                if(this.getCmd().equals(u.getCmd()) && this.getStep() == u.getStep()){  
                	return true;  
                }else{  
                    return false;  
                }      
            }else{  
                return false;  
            }  
        }             
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBuildId() {
		return buildId;
	}

	public void setBuildId(long buildId) {
		this.buildId = buildId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	} 
}
