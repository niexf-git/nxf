package com.cmsz.paas.web.image.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.paas.common.log.annotations.UnLogging;
import com.cmsz.paas.web.application.model.DataCenterInfo;
import com.cmsz.paas.web.application.service.ApplicationService;
import com.cmsz.paas.web.base.action.AbstractAction;
import com.cmsz.paas.web.base.dao.page.Page;
import com.cmsz.paas.web.base.exception.PaasWebException;
import com.cmsz.paas.web.base.util.JackJson;
import com.cmsz.paas.web.image.model.PublicImage;
import com.cmsz.paas.web.image.service.PublicImageService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 公共镜像管理类
 * 
 * @author lin.my 2016-4-18
 */
public class PublicImageAction extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2197427342741527906L;

	private static final Logger logger = LoggerFactory
			.getLogger(PublicImageAction.class);

	@Autowired
	private PublicImageService publicImageService;
	
	@Autowired
	private ApplicationService applicationRestService;

	private String id;

	private String name = "";

	private String type;

	private String deployPath;

	private String startCmd;

	private String logDir;

	private String description;

	private String registryUrl;

	private String version;

	private String status;

	private PublicImage publicImage;
	
	private String publicImageId = ""; //[发布生产]的时候使用
	
	private String versionId; //[发布生产]的时候使用
	
	private List<DataCenterInfo> dataCenterInfo; //数据中心信息
	
	private String dataCenterIds; //数据中心ID
	
	private String oper;
	
	private String configFilePath;
	
	private String queryType;
	
	// private List<PublicImageVersion> versions;
	//
	// public List<PublicImageVersion> getVersions() {
	// return versions;
	// }
	//
	// public void setVersions(List<PublicImageVersion> versions) {
	// this.versions = versions;
	// }

	public String getConfigFilePath() {
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	/**
	 * 查询公共镜像列表
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public void queryPublicImageList() throws Exception {
		try {
			logger.info("查询公共镜像列表");
			Page<PublicImage> page = this.getJqGridPage("createTime");
			String roleType = ""; // roleType(int):1-管理员，2-普通用户【接口中定义的类型】
			String tempRoleType = getSessionMap().get("roleType") + ""; // 当前用户的角色类型，0：管理员，1：普通用户
			if (tempRoleType.equals("0") && (oper == null || "".equals(oper))) {
				roleType = "1";
			} else {
				roleType = "2";
			}

			String imageType = "";

			List<PublicImage> list = publicImageService.queryPublicImageList(name, roleType, imageType, publicImageId);
			
			if(oper != "" && null != oper && oper.equals("serviceOper")){
				list = filterList(list);
			}

			checkedImage(list);
			page.setResult(list);
			page.setTotalCount(list.size());
			
			logger.info("查询公共镜像列表完成，返回条数：" + page.getTotalCount());
			this.renderText(JackJson.fromObjectToJson(page));
		} catch (PaasWebException ex) {
			logger.error("查询公共镜像信息异常", ex);
			this.sendFailResult4jqGrid(ex.getKey(), ex.toString());
		}
	}
	
	private void checkedImage(List<PublicImage> list){
		if(id == null){return;}
		for (PublicImage publicImage : list) {
			if(id.equals(publicImage.getId())){
				publicImage.setChecked(true);
				break;
			}
		}
	}
	
	private List<PublicImage> filterList(List<PublicImage> list){
		List<PublicImage> tempList = new ArrayList<PublicImage>();
		for (PublicImage publicImage : list) {
			if(publicImage.getStatus().equals("1")){
				tempList.add(publicImage);
			}
		}
		return tempList;
	}

	/**
	 * 根据ID查询公共镜像
	 * 
	 * @return
	 * @throws Exception
	 */
	@UnLogging
	public String queryPublicImageById() throws Exception {
		try {
			logger.info("查询公共镜像信息，id：" + id);
			publicImage = publicImageService.queryPublicImageById(id);

			logger.info("查询公共镜像信息，查询结果：" + publicImage.toString());
		} catch (PaasWebException ex) {
			logger.error("查询公共镜像信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
		return SUCCESS;
	}

	/**
	 * 根据ID查询公共镜像的版本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public void queryPublicImageVersionById() throws Exception {
		String appPermissionId = "";
		try {
			logger.info("查询公共镜像的版本信息，id：" + id);
			PublicImage publicImage = publicImageService
					.queryPublicImageById(id);
			appPermissionId = getSessionMap().get("opertype").toString();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("imageVersion", publicImage.getVersion());
			jsonObject.put("imageVersionId", publicImage.getVersionId());
			jsonObject.put("imageLogDir", publicImage.getLogDir());
			jsonObject.put("imageConfigFilePath", publicImage.getConfigFilePath());//配置文件完整路径
			jsonObject.put("code", "0");
			if(appPermissionId.indexOf("3") > -1 && publicImage.getIsImported().equals("0")){
				jsonObject = new JSONObject();
				jsonObject.put("code", "-1");
			}
			sendSuccessReslult(jsonObject.toString());// 返回”镜像版本名称_镜像版本id“串
			logger.info("查询公共镜像的版本信息，查询结果：" + publicImage.toString());
		} catch (PaasWebException ex) {
			logger.error("查询公共镜像的版本信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 修改公共镜像信息
	 * 
	 * @throws Exception
	 */
	public void updatePublicImage() throws Exception {
		try {
			logger.info("修改公共镜像信息，修改内容：" + publicImage.toString());
			publicImageService.updatePublicImage(publicImage);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("修改公共镜像信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 单独修改公共镜像描述信息
	 * 
	 * @throws Exception
	 */
	public void updateDescription() throws Exception {
		try {
			logger.info("查询公共镜像信息，id：" + id);
			// 1、根据ID查询公共镜像
			publicImage = publicImageService.queryPublicImageById(id);
			logger.info("查询公共镜像信息，查询结果：" + publicImage.toString());
			// 2、修改公共镜像描述信息
			publicImage.setDescription(description);
			// 3、更新公共镜像实体
			publicImageService.updatePublicImage(publicImage);
			logger.info("修改公共镜像描述信息成功");
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("修改公共镜像描述信息异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}

	/**
	 * 切换公共镜像可见状态on/off
	 * 
	 * @throws Exception
	 */
	public void changeStatus() throws Exception {
		try {
			logger.info("查询公共镜像信息，id：" + id);
			// 1、根据ID查询公共镜像
			publicImage = publicImageService.queryPublicImageById(id);
			logger.info("查询公共镜像信息，查询结果：" + publicImage.toString());

			// 1:可见状态on 0:不可见状态off
			if (status.equals("1")) {
				publicImage.setStatus("0");
			} else {
				publicImage.setStatus("1");
			}
			// 2、修改公共镜像状态
			publicImage.getVersions().get(0).setStatus(publicImage.getStatus());
			// 3、更新公共镜像实体
			publicImageService.updatePublicImage(publicImage);
			logger.info("切换公共镜像可见状态on/off成功");
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("切换公共镜像可见状态on/off异常", ex);
			this.sendFailResult(ex.getKey(), ex.toString());
		}
	}
	
	/**
	 * 公共镜像-发布生产
	 * @throws Exception
	 */
	public void publish2Product() throws Exception {
		try {
			logger.info("发布生产，公共镜像ID："+ publicImageId +"，镜像版本ID："+versionId+"，数据中心ID："+dataCenterIds);
			publicImageService.publish2Product(publicImageId, versionId, dataCenterIds);
			sendSuccessReslutl();
		} catch (PaasWebException ex) {
			logger.error("发布生产异常", ex);
			this.sendFailResult(ex.getKey(),ex.toString());
		}
	}
	
	/**
	 * 加载数据中心信息
	 * @return
	 * @throws Exception
	 */
	@UnLogging
//	public String loadDataCenterInfo() throws Exception {
//		
//		logger.info("公共镜像发布生产，开发加载数据中心信息");
//		dataCenterInfo = applicationRestService.quertCenterLists();
//		return SUCCESS;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeployPath() {
		return deployPath;
	}

	public void setDeployPath(String deployPath) {
		this.deployPath = deployPath;
	}

	public String getStartCmd() {
		return startCmd;
	}

	public void setStartCmd(String startCmd) {
		this.startCmd = startCmd;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegistryUrl() {
		return registryUrl;
	}

	public void setRegistryUrl(String registryUrl) {
		this.registryUrl = registryUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PublicImage getPublicImage() {
		return publicImage;
	}

	public void setPublicImage(PublicImage publicImage) {
		this.publicImage = publicImage;
	}

	public String getPublicImageId() {
		return publicImageId;
	}

	public void setPublicImageId(String publicImageId) {
		this.publicImageId = publicImageId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public List<DataCenterInfo> getDataCenterInfo() {
		return dataCenterInfo;
	}

	public void setDataCenterInfo(List<DataCenterInfo> dataCenterInfo) {
		this.dataCenterInfo = dataCenterInfo;
	}

	public String getDataCenterIds() {
		return dataCenterIds;
	}

	public void setDataCenterIds(String dataCenterIds) {
		this.dataCenterIds = dataCenterIds;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
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
