/*
 * 文件名：		LineContinuationRemover.java
 * 创建日期：	2013-03-30
 * 最近修改：	2013-04-25
 * 作者：		徐犇
 */
package com.cmp.pretreat;


import com.cmp.lang.Language;

/**
 * 去掉代码中续行符\，并将所续行并到前一行的预处理类
 * 
 * @author ben
 * 
 */
public class LineContinuationRemover extends Pretreator {

	/**
	 * 全局唯一实例
	 */
	private static LineContinuationRemover lcr = null;
	
	/**
	 * 私有构造函数
	 */
	private LineContinuationRemover() {
	}
	
	/**
	 * @return 一个本类的实例
	 */
	public static synchronized LineContinuationRemover getInstance() {
		if(lcr == null) {
			lcr = new LineContinuationRemover();
		}
		return lcr;
	}
	
	@Override
	protected String excute(String codeText, Language lang) {
		return codeText.replaceAll("\\\\\\s*\n", "").trim();
	}

}
