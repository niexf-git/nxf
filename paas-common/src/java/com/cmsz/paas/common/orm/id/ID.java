package com.cmsz.paas.common.orm.id;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ID {

	/**
	 * 要生成的ID类型，如'UUID'
	 * 
	 * @return
	 */
	GenType genType() default GenType.UUID;

	/**
	 * ID名字，如果是注释在类的话必须指定哪个字段为ID
	 * 
	 * @return
	 */
	String value() default "";

}
