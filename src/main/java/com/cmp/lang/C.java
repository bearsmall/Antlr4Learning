package com.cmp.lang;

import com.cmp.antlr.c.CLexer;
import com.cmp.antlr.c.CParser;
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

	public static final int Newline=115;
	public static final int BlockComment=116;
	public static final int LineComment=117;
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
		return new int[1];
	}

	@Override
	public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile) {
		CharStream inputStream = CharStreams.fromString(defaultCodeFile.getContent());
		CLexer lexer = new CLexer(inputStream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokenStream);
		ParseTree tree = parser.compilationUnit();
		int hashValue=0;
		int tempHashValue=0;
		int tempLineNum=0;
		String lineString = new String();
		List<LineStruct> linetokenlist = new LinkedList<>();
		for (Token token:tokenStream.getTokens()){
			int type = token.getType();
			if(type==Newline||type==BlockComment||type==LineComment){
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
		try {
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
}
