package com.algorithm.suffixarray;

import com.algorithm.suffixarray.result.SuffixResult;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SuffixArrayCoreTest {

    public List<Token> createTokens1(){
        List<Token> tokenList = new ArrayList<>();
        for(int i=1;i<10;i++){
            CommonToken st1 = new CommonToken(i);
            st1.setLine(i);
            st1.setChannel(0);
            st1.setTokenIndex(i);
            st1.setStartIndex(i*10);
            st1.setStopIndex(i*10+5);
            tokenList.add(st1);
        }
        return tokenList;
    }

    public List<Token> createTokens2(){
        List<Token> tokenList = new ArrayList<>();
        for(int i=3;i<12;i++){
            CommonToken st1 = new CommonToken(i);
            st1.setLine(i);
            st1.setChannel(0);
            st1.setTokenIndex(i);
            st1.setStartIndex(i*10);
            st1.setStopIndex(i*10+5);
            tokenList.add(st1);
        }
        return tokenList;
    }

    @Test
    public void testSuffixArray() {
        SuffixArrayCore suffixArray = new SuffixArrayCore();
        List<Token> tokenList = createTokens1();
        tokenList.add(new CommonToken(0));
        tokenList.addAll(createTokens2());
        int n = tokenList.size();
//        s = "abcdefghijklmnopqrst";
        int[] sa = suffixArray.generateSA(tokenList);
        int[] rank = suffixArray.generateRank(sa);
        int[] height = suffixArray.generateH(tokenList,sa,9);
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
//        for (int i = 0; i < sa.length; i++) {
//            System.out.println(height[i] + ":" + s.substring(sa[i]));
//
//        }

        System.out.println("-----------------------");
        int s1 = sa[height[n]];
        int s2 = sa[height[n]-1];
        for(int i=0;i<height[0];i++){
            System.out.println((i+s1)+"->"+tokenList.get(i+s1).getType());
        }
        System.out.println("-----------------------");
        for(int i=0;i<height[0];i++){
            System.out.println((i+s2)+"->"+tokenList.get(i+s2).getType());
        }
        System.out.println("-----------------------");
    }

    @Test
    public void testSuffixArray2() {
        SuffixArrayCore suffixArray = new SuffixArrayCore();
        List<Token> tokenList1 = createTokens1();
        List<Token> tokenList2 = createTokens2();
        SuffixResult suffixResult = suffixArray.compare(tokenList1,tokenList2);
        System.out.println(suffixResult);
    }
}