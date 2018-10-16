/*
 * 文件名：		EmptyLineRemover.java
 * 创建日期：	2013-3-29
 * 最近修改：	2013-4-25
 * 作者：		徐犇
 */
package com.bupt.cmp.pretreat;


import com.bupt.cmp.lang.Language;

/**
 * 去掉代码中(多余)空行的预处理类
 * 
 * @author ben
 */
public class EmptyLineRemover extends Pretreator {

	/**
	 * 全局唯一实例
	 */
	private static EmptyLineRemover elr = null;
	
	/**
	 * 私有构造函数
	 */
	private EmptyLineRemover() {
	}
	
	/**
	 * @return 一个本类的实例
	 */
	public static synchronized EmptyLineRemover getInstance() {
		if(elr == null) {
			elr = new EmptyLineRemover();
		}
		return elr;
	}
	
	@Override
	protected String excute(String codeText, Language lang) {
		return codeText.replaceAll("\n\\s*\n", "\n").trim();
	}

}
