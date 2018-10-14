package com.xy.sheet7.demo1.main;

import com.xy.sheet7.demo1.PropertyFileLexer;
import com.xy.sheet7.demo1.PropertyFileParser;
import com.xy.sheet7.demo1.listener.PropertyFileLoader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class PropertyFileListenerTest {
    public static void main(String[] args) throws IOException {
        CharStream inputStream = CharStreams.fromStream(PropertyFileMapperTest.class.getClassLoader().getResourceAsStream("t.properties"));
        PropertyFileLexer lexer = new PropertyFileLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        PropertyFileParser parser = new PropertyFileParser(tokenStream);
        ParseTree tree = parser.file();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】

        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的ANTLR语法分析树遍历器
        PropertyFileLoader loader = new PropertyFileLoader();//新建一个监听器，将其传递给遍历器
        walker.walk(loader,tree);   //遍历语法分析器

        System.out.println(loader.getProps().get("name"));//从map中取值
    }
}