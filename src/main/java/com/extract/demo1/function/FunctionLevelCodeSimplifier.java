package com.extract.demo1.function;

import com.cmp.LineStruct;
import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.extract.demo1.listener.ExtractMethodListenner;
import com.extract.demo1.listener.MethodParseListenner;
import com.extract.demo1.utils.ConstantParams;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FunctionLevelCodeSimplifier {

    public List<String> getSimplifiedMethods(String code){
        CharStream inputStream = CharStreams.fromString(code);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();

        int hashValue=0;
        int tempHashValue=0;
        int tempLineNum=1;
        String lineString = new String();
        List<LineStruct> linetokenlist = new LinkedList<>();
        for (Token token:tokenStream.getTokens()){
            int type = token.getType();
            if(token.getChannel()==JavaLexer.HIDDEN){
                continue;
            }
            if (token.getLine()==tempLineNum){
                lineString = lineString+" "+type;
            }else {
                hashValue = lineString.hashCode();
                if(hashValue!=tempHashValue){
                    LineStruct lineStruct=new LineStruct();
                    lineStruct.setLineNum(tempLineNum);
                    lineStruct.setHashValue(hashValue);
                    linetokenlist.add(lineStruct);
                    tempHashValue=hashValue;
                }
                lineString = " "+token.getText();;
                tempLineNum = token.getLine();
            }
        }
        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
        ExtractMethodListenner extractor = new ExtractMethodListenner(code,tokenStream.getTokens());
        walker.walk(extractor,tree);
        List<String> functionContents = extractor.getFunctionContents();
        List<String> modifiedMethods = new ArrayList<>();
        for(String function:functionContents){
            String method = getSimplifiedMethod(function);
            modifiedMethods.add(method);
        }
        return modifiedMethods;
    }

    public String getSimplifiedMethod(String method){
        CharStream inputStream = CharStreams.fromString(method);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.methodDeclaration();  //运行我们的特殊版本的语法分析器【重写了defineProperty方法】

        ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的ANTLR语法分析树遍历器
        MethodParseListenner loader = new MethodParseListenner(method,tokenStream.getTokens());//新建一个监听器，将其传递给遍历器
        walker.walk(loader,tree);   //遍历语法分析器
        StringBuffer str = new StringBuffer();
        List<Token> tokenList = modifyTokenValue(loader);
        for(int i=0;i<tokenList.size()-1;i++){
            str.append(tokenList.get(i).getText()).append(' ');
        }
        return str.toString();
    }

    /**
     * Token值类型替换
     * @param loader
     * @return
     */
    private List<Token> modifyTokenValue(MethodParseListenner loader) {
        List<Token> formalParamInterval = loader.getFormalParamInterval();//形式参数
        List<Token> localParamInterval = loader.getLocalParamInterval();//局部变量
        List<Token> dataTypeInterval = loader.getDataTypeInterval();//数据类型
        List<Token> methodCallInterval = loader.getMethodCallInterval();//方法调用
        List<Token> paramInterval = loader.getParamInterval();//无标识参数
        List<Token> tokenList = loader.getTokenList();//token列表

        for(Token token: formalParamInterval){
            for(Token t : paramInterval){
                if(t.getText().equals(token.getText())) {
                    tokenList.set(t.getTokenIndex(),new CommonToken(1, ConstantParams.FPARAM));

                }
            }
            tokenList.set(token.getTokenIndex(),new CommonToken(1,ConstantParams.FPARAM));
        }
        for(Token token : localParamInterval){
            for(Token t : paramInterval){
                if(t.getText().equals(token.getText())) {
                    tokenList.set(t.getTokenIndex(),new CommonToken(1, ConstantParams.LVAR));
                }
            }
            tokenList.set(token.getTokenIndex(),new CommonToken(1,ConstantParams.LVAR));
        }
        for(Token token : dataTypeInterval){
            tokenList.set(token.getTokenIndex(),new CommonToken(1,ConstantParams.DTYPE));
        }
        for(Token token : methodCallInterval){
            tokenList.set(token.getTokenIndex(),new CommonToken(1,ConstantParams.FUNCCALL));
        }
        for(Token token : paramInterval){
            String txt = tokenList.get(token.getTokenIndex()).getText();
            if(txt.equals(ConstantParams.FPARAM)||txt.equals(ConstantParams.LVAR)) {
            }else {
                tokenList.set(token.getTokenIndex(),new CommonToken(1, ConstantParams.GLOBALPARAM));
            }
        }
        return tokenList;
    }
}
