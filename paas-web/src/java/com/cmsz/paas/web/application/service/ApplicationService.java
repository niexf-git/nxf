package com.cmsz.paas.web.application.service;

import java.util.List;

import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.web.application.model.ApplicationInfo;
import com.cmsz.paas.web.application.model.ApplicationInfoUtil;
import com.cmsz.paas.web.application.model.ClusterInfo;
import com.cmsz.paas.web.application.model.DNSModel;
import com.cmsz.paas.web.application.model.DataCenterInfo;
import com.cmsz.paas.web.application.model.DetailsApp;
import com.cmsz.paas.web.application.model.createAppInfo;

public interface ApplicationService {
	
	/***
	 * 调用rest接口获取应用集合
	 * @param ids
	 * @param name
	 * @return
	 */
	public List<ApplicationInfo> queryRestAppList(String ids,String name);
	
	
	/***
	 * 调用rest接口获取数据中心集合
	 * @return
	 */
	public List<DataCenterInfo> quertCenterLists();
	
	/***
	 * 调用rest接口获取集群集合
	 * @return
	 */
	public List<ClusterInfo> queryCluster();
	
	/***
	 * 创建应用
	 * @param infoUtil
	 * @return
	 */
	public long createAppInfo(ApplicationInfoUtil infoUtil);
	
	
	public createAppInfo queryAppInfoToUpdate(long id,List<DataCenterInfo> centerInfos,List<ClusterInfo> clusterInfos);
	
	/***
	 * 修改应用
	 * @param infoUtil
	 */
	public void updateAppInfo(ApplicationInfoUtil infoUtil);
	
	/***
	 * 删除应用
	 * @param infoUtil
	 * @return
	 */
	public void deleteAppInfo(long id);
	
	
	/***
	 * 查询迁移详情
	 * @param appId
	 */
	public List<DetailsApp> queryDetailsList(long appId);
	
	/***
	 * 生产环境导入开发测试应用
	 */
	public void importApp();
	
	/***
	 *查询DNS列表 
	 * @param appId
	 * @return
	 */
	public DNSModel queryDNSList(long appId);
	
	/***
	 * 创建DNS配置
	 * @param appId
	 */
	public void createDNS(DNSModel dnsModel);

	/***
	 * 获取容灾状态
	 * @param id
	 */
	public IdValue getDisasterStatus(long id);
	
}
