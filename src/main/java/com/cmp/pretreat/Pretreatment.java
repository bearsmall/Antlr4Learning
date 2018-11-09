/*
 * 文件名：		Pretreatment.java
 * 类名：		Pretreatment
 * 创建日期：	2011/02/20
 * 最近修改：	2013/03/01
 * 作者：		徐犇
 */

package com.cmp.pretreat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预处理类 封装各种预处理的方法和算法，大多数以静态函数的方式提供使用
 * @author ben
 */
public final class Pretreatment {

	/**
	 * 去掉多余的逗号，采用这种办法兼容GNU C的一些语法
	 * 
	 * @param code
	 * @return 处理后的代码文本
	 */
	public static String removeExtraComma(String code) {
		if (code == null || code.trim().length() <= 0) {
			return null;
		}
		String ret = code.replaceAll(",(\\s*[\\}\\)])", "$1");
		return ret;
	}

	/**
	 * 去掉__attribute__标记，这是GCC编译器产生的
	 * 
	 * @param code
	 * @return 处理后的代码文本
	 */
	public static String remove__attribute__(String code) {
		if (code == null || code.trim().length() <= 0) {
			return null;
		}
		String ret = code.replaceAll("__attribute__\\s*\\(\\([^)]*\\)\\)", "");
		/*
		 * 再去掉一些其它标记，为了方便暂不建新函数，直接在这里写
		 */
		// ret = ret.replaceAll("__attribute__\\s*\\(\\([^)]*\\)\\)", "");
		return ret;
	}

	/**
	 * 将代码中双引号字符串除掉
	 * 
	 * @param codes
	 *            给定的代码文本
	 * @return 去掉了了双引号字符串的代码文本
	 */
	public static String removeDoubleQuotation(String codes) {
		/**
		 * 匹配单引号字符和双引号字符串的正则表达式,将Replacement设为组1就不会删掉单引号字符内容
		 */
		return Pattern
				.compile(
						"([^\"']+|'[^'\\\\]*(?:\\\\.[^'\\\\]*)*'[^\"']*)|\"[^\\\\\"]*(?:\\\\.[^\\\\\"]*)*\"")
				.matcher(codes).replaceAll("$1");

	}

	/**
	 * 将C或C++源文件中的多行引号字符串转化成单行的统一的形式
	 * 
	 * @param file
	 * @return 处理完毕后的文本
	 */
	public static String formatQuotations(String file) {
		if (file == null) {
			return null;
		}
		/**
		 * 匹配单引号字符和双引号字符串的正则表达式
		 */
		Pattern p = Pattern
				.compile("'[^'\\\\]*(?:\\\\(?s).[^'\\\\]*)*'|\"[^\\\\\"]*(?:\\\\(?s).[^\\\\\"]*)*\"");
		Matcher m = p.matcher(file);
		StringBuffer result = new StringBuffer();
		String temp;
		while (m.find()) {
			temp = m.group();
			if (temp.charAt(0) == '\"') {
				temp = temp.replaceAll("\\\\[ \t\r]*\n", "");
			} else {
				temp = temp.replaceFirst("\\\\[ \t\r]*\n[ \t]*", "");
			}
			m.appendReplacement(result, Matcher.quoteReplacement(temp));
		}
		m.appendTail(result);
		return result.toString();
	}

}
