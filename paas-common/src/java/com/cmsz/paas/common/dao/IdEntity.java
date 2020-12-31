package com.cmsz.paas.common.dao;

import java.io.Serializable;

public class IdEntity<ID extends Serializable> {

	private ID id;

	public void setId(ID id) {

		this.id = id;
	}

	public ID getId() {

		return id;
	}

}
