package com.cmsz.paas.common.model.controller.entity;

import java.util.Arrays;

public class HostConfig {
	//容器挂载
	private String[] Binds;

	public String[] getBinds() {
		return Binds;
	}

	public void setBinds(String[] binds) {
		Binds = binds;
	}

	@Override
	public String toString() {
		return "HostConfig [Binds=" + Arrays.toString(Binds) + "]";
	}
}
