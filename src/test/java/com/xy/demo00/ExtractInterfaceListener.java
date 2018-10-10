package com.xy.demo00;

import org.antlr.v4.runtime.TokenStream;

public class ExtractInterfaceListener extends JavaBaseListener {
    JavaParser javaParser;

    public ExtractInterfaceListener(JavaParser javaParser) {
        this.javaParser = javaParser;
    }

    /**
     * 监听对类定义的配置
     * @param ctx
     */
    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("interface I"+ctx.Identifier()+" {");
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }

    /**
     * 监听对方法定义的匹配
     * @param ctx
     */
    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        //需要从语法分析器中获取词法符号
        TokenStream tokenStream = javaParser.getTokenStream();
        String type = "void";
        if(ctx.type()!=null){
            type = tokenStream.getText(ctx.type());
        }
        String args = tokenStream.getText(ctx.formalParameters());
        System.out.println("\t"+type+" "+ ctx.Identifier()+args+":");
    }

    /**
     * 监听 import
     * @param ctx
     */
    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        if(ctx.getChildCount()==3) {
            System.out.println("import " + ctx.qualifiedName().getText() + ";\n");
        }else {
            System.out.println("import static " + ctx.qualifiedName().getText() + ";\n");
        }
    }
}
