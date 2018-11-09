package com.cmp.factory;

import com.cmp.DefaultCodeFile;
import com.cmp.lang.C;
import com.cmp.lang.Language;
import com.cmp.lang.file.CFile;

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
        //TODO
    }
}
