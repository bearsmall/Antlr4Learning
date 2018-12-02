package com.xy.sheet4.demo3;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CalcTest {
    @Test
    public void test() throws IOException{
        String inputFile = "t.expr";
        InputStream is = System.in;
        if(inputFile!=null){
            is = CalcTest.class.getClassLoader().getResourceAsStream(inputFile);
        }
        ANTLRInputStream inputStream = new ANTLRInputStream(is);
        LabeledExprLexer lexer = new LabeledExprLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog();

        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}