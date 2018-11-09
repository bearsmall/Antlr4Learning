/*
 * 文件名：		CommentRemover.java
 * 创建日期：	2013-03-29
 * 最近修改：	2013-04-25
 * 作者：		徐犇
 */
package com.cmp.pretreat;



import com.cmp.lang.Java;
import com.cmp.lang.Language;
import com.cmp.utils.RegexStorage;

import java.util.regex.Pattern;

/**
 * 去掉代码中注释的预处理类
 * @author ben
 *
 */
public class CommentRemover extends Pretreator {
	
	/**
	 * 全局唯一实例
	 */
	private static CommentRemover cr = null;
	
	/**
	 * 私有构造函数
	 */
	private CommentRemover() {
	}
	
	/**
	 * @return 一个本类的实例
	 */
	public static synchronized CommentRemover getInstance() {
		if(cr == null) {
			cr = new CommentRemover();
		}
		return cr;
	}
	
	/**
	 * 正则表达式仓库
	 */
	private RegexStorage res = RegexStorage.getInstance();
	
	@Override
	protected String excute(String codeText, Language lang) {
		Pattern p = null;
		if(lang instanceof Java) {//java代码
			p = res.getJavaCommentPattern();
		}else {
			p = res.getCCommentPattern();
		}
		if(p == null) {
			return codeText;
		}
		return p.matcher(codeText).replaceAll("$1");
	}

}
