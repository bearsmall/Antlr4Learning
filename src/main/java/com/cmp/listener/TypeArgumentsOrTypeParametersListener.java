package com.cmp.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import com.cmp.listener.replace.ReplaceConst;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TypeArgumentsOrTypeParametersListener extends JavaParserBaseListener {
    public static AtomicLong typeArgumentsCount = new AtomicLong(0);
    public static AtomicLong typeArgumentsOrDiamondCount = new AtomicLong(0);
    public static AtomicLong typeParameters = new AtomicLong(0);
    public static AtomicLong nonWildcardTypeArgumentsOrDiamond = new AtomicLong(0);
    public static AtomicLong nonWildcardTypeArguments = new AtomicLong(0);

    private char[] content;
    private List<Token> tokens;
    private String contentBefore;
    private boolean changed;

    public char[] getContent() {
        return content;
    }

    public void setContent(char[] content) {
        this.content = content;
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

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public TypeArgumentsOrTypeParametersListener(String content, List<Token> tokens) {
        this.contentBefore = content;
        this.content = content.toCharArray();
        this.tokens = tokens;
    }

    @Override
    public void enterTypeArguments(JavaParser.TypeArgumentsContext ctx) {
        typeArgumentsCount.addAndGet(1);
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if (start<stop){
            for(int i=start;i<=stop;i++){
                content[i] =  ReplaceConst.MARKABLE;
            }
            changed = true;
        }
        super.enterTypeArguments(ctx);
    }

    @Override
    public void enterTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx) {
        typeArgumentsOrDiamondCount.addAndGet(1);
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if (start<stop){
            for(int i=start;i<=stop;i++){
                content[i] =  ReplaceConst.MARKABLE;
            }
            changed = true;
        }
        super.enterTypeArgumentsOrDiamond(ctx);
    }

    @Override
    public void enterTypeParameters(JavaParser.TypeParametersContext ctx) {
        typeParameters.addAndGet(1);
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if (start<stop){
            for(int i=start;i<=stop;i++){
                content[i] = ReplaceConst.MARKABLE;
            }
            changed = true;
        }
        super.enterTypeParameters(ctx);
    }

    @Override
    public void enterNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx) {
        nonWildcardTypeArgumentsOrDiamond.getAndAdd(1);
        super.enterNonWildcardTypeArgumentsOrDiamond(ctx);
    }

    @Override
    public void enterNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx) {
        nonWildcardTypeArguments.getAndAdd(1);
        super.enterNonWildcardTypeArguments(ctx);
    }
}
