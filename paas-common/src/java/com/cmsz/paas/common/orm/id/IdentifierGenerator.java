/**
 * 修改历史�?<br/>
 * =================================================================<br/>
 * 修改�? 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.orm.id;

import java.io.Serializable;

/**
 * The general contract between a class that generates unique identifiers and the <tt>Session</tt>.
 * It is not intended that this interface ever be exposed to the application. It <b>is</b> intended
 * that users implement this interface to provide custom identifier generation strategies.<br>
 * <br>
 * Implementors should provide a public default constructor.<br>
 * <br>
 * Implementations that accept configuration parameters should also implement <tt>Configurable</tt>. <br>
 * Implementors <em>must</em> be threadsafe
 * 
 * @author Gavin King
 * @see PersistentIdentifierGenerator
 * @see Configurable
 */
public interface IdentifierGenerator {
    
    /**
     * The configuration parameter holding the entity name
     */
    public static final String ENTITY_NAME = "entity_name";
    
    /**
     * Generate a new identifier.
     */
    public Serializable generate();
    
}
