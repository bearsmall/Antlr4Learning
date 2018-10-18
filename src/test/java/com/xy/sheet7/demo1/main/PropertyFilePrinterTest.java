package com.xy.sheet7.demo1.main;

import com.xy.sheet7.demo1.PropertyFileLexer;
import com.xy.sheet7.demo1.PropertyFilePrinter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

public class PropertyFilePrinterTest {
    @Test
    public void test() throws IOException{
        CharStream inputStream = CharStreams.fromStream(PropertyFilePrinterTest.class.getClassLoader().getResourceAsStream("t.properties"));
        PropertyFileLexer lexer = new PropertyFileLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        PropertyFilePrinter parser = new PropertyFilePrinter(tokenStream);
        parser.file();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】
    }
}
