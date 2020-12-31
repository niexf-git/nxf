package com.cmsz.paas.web.application.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.firewall.FirewalledRequest;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.common.model.controller.response.IdValue;
import com.cmsz.paas.common.utils.BasePropertiesUtil;
import com.cmsz.paas.web.application.model.ApplicationInfo;
import com.cmsz.paas.web.application.model.ApplicationInfoUtil;
import com.cmsz.paas.web.application.model.ClusterInfo;
import com.cmsz.paas.web.application.model.DNSInfo;
import com.cmsz.paas.web.application.model.DNSModel;
import com.cmsz.paas.web.application.model.DataCenterInfo;
import com.cmsz.paas.web.application.model.DetailsApp;
import com.cmsz.paas.web.application.model.createAppInfo;
import com.cmsz.paas.web.application.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.permission.entity.OperPermission;
import com.cmsz.paas.web.permission.service.PermissionService;
import com.cmsz.paas.web.role.entity.Role;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.role.service.RolePerRelationService;
import com.cmsz.paas.web.role.service.RoleService;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.entity.UserPermission;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.opensymphony.xwork2.ActionContext;

public class ApplicationAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationAction.class);
	
	private ApplicationInfoUtil appUtil;
	private boolean isProduct;
	private long dataId;
	@Autowired
	private ApplicationService applicationRestService;
	
	@Autowired
	private com.cmsz.paas.web.appservice.service.ApplicationService applicationService;
	/**
	 * 分页参数 page : 第几页 rows : 每页几行 sord : 升序还是降序 sidx : 按什么排序
	 */
	protected Integer page = 1;
	protected Integer rows = 10;
	protected String sord = null;
	protected String sidx = null;

	protected String resultMessage;
	protected String resultCode;
	protected String handleResult, handleOpinion;
	protected String userdata;
	
	/** 用户管理service对象 . */
	@Autowired
	private UserManagerService userManagerService;
	
	/** 角色与权限管理service对象. */
	@Autowired
	private RolePerRelationService rolePerService;
	
	/** 权限service对象. */
	@Autowired
	private PermissionService permissionService;
	
	@SuppressWarnings("rawtypes")
	public Page getJqGridPage(String defaultSidx) {

		page = (page == null) ? 1 : page;
		sidx = (sidx == null) ? defaultSidx : sidx;
		sord = (sord == null) ? "DESC" : sord;
		Page pageResult = new Page(rows);
		pageResult.setPageNo(page);
		pageResult.setOrder(sord);
		pageResult.setOrderBy(sidx);
		return pageResult;
	}
	
	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}
	
	/**
	 * 获取用户权限：操作权限，数据权限，没有拥有的权限
	 * 
	 * @param userId
	 * 			用户id
	 * @return UserPermission
	 * 			用户权限：操作权限，数据权限，没有拥有的权限
	 * @throws PaasWebException
	 */
	private UserPermission getUserPermission(Long userId) throws PaasWebException {
		logger.info("获取用户权限" + userId);
		//获取用户权限
		UserPermission userPermision = new UserPermission();
		//获取用户权限其中的【操作权限】
		List<OperPermission> operPermission = permissionService.queryOperPermission();
		
		try {
			if (userManagerService.isSuperManager(userId)) { //超级管理员，具有全部的数据权限和操作权限
				//全部
				List<RolePermissionRelation> appInfo = rolePerRelationService.queryRolePermissionRelation();
				//超级管理员应用权限
				List<RolePermissionRelation> adminAppPer = new ArrayList<RolePermissionRelation>();
				for(RolePermissionRelation relation : appInfo){
					if(relation.getRoleId() == Constants.ROLE_ID){
						adminAppPer.add(relation);
					}
				}
				userPermision.setRolePermissionList(adminAppPer); //【应用权限】
				userPermision.setOperPermissionList(operPermission); //【操作权限】
				userPermision.setUnOperPermissionList(new ArrayList<OperPermission>()); //【没有拥有的权限】
			} else { //普通用户
				List<RolePermissionRelation> userPermissionList = userManagerService.queryUserPermission(userId);
				List<OperPermission> operPermissionList = new ArrayList<OperPermission>();
				List<RolePermissionRelation> appPermissionList = new ArrayList<RolePermissionRelation>();
				for (RolePermissionRelation rolePermission : userPermissionList) {
					if (null != rolePermission) {
						//权限类型，1表示操作权限，2表示数据权限，如应用
						if (rolePermission.getPermissionType() == Constants.ROLE_OPER_PERMISSION_TYPE) {
							for (OperPermission per : operPermission) {
								if (rolePermission.getPermissionId().equals(per.getId())) {
									if (!isAdd(operPermissionList, per.getId())) {
										operPermissionList.add(per);
									}
									break;
								}
							}
						}else if(rolePermission.getPermissionType() == Constants.ROLE_APP_PERMISSION_TYPE){
							appPermissionList.add(rolePermission);
						}
					}
				}
				userPermision.setUnOperPermissionList(findUnOperPermision(operPermissionList, operPermission));
				userPermision.setOperPermissionList(operPermissionList);
				userPermision.setRolePermissionList(appPermissionList);
			}
		} catch (PaasWebException ex) {
			throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
		}
		return userPermision;
	}
	
	/**
	 * 判断权限是否已经添加到权限列表里面
	 * 
	 * @param operPermissionList
	 * 			权限列表
	 * @param id
	 * 		权限id
	 * @return true 已经添加  false 没有添加
	 */
	private boolean isAdd(List<OperPermission> operPermissionList, String id) {
		for (OperPermission operPermission : operPermissionList) {
			if (operPermission.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取用户没有的操作权限
	 * 
	 * @param operPermissionList
	 *            用户拥有的操作权限
	 * @param allOperPerList
	 *            系统中所有的操作权限
	 * @return List OperPermission
	 * 				用户没有的操作权限集合
	 */
	private List<OperPermission> findUnOperPermision(
			List<OperPermission> operPermissionList,
			List<OperPermission> allOperPerList) {
		logger.info("查询没有赋予的权限");
		if (operPermissionList == null || operPermissionList.size() == 0) {
			return allOperPerList;
		}
		List<OperPermission> unOperPerList = new ArrayList<OperPermission>();
		for (OperPermission operPer : allOperPerList) {
			boolean isFind = false;
			for (OperPermission per : operPermissionList) {
				if (operPer.getId().equals(per.getId())) {
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				unOperPerList.add(operPer);
			}
		}
		logger.info("查询成功" + unOperPerList);
		return unOperPerList;
	}
	
	/***
	 * 刷新session中的数据
	 */
	@SuppressWarnings("rawtypes")
	private void refreshSession(){
		User user = (User) getSessionMap().get(Constants.CURRENT_USER);
		//角色应用关联关系
		List<RolePermissionRelation> permissionRelations = null;
//		if (userManagerService.isSuperManager(user.getId())) { //超级管理员，具有全部的数据权限和操作权限
//			try {
//				permissionRelations = rolePerRelationService.queryRolePermissionRelation();
//			} catch (PaasWebException ex) {
//				throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
//			}
//		} else { //普通用户
//			permissionRelations = userManagerService.queryUserPermission(user.getId());
//		}
		permissionRelations = getUserPermission(user.getId()).getRolePermissionList();
		
		String appPermissionId = "";
		String appPermissionName = "";
		String opertype = "";
		
		for (RolePermissionRelation rolePermission : permissionRelations) {
			if (null != rolePermission) {
				if (rolePermission.getPermissionType() == 2) { //权限类型，1表示操作权限，2表示数据权限，如应用
					if (appPermissionId.equals("")) {
						appPermissionId += rolePermission.getPermissionId();
						appPermissionName += rolePermission.getPermissionName();
						opertype += rolePermission.getOpertype();
					} else {
						appPermissionId += "," + rolePermission.getPermissionId();
						appPermissionName += "," + rolePermission.getPermissionName();
						opertype += "," + rolePermission.getOpertype();
					}
				}
			}
		}
		
		TreeMap<String, String> noReapted = new TreeMap<String, String>();
		
		String noReaptedAppId = "";
		String noReaptedAppName = "";
		
		String[] appPermissionIdTemp = appPermissionId.split(",");
		String[] appPermissionNameTemp = appPermissionName.split(",");
		
		for (int i = 0; i < appPermissionIdTemp.length; i++) {
			noReapted.put(appPermissionIdTemp[i], appPermissionNameTemp[i]);
        }
		
		Iterator it = noReapted.entrySet().iterator();
		while (it.hasNext()) {
			// entry的输出结果如key0=value0等
			Map.Entry entry =(Map.Entry) it.next();
			Object key = entry.getKey();
			Object value=entry.getValue();
		   
			if (noReaptedAppId.equals("")) {
				noReaptedAppId += key;
			} else {
				noReaptedAppId += "," + key;
			}
		   
			if (noReaptedAppName.equals("")) {
				noReaptedAppName += value;
			} else {
				noReaptedAppName += "," + value;
			}
		}
		
        String noReaptedOpertype = "";
		TreeSet<String> noReapted3 = new TreeSet<String>(); //带有String类型的TreeSet泛型
		String[] opertypeTemp = opertype.split(",");
		for (int i = 0; i < opertypeTemp.length; i++) {
			noReapted3.add(opertypeTemp[i]);
        }
        for(String index : noReapted3){
            if (noReaptedOpertype.equals("")) {
            	noReaptedOpertype += index;
			} else {
				noReaptedOpertype += "," + index;
			}
        }
        getSessionMap().put("appPermissionId", noReaptedAppId); //应用ID
		getSessionMap().put("appPermissionName",noReaptedAppName); //应用名称
		getSessionMap().put("opertype", noReaptedOpertype); //操作类型，如开发1，测试2，运维3，只有应用才有操作类型
		getSessionMap().put("dataPermission",permissionRelations); //应用权限
		if(getSessionMap().get("appPerSelectedId") != null && !getSessionMap().get("appPerSelectedId").toString().contains(",")){
			return;
		}
		if(noReaptedAppId.indexOf(",")>-1){
			getSessionMap().put("appPerSelectedId", noReaptedAppId);
		}
	}
	/** 应用名称  */
	private String appName;
	
	/** 创建应用应用信息对象 */
	private createAppInfo createAppInfo;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RolePerRelationService rolePerRelationService;
	
	private String[] dnsDomainName;
	
	private String[] dnsHostIp;
	
	private String[] dnsSpareIp;
	
	private long id;
	
	@UnLogging
	public void isAdminCheck(){
		User user = (User) getSessionMap().get(Constants.CURRENT_USER);
		if (userManagerService.isSuperManager(user.getId())) {
			this.sendSuccessReslult("true");
		}else{
			this.sendFailReslutl("false");
		}
	}
	
	/***
	 * 获取应用管理列表
	 */
	@SuppressWarnings("unchecked")
	public void queryAppList(){
		Page<ApplicationInfo> page = this.getJqGridPage("create_time");
		try {
			refreshSession();
			List<ApplicationInfo> infos = null;
			List<ApplicationInfo> tempInfos = null;
			String ids = (String) getSessionMap().get("appPerSelectedId");
			tempInfos = applicationRestService.queryRestAppList("",appName);
			infos = applicationRestService.queryRestAppList(ids,appName);
			infos = appInfosList(infos,tempInfos);
			infos = isAdmin(infos);
			page.setResult(infos);
			page.setTotalCount(infos.size());
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException e) {
			logger.error("查询应用异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	
	private DNSModel dnsModel;
	
	@UnLogging
	public String queryDNSList(){
		try {
			dnsModel = applicationRestService.queryDNSList(id);
		} catch (PaasWebException e) {
			logger.error("查询DNS异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
		return SUCCESS;
	}
	
	public void createDNS(){
		try {
			dnsModel.setDnsList(getDNSList());
			applicationRestService.createDNS(dnsModel);
			this.sendSuccessReslutl();
		} catch (PaasWebException e) {
			logger.error("创建DNS配置信息异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	
	private List<DNSInfo> getDNSList(){
		List<DNSInfo> l = null;
		if(dnsDomainName != null){
			l = new ArrayList<DNSInfo>();
			for (int i = 0; i < dnsDomainName.length; i++) {
				DNSInfo d =  new DNSInfo();
				d.setDomainName(dnsDomainName[i]);
				d.setHostIp(dnsHostIp[i]);
				d.setSpareIp(dnsSpareIp[i]);
				l.add(d);
			}
		}
		return l;
	}
	
	
	@UnLogging
	@SuppressWarnings("unchecked")
	public void queryDetailsList(){
		Page<DetailsApp> page = this.getJqGridPage("create_time");
		try {
			List<DetailsApp> infos = null;
			infos = applicationRestService.queryDetailsList(id);
			page.setResult(infos);
			page.setTotalCount(infos.size());
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException e) {
			logger.error("查询应用异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	
	private List<ApplicationInfo> isAdmin(List<ApplicationInfo> infos){
		Set<String> keySet = null;
		boolean isAdmin = false;
		User user = (User) getSessionMap().get(Constants.CURRENT_USER);
		if (userManagerService.isSuperManager(user.getId())) {
			isAdmin = true;
			for (ApplicationInfo applicationInfo : infos) {
				applicationInfo.setAdmin(isAdmin);
			}
			return infos;
		}else{
			keySet =  new HashSet<String>();
			List<RolePermissionRelation> roleList = rolePerRelationService.queryByUserId(user.getId());
			for (RolePermissionRelation role : roleList) {
				if(role.getPermissionType() == 2){
					keySet.add(role.getPermissionId());
				}
			}
			List<ApplicationInfo> applicationInfos = new ArrayList<ApplicationInfo>();
			for (ApplicationInfo app : infos) {
				if(keySet.contains(app.getId()+"")){
					applicationInfos.add(app);
				}
			}
			return applicationInfos;
		}
	}
	

	/***
	 * 两个集合做交集
	 * 该方法主要用于运维环境获取开发环境上发布过来的版本应用信息
	 * @param infos
	 * @param tempInfos
	 * @return
	 */
	private List<ApplicationInfo> appInfosList(List<ApplicationInfo> infos,List<ApplicationInfo> tempInfos){
		List<ApplicationInfo> tempList = new ArrayList<ApplicationInfo>();
		tempList.addAll(infos);
		String name = (String) getSessionMap().get("opertypestate");
		for (ApplicationInfo applicationInfo : tempInfos) {
			boolean flag = true;
			for (ApplicationInfo info : infos) {
				//如果第二个集合中存在第一个集合中同等ID的不添加
				if(info.getId() == applicationInfo.getId()){
					flag = false;
				}
			}
			if(null != name){
				//如果未全部 集合中并未存在则添加  
				if(("全部".equals(name) || name.indexOf(",")>-1) && flag){
					tempList.add(applicationInfo);
				}
			}
			
		}
		return tempList;
		
	}
	private List<ClusterInfo>  transformationClusterInfo(List<com.cmsz.paas.web.monitoroperation.model.Cluster> clusterInfos){
		List<ClusterInfo> list = new ArrayList<ClusterInfo>();
		for (com.cmsz.paas.web.monitoroperation.model.Cluster clusterInfo : clusterInfos) {
			//过滤Ipaas集群信息
			if(clusterInfo.getType() == "1"){
				continue;
			}
			ClusterInfo info = new ClusterInfo();
			info.setId(Long.valueOf(clusterInfo.getId()));
			info.setName(clusterInfo.getName());
			info.setType(Integer.valueOf(clusterInfo.getType()));
			info.setChecked(false);
			list.add(info);
		}
		return list;
	}
	
	private long getCheckDataCenterInfo(List<DataCenterInfo> list){
		for (DataCenterInfo dataCenterInfo : list) {
			if(dataCenterInfo.isChecked()){
				id = dataCenterInfo.getId();
				break;
			}
		}
		return id;
	}

	@UnLogging
	@SuppressWarnings("unchecked")
	public void dataCenterChange(){
		Page<ClusterInfo> page = this.getJqGridPage("id");
		try {
			createAppInfo = new createAppInfo();
			List<ClusterInfo> clusterInfos = transformationClusterInfo(applicationService.queryClusterList(String.valueOf(dataId), "-1","dataCenter","",""));
			page.setResult(clusterInfos);
			page.setTotalCount(clusterInfos.size());
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException e) {
			logger.error("查询应用异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	/***
	 * 调用接口查询创建应用页面的字段 (数据中心，角色集合，集群集合)
	 * @return
	 */
	@UnLogging
	public String queryAppInfo(){
		try {
			createAppInfo = new createAppInfo();
			List<DataCenterInfo> centerLists = applicationRestService.quertCenterLists();
			List<List<DataCenterInfo>> listData = new ArrayList<List<DataCenterInfo>>();
			listData.add(centerLists);
			listData.add(centerLists);
			if(listData.size()>0){
				listData.get(0).get(0).setChecked(true);
			}
			//获取数据中心数据
			createAppInfo.setDataCenterInfos(listData);
			//获取集群信息
			List<ClusterInfo> clusterInfos = transformationClusterInfo(applicationService.queryClusterList(String.valueOf(listData.size()>0?getCheckDataCenterInfo(listData.get(0)):0), "-1","dataCenter","",""));
			List<List<ClusterInfo>> list = new ArrayList<List<ClusterInfo>>();
			list.add(clusterInfos);//0 对应着集群 开发信息
			/*list.add(clusterInfos);//1对应着集群 测试信息
			list.add(clusterInfos);//2对应着集群 运维信息
*/			createAppInfo.setClusterInfos(list);
			//查询所有角色
			List<Role> role = roleService.queryAll();
			filterManager(role);
			assignOperTypeToRole(role);
			List<List<Role>> allRole = new ArrayList<List<Role>>();
			allRole.add(role);//0 对应着角色 开发信息
			allRole.add(role);//1 对应着角色测试信息
			createAppInfo.setRoles(allRole);
		} catch (PaasWebException e) {
			throw new PaasWebException(e.getErrorCode(), e.toString());
		}
		return SUCCESS;
	}
	
	/***
	 * 过滤管理员
	 * @param allRole 全部角色
	 */
	private void filterManager(List<Role> allRole){
		for (Role roleObj : allRole) {
			if(roleObj.getType() == Constants.ROLE_TYPE_SUPER_MANAGER){
				allRole.remove(roleObj);
				break;
			}
		}
	}
	
	/***
	 * 根据ID查询应用信息
	 * @return
	 */
	@UnLogging
	public String queryAppById(){
		try{
			//获取数据中心集合
			List<DataCenterInfo> centerInfos = applicationRestService.quertCenterLists();
	/*		//获取集群集合
			List<ClusterInfo> clusterInfos = transformationClusterInfo();*/
			//查询对应ID的应用
			createAppInfo = applicationRestService.queryAppInfoToUpdate(id,centerInfos,null);
			
			List<List<ClusterInfo>> lists = createAppInfo.getClusterInfos();
			if(lists != null){
				if(lists.size()>1){
					createAppInfo.setProductClusterIds(createAppInfo.getProductClusterIds());
					if(!(boolean) getSessionMap()
							.get("currentContext")){
						String[] str = createAppInfo.getProductClusterIds().split(",");
						for (int i = 0; i < str.length; i++) {
							if(str[i].split(":")[0].equals(centerInfos.get(0).getId()+"")){
								createAppInfo.getDataCenterInfos().get(1).get(0).setChecked(true);
								createAppInfo.getDataCenterInfos().get(1).get(1).setChecked(false);
								break;
							}
						}
						
					}
				}
			}
			
			transformationCreateAppInfo();//填充开发数据
		} catch (PaasWebException e) {
			throw new PaasWebException(e.getErrorCode(), e.toString());
		}
		//createAppInfo.setRoles(roles);
		//applicationRestService.queryAppInfoToUpdate();
		return SUCCESS;
	}
	
	/***
	 * 拷贝重复角色集合
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Role> copyList(List<Role> list){
		List<Role> ts = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(list);
			
			ByteArrayInputStream  inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			ts = (List<Role>) objectInputStream.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
		
	}
	
	/***
	 * 填充开发模块数据
	 * @return
	 */
	private boolean[] roleCheckeds = new boolean[]{false,false,false};
	public createAppInfo transformationCreateAppInfo(){
		List<List<Role>> list = new ArrayList<List<Role>>();
		try {
			boolean currentContext = (boolean) getSessionMap().get("currentContext");
			//构造角色信息 对应着 开发 测试 集群
			List<Role> role = roleService.queryAll();
			filterManager(role);
			assignOperTypeToRole(role);
			List<Role> role2 = copyList(role);
			list.add(role);
			list.add(role2);
			List<RolePermissionRelation> listById = new ArrayList<RolePermissionRelation>();
			User user = (User) getSessionMap().get(Constants.CURRENT_USER);
			//角色应用关联关系
			List<RolePermissionRelation> permissionRelations = null;
			if (userManagerService.isSuperManager(user.getId())) { //超级管理员，具有全部的数据权限和操作权限
				try {
					permissionRelations = rolePerRelationService.queryRolePermissionRelation();
				} catch (PaasWebException ex) {
					throw new PaasWebException(ex.getErrorCode(), ex.toString()); // 从控制中心获取，前面是错误码，后面是错误内容
				}
			} else { //普通用户
				permissionRelations = userManagerService.queryUserPermission(user.getId());
			}
			//取出当前App的关联对象
			for (RolePermissionRelation rolePermissionRelation : permissionRelations) {
				if(rolePermissionRelation.getPermissionId().equals(String.valueOf(id)) && rolePermissionRelation.getPermissionType() == 2){
					listById.add(rolePermissionRelation);
				}
			}
			//listById = (List<RolePermissionRelation>) getSessionMap().get("dataPermission");
			String roleId = "";
			//后台填充需要选中的角色信息
			for (int i = 0; i < listById.size(); i++) {
				roleId =  String.valueOf(listById.get(i).getRoleId());
				//type为1、2、3 对应着页面上的开发测试运维
				List<Role> roles = list.get(currentContext?(listById.get(i).getOpertype()-1):(listById.get(i).getOpertype()-3));
				//遍历全部角色信息找到相同ID的标示选中状态
				for (int j = 0; j < roles.size(); j++) {
					if(roles.get(j).getId().toString().equals(roleId)){
						roles.get(j).setChecked(true);
						//外层复选框状态
						roleCheckeds[(listById.get(i).getOpertype()-1)] = true;
						continue;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		createAppInfo.setRoleCheckeds(roleCheckeds);
		createAppInfo.setRoles(list);
		return createAppInfo;
	}
	
	/**
	 * 遍历所有的角色，判断角色和应用是否已经有绑定关系，
	 * 有则将roleOperType（1-开发，2-测试，0-默认没有）值赋给hasOperType
	 * @param roleList
	 */
	private void assignOperTypeToRole(List<Role> roleList) {
		for(int i = 0; i < roleList.size(); i++){
			String roleOperType = rolePerService.queryOperTypeByRoleId(roleList.get(i).getId());
			if(roleOperType != null && !"".equals(roleOperType)){
				roleList.get(i).setHasOperType(Integer.parseInt(roleOperType));
			}
		}
	}
	
	/***
	 * 创建应用
	 * @throws Exception
	 */
	public void createAppInfo() throws Exception{
		try {
			User user = (User) getSessionMap().get(Constants.CURRENT_USER);
			if(userManagerService.isSuperManager(user.getId())){
				logger.info("ApplicationAction收到创建应用的请求，开始创建应用！");
				appUtil.setLoginName(getSessionMap().get("loginName").toString());
				long appId = applicationRestService.createAppInfo(appUtil);
				
				logger.info("ApplicationAction 创建应用成功 , appId: "
						+ appId);
				
				logger.info("开始创建应用与角色之间关系！");
				rolePerRelationService.createRoleToAppRelation(appId, appUtil);
				logger.info("创建应用与角色之间成功！");
				refreshSession();
				sendSuccessReslult(String.valueOf(appId));
			}else{
				logger.info("非管理员没有操作权限");
				this.sendFailResult(Constants.NOT_ADMIN_ERROR, BasePropertiesUtil.getValue(Constants.NOT_ADMIN_ERROR));
			}
			
		} catch (PaasWebException e) {
			logger.info("创建应用失败",e.getErrorCode());
			this.sendFailResult(e.getKey(), e.toString());
		}
	}
	
	/***
	 * 修改应用
	 */
	public void updateAppInfo(){
		logger.info("修改应用开始");
		try {
			appUtil.setLoginName(getSessionMap().get("loginName").toString());
			appUtil.setAppId(createAppInfo.getId());
			appUtil.setDataCoreId(createAppInfo.getDataCenterInfos()==null?0:createAppInfo.getDataCenterInfos().get(0).get(0).getId());
			applicationRestService.updateAppInfo(appUtil);
			logger.info("ApplicationAction 修改应用成功 , appId: "
					+ createAppInfo.getId());
			rolePerRelationService.updateAppRolePerRelation(appUtil);
			refreshSession();
			sendSuccessReslult("SUCCESS");
		}catch(PaasWebException ex){
			logger.error("["+ex.getKey()+"]执行修改应用出错出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
		
	}
	
	/***
	 * 删除应用
	 */
	public void deleteAppInfo(){
		logger.info("开始执行删除应用，应用ID：" + id);
		try {
			//1. 删除应用
			applicationRestService.deleteAppInfo(id);
			
			//2、删除数据库中的关联关系
			rolePerRelationService.deleteAppRolePerRelation(id);
			logger.info("执行删除应用成功！");
			refreshSession();
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("["+ex.getKey()+"]执行删除应用出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	public void importApp(){
		logger.info("开始执行导入应用.");
		try {
			applicationRestService.importApp();
			logger.info("执行导入应用成功！");
			sendSuccessReslult("SUCCESS");
		} catch (PaasWebException ex) {
			logger.error("["+ex.getKey()+"]执行导入应用出错", ex);
			this.sendFailResult(ex.getErrorCode(), ex.toString());
		}
	}
	
	/***
	 * 获取DNS与集群信息
	 */
	@UnLogging
	public void queryDnsAndClusterById(){
		try {
			//获取数据中心集合
			List<DataCenterInfo> centerInfos = applicationRestService.quertCenterLists();
			createAppInfo = applicationRestService.queryAppInfoToUpdate(id,centerInfos,null);
			this.renderText(JackJson.fromObjectToJson(createAppInfo));
		} catch (PaasWebException e) {
			logger.error("查询DNS与集群异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	@UnLogging
	public void getDisasterStatus(){
		try {
			IdValue	iv = applicationRestService.getDisasterStatus(id);
			this.renderText(JackJson.fromObjectToJson(iv));
		} catch (PaasWebException e) {
			logger.error("查询容灾状态异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
	}
	
	
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public createAppInfo getCreateAppInfo() {
		return createAppInfo;
	}

	public void setCreateAppInfo(createAppInfo createAppInfo) {
		this.createAppInfo = createAppInfo;
	}

	public ApplicationInfoUtil getAppUtil() {
		return appUtil;
	}

	public void setAppUtil(ApplicationInfoUtil appUtil) {
		this.appUtil = appUtil;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getProduct() {
		isProduct = true;
		return isProduct;
	}

	public void setProduct(boolean isProduct) {
		this.isProduct = isProduct;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
	}

	public DNSModel getDnsModel() {
		return dnsModel;
	}

	public void setDnsModel(DNSModel dnsModel) {
		this.dnsModel = dnsModel;
	}

	public String[] getDnsDomainName() {
		return dnsDomainName;
	}

	public void setDnsDomainName(String[] dnsDomainName) {
		this.dnsDomainName = dnsDomainName;
	}

	public String[] getDnsHostIp() {
		return dnsHostIp;
	}

	public void setDnsHostIp(String[] dnsHostIp) {
		this.dnsHostIp = dnsHostIp;
	}

	public String[] getDnsSpareIp() {
		return dnsSpareIp;
	}

	public void setDnsSpareIp(String[] dnsSpareIp) {
		this.dnsSpareIp = dnsSpareIp;
	}

	
	
	
}
