package com.xy.demo2;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExprJoyRide {
    public static void main(String[] args) throws IOException {
        String inputFile = null;
        if(args.length>0){
            inputFile = args[0];
        }
        InputStream is;
        if(inputFile==null){
            inputFile = "t.expr";
        }
        is = ExprJoyRide.class.getClassLoader().getResourceAsStream(inputFile);
        CharStream inputStream = CharStreams.fromStream(is);
        ExprLexer lexer = new ExprLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();
        System.out.println(tree.toStringTree(parser));
    }
}
