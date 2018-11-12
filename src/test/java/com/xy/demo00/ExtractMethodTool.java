package com.xy.demo00;

import com.xy.demo00.listener.ExtractMethodListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExtractMethodTool {

    @Test
    public void test() throws IOException {
        String inputFile = "ExtractInterfaceListenerTest.java";
        InputStream is = ExtractMethodTool.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();//开始语法分析的过程

        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
        ExtractMethodListener extractor = new ExtractMethodListener(parser);
        walker.walk(extractor,tree);
    }

    @Test
    public void test2() throws IOException {
        String inputFile = "ExtractInterfaceListenerTest.java";
        InputStream is = ExtractMethodTool.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();//开始语法分析的过程
        List<Token> tokenList = tokenStream.getTokens();
        for(Token token:tokenList){
            if(token.getChannel()==2){
                System.out.println(token);
            }
        }
    }
}
