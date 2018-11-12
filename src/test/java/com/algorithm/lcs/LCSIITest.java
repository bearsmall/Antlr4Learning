package com.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.*;

public class LCSIITest {

    @Test
    public void lcs() {
        String query="ABC";
        String text="ACB";
        System.out.println(LCSII.lcs(query.toCharArray(), text.toCharArray()));
    }
}