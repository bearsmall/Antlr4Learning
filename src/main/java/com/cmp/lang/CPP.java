/**
 * 
 */
package com.cmp.lang;


import com.cmp.antlr.cpp.CPP14Lexer;
import com.cmp.antlr.cpp.CPP14Parser;
import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.LinkedList;
import java.util.List;

/**
 * CPP语言
 */
public class CPP implements Language {
	//Java的token，需要改成CPP的
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
	private static CPP cpp;

	/**
	 * 名称
	 */
	private String name = "CPP";
	
	/**
	 * @return 本类的一个实例
	 */
	public synchronized static CPP getInstance() {
		if (cpp == null) {
			cpp = new CPP();
		}
		return cpp;
	}
	
	/**
	 * 构造函数私有化
	 */
	private CPP() {
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
		return new int[1];
	}
	@Override
	public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile){
		CharStream inputStream = CharStreams.fromString(defaultCodeFile.getContent());
		CPP14Lexer lexer = new CPP14Lexer(inputStream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokenStream);
		ParseTree tree = parser.compoundstatement();
		int hashValue=0;
		int tempHashValue=0;
		int tempLineNum=0;
		String lineString = new String();
		List<LineStruct> linetokenlist = new LinkedList<>();
		for (Token token:tokenStream.getTokens()){
			int type = token.getType();
			if(token.getChannel()==CPP14Lexer.HIDDEN){
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
				lineString = " "+token.getText();
				tempLineNum = token.getLine();
			}
		}
		defaultCodeFile.setTokenLine(linetokenlist);
		try {
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
}
