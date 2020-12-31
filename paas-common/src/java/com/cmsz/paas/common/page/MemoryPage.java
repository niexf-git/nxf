/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存分页对象
 * 
 * @author Sam
 * 
 * @param <E>
 */
public class MemoryPage<E> extends Page<E> {
    
    protected List<E> allData;
    
    public MemoryPage(Bound range, List<E> allData) {

        this.allData = allData;
        List<E> result = new ArrayList<E>();
        if (allData != null) {
        	long start = range.getOffset()==0?0:range.getOffset()-1;
        	long end   = start==0?range.getLimit():range.getLimit()-1;
        	long size  = allData.size();
            for (long i = start, len = end; i < len; i++) {
            	if (i < size) {
					result.add(allData.get((int) i));
				}
            }
        }
        setRange(range);
        setTotalCount(allData.size());
        setResult(result);
    }
    
    public MemoryPage(int pageNo, int pageSize, List<E> allData) {

        this(Paginations.createBound(pageNo, pageSize), allData);
    }
    
}
