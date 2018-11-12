package com.algorithm.longestcommon.core.sequence;


import com.algorithm.longestcommon.result.CmpResult;
import com.algorithm.longestcommon.result.SimiBlock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LCSCompare2 {

    /**
     *
     * @param list1 target地址
     * @param list2 source地址
     * @return
     */
    public CmpResult getSimRecordMlcs(String list1, String list2){
        if(list1==null || list2==null){
            return null;
        }
        List<SimiBlock> retRecord = new LinkedList<>();
        boolean revertFlag = false;
        //确保 m < n，list1 < list2
        if(list1.length()>list2.length()){
            String tmpList = list1;
            list1 = list2;
            list2 = tmpList;
            revertFlag = true;
        }
        int m = list1.length();
        int n = list2.length();

        int[] c = new int[n+1];
        int[] sim = new int[m];//用于计算相似度
        int ans=0;//记录相似行数
        int templen=0;//临时记录相似串长度
        Arrays.fill(c, 0);
        Arrays.fill(sim, 0);

        for (int i = 0; i <m; i++) {
            for (int j =0; j <n; j++) {
                if(list1.charAt(i) == list2.charAt(j)){
                    sim[i] = 1;
                    c[j+1] = c[j] + 1;
                    if(c[j] != 0){
                        j = j + c[j] - 1;
                    }
                }else if (c[j] != 0){
                    if(c[j] > 3){
                        SimiBlock simiBlock;
                        if(revertFlag){
                            simiBlock = new SimiBlock(i-c[j],i-1,j-c[j],j-1);//记录样本文件起止行号，行号从1开始
                        }else {
                            simiBlock = new SimiBlock(j-c[j],j-1,i-c[j],i-1);//记录样本文件起止行号，行号从1开始
                        }
                        retRecord.add(simiBlock);
                    }
                    templen=c[j];
                    for(int temp=0;temp<templen;temp++){//将该记录清空
                        c[j-temp]=0;
                    }

                    c[j+1]=0;
                    j = j+templen-1;
                }
                else{
                    c[j+1]=0;
                }
            }
        }
        for(int i=n;i>0;i--){//最后一次比对未记录的匹配字符串
            if(c[i]>3){//相似串长度超过3才进行记录
                SimiBlock simiBlock;
                if(revertFlag){
                    simiBlock = new SimiBlock(m-c[i],m-1,i-c[i],i-1);//记录样本文件起止行号，行号从1开始
                }else {
                    simiBlock = new SimiBlock(i-c[i],i-1,m-c[i],m-1);//记录样本文件起止行号，行号从1开始
                }
                retRecord.add(simiBlock);
            }
            i=i-c[i];
        }
        for(int i=0;i<m;i++)if(sim[i]!=0)ans++;//相似行数
        CmpResult cmpResult;
        if(revertFlag) {
            cmpResult = new CmpResult(retRecord,m,n,ans,ans);
        }else {
            cmpResult = new CmpResult(retRecord,n,m,ans,ans);
        }
        return cmpResult;

    }
}
