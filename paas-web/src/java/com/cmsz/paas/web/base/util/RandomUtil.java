package com.cmsz.paas.web.base.util;

import java.util.Random;

public class RandomUtil {
	// length表示生成随机字符串的长度
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	/*
	 * total:总量
	 * sampleSize:样本数量
	 */
	public static int[] getRandomIndexArray(int total, int sampleSize) {
		if (total <= 0 || sampleSize <= 0) {
			return null;
		}

		int[] arr = new int[total];
		if (total <= sampleSize) {
			for (int i = 0; i < arr.length; i++) {
				arr[i] = 1;
			}
			return arr;
		}

		Random random = new Random();
		int Interval = 0;
		if (total % sampleSize == 0) {
			Interval = total / sampleSize;
		} else {
			Interval = total / sampleSize + 1;
		}
		System.out.println("取数间隔=" + Interval);

		int count = 0;
		int index = 0;
		out: 
		while (count < sampleSize) {
			for (int i = 0; i < total; i += Interval) {
				index = i + random.nextInt(Interval);
				if (index >= total) {
					continue;
				}
				if (arr[index] == 0) {
					arr[index] = 1;
					count++;
					if (count == sampleSize) {
						break out;
					}
				}
			}
		}
		return arr;
	}

}
