package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class ExecAction {

	private String[] command;

	@Override
	public String toString() {
		return "ExecAction"
				+ " [command=" + Arrays.toString(command) + "]";
	}

	public void setCommand(String[] command) {
		this.command = command;
	}

	public String[] getCommand() {
		return command;
	}
}
