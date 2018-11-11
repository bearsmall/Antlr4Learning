package com.algorithm.longestcommon.core.sequence;


import com.algorithm.longestcommon.ICompare;
import com.algorithm.longestcommon.result.CmpResult;
import com.algorithm.longestcommon.result.SimiBlock;

import java.util.LinkedList;
import java.util.List;

public class LCSequenceOld implements ICompare {

    @Override
    public CmpResult compare(String str, String target) {
        char[] s1 = str.toCharArray();
        int len1 = str.length();
        char[] s2 = target.toCharArray();
        int len2 = target.length();

        int[] sim1 = new int[len1+1];
        int[] sim2 = new int[len1+1];
        boolean[] flag1 = new boolean[len1+1];
        boolean[] flag2 = new boolean[len2+1];

        List<SimiBlock> simiBlockList = new LinkedList<>();
        for(int i=0;i<len1;i++){
            for(int j=0;j<len2;j++){
                if(s1[i]==s2[j]){
                    sim1[j+1]=sim2[j]+1;
                }else {
                    if(sim1[j]>3){
                        SimiBlock simiBlock = new SimiBlock(i + 2 - sim1[j], i + 1, j + 2 - sim1[j], j + 1);
                        coverSimiBlock(flag1, flag2, simiBlock);
                        simiBlockList.add(simiBlock);
                    }else if(sim1[j]==0&&sim2[j]>3){
//                        SimiBlock simiBlock = new SimiBlock(i + 1 - sim2[j], i, j + 1 - sim2[j], j);
//                        coverSimiBlock(flag1, flag2, simiBlock);
//                        simiBlockList.add(simiBlock);
                    }
                }
            }
            for(int n=0;n<=len2;n++){
                System.out.print(sim1[n]+" ");
            }
            System.out.println();
            if(i<len1-1 && sim1[len2] > 3) {
                if (s1[i] == s2[len2 - 1]) {
                    if(s1[i+1]!=s2[len2-1]||sim1[len2-1]<sim1[len2]) {
                        SimiBlock simiBlock = new SimiBlock(i + 2 - sim1[len2], i + 1, len2 + 1 - sim1[len2], len2);
                        coverSimiBlock(flag1, flag2, simiBlock);
                        simiBlockList.add(simiBlock);
                    }
                }else {
                    SimiBlock simiBlock = new SimiBlock(i + 2 - sim1[len2], i + 1, len2 + 1 - sim1[len2], len2);
                    coverSimiBlock(flag1, flag2, simiBlock);
                    simiBlockList.add(simiBlock);
                }
            }else {
                if (s1[i] == s2[len2 - 1] && sim1[len2] > 3) {
                    SimiBlock simiBlock = new SimiBlock(i + 2 - sim1[len2], i + 1, len2 + 1 - sim1[len2], len2);
                    coverSimiBlock(flag1, flag2, simiBlock);
                    simiBlockList.add(simiBlock);
                }
            }
            int[] tmp = sim1;
            sim1 = sim2;
            sim2 = tmp;
            for(int k=0;k<len1;k++){
                sim1[k]=0;
            }
        }
        int countLine1 = 0;
        int countLine2 = 0;
        for(int i=1;i<=len1;i++){
            if(flag1[i]){
                countLine1++;
            }
        }
        for(int j=1;j<=len2;j++){
            if(flag2[j]){
                countLine2++;
            }
        }
        return new CmpResult(simiBlockList,len1,len2,countLine1,countLine2);
    }

    private void coverSimiBlock(boolean[] flag1, boolean[] flag2, SimiBlock simiBlock) {
        for (int t = simiBlock.getSbegin(); t <= simiBlock.getSend(); t++) {
            flag1[t] = true;
        }
        for (int t = simiBlock.getTbegin(); t <= simiBlock.getTend(); t++) {
            flag2[t] = true;
        }
    }
}
