package com.bupt.cmp.lang;


import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.parser.c.EG1;
import com.bupt.cmp.parser.c.ParseException;

import java.io.StringReader;

/**
 * C语言
 */
public class C implements Language {
	//Java的token，需要改成C的
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
	private static C c;

	/**
	 * 名称
	 */
	private String name = "C";
	
	/**
	 * @return 本类的一个实例
	 */
	public synchronized static C getInstance() {
		if (c == null) {
			c = new C();
		}
		return c;
	}
	
	/**
	 * 构造函数私有化
	 */
	private C() {
	}

	@Override
	public String[] getKeyWordsArray() {
		return this.keyWords;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	//没有用，占坑的
	@Override
	public int[] getTokenSequence(DefaultCodeFile defaultCodeFile)  {
		EG1 lex = new EG1(new StringReader(defaultCodeFile.getContent()));
		int[] ret = new int[0];
		return ret;
		/*
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
		*/
	}
	public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile) throws Exception {
		EG1 lex = new EG1(new StringReader(defaultCodeFile.getContent()));
		try {
			lex.runLineLexical(defaultCodeFile);
			return true;
		} catch (ParseException e) {
			throw e;
		}
	}
}
