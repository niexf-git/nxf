package com.cmsz.paas.web.grafana.dao.impl;

import org.springframework.stereotype.Repository;

import com.cmsz.paas.common.restdao.impl.ResponseInfoRestDaoImpl;
import com.cmsz.paas.web.grafana.dao.GrafanaDao;

@Repository("grafanaDao")
public class GrafanaDaoImpl extends ResponseInfoRestDaoImpl implements GrafanaDao{

}
