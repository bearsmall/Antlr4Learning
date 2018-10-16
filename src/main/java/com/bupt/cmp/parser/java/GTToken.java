/*
 * 文件名：		GTToken.java
 * 创建日期：	2013-4-30
 * 最近修改：	2013-4-30
 * 作者：		徐犇
 */
package com.bupt.cmp.parser.java;

/**
 * 
 * 解析器辅助类
 * 
 * @author ben
 */
public final class GTToken extends Token {
	/**
	 * The version identifier for this Serializable class. Increment only if the
	 * <i>serialized</i> form of the class changes.
	 */
	private static final long serialVersionUID = 1L;

	int realKind = JavaParserConstants.GT;
	
	public GTToken(int ofKind, String image) {
		super(ofKind, image);
	}
	
}
