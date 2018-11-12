package com.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.*;

public class LCSequence2Test {

    @Test
    public void printLCS() {
        String s1 = "ABC";
        String s2 = "ACB";
        int[][] b = LCSequence2.LCS2(s1,s2);
        System.out.println(LCSequence2.PrintLCS(b,s1,s1.length(),s2.length()));
    }
}