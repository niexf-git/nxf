package com.cmsz.paas.web.cicd.service;
/**
 * 查询报告service
 * @author jiayz
 * 
 */
public interface QueryReportService {
	String getReportHtml(String flowId, String type);
}
