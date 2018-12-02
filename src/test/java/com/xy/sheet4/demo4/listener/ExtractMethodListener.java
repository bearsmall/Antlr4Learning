package com.xy.sheet4.demo4.listener;

import com.xy.sheet4.demo4.JavaBaseListener;
import com.xy.sheet4.demo4.JavaParser;

public class ExtractMethodListener extends JavaBaseListener {
    JavaParser javaParser;

    public ExtractMethodListener(JavaParser javaParser) {
        this.javaParser = javaParser;
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        System.out.println(ctx);
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        System.out.println(ctx);
    }
}
