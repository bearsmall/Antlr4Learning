package com.method.demo1.listener;

import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodExtractorListenner extends JavaParserBaseListener {

    private List<ModuleEntity> moduleEntityList = new ArrayList<>();
    private List<Token> formalParamInterval = new ArrayList<>();
    private List<Token> localParamInterval = new ArrayList<>();
    private List<Token> dataTypeInterval = new ArrayList<>();
    private List<Token> methodCallInterval = new ArrayList<>();

    private List<Token> paramInterval = new ArrayList<>();

    private List<Token> tokenList;
    private String content;

    public List<ModuleEntity> getModuleEntityList() {
        return moduleEntityList;
    }

    public void setModuleEntityList(List<ModuleEntity> moduleEntityList) {
        this.moduleEntityList = moduleEntityList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Token> getFormalParamInterval() {
        return formalParamInterval;
    }

    public void setFormalParamInterval(List<Token> formalParamInterval) {
        this.formalParamInterval = formalParamInterval;
    }

    public List<Token> getLocalParamInterval() {
        return localParamInterval;
    }

    public void setLocalParamInterval(List<Token> localParamInterval) {
        this.localParamInterval = localParamInterval;
    }

    public List<Token> getDataTypeInterval() {
        return dataTypeInterval;
    }

    public void setDataTypeInterval(List<Token> dataTypeInterval) {
        this.dataTypeInterval = dataTypeInterval;
    }

    public List<Token> getMethodCallInterval() {
        return methodCallInterval;
    }

    public void setMethodCallInterval(List<Token> methodCallInterval) {
        this.methodCallInterval = methodCallInterval;
    }

    public List<Token> getParamInterval() {
        return paramInterval;
    }

    public void setParamInterval(List<Token> paramInterval) {
        this.paramInterval = paramInterval;
    }

    public MethodExtractorListenner(String content, List<Token> tokenList) {
        this.content = content;
        this.tokenList = tokenList;
    }

    private ModuleEntity extractModuleEntity(Token start,Token stop) {
        int charStart = start.getStartIndex();
        int charEnd = stop.getStartIndex();
        int tokenStart = start.getTokenIndex();
        int tokenEnd = stop.getTokenIndex();
        return new ModuleEntity(charStart,charEnd,tokenStart,tokenEnd,content.substring(charStart,charEnd+1),tokenList.subList(tokenStart,tokenEnd));
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
//        String method = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        moduleEntityList.add(moduleEntity);
    }



    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        ParseTree parseTree = ctx.getChild(1);
        String param = parseTree.getText();
        Interval interval = parseTree.getSourceInterval();
        System.out.println("enterFormalParameter-->"+param);
        formalParamInterval.add(tokenList.get(interval.a));
    }

    @Override
    public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        ParseTree parseTree = ctx.getChild(1);
        for(int i=0;i<parseTree.getChildCount();i++){
            ParseTree children = parseTree.getChild(i).getChild(0);
            String param = children.getText();
            Interval interval = children.getSourceInterval();
            System.out.println("enterLocalVariableDeclaration-->"+param);
            localParamInterval.add(tokenList.get(interval.a));
        }
    }

    @Override
    public void enterClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx) {
        String param = ctx.getText();
        Interval interval = ctx.getSourceInterval();
        System.out.println("enterClassOrInterfaceType-->"+param);
        dataTypeInterval.add(tokenList.get(interval.a));
    }

    @Override
    public void enterPrimitiveType(JavaParser.PrimitiveTypeContext ctx) {
        String param = ctx.getText();
        Interval interval = ctx.getSourceInterval();
        System.out.println("enterPrimitiveType-->"+param);
        dataTypeInterval.add(tokenList.get(interval.a));
    }

    @Override
    public void enterMethodCall(JavaParser.MethodCallContext ctx) {
        ParseTree parseTree = ctx.getChild(0);
        String param = parseTree.getText();
        Interval interval = parseTree.getSourceInterval();
        System.out.println("enterMethodCall-->"+param);
        methodCallInterval.add(tokenList.get(interval.a));
    }

    @Override
    public void enterPrimary(JavaParser.PrimaryContext ctx) {
        if(ctx.getChild(0).getChildCount()>0){
            return;
        }
        String param = ctx.getText();
        Interval interval = ctx.getSourceInterval();
        System.out.println("enterPrimary-->"+param);
        paramInterval.add(tokenList.get(interval.a));
    }
}
