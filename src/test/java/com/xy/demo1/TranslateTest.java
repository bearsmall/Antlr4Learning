package com.xy.demo1;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

public class TranslateTest {
    @Test
    public void test() {
        Scanner in = new Scanner(System.in);
        String content = "{1,2,3}\n";//in.nextLine();
        in.close();
        //新建一个CharSteam
        CharStream inputStream = CharStreams.fromString(content);
        //新建一个词法分析器，处理输入的CharSteam
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        //新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //新建一个语法分析器，处理词法符号缓冲区中的内容
        ArrayInitParser parser = new ArrayInitParser(tokens);
        //针对init规则，开始语法分析
        ParseTree tree = parser.init();

        //新建一个通用的、能够触发回调函数的语法分析树遍历器
        ParseTreeWalker walker = new ParseTreeWalker();
        //遍历语法分析过程中生成的语法分析器，触发回调
        walker.walk(new ShortToUnicodeString(),tree);
        System.out.println();
    }
}
