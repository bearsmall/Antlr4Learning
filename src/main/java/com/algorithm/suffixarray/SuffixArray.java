package com.algorithm.suffixarray;

import java.util.Arrays;

public class SuffixArray {

    public static final int SIZE = 256;

    public static int[] generateSA(String s) {
        int[] wa = new int[SIZE];
        int[] wb = new int[SIZE];
        int[] wv = new int[SIZE];
        int[] ws = new int[SIZE];
        char[] r = s.toCharArray();
        int n = r.length;
        int m = SIZE;
        int[] x = wa;
        int[] y = wb;
        int[] t ;
        int[] sa = new int[n];
        int i,j,p;
        for(i=0;i<m;i++) ws[i]=0;
        for(i=0;i<n;i++) ws[x[i]=r[i]]++;
        for(i=1;i<m;i++) ws[i]+=ws[i-1];
        for(i=n-1;i>=0;i--) sa[--ws[x[i]]]=i;
        for(j=1,p=1;p<n;j*=2,m=p){
            if(j>=n){
                break;
            }
            for(p=0,i=n-j;i<n;i++) y[p++]=i;
            for(i=0;i<n;i++) if(sa[i]>=j) y[p++]=sa[i]-j;
            for(i=0;i<n;i++) wv[i]=x[y[i]];
            for(i=0;i<m;i++) ws[i]=0;
            for(i=0;i<n;i++) ws[wv[i]]++;
            for(i=1;i<m;i++) ws[i]+=ws[i-1];
            for(i=n-1;i>=0;i--) sa[--ws[wv[i]]]=y[i];
            for(t=x,x=y,y=t,p=1,x[sa[0]]=0,i=1;i<n;i++)
                x[sa[i]]=compare(y,sa[i-1],sa[i],j)?p-1:p++;
        }
        return sa;
    }

    private static boolean compare(int[] y, int a, int b, int l) {
        return y[a] == y[b] && y[a+l] == y[b+l];
    }

    public static int[] generateRank(int[] sa) {
        int len = sa.length;
        int[] rank = new int[len];
        for (int i = 0; i < len; i++)
            rank[sa[i]] = i;
        return rank;
    }

    public static int[] generateH(String s, int[] sa) {
        char[] ch = s.toCharArray();
        int n = sa.length;
        int[] h = new int[n];
        int[] rank = generateRank(sa);

        Arrays.fill(h, 0);
        int i,j,k=0;
        for(i=0;i<n;h[rank[i++]]=k) {
            if(rank[i]==0){
                continue;
            }
            for (k = (k != 0 ? k-1 : 0), j = sa[rank[i]-1]; (i+k<n)&&(j+k<n)&&ch[i + k] == ch[j + k]; k++) {
            }
        }
        h[0]=0;
        return h;
    }

}