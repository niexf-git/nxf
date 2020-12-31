package com.cmsz.paas.common.model.controller.entity;

import java.io.Serializable;
import java.util.List;

public class CodePathAccountEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CodePathEntity> codePath;
	
	private CodeAccountEntity codeAccount;
	
	

	public List<CodePathEntity> getCodePath() {
		return codePath;
	}

	public void setCodePath(List<CodePathEntity> codePath) {
		this.codePath = codePath;
	}

	public CodeAccountEntity getCodeAccount() {
		return codeAccount;
	}

	public void setCodeAccount(CodeAccountEntity codeAccount) {
		this.codeAccount = codeAccount;
	}

	@Override
	public String toString() {
		return "CodePathAccountEntity [codePath=" + codePath.toString() + ", codeAccount="
				+ codeAccount.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeAccount == null) ? 0 : codeAccount.hashCode());
		result = prime * result
				+ ((codePath == null) ? 0 : codePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodePathAccountEntity other = (CodePathAccountEntity) obj;
		if (codeAccount == null) {
			if (other.codeAccount != null)
				return false;
		} else if (!codeAccount.equals(other.codeAccount))
			return false;
		if (codePath == null) {
			if (other.codePath != null)
				return false;
		} else if (!codePath.equals(other.codePath))
			return false;
		return true;
	}	
	
	

}
