package com.algorithm.suffixarray;

import org.junit.Test;

import java.util.Arrays;

public class SuffixArrayTest {


    @Test
    public void testSuffixArray() {
        SuffixArray suffixArray = new SuffixArray();
        String s = "aabaaaab";
        s = "abcdaefg$abcdef";
        int n = s.length();
//        s = "abcdefghijklmnopqrst";
        int[] sa = suffixArray.generateSA(s);
        int[] rank = suffixArray.generateRank(sa);
        int[] height = suffixArray.generateH(s,sa);
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
        for (int i = 0; i < sa.length; i++) {
            System.out.println(height[i] + ":" + s.substring(sa[i]));

        }

        System.out.println("-----------------------");
        int s1 = sa[height[n]];
        int s2 = sa[height[n]-1];
        for(int i=0;i<height[0];i++){
            System.out.println((i+s1)+"->"+s.charAt(i+s1));
        }
        System.out.println("-----------------------");
        for(int i=0;i<height[0];i++){
            System.out.println((i+s2)+"->"+s.charAt(i+s2));
        }
        System.out.println("-----------------------");
    }

}