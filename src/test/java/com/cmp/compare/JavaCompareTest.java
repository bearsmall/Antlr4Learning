package com.cmp.compare;

import com.cmp.DefaultCodeFile;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import org.junit.Test;

import java.io.File;

public class JavaCompareTest {

    public static ICodeFactory icodeFactory;
    CompareDirector mlcsd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TEXT_COMPARE);         //比对指示器1（文本比对）;
    CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);    //比对指示器2（Token比对）;
    CompareDirector syntaxd = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.SYNTAX_COMPARE);     //比对指示器3（语法树比对）
    public static DefaultCodeFile defaultCodeFile1;
    public static DefaultCodeFile defaultCodeFile2;

    static {
        icodeFactory = JavaCodeFactory.getInstance();
        String src1 = JavaCompareTest.class.getClassLoader().getResource("language/java/Demo1.java").getPath();
        String src2 = JavaCompareTest.class.getClassLoader().getResource("language/java/Demo2.java").getPath();
        Long start = System.currentTimeMillis();
        defaultCodeFile1 = icodeFactory.generateDefectCodeFile(new File(src1));
        defaultCodeFile2 = icodeFactory.generateDefectCodeFile(new File(src2));
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    @Test
    public void test1(){
        CompareResult compareResult1 = mlcsd.compare(defaultCodeFile1.getTextLine(),defaultCodeFile2.getTextLine());
        CompareResult compareResult2 = tokenmd.compare(defaultCodeFile1.getTokenLine(),defaultCodeFile2.getTokenLine());
//        CompareResult compareResult3 = syntaxd.compareSyntax(defaultCodeFile1.getTree(),defaultCodeFile2.getTree());
        System.out.println(compareResult1.getSimvalue());
        System.out.println(compareResult1.getSimRecords());

        System.out.println(compareResult2.getSimvalue());
        System.out.println(compareResult2.getSimRecords());

        String str = SimRecord.SimRecordtoString(compareResult2.getSimRecords());
        SimRecord[] simRecords = SimRecord.StringtoSimRecord(str);
    }
}
