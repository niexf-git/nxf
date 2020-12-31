package com.cmsz.paas.web.constants;

public class Constants {

	// 当前用户
	public static final String CURRENT_USER = "CURRENT_USER";

	/*
	 * rest 接口返回码
	 */
	public static final String REST_CODE_SUCCESS = "0";
	public static final String REST_CODE_COMPLETE = "1";
	public static final String REST_CODE_ERROR = "-1";

	//控制中心 环境变量
	public static final String HTTP = "http";
	public static final String COLON = ":";
	public static final String SEPARATORS = "//";
	public static final String HOST = "PAASCONTROLLER_SERVICE_HOST";
	public static final String PORT = "PAASCONTROLLER_SERVICE_PORT";
	
	
	// grafana
	public static final String GRAFANA_REST_CODE_SUCCESS = "200";
	public static final String GRAFANA_BYNAME = "heapster"; //ip别名

	/**
	 * iPaaS服务环境变量
	 */
	public static final String IPAAS_ENVS_CONN = "CONN";// 连接串
	public static final String IPAAS_ENVS_PWD = "PWD";// 密码

	/** 系统管理 迭代三角色类型更改 */
	public static final int ROLE_TYPE_SUPER_MANAGER = 0;// 超级管理员
	public static final int ROLE_TYPE_GENERAL_USER = 1;// 普通用户
	public static final int ROLE_OPER_PERMISSION_TYPE = 1;// 操作权限类型
	public static final int ROLE_APP_PERMISSION_TYPE = 2;// 应用权限

	public static final int ROLE_ID = 1; // 管理员角色ID
	public static final int USER_ID = 1; // 管理员用户ID
	
	// 操作类型（开发、测试、运维）
	public static final String DEVELOPER_TYPE = "1";
	public static final String TEST_TYPE = "2";
	public static final String OPERATOR_TYPE = "3";
	
	public static final String IPAAS_CLUSTER = "1";//ipaas集群
	public static final String APAAS_CLUSTER = "2";//apaas集群
	public static final String PAAS_PLATFORM = "3";//paas平台
	
	public static final String SVN_REPOSITORY = "1"; //svn仓库
	public static final String GIT_REPOSITORY = "2"; //git仓库
	
	/**
	 * 错误码定义
	 * 
	 * 错误码共5位，1开头表示PAAS前台错误，2开头表示应用控制中心错误，3开头表示监控中心错误;
	 * 错误码第二三位表示模块，公共用00表示，具体模块如应用管理模块用01
	 * ，服务管理用02，基础服务用03，镜像用04，监控用05，告警用06，日志用07，用户用08，角色用09，权限用10，构建12，其他依次类推
	 * 错误码最后两位表示具体错误 eg:PAAS-20001 表示PAAS平台应用控制中心公共的1号错误
	 */
	public static final String CONTROLLER_CONN_ERROR = "PAAS-10000";// 控制中心连接失败
	public static final String FTP_IO_EXCEPTION = "PAAS-10001";// FTP IO 异常
	public static final String USER_UPLOAD_FILE_IO_EXCEPTION = "PAAS-10002";// 用户上传文件到PAAS系统失败
	public static final String SYS_LOAD_FILE_IO_EXCEPTION = "PAAS-10003";// 系统加载文件错误
	public static final String FTP_SOCKET_EXCEPTION = "PAAS-10004";// FTP
																	// SOCKET错误
	public static final String FTP_CHGDIR_EXCEPTION = "PAAS-10005";// FTP
																	// 切换文件夹错误
	public static final String FTP_CREDIR_EXCEPTION = "PAAS-10006";// FTP
																	// 创建文件夹错误
	public static final String FTP_REMDIR_EXCEPTION = "PAAS-10007";// FTP
																	// 删除文件夹错误
	public static final String WEBSOCKET_CONN_ERROR = "PAAS-10008";// 页面长连接websocket出错

	public static final String JSON_PARSE_ERROR = "PAAS-10009";// Json解析出错

	public static final String PARAMS_VALIDATE_ERROR = "PAAS-10010";// 输入的内容中包含非法字符，如<script>、delete和truncate等

