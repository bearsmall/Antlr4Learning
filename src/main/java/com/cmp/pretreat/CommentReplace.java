package com.cmp.pretreat;



import com.cmp.lang.Language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Java实现将源文件中的注释替换成空行。
 * 
 */
public class CommentReplace extends Pretreator {
	
//	public static void main(String[] args) throws Exception{
//
//		
//		String path1 = "d:/testsrc/2.java";
//		CodeFileFactory factory = JavaFileFactory.getInstance();
//		CodeFile cf = factory.createCodeFile(new File(path1));
//		String content1 =cf.getText();
//		System.out.println("content1: \n" + content1);
//		System.out.println(getLineNum(content1));
//		String content2 = replace(content1);
//		System.out.println("content2: \n" + content2);
//		System.out.println(getLineNum(content2));
//	}
	/**
	 * 全局唯一实例
	 */
	private static CommentReplace crp = null;
	
	/**
	 * 私有构造函数
	 */
	private CommentReplace() {
	}
	
	/**
	 * @return 一个本类的实例
	 */
	public static synchronized CommentReplace getInstance() {
		if(crp == null) {
			crp = new CommentReplace();
		}
		return crp;
	}
	
	public String replace(String content) {
		String pattern= "('[^'\\\\]*(?:\\\\(?s).[^'\\\\]*)*'[^\"'/@]*)|/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/|//[^\n]*|@\\S*";
		pattern = "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/|(?s)/\\*.*?\\*/|//[^\n]*|/\\*([\\S\\s]+?)\\*/";
//		System.out.println(pattern);
		Pattern p = Pattern.compile(pattern);
		/**
		 * 正则表达式仓库
		 */
//		RegexStorage res = RegexStorage.getInstance();
//		Pattern p = res.getJavaCommentPattern();
		Matcher m=p.matcher(content);
		
		StringBuffer sb=new StringBuffer();
		try {
			while (m.find()) {
				String comment = m.group();

				int linenum = getLineNum(comment);
				String replacement = createBlankLines(linenum);

				m.appendReplacement(sb, replacement);
			}
		}catch (Error error){
			try {
				while (m.find()) {
					String comment = m.group();

					int linenum = getLineNum(comment);
					String replacement = createBlankLines(linenum);

					m.appendReplacement(sb, replacement);
				}
			}catch (Error error2){
				while (m.find()) {
					String comment = m.group();

					int linenum = getLineNum(comment);
					String replacement = createBlankLines(linenum);

					m.appendReplacement(sb, replacement);
				}
			}
		}
		m.appendTail(sb);
		
		return sb.toString();
	}
	//获取注释行数量
	private int getLineNum(String content) {
//		String[] splitted = content.split(System.getProperty("line.separator"));
		String[] splitted=content.split("\n",-1);
		return splitted.length;
	}
	//创建与注释行数量相同的空白行
	private String createBlankLines(int lineNums) {
//		 String lineSep = System.getProperty("line.separator");
		String lineSep="\n";
		 
		 StringBuilder result = new StringBuilder();
		 for(int i=0;i<lineNums -1 ;i++) {
//			 result.append(lineSep + " ");
			 result.append(lineSep);
		 }
		 
		 return result.toString();
	}
	@Override
	protected String excute(String codeText, Language lang) {
		
		// TODO Auto-generated method stub
		return replace(codeText);
	}

}
