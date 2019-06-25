package com.cmp.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import com.cmp.listener.replace.ReplaceConst;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ChangeToLambdaExtractListener extends JavaParserBaseListener {
    private char[] content;
    private List<Token> tokens;
    private String contentBefore;
    private boolean changed;

    public static AtomicLong lambdaCount = new AtomicLong(0);
    public static AtomicLong innerCount = new AtomicLong(0);

    public ChangeToLambdaExtractListener(String content, List<Token> tokens) {
        this.contentBefore = content;
        this.content = content.toCharArray();
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getContentBefore() {
        return contentBefore;
    }

    public void setContentBefore(String contentBefore) {
        this.contentBefore = contentBefore;
    }

    public char[] getContent() {
        return content;
    }

    public void setContent(char[] content) {
        this.content = content;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public void enterLambdaExpression(JavaParser.LambdaExpressionContext ctx) {
        lambdaCount.addAndGet(1);
        try {
            if(ctx.lambdaBody().block().blockStatement()!=null&&ctx.lambdaBody().block().blockStatement().size()==1){
                if(ctx.lambdaBody().block().children.size()==1){
                    return;
                }else {
                    int start = ctx.lambdaBody().block().start.getStartIndex();
                    int stop = ctx.lambdaBody().block().stop.getStopIndex();
                    for(int i=start;i<=stop;i++){
                        content[i] =  ReplaceConst.MARKABLE;
                    }
                    int point = start;
                    String blockContent = ctx.lambdaBody().block().blockStatement(0).statement().statementExpression.getText();
                    for(int i=0;i<blockContent.length();i++){
                        content[point++] = blockContent.charAt(i);
                    }
                    changed = true;
//                    String str = new String(content);
//                    log.info(str);
                }
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Override
    public void enterCreator(JavaParser.CreatorContext ctx) {
        StringBuilder params = new StringBuilder();
        try {
            if(ctx==null||ctx.classCreatorRest()==null||ctx.classCreatorRest().arguments()==null){
                log.info("not creator!");
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
                            log.info("following lambda params!");
                        }
                    }
                }
            }
            if(ctx==null||ctx.classCreatorRest()==null||ctx.classCreatorRest().classBody()==null){
                log.info("not lambda!");
                return;
            }
            //inner class params
            List<JavaParser.ClassBodyDeclarationContext> classBodyDeclarationContextList =  ctx.classCreatorRest().classBody().classBodyDeclaration();
            if(classBodyDeclarationContextList==null||classBodyDeclarationContextList.size()!=1){
                //inner class params，not lambda expression
                log.info("not lambda!");
            }else {
                //lambda expression
                JavaParser.MethodDeclarationContext methodDeclarationContext = classBodyDeclarationContextList.get(0).memberDeclaration().methodDeclaration();
                JavaParser.FormalParameterListContext formalParameterListContext = methodDeclarationContext.formalParameters().formalParameterList();
                if(formalParameterListContext!=null) {
                    List<JavaParser.FormalParameterContext> formalParameterContextList = formalParameterListContext.formalParameter();
                    if(formalParameterContextList!=null&&formalParameterContextList.size()>0){
                        //lambda expression params
                        for (JavaParser.FormalParameterContext fp : formalParameterContextList) {
//                            log.info(fp.toString());
                            params.append(fp.variableDeclaratorId());
                            params.append(",");
                        }
                    }
                }
                JavaParser.BlockContext blockContext = methodDeclarationContext.methodBody().block();
                String blockString = null;
                int blockStart = 0;
                int blockEnd = 0;
                if(blockContext.blockStatement()==null){
                    return;
                }else if(blockContext.blockStatement().size()==1){
                    if(blockContext.blockStatement(0).statement()==null){
                        blockStart = blockContext.blockStatement(0).start.getStartIndex();
                        blockEnd = blockContext.blockStatement(0).stop.getStopIndex();
                    }else {
                        blockStart = blockContext.blockStatement(0).statement().statementExpression.start.getStartIndex();
                        blockEnd = blockContext.blockStatement(0).statement().statementExpression.stop.getStopIndex();
                    }
                }else {
                    blockStart = blockContext.start.getStartIndex();
                    blockEnd = blockContext.stop.getStopIndex();
                }
                blockString = contentBefore.substring(blockStart,blockEnd);
                int start = ctx.getParent().start.getStartIndex();
                int stop = ctx.getParent().stop.getStopIndex();
                for(int i=start;i<=stop;i++){
                    content[i] =  ReplaceConst.MARKABLE;
                }
                int point = start;
                content[point++] = '(';
                if(!params.equals("")){
                    for(int i=0;i<params.length()-1;i++){
                        content[point++] = params.charAt(i);
                    }
                }
                content[point++] = ')';
                content[point++] = '-';
                content[point++] = '>';
                for(int i=0;i<blockString.length();i++){
                    if(blockString.charAt(i)=='\n'){
                        log.info("return!");
                    }
                    content[point++] = blockString.charAt(i);
                }
                changed = true;
                innerCount.addAndGet(1);
//                String str = new String(content);
//                log.info(str);
//
//                StringBuilder sb = new StringBuilder();
//                sb.append(content.substring(0,blockContext.start.getStartIndex()));
//                sb.append("(");
//                if(!params.equals("")){
//                    sb.append(params.substring(0,params.length()-1));
//                }
//                sb.append(")->");
//                sb.append(content.substring(blockContext.stop.getStopIndex()));
//
//                log.info(blockContext.getText());
//                log.info(sb);
            }
        }catch (NullPointerException e){
            log.info(e.getMessage());
        }
    }
}
