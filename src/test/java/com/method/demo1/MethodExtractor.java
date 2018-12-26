package com.method.demo1;

import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.method.demo1.listener.MethodExtractorListenner;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodExtractor {
    @Test
    public void test() throws IOException {
        CharStream inputStream = CharStreams.fromStream(MethodExtractor.class.getClassLoader().getResourceAsStream("language\\java\\Method.java"));
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.methodDeclaration();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】

        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的ANTLR语法分析树遍历器
        MethodExtractorListenner loader = new MethodExtractorListenner(inputStream.toString(),tokenStream.getTokens());//新建一个监听器，将其传递给遍历器
        walker.walk(loader,tree);   //遍历语法分析器
        StringBuffer str = new StringBuffer();
        List<Token> tokenList = modifyTokenValue(loader);
        for(int i=0;i<tokenList.size()-1;i++){
            str.append(tokenList.get(i).getText()).append(' ');
        }
        System.out.println(str);
        System.out.println(loader);//从map中取值
    }

    /**
     * Token值类型替换
     * @param loader
     * @return
     */
    private List<Token> modifyTokenValue(MethodExtractorListenner loader) {
        List<Token> formalParamInterval = loader.getFormalParamInterval();//形式参数
        List<Token> localParamInterval = loader.getLocalParamInterval();//局部变量
        List<Token> dataTypeInterval = loader.getDataTypeInterval();//数据类型
        List<Token> methodCallInterval = loader.getMethodCallInterval();//方法调用
        List<Token> paramInterval = loader.getParamInterval();//无标识参数
        List<Token> tokenList = loader.getTokenList();//token列表

        for(Token token: formalParamInterval){
            for(Token t : paramInterval){
                if(t.getText().equals(token.getText())) {
                    tokenList.set(t.getTokenIndex(),new CommonToken(1, "FPARAM"));

                }
            }
            tokenList.set(token.getTokenIndex(),new CommonToken(1,"FPARAM"));
        }
        for(Token token : localParamInterval){
            for(Token t : paramInterval){
                if(t.getText().equals(token.getText())) {
                    tokenList.set(t.getTokenIndex(),new CommonToken(1, "LVAR"));
                }
            }
            tokenList.set(token.getTokenIndex(),new CommonToken(1,"LVAR"));
        }
        for(Token token : dataTypeInterval){
            tokenList.set(token.getTokenIndex(),new CommonToken(1,"DTYPE"));
        }
        for(Token token : methodCallInterval){
            tokenList.set(token.getTokenIndex(),new CommonToken(1,"FUNCCALL"));
        }
        for(Token token : paramInterval){
            String txt = tokenList.get(token.getTokenIndex()).getText();
            if(txt.equals("FPARAM")||txt.equals("LVAR")) {
            }else {
                tokenList.set(token.getTokenIndex(),new CommonToken(1, "GLOBAL_PARAM"));
            }
        }
        return tokenList;
    }
}
