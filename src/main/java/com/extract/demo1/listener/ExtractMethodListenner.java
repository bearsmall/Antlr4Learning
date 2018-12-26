package com.extract.demo1.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class ExtractMethodListenner extends JavaParserBaseListener {

    private String code;
    private List<Token> tokenList;
    private List<String> functionContents = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public List<String> getFunctionContents() {
        return functionContents;
    }

    public void setFunctionContents(List<String> functionContents) {
        this.functionContents = functionContents;
    }

    public ExtractMethodListenner(String code, List<Token> tokenList) {
        this.code = code;
        this.tokenList = tokenList;
    }

    private String getPureContent(Token start,Token stop) {
        int starOff = start.getStartIndex();
        int stopOff = stop.getStopIndex();
        return code.substring(starOff, stopOff+1);
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
//        String method = getPureContent(ctx.getStart(), ctx.getStop());
        String content = getPureContent(ctx.getStart(),ctx.getStop());
        functionContents.add(content);
    }
}
