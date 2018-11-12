package com.algorithm.lcs;

public class LCSII {
    // 最长公共子串问题 要求连续的最长公共子序列
    // 与I相比,可以采用一维数组存储结果即可,矩阵每次遍历一行后可删除

    // 样本矩阵
    // a[i] == b[j] c[i][j] = c[i-1][j-1]+1
    // else c[i][j] = 0

    public static int lcs(char[] a, char[] b) {
        int[] result = new int[b.length];
        int maxLength = 0;
        for(int i=0;i<a.length;i++){
            for(int j=b.length-1; j>=0; j--){
                if(i==0){
                    if(a[i]==b[j]) {
                        result[j] = 1;
                        maxLength = 1;
                    } else {
                        result[j] = 0;
                    }
                } else if (j==0){
                    if(a[i]==b[j]) {
                        result[j] = 1;
                        maxLength = maxLength > 1 ? maxLength:1;
                    }
                } else {
                    if(a[i]==b[j]) {
                        result[j] = result[j-1] + 1;
                        maxLength = maxLength<result[j]?result[j]:maxLength;
                    } else {
                        result[j] = 0;
                    }
                }
            }
        }
        return maxLength;
    }
}