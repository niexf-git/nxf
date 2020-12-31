/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.lang.reflect.converter;

import java.io.File;

import org.apache.commons.beanutils.Converter;

import com.cmsz.paas.common.io.Files;
import com.cmsz.paas.common.lang.reflect.Converts;

/**
 * string->file转换器
 * 
 * @author sam
 * 
 */
public class StringToFile implements Converter {

	static {
		Converts.addConvert(new StringToFile(), File.class);
	}

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) {

		return Files.of(value.toString());
	}

}
