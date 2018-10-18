package com.xy.demo000;

import com.xy.demo00.ExtractInterfaceTool;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Col {
    @Test
    public void test() throws IOException{
        String inputFile = "abc.idea";
        InputStream is = ExtractInterfaceTool.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        RowsLexer lexer = new RowsLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        int col = 1;
        RowsParser parser = new RowsParser(tokenStream,col);//传递列号作为参数
        parser.setBuildParseTree(false);//不需要浪费时间建立语法分析树
        parser.file();//开始语法分析
    }
}
