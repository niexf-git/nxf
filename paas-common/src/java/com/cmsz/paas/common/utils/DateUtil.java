package com.cmsz.paas.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static void main(String[] args) {
		String str = "Wed Jul 02 16:00:20 CST 2014";
		System.out.println("dd>>>>  " + tranformDate(str));
	}

	// 将CST日期转换为“yyyy-MM-dd HH:mm:ss”格式
	public static String tranformDate(String dateStr) {
		Date date = parse(dateStr, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormate = format.format(date);
		return dateFormate;
	}

	public static Date parse(String str, String pattern, Locale locale) {
		if (str == null || pattern == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern, locale).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date date, String pattern, Locale locale) {
		if (date == null || pattern == null) {
			return null;
		}
		return new SimpleDateFormat(pattern, locale).format(date);
	}

}
