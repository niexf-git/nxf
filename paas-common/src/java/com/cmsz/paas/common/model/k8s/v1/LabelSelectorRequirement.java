package com.cmsz.paas.common.model.k8s.v1;

import edu.emory.mathcs.backport.java.util.Arrays;

public class LabelSelectorRequirement {
	
	private String key;
	
	private String operator;
	
	private String [] values;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
	
	@Override
	public String toString() {
		return "LabelSelectorRequirement "
				+ "[key=" + key 
				+ ", operator="+operator
				+ ", values="+Arrays.toString(values)+"]";
	}

}
