package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class Capabilities {

	private String[] add;

	private String[] drop;

	public void setAdd(String[] add) {
		this.add = add;
	}

	public String[] getAdd() {
		return add;
	}

	public void setDrop(String[] drop) {
		this.drop = drop;
	}

	public String[] getDrop() {
		return drop;
	}

	@Override
	public String toString() {
		return "Capabilities "
				+ "[add=" + Arrays.toString(add)
				+ ", drop=" + Arrays.toString(drop) + "]";
	}
}
