package com.algorithm.suffixarray;

import org.junit.Test;

import java.util.Arrays;

public class SuffixArrayTest {


    @Test
    public void testSuffixArray() {
        SuffixArray suffixArray = new SuffixArray();
        String s = "aabaaaab";
        s = "aaaaabaaaaaa&aaaaabaaaaaa";
        int[] sa = suffixArray.generateSA(s);
        int[] rank = suffixArray.generateRank(sa);
        int[] height = suffixArray.generateH(s,sa);
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
        for (int i = 0; i < sa.length; i++)
            System.out.print(s.substring(sa[i])+" ");
    }

}