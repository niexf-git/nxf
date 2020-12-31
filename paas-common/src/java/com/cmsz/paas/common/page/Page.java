/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import java.util.List;

/**
 * 分页结果对象
 * 
 * @author Sam
 * 
 * @param <E>
 */
public class Page<E> {
    
    protected List<E> result;
    protected long totalCount;
    protected Bound range;
    
    public Page() {

    }
    
    public Page(long totalCount, Bound range, List<E> result) {

        setTotalCount(totalCount);
        setResult(result);
        setRange(range);
    }
    
    public Bound getRange() {

        return range;
    }
    
    public List<E> getResult() {

        return result;
    }
    
    public long getTotalCount() {

        return totalCount;
    }
    
    public void setRange(Bound range) {

        this.range = range;
    }
    
    public void setResult(List<E> result) {

        this.result = result;
    }
    
    public void setTotalCount(long totalCount) {

        this.totalCount = totalCount;
    }
    
}
