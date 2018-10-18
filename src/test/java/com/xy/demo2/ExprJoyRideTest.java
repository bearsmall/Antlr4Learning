package com.xy.demo2;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ExprJoyRideTest {
    @Test
    public void test() throws IOException{
        String inputFile = null;
        InputStream is;
        if(inputFile==null){
            inputFile = "t.expr";
        }
        is = ExprJoyRideTest.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        ExprLexer lexer = new ExprLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();
        System.out.println(tree.toStringTree(parser));
    }
}
