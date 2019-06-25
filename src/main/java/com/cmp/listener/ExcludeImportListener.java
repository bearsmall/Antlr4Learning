package com.cmp.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import com.cmp.listener.replace.ReplaceConst;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ExcludeImportListener extends JavaParserBaseListener {
    public static AtomicLong importCount = new AtomicLong(0);

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

    public ExcludeImportListener(String content, List<Token> tokens) {
        this.contentBefore = content;
        this.content = content.toCharArray();
        this.tokens = tokens;
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        importCount.addAndGet(1);
        int start = ctx.getStart().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if (start<stop){
            for(int i=start;i<=stop;i++){
                content[i] =  ReplaceConst.MARKABLE;
            }
            changed = true;
        }
        super.enterImportDeclaration(ctx);
    }
}