	public static final String USER_AUTHORITY_ERROR = "PAAS-10011";//用户权限不足
	
	public static final String SERVICEIP_IS_NULL_ERROR = "PAAS-10100";// PAAS系统内部错误，外部服务IP为空

	// 镜像管理
	public static final String QUERY_PRIVATE_IMAGE_ERROR = "PAAS-10401"; // 查询私有镜像异常
	public static final String QUERY_PRIVATE_IMAGE_VERSION_ERROR = "PAAS-10402"; // 查询私有镜像版本异常
	public static final String QUERY_PUBLIC_IMAGE_ERROR = "PAAS-10403"; // 查询公共镜像异常
	public static final String PRIVATE_IMAGE_USED_BY_VERSION_ERROR = "PAAS-10404"; // 删除已有版本的私有镜像异常
	public static final String PRIVATE_IMAGE_VERSION_PUBLISHED2TEST_ERROR = "PAAS-10405"; // 发布测试异常
	public static final String PRIVATE_IMAGE_VERSION_PUBLISHED2PRODUCT_ERROR = "PAAS-10406"; // 发布生产异常
	public static final String PUBLIC_IMAGE_PUBLISHED2PRODUCT_ERROR = "PAAS-10407"; // 公共镜像-发布生产异常
	public static final String PUBLISHED2PRODUCT_WEB_SOCKET_STREAM_CLOSE_ERROR = "PAAS-10408"; // 发布生产-长连接异常
	public static final String DELETE_PRIVATE_IMAGE_ERROR = "PAAS-10409"; // 删除私有镜像异常
	public static final String QUERY_PRIVATE_IMAGE_BY_ID_ERROR = "PAAS-10410"; // 根据ID查询私有镜像异常
	public static final String UPDATE_PRIVATE_IMAGE_ERROR = "PAAS-10411"; // 修改私有镜像异常
	public static final String DELETE_PRIVATE_IMAGE_VERSION_ERROR = "PAAS-10412"; // 删除私有镜像版本异常
	public static final String QUERY_PUBLIC_IMAGE_BY_ID_ERROR = "PAAS-10413"; // 根据ID查询公共镜像异常
	public static final String UPDATE_PUBLIC_IMAGE_ERROR = "PAAS-10414"; // 修改公共镜像信息异常

	// 系统告警
	public static final String QUERY_ALARM_ERROR = "PAAS-10601";// 查询系统告警异常
	public static final String DELETE_ALARM_ERROR = "PAAS-10602";// 删除系统告警异常

	// 审计日志
	public static final String QUERY_SYS_LOG_ERROR = "PAAS-10701";// 查询审计日志异常

	// 应用日志
	public static final String QUERY_APP_LOG_ERROR = "PAAS-10702";// 获取应用日志列表异常
	public static final String DOWNLOAD_APP_LOG_ERROR = "PAAS-10703";// 下载应用日志异常
	public static final String DELETE_APP_LOG_ERROR = "PAAS-10706";// 删除应用日志异常

	// 应用服务标准输出日志
	public static final String QUERY_APPSERVICE_STD_LOG_ERROR = "PAAS-10704";// 获取应用服务标准输出日志的URL错误
	
	//容器历史日志
	public static final String QUERY_CH_STD_LOG_ERROR = "PAAS-10705";//获取应用服务容器历史日志异常

