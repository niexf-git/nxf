/**
 * 修改历史�?<br/>
 * =================================================================<br/>
 * 修改�? 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.ibatis3;

import org.apache.ibatis.session.SqlSession;

public interface SqlSessionCallback {
    
    public Object doInSession(SqlSession session);
    
}
