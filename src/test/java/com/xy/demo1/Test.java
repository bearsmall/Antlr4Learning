package com.xy.demo1;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String content = in.nextLine();
        in.close();
        //新建一个CharSteam
        CharStream inputStream = CharStreams.fromString(content);
        //新建一个词法分析器，处理CharSteam
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //新建一个语法分析器，处理词法符号缓冲区中的内容
        ArrayInitParser parser = new ArrayInitParser(tokens);
        //针对init规则，开始语法分析
        ParseTree tree = parser.init();
        //用LISP风格打印生成的树
        System.out.println(tree.toStringTree(parser));
    }
}
