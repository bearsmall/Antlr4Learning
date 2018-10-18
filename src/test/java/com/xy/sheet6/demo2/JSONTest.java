package com.xy.sheet6.demo2;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;

public class JSONTest {
    @Test
    public void test() throws IOException{
        CharStream inputStream = CharStreams.fromStream(JSONTest.class.getClassLoader().getResourceAsStream("t.json"));
        JSONLexer lexer = new JSONLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokenStream);
        ParseTree tree = parser.json();
        System.out.println(tree.toStringTree(parser));
    }
}
