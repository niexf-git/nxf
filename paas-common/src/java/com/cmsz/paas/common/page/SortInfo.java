/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import com.cmsz.paas.common.lang.Strings;

/**
 * 排序条件
 * 
 * @author Sam
 * 
 */
public class SortInfo {
    
    private String sortField;
    private String sortValue;
    
    public SortInfo(String sortField, String order) {

        this.sortField = sortField;
        this.setSortValue(order);
    }
    
    public String getSortField() {

        return sortField;
    }
    
    public String getSortValue() {

        return sortValue;
    }
    
    public void setSortField(String sortField) {

        this.sortField = sortField;
    }
    
    public void setSortValue(String sortValue) {

        this.sortValue = sortValue;
    }
    
    @Override
	public String toString() {

        if (Strings.isNotEmpty(sortField) && Strings.isNotEmpty(sortValue)) {
            return Strings.BLANK + sortField + Strings.BLANK + sortValue;
        }
        return Strings.EMPTY;
    }
    
}
