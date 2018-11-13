package com.algorithm.suffixarray;

import com.algorithm.suffixarray.result.SuffixResult;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuffixArrayCore {

    public static int SIZE;

    public static int[] generateSA(List<Token> tokens) {
        SIZE = calculateN(tokens.size());
        int[] wa = new int[SIZE];
        int[] wb = new int[SIZE];
        int[] wv = new int[SIZE];
        int[] ws = new int[SIZE];
        Token[] tk = new CommonToken[tokens.size()];
        tokens.toArray(tk);
        int n = tk.length;
        int m = SIZE;
        int[] x = wa;
        int[] y = wb;
        int[] t ;
        int[] sa = new int[n];
        int i,j,p;
        for(i=0;i<m;i++) ws[i]=0;
        for(i=0;i<n;i++) ws[x[i]=tk[i].getType()]++;
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
            for(t=x,x=y,y=t,p=1,x[sa[0]]=0,i=1;i<n;i++) {
                if(sa[i-1]>526||sa[i]>526){
                    System.out.println("");
                }
                x[sa[i]] = compare(y, sa[i - 1], sa[i], j) ? p - 1 : p++;
            }
        }
        return sa;
    }

    private static int calculateN(int length) {
        int n = 1;
        while (n<length){
            n = n*2;
        }
        return n+length>256?n+length:256;
    }

    private static boolean compare(int[] y, int a, int b, int l) {
        return y[a] == y[b] && y[a+l] == y[b+l];
    }

    public static int[] generateRank(int[] sa) {
        int len = sa.length;
        int[] rank = new int[len];
        for (int i = 0; i < len; i++) {
            rank[sa[i]] = i;
        }
        return rank;
    }

    public static int[] generateH(List<Token> tokens, int[] sa,int fromSize) {
        Token[] ch = new CommonToken[tokens.size()];
        tokens.toArray(ch);
        int n = sa.length;
        int[] h = new int[n+1];
        int[] rank = generateRank(sa);

        int max = 0;
        int maxIndex = 0;
        Arrays.fill(h, 0);
        int i,j,k=0;
        for(i=0;i<n;) {
            if(rank[i]==0){
                h[rank[i++]]=0;
            }else if(sa[rank[i]-1]<fromSize&&sa[rank[i]]<fromSize){
                h[rank[i++]]=-1;
            }else if(sa[rank[i]-1]>=fromSize&&sa[rank[i]]>=fromSize){
                h[rank[i++]]=-1;
            }else {
                for (k = (k != 0 ? k - 1 : 0), j = sa[rank[i] - 1]; (i + k < n) && (j + k < n) && ch[i + k].getType() == ch[j + k].getType(); k++) {
                }
                if (k > max) {
                    max = k;
                    maxIndex = rank[i];
                }
                h[rank[i++]] = k;
            }
        }
        h[0]=max;
        h[n]=maxIndex;
        return h;
    }

    public static int[] generateH2(List<Token> tokens, int[] sa) {
        Token[] ch = new CommonToken[tokens.size()];
        tokens.toArray(ch);
        int n = sa.length;
        int[] h = new int[n+1];
        int[] rank = generateRank(sa);

        int max = 0;
        int maxIndex = 0;
        Arrays.fill(h, 0);
        int i,j,k=0;
        for(i=0;i<n;) {
            if(rank[i]==0){
                continue;
            }
            if(sa[rank[i]-1]<tokens.size()&&sa[rank[i]]<tokens.size()){
                continue;
            }
            if(sa[rank[i]-1]>=tokens.size()&&sa[rank[i]]>=tokens.size()){
                continue;
            }
            for (k = (k != 0 ? k-1 : 0), j = sa[rank[i]-1]; (i+k<n)&&(j+k<n)&&ch[i + k].getType() == ch[j + k].getType(); k++) {
            }
            if(k>max){
                max = k;
                maxIndex=rank[i];
            }
            h[rank[i++]]=k;
        }
        h[0]=max;
        h[n]=maxIndex;
        return h;
    }

    public static SuffixResult compare(List<Token> tk1, List<Token> tk2){
        List<Token> tokenList = new ArrayList<>();
        tokenList.addAll(tk1);
        tokenList.add(new CommonToken(0));
        tokenList.addAll(tk2);

        int n = tokenList.size();
        int[] sa = generateSA(tokenList);
        int[] rank = generateRank(sa);
        int[] height = generateH(tokenList,sa,tk1.size());
        System.out.println("sa   : " + Arrays.toString(sa));
        System.out.println("rank : " + Arrays.toString(rank));
        System.out.println("heig : " + Arrays.toString(height));
//        for (int i = 0; i < sa.length; i++) {
//            System.out.println(height[i] + ":" + s.substring(sa[i]));
//
//        }
        SuffixResult suffixResult = new SuffixResult();
        suffixResult.setLength1(tk1.size());
        suffixResult.setLength2(tk2.size());

        int s1 = sa[height[n]];
        int s2 = sa[height[n]-1];

        if(s1>s2){
            int tmp = s1;
            s1 = s2;
            s2 = tmp;
        }
        suffixResult.setBeginIndex1(s1);
        suffixResult.setBeginIndex2(s2-tk2.size()-1);
        suffixResult.setCommonLength(height[0]);

//        for(int i=0;i<height[0];i++){
//            System.out.println((i+s1)+"->"+tokenList.get(i+s1).getType());
//        }
//        System.out.println("-----------------------");
//        for(int i=0;i<height[0];i++){
//            System.out.println((i+s2)+"->"+tokenList.get(i+s2).getType());
//        }
//        System.out.println(height[0]*2.0/(tk1.size()+tk2.size()));
        return suffixResult;
    }
}