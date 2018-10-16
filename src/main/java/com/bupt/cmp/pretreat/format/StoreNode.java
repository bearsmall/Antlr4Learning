package com.bupt.cmp.pretreat.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 预处理-语法树信息类
 * @author Administrator
 *
 */
public class StoreNode implements Comparable<StoreNode>,Serializable {
	private static final long serialVersionUID = 864895592124318086L;
	private int beginLine;//开始行
	private int endLine;//结束行
	private long hashValue;//Hash值
	private long parentHashValue;//父节点hash值
	private int nodeNum;//节点数目
	private int parentNodeNum;//父节点数目
	private int mark;
    public StoreNode(){
    	
    }
	public StoreNode(int beginLine,int endLine,long hashValue,long parentHashVlaue,int nodeNum,int parentNodeNum){
		this.beginLine=beginLine;
		this.endLine=endLine;
		this.hashValue=hashValue;
		this.parentHashValue=parentHashVlaue;
		this.nodeNum=nodeNum;
		this.parentNodeNum=parentNodeNum;
		this.mark=0;
		
	}

	@Override
	public String toString(){
		return beginLine+":"+endLine+":"+hashValue+":"+parentHashValue+":"+nodeNum+":"+parentNodeNum;
	}
	//节点转换为String
	public static String StoreNodeToString(List<StoreNode> StoreNodeList){
		String arraystring=new String();
		for(int i=0;i<StoreNodeList.size();i++){
			  arraystring=arraystring+","+StoreNodeList.get(i).toString(); 
		  }		  
		  return arraystring.replaceAll(" ","");//替换掉字符串中的空格
		
	}
	//String转换为StoreNode
	public static List<StoreNode> StringToStoreNode(String StringNode){
		List<StoreNode> StoreNodeList=new ArrayList<StoreNode>();
		String[] array;
		if(StringNode!=null&&StringNode!="")
			array=StringNode.split(",");//行记录对数
		else
			return null;
		
		 for(int i=1;i<array.length;i++){
			 StoreNode temp=new StoreNode();
			 String[] t=array[i].split(":");
			 temp.setBeginLine(Integer.parseInt(t[0]));
			 temp.setEndLine(Integer.parseInt(t[1]));
			 temp.setHashValue(Long.parseLong(t[2]));
			 temp.setParentHashValue(Long.parseLong(t[3]));
			 temp.setNodeNum(Integer.parseInt(t[4]));
			 temp.setParentNodeNum(Integer.parseInt(t[5]));
			 temp.reMark();
			 StoreNodeList.add(temp); 
		 }
		return StoreNodeList;		
	}
	
	//比较函数
	@Override
	public int compareTo(StoreNode node) {
		if (this.nodeNum > node.nodeNum) {
			return -1;
		} else if (this.nodeNum < node.nodeNum) {
			return 1;
		} else {
			if (this.hashValue > node.hashValue) {
				return -1;
			} else if (this.hashValue < node.hashValue) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	public int getBeginLine() {
		return beginLine;
	}
	public void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}
	public int getEndLine() {
		return endLine;
	}
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}
	public long getHashValue() {
		return hashValue;
	}
	public void setHashValue(long hashValue) {
		this.hashValue = hashValue;
	}

	public long getParentHashValue() {
		return parentHashValue;
	}
	public void setParentHashValue(long parentHashValue) {
		this.parentHashValue = parentHashValue;
	}
	public int getNodeNum() {
		return nodeNum;
	}
	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}
	public int getParentNodeNum() {
		return this.parentNodeNum;
	}
	public void setParentNodeNum(int parentNodeNum) {
		this.parentNodeNum = parentNodeNum;
	}
	public int getMark() {
		return this.mark;
	}
	public void setMark() {
		this.mark = 1;
	}
	public void reMark() {
		this.mark = 0;
	}


}
