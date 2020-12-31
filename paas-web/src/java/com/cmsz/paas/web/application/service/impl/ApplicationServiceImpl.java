package com.cmsz.paas.web.application.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmsz.paas.common.model.controller.entity.AppEntity;
import com.cmsz.paas.common.model.controller.entity.ClusterEntity;
import com.cmsz.paas.common.model.controller.entity.ClusterToAppEntity;
import com.cmsz.paas.common.model.controller.entity.DataCenterEntity;
import com.cmsz.paas.common.model.controller.entity.DisasterToleranceConfEntity;
import com.cmsz.paas.common.model.controller.entity.MigrationRecordEntity;
import com.cmsz.paas.common.model.controller.request.DisasterToleranceConfList;
import com.cmsz.paas.common.model.controller.response.AppDetail;
import com.cmsz.paas.common.model.controller.response.AppList;
import com.cmsz.paas.common.model.controller.response.ClusterList;
import com.cmsz.paas.common.model.controller.response.DataCenterList;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.model.controller.response.MigrationRecordList;
import com.cmsz.paas.common.model.response.ResponseInfo;
import com.cmsz.paas.web.application.dao.ApplicationDao;
import com.cmsz.paas.web.application.model.ApplicationInfo;
import com.cmsz.paas.web.application.model.ApplicationInfoUtil;
import com.cmsz.paas.web.application.model.ClusterInfo;
import com.cmsz.paas.web.application.model.DNSInfo;
import com.cmsz.paas.web.application.model.DNSModel;
import com.cmsz.paas.web.application.model.DataCenterInfo;
import com.cmsz.paas.web.application.model.DetailsApp;
import com.cmsz.paas.web.application.model.createAppInfo;
import com.cmsz.paas.web.application.service.ApplicationService;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.base.util.RestUtils;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.opensymphony.xwork2.ActionContext;

@Service("applicationRestService")
public class ApplicationServiceImpl implements ApplicationService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private com.cmsz.paas.web.appservice.service.ApplicationService applicationService;

	@Autowired
	private ApplicationDao applicationRestDao;

	@Autowired
	private RolePerRelationService rolePerRelationService;

	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public List<ApplicationInfo> queryRestAppList(String ids, String name) {
		List<ApplicationInfo> list = new ArrayList<ApplicationInfo>();

		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			if (name == null)
				name = "";
			String url = RestUtils.restUrlTransform("queryApplicationUrl", ids,
					name);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					AppList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppList appList = (AppList) responseInfo.getData();
			// 调用接口成功并解析是素具
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (appList != null && appList.getAppList() != null) {
					for (int i = 0; i < appList.getAppList().size(); i++) {
						AppEntity appEntity = appList.getAppList().get(i);
						ApplicationInfo appInfo = translateApplicationInfo(appEntity);
						list.add(appInfo);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询应用列表错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPLICATIONLIST_ERROR, e.getMessage());
			}
		}
		return list;
	}

	private ApplicationInfo translateApplicationInfo(AppEntity appEntity) {
		ApplicationInfo info = new ApplicationInfo();
		String clusterName = "";
		String clusterName1 = "";
		info.setMigrationRecords(appEntity.getMigrationRecords());
		appEntity.getMigrationRecords();
		if (appEntity.getClusters() != null) {
			for (ClusterToAppEntity entity : appEntity.getClusters()) {
				if (entity.getType() == 3) {
					info.setOriginalDataName(entity.getCluster()
							.getDataCenterName());
					if (clusterName!=null&&clusterName!="") {
						clusterName=clusterName+","+entity.getCluster().getName();
					}else{
						clusterName=entity.getCluster().getName();
					}
					info.setIsDisasterTolerance(entity.getIsDisasterTolerance());
				} else if (entity.getType() == 4) {
					info.setMigrationDataName(entity.getCluster()
							.getDataCenterName());
					if (clusterName1!=null&&clusterName1!="") {
						clusterName1=clusterName1+","+entity.getCluster().getName();
					}else{
						clusterName1=entity.getCluster().getName();
					}
				}
			}
		}
		info.setOriginalCluster(clusterName);
		info.setMigrationCluster(clusterName1);
		info.setId(appEntity.getId());
		info.setAppName(appEntity.getName());
		info.setUserPwd(new StringBuffer().append(appEntity.getHarborUser())
				.append("/").append(appEntity.getHarborPwd()).toString());
		info.setDesc(appEntity.getDescription());
		info.setCreateTime(format.format(appEntity.getCreateTime()));
		return info;
	}

	/**
	 * 把可变参数用逗号分隔连接
	 * 
	 * @param strings
	 *            可变参数
	 * @return 用逗号分隔的字符串
	 */
	@SuppressWarnings("unused")
	private String concatString(String... strings) {
		String str = "";
		if (strings != null && strings.length > 0) {
			for (String s : strings) {
				if (s == null || "".equals(s)) {
					continue;
				}
				str += s + ",";
			}
			int index = str.lastIndexOf(",");
			if (index != -1) {
				str = str.substring(0, index);
			}
		}
		return str;
	}

	/***
	 * 调接口查询数据中心数据
	 */
	@Override
	public List<DataCenterInfo> quertCenterLists() {
		List<DataCenterInfo> dataCenterInfos = new ArrayList<DataCenterInfo>();
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("qyeryDataCenterUrl", "");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					DataCenterList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			DataCenterList dataCenterList = (DataCenterList) responseInfo
					.getData();
			// 调用接口成功并解析是素具
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (dataCenterList != null
						&& dataCenterList.getDataCenterList() != null) {
					for (int i = 0; i < dataCenterList.getDataCenterList()
							.size(); i++) {
						DataCenterEntity centerEntity = dataCenterList
								.getDataCenterList().get(i);
						DataCenterInfo appInfo = translateDataCenterInfo(centerEntity);
						dataCenterInfos.add(appInfo);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询应用列表错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_DATACORE_ERROR,
						e.getMessage());
			}
		}
		return dataCenterInfos;
	}

	private DataCenterInfo translateDataCenterInfo(DataCenterEntity centerEntity) {
		DataCenterInfo centerInfo = new DataCenterInfo();
		centerInfo.setId(centerEntity.getId());
		centerInfo.setName(centerEntity.getName());
		return centerInfo;

	}

	@Override
	public List<ClusterInfo> queryCluster() {
		List<ClusterInfo> clusterInfos = new ArrayList<ClusterInfo>();
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryClusters", "");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					ClusterList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			ClusterList clusterList = (ClusterList) responseInfo.getData();
			// 调用接口成功并解析是素具
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (clusterList != null && clusterList.getClusterList() != null) {
					for (int i = 0; i < clusterList.getClusterList().size(); i++) {
						ClusterEntity clusterEntity = clusterList
								.getClusterList().get(i);
						ClusterInfo appInfo = translateClusterInfo(clusterEntity);
						clusterInfos.add(appInfo);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询应用列表错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPLICATION_CLUSTER_ERROR,
						e.getMessage());
			}
		}
		return clusterInfos;
	}

	/***
	 * 转换集群Entity
	 * 
	 * @param clusterEntity
	 * @return
	 */
	private ClusterInfo translateClusterInfo(ClusterEntity clusterEntity) {
		ClusterInfo clusterInfo = new ClusterInfo();
		clusterInfo.setId(clusterEntity.getId());
		clusterInfo.setName(clusterEntity.getName());
		return clusterInfo;
	}

	private List<ClusterToAppEntity> getClusterToAppEntity(
			Map<String, List<String>> map, long deveDataCenterId,
			long testDataCenterId, Map<String, String> mapList) {
		List<ClusterToAppEntity> clusterEntities = new ArrayList<ClusterToAppEntity>();
		Iterator<String> iterator = map.keySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<String> tempList = map.get(key);
			for (String clusterId : tempList) {
				ClusterToAppEntity entity = new ClusterToAppEntity();
				entity.setClusterId(Long.valueOf(clusterId));
				entity.setType("Development".equals(key) ? 1 : "Test"
						.equals(key) ? 2 : "Production".equals(key) ? 3 : 4);
				if ("Production".equals(key)
						&& (clusterId.equals(mapList.get("productionByApaas")) || clusterId
								.equals(mapList.get("productionByIpaas")))) {
					entity.setIsDisasterTolerance(1);
				}
				ClusterEntity clusterEntity = new ClusterEntity();
				clusterEntity.setDataCenterId(i == 0 ? deveDataCenterId
						: testDataCenterId);
				clusterEntity.setId(Long.valueOf(clusterId));
				entity.setCluster(clusterEntity);
				clusterEntities.add(entity);
				i++;
			}

		}
		return clusterEntities;
	}

	private AppEntity translateAppEntityInfo(ApplicationInfoUtil infoUtil)
			throws Exception {
		infoUtil.setDisasterToleranceProByApaas(translateString(infoUtil
				.getDisasterToleranceProByApaas()));
		infoUtil.setDisasterToleranceProByIpaas(translateString(infoUtil
				.getDisasterToleranceProByIpaas()));
		Map<String, String> map = new HashMap<String, String>();
		map.put("productionByApaas", infoUtil.getDisasterToleranceProByApaas());
		map.put("productionByIpaas", infoUtil.getDisasterToleranceProByIpaas());
		AppEntity entity = new AppEntity();
		entity.setName(infoUtil.getAppName());
		entity.setDescription(infoUtil.getDesc());
		entity.setCreateBy(infoUtil.getLoginName());
		// entity.setCreateTime(new Date());
		List<Map<String, List<String>>> list = infoUtil.getTableData();
		if (list.size() > 1) {
			if (list.get(1) != null) {
				entity.setClusters(getClusterToAppEntity(list.get(1),
						Long.valueOf(infoUtil.getDeveDataCoreId()),
						Long.valueOf(infoUtil.getTestDataCoreId()), map));
			}
		}
		return entity;
	}

	private String translateString(String clusterId) {
		if (clusterId != null && clusterId.indexOf("t") > 0) {
			clusterId = clusterId.substring(0, clusterId.indexOf("t"));
		}
		return clusterId;
	}

	private createAppInfo translateAppDetailEntityInfo(AppEntity appEntity,
			List<DataCenterInfo> centerInfos, List<ClusterInfo> clusterInfos) {

		createAppInfo appInfo = new createAppInfo();
		appInfo.setId(appEntity.getId());
		appInfo.setName(appEntity.getName());
		appInfo.setDesc(appEntity.getDescription());
		appInfo.setDataCenterInfos(transformationDataCenter(appEntity,
				centerInfos));
		appInfo.setDockerRegistryUser(appEntity.getHarborUser());
		appInfo.setDockerRegistryPwd(appEntity.getHarborPwd());
		appInfo.setClusterInfos(transformationCluster(appEntity.getClusters(),
				appInfo.getDataCenterInfos()));
		appInfo.setDnsInfo(appEntity.getDisasterToleranceConfs());
		appInfo.setCheckeds(checkBoolean);
		appInfo.setClusters(appEntity.getClusters());
		return appInfo;
	}

	private List<List<DataCenterInfo>> transformationDataCenter(
			AppEntity appEntity, List<DataCenterInfo> centerInfos) {
		List<List<DataCenterInfo>> list = new ArrayList<List<DataCenterInfo>>();
		for (int i = 0; i < 2; i++) {
			List<DataCenterInfo> tempList = new ArrayList<DataCenterInfo>();
			for (DataCenterInfo center : centerInfos) {
				DataCenterInfo centerInfo = new DataCenterInfo();
				centerInfo.setId(center.getId());
				centerInfo.setName(center.getName());
				if (i==0) {
					if (appEntity.getClusters().size() > 0&& appEntity.getClusters().get(0).getCluster() != null) {
						for (ClusterToAppEntity clusterToAppEntity : appEntity.getClusters()) {
							if ((clusterToAppEntity.getType()==4||clusterToAppEntity.getType()==1)&&center.getId()==clusterToAppEntity.getCluster().getDataCenterId()) {
								centerInfo.setChecked(true);
								break;
							}
						}
		
					}
				}else{
					if (appEntity.getClusters().size() > 0&& appEntity.getClusters().get(0).getCluster() != null) {
						for (ClusterToAppEntity clusterToAppEntity : appEntity.getClusters()) {
							if ((clusterToAppEntity.getType()==3||clusterToAppEntity.getType()==2)&&center.getId()==clusterToAppEntity.getCluster().getDataCenterId()) {
								centerInfo.setChecked(true);
								break;
							}
						}
		
					}
				}
				tempList.add(centerInfo);
			}
			list.add(tempList);
		}
		return list;
	}

	private List<ClusterInfo> transformationClusterInfo(
			List<com.cmsz.paas.web.monitoroperation.model.Cluster> clusterInfos) {
		List<ClusterInfo> list = new ArrayList<ClusterInfo>();
		for (com.cmsz.paas.web.monitoroperation.model.Cluster clusterInfo : clusterInfos) {
			ClusterInfo info = new ClusterInfo();
			info.setId(Long.valueOf(clusterInfo.getId()));
			info.setName(clusterInfo.getName());
			info.setType(Integer.valueOf(clusterInfo.getType()));
			info.setChecked(false);
			info.setCheckeds(false);
			list.add(info);
		}
		return list;
	}

	private Long getDataCenterId(List<DataCenterInfo> list, boolean flag) {
		Long id = 0L;
		for (DataCenterInfo dataCenterInfo : list) {
			if (dataCenterInfo.isChecked()) {
				id = dataCenterInfo.getId();
				break;
			}
		}
		// 开发测试环境上 如果未选中付默认值
		if ((id == 0 && list.size() > 0) || (!flag && id == 0)) {
			id = list.get(0).getId();
		}

		return id;
	}

	private Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	private boolean[] checkBoolean;

	private List<List<ClusterInfo>> transformationCluster(
			List<ClusterToAppEntity> centerInfos,
			List<List<DataCenterInfo>> dataCenterInfos) {
		boolean currentContext = (boolean) getSessionMap()
				.get("currentContext");
		List<List<ClusterInfo>> infos = new ArrayList<List<ClusterInfo>>();
		for (int j = 0; j < 2; j++) {
			List<ClusterInfo> clusterInfos = transformationClusterInfo(applicationService
					.queryClusterList(String.valueOf(getDataCenterId(
							dataCenterInfos.get(dataCenterInfos.size() > j ? j
									: 0), currentContext)), "-1", "dataCenter","",""));
			List<ClusterInfo> list = new ArrayList<ClusterInfo>();
			List<ClusterInfo> list1 = new ArrayList<ClusterInfo>();
			for (int i = 0; i < clusterInfos.size(); i++) {
				ClusterInfo info = new ClusterInfo();
				info.setId(clusterInfos.get(i).getId());
				info.setName(clusterInfos.get(i).getName());
				info.setType(clusterInfos.get(i).getType());
				if (clusterInfos.get(i).getType() == 2) {
					list1.add(info);
				} else if (clusterInfos.get(i).getType() == 1) {
					list.add(info);
				}
			}
			for (int i = 0; i < list1.size(); i++) {
				list.add(list1.get(i));
			}
			infos.add(list);
		}

		checkBoolean = new boolean[] { false, false, false };
		// 是否为生产环境 开发测试类型为1、2 生产迁移为3、4
		int i = currentContext ? 2 : 4;
		if (currentContext) {
			Collections.swap(infos, 0, infos.size() - 1);
			Collections.swap(dataCenterInfos, 0, dataCenterInfos.size() - 1);
		}
		for (List<ClusterInfo> clusters : infos) {
			for (ClusterInfo clusterInfo : clusters) {
				for (ClusterToAppEntity appEntity : centerInfos) {
					if (i == appEntity.getType()
							&& appEntity.getClusterId() == clusterInfo.getId()) {
						if (appEntity.getIsDisasterTolerance() == 1) {
							clusterInfo.setCheckeds(true);
						}
						clusterInfo.setChecked(true);
						// clusterInfo.setType(appEntity.getType());
						// i-1因为开发测试的基数是1、2 i-3因为生产迁移类型为3、4 最终相减的结果只能为0、1
						checkBoolean[currentContext ? i - 1 : i - 3] = true;
						continue;
					}
				}
			}
			i--;
		}

//		if (!isCheckBoolean(checkBoolean)) {
//			if (!(!currentContext && getDataCheckId(dataCenterInfos, 0) > -1 && getDataCheckId(
//					dataCenterInfos, 1) > -1)) {
//				Collections.swap(infos, 0, infos.size() - 1);
//				Collections
//						.swap(dataCenterInfos, 0, dataCenterInfos.size() - 1);
//			}
//			int i2 = currentContext ? 2 : 4;
//			for (List<ClusterInfo> clusters : infos) {
//				for (ClusterInfo clusterInfo : clusters) {
//					for (ClusterToAppEntity appEntity : centerInfos) {
//						if (i2 == appEntity.getType()
//								&& appEntity.getClusterId() == clusterInfo
//										.getId()) {
//							if (appEntity.getIsDisasterTolerance() == 1) {
//								clusterInfo.setCheckeds(true);
//							}
//							clusterInfo.setChecked(true);
//							// clusterInfo.setType(appEntity.getType());
//							// i-1因为开发测试的基数是1、2 i-3因为生产迁移类型为3、4 最终相减的结果只能为0、1
//							checkBoolean[currentContext ? i2 - 1 : i2 - 3] = true;
//							continue;
//						}
//					}
//				}
//				i2--;
//			}
//		}

		/*
		 * for (ClusterToAppEntity clusterToAppEntity : centerInfos) {
		 * List<ClusterInfo> list = infos.get(i); for (ClusterInfo clusterInfo :
		 * list) { if((clusterToAppEntity.getClusterId() == clusterInfo.getId())
		 * && (i==clusterToAppEntity.getType()-1)){
		 * clusterInfo.setChecked(true); checkBoolean[i] = true; continue; } }
		 * i++; }
		 */

		return infos;
	}

//	private boolean isCheckBoolean(boolean[] bool) {
//		for (int i = 0; i < bool.length; i++) {
//			if (bool[i]) {
//				return bool[i];
//			}
//		}
//		return false;
//	}
//
//	private int getClusterId(List<List<ClusterInfo>> infos, int i) {
//		for (ClusterInfo cluster : infos.get(i)) {
//			if (cluster.isChecked()) {
//				return Long.valueOf(cluster.getId()).intValue();
//			}
//		}
//		return -1;
//	}

	private int getDataCheckId(List<List<DataCenterInfo>> dataCenterInfos, int i) {
		for (DataCenterInfo dataCenterInfo : dataCenterInfos.get(i)) {
			if (dataCenterInfo.isChecked()) {
				return Long.valueOf(dataCenterInfo.getId()).intValue();
			}
		}
		return -1;
	}

//	private int getCheckBooleanNumber(boolean[] booleans) {
//		int index = 0;
//		for (boolean b : booleans) {
//			if (b)
//				index++;
//		}
//		return index;
//	}

	/***
	 * 查询修改页面信息
	 * 
	 * @param id
	 * @param centerInfos
	 * @return
	 */
	public createAppInfo queryAppInfoToUpdate(long id,
			List<DataCenterInfo> centerInfos, List<ClusterInfo> clusterInfos) {
		createAppInfo appInfo = null;
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"queryApplicationDetailsUrl", id);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					AppDetail.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问查询应用详情的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			AppDetail appDetail = (AppDetail) responseInfo.getData();
			logger.debug("调用查询应用详情rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				if (appDetail != null && appDetail.getAppDetail() != null) {
					AppEntity appEntity = appDetail.getAppDetail();
					appInfo = translateAppDetailEntityInfo(appEntity,
							centerInfos, clusterInfos);
					if(!(boolean) getSessionMap()
							.get("currentContext")){
						List<ClusterToAppEntity> cList =appEntity.getClusters();
						String ids="";
						for (ClusterToAppEntity c : cList) {
							if(c.getType() == 3){
								ids+=c.getCluster().getDataCenterId()+":"+c.getClusterId()+",";
							}
						}
						appInfo.setProductClusterIds(ids);
					}
					
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询应用详情错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPLICATION_INFO_ERROR, e.getMessage());
			}
		}
		return appInfo;
	}

	/***
	 * 调用接口创建应用信息
	 * 
	 * @param infoUtil
	 *            应用信息
	 * @return 新创建的应用ID
	 */
	@Override
	public long createAppInfo(ApplicationInfoUtil infoUtil) {
		long appId;
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			AppEntity appEntity = translateAppEntityInfo(infoUtil);
			String url = RestUtils.restUrlTransform("createApplicationUrl", "");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.create(url,
					appEntity, IdValue.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问创建应用的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用创建应用的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				appId = Long
						.valueOf(((IdValue) responseInfo.getData()).getId());
				logger.info("调用创建应用的rest接口返回成功！ appId:" + appId);
			} else {
				// 如果为Habor错误 则继续执行关系添加 并展示Habor异常提示
				if ("PAAS-20338".equals(retCode)) {
					appId = Long.valueOf(((IdValue) responseInfo.getData())
							.getId());
					rolePerRelationService.createRoleToAppRelation(appId,
							infoUtil);
				}
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("创建应用错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CREATE_APPLICATION_ERROR,
						e.getMessage());
			}
		}

		return appId;

	}

	private AppEntity translateUpdateAppEntityInfo(ApplicationInfoUtil infoUtil) {
		infoUtil.setDisasterToleranceProByApaas(translateString(infoUtil
				.getDisasterToleranceProByApaas()));
		infoUtil.setDisasterToleranceProByIpaas(translateString(infoUtil
				.getDisasterToleranceProByIpaas()));
		Map<String, String> map = new HashMap<String, String>();
		map.put("productionByApaas", infoUtil.getDisasterToleranceProByApaas());
		map.put("productionByIpaas", infoUtil.getDisasterToleranceProByIpaas());
		AppEntity appEntity = new AppEntity();
		appEntity.setDescription(infoUtil.getDesc());
		List<Map<String, List<String>>> list;
		try {
			list = infoUtil.getTableData();
			if (list.size() > 1) {
				if (list.get(1) != null) {
					appEntity.setClusters(getClusterToAppEntity(list.get(1),
							Long.valueOf(infoUtil.getDeveDataCoreId()),
							Long.valueOf(infoUtil.getTestDataCoreId()), map));
					
					String[] proIds = infoUtil.getProductClusterIds().split(",");
					Set<String> set = new HashSet<String>();
					for (int i = 0; i < proIds.length; i++) {
						String id =proIds[i];
						if(id.contains(":")){
							if((!id.split(":")[0].equals(infoUtil.getDeveDataCoreId())) && !set.contains(id.split(":")[1])){
							
								ClusterEntity centity = new ClusterEntity();
								centity.setDataCenterId(Long.valueOf(id.split(":")[0]));
								ClusterToAppEntity cEntity = new ClusterToAppEntity();
								cEntity.setCluster(centity);
								cEntity.setType(3);
								cEntity.setClusterId(Long.valueOf(id.split(":")[1]));
								appEntity.getClusters().add(cEntity);
								set.add(id.split(":")[1]);
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("修改应用时转换数据类型异常:" + e.getMessage());
		}
		return appEntity;
	}

	@Override
	public void updateAppInfo(ApplicationInfoUtil infoUtil) {
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			AppEntity appEntity = translateUpdateAppEntityInfo(infoUtil);
			String url = RestUtils.restUrlTransform("updateApplicationUrl",
					infoUtil.getAppId());
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.update(url,
					appEntity, AppEntity.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问创建应用的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用修改应用的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用修改应用的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("修改应用错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.UPDATE_APPLICATION_ERROR,
						e.getMessage());
			}
		}
	}

	@Override
	public void deleteAppInfo(long id) {
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {

			String url = RestUtils.restUrlTransform("deleteApplicationUrl", id,
					PropertiesUtil.getValue("currentContext"));
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.delete(url,
					ResponseInfo.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问删除应用的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用修改应用的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用删除应用的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("修改应用错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DELETE_APPLICATION_ERROR,
						e.getMessage());
			}
		}
	}

	@Override
	public List<DetailsApp> queryDetailsList(long appId) {
		List<DetailsApp> list = new ArrayList<DetailsApp>();

		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("queryRecordUrl", appId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					MigrationRecordList.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			MigrationRecordList recordList = (MigrationRecordList) responseInfo
					.getData();
			// 调用接口成功并解析是素具
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (recordList != null
						&& recordList.getMigrationRecordList() != null) {
					for (int i = 0; i < recordList.getMigrationRecordList()
							.size(); i++) {
						MigrationRecordEntity recordEntity = recordList
								.getMigrationRecordList().get(i);
						DetailsApp detailsApp = translateDetailsApp(recordEntity);
						list.add(detailsApp);
					}
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询应用列表错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(
						Constants.QUERY_APPLICATIONLIST_ERROR, e.getMessage());
			}
		}
		return list;
	}

	private DetailsApp translateDetailsApp(MigrationRecordEntity recordEntity) {
		DetailsApp detailsApp = new DetailsApp();
		detailsApp.setId(recordEntity.getId());
		detailsApp.setMigrationApaasClusterName(recordEntity
				.getMigrationApaasClusterName());
		detailsApp.setMigrationIpaasClusterName(recordEntity
				.getMigrationIpaasClusterName());
		detailsApp.setOriginalApaasClusterName(recordEntity
				.getOriginalApaasClusterName());
		detailsApp.setOriginalIpaasClusterName(recordEntity
				.getOriginalIpaasClusterName());
		detailsApp.setMigrationDataCenterName(recordEntity
				.getMigrationDataCenterName());
		detailsApp.setMigrationDate(format.format(recordEntity
				.getMigrationDate()));
		detailsApp.setOriginalDataCenterName(recordEntity
				.getOriginalDataCenterName());
		detailsApp.setRecordType(recordEntity.getRecordType());
		return detailsApp;
	}

	@Override
	public void importApp() {

		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("importAppUrl");
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.create(url,
					ResponseInfo.class);
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			// 执行失败
			if (!Constants.REST_CODE_SUCCESS.equals(retCode)) {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("导入应用错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.IMPORT_APPLICATION_ERROR,
						e.getMessage());
			}
		}
	}

	@Override
	public DNSModel queryDNSList(long appId) {// 状态码
		DNSModel d = null;
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform(
					"queryApplicationDetailsUrl", appId);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					AppDetail.class);
			AppDetail appDetail = (AppDetail) responseInfo.getData();
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			// 执行失败
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				if (appDetail != null && appDetail.getAppDetail() != null) {
					d = tranDNSModel(appDetail.getAppDetail());
				}
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("查询DNS错误", e);
			if (e instanceof PaasWebException) {
				// throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.QUERY_DNS_INFO_ERROR,
						e.getMessage());
			}
		}
		return d;
	}

	private DNSModel tranDNSModel(AppEntity appEntity) {
		DNSModel dnsModel = new DNSModel();
		dnsModel.setAppId(appEntity.getId());
		dnsModel.setAppName(appEntity.getName());
		for (ClusterToAppEntity c : appEntity.getClusters()) {
			if (c.getType() == 3) {
				dnsModel.setHostIpName(c.getCluster().getDataCenterName() + "_"
						+ c.getCluster().getName());
			} else if (c.getType() == 4) {
				dnsModel.setSpareIpName(c.getCluster().getDataCenterName()
						+ "_" + c.getCluster().getName());
			}
		}

		List<DNSInfo> list = new ArrayList<DNSInfo>();
		for (DisasterToleranceConfEntity d : appEntity
				.getDisasterToleranceConfs()) {
			DNSInfo info = new DNSInfo();
			info.setDomainName(d.getDomainName());
			info.setHostIp(d.getMasterIp());
			info.setSpareIp(d.getMinionIp());
			list.add(info);
		}
		dnsModel.setDnsList(list);
		return dnsModel;
	}

	private DisasterToleranceConfList tranDis(DNSModel d) {
		DisasterToleranceConfList confList = new DisasterToleranceConfList();
		confList.setAppId(d.getAppId());
		confList.setAppName(d.getAppName());
		List<DisasterToleranceConfEntity> list = new ArrayList<DisasterToleranceConfEntity>();
		if (d.getDnsList() != null) {
			for (DNSInfo ds : d.getDnsList()) {
				DisasterToleranceConfEntity disEntity = new DisasterToleranceConfEntity();
				disEntity.setDomainName(ds.getDomainName());
				disEntity.setMasterIp(ds.getHostIp());
				disEntity.setMinionIp(ds.getSpareIp());
				list.add(disEntity);
			}
		}
		confList.setDisasterToleranceConfList(list);
		return confList;
	}

	@Override
	public void createDNS(DNSModel dnsModel) {
		// 状态码
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("createDNSInfoUrl", "");
			logger.info("开始调用Rest接口：" + url);

			DisasterToleranceConfList dis = tranDis(dnsModel);

			ResponseInfo responseInfo = applicationRestDao.create(url, dis,
					ResponseInfo.class);
			if (responseInfo == null) {
				throw new PaasWebException(Constants.CONTROLLER_CONN_ERROR,
						"无法访问创建容灾配置的Rest接口：" + url);
			}
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			logger.debug("调用创建容灾配置的rest接口返回码：" + retCode + ", 返回信息：" + msg);
			if (retCode.equals(Constants.REST_CODE_SUCCESS)) {
				logger.info("调用创建容灾配置的rest接口返回成功！");
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("创建容灾配置错误", e);
			if (e instanceof PaasWebException) {
				throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.CREATE_DNS_INFO_ERROR,
						e.getMessage());
			}
		}

	}

	@Override
	public IdValue getDisasterStatus(long id) {
		String retCode = "";
		// 返回信息
		String msg = "";
		try {
			String url = RestUtils.restUrlTransform("getDisasterStatusUrl", id);
			logger.info("开始调用Rest接口：" + url);
			ResponseInfo responseInfo = applicationRestDao.get(url,
					IdValue.class);
			IdValue iv = (IdValue) responseInfo.getData();
			retCode = responseInfo.getRetCode();
			msg = responseInfo.getMsg();
			if (Constants.REST_CODE_SUCCESS.equals(retCode)) {
				return iv;
			} else {
				throw new PaasWebException(retCode, msg);
			}
		} catch (Exception e) {
			logger.error("获取容灾状态错误", e);
			if (e instanceof PaasWebException) {
				// throw new PaasWebException(retCode, msg);
			} else {
				throw new PaasWebException(Constants.DISASTER_STATUS,
						e.getMessage());
			}
		}
		return null;
	}

}
