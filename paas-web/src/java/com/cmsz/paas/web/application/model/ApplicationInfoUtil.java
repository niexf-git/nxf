package com.cmsz.paas.web.application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationInfoUtil {
	private boolean isProduct;

	private long appId;

	private String appName;

	private long dataCoreId;

	private String desc;

	private String loginName;

	private String[] role;

	private String[] roleDevelopment;

	private String[] roleTest;

	private String[] roleProduction;

	private String deveDataCoreId;

	private String testDataCoreId;

	private String[] colony;

	private String[] colonyDevelopment;

	private String[] colonyTest;


	private String[] colonyProduction;
	
	private String productClusterIds;
	
	private String dataIds;

	private String DisasterToleranceProByIpaas;
	public String getColonyTransferByIpaas() {
		return colonyTransferByIpaas;
	}

	public void setColonyTransferByIpaas(String colonyTransferByIpaas) {
		this.colonyTransferByIpaas = colonyTransferByIpaas;
	}

	public String getColonyTransferByApaas() {
		return colonyTransferByApaas;
	}

	public void setColonyTransferByApaas(String colonyTransferByApaas) {
		this.colonyTransferByApaas = colonyTransferByApaas;
	}

	private String DisasterToleranceProByApaas;
	private String colonyTransferByIpaas;
	private String colonyTransferByApaas;

	public String getDisasterToleranceProByIpaas() {
		return DisasterToleranceProByIpaas;
	}

	public void setDisasterToleranceProByIpaas(
			String disasterToleranceProByIpaas) {
		DisasterToleranceProByIpaas = disasterToleranceProByIpaas;
	}

	public String getDisasterToleranceProByApaas() {
		return DisasterToleranceProByApaas;
	}

	public void setDisasterToleranceProByApaas(
			String disasterToleranceProByApaas) {
		DisasterToleranceProByApaas = disasterToleranceProByApaas;
	}

	/***
	 * 拆分角色和集群数据
	 * 
	 * @return 集合中下标0位角色的选项 1为集群的选项集合
	 * @throws Exception
	 */
	public List<Map<String, List<String>>> getTableData() throws Exception {
		List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
		list.add(getRoleData());
		list.add(getColonyData());
		return list;
	}

	private Map<String, List<String>> getColonyData() throws Exception {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (colonyDevelopment != null && colonyIsExistence("Development")) {
			List<String> colonyDevelopmentList = new ArrayList<String>();
			for (int i = 0; i < colonyDevelopment.length; i++) {
				colonyDevelopmentList.add(colonyDevelopment[i]);
			}
			map.put("Development", colonyDevelopmentList);
		}

		if (colonyTest != null && colonyIsExistence("Test")) {
			List<String> colonyDevelopmentList = new ArrayList<String>();
			for (int i = 0; i < colonyTest.length; i++) {
				colonyDevelopmentList.add(colonyTest[i]);
			}
			map.put("Test", colonyDevelopmentList);
		}

		if (colonyProduction != null && colonyIsExistence("Production")) {
			List<String> colonyProductionList = new ArrayList<String>();
			for (int i = 0; i < colonyProduction.length; i++) {
				colonyProductionList.add(colonyProduction[i]);
			}
			map.put("Production", colonyProductionList);
		}
		List<String> colonyTransfer=new ArrayList<String>();
		if (colonyTransferByIpaas != null && colonyTransferByIpaas != "") {
			colonyTransfer.add(colonyTransferByIpaas);
		}
		if (colonyTransferByApaas != null && colonyTransferByApaas != "") {
			colonyTransfer.add(colonyTransferByApaas);
		}
		if (colonyProduction != null && colonyIsExistence("ProductionTransfer")) {
			List<String> colonyProductionTransferList = new ArrayList<String>();
			for (int i = 0; i < colonyTransfer.size(); i++) {
				colonyProductionTransferList.add(colonyTransfer.get(i));
			}
			map.put("ProductionTransfer", colonyProductionTransferList);
		}
		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
		if (map.size() > 0 && colony != null) {
			for (int i = 0; i < colony.length; i++) {
				if (map.containsKey(colony[i])) {
					tempMap.put(colony[i], map.get(colony[i]));
				}

			}
		} else {
			return null;
		}
		return map;
	}

	private Map<String, List<String>> getRoleData() throws Exception {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (roleDevelopment != null && roleIsExistence("Development")) {
			List<String> colonyDevelopmentList = new ArrayList<String>();
			for (int i = 0; i < roleDevelopment.length; i++) {
				colonyDevelopmentList.add(roleDevelopment[i]);
			}
			map.put("Development", colonyDevelopmentList);
		}

		if (roleTest != null && roleIsExistence("Test")) {
			List<String> colonyDevelopmentList = new ArrayList<String>();
			for (int i = 0; i < roleTest.length; i++) {
				colonyDevelopmentList.add(roleTest[i]);
			}
			map.put("Test", colonyDevelopmentList);
		}

		if (roleProduction != null && roleIsExistence("Production")) {
			List<String> colonyProductionList = new ArrayList<String>();
			for (int i = 0; i < roleProduction.length; i++) {
				colonyProductionList.add(roleProduction[i]);
			}
			map.put("Production", colonyProductionList);
		}

		if (roleProduction != null && roleIsExistence("Production")) {
			List<String> colonyProductionList = new ArrayList<String>();
			for (int i = 0; i < roleProduction.length; i++) {
				colonyProductionList.add(roleProduction[i]);
			}
			map.put("Production", colonyProductionList);
		}

		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
		if (map.size() > 0 && role != null) {
			for (int i = 0; i < role.length; i++) {
				if (map.containsKey(role[i])) {
					tempMap.put(role[i], map.get(role[i]));
				}

			}
		} else {
			return null;
		}

		return map;
	}

	public boolean colonyIsExistence(String Str) {
		boolean flag = false;
		if (colony != null) {
			for (int i = 0; i < colony.length; i++) {
				if (colony[i].equals(Str)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public boolean roleIsExistence(String Str) {
		boolean flag = false;
		if (role != null) {
			for (int i = 0; i < role.length; i++) {
				if (role[i].equals(Str)) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {
		ApplicationInfoUtil applicationInfoUtil = new ApplicationInfoUtil();
		applicationInfoUtil.getColonyData();
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getDataCoreId() {
		return dataCoreId;
	}

	public void setDataCoreId(long dataCoreId) {
		this.dataCoreId = dataCoreId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public String[] getRoleDevelopment() {
		return roleDevelopment;
	}

	public void setRoleDevelopment(String[] roleDevelopment) {
		this.roleDevelopment = roleDevelopment;
	}

	public String[] getRoleTest() {
		return roleTest;
	}

	public void setRoleTest(String[] roleTest) {
		this.roleTest = roleTest;
	}

	public String[] getRoleProduction() {
		return roleProduction;
	}

	public void setRoleProduction(String[] roleProduction) {
		this.roleProduction = roleProduction;
	}

	public String[] getColony() {
		return colony;
	}

	public void setColony(String[] colony) {
		this.colony = colony;
	}

	public String[] getColonyDevelopment() {
		return colonyDevelopment;
	}

	public void setColonyDevelopment(String[] colonyDevelopment) {
		this.colonyDevelopment = colonyDevelopment;
	}

	public String[] getColonyTest() {
		return colonyTest;
	}

	public void setColonyTest(String[] colonyTest) {
		this.colonyTest = colonyTest;
	}

	public String[] getColonyProduction() {
		return colonyProduction;
	}

	public void setColonyProduction(String[] colonyProduction) {
		this.colonyProduction = colonyProduction;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public boolean isProduct() {
		return isProduct;
	}

	public void setProduct(boolean isProduct) {
		this.isProduct = isProduct;
	}

	public String getDeveDataCoreId() {
		return deveDataCoreId;
	}

	public void setDeveDataCoreId(String deveDataCoreId) {
		this.deveDataCoreId = deveDataCoreId;
	}

	public String getTestDataCoreId() {
		return testDataCoreId;
	}

	public void setTestDataCoreId(String testDataCoreId) {
		this.testDataCoreId = testDataCoreId;
	}

	public String getProductClusterIds() {
		return productClusterIds;
	}

	public void setProductClusterIds(String productClusterIds) {
		this.productClusterIds = productClusterIds;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}


}
