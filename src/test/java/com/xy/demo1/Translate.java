package com.xy.demo1;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Scanner;

public class Translate {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String content = in.nextLine();
        in.close();
        ANTLRInputStream inputStream = new ANTLRInputStream(content);
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArrayInitParser parser = new ArrayInitParser(tokens);
        ParseTree tree = parser.init();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ShortToUnicodeString(),tree);
        System.out.println();
    }
}
