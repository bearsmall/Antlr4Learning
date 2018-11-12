package com.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.*;

public class LCSITest {

    @Test
    public void printArray() {
        String b = "ABC";
        String a = "ACB";
        int[][] c = LCSI.getArray(a.toCharArray(), b.toCharArray());
        LCSI.display(c, a.toCharArray(), a.length()-1, b.length()-1);
    }
}