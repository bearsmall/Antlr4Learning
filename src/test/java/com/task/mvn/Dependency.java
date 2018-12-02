package com.task.mvn;

import com.xy.sheet4.demo4.ExtractInterfaceTool;
import com.xy.sheet4.demo5.RowsLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Dependency {

    @Test
    public void test1() throws IOException {
        String inputFile = "result.txt";
        InputStream is = Dependency.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        DependencyLexer lexer = new DependencyLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        int col = 1;
        DependencyParser parser = new DependencyParser(tokenStream);//传递列号作为参数
        ParseTree tree = parser.depend();//开始语法分析

        MyDependencyVisitor visitor = new MyDependencyVisitor();
        visitor.visit(tree);
        System.out.println(tree);
    }
}
