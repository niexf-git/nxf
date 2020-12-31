/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.orm.id;

import java.lang.reflect.Field;

import org.springframework.util.Assert;

import com.cmsz.paas.common.lang.Arrays;
import com.cmsz.paas.common.lang.reflect.ClassWrapper;
import com.cmsz.paas.common.lang.reflect.Reflections;

public class IdHelper {

	public static String genId(GenType idtype) {

		if (idtype == GenType.UUID) {
			return (String) new UUIDHexGenerator().generate();
		}
		return null;
	}

	public static <T> T setId(T entity) {

		ClassWrapper<T> cw = ClassWrapper.wrap(entity);
		Field[] fields = cw.getFields(ID.class);
		if (Arrays.isNotEmpty(fields)) {
			Field idField = fields[0];
			if (ClassWrapper.isChildOf(String.class, idField.getType())) {
				GenType idtype = idField.getAnnotation(ID.class).genType();
				Reflections.setValue(entity, idField.getName(), genId(idtype));
			}
		} else {
			ID id = entity.getClass().getAnnotation(ID.class);
			Assert.notNull(id, "在类中定义ID类型必须指定ID字段名称！");
			Field idField = cw.getField(id.value());
			if (ClassWrapper.isChildOf(String.class, idField.getType())) {
				Reflections.setValue(entity, idField.getName(),
						genId(id.genType()));
			}
		}
		return entity;
	}
}
