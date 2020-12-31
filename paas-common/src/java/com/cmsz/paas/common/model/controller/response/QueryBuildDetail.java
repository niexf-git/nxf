package com.cmsz.paas.common.model.controller.response; 

import com.cmsz.paas.common.model.controller.entity.QueryBuild;

/** 
 * @author zhangys 
 * @version date：2017年9月4日 下午4:01:29 
 * 类说明 
 */
public class QueryBuildDetail
{
    @Override
    public String toString()
    {
        return "queryBuildDetail [queryBuild=" + queryBuild + "]";
    }

    QueryBuild queryBuild;

    public QueryBuild getQueryBuild()
    {
        return queryBuild;
    }

    public void setQueryBuild(QueryBuild queryBuild)
    {
        this.queryBuild = queryBuild;
    }
}
 