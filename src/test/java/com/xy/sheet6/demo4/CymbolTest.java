package com.xy.sheet6.demo4;

import com.xy.sheet6.demo3.DOTLexer;
import com.xy.sheet6.demo3.DOTParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;

public class CymbolTest {

    public void test() throws IOException{
        CharStream inputStream = CharStreams.fromStream(CymbolTest.class.getClassLoader().getResourceAsStream("t.cymbol"));
        CymbolLexer lexer = new CymbolLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CymbolParser parser = new CymbolParser(tokenStream);
        ParseTree tree = parser.block();
        System.out.println(tree.toStringTree(parser));
    }
}
