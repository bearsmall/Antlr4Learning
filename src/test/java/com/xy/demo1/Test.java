package com.xy.demo1;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String content = in.nextLine();
        in.close();
        ANTLRInputStream inputStream = new ANTLRInputStream(content);
        ArrayInitLexer lexer = new ArrayInitLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArrayInitParser parser = new ArrayInitParser(tokens);
        ParseTree tree = parser.init();
        System.out.println(tree.toStringTree(parser));
    }
}
