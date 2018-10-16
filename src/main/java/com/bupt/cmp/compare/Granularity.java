/*
 * 文件名：		Granularity.java
 * 创建日期：	2013-4-7
 * 最近修改：	2013-4-7
 * 作者：		徐犇
 */
package com.bupt.cmp.compare;

/**
 * 比对粒度枚举结构
 * 
 * @author Administrator
 */
public enum Granularity {
	CHARACTER, // 按字符
	LINE, // 按行
	PARAGRAPH, // 按段落
	BLOCK,//分块比对
	MLCS//按字符串匹配
	;
}
