package com.algorithm.longestcommon.core.sequence;

import com.algorithm.longestcommon.result.CmpResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class LCSCompareTest {

    @Test
    public void getSimRecordMlcs() {
        CmpResult cmpResult = new LCSCompare().getSimRecordMlcs("abcdefgh","acefh");
        System.out.println(cmpResult);
    }
}