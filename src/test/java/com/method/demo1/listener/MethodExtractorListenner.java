package com.cmp.listener;

import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.antlr.java.JavaParser;
import com.cmp.antlr.java.JavaParserBaseListener;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodExtractorListenner extends JavaParserBaseListener {

    private DefaultCodeFile defaultCodeFile;
    private List<Token> tokenList;

    public MethodExtractorListenner(DefaultCodeFile defaultCodeFile, List<Token> tokenList) {
        this.defaultCodeFile = defaultCodeFile;
        this.tokenList = tokenList;
    }

    /**
     * 根据起始行号截取起止文本
     * @param start
     * @param stop
     * @return
     */
    private String getPureContentWithLine(Token start, Token stop) {
        int startLine = start.getLine();
        int stopLine = stop.getLine();
        Pattern pattern = Pattern.compile("\n");
        Matcher find = pattern.matcher(defaultCodeFile.getContent());
        int number = 0;
        int i = 0;
        if (startLine != 1) {
            while (find.find()) {
                number++;
                if (number == startLine - 1) {
                    break;
                }
            }
            i = find.start() + 1;
        }
        int j = defaultCodeFile.getContent().length();
        if (stopLine != defaultCodeFile.getLineNum()) {
            while (find.find()) {
                number++;
                if (number == stopLine) {
                    break;
                }
            }
            j = find.start();
        }
        return defaultCodeFile.getContent().substring(i, j);
    }

    /**
     * 根据字符串起始偏移量截取起止文本
     * @param start
     * @param stop
     * @return
     */
    private String getPureContent(Token start, Token stop) {
        int starOff = start.getStartIndex();
        int stopOff = stop.getStopIndex();
        return defaultCodeFile.getContent().substring(starOff, stopOff+1);
    }

    private ModuleEntity extractModuleEntity(Token start,Token stop) {
        int charStart = start.getStartIndex();
        int charEnd = stop.getStartIndex();
        int tokenStart = start.getTokenIndex();
        int tokenEnd = stop.getTokenIndex();
        return new ModuleEntity(charStart,charEnd,tokenStart,tokenEnd,defaultCodeFile.getContent().substring(charStart,charEnd+1),tokenList.subList(tokenStart,tokenEnd));
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
//        String method = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getMethods().add(moduleEntity);
    }

    @Override
    public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
//        String construction = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getConstructor().add(moduleEntity);
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
//        String classBlock = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getClassBlocks().add(moduleEntity);
    }

    @Override
    public void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx) {
//        String interfaceBlock = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getInterfaceBlocks().add(moduleEntity);
    }

    @Override
    public void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx) {
//        String enumBlock = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getEnumBlocks().add(moduleEntity);
    }

    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {
        if(ctx.getParent().getRuleIndex()==JavaParser.RULE_classBodyDeclaration) {
//            String staticBlock = getPureContent(ctx.getStart(), ctx.getStop());
            ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
            defaultCodeFile.getStaticBlocks().add(moduleEntity);
        }
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
//        String field = getPureContent(ctx.getStart(), ctx.getStop());
        ModuleEntity moduleEntity = extractModuleEntity(ctx.getStart(),ctx.getStop());
        defaultCodeFile.getFields().add(moduleEntity);
    }
}
