package com.algorithm.sequence;

import org.junit.Test;

import static org.junit.Assert.*;

public class EditDistanceTest {

    private int[] a = new int[]{0,1,2,3,4,5,6,7,8,9};
    private int[] b = new int[]{0,1,2,3,4,5,6,7,8,9};

    @Test
    public void compareSequence() {
        System.out.println(EditDistance.compareDistance(a,b));
    }

    @Test
    public void compareSequence2() {
        System.out.println(EditDistance.compareDistance2(a,b));
    }
}