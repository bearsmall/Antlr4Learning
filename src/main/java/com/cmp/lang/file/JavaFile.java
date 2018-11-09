/*
 * 文件名:		JavaFile.java
 * 类名：		JavaFile
 * 创建日期：	2009-10-15
 * 最近修改：	2013-05-04
 * 作者：		徐犇
 */
package com.cmp.lang.file;

import com.cmp.DefaultCodeFile;
import com.cmp.lang.Java;
import com.cmp.pretreat.Pretreatment;
import com.cmp.pretreat.SpecialCharactersSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Java代码文件类
 * @author ben
 */
public class JavaFile extends DefaultCodeFile {

	public JavaFile(File f) {
		super(Java.getInstance());
		super.init(f);
	}

	public JavaFile(String code) {
		super(Java.getInstance());
		super.init(code);
	}

	@Override
	public int[] getKeySequence() throws Exception {
		String code = null;
		code = this.content + "\n";
		String temp = null;//JavaLexical.getKeySequence(code);
		String[] keys = temp.split("[\\s]*\n[\\s]*");
		int len = keys.length;
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = keys[i].hashCode();
		}
		return ans;
	}

	@Override
	public int[] convertCodeToKeySequence(String code) {
		String[] keyWord = this.lang.getKeyWordsArray();
		/**
		 * 去掉双引号字符串
		 */
		code = Pretreatment.removeDoubleQuotation(code);

		/**
		 * 调用函数将代码分割成字符串并保存在 数组中
		 */
		String[] codeList = SpecialCharactersSearch.splitCodeBySeparator(code);

		/**
		 * 使用LinkedList临时存储关键字序列
		 */
		LinkedList<Integer> kwList = new LinkedList<Integer>();

		for (int i = 0; i < codeList.length; i++) {
			String temp = codeList[i].trim();
			for (int j = 0; j < keyWord.length; j++) {
				if (temp.equals(keyWord[j])) {
					kwList.add(j);
					break;
				}
			}
		}

		/**
		 * 将LinkedList中的序列存到数组里并返回
		 */
		int len = kwList.size();
		int[] kwcs = new int[len];
		for (int i = 0; i < len; i++) {
			kwcs[i] = kwList.get(i);
		}
		return kwcs;
	}

	/**
	 * 根据给定的一个java类的内容，分析其中的函数 并一一提取出来
	 * 
	 * @param classText
	 * @return 字符串数组，数组的每个元素为一个函数的代码文本
	 */
	public static String[] getFunctions(String classText) {
		/**
		 * 保存找出的函数头的起始位置和结束位置 以及最终提取出的函数的ArrayList
		 */
		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();
		ArrayList<String> functions = new ArrayList<String>();

		/**
		 * 找出函数头的正则表达式
		 */
		// Pattern
		// pFunctionHead=Pattern.compile("[\\t ]*([\\w_$]+[ \\t]*)*\\(([\\w_$]+[ \\t]+[\\w_$]+)?([ \\t]*,[ \\t]*[\\w_$]+[ \\t]+[\\w_$]+)*\\)[\\s]*\\{");
		Pattern pFunctionHead = Pattern
				.compile("[\\t ]*[\\w$ \\t]*\\(([\\w$]+[ \\t]+[\\w$]+)?([ \\t]*,[ \\t]*[\\w$]+[ \\t]+[\\w$]+)*\\)[\\s]*\\{");
		Matcher mFunctionHead = pFunctionHead.matcher(classText);
		while (mFunctionHead.find()) {
			/**
			 * 排除catch程序块和匿名类对匹配函数的干扰
			 */
			if (!mFunctionHead.group().matches("[\\t ]*catch.*")
					&& !mFunctionHead.group().matches("[\\t ]*new.*")) {//
				starts.add(mFunctionHead.start());
				ends.add(mFunctionHead.end());
			}
		}

		/**
		 * 找出所有成对的双引号的正则表达式
		 */
		Pattern p = Pattern.compile("\"([^\\\\\"]|\\\\.)*\"");
		Matcher m = p.matcher(classText);
		char[] charArray = classText.toCharArray();
		int length = charArray.length;
		while (m.find()) {
			int s = m.start();
			int e = m.end();
			for (int i = s; i < e; i++) {
				if (charArray[i] == '{' || charArray[i] == '}')
					charArray[i] = ' ';
			}
		}

		int startsNum = starts.size(), index = 0;
		int s = 0;
		int e = s;
		while (index < startsNum) {
			s = ends.get(index);
			if (s < e) {
				index++;
				continue;
			}
			e = s;
			int flag = 1;
			while (flag > 0 && e < length) {
				if (charArray[e] == '{')
					flag++;
				else if (charArray[e] == '}')
					flag--;
				e++;
			}
			functions.add(classText.substring(starts.get(index), e));
			index++;
		}
		int size = functions.size();
		String[] results = new String[size];
		for (int i = 0; i < size; i++) {
			results[i] = functions.get(i);
		}
		return results;
	}

	/**
	 * 分析本文件中含有的java类 并将这些类一一提取出来
	 * 
	 * @return 字符串数组，数组的每一个元素为一个java类的字符串
	 */
	public String[] getClassSequence() throws Exception {
//		return JavaParser.runParser(this.content).getClassSequence();
		return null;
	}

	/**
	 * 分析本文件中含有的java类 并将这些类一一提取出来
	 * 
	 * @return 字符串数组，数组的每一个元素为一个java类的字符串
	 */
	@Deprecated
	public String[] getClasses() {

		String fileText = this.content;

		/**
		 * 保存找出的类头的起始位置和结束位置 以及最终提取出的类的ArrayList
		 */
		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();
		ArrayList<String> classes = new ArrayList<String>();

		/**
		 * 找出类头的正则表达式
		 */
		Pattern pClassHead = Pattern
				.compile("(\\s|(public\\s)|(private\\s)|(protected\\s)|(static\\s)|(final\\s)|(abstract\\s))*class\\s+[^\\{]+\\{");
		Matcher mClassHead = pClassHead.matcher(fileText);
		while (mClassHead.find()) {
			starts.add(mClassHead.start());
			ends.add(mClassHead.end());
		}

		/**
		 * 找出所有成对的双引号的正则表达式
		 */
		Pattern p = Pattern.compile("\"([^\\\\\"]|\\\\.)*\"");
		Matcher m = p.matcher(fileText);
		char[] charArray = fileText.toCharArray();
		int length = charArray.length;
		while (m.find()) {
			int s = m.start();
			int e = m.end();
			for (int i = s; i < e; i++) {
				if (charArray[i] == '{' || charArray[i] == '}')
					charArray[i] = ' ';
			}
		}
		int startsNum = starts.size(), index = 0;
		int s = 0;
		int e = s;
		while (index < startsNum) {
			s = ends.get(index);
			if (s < e) {
				index++;
				continue;
			}
			e = s;
			int flag = 1;
			while (flag > 0 && e < length) {
				if (charArray[e] == '{')
					flag++;
				else if (charArray[e] == '}')
					flag--;
				e++;
			}
			classes.add(fileText.substring(starts.get(index), e));
			index++;
		}
		int size = classes.size();
		String[] results = new String[size];
		for (int i = 0; i < size; i++) {
			results[i] = classes.get(i);
		}
		return results;
	}

	protected String[] classArray;
	protected String[] functionArray;
}
