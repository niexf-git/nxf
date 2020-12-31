/**
 * Copyright (c) 2015 cmsz, Inc. All Rights Reserved
 * File Constant.java
 */
package com.cmsz.paas.common.constant;

import javax.ws.rs.core.MediaType;

/**
 * Constant
 *
 * @author hehm
 *
 * @date 2015-4-11
 */
public class Constant {
	/**
	 * 启动
	 */
	public static final String START = "start";

	/**
	 * 停止
	 */
	public static final String STOP = "stop";

	/**
	 * 重启
	 */
	public static final String RESTART = "restart";
	
	/**
	 * 自身启动
	 */
	public static final String SELF_START = "self-start";

	/**
	 * 自身重启
	 */
	public static final String SELF_RESTART="self-restart";
	
	/**
	 * 全部启动
	 */
	public static final String ALL_START = "all-start";

	/**
	 * 全部重启
	 */
	public static final String ALL_RESTART="all-restart";
	
	/**
	 * 强制停止
	 */
	public static final String FORCE_STOP="force-stop";

	/**
	 * 强制重启
	 */
	public static final String FORCE_RESTART="force-restart";
	
	/**
	 * 新建
	 */
	public static final String POST = "post";

	/**
	 * 删除
	 */
	public static final String DELETE = "delete";
	
	/**
	 * 更新
	 */
	public static final String UPDATE = "update";
	
	/**
	 * 获取
	 */
	public static final String GET = "get";
	
	/**
	 * 发信号
	 */
	public static final String SIGNAL = "signal";
	
	/**
	 * 执行
	 */
	public static final String EXEC = "exec";
	
	/**
	 * 创建灰度版本
	 */
	public static final String CREATE_GRAY = "create-gray";
	
	/**
	 * 更新灰度版本
	 */
	public static final String UPDATE_GRAY = "update-gray";
	
	/**
	 * 删除灰度版本
	 */
	public static final String DELETE_GRAY = "delete-gray";
	
	/**
	 * 升级灰度版本
	 */
	public static final String UPGRADE_GRAY = "upgrade-gray";
	
	/**
	 * 授权rest访问数据xml格式
	 */
	public static final String REST_MODE_XML="MediaType.APPLICATION_XML";
	
	/**
	 * 授权rest访问数据json格式
	 */
	public static final String REST_MODE_JSON="MediaType.APPLICATION_JSON";
	
	/**
	 * 诊断挂载控制中心的路径
	 */
	public static final String CONTROLLER_DATA_PATH="CONTROLLER_DATA_PATH";
	
	/**
	 * 流水卡片名称定义
	 */
	public static final String STEP_DEPSCAN = "depscan";
	public static final String STEP_BUILD = "build";
	public static final String STEP_AUTO_TEST = "autotest";
	public static final String STEP_INTE_TEST = "intetest";
	public static final String STEP_DOWN_LOAD_CHECK = "downloadcheck";
	public static final String STEP_RELEASE = "release";
	public static final String STEP_TEST_DEPLOY = "testdeploy";
	public static final String STEP_TEST_AUTO_TEST = "testautotest";
	public static final String STEP_TEST_INTE_TEST = "testintetest";
	public static final String STEP_TEST_RELEASE = "testrelease";

	/**
	 * ZK发布的环境变量
	 */
	public static enum IPAAS_ZK_ENVS {
		CONN;
	}

	/**
	 * REDIS发布的环境变量
	 */
	public static enum IPAAS_REDIS_ENVS {
		CONN, PWD,USER;
	}

	/**
	 * 私有镜像状态：开发-1，测试-2，生产-3
	 */
	public static enum PRIVATE_IMAGE_STATUS {

		DEV(1), TEST(2), OPRT(3);

		private int intVal;
	    private PRIVATE_IMAGE_STATUS(int intVal) {
	        this.intVal = intVal;
	    } 
	    public int getVal(){
	        return this.intVal;
	    }
	}

	/**
	 * k8s服务类型：ipaas服务-1，应用服务-2
	 */
	public static enum K8S_SERVICE_TYPE {

		IPAAS(1), APPSERVICE(2);

		private int intVal;
	    private K8S_SERVICE_TYPE(int intVal) {
	        this.intVal = intVal;
	    } 
	    public int getVal(){
	        return this.intVal;
	    }
	}
}
