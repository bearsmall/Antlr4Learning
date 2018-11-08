package com.xy.demo00;

import com.xy.demo00.listener.ExtractInterfaceListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class LexicalParser {

    @Test
    public void test() throws IOException {
        String inputFile = "JavaParserTest.java";
        InputStream is = LexicalParser.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
//        parser.setBuildParseTree(false);
        Long start = System.currentTimeMillis();
        ParseTree tree = parser.compilationUnit();
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
        for (Token token : tokenStream.getTokens()) {
//            System.out.println(token);
        }
//        System.out.println(tree);
    }
}
