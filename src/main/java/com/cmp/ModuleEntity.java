package com.cmp;

import org.antlr.v4.runtime.Token;

import java.util.*;

public class ModuleEntity {
    private int charStart; //字符开始
    private int charEnd;  //字符结束

    private int tokenStart; //token开始
    private int tokenEnd;   //token结束

    private int lineBegin;  //开始行
    private int lineEnd;    //结束行

    private String content;  //文本内容
    private List<Token> tokenList = new LinkedList<>(); //token列表

    private Set<Token> declaredParams;  //函数定义的参数
    private Set<Token> unUsedParams;    //未使用的参数

    public ModuleEntity(int charStart, int charEnd, int tokenStart, int tokenEnd, int lineBegin, int lineEnd, String content, List<Token> tokenList) {
        this.charStart = charStart;
        this.charEnd = charEnd;
        this.tokenStart = tokenStart;
        this.tokenEnd = tokenEnd;
        this.lineBegin = lineBegin;
        this.lineEnd = lineEnd;
        this.content = content;
        this.tokenList = tokenList;
    }

    public int getCharStart() {
        return charStart;
    }

    public void setCharStart(int charStart) {
        this.charStart = charStart;
    }

    public int getCharEnd() {
        return charEnd;
    }

    public void setCharEnd(int charEnd) {
        this.charEnd = charEnd;
    }

    public int getTokenStart() {
        return tokenStart;
    }

    public void setTokenStart(int tokenStart) {
        this.tokenStart = tokenStart;
    }

    public int getTokenEnd() {
        return tokenEnd;
    }

    public void setTokenEnd(int tokenEnd) {
        this.tokenEnd = tokenEnd;
    }

    public int getLineBegin() {
        return lineBegin;
    }

    public void setLineBegin(int lineBegin) {
        this.lineBegin = lineBegin;
    }

    public int getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public Set<Token> getDeclaredParams() {
        return declaredParams;
    }

    public void setDeclaredParams(Set<Token> declaredParams) {
        this.declaredParams = declaredParams;
    }

    public Set<Token> getUnUsedParams() {
        return unUsedParams;
    }

    public void setUnUsedParams(Set<Token> unUsedParams) {
        this.unUsedParams = unUsedParams;
    }

    //获取函数实际参数个数
    public int getRealParamNumber(){
        if(declaredParams==null||declaredParams.isEmpty()){
            return 0;
        }
        if(unUsedParams==null||unUsedParams.isEmpty()){
            return declaredParams.size();
        }else {
            return declaredParams.size()-unUsedParams.size();
        }
    }
}
