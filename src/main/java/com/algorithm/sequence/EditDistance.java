package com.algorithm.sequence;

public class EditDistance {

    public static double compareDistance(int[] token1, int[] token2){
        int n = token1.length;
        int m = token2.length;
        int[][] dp = new int[n+1][m+1];
        for(int i=0;i<=n;i++){
            dp[i][0] = i;
        }
        for(int i=0;i<=m;i++){
            dp[0][i] = i;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int flag;
                if(token1[i-1]==token2[j-1]) {
                    flag = 0;
                }
                else {
                    flag = 1;
                }
                dp[i][j]=Math.min(dp[i-1][j]+1,Math.min(dp[i][j-1]+1,dp[i-1][j-1]+flag));
            }
        }
        return 1 - dp[n][m]*1.0/(m+n);
    }

    public static double compareDistance2(int[] token1, int[] token2){
        if(token1.length < token2.length){
            int[] tmp = token1;
            token1 = token2;
            token2 = tmp;
        }
        int n = token1.length;
        int m = token2.length;
        int[] dp = new int[m+1];
        int[] tmp = new int[m+1];
        for(int i=0;i<=n;i++){
            dp[i] =  i;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int flag;
                if(token1[i-1]==token2[j-1]) {
                    flag = 0;
                }
                else {
                    flag = 1;
                }
                dp[j]=Math.min(tmp[j]+1,Math.min(dp[j-1]+1,tmp[j-1]+flag));
            }
            int[] t = tmp;
            tmp = dp;
            dp = t;
        }
        return 1 - tmp[m]*1.0/(m+n);
    }
}
