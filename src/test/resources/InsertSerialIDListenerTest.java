package com.xy.demo00.listener;

import com.xy.demo00.JavaBaseListener;
import com.xy.demo00.JavaParser;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class InsertSerialIDListenerTest extends JavaBaseListener {
    TokenStreamRewriter rewriter;

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

    public InsertSerialIDListenerTest(TokenStream tokenStream) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    @Override
    public void enterClassBody(JavaParser.ClassBodyContext ctx) {
        String field = "\n\t public static final long serialVersionID = 1L";
        rewriter.insertAfter(ctx.start,field);
    }
}
