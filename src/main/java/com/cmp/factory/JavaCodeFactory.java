package com.cmp.factory;

import com.cmp.DefaultCodeFile;
import com.cmp.lang.Java;
import com.cmp.lang.Language;
import com.cmp.lang.file.JavaFile;

import java.io.File;

/**
 * Java代码工厂类
 * Created by bearsmall on 2017/9/25.
 */
public class JavaCodeFactory extends ICodeFactory {
    private JavaCodeFactory(Language language) {
        super(language);
    }

    /**
     * 静态内部类下的单利模式
     */
    private static class JavaCodeFactoryImplHolder{
        private static ICodeFactory ICodeFactory = new JavaCodeFactory(Java.getInstance());
    }

    public static ICodeFactory getInstance(){
        return JavaCodeFactoryImplHolder.ICodeFactory;
    }

    @Override
    protected DefaultCodeFile init(File file) {
        DefaultCodeFile defaultCodeFile = new JavaFile(file);
        return defaultCodeFile;
    }

    @Override
    protected DefaultCodeFile init(String code) {
        DefaultCodeFile defaultCodeFile = new JavaFile(code);
        return defaultCodeFile;
    }

    @Override
    public void prepareSyntaxTree(DefaultCodeFile defaultCodeFile) throws Exception{
        //TODO
    }

}
