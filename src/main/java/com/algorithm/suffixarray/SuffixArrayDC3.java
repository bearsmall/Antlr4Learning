package com.algorithm.suffixarray;

import java.util.Arrays;

public class SuffixArrayDC3 {

    private int wa[] = new int[1024];
    private int wb[] = new int[1024];
    private int wv[] = new int[1024];
    private int ws[] = new int[1024];
    private int tb =0;

    private int F(int x) {
        return ((x)/3+((x)%3==1?0:tb));
    }
    private int G(int x) {
        return ((x)<tb?(x)*3+1:((x)-tb)*3+2);
    }

    public boolean c0(int[] tokens, int a, int b){
        return tokens[a]==tokens[b]
                && tokens[a+1]==tokens[b+1]
                && tokens[a+2]==tokens[b+2];
    }

    public boolean c12(int k, int[] tokens, int a, int b){
        if(k==2) {
            return tokens[a] < tokens[b]
                    || tokens[a] == tokens[b] && c12(1, tokens, a + 1, b + 1);
        }
        else {
            return tokens[a] < tokens[b]
                    || tokens[a] == tokens[b] && wv[a + 1] < wv[b + 1];
        }
    }

    public void sort(int[] tokens, int pre, int[] a, int[] b, int n, int m){
        int i;
        for(i=0;i<n;i++) wv[i]=tokens[pre+a[i]];
        for(i=0;i<m;i++) ws[i]=0;
        for(i=0;i<n;i++) ws[wv[i]]++;
        for(i=1;i<m;i++) ws[i]+=ws[i-1];
        for(i=n-1;i>=0;i--) b[--ws[wv[i]]]=a[i];
    }

    void dc3(int[] tokens, int[] sa, int n, int m){
//        int i,j,*rn=r+n,*san=sa+n,ta=0,tb=(n+1)/3,tbc=0,p;
        int i;
        int j;
        int ta=0;
        int tb=(n+1)/3;
        int tbc=0;
        int p;

        int[] rn = tokens;
        int[] san = sa;

//        r[n]=r[n+1]=0;
        for(i=0;i<n;i++) if(i%3!=0) wa[tbc++]=i;
        sort(tokens,2,wa,wb,tbc,m);
        sort(tokens,1,wb,wa,tbc,m);
        sort(tokens,0,wa,wb,tbc,m);
        for(p=1,rn[F(wb[0])]=0,i=1;i<tbc;i++)
            rn[F(wb[i])]=c0(tokens,wb[i-1],wb[i])?p-1:p++;
        if(p<tbc) dc3(rn,san,tbc,p);
        else for(i=0;i<tbc;i++) san[rn[i]]=i;
        for(i=0;i<tbc;i++) if(san[i]<tb) wb[ta++]=san[i]*3;
        if(n%3==1) wb[ta++]=n-1;
        sort(tokens,0,wb,wa,ta,m);
        for(i=0;i<tbc;i++) {
            wb[i]=G(san[i]);
            wv[wb[i]]=i;
        }
        for(i=0,j=0,p=0;i<ta && j<tbc;p++)
            sa[p]=c12(wb[j]%3,tokens,wa[i],wb[j])?wa[i++]:wb[j++];
        for(;i<ta;p++) sa[p]=wa[i++];
        for(;j<tbc;p++) sa[p]=wb[j++];
    }

    private static int calculateN(int length) {
        int n = 1;
        while (n<length){
            n = n*2;
        }
        return n+length>256?n+length:256;
    }

    public static int[] generateRank(int[] sa) {
        int len = sa.length;
        int[] rank = new int[len];
        for (int i = 0; i < len; i++) {
            rank[sa[i]] = i;
        }
        return rank;
    }

    public static int[] generateH(int[] ch, int[] sa,int fromSize) {
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
                for (k = (k != 0 ? k - 1 : 0), j = sa[rank[i] - 1]; (i + k < n) && (j + k < n) && ch[i + k] == ch[j + k]; k++) {
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

}