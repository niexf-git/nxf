package com.cmsz.paas.common.model.k8s.v1;

import java.util.Arrays;

public class Container {

	private String name;

	private String image;

	private String[] command;

	private String[] args;

	private String workingDir;

	private ContainerPort[] ports;

	private EnvVar[] env;

	private ResourceRequirements resources;

	private VolumeMount[] volumeMounts;

	private Probe livenessProbe;

	private Probe readinessProbe;

	private Lifecycle lifecycle;

	private String terminationMessagePath;

	private String imagePullPolicy;

	private SecurityContext securityContext;

	private boolean stdin;
	
	private boolean stdinOnce;
	
	private boolean tty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String[] getCommand() {
		return command;
	}

	public void setCommand(String[] command) {
		this.command = command;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
	}

	public ContainerPort[] getPorts() {
		return ports;
	}

	public void setPorts(ContainerPort[] ports) {
		this.ports = ports;
	}

	public EnvVar[] getEnv() {
		return env;
	}

	public void setEnv(EnvVar[] env) {
		this.env = env;
	}

	public ResourceRequirements getResources() {
		return resources;
	}

	public void setResources(ResourceRequirements resources) {
		this.resources = resources;
	}

	public VolumeMount[] getVolumeMounts() {
		return volumeMounts;
	}

	public void setVolumeMounts(VolumeMount[] volumeMounts) {
		this.volumeMounts = volumeMounts;
	}

	public Probe getLivenessProbe() {
		return livenessProbe;
	}

	public void setLivenessProbe(Probe livenessProbe) {
		this.livenessProbe = livenessProbe;
	}

	public Probe getReadinessProbe() {
		return readinessProbe;
	}

	public void setReadinessProbe(Probe readinessProbe) {
		this.readinessProbe = readinessProbe;
	}

	public Lifecycle getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Lifecycle lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getTerminationMessagePath() {
		return terminationMessagePath;
	}

	public void setTerminationMessagePath(String terminationMessagePath) {
		this.terminationMessagePath = terminationMessagePath;
	}

	public String getImagePullPolicy() {
		return imagePullPolicy;
	}

	public void setImagePullPolicy(String imagePullPolicy) {
		this.imagePullPolicy = imagePullPolicy;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public boolean isStdin() {
		return stdin;
	}

	public void setStdin(boolean stdin) {
		this.stdin = stdin;
	}

	public boolean isStdinOnce() {
		return stdinOnce;
	}

	public void setStdinOnce(boolean stdinOnce) {
		this.stdinOnce = stdinOnce;
	}

	public boolean isTty() {
		return tty;
	}

	public void setTty(boolean tty) {
		this.tty = tty;
	}

	@Override
	public String toString() {
		return "Container "
				+ "[name=" + name 
				+ ", image=" + image 
				+ ", command=" + Arrays.toString(command)
				+ ", args=" + Arrays.toString(args)
				+ ", workingDir=" + workingDir 
				+ ", ports=" + Arrays.toString(ports)
				+ ", env=" + Arrays.toString(env)
				+ ", resources=" + resources 
				+ ", volumeMounts=" + Arrays.toString(volumeMounts) 
				+ ", livenessProbe=" + livenessProbe 
				+ ", readinessProbe=" + readinessProbe
				+ ", lifecycle=" + lifecycle
				+ ", terminationMessagePath=" + terminationMessagePath
				+ ", imagePullPolicy=" + imagePullPolicy 
				+ ", securityContext=" + securityContext
				+ ", stdin=" + stdin
				+ ", stdinOnce=" + stdinOnce
				+ ", tty=" + tty + "]";
	}
}
