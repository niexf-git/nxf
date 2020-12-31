/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.cmsz.paas.common.lang.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.cmsz.paas.common.lang.Lang;
import com.cmsz.paas.common.lang.Objects;
import com.cmsz.paas.common.lang.reflect.Beans;
import com.cmsz.paas.common.lang.reflect.Converts;

/**
 * 基于Collection接口的工具类
 * 
 * @author sam
 * 
 */
public abstract class Collections extends CollectionUtils {

	/**
	 * 将某个Collection转成数组
	 * 
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> c) {

		if (isEmpty(c))
			return null;
		T t = first(c);
		T[] tArray = (T[]) Array.newInstance(t.getClass(), c.size());
		c.toArray(tArray);
		return tArray;
	}

	/**
	 * 删除列表中的空项
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, C extends Collection<T>> C removeIfEmpty(C c) {

		if (isNotEmpty(c)) {
			List<T> l = newList();
			for (T t : c) {
				if (Objects.isEmpty(t))
					continue;
				l.add(t);
			}
			return (C) l;
		}
		return c;
	}

	/**
	 * 获取Collection中的第一个对象
	 * 
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T first(Collection<T> c) {

		return isNotEmpty(c) ? (T) get(c, 0) : null;
	}

	/**
	 * 获取Collection中的最后一个对象
	 * 
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T last(Collection<T> c) {

		return isNotEmpty(c) ? (T) get(c, c.size() - 1) : null;
	}

	/**
	 * 检测一个Collection是否为空
	 * 
	 * @param c
	 * @return
	 */
	// public static boolean isEmpty(Collection<?> c) {
	//
	// return c == null || c.size() == 0;
	// }

	/**
	 * ' 检测一个Collection是否不为空
	 * 
	 * @param c
	 * @return
	 */
	// public static boolean isNotEmpty(Collection<?> c) {
	//
	// return !isEmpty(c);
	// }

	/**
	 * 创建一个空的ArrayList
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> newList() {

		return new ArrayList<T>();
	}

	/**
	 * 分组一组数据
	 * 
	 * @param <K>
	 *            分组后的map的KEY
	 * @param <V>
	 *            分组后的map的VALUE
	 * @param values
	 *            要分组的数据
	 * @param property
	 *            分组的属性
	 * @param type
	 *            分组后的map的KEY 类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, List<V>> group(Collection<V> values,
			String property, Class<K> type) {
		Map<K, List<V>> group = Maps.newMap();
		if (isNotEmpty(values)) {
			for (V v : values) {
				Object o = Beans.getAnyProperty(v, property);
				if (v instanceof Map) {
					o = ((Map) v).get(property);
				}

				K k = Converts.convert(o, type);
				List<V> gList = group.get(k);
				if (gList == null) {
					gList = Lists.newList();
				}
				gList.add(v);
				group.put(k, gList);
			}
		}
		return group;
	}

	/**
	 * 根据一组动态参数创建一个List列表集
	 * 
	 * @param <T>
	 * @param objs
	 * @return
	 */
	public static <T> List<T> list(T... objs) {

		List<T> l = new ArrayList<T>();
		for (T o : objs) {
			l.add(o);
		}
		return l;
	}

	public static <T> List<T> linkedList(T... objs) {

		List<T> l = new LinkedList<T>();
		for (T o : objs)
			l.add(o);
		return l;
	}

	public static void main(String[] s) {
		Map<String, Integer> mm = Maps.map("key", 1);
		Lang.println(mm + "" + Beans.getAnyProperty(mm, "key"));
		List<Map<String, Integer>> data = Lists.newList();
		for (int i = 0; i < 90; i++) {
			Integer gkey = i < 30 ? 1000 : (i >= 30 && i < 60 ? 2000 : 3000);
			Map<String, Integer> m = Maps.map("key", gkey, "v", i);
			data.add(m);
		}
		Lang.println(group(data, "key", Integer.class));
	}
}
