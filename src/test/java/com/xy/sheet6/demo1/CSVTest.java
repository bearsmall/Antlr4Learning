package com.xy.sheet6.demo1;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class CSVTest {
    public static void main(String[] args) throws IOException {
        CharStream inputStream = CharStreams.fromStream(CSVTest.class.getClassLoader().getResourceAsStream("data2.csv"));
        CSVLexer lexer = new CSVLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(tokenStream);
        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
    }
}