	// 系统管理错误码
	// 角色
	public static final String QUERY_ROLE_PERMISSION_ERROR = "PAAS-10901";// 通过角色ID查询拥有操作权限异常
	public static final String QUERY_ROLE_PERMISSION_FIND_ALL_ERROR = "PAAS-10911"; // 查询角色和权限关系异常
	public static final String DELETE_ROLE_PERMISSION_ERROR = "PAAS-10909";// 删除所有角色与该应用id关联的权限异常
	public static final String FIND_PAGE_ROLE_ERROR = "PAAS-10902";// 查询角色列表异常
	public static final String FIND_ALL_ROLE_ERROR = "PAAS-10903";// 查询所有角色异常
	public static final String DELETE_ROLE_ERROR = "PAAS-10904";// 删除角色异常
	public static final String CREATE_ROLE_ERROR = "PAAS-10905";// 创建角色异常
	public static final String QUERY_BY_ROLE_ID_ERROR = "PAAS-10906";// 通过角色id查询角色信息异常
	public static final String UPDATE_ROLE_ERROR = "PAAS-10907";// 修改角色异常
	public static final String QUERY_ROLE_BY_NAME_ERROR = "PAAS-10908";// 通过角色名称查询角色信息异常
	public static final String ROLE_USED_BY_ERROR = "PAAS-10910";// 角色被用户使用不能删除
	// 权限
	public static final String QUERY_PERMISSION_FIND_ALL_ERROR = "PAAS-11001";// 查询所有操作权限
	public static final String QUERY_USERROLE_AND_OPERTYPE_ERROR = "PAAS-11002";//查询当前用户的角色及操作类型
	// 用户
	public static final String QUERY_USER_ROLE_ERROR = "PAAS-10801";// 通过用户查询拥有角色异常
	public static final String DELETE_USER_ROLE = "PAAS-10802";// 删除用户拥有的角色
	public static final String UPDATE_USER_ROLE = "PAAS-10803";// 修改用户拥有的角色
	public static final String QUERY_USER_ROLE_BY_ROLE_ID = "PAAS-10804";// 通过角色ID查询查询用户与角色的关联
	public static final String DELETE_USER_ROLE_BY_ROLE_ID = "PAAS-10805";// 通过角色ID查询删除用户与角色的关联
	public static final String FIND_PAGE_USER_ERROR = "PAAS-10806";// 查询用户列表
	public static final String FIND_USER_BY_NAME_ERROR = "PAAS-10807";// 查询用户通过用户名称
	public static final String FIND_USER_ALL_ERROR = "PAAS-10808";// 查询所有用户
	public static final String CREATE_USER_ERROR = "PAAS-10809";// 创建用户
	public static final String DELETE_USER_ERROR = "PAAS-10810";// 删除用户
	public static final String FIND_USER_BY_ID_ERROR = "PAAS-10811";// 查询用户通过用户ID
	public static final String UPDATE_USER_ERROR = "PAAS-10812";// 修改用户
	public static final String QUERY_USER_PERMISSION_ERROR = "PAAS-10813";// 查询用户权限
	public static final String QUERY_USER_ROLE_ADMIN_ERROR = "PAAS-10814";// 查询用户是否拥有admin权限
	public static final String UPDATE_USER_PASSWORD_ERROR = "PAAS-10815";// 修改用户密码(旧密码错误)
	public static final String UPDATE_USER_EXIST_ERROR = "PAAS-10816";// 修改用户(用户账号已存在)
	public static final String CREATE_USER_EXIST_ERROR = "PAAS-10817";// 创建用户(用户账号已存在)
	
	// grafana
	public static final String QUERY_GRAFANA_ERROR = "PAAS-11101";
	public static final String CREATE_GRAFANA_ERROR = "PAAS-11102";
	public static final String DELETE_GRAFANA_ERROR = "PAAS-11103";

	// 构建管理
	public static final String QUERY_BUILD_ERROR = "PAAS-11201";
	public static final String QUERY_BUILD_LIST_ERROR = "PAAS-11202";
	public static final String QUERY_REPOSITORY_ERROR = "PAAS-11203";
	public static final String VERIFY_REPOSITORY_ERROR = "PAAS-11204";
	public static final String QUERY_BUILD_RECORD_LIST_ERROR = "PAAS-11205";
	public static final String CREATE_BUILD_ERROR = "PAAS-11206";
	public static final String MODIFY_BUILD_ERROR = "PAAS-11207";
	public static final String DELETE_BUILD_ERROR = "PAAS-11208";
	public static final String EXECUTE_BUILD_ERROR = "PAAS-11209";
	public static final String QUERY_BUILD_RECORD_LOG_ERROR = "PAAS-11210";
	public static final String QUERY_BUILD_RECORD_STATUS_ERROR = "PAAS-11211";
	public static final String WEB_SOCKET_STREAM_CLOSE_ERROR = "PAAS-11212";
	public static final String QUERY_BRANCHES_ERROR = "PAAS-11213";
	
	//总览
	public static final String QUERY_SERVICE_STATE_ERROR = "PAAS-11601";
	public static final String QUERY_INSTANCE_STATE_ERROR = "PAAS-11602";
	public static final String QUERY_ALARM_DETAILS_ERROR = "PAAS-11603";//查询告警统计数据错误
	public static final String QUERY_ASSIGNED_RESOURCE_RATE_ERROR = "PAAS-11604";
	public static final String QUERY_ACTUAL_RESOURCE_RATE_ERROR = "PAAS-11605";
	public static final String QUERY_FLOW_BUILD_STATE_ERROR = "PAAS-11606";
	public static final String QUERY_TOTAL_RESOURCE_ERROR = "PAAS-11607";//查询资源统计数据错误
	// CI/CD
	public static final String QUERY_CODE_DOWNLOAD_AND_CHECK_ERROR = "PAAS-11501";
	public static final String QUERY_REPORT_ERROR="PAAS-11502";
	public static final String QUERY_STEPDETAIL_ERROR="PAAS-11503";// 查询流水步骤详情错误
	public static final String MODIFY_CODE_DOWNLOAD_AND_CHECK_ERROR = "PAAS-11504";
	public static final String QUERY_STEPRECODELIST_ERROR="PAAS-11505";//单步骤执行记录构建列表查询
	public static final String MODIFY_IS_CODE_CHECK_ERROR = "PAAS-11506";
	public static final String QUERY_AUTOTEST_ERROR="PAAS-11507";// 查询自动化测试配置信息错误
	public static final String UPDATE_AUTOTEST_ERROR="PAAS-11508";// 修改自动化测试配置信息错误
	public static final String CREATE_FLOW_ERROR="PAAS-11509";// 创建流水错误
	public static final String QUERY_FLOW_ERROR="PAAS-11510";// 模糊查询(流水列表)错误
	public static final String QUERY_FLOW_DETAIL_ERROR="PAAS-11513";// 查询单个流水错误
	public static final String QUERY_COMPILE_BUILD_ERROR="PAAS-11511";// 查询编译&构建配置信息错误
	public static final String MODIFY_COMPILE_BUILD_ERROR="PAAS-11512";// 修改编译&构建配置信息错误
	public static final String MODIFY_FLOW_ERROR="PAAS-11514";// 修改流水错误
	public static final String DELETE_FLOW_ERROR="PAAS-11515";// 删除流水错误
	public static final String QUERY_CODEDETAILSLIST_ERROR="PAAS-11516";// 查询代码详情列表错误
	public static final String SAVE_CHECK_ERROR = "PAAS-11517"; //保存是否安全状态错误
	public static final String QUERY_DEPSCAN_ENTITY_ERROR = "PAAS-11518";//查询部署&扫描详情错误
	public static final String UPDATE_DEPSCAN_ERROR = "PAAS-11519";//修改部署&扫描配置信息错误
	public static final String QUERY_CODEDETAILS_ERROR="PAAS-11520";// 查询代码详情错误
	public static final String QUERY_AUTOTESTREPORTLIST_ERROR="PAAS-11521";// 查询自动化测试报表错误
	public static final String QUERY_STEP_LOGS_ERROR = "PAAS-11522";//查询单步骤记录日志信息错误
	public static final String QUERY_PROBLEMDETAILS_ERROR="PAAS-11523";// 查询问题代码详情错误
	public static final String QUERY_FLOW_BY_ID="PAAS-11524";// 查询单个流水详情错误
	public static final String QUERY_INTE_TEST_REPORT_ERROR = "PAAS-11529";//查询集成测试报表错误
	
	
	public static final String QUERY_DOWNLOAD_CHECK_ERROR="PAAS-11525";// 查询下载检查错误
	public static final String QUERY_COMPILE_BUILD_LIST_ERROR="PAAS-11526";// 查询编译构建列表错误
	public static final String QUERY_AUTO_MATE_TEST_ERROR="PAAS-11527";// 查询自动测试错误
	public static final String QUERY_INTEGRATION_TEST="PAAS-11528";// 查询集成测试错误
	public static final String QUERY_CODE_QUALITY_REPORT_ERROR = "PAAS-11530";//查询代码质量报表错误
	public static final String QUERY_BUILD_REPORT_ERROR = "PAAS-11531";//查询构建记录报表错误
	public static final String PROMPT_BUILD_ERROR = "PAAS-11532"; // 立即构建错误
	public static final String QUERY_FLOW_EXCUTE_RECORD_LIST_ERROR = "PAAS-11533"; // 流水执行记录列表错误
	public static final String QUERY_FLOW_EXCUTE_RECORD_ERROR = "PAAS-11534"; // 流水执行记录错误
	public static final String QUERY_INTETEST_ERROR="PAAS-11535";// 查询集成测试配置信息错误
	public static final String UPDATE_INTETEST_ERROR="PAAS-11536";// 修改集成测试配置信息错误
	public static final String QUERY_FLOW_NAMES_LIST_ERRORS = "PAAS-11537";//查询流水名称集合错误
	public static final String QUERY_PERFORMANCETEST_ERROR="PAAS-11538";// 查询性能测试配置信息错误
	public static final String UPDATE_PERFORMANCETEST_ERROR="PAAS-11539";// 修改性能测试配置信息错误

	
	public static final String QUERY_UNIT_TEST_REPORT_LIST_ERROR="PAAS-11540";// 查询单元测试报表错误
	public static final String QUERY_PERFORMANCE_TEST_REPORT_LIST_ERROR="PAAS-11541";// 查询性能测试报表错误
	
	public static final String QUERY_AUTO_TEST_REPORT_ERROR = "PAAS-11542";//查询自动化测试报表（外）错误
	public static final String QUERY_INTE_TEST_REPORT_LIST_ERROR = "PAAS-11543";//查询集成测试报表（外）错误
	public static final String QUERY_UNIT_TEST_REPORT_ERROR = "PAAS-11544";//查询单元测试报表（外）错误
	public static final String QUERY_AUTO_LINE_CHART_ERROR = "PAAS-11545";//查询自动化测试问题折线图错误
	public static final String QUERY_INTE_LINE_CHART_ERROR = "PAAS-11546";//查询集成测试问题折线图错误
	public static final String QUERY_UNIT_LINE_CHART_ERROR = "PAAS-11547";//查询单元测试问题折线图错误
	public static final String QUERY_FLOW_NAMESLIST_ERRORS = "PAAS-11548";//查询流水名称集合错误
	public static final String QUERY_MEASURE_BUILD_ERROR ="PAAS-11549";//查询概览构建信息错误
	public static final String QUERY_MEASURE_QUALITYANALYSIS_ERROR ="PAAS-11550";//查询概览质量分析错误
	public static final String QUERY_MEASURE_DEPLOY_ERROR="PAAS-11551";//查询概览部署错误
	public static final String STOP_FLOW_ERROR = "PAAS-11552"; // 停止流水错误
	public static final String COPY_FLOW_ERROR = "PAAS-11553"; // 复制流水错误
	public static final String QUERY_BUILD_REPORT_ABROAD_ERROR="PAAS-11554";//查询构建记录报表（外）
	public static final String QUERY_RELEASE_ERROR = "PAAS-11555"; //查询发布的配置信息错误
	public static final String MODIFY_RELEASE_ERROR = "PAAS-11556"; //修改发布的配置信息错误
	public static final String RELEASE_PRODUCT_ERROR = "PAAS-11557"; //镜像版本由开发直接发布生产错误
	public static final String QUERY_CODE_QUALITY_REPORT_LIST_ERROR = "PAAS-11558"; //查询代码质量报表（外）错误
	public static final String QUERY_CODE_QUALITY_LINE_CHART_ERROR = "PAAS-11559"; //查询代码质量（构建次数）折线图（外）错误
	public static final String QUERY_CODE_REPO_LIST_ERROR="PAAS-11560";//查询代码库列表错误
	public static final String QUERY_CODE_REPO_ERROR="PAAS-11561";//查询代码库图表错误
	public static final String QUERY_PERFORMANCE_TEST_REPORT_ERROR = "PAAS-11562"; // 查询性能测试报表(外)列表错误
	public static final String QUERY_PERFORMANCE_TEST_LINE_CHART_ERROR = "PAAS-11563"; // 查询性能测试折线图(外)错误
	
	
	// 应用服务管理
	public static final String QUERY_APPSERVICELIST_ERROR = "PAAS-10201"; // 查询应用服务列表错误
	public static final String QUERY_APPSERVICEDETAIL_ERROR = "PAAS-10202"; // 查询单个应用服务详情错误
	public static final String CREATE_APPSERVICE_ERROR = "PAAS-10203"; // 创建单个应用服务错误
	public static final String MODIFY_APPSERVICE_ERROR = "PAAS-10204"; // 修改单个应用服务错误
	public static final String START_APPSERVICE_ERROR = "PAAS-10205"; // 启动单个应用服务错误
	public static final String STOP_APPSERVICE_ERROR = "PAAS-10206"; // 停止单个应用服务错误
	public static final String DELETE_APPSERVICE_ERROR = "PAAS-10207";// 删除单个应用服务错误
	public static final String RESTART_APPSERVICE_ERROR = "PAAS-10208";// 重启单个应用服务错误
	public static final String BATCH_START_APPSERVICE_ERROR = "PAAS-10209";// 批量启动应用服务错误
	public static final String BATCH_STOP_APPSERVICE_ERROR = "PAAS-10210";// 批量停止应用服务错误
	public static final String BATCH_DELETE_APPSERVICE_ERROR = "PAAS-10211";// 批量删除应用服务错误
	public static final String BATCH_RESTART_APPSERVICE_ERROR = "PAAS-10212";// 批量重启应用服务错误
	public static final String SEND_SIGNAL_TO_APPSERVICE_ERROR = "PAAS-10213";// 发送信号错误
	public static final String RESTART_APPSERVICE_INSTANCE_ERROR = "PAAS-10214";// 重启服务实例错误
	public static final String QUERY_APPSERVICE_INSTANCE_ERROR = "PAAS-10215"; // 查询服务实例列表错误
	public static final String DIAGNOSIS_APPSERVICE_ERROR = "PAAS-10216"; // 服务实例诊断错误
	public static final String QUERY_APPSERVICE_INST_ERROR = "PAAS-10217"; // 查询应用服务（包含实例）错误
	public static final String MODIFY_APPSERVICE_CONF_ERROR = "PAAS-10218"; // 修改应用服务配置错误
	public static final String CREATE_APPSERVICE_ENVSCONF_NOTFOUND_ERROR = "PAAS-10219"; // 创建应用服务时生成的环境变量文件路径错误
	public static final String CREATE_APPSERVICE_ENVSCONF_IO_ERROR = "PAAS-10220"; // 创建应用服务时生成环境变量文件IO错误
	public static final String ALLRESTART_APPSERVICE_ERROR = "PAAS-10221";// 一键重启单个应用服务以及绑定的基础服务错误
	public static final String DIALOG_CONTAINERDETAILS_ERROR = "PAAS-10222"; //查询实例容器详情错误
	public static final String QUERY_CLUSTER_LIST_ERROR = "PAAS-10223";//查询集群列表错误
	public static final String CHECK_IPAAS_ERROR = "PAAS-10224";//检测基础服务错误
	public static final String QUERY_CLUSER_HOST_IP_ERROR = "PAAS-10225";//查询集群下的主机IP错误
	public static final String QUERY_APPSERVICE_INSTANCE_DETAIL_ERROR = "PAAS-10226"; // 查询服务实例详情错误
	public static final String BATCH_UPGRADE_APPSERVICE_VERSION_ERROR = "PAAS-10227";// 一键升级服务版本错误
	public static final String IS_CHECK_IP_ERROR="PAAS-10228"; //校验IP是否冲突错误
	public static final String IS_EXCESS_ERROR = "PAAS-10229"; //校验CPU、内存超额错误

	// 应用管理
	public static final String QUERY_APPLICATIONLIST_ERROR = "PAAS-10101";// 查询应用列表错误
	public static final String CREATE_APPLICATION_ERROR = "PAAS-10102";// 创建应用错误
	public static final String UPDATE_APPLICATION_ERROR = "PAAS-10103";// 修改应用错误
	public static final String DELETE_APPLICATION_ERROR = "PAAS-10104";// 删除应用错误
	public static final String QUERY_DATACORE_ERROR = "PAAS-10105";// 查询数据中心错误
	public static final String QUERY_APPLICATION_CLUSTER_ERROR = "PAAS-10106";// 查询集群信息错误
	public static final String QUERY_APPLICATION_INFO_ERROR = "PAAS-10107";// 查询应用详情信息错误
	public static final String NOT_ADMIN_ERROR = "PAAS-10108";// 非管理员不能创建应用
	public static final String MIGRATE_APPLICATION_ERROR = "PAAS-10109";//应用迁移错误
	public static final String IMPORT_APPLICATION_ERROR = "PAAS-10110";//导入迁移错误
	public static final String CREATE_DNS_INFO_ERROR = "PAAS-10111";//创建DNS配置信息错误
	public static final String QUERY_DNS_INFO_ERROR = "PAAS-10112";//查询容灾DNS配置信息错误
	public static final String UN_DISASTER_RECOVERY = "PAAS-10113";//容灾恢复错误
	public static final String DISASTER_RECOVERY = "PAAS-10114";//容灾错误
	public static final String DISASTER_STATUS = "PAAS-10115";//容灾状态
	

	// 基础服务
	public static final String QUERY_IPAASSERVICELIST_ERROR = "PAAS-10301"; // 查询基础服务列表错误
	public static final String QUERY_IPAASSERVICEDETAIL_ERROR = "PAAS-10302"; // 查询单个基础服务详情错误
	public static final String QUERY_IPAASSERVICEENV_ERROR = "PAAS-10303";// 查询基础服务发布的环境变量
	public static final String DELETE_IPAASSERVICE_ERROR = "PAAS-10310"; // 删除基础服务错误
	public static final String QUERY_IPAASSERVICE_INST_ERROR = "PAAS-10311"; // 查询基础服务（包含实例）错误
	public static final String QUERY_DEFAULT_CONFIG_ERROR = "PAAS-10312"; // 查询基础服务默认配置错误
	public static final String CREATE_IPAAS_SERVICE_ERROR = "PAAS-10313"; // 创建基础服务错误
	public static final String MODIFY_IPAAS_SERVICE_ERROR = "PAAS-10314"; // 修改基础服务错误
	public static final String QUERY_IPAAINST_ERROR = "PAAS-10315";// 查询基础服务实例错误
	public static final String DIAGNOSIS_IPAAINST_ERROR = "PAAS-10316";// 诊断基础服务实例错误
	public static final String START_IPAASERVICE_ERROR = "PAAS-10317";// 启动基础服务错误
	public static final String STOP_IPAASERVICE_ERROR = "PAAS-10318";// 停止基础服务错误
	public static final String RESTART_IPAASERVICE_ERROR = "PAAS-10319";// 重启基础服务错误
	public static final String FORCESTOP_IPAASERVICE_ERROR = "PAAS-10320";// 强制停止基础服务错误
	public static final String FORCERESTART_IPAASERVICE_ERROR = "PAAS-10321";// 强制重启基础服务错误

	// 实例管理
	public static final String QUERY_INSTANCE_ERROR = "PAAS-10201";
	public static final String RESTART_INSTANCE_ERROR = "PAAS-10202";
	public static final String FORCED_RESTART_INSTANCE_ERROR = "PAAS-10203";
	public static final String DIAGNOSIS_INSTANCE_ERROR = "PAAS-10204";
	public static final String EXE_COMMAND_INSTANCE_ERROR = "PAAS-10205";

	// 系统监控
	public static final String SYSTEM_MONITOR_QUERY_CLUSTER_ERROR = "PAAS-10501";
	public static final String SYSTEM_MONITOR_QUERY_MINION_ERROR = "PAAS-10502";
	public static final String SYSTEM_MONITOR_QUERY_APP_ERROR = "PAAS-10503";
	public static final String SYSTEM_MONITOR_QUERY_COUNT_INFO_ERROR = "PAAS-10504";
	public static final String SYSTEM_MONITOR_QUERY_DRAW_DATA_ERROR = "PAAS-10505";
	public static final String SYSTEM_MONITOR_QUERY_MAX_MIN_ERROR = "PAAS-10506";
	public static final String SYSTEM_MONITOR_QUERY_APPGROUP_ERROR = "PAAS-10507";
	public static final String READ_JSON_TEMPLATE_FILE_ERROR = "PAAS-10508";// 读取json模板文件错误
	public static final String DELETE_JSON_FILE_ERROR = "PAAS-10509";// 删除json文件错误
	public static final String GET_FULL_FILE_NAME_ERROR = "PAAS-10510";// 获取完整的文件名错误

	// 系统监控样式
	public static final int PICTURE_WIDTH = 365;
	public static final int PICTURE_HEIGHT = 280;
	public static final int APP_PICTURE_WIDTH = 450;
	public static final int APP_PICTURE_HEIGHT = 280;
	public static final int SAMPLE_SIZE = 240;
	public static final String OPERATE_IS_ERROR = "isError";
	
	//监控运维模块
	//数据中心
	public static final String QUERY_DATACENTER_LIST_ERROR = "PAAS-11311";
	public static final String CREATE_DATACENTER_ERROR = "PAAS-11312";
	public static final String DELETE_DATACENTER_ERROR = "PAAS-11313";
	public static final String UPDATE_DATACENTER_DESC_ERROR = "PAAS-11314";
	//集群
	public static final String QUERY_CLUSTERS_LIST_ERROR = "PAAS-11321";
	public static final String CREATE_CLUSTERS_ERROR = "PAAS-11322";
	public static final String DELETE_CLUSTERS_ERROR = "PAAS-11323";
	public static final String UPDATE_CLUSTERS_DESC_ERROR = "PAAS-11324";
	//主机
	public static final String QUERY_HOST_LIST_ERROR = "PAAS-11331";
	public static final String DELETE_HOST_ERROR = "PAAS-11332";
	public static final String CREATE_HOST_ERROR = "PAAS-11333";
	public static final String QUERY_DEPLOY_COMPONENT_LIST_FOR_NODE_ERROR = "PAAS-11334";
	public static final String QUERY_DEPLOY_SCHEME_LIST_ERROR = "PAAS-11335";
	public static final String QUERY_DEPLOY_COMPONENT_LIST_FOR_PAAS_ERROR = "PAAS-11336";
	public static final String DELETE_ALLHOST_ERROR = "PAAS-11337";
	//告警
	public static final String QUERY_SYSALARM_LIST_ERROR = "PAAS-11341";
	public static final String QUERY_SYSALARM_CONDITION_ERROR = "PAAS-11342";
	public static final String UPDATE_SYSALARM_CONDITION_ERROR = "PAAS-11343";
	//组件
	public static final String QUERY_COMPONENT_INST_ERROR = "PAAS-11351";
	public static final String OPERATION_COMPONENT_ERROR = "PAAS-11352";
	//镜像同步任务（Job）
	public static final String QUERY_JOB_LIST_ERROR = "PAAS-11361";
	public static final String LOAD_PUBLIC_IMAGES__ERROR = "PAAS-11362";
	
	//灰度版本
	public static final String QUERY_GRAYRELEASEDETAIL_ERROR = "PAAS-11401";
	public static final String QUERY_GRAYRELEASELIST_ERROR = "PAAS-11402";
	public static final String CREATE_GRAYRELEASE_ERROR = "PAAS-11403";
	public static final String UPDATE_GRAYRELEASE_ERROR = "PAAS-11404";
	public static final String DELETE_GRAYRELEASE_ERROR = "PAAS-11405";
	public static final String START_GRAYRELEASE_ERROR = "PAAS-11406";
	public static final String STOP_GRAYRELEASE_ERROR = "PAAS-11407";
	public static final String UPGRADE_GRAYRELEASE_ERROR = "PAAS-11408";
	public static final String MODIFY_DEPLOYMENTTYPE_ERROR = "PAAS-11409";  //修改部署方式错误
	public static final String QUERY_HOST_AND_COMP_DETAILS_ERROR = "PAAS-11410";
	
	

}
