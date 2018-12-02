package com.xy.sheet4.demo4.listener;

import com.xy.sheet4.demo4.JavaBaseListener;
import com.xy.sheet4.demo4.JavaParser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class InsertSerialIDListener extends JavaBaseListener {
    TokenStreamRewriter rewriter;

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

    public InsertSerialIDListener(TokenStream tokenStream) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    @Override
    public void enterClassBody(JavaParser.ClassBodyContext ctx) {
        String field = "\n\tpublic static final long serialVersionID = 1L\n";
        rewriter.insertAfter(ctx.start,field);
    }
}
