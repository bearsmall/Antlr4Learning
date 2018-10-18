package com.xy.sheet7.demo1.main;

import com.xy.sheet7.demo1.PropertyFileLexer;
import com.xy.sheet7.demo1.PropertyFileMapper;
import com.xy.sheet7.demo1.PropertyFilePrinter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;

import java.io.IOException;

public class PropertyFileMapperTest {
    @Test
    public void test() throws IOException{
        CharStream inputStream = CharStreams.fromStream(PropertyFileMapperTest.class.getClassLoader().getResourceAsStream("t.properties"));
        PropertyFileLexer lexer = new PropertyFileLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        PropertyFileMapper parser = new PropertyFileMapper(tokenStream);
        parser.file();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】
        System.out.println(parser.getProperty("name")); //从map中取值
    }
}
