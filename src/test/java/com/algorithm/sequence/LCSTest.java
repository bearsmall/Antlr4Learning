package com.algorithm.sequence;

import org.junit.Test;

import static org.junit.Assert.*;

public class LCSTest {
    private int[] a = new int[]{0,3,2,3,4,5,6,7,8,9};
    private int[] b = new int[]{0,1,2,3,4,5,6,7,8,9};

    @Test
    public void compareSequence() {
        System.out.println(LCS.compareSequence(a,b));
    }

    @Test
    public void compareSequence2() {
        System.out.println(LCS.compareSequence2(a,b));
    }
    @Test
    public void compareSequence3() {
        System.out.println(LCS.compareSequence3(a,b));
    }

    @Test
    public void compareString() {
        System.out.println(LCS.compareString(a,b));
    }

    @Test
    public void compareString2() {
        System.out.println(LCS.compareString2(a,b));
    }

    @Test
    public void compareString3() {
        System.out.println(LCS.compareString2(a,b));
    }
}