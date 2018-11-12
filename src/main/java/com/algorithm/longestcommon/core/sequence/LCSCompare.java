package com.algorithm.longestcommon.core.sequence;


import com.algorithm.longestcommon.result.CmpResult;
import com.algorithm.longestcommon.result.SimiBlock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LCSCompare {

    /**
     *
     * @param list1 target地址
     * @param list2 source地址
     * @return
     */
    public CmpResult getSimRecordMlcs(String list1, String list2){
        List<SimiBlock> retRecord = new LinkedList<>();
        if(list1==null || list2==null){
            return null;
        }
        boolean revertFlag = false;
        int m = list1.length();
        int n = list2.length();
        //确保 m < n，list1 < list2
        if (m>n){
            String lin = list2;
            list2 = list1;
            list1 = lin;
            int temp = n;
            n = m;
            m = temp;
            revertFlag = true;
        }
        int[] c = new int[n+1];
        int[] sim = new int[m];//用于计算相似度
        int ans=0;//记录相似行数
        int templen=0;//临时记录相似串长度
        Arrays.fill(c, 0);
        Arrays.fill(sim, 0);

        for (int i = 0; i <m; i++) {
            for (int j =1; j <n+1; j++) {
                if(i==0){//目标文件第一个字从头比较
                    if (list1.charAt(i) == list2.charAt(j-1)) {
                        c[j]=1;
                        sim[i]=1;
                    }
                    else{
                        c[j]=0;
                    }
                }
                else{//目标文件除了第一个字的其他字从尾比较
                    if(list1.charAt(i) == list2.charAt(n-j)){//相同
                        sim[i] = 1;
                        c[n-j+1] = c[n-j]+1;
                        if(c[n-j]!=0) {
                            j = j + c[n - j] - 1;
                        }
                    }
                    else if(c[n-j]!=0){//不相同且前一位不为0，相同字符串结束
                        if(c[n-j]>3){//相似串长度超过3才进行记录
                            SimiBlock simiBlock;
                            if(revertFlag){
                                simiBlock = new SimiBlock(i-c[n-j],i-1,n-j-c[n-j],n-j-1);//记录样本文件起止行号，行号从1开始
                            }else {
                                simiBlock = new SimiBlock(n-j-c[n-j],n-j-1,i-c[n-j],i-1);//记录样本文件起止行号，行号从1开始
                            }
                            retRecord.add(simiBlock);
                        }
                        templen=c[n-j];
                        for(int temp=0;temp<templen;temp++){//将该记录清空
                            c[n-j-temp]=0;
                        }

                        c[n-j+1]=0;
                        j = j+templen-1;
                    }
                    else{
                        c[n-j+1]=0;
                    }
                }
            }
        }
        for(int i=n;i>0;i--){//最后一次比对未记录的匹配字符串
            if(c[i]>3){//相似串长度超过2才进行记录
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
        //System.out.println(m+"-----"+n+"---------"+ans);
        CmpResult cmpResult;
        if(revertFlag) {
            cmpResult = new CmpResult(retRecord,m,n,ans,ans);
        }else {
            cmpResult = new CmpResult(retRecord,n,m,ans,ans);
        }
        return cmpResult;

    }
}
