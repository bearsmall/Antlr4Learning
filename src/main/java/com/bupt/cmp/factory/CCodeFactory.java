package com.bupt.cmp.factory;

import com.bupt.cmp.ast.cpp.CPPTreeParser;
import com.bupt.cmp.lang.C;
import com.bupt.cmp.lang.Language;
import com.bupt.cmp.lang.file.CFile;
import com.bupt.cmp.lang.file.DefaultCodeFile;

import java.io.File;

/**
 * C语言代码工厂
 * Created by bearsmall on 2017/9/25.
 */
public class CCodeFactory extends ICodeFactory {
    public CCodeFactory(Language language) {
        super(language);
    }

    /**
     * 静态内部类下的单利模式
     */
    private static class CCodeFactoryImplHolder{
        private static ICodeFactory ICodeFactory = new CCodeFactory(C.getInstance());
    }

    public static ICodeFactory getInstance(){
        return CCodeFactoryImplHolder.ICodeFactory;
    }

    @Override
    protected DefaultCodeFile init(File file) {
        DefaultCodeFile defaultCodeFile = new CFile(file);
        return defaultCodeFile;
    }

    @Override
    protected DefaultCodeFile init(String code) {
        DefaultCodeFile defaultCodeFile = new CFile(code);
        return defaultCodeFile;
    }

    @Override
    public void prepareSyntaxTree(DefaultCodeFile defaultCodeFile) throws Exception{
        try {
            CPPTreeParser.GetCPPGrammarTree(defaultCodeFile);
        } catch (Exception e) {
            System.out.println("wht's the f**k?");
            // 语法预处理错误
//            System.out.println("语法预处理异常：" + defaultCodeFile.getName());
            throw e;
        }
        return ;
    }
}
