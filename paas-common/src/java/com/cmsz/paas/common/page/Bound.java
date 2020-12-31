/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;
/**
 * 分页查询的开始结束行对象
 * @author Sam
 *
 */
public class Bound {
    
    private long offset;
    private long limit;
    
    public Bound(long offset, long limit) {

        this.offset = offset;
        this.limit = limit;
    }
    
    public long getLimit() {

        return limit;
    }
    
    public long getOffset() {

        return offset;
    }
    
    public void setLimit(long limit) {

        this.limit = limit;
    }
    
    public void setOffset(long offset) {

        this.offset = offset;
    }
    
}
