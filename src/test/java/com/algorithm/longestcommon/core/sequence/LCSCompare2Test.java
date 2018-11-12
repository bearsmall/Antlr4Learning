package com.algorithm.longestcommon.core.sequence;

import com.algorithm.longestcommon.result.CmpResult;
import org.junit.Test;


public class LCSCompare2Test {

    @Test
    public void getSimRecordMlcs() {
//        CmpResult cmpResult = new LCSCompare2().getSimRecordMlcs("aaaaabaaaaaa","aaaabaaaaaa");
        CmpResult cmpResult = new LCSCompare2().getSimRecordMlcs("abcdef","abcdef");
        System.out.println(cmpResult);
    }
}