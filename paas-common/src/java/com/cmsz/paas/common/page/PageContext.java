/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.lang.collection.Collections;
import com.cmsz.paas.common.lang.collection.Lists;
import com.cmsz.paas.common.lang.collection.Maps;

/**
 * 分页上下文对象,封装客户请求的分页信息及过滤条件参数
 * 
 * @author Sam
 * 
 */
public class PageContext implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
    private final Map p = new HashMap();
    private boolean usePagination = true;
    private boolean useSort = true;
    private final List<SortInfo> sorts = Lists.newList();
    private Bound bound;
    
    @SuppressWarnings("unchecked")
    public void addParam(Object key, Object value) {

        p.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public void addParams(Map params) {

        if (Maps.isNotEmpty(params)) {
			p.putAll(params);
		}
    }
    
    public void addSort(SortInfo sortInfo) {

        sorts.add(sortInfo);
    }
    
    public void addSort(String field, String sortvalue) {

        addSort(new SortInfo(field, sortvalue));
    }
    
    public void addSorts(List<SortInfo> sortInfo) {

        if (Collections.isNotEmpty(sortInfo)) {
			sorts.addAll(sortInfo);
		}
    }
    
    public Bound getBound() {

        return bound;
    }
    
    @SuppressWarnings("unchecked")
    public Map getP() {

        return p;
    }
    
    public List<SortInfo> getSorts() {

        return sorts;
    }
    
    public boolean isUsePagination() {

        return usePagination;
    }
    
    public boolean isUseSort() {

        return useSort;
    }
    
    public void removeParam(Object key) {

        p.remove(key);
    }
    
    public void removeSort(String sortField) {

        for (SortInfo s : sorts) {
            if (s.getSortField().equalsIgnoreCase(sortField)) {
                sorts.remove(s);
                break;
            }
        }
    }
    
    public void setBound(Bound bound) {

        this.bound = bound;
    }
    
    public void setUsePagination(boolean usePaging) {

        this.usePagination = usePaging;
    }
    
    public void setUseSort(boolean useSort) {

        this.useSort = useSort;
    }
}
