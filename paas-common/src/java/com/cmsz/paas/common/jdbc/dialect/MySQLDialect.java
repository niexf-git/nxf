/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.jdbc.dialect;

/**
 * 基于Mysql的SQL分页实现
 * @author badqiu(rapid-framework作者)
 */
public class MySQLDialect extends Dialect {
    
    public boolean supportsLimitOffset() {

        return true;
    }
    
    public boolean supportsLimit() {

        return true;
    }
    
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit,
        String limitPlaceholder) {

        if (offset > 0) {
            return sql + " limit " + offset + "," + limit;
        } else {
            return sql + " limit " + limit;
        }
    }
    
}
