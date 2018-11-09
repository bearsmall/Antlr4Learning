/*
 * 文件名：		RegexStorage.java
 * 创建日期：	2013-3-30
 * 最近修改：	2013-3-30
 * 作者：		徐犇
 */
package com.cmp.utils;

import java.util.regex.Pattern;

/**
 * 正则表达式仓库类。
 * 
 * 由于本程序中大量用到正则表达式，故建立此类对其进行集中存储与管理。通常情况下，本类中存储的正则表达式是被频繁使用的，通过本类作为中介，
 * 可以避免在使用的过程中频繁编译用到的正则表达式，从而在一定程度上提高程序运行效率。所以，理论上本程序中所有正则表达式的应用均应由本类作为中介。
 * 
 * @author ben
 * 
 */
public final class RegexStorage {
	/**
	 * 全局唯一实例
	 */
	private static RegexStorage res = null;

	/**
	 * 去除代码中C/C++注释的正则表达式模式
	 */
	private Pattern ccoment = null;

	/**
	 * 去除代码中Java风格注释的正则表达式模式
	 */
	private Pattern javacomment = null;

	private RegexStorage() {
	}

	/**
	 * @return 本类的一个实例
	 */
	public static synchronized RegexStorage getInstance() {
		if (res == null) {
			res = new RegexStorage();
		}
		return res;
	}

	/**
	 * 去除C/C++代码注释的正则表达式。
	 * 
	 * 模式可以匹配C/C++风格的注释(//和/xx/形式)和引号字符串，将Replacement设为组1就不会删掉引号字符串的内容。
	 * 模式会遍历字符串一次。
	 * 
	 * 如：Pattern p = getCCommentPattern(); text =
	 * p.matcher(text).replaceAll("$1"); 就可删除text中的注释。
	 * 
	 * @return 编译好的正则表达式模式
	 */
	public synchronized Pattern getCCommentPattern() {
		if (ccoment == null) {
			String rtext = "([^\"'/]+|\"[^\\\\\"]*(?:\\\\(?s).[^\\\\\"]*)*\"[^\"'/]*|'[^'\\\\]*(?:\\\\(?s).[^'\\\\]*)*'[^\"'/]*)|/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/|//[^\n]*";
			ccoment = Pattern.compile(rtext);
		}
		return ccoment;
	}

	/**
	 * 去除Java风格注释的正则表达式。
	 * 
	 * 模式可以匹配Java风格的注释(包括//和/xx/形式的C/C++风格的注释以及@型的注释)和引号字符串，
	 * 将Replacement设为组1就不会删掉引号字符串的内容。 模式会遍历字符串一次。
	 * 
	 * 如：Pattern p = getCCommentPattern(); text =
	 * p.matcher(text).replaceAll("$1"); 就可删除text中的注释。
	 * 
	 * @return 编译好的正则表达式模式
	 */
	public synchronized Pattern getJavaCommentPattern() {
		if (javacomment == null) {
			String rtext = "([^\"'/@]+|\"[^\\\\\"]*(?:\\\\(?s).[^\\\\\"]*)*\"[^\"'/@]*|'[^'\\\\]*(?:\\\\(?s).[^'\\\\]*)*'[^\"'/@]*)|/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/|//[^\n]*|@\\S*";
			javacomment = Pattern.compile(rtext);
		}
		return javacomment;
	}

}
