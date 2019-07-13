package com.algorithm.sequence;

public class LCS {

    public static double compareSequence(int[] token1, int[] token2){
        int n = token1.length;
        int m = token2.length;
        int[][] dp = new int[n+1][m+1];
        for(int i=0;i<=n;i++){
            dp[i][0] = 0;
        }
        for(int i=0;i<=m;i++){
            dp[0][i] = 0;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(token1[i-1]==token2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[n][m]*2.0/(n+m);
    }

    public static double compareSequence2(int[] token1, int[] token2){
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
            dp[i] =  0;
            tmp[i] = 0;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(token1[i-1]==token2[j-1]){
                    dp[j] = tmp[j-1]+1;
                }else {
                    dp[j] = Math.max(dp[j-1], tmp[j]);
                }
            }
            int[] t = tmp;
            tmp = dp;
            dp = t;
        }
        return tmp[m]*2.0/(n+m);
    }

    public static double compareSequence3(int[] token1, int[] token2){
        if(token1.length>token2.length){
            int[] tmp = token1;
            token1 = token2;
            token2 = tmp;
        }
        int n = token1.length;
        int m = token2.length;
        int[] dp = new int[n];
        for(int i=0;i<n;i++){
            dp[i] =  (token2[0] ==  token1[i] || (i > 1 && dp[i-1] == 1)) ? 1 : 0;
        }
        int preValRecord = 0;
        int preValForRefreshDp = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                preValRecord = dp[j];
                if(j==0){
                    dp[0] = (token1[0] == token2[i] || dp[0] == 1) ? 1 : 0;
                }else {
                    dp[j] =(token2[i] != token1[j]) ? Math.max(dp[j], dp[j-1]) : preValForRefreshDp+1;
                }
                preValForRefreshDp = preValRecord;
            }
        }
        return dp[n-1]*2.0/(n+m);
    }

    public static double compareString(int[] token1, int[] token2){
        int n = token1.length;
        int m = token2.length;
        int rs = 0;
        int[][] dp = new int[n+1][m+1];
        for(int i=0;i<=n;i++){
            dp[i][0] = 0;
        }
        for(int i=0;i<=m;i++){
            dp[0][i] = 0;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(token1[i-1]==token2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                    rs = Math.max(rs, dp[i][j]);
                }else {
                    dp[i][j] = 0;
                }
            }
        }
        return rs*2.0/(n+m);
    }

    public static double compareString2(int[] token1, int[] token2){
        if(token1.length < token2.length){
            int[] tmp = token1;
            token1 = token2;
            token2 = tmp;
        }
        int rs = 0;
        int n = token1.length;
        int m = token2.length;
        int[] dp = new int[m+1];
        int[] tmp = new int[m+1];
        for(int i=0;i<=n;i++){
            dp[i] =  0;
            tmp[i] = 0;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(token1[i-1]==token2[j-1]){
                    dp[j] = tmp[j-1]+1;
                    rs = Math.max(rs, dp[j]);
                }else {
                    dp[j] = 0;
                }
            }
            int[] t = tmp;
            tmp = dp;
            dp = t;
        }
        return tmp[m]*2.0/(n+m);
    }

    public static double compareString3(int[] token1, int[] token2){
        if(token1.length>token2.length){
            int[] tmp = token1;
            token1 = token2;
            token2 = tmp;
        }
        int n = token1.length;
        int m = token2.length;
        int[] dp = new int[n];
        for(int i=0;i<n;i++){
            dp[i] =  (token2[0] ==  token1[i] || (i > 1 && dp[i-1] == 1)) ? 1 : 0;
        }
        int preValRecord = 0;
        int preValForRefreshDp = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                preValRecord = dp[j];
                if(j==0){
                    dp[0] = (token1[0] == token2[i] || dp[0] == 1) ? 1 : 0;
                }else {
                    dp[j] =(token2[i] != token1[j]) ? 0 : preValForRefreshDp+1;
                }
                preValForRefreshDp = preValRecord;
            }
        }
        return dp[n-1]*2.0/(n+m);
    }
}
