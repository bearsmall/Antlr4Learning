package com.cmp.compare;

import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.StoreNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 抽象比较器
 * Created by bearsmall on 2017/9/25.
 */
public abstract class Comparator {
    //记录比较后的相似度
    private float simValue;//相似度

    public float getSimValue() {
        return simValue;
    }

    public void setSimValue(float simValue) {
        this.simValue = simValue;
    }

    /**
     * 对两个文件进行比较，并记录具体的相似信息。需要派生类实现具体功能
     * @param file1 参与比对的第一个文件
     * @param file2 参与比对的第二个文件
     * @return 比对结果对象
     * @throws Exception
     */
    public abstract CompareResult compareAndRecord(DefaultCodeFile file1, DefaultCodeFile file2) throws Exception;

    /**
     * 两个List进行比对
     * @param list1，参与比对的第一个List<LineStruct>
     * @param list2，参与比对的第二个List<LineStruct>
     * @return
     * @throws Exception
     */
    public abstract CompareResult compareAndRecord(List<LineStruct> list1, List<LineStruct> list2) throws Exception;

    /**
     * 两个List进行比对
     * @param list1，参与比对的第一个List<LineStruct>
     * @param list2，参与比对的第二个List<LineStruct>
     * @return
     * @throws Exception
     */
    public abstract CompareResult compareAndRecordSyntax(List<StoreNode> list1, List<StoreNode> list2) throws Exception;

    /**
     *
     * 可优化
     * 分块比对
     * 按块大小比较两个整型序列，并记录相似对
     * @param list1
     * 参与比较的第一个序列
     * @param list2
     * 参与比较的第二个序列
     * @return List<SimRecord> 记录相似对结果类，数组
     */
    public  List<SimRecord> getSimRecordBlock(List<LineStruct> list1, List<LineStruct> list2, int blocksize){
        List<SimRecord> retRecord=new ArrayList<SimRecord>();
        if(list1==null||list2==null){
            simValue=0;
            return retRecord;
        }
        int m = list1.size();
        int n = list2.size();
        int[][] s=new int[m][n];//记录对应每行是否都有数
        int[] sim=new int[m];//用于计算相似度
        int[] aFlag=new int[blocksize];
        int[] bFlag=new int[blocksize];

        float ans=0;//记录相似行数
		/*
		 * 记录两个序列比对结果矩阵，初始化
		 */

        for(int i=0;i<m;i++) {Arrays.fill(s[i], 0);}

		/*
		 * 比较两个整型序列，并将结果记录在s[][]中
		 */
        for (int i = 0; i <m; i++) {
            for (int j = 0; j <n; j++) {
                if(list1.get(i).getHashValue()==list2.get(j).getHashValue()){
                    s[i][j]=1;
                    sim[i]=1;

                }
            }
        }
        //按分块大小匹配
        for (int i = 0; i <=m-blocksize; i++) {
            for (int j = 0; j <=n-blocksize; j++) {
                Arrays.fill(aFlag, 0);
                Arrays.fill(bFlag, 0);
                for(int a=0;a<blocksize;a++){//判断分块中每一行每一列是否有一个1
                    for(int b=0;b<blocksize;b++){
                        aFlag[a]=aFlag[a]+s[i+a][j+b];
                        bFlag[b]=bFlag[b]+s[i+a][j+b];

                    }
                }
                if(JudgeZero(aFlag,blocksize)&&JudgeZero(bFlag,blocksize)){//找到一个相似的块
                    SimRecord tempRecord=new SimRecord(blocksize);
                    tempRecord.setSrcLine(list1.get(i).getLineNum(), list1.get(i+blocksize-1).getLineNum());
                    tempRecord.setTrgLine(list2.get(j).getLineNum(), list2.get(j+blocksize-1).getLineNum());
                    retRecord.add(tempRecord);
                    Arrays.fill(aFlag, 0);
                    Arrays.fill(bFlag, 0);
                    for(int a=0;a<blocksize;a++){//为避免重复记录，将相似块的记录置0
                        for(int b=0;b<blocksize;b++){
                            s[i+a][j+b]=0;
                        }
                    }
                    j=j+blocksize-1;
                }
                else{
                    continue;
                }

            }
        }
        for(int i=0;i<m;i++) {
            if (sim[i] != 0) {
                ans++;//相似行数
            }
        }
        simValue=ans/m;//计算相似度
        return retRecord;

    }

    /**
     * 可优化
     * MLCS
     *
     * 按串匹配比较两个整型序列，并记录相似对
     * @param list1
     * 参与比较的第一个序列
     * @param list2
     * 参与比较的第二个序列
     * @return List<SimRecord> 记录相似对结果类，数组
     */
    public  List<SimRecord> getSimRecordMlcs(List<LineStruct> list1, List<LineStruct> list2){
        List<SimRecord> retRecord=new ArrayList<SimRecord>();
        if(list1==null || list2==null){
            simValue = 0;
            return retRecord;
        }
        boolean reverFlag = false;
        int m = list1.size();
        int n = list2.size();
		if (m>n){
			List<LineStruct> lin = list2;
			list2 = list1;
			list1 = lin;
			int temp = n;
			n = m;
			m = temp;
			reverFlag = true;
		}
        int[] c = new int[n+1];
        int[] sim = new int[m];//用于计算相似度
        float ans=0;//记录相似行数
        int templen=0;//临时记录相似串长度
        Arrays.fill(c, 0);
        Arrays.fill(sim, 0);

        for (int i = 0; i <m; i++) {
            for (int j =1; j <n+1; j++) {
                if(i==0){//目标文件第一个字从头比较
                    if (list1.get(i).getHashValue() == list2.get(j-1).getHashValue()) {
                        c[j]=1;
                        sim[i]=1;
                    }
                    else{
                        c[j]=0;
                    }
                }
                else{//目标文件除了第一个字的其他字从尾比较
                    if(list1.get(i).getHashValue() == list2.get(n-j).getHashValue()){//相同
                        sim[i] = 1;
                        c[n-j+1] = c[n-j]+1;
                        if(c[n-j]!=0) {
                            j = j + c[n - j] - 1;
                        }
                    }
                    else if(c[n-j]!=0){//不相同且前一位不为0，相同字符串结束
                        if(c[n-j]>3){//相似串长度超过3才进行记录
                            SimRecord tempRecord=new SimRecord();
                            if(reverFlag){
                                tempRecord.setTrgLine(list1.get(i-c[n-j]).getLineNum(),list1.get(i-1).getLineNum());//记录样本文件起止行号，行号从1开始
                                tempRecord.setSrcLine(list2.get(n-j-c[n-j]).getLineNum(), list2.get(n-j-1).getLineNum());
                            }else {
                                tempRecord.setSrcLine(list1.get(i-c[n-j]).getLineNum(),list1.get(i-1).getLineNum());
                                tempRecord.setTrgLine(list2.get(n-j-c[n-j]).getLineNum(), list2.get(n-j-1).getLineNum());//记录样本文件起止行号，行号从1开始
                            }
                            retRecord.add(tempRecord);
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
                SimRecord tempRecord=new SimRecord();
                if(reverFlag){
                    tempRecord.setTrgLine(list1.get(m-c[i]).getLineNum(),list1.get(m-1).getLineNum());//记录样本文件起止行号，行号从1开始
                    tempRecord.setSrcLine(list2.get(i-c[i]).getLineNum(), list2.get(i-1).getLineNum());
                }else {
                    tempRecord.setSrcLine(list1.get(m-c[i]).getLineNum(),list1.get(m-1).getLineNum());
                    tempRecord.setTrgLine(list2.get(i-c[i]).getLineNum(), list2.get(i-1).getLineNum());//记录样本文件起止行号，行号从1开始
                }
                retRecord.add(tempRecord);
            }
            i=i-c[i];
        }
        for(int i=0;i<m;i++)if(sim[i]!=0)ans++;//相似行数
        //System.out.println(m+"-----"+n+"---------"+ans);
        if(reverFlag) {
            simValue = ans / m;
        }else {
            simValue = ans / n;
        }

        return retRecord;

    }

    public List<SimRecord> getSimRecord(List<LineStruct> list1,List<LineStruct> list2){

        return null;
    }
    /**
     * 强制子类重写toString方法
     */
    @Override
    public abstract String toString();

    public  boolean JudgeZero(int A[] , int blocksize){ //判断是否有0
        //判断每行是否都有数
        for(int i = 0 ; i < blocksize ; i++){
            if(A[i] != 0) {
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }

    public boolean IsEmptyLine(int[] list,int length,int start ){
        for(int i=start;i<start+length;i++){
            if(list[i]!=0){
                return true;
            }
        }
        return false;

    }
}
