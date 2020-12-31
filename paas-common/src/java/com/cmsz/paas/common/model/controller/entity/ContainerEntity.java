package com.cmsz.paas.common.model.controller.entity;

import java.util.Arrays;

import com.cmsz.paas.common.model.k8s.v1.EnvVar;

public class ContainerEntity {
	private String Hostname;
	private EnvVar[] Env;
	private String[] Cmd;
	private HostConfig HostConfig;
	private String Image;
	private String WorkingDir;
	private String MacAddress;

	public String getHostname() {
		return Hostname;
	}

	public void setHostname(String hostname) {
		Hostname = hostname;
	}


	public EnvVar[] getEnv() {
		return Env;
	}

	public void setEnv(EnvVar[] env) {
		Env = env;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getWorkingDir() {
		return WorkingDir;
	}

	public void setWorkingDir(String workingDir) {
		WorkingDir = workingDir;
	}

	public String getMacAddress() {
		return MacAddress;
	}

	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}

	public HostConfig getHostConfig() {
		return HostConfig;
	}

	public void setHostConfig(HostConfig hostConfig) {
		HostConfig = hostConfig;
	}

	public String[] getCmd() {
		return Cmd;
	}

	public void setCmd(String[] cmd) {
		Cmd = cmd;
	}

	@Override
	public String toString() {
		return "ContainerEntity [Hostname=" + Hostname + ", Env="
				+ Arrays.toString(Env) + ", Cmd=" + Arrays.toString(Cmd)
				+ ", HostConfig=" + HostConfig + ", Image=" + Image
				+ ", WorkingDir=" + WorkingDir + ", MacAddress=" + MacAddress
				+ "]";
	}
}
