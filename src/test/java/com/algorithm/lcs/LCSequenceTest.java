package com.algorithm.lcs;

import org.junit.Test;

public class LCSequenceTest {

    @Test
    public void LCS() {
        String str1 = "BDCABA";
        String str2 = "ABCBDAB";
        int[][] c = LCSequence.LCS(str1, str2);
        System.out.println(c[str1.length()][str2.length()]);
    }

    @Test
    public void LCSString() {
        String str1 = "ABC";
        String str2 = "ACB";
        int[][] c = LCSequence.LCS(str1, str2);
        System.out.println(c[str1.length()][str2.length()]);
        System.out.println(LCSequence.LCSString(str1,str2,c));
    }
}