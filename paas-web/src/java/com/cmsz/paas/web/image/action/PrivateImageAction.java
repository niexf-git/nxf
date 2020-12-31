package com.cmsz.paas.web.image.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.base.util.PropertiesUtil;
import com.cmsz.paas.web.constants.Constants;
import com.cmsz.paas.web.image.model.PrivateImage;
import com.cmsz.paas.web.image.model.PrivateImageVersion;
import com.cmsz.paas.web.image.service.PrivateImageService;
import com.cmsz.paas.web.role.entity.RolePermissionRelation;
import com.cmsz.paas.web.user.entity.User;
import com.cmsz.paas.web.user.service.UserManagerService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 私有镜像管理类
 * @author lin.my 2016-4-1
 */
public class PrivateImageAction extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -309006101185348920L;

	private static final Logger logger = LoggerFactory.getLogger(PrivateImageAction.class);

	@Autowired
	private PrivateImageService privateImageService;
	
	@Autowired
	private UserManagerService userManagerService;
	
	private PrivateImage privateImage;
	
	private String id;

	private String appId;

	private String appName; //应用名称

	private String buildId;

	private String buildName; //构建名称

	private String name = ""; //私有镜像名称

	private String description;

	private String createBy; //创建人

	private String createTime; //创建时间
	
	private String registryUrl; //仓库地址

	private String registryUser; //用户名

	private String registryPswd; //密码

	private String status;
	
	private String privateImageId = ""; //私有镜像ID
	
	private String version = ""; //私有镜像版本号
	
	private String privateImageVersionId; //私有镜像版本ID

	private int privateImageType;
	
	private String oper;
	
	private String checkImageId;
	
	private String queryType;
	
	@SuppressWarnings("unchecked")
	private String getRolePermissionRelation(String appName){
		List<RolePermissionRelation> temp = (List<RolePermissionRelation>) getSessionMap().get("dataPermission");
		List<String> list = null;
		if(temp.size()>0){
			list = new ArrayList<String>();
			for (RolePermissionRelation rolePermissionRelation : temp) {
				if(rolePermissionRelation.getPermissionType() == 2 && rolePermissionRelation.getPermissionName().equals(appName)){
					list.add(String.valueOf(rolePermissionRelation.getOpertype()));
				}
			}
		}
		list = new ArrayList<String>(new HashSet<String>(list));
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sbf.append(list.get(i));
			if(i != list.size()-1){
				sbf.append(",");
			}
		}
		return sbf.toString();
		
	}
	
	@UnLogging
	public void queryPrivateImageInfo() throws Exception{
		try {
			String type = "";
			type =  getRolePermissionRelation(appName); //操作类型（开发、测试、运维）
			List<String> opertypeList = new ArrayList<String>();
			if(type.indexOf(",")>-1){
				String[] opertypeTemp = type.split(",");
				if(privateImageType == 1){
					logger.info("查询私有镜像版本列表，私有镜像Id：" + id);
					List<PrivateImageVersion> list = privateImageService.queryPrivateImageVersionList(id, version); //privateImageId
					
					for (int i = 0; i < opertypeTemp.length; i++) {
						for (int j = 0; j < list.size(); j++) {
							if(list.get(j).getStatus().equals(opertypeTemp[i])){
								opertypeList.add(list.get(j).getStatus());
							}
						}
					}
				}else if(privateImageType == 2){
					if(status.equals("1")){
						opertypeList.add("1");
					}else if(status.equals("2")){
						for (int i = 0; i < opertypeTemp.length; i++) {
							if(Integer.valueOf(opertypeTemp[i])<=2){
								opertypeList.add(opertypeTemp[i]);
							}
						}
					}else if(status.equals("3")){
						for (int i = 0; i < opertypeTemp.length; i++) {
								opertypeList.add(opertypeTemp[i]);
						}
					}
				}
			}else{
				if(!"".equals(type))opertypeList.add(type);
			}
			
			String tempType = "";
			if(opertypeList.size() < 1){
				if(getSessionMap().get("opertype").toString().indexOf(",")>-1){
					tempType = "1";
				}else{
					try {
						tempType = Integer.valueOf(getSessionMap().get("opertype").toString()).toString();
					} catch (Exception e) {
						tempType = type;
					}
				}
				opertypeList.add(tempType);
			}
			//去重
			opertypeList=new ArrayList<String>(new HashSet<String>(opertypeList));
			String operTypeJson = "";
			for (int i = 0; i < opertypeList.size(); i++) {
				operTypeJson += opertypeList.get(i);
				if(i != opertypeList.size()-1){
					operTypeJson += ",";
				}
			}
			this.renderText(JackJson.fromObjectToJson(operTypeJson));
		} catch (PaasWebException e) {
			logger.error("查询私有镜像信息异常", e);
			this.sendFailResult2jqGrid(e.getKey(),e.toString());
		}
		
	}
	
	/**
	 * 查询私有镜像列表
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void queryPrivateImageList() throws Exception {
		try {
			Page<PrivateImage> page = this.getJqGridPage("createTime");
			List<PrivateImage> list = null;
			
			String appIds = "";
			appIds = (String) getSessionMap().get("appPerSelectedId"); //应用权限
			
			String type = "";
			type = getSessionMap().get("selectedOpertype").toString(); //操作类型（开发、测试、运维）
			String[] opertypeTemp = type.split(",");
			if(opertypeTemp != null && opertypeTemp.length > 1){ //如果操作类型多于一个就显示全部，type值即传空的字符串
				type = "";
			}
			
			int roleType = 0; //管理员：0，普通用户：1
			roleType = (Integer)getSessionMap().get("roleType");
			if(oper != "" && null != oper && oper.equals("serviceOper") && privateImageType != 0){
				appIds = id;
				type =String.valueOf(privateImageType);
			}
			if(roleType != Constants.ROLE_TYPE_SUPER_MANAGER && appIds.equals("") && type.equals("")){
//				if(appIds.equals("") && type.equals("")){
//					this.renderText(JackJson.fromObjectToJson(page));
//				}
				page.setResult(list);
				page.setTotalCount(0);
				this.renderText(JackJson.fromObjectToJson(page));
			}else{
				logger.info("查询私有镜像列表，应用Id：" + appIds + ";操作类型type：" + type);
				list = privateImageService.queryPrivateImageList(name, appIds, privateImageId, type);
//				List<PrivateImage> listTemp = deployControl(list);
//				checkedImage(listTemp);
//				page.setResult(listTemp);
//				page.setTotalCount(listTemp.size());
				checkedImage(list);
				page.setResult(list);
				page.setTotalCount(list.size());
				logger.info("查询私有镜像列表完成，返回条数：" + page.getTotalCount());
				this.renderText(JackJson.fromObjectToJson(page));
			}
		} catch (PaasWebException ex) {
			logger.error("查询私有镜像信息异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(),ex.toString());
		}
	}
	
//	private List<PrivateImage> deployControl(List<PrivateImage> list){
//		List<PrivateImage> listPrivate = list;
//		List<PrivateImageVersion> list4Version = null;
//		for(int i=0; i<list.size(); i++){
//			String id = list.get(i).getId();
//			list4Version = privateImageService.queryPrivateImageVersionList(id, version); //privateImageId
//		}
//		
//		
//		String typeIds = "";
//		
//		String type = "";
//		type = getSessionMap().get("selectedOpertype").toString(); //操作类型（开发、测试、运维）
//		
//		User user = (User) getSessionMap().get(Constants.CURRENT_USER);
//		//角色应用关联关系
//		List<RolePermissionRelation> dataPermission = null;
//		if (!userManagerService.isSuperManager(user.getId())) { //超级管理员，具有全部的数据权限和操作权限
//			dataPermission = userManagerService.queryUserPermission(user.getId());
//			if(!"0".equals(appId)){
//				
//				for (int i = 0;i<dataPermission.size();i++) {
//					//类型2的为应用信息
//					if(dataPermission.get(i).getPermissionType() == 2){
//						//获取集合中应用一致的 类型名称
//						if(dataPermission.get(i).getPermissionId().equals(String.valueOf(appId))){
//							//去重复
//							if(!typeIds.contains(String.valueOf(dataPermission.get(i).getOpertype()))){
//								typeIds += dataPermission.get(i).getOpertype()+",";
//							}
//						}
//					}
//				}
//				String[] tempTypeIds = null;
//				if(typeIds.contains(",")){
//					typeIds = typeIds.substring(0, typeIds.lastIndexOf(","));
//					tempTypeIds = typeIds.split(",");
//				}
//				if(tempTypeIds!=null && typeIds.contains(",")){
//					typeIds = "";
//					int[] sortTypeIds = new int[tempTypeIds.length];
//					for (int i = 0; i < sortTypeIds.length; i++) {
//						sortTypeIds[i] =Integer.valueOf(tempTypeIds[i]);
//					}
//					Arrays.sort(sortTypeIds);
//					for (int i = 0; i < sortTypeIds.length; i++) {
//						typeIds +=sortTypeIds[i];
//						if(i != sortTypeIds.length-1){
//							typeIds+=",";
//						}
//					}
//				}
//			}
//			
//			String[] arr1 = type.split(",");
//			String[] arr2 = typeIds.split(",");
//			
//			List<PrivateImageVersion> list4Test = new ArrayList<PrivateImageVersion>();
//			for(PrivateImageVersion version : list4Version){
//				
//				if(intersect(arr1, arr2).size() > 1){
////					for(int i=0; i< intersect(arr1, arr2).size(); i++){
////						if(version.getStatus().equals(intersect(arr1, arr2).get(i)) || version.getStatus().equals(Constants.OPERATOR_TYPE)){
////							list4Test.add(version);
////						}
////					}
//					list4Test.add(version);
//				}else{
//					if(version.getStatus().equals(intersect(arr1, arr2).get(0)) || version.getStatus().equals(Constants.OPERATOR_TYPE)){
//						list4Test.add(version);
//					}
//				}
//			}
//			if(list4Test.size() > 0){
//				for(int i=0; i< listPrivate.size(); i++){
//					listPrivate.get(i).setDeployOrnot(true);
//				}
//			}
//			
//		}
//		return listPrivate;
//	}
	
	private void checkedImage(List<PrivateImage> list){
		if(id == null){return;}
		for (PrivateImage privateImage : list) {
			if(( null == checkImageId ?id:checkImageId).equals(privateImage.getId())){
				privateImage.setChecked(true);
				break;
			}
		}
	}
	
	//求两个数组的交集   
//  public static String[] intersect(String[] arr1, String[] arr2) {
	public static LinkedList<String> intersect(String[] arr1, String[] arr2) {
      Map<String, Boolean> map = new HashMap<String, Boolean>();
      LinkedList<String> list = new LinkedList<String>();
      for (String str : arr1) {   
          if (!map.containsKey(str)) {   
              map.put(str, Boolean.FALSE);   
          }   
      }   
      for (String str : arr2) {   
          if (map.containsKey(str)) {   
              map.put(str, Boolean.TRUE);   
          }   
      }   

      for (Entry<String, Boolean> e : map.entrySet()) {
          if (e.getValue().equals(Boolean.TRUE)) {   
              list.add(e.getKey());   
          }   
      }   

//      String[] result = {};   
//      return list.toArray(result);
      return list;
  } 
	
	
	/***
	 * 生产环境过滤镜像版本是否已导入
	 * @param list
	 * @return
	 */
	private List<PrivateImageVersion> privateImageVersionFilter(List<PrivateImageVersion> list){
		if(null == queryType)return list;
		
		String appPermissionId = getSessionMap().get("opertype").toString();;
		
		if(!appPermissionId.contains("3"))return list;
		
		List<PrivateImageVersion> filterList = new ArrayList<PrivateImageVersion>();
		for (PrivateImageVersion version : list) {
			if(!version.getIsImported().equals("0")){
				filterList.add(version);
			}
		}
		return filterList;
	}
	/**
	 * 查询私有镜像版本列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void queryPrivateImageVersionList() throws Exception {
		try {
			logger.info("查询私有镜像版本列表，私有镜像Id：" + id);
			Page<PrivateImageVersion> page = this.getJqGridPage("createTime");
			
			List<PrivateImageVersion> list = privateImageService.queryPrivateImageVersionList(id, version); //privateImageId
			
			list = privateImageVersionFilter(list);
			
			String typeIds = "";
			
			String type = "";
			type = getSessionMap().get("selectedOpertype").toString(); //操作类型（开发、测试、运维）
			String[] opertypeTemp = type.split(",");
			if(opertypeTemp != null && opertypeTemp.length > 1){
				if(opertypeTemp[0].equals(Constants.TEST_TYPE) && opertypeTemp[1].equals(Constants.OPERATOR_TYPE)){ //测试、运维
					List<PrivateImageVersion> list4Test = new ArrayList<PrivateImageVersion>();
					for(PrivateImageVersion version : list){
						if(version.getStatus().equals(Constants.TEST_TYPE) || version.getStatus().equals(Constants.OPERATOR_TYPE)){
							list4Test.add(version);
						}
					}
					checkedVersion(list4Test);
					page.setResult(list4Test);
					page.setTotalCount(list4Test.size());
				}else{ //管理员、同时是开发和测试、同时是开发和运维
					
					User user = (User) getSessionMap().get(Constants.CURRENT_USER);
					//角色应用关联关系
					List<RolePermissionRelation> dataPermission = null;
					if (!userManagerService.isSuperManager(user.getId())) { //超级管理员，具有全部的数据权限和操作权限
						dataPermission = userManagerService.queryUserPermission(user.getId());
						if(!"0".equals(appId)){
							
							for (int i = 0;i<dataPermission.size();i++) {
								//类型2的为应用信息
								if(dataPermission.get(i).getPermissionType() == 2){
									//获取集合中应用一致的 类型名称
									if(dataPermission.get(i).getPermissionId().equals(String.valueOf(appId))){
										//去重复
										if(!typeIds.contains(String.valueOf(dataPermission.get(i).getOpertype()))){
											typeIds += dataPermission.get(i).getOpertype()+",";
										}
									}
								}
							}
							String[] tempTypeIds = null;
							if(typeIds.contains(",")){
								typeIds = typeIds.substring(0, typeIds.lastIndexOf(","));
								tempTypeIds = typeIds.split(",");
							}
							if(tempTypeIds!=null && typeIds.contains(",")){
								typeIds = "";
								int[] sortTypeIds = new int[tempTypeIds.length];
								for (int i = 0; i < sortTypeIds.length; i++) {
									sortTypeIds[i] =Integer.valueOf(tempTypeIds[i]);
								}
								Arrays.sort(sortTypeIds);
								for (int i = 0; i < sortTypeIds.length; i++) {
									typeIds +=sortTypeIds[i];
									if(i != sortTypeIds.length-1){
										typeIds+=",";
									}
								}
							}
						}
						
						String[] arr1 = type.split(",");
						String[] arr2 = typeIds.split(",");
						
						List<PrivateImageVersion> list4Test = new ArrayList<PrivateImageVersion>();
						for(PrivateImageVersion version : list){
							
							if(intersect(arr1, arr2).size() > 1){
//								for(int i=0; i< intersect(arr1, arr2).size(); i++){
//									if(version.getStatus().equals(intersect(arr1, arr2).get(i)) || version.getStatus().equals(Constants.OPERATOR_TYPE)){
//										list4Test.add(version);
//									}
//								}
								list4Test.add(version);
							}else{
								if(version.getStatus().equals(intersect(arr1, arr2).get(0)) || version.getStatus().equals(Constants.OPERATOR_TYPE)){
									list4Test.add(version);
								}
							}
						}
						checkedVersion(list4Test);
						page.setResult(list4Test);
						page.setTotalCount(list4Test.size());
					}else{
						checkedVersion(list);
						page.setResult(list);
						page.setTotalCount(list.size());
					}
					
//					checkedVersion(list);
//					page.setResult(list);
//					page.setTotalCount(list.size());
				}
				
			}else{
				if(opertypeTemp[0].equals(Constants.DEVELOPER_TYPE)){ //开发
					checkedVersion(list);
					page.setResult(list);
					page.setTotalCount(list.size());
				}else if(opertypeTemp[0].equals(Constants.TEST_TYPE)){ //测试
					List<PrivateImageVersion> list4Test = new ArrayList<PrivateImageVersion>();
					for(PrivateImageVersion version : list){
						if(version.getStatus().equals(type) || version.getStatus().equals("3")){
							list4Test.add(version);
						}
					}
					checkedVersion(list4Test);
					page.setResult(list4Test);
					page.setTotalCount(list4Test.size());
				}else if(opertypeTemp[0].equals(Constants.OPERATOR_TYPE)){ //运维
					List<PrivateImageVersion> list4Operator = new ArrayList<PrivateImageVersion>();
					for(PrivateImageVersion version : list){
						if(version.getStatus().equals(type)){
							list4Operator.add(version);
						}
					}
					checkedVersion(list4Operator);
					page.setResult(list4Operator);
					page.setTotalCount(list4Operator.size());
				}else{
					page.setResult(Collections.EMPTY_LIST);
					page.setTotalCount(0);
				}
			}
			logger.info("查询私有镜像版本列表完成，返回条数：" + page.getTotalCount());
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException ex) {
			logger.error("查询私有镜像版本信息异常", ex);
			this.sendFailResult2jqGrid(ex.getKey(),ex.toString());
		}
	}
	
	private void checkedVersion(List<PrivateImageVersion> list){
		int[] array = new int[list.size()];
		if(privateImageVersionId == null || "".equals(privateImageVersionId)){return;}
		int i = 0;
		for (PrivateImageVersion privateImageVersion : list) {
			if(privateImageVersionId.equals(privateImageVersion.getId())){
				privateImageVersion.setChecked(true);
				break;
			}else{
				array[i] =Integer.valueOf(privateImageVersion.getId());
				i++;
			}
		}
		if("maxId".equals(privateImageVersionId)){
			int maxId = max(array);
			for (PrivateImageVersion version : list) {
				if(version.getId().equals(String.valueOf(maxId))){
					version.setChecked(true);
					break;
				}
			}
		}
	}
	
	private static int max(int[] array) {
	    return max(array, 0);
	}
	
	private static int max(int[] array, int from) {
		    if (from == array.length - 1) {
		      return array[from];
		    }
		    return Math.max(array[from], max(array, from + 1));
	}
	/**
	 * 删除私有镜像
	 * 
	 * @throws Exception the exception
	 */
	public void deletePrivateImage() throws Exception {
		try {
			logger.info("删除私有镜像信息，id："+id);
			//1. 先判断私有镜像下面是否镜像版本，如果有，不能删除；
			List<PrivateImageVersion> list = privateImageService.queryPrivateImageVersionList(id, version);
			if(list != null && list.size() > 0){
				sendFailResult(Constants.PRIVATE_IMAGE_USED_BY_VERSION_ERROR, "该私有镜像有版本信息,不能删除");
				return;
			}
			
			String currentContext = PropertiesUtil.getValue("currentContext");
			//2. 开发不能删已发布到测试的私有镜像，测试不能删已发布到生产的私有镜像，但是测试可以删已发布到测试的私有镜像，生产可以删已发布到生产的私有镜像；
			privateImageService.deletePrivateImage(id, currentContext);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("删除私有镜像信息异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 修改私有镜像详情字段并保存
	 * @throws Exception
	 */
	public void updateDescription() throws Exception {
		try {
			logger.info("查询私有镜像信息，id：" + id);
			// 1、根据ID查询私有镜像
			privateImage = privateImageService.queryPrivateImageById(id);
			logger.info("查询私有镜像信息，查询结果：" + privateImage.toString());
			// 2、修改私有镜像描述信息
			privateImage.setDescription(description);
			// 3、更新私有镜像实体
			privateImageService.updatePrivateImage(privateImage);
			logger.info("修改私有镜像描述信息成功");
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("修改私有镜像描述信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 删除私有镜像版本
	 * @throws Exception
	 */
	public void deletePrivateImageVersion() throws Exception {
		try {
			logger.info("删除私有镜像版本信息，私有镜像id："+ privateImageId +"，镜像版本id："+privateImageVersionId);
			
			//开发不能删已发布到测试的私有镜像，测试不能删已发布到生产的私有镜像，但是测试可以删已发布到测试的私有镜像，生产可以删已发布到生产的私有镜像；
			String selectedOpertype = getSessionMap().get("selectedOpertype").toString();
			System.out.println(selectedOpertype + ":" + status); //状态：1-开发-发布测试，2-测试-发布生产
			
			String[] opertypeTemp = selectedOpertype.split(",");
			if(!(opertypeTemp != null && opertypeTemp.length > 1)){ //如果操作类型多于一个就显示全部，type值即传空的字符串
				
				if(opertypeTemp[0].equals("1")){
					if(status.equals("2")){
						sendFailResult(Constants.PRIVATE_IMAGE_VERSION_PUBLISHED2TEST_ERROR, "该私有镜像有版本已发布测试,不能删除");
						return;
					}
				}else if(opertypeTemp[0].equals("2")){
					if(status.equals("3")){
						sendFailResult(Constants.PRIVATE_IMAGE_VERSION_PUBLISHED2PRODUCT_ERROR, "该私有镜像有版本已发布生产,不能删除");
						return;
					}
				}
			}
			String currentContext = PropertiesUtil.getValue("currentContext");
			privateImageService.deletePrivateImageVersion(privateImageId, privateImageVersionId, currentContext);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("删除私有镜像信息异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 发布测试
	 * @throws Exception
	 */
	public void publish2Test() throws Exception {
		try {
			logger.info("发布测试，私有镜像id："+ privateImageId +"，镜像版本id："+privateImageVersionId);
			
			privateImageService.publish2Test(privateImageId, privateImageVersionId);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("发布测试异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 私有镜像版本发布生产，版本号校验
	 * @throws Exception
	 */
	@UnLogging
	public void queryPrivateImageVersion() throws Exception {
		try {
			logger.info("发布生产，私有镜像id："+ privateImageId +"，镜像版本id："+privateImageVersionId);
			
			List<PrivateImageVersion> list = privateImageService.queryTheSamePrivateImageVersion(privateImageId, version);
			if(list.size() > 0){
				if(!list.get(0).getId().equals(privateImageVersionId)){
					sendSuccessReslutl();
				}else{
					sendErrorReslutl();
				}
			}else{
				sendErrorReslutl();
			}
		} catch (PaasWebException ex) {
			logger.error("发布生产异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 私有镜像版本-发布生产
	 * @throws Exception
	 */
	public void publish2Product() throws Exception {
		try {
			logger.info("发布生产，私有镜像id："+ privateImageId +"，镜像版本id："+privateImageVersionId+"，版本号："+version);
			
			privateImageService.publish2Product(privateImageId, privateImageVersionId, version);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("发布生产异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public String getRegistryUser() {
		return registryUser;
	}

	public void setRegistryUser(String registryUser) {
		this.registryUser = registryUser;
	}

	public String getRegistryPswd() {
		return registryPswd;
	}

	public void setRegistryPswd(String registryPswd) {
		this.registryPswd = registryPswd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPrivateImageId() {
		return privateImageId;
	}

	public void setPrivateImageId(String privateImageId) {
		this.privateImageId = privateImageId;
	}

	public PrivateImage getPrivateImage() {
		return privateImage;
	}

	public void setPrivateImage(PrivateImage privateImage) {
		this.privateImage = privateImage;
	}

	public String getPrivateImageVersionId() {
		return privateImageVersionId;
	}

	public void setPrivateImageVersionId(String privateImageVersionId) {
		this.privateImageVersionId = privateImageVersionId;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getPrivateImageType() {
		return privateImageType;
	}

	public void setPrivateImageType(int privateImageType) {
		this.privateImageType = privateImageType;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getCheckImageId() {
		return checkImageId;
	}

	public void setCheckImageId(String checkImageId) {
		this.checkImageId = checkImageId;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Map<String, Object> getSessionMap() {
		return ActionContext.getContext().getSession();
	}
}
