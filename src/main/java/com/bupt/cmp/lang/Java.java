/*
 * 文件名:		Java.java
 * 接口名：		Java
 * 创建日期：	2013-08-08
 * 最近修改：	2013-08-08
 * 作者：		lbl
 */
package com.bupt.cmp.lang;


import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.parser.java.JavaParser;
import com.bupt.cmp.parser.java.ParseException;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Java程序设计语言类
 * 
 * @author ben
 */
public class Java implements Language {
	/**
	 * Java语言关键字
	 */
	private final String[] keyWords = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "const", "continue", "default",
			"do", "double", "else", "extends", "final", "finally", "float",
			"for", "goto", "if", "implements", "import", "instanceof", "int",
			"interface", "long", "native", "new", "package", "private",
			"protected", "public", "return", "short", "static", "strictfp",
			"super", "switch", "synchronized", "this", "throw", "throws",
			"transient", "try", "void", "volatile", "while" };

	/**
	 * 全局唯一实例
	 */
	private static Java java;

	/**
	 * 名称
	 */
	private String name = "Java";
	
	/**
	 * @return 本类的一个实例
	 */
	public synchronized static Java getInstance() {
		if (java == null) {
			java = new Java();
		}
		return java;
	}
	
	/**
	 * 构造函数私有化
	 */
	private Java() {
	}

	@Override
	public String[] getKeyWordsArray() {
		return this.keyWords;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int[] getTokenSequence(DefaultCodeFile defaultCodeFile) throws Exception {
		JavaParser lex = new JavaParser(new StringReader(defaultCodeFile.getContent()));
		try {
			lex.runLexical();
			ArrayList<Integer> list = lex.getTokenList();
			int len = list.size();
			int[] ret = new int[len];
			for(int i = 0; i < len; i++) {
				ret[i] = list.get(i);
			}
			return ret;
		} catch (ParseException e) {
			throw e;
		}
	}
	public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile) throws Exception {
		JavaParser lex = new JavaParser(new StringReader(defaultCodeFile.getContent()));
		try {
			lex.runLineLexical(defaultCodeFile);
			return true;
		} catch (ParseException e) {
			throw e;
		}
	}

}
