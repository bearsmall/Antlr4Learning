package com.algorithm.lcs;

public class LCSequence {

    /**
     * 求解str1 和 str2 的最长公共子序列
     */
     public static int[][] LCS(String str1, String str2){
         int[][] c = new int[str1.length() + 1][str2.length() + 1];
         for(int row = 0; row <= str1.length(); row++)
             c[row][0] = 0;
         for(int column = 0; column <= str2.length(); column++)
             c[0][column] = 0;
         
         for(int i = 1; i <= str1.length(); i++)
             for(int j = 1; j <= str2.length(); j++){
                 if(str1.charAt(i-1) == str2.charAt(j-1))
                     c[i][j] = c[i-1][j-1] + 1;
                 else if(c[i][j-1] > c[i-1][j])
                     c[i][j] = c[i][j-1];
                 else
                     c[i][j] = c[i-1][j];
             }
         return c;
     }

     public static String LCSString(String str1,String str2,int[][] c){
         int m = str1.length();
         int n = str2.length();
         int i=m-1;
         int j=n-1;

         String s = "";
         while (i>=0&&j>=0){
             if(str1.charAt(i)==str2.charAt(j)){
                 s=str1.charAt(i)+s;
                 i--;
                 j--;
             }else if(i>0&&j>0&&c[i-1][j]>=c[i][j-1]){
                 i--;
             }else{
                 j--;
             }
         }
         return s;
     }
 }