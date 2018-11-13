package com.algorithm.suffixarray;

import com.cmp.SingleToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SuffixArrayCoreTest {

    public List<SingleToken> createTokens(){
        List<SingleToken> tokenList = new ArrayList<>();
        for(int i=1;i<10;i++){
            SingleToken st1 = new SingleToken();
            st1.setType(i);
            st1.setLine(i);
            st1.setChannel(0);
            st1.setIndex(i);
            st1.setStart(i*10);
            st1.setStart(i*10+5);
            tokenList.add(st1);
        }
        SingleToken st = new SingleToken();
        st.setType(0);
        st.setLine(0);
        st.setChannel(0);
        st.setIndex(0);
        st.setStart(0*10);
        st.setStart(0*10+5);
        tokenList.add(st);
        for(int i=1;i<8;i++){
            SingleToken st2 = new SingleToken();
            st2.setType(i);
            st2.setLine(i);
            st2.setChannel(0);
            st2.setIndex(i);
            st2.setStart(i*10);
            st2.setStart(i*10+5);
            tokenList.add(st2);
        }
        return tokenList;
    }

    @Test
    public void testSuffixArray() {
        SuffixArrayCore suffixArray = new SuffixArrayCore();
        List<SingleToken> tokenList = createTokens();
        int n = tokenList.size();
//        s = "abcdefghijklmnopqrst";
        int[] sa = suffixArray.generateSA(tokenList);
        int[] rank = suffixArray.generateRank(sa);
        int[] height = suffixArray.generateH(tokenList,sa);
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
//        for (int i = 0; i < sa.length; i++) {
//            System.out.println(height[i] + ":" + s.substring(sa[i]));
//
//        }

        System.out.println("-----------------------");
        int s1 = sa[height[n]];
        int s2 = sa[height[n]-1];
        for(int i=0;i<height[0];i++){
            System.out.println((i+s1)+"->"+tokenList.get(i+s1).getType());
        }
        System.out.println("-----------------------");
        for(int i=0;i<height[0];i++){
            System.out.println((i+s2)+"->"+tokenList.get(i+s2).getType());
        }
        System.out.println("-----------------------");
    }
}