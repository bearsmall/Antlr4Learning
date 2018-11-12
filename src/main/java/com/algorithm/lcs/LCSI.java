package com.algorithm.lcs;

public class LCSI {
    // 最长公共子序列问题 矩阵法解决

    // 求样本矩阵
    // a[i] == b[j] c[i][j] = c[i-1][j-1]+1
    // else c[i][j] = max(c[i-1][j], c[i][j-1])

    public static int[][] getArray(char[] a, char[] b) {
        // 数组c用于记录最长公共子序列矩阵
        // 数组o用于记录最长公共子序列方向
        int[][] c = new int[a.length][b.length];
        int[][] o = new int[a.length][b.length];
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b.length;j++){
                if (i==0&&j==0){
                    if (a[i]==b[j]) {
                        c[i][j] = 1;
                        o[i][j] = -2;
                    }
                } else if (i==0) {
                    if (a[i]==b[j]) {
                        o[i][j] = -2;
                        c[i][j] = 1;
                    } else {
                        c[i][j] = c[i][j-1];
                        o[i][j] = -1;
                    }
                } else if (j==0) {
                    if(a[i]==b[j]) {
                        c[i][j] = 1;
                        o[i][j] = -2;
                    } else {
                        c[i][j] = c[i-1][j];
                        o[i][j] = 1;
                    }
                } else {
                    if(a[i]==b[j]) {
                        c[i][j] = c[i-1][j-1]+1;
                        o[i][j] = 2;
                    } else if (c[i-1][j]>= c[i][j-1]){
                        c[i][j] = c[i-1][j];
                        o[i][j] = 1;
                    } else {
                        c[i][j] = c[i][j-1];
                        o[i][j] = -1;
                    }
                }
            }
        }
        printArray(c);
        return o;
    }

    // 回溯法显示
    public static void display(int[][] o, char[] a, int i, int j) {
        if(o[i][j] == -2) {
             System.out.print(a[i]+" ");
        } else if(o[i][j]==2) {
             display(o, a, i-1, j-1);
             System.out.print(a[i]+" ");
        } else if (o[i][j] == 1) {
             display(o, a, i-1, j);
        } else if (o[i][j] == -1){
             display(o, a, i, j-1);

        }
    }


    public static void printArray(int[][] c){
        for(int i=0;i<c.length;i++){
            for(int j=0;j<c[i].length;j++){
                System.out.print(c[i][j]+" ");
            }
            System.out.println();
        }
    }
}