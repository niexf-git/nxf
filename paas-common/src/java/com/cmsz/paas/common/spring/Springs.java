/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.spring;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cmsz.paas.common.lang.collection.Collections;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 注意：此类的Bean定义所在的applicationContext*.xml文件必须设置default-lazy-init="false"或者定义
 * Springs <bean/>的属性lazy-init="false"
 * 
 * @author calvin,sam
 */
public class Springs implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {

		Springs.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {

		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {

		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	  /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 如果同Class类型配置了多个Bean，只返回第一个
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {

        checkApplicationContext();
        Map beans = applicationContext.getBeansOfType(clazz);
        return (T) Collections.first(beans.values());
    }

	/**
	 * 获取一个在SpringContext定义的消息资源的某个消息
	 * 
	 * @return
	 */
	public static String getMessage(String code, String defaultMessage,
			Locale locale, Object... args) {

		return getApplicationContext().getMessage(code, args, defaultMessage,
				locale);
	}

	/**
	 * 获取一个在SpringContext定义的消息资源的某个消息
	 * 
	 * @return
	 */
	public static String getMessage(String code, Locale locale, Object... args) {

		return getApplicationContext().getMessage(code, args, locale);
	}

	public static String getMessage(String code, Object... args) {

		return getMessage(code, Locale.CHINA, args);
	}

	// @SuppressWarnings("unchecked")
	// public static <T> T createBean(Class<T> beanClass, String beanName) {
	//
	// AutowireCapableBeanFactory af = Springs.getApplicationContext()
	// .getAutowireCapableBeanFactory();
	// return (T) af.createBean(beanClass,
	// AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
	// }

	private static void checkApplicationContext() {

		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义Springs");
		}
	}
}
