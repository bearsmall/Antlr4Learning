package com.xy.sheet4.demo4;

import com.xy.sheet4.demo4.listener.InsertSerialIDListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class InsertSerialID {
    @Test
    public void test() throws IOException {
        String inputFile = "InsertSerialIDListenerTest.java";
        InputStream is = ExtractInterfaceTool.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();//开始语法分析的过程

        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
        InsertSerialIDListener extractor = new InsertSerialIDListener(tokenStream);
        walker.walk(extractor,tree);

        //打印修改后的词法符号流
        System.out.println(extractor.getRewriter().getText());
    }
}
