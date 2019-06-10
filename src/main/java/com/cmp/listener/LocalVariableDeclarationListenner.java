package com.cmp.listener;

import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LocalVariableDeclarationListenner extends JavaParserBaseListener {
    private Set<Token> methodVarSet;

    public LocalVariableDeclarationListenner(Set<Token> methodVarSet) {
        this.methodVarSet = methodVarSet;
    }

    @Override
    public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        List<JavaParser.VariableDeclaratorContext> vdc = ctx.variableDeclarators().variableDeclarator();
        for(JavaParser.VariableDeclaratorContext jvdc:vdc){
            String varId = jvdc.variableDeclaratorId().getText();
            System.out.println(varId);
        }
    }

    /**
     * 在遍历Primary的时候确定变未使用过的量名
     * @param ctx
     */
    @Override
    public void enterPrimary(JavaParser.PrimaryContext ctx) {
        TerminalNode tn = ctx.IDENTIFIER();
        if(tn!=null){
            if(!methodVarSet.isEmpty()){
                for(Iterator<Token> iterator = methodVarSet.iterator();iterator.hasNext();){
                    Token token = iterator.next();
                    if(token.getText().equals(tn.getSymbol().getText())){
                        iterator.remove();
                    }
                }
            }
        }
    }
}
