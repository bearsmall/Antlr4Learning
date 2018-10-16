package com.xy.sheet7.demo1.main;

import com.xy.sheet7.demo1.PropertyFileLexer;
import com.xy.sheet7.demo1.PropertyFileParser;
import com.xy.sheet7.demo1.visitor.PropertyFileVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class PropertyFileVisitorTest {

    public static void main(String[] args) throws IOException {
        CharStream inputStream = CharStreams.fromStream(PropertyFileMapperTest.class.getClassLoader().getResourceAsStream("t.properties"));
        PropertyFileLexer lexer = new PropertyFileLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        PropertyFileParser parser = new PropertyFileParser(tokenStream);
        ParseTree tree = parser.file();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】

        PropertyFileVisitor loader = new PropertyFileVisitor();
        loader.visit(tree);

        System.out.println(loader.getProps().get("name"));//从map中取值
    }
}