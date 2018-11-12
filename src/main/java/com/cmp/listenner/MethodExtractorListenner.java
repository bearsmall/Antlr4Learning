package com.cmp.listenner;

import com.cmp.DefaultCodeFile;
import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodExtractorListenner extends JavaParserBaseListener {

    private DefaultCodeFile defaultCodeFile;

    public MethodExtractorListenner(DefaultCodeFile defaultCodeFile) {
        this.defaultCodeFile = defaultCodeFile;
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        int startLine = ctx.getStart().getLine();
        int stopLine = ctx.getStop().getLine();
        Pattern pattern = Pattern.compile("\n");
        Matcher find = pattern.matcher(defaultCodeFile.getContent());
        int number = 0;
        while (find.find()){
            number++;
            if(number==startLine-1){
                break;
            }
        }
        int i = find.start();
        while (find.find()){
            number++;
            if(number==stopLine){
                break;
            }
        }
        int j = find.start();
        String method = defaultCodeFile.getContent().substring(i,j);
        defaultCodeFile.getMethods().add(method);
    }

}
