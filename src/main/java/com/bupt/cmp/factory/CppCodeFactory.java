package com.bupt.cmp.factory;

import com.bupt.cmp.ast.cpp.CPPTreeParser;
import com.bupt.cmp.lang.CPP;
import com.bupt.cmp.lang.Language;
import com.bupt.cmp.lang.file.CPPFile;
import com.bupt.cmp.lang.file.DefaultCodeFile;

import java.io.File;

/**
 * CPP语言代码工厂
 * Created by bearsmall on 2017/9/25.
 */
public class CppCodeFactory extends ICodeFactory {
    private CppCodeFactory(Language language) {
        super(language);
    }

    /**
     * 静态内部类下的单利模式
     */
    private static class CppCodeFactoryImplHolder{
        private static ICodeFactory ICodeFactory = new CppCodeFactory(CPP.getInstance());
    }

    public static ICodeFactory getInstance(){
        return CppCodeFactoryImplHolder.ICodeFactory;
    }

    @Override
    protected DefaultCodeFile init(File file) {
        DefaultCodeFile defaultCodeFile = new CPPFile(file);
        return defaultCodeFile;
    }

    @Override
    protected DefaultCodeFile init(String code) {
        DefaultCodeFile defaultCodeFile = new CPPFile(code);
        return defaultCodeFile;
    }

    @Override
    public void prepareSyntaxTree(DefaultCodeFile defaultCodeFile) throws Exception{
        try {
            CPPTreeParser.GetCPPGrammarTree(defaultCodeFile);
        } catch (Exception e) {
            // 语法预处理错误
//            System.out.println("语法预处理异常：" + defaultCodeFile.getName());
            throw e;
        }
        return ;
    }




}