/**
 * 
 */
package com.cmsz.paas.common.model.controller.response;
/**
*@author chenlq	
*@date 2018年5月21日 --- 下午5:07:22
**/

public class TrueOrFalse {
  private  boolean bool;

public boolean isBool() {
	return bool;
}

public void setBool(boolean bool) {
	this.bool = bool;
}

@Override
public String toString() {
	return "TrueOrFalse [bool=" + bool + "]";
}
}

