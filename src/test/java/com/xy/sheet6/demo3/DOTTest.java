package com.xy.sheet6.demo3;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;

public class DOTTest {
    @Test
    public void test() throws IOException{
        CharStream inputStream = CharStreams.fromStream(DOTTest.class.getClassLoader().getResourceAsStream("t.dot"));
        DOTLexer lexer = new DOTLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        DOTParser parser = new DOTParser(tokenStream);
        ParseTree tree = parser.graph();
        System.out.println(tree.toStringTree(parser));
    }
}
