/*
 * 类名：		SpecialCharactersSearch
 * 创建日期：	2009/10/15
 * 最近修改：	2011/03/14
 * 作者：		徐犇
 */
package com.bupt.cmp.pretreat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 特征字符搜索类,运用搜索引擎(正则表达式)的一些原理,在文本级别搜索源代码文件中的特征字符
 * @author ben
 */
public class SpecialCharactersSearch {

	/**
	 * 通过代码(先要去掉引号内容)中的分隔符将代码分隔 成字符串序列
	 * 
	 * @param code
	 * @return codeList
	 */
	public static String[] splitCodeBySeparator(String code) {
		String[] codeList = code
				.split("[\\s\\[\\]\\(\\)\\{\\}\\,\\;\\<\\>\\%\\:]+");
		return codeList;
	}

	/**
	 * 通过关键字编号序列得到关键字字符串序列
	 * @deprecated
	 * @param codeSequence
	 * @param codeType
	 * @return 关键字字符串序列
	 */
	public static String[] getKeyWordSequence(int[] codeSequence, int codeType) {
//		String[] keyWords = KeyWord.getKeyWordsOfALanguage(codeType);
//
//		int len = codeSequence.length;
//		int keyWordLength = keyWords.length;
//		String[] kws = new String[len];
//		for (int i = 0; i < len; i++) {
//			int temp = codeSequence[i];
//			if (temp >= 0 && temp < keyWordLength) {
//				kws[i] = keyWords[temp];
//			}
//		}
//		return kws;
		return null;
	}

	/**
	 * 为了去除在进行文本分析时引号内容对关键字提取等的干扰, 使用正则表达式将引号内容用特定的字符进行替换
	 * 
	 * @param codes
	 *            待处理的代码文本字符串
	 * @param rc
	 *            将引号字符串的字符替换成该字符
	 * @return 替换完成后的字符串
	 */
	public static String replaceQuotation(String codes, char rc) {

		/**
		 * 匹配单引号字符和双引号字符串的正则表达式
		 */
		Pattern p = Pattern.compile("[^\"']+|'[^'\\\\]*(?:\\\\.[^'\\\\]*)*'|\"[^\\\\\"]*(?:\\\\.[^\\\\\"]*)*\"");

		char[] charArray = codes.toCharArray();
		Matcher m = p.matcher(codes);
		while (m.find()) {
			if (m.group().charAt(0) == '\'' || m.group().charAt(0) == '\"') {
				int s = m.start();
				int e = m.end();
				for (int i = s; i < e; i++) {
					charArray[i] = rc;
				}
			}
		}

		return charArray.toString();
	}


	/**
	 * 将C语言代码按模块切分，即提取出其中的每个函数做为一个模块，提完函数的剩余文件内容也作为一个模块
	 * @deprecated
	 * @param cfile
	 * @return 模块序列
	 */
	public static String[] splitCFileToBlocks(String cfile) {
		/**
		 * 匹配函数头的正则表达式
		 */
		String regex = new String(
				"([a-zA-Z_][\\w]*[\\s\\*]+)+([a-zA-Z_][\\w]*[\\s]*)\\([^\\(\\)\\{\\}]*\\)[\\s]*");

		/**
		 * 层数设为10表示能匹配的代码中括号嵌套最多10层
		 */
		regex += createExpRecursive("\\{[^{}]*(", "\\{[^{}]*\\}",
				"[^{}]*)*\\}", 10);

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(cfile);

		/**
		 * 提取完函数后剩余的内容
		 */
		String remain = m.replaceAll("");

		/**
		 * 暂时存放提取出的函数块
		 */
		ArrayList<String> blocks = new ArrayList<String>();

		m = p.matcher(cfile);
		while (m.find()) {
			blocks.add(m.group());
		}

		int len = blocks.size();

		if (remain == null || remain.trim().equals("")) {
			String[] aBlocks = new String[len];
			for (int i = 0; i < len; i++) {
				aBlocks[i] = blocks.get(i);
			}
			return aBlocks;
		}

		String[] aBlocks = new String[len + 1];
		aBlocks[0] = remain;
		for (int i = 0; i < len; i++) {
			aBlocks[i + 1] = blocks.get(i);
		}

		return aBlocks;
	}

	/**
	 * 递归生成正则表达式以用于匹配嵌套括号结构，私有函数
	 * 
	 * @param start
	 * @param mid
	 * @param end
	 * @param depth
	 * @return 生成的正则表达式
	 */
	private static String createExpRecursive(String start, String mid,
			String end, int depth) {
		if (depth <= 0) {// 调用出现错误
			return null;
		} else if (depth == 1) {
			return start + mid + end;
		} else {
			return start + createExpRecursive(start, mid, end, depth - 1) + end;

		}
	}

	/**
	 * 将Java代码文件中的类取出并存放到字符数组中
	 * @deprecated
	 * @param javaFile
	 * @return 保存着该代码文件中的类的字符数组
	 */
	public static String[] getClasses(String javaFile) {

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
		Matcher mClassHead = pClassHead.matcher(javaFile);
		while (mClassHead.find()) {
			starts.add(mClassHead.start());
			ends.add(mClassHead.end());
		}

		/**
		 * 找出所有成对的双引号的正则表达式
		 */
		Pattern p = Pattern.compile("\"([^\\\\\"]|\\\\.)*\"");
		Matcher m = p.matcher(javaFile);
		char[] charArray = javaFile.toCharArray();
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
			classes.add(javaFile.substring(starts.get(index), e));
			index++;
		}
		int size = classes.size();
		String[] results = new String[size];
		for (int i = 0; i < size; i++) {
			results[i] = classes.get(i);
		}
		return results;
	}
	

	/**
	 * 将给定的一个java类的代码文本，分析出其中的函数 ，提取出来并存放到字符数组里
	 * @deprecated
	 * @param classText
	 * @return 存放提取出的函数的字符数组
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
}
