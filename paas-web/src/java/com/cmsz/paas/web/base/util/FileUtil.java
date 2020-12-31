package com.cmsz.paas.web.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具类
 * 
 * @author liaohw
 * @date 2015-10-8
 */
public class FileUtil {
	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String read(String filePath) throws Exception {
		String line = null;
		BufferedReader br = null;
		StringBuffer buf = new StringBuffer();
		try {
			// 根据文件路径创建缓冲输入流
			br = new BufferedReader(new FileReader(filePath));
			// 循环读取文件的每一行
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append(System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 将内容回写到文件中
	 * 
	 * @param filePath
	 * @param content
	 * @throws Exception
	 */
	public static void write(String filePath, String content) throws Exception {
		BufferedWriter bw = null;
		try {
			// 根据文件路径创建缓冲输出流
			bw = new BufferedWriter(new FileWriter(filePath));
			// 将内容写入文件中
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "C:/Users/lenov/Desktop/grafana-template.json"; // 文件路径
		String content = FileUtil.read(filePath);
		System.out.println(content);
		FileUtil.write("C:/Users/lenov/Desktop/lhw-template2.json", content); // 读取修改文件
	}

}
