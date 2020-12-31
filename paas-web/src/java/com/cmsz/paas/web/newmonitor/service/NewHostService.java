package com.cmsz.paas.web.newmonitor.service;

import java.util.List;




import com.cmsz.paas.web.newmonitor.model.NewHost;


public interface NewHostService {

	// 查询主机列表
	public List<NewHost> queryHostList(String clusterId);


}
