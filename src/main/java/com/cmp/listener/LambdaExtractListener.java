package com.cmp.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class LambdaExtractListener extends JavaParserBaseListener {
    public static AtomicLong lambdaCount = new AtomicLong(0);
    public static AtomicLong innerCount = new AtomicLong(0);

    @Override
    public void enterLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
        lambdaCount.addAndGet(1);
        System.out.println(ctx.lambdaBody().getText());
    }

    @Override
    public void enterCreator(JavaParser.CreatorContext ctx) {
        try {
            if(ctx==null||ctx.classCreatorRest()==null||ctx.classCreatorRest().arguments()==null){
                System.out.println("not creator!");
                return;
            }
            JavaParser.ArgumentsContext argumentsContext = ctx.classCreatorRest().arguments();
            if(argumentsContext.expressionList()!=null) {
                List<JavaParser.ExpressionContext> expressionContextList = argumentsContext.expressionList().expression();
                for (JavaParser.ExpressionContext ex : expressionContextList) {
                    if (ex.lambdaExpression() != null) {
                        return;
                    }else {
                        if(ctx.classCreatorRest().classBody()==null){
                            //normal params
                            return;
                        }else {
                            System.out.println("following lambda params!");
                        }
                    }
                }
            }
            if(ctx==null||ctx.classCreatorRest()==null||ctx.classCreatorRest().classBody()==null){
                System.out.println("not lambda!");
                return;
            }
            //inner class params
            List<JavaParser.ClassBodyDeclarationContext> classBodyDeclarationContextList =  ctx.classCreatorRest().classBody().classBodyDeclaration();
            if(classBodyDeclarationContextList==null||classBodyDeclarationContextList.size()!=1){
                //inner class params，not lambda expression
                System.out.println("not lambda!");
            }else {
                //lambda expression
                JavaParser.MethodDeclarationContext methodDeclarationContext = classBodyDeclarationContextList.get(0).memberDeclaration().methodDeclaration();
                JavaParser.FormalParameterListContext formalParameterListContext = methodDeclarationContext.formalParameters().formalParameterList();
                if(formalParameterListContext!=null) {
                    List<JavaParser.FormalParameterContext> formalParameterContextList = formalParameterListContext.formalParameter();
                    if(formalParameterContextList!=null&&formalParameterContextList.size()>0){
                        //lambda expression params
                        for (JavaParser.FormalParameterContext fp : formalParameterContextList) {
                            System.out.println(fp);
                        }
                    }
                }
                JavaParser.BlockContext blockContext = methodDeclarationContext.methodBody().block();
//            blockContext.start.getTokenIndex();
//            blockContext.stop.getTokenIndex();
//            new CommonToken(JavaLexer.LPAREN);
//            new CommonToken(JavaLexer.COMMA);
//            new CommonToken(JavaLexer.RPAREN);
                innerCount.addAndGet(1);
                System.out.println(blockContext.getText());
            }
        }catch (NullPointerException e){
            System.out.println(ctx.getText());
        }
    }
}
