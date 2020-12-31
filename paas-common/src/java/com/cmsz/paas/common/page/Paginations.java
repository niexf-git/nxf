/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.page;

import java.util.List;
import java.util.Map;

import com.cmsz.paas.common.lang.Strings;
import com.cmsz.paas.common.lang.collection.Lists;

/**
 * 分页处理工具类
 * 
 * @author Sam
 * 
 */
public class Paginations {
    
    public static Bound createBound(int pageNo, int pageSize) {

    	long offset = (pageNo - 1) * pageSize;
        return new Bound(offset, pageSize);
    }
    
    public static PageContext createContext(Map<?, ?> params, List<SortInfo> sorts, Bound bound) {

        PageContext context = new PageContext();
        context.addParams(params);
        context.addSorts(sorts);
        context.setBound(bound);
        return context;
    }
    
    public static PageContext createContext(Map<?, ?> params, List<SortInfo> sorts, int pageNo,
        int pageSize) {

        return createContext(params, sorts, createBound(pageNo, pageSize));
    }
    
    public static PageContext createContext(Map<?, ?> params, String sortname, String sortvalue,
        int pageNo, int pageSize) {

        List<SortInfo> sorts = null;
        if (Strings.isNotBlank(sortname) && Strings.isNotBlank(sortvalue)) {
            sorts = Lists.newList();
            sorts.add(new SortInfo(sortname, sortvalue));
        }
        return createContext(params, sorts, pageNo, pageSize);
        
    }
    
    public static <E> Page<E> createPage(long totalCount, Bound bound, List<E> result) {

        Page<E> p = new Page<E>(totalCount, bound, result);
        return p;
    }
    
}
