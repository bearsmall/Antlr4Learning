package com.cmp.compare;

import java.util.Arrays;
import java.util.List;

/*
 * 记录相似的代码段信息
 */
public class SimRecord {
	private int[] srcLine;//源文件开始行、结束行
	private int[] trgLine;//比对库文件开始行、结束行
	private int block;//块大小
	public SimRecord(){//记录检测文件与样本文件中相似行的行号
		this.block=0;
		srcLine=new int[2];//检测文件的起止行，srcLine[0]记录起始行，srcLine[1]记录终止行
		trgLine=new int[2];//样本文件的起止行，trgLine[0]记录起始行，trgLine[1]记录终止行
	}
	public SimRecord(int block){
		this.block=block;
		srcLine=new int[2];//记录与目标块相似的行
		trgLine=new int[2];//trgLine[0]记录起始行，trgLine记录终止行
	}
	public int getBlock() {
		return block;
	}
	public void setBlock(int block) {
		this.block = block;
	}
	public void setSrcLine(int start,int end){
			srcLine[0]=start;
			srcLine[1]=end;
		
	}
	public void setTrgLine(int start,int end){
		trgLine[0]=start;
		trgLine[1]=end;
	}
	public int[] getSrcLine(){
		return srcLine;
	}
	public int[] getTrgLine(){
		return trgLine;
	}
	public String toString(){
		return Arrays.toString(srcLine)+"&"+Arrays.toString(trgLine);
		
	}

	/**
	 * 比对结果格式化字符串信息转换为比对记录结果集
	 * @param recordString
	 * @return
	 */
	public static SimRecord[] StringtoSimRecord(String recordString){
		if(recordString!=null){
			String[] array=recordString.split("#");//相似记录对数
			SimRecord[] simrecord=new SimRecord[array.length-1];
			  for(int i=0;i<array.length-1;i++){
				  String[] st=array[i+1].split("&");
				  String[] sline=st[0].replaceAll("\\[|]", "").split(",");//源文件相似记录行号
				  simrecord[i]=new SimRecord(Integer.parseInt(sline[1])-Integer.parseInt(sline[0])+1);//分块大小
			
				  simrecord[i].setSrcLine(Integer.parseInt(sline[0]),Integer.parseInt(sline[1]));
				  String[] tline=st[1].replaceAll("\\[|]", "").split(",");//样本文件相似记录行号
				  simrecord[i].setTrgLine(Integer.parseInt(tline[0]), Integer.parseInt(tline[1]));
			  }
			return simrecord;
		}
		else 
			return null;
	}

	/**
	 * 比对记录结果集格式化为字符串信息
	 * @param simlist
	 * @return
	 */
	public static String SimRecordtoString(SimRecord[] simlist){
		String arraystring=new String();
		for(int i=0;i<simlist.length;i++){
			  arraystring=arraystring+"#"+simlist[i].toString(); 
		  }		  
		  return arraystring.replaceAll(" ","");//替换掉字符串中的空格
		
	}

	/**
	 * 比对记录结果集格式化为字符串信息
	 * @param simlist
	 * @return
	 */
	public static String SimRecordtoString(List<SimRecord> simlist){
		String arraystring=new String();
		for(int i=0;i<simlist.size();i++){
			  arraystring=arraystring+"#"+simlist.get(i).toString(); 
		  }		  
		  return arraystring.replaceAll(" ","");//替换掉字符串中的空格
		
	}
	
}
