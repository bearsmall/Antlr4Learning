package com.cmp.compare;

import com.algorithm.suffixarray.SuffixArrayCore;
import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JavaCompareTest {

    public static ICodeFactory icodeFactory;
    CompareDirector mlcsd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TEXT_COMPARE);         //比对指示器1（文本比对）;
    CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);    //比对指示器2（Token比对）;
    CompareDirector syntaxd = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.SYNTAX_COMPARE);     //比对指示器3（语法树比对）
    public static DefaultCodeFile defaultCodeFile1;
    public static DefaultCodeFile defaultCodeFile2;

    static {
        icodeFactory = JavaCodeFactory.getInstance();
        String src1 = JavaCompareTest.class.getClassLoader().getResource("language/java/complex1/Demo1.java").getPath();
        String src2 = JavaCompareTest.class.getClassLoader().getResource("language/java/complex1/Demo2.java").getPath();
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

    @Test
    public void test2(){
//        ModuleEntity moduleEntity1 = defaultCodeFile1.getClassBlocks().get(0);
//        ModuleEntity moduleEntity2 = defaultCodeFile2.getClassBlocks().get(0);

        List<Token> tokenList1 = defaultCodeFile1.getMethods().get(1).getTokenList();
        Set<Token> param1 = defaultCodeFile1.getMethods().get(1).getDeclaredParams();
        if(defaultCodeFile1.getMethods().get(1).getUnUsedParams()!=null) {
            param1.removeAll(defaultCodeFile1.getMethods().get(1).getUnUsedParams());
        }
        List<Token> tokenList2 = defaultCodeFile2.getMethods().get(1).getTokenList();
        Set<Token> param2 = defaultCodeFile2.getMethods().get(1).getDeclaredParams();
        if(defaultCodeFile2.getMethods().get(1).getDeclaredParams()!=null) {
            param2.removeAll(defaultCodeFile2.getMethods().get(1).getUnUsedParams());
        }
        if(tokenList1.get(tokenList1.size()-1).getType()==-1){
            tokenList1.remove(tokenList1.size()-1);
        }
        if(tokenList2.get(tokenList2.size()-1).getType()==-1){
            tokenList2.remove(tokenList2.size()-1);
        }
        double simi = calculateTokenSimi(tokenList1, tokenList2);
        System.out.println(simi);
    }

    private double calculateTokenSimi(List<Token> tokens1, List<Token> tokens2) {
        List<Token> tokenList = new ArrayList<>();
        tokenList.addAll(tokens1);
        tokenList.addAll(tokens2);

        SuffixArrayCore suffixArray = new SuffixArrayCore();
        int n = tokenList.size();
//        s = "abcdefghijklmnopqrst";
        int[] sa = suffixArray.generateSA(tokenList);
        int[] rank = suffixArray.generateRank(sa);
        int[] height = suffixArray.generateH(tokenList,sa,tokens1.size());
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
//        for (int i = 0; i < sa.length; i++) {
//            System.out.println(height[i] + ":" + s.substring(sa[i]));
//        }

        System.out.println("-----------------------");
        int s1 = sa[height[n]];
        int s2 = sa[height[n]-1];
//        for(int i=0;i<height[0];i++){
//            System.out.println((i+s1)+"->"+tokenList.get(i+s1).getType());
//        }
//        System.out.println("-----------------------");
//        for(int i=0;i<height[0];i++){
//            System.out.println((i+s2)+"->"+tokenList.get(i+s2).getType());
//        }
        System.out.println("-----------------------");
        System.out.println(height[0]*2.0/n);
        return height[0]*2.0/n;
    }
}
