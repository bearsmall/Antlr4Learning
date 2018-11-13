/*
 * 文件名:		Java.java
 * 接口名：		Java
 * 创建日期：	2013-08-08
 * 最近修改：	2013-08-08
 * 作者：		lbl
 */
package com.cmp.lang;



import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.listener.MethodExtractorListenner;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.LinkedList;
import java.util.List;

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
		return new int[1];
	}

	@Override
	public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile){
		CharStream inputStream = CharStreams.fromString(defaultCodeFile.getContent());
		JavaLexer lexer = new JavaLexer(inputStream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		JavaParser parser = new JavaParser(tokenStream);
		ParseTree tree = parser.compilationUnit();

		int hashValue=0;
		int tempHashValue=0;
		int tempLineNum=1;
		String lineString = new String();
		List<LineStruct> linetokenlist = new LinkedList<>();
		for (Token token:tokenStream.getTokens()){
			int type = token.getType();
			if(token.getChannel()==JavaLexer.HIDDEN){
				continue;
			}
			if (token.getLine()==tempLineNum){
				lineString = lineString+" "+type;
			}else {
				hashValue = lineString.hashCode();
				if(hashValue!=tempHashValue){
					LineStruct lineStruct=new LineStruct();
					lineStruct.setLineNum(tempLineNum);
					lineStruct.setHashValue(hashValue);
					linetokenlist.add(lineStruct);
					tempHashValue=hashValue;
				}
				lineString = " "+token.getText();;
				tempLineNum = token.getLine();
			}
		}
		defaultCodeFile.setTokenLine(linetokenlist);
		ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
		MethodExtractorListenner extractor = new MethodExtractorListenner(defaultCodeFile,tokenStream.getTokens());
		walker.walk(extractor,tree);

		try {
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

}
