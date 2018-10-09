package com.xy.demo4;

import com.xy.demo2.ExprLexer;
import com.xy.demo2.ExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;

public class Calc {
    public static void main(String[] args) throws IOException {
        String inputFile = "t.expr";
        if(args.length>0){
            inputFile = args[0];
        }
        InputStream is = System.in;
        if(inputFile!=null){
            is = Calc.class.getClassLoader().getResourceAsStream(inputFile);
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