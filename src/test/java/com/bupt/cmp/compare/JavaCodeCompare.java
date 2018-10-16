package com.bupt.cmp.compare;

import com.bupt.cmp.factory.ICodeFactory;
import com.bupt.cmp.factory.JavaCodeFactory;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import org.junit.Test;

import java.io.File;

public class JavaCodeCompare {
    public static ICodeFactory icodeFactory;
    CompareDirector mlcsd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TEXT_COMPARE);         //比对指示器1（文本比对）;
    CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);    //比对指示器2（Token比对）;
    CompareDirector syntaxd = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.SYNTAX_COMPARE);     //比对指示器3（语法树比对）
    public static DefaultCodeFile defaultCodeFile1;
    public static DefaultCodeFile defaultCodeFile2;

    static {
        icodeFactory = JavaCodeFactory.getInstance();
        defaultCodeFile1 = icodeFactory.generateDefectCodeFile(new File("D:\\test\\demo\\Demo1.java"));
        defaultCodeFile2 = icodeFactory.generateDefectCodeFile(new File("D:\\test\\demo\\Demo2.java"));
    }

    @Test
    public void test1(){
        CompareResult compareResult1 = mlcsd.compare(defaultCodeFile1.getTextLine(),defaultCodeFile2.getTextLine());
        CompareResult compareResult2 = tokenmd.compare(defaultCodeFile1.getTokenLine(),defaultCodeFile2.getTokenLine());
        CompareResult compareResult3 = syntaxd.compareSyntax(defaultCodeFile1.getTree(),defaultCodeFile2.getTree());
        System.out.println(compareResult1.getSimvalue());
        System.out.println(compareResult1.getSimRecords());
        String str = SimRecord.SimRecordtoString(compareResult2.getSimRecords());
        SimRecord[] simRecords = SimRecord.StringtoSimRecord(str);
        System.out.println(str);
        System.out.println(simRecords);
        System.out.println(compareResult2.getSimvalue());
        System.out.println(compareResult2.getSimRecords());
        System.out.println(compareResult3.getSimvalue());
        System.out.println(compareResult3.getSimRecords());
    }

    @Test
    public void test2(){
        System.out.println(0|128);
    }


    @Test
    public void test3(){
        File file = new File(JavaCodeCompare.class.getResource("").getPath());
        System.out.println(file.getAbsolutePath());
    }
}
