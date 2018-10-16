/*
 * 文件名：		Comparator.java
 * 类名：		Comparator
 * 创建日期：	2009/10/15
 * 最近修改：	2011/02/26
 * 作者：		徐犇
 */

package com.bupt.cmp.compare;

import com.bupt.cmp.ast.cpp.ParseException;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.StoreNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 比对器,提供源文件比对的方法
 * 
 * @author ben
 */
public class CompareMethod {

	/**
	 * 比较两棵语法树，传入参数为两个预处理列表
	 * 
	 * @param list1
	 * @param list2
	 * @return 相似度值
	 * @throws ParseException
	 */
	public CompareResult compareTwoTree(List<StoreNode> list1, List<StoreNode> list2) throws Error, ParseException {

		List<SimRecord> simlist = new ArrayList<SimRecord>();

		if (list1 == null || list2 == null||list1.size()==0||list2.size()==0) {
			simlist = null;
			return new CompareResult((float) 0.0,simlist);
		}


		/**
		 * 控制比较的层数，让大型程序比较的时候忽略某些低层的结点而只关心比较高层的结点
		 */
		int limit = list1.get(0).getNodeNum() > list2.get(0).getNodeNum() ?
				list1.get(0).getNodeNum() : list2.get(0).getNodeNum();
		limit /= 30;

		int len1 = list1.size();
		int len2 = list2.size();

		int equalNodeNum = 0;

		/**
		 * 两树树根相同，则两树完全相同
		 */
		if (list1.get(0).compareTo(list2.get(0)) == 0) {
			SimRecord temp = new SimRecord();
			temp.setSrcLine(list1.get(0).getBeginLine(), list1.get(0).getEndLine());
			temp.setTrgLine(list2.get(0).getBeginLine(), list2.get(0).getEndLine());
			simlist.add(temp);
			return new CompareResult((float) 1.0,simlist);
		}

		int i = 0, j = 0;
		int k = 0;                  //临时变量，避免计算相似度时重复计算问题
		int temp_j = 0;             //存放j的临时变量
		for (; i < len1 && j < len2;) {
			/**
			 * 到达层数限制时停止比较，跳过叶子节点
			 */
			if (list1.get(i).getNodeNum() < limit
					|| list2.get(j).getNodeNum() < limit||list1.get(i).getNodeNum() <=1
					|| list2.get(j).getNodeNum() <=1) {
				break;
			}

			/**
			 * 过滤掉父结点已经相同的结点的比较，防止出现大量无意义的相同结点
			 */
			if (list1.get(i).getParentHashValue() != 0 && list2.get(j).getParentHashValue() != 0 && list1.get(i).getParentHashValue()==list2.get(j).getParentHashValue()
					&&list1.get(i).getParentNodeNum()==list2.get(j).getParentNodeNum()) {
				i++;
				j++;
				k = 0;
				//相同节点加1
//				equalNodeNum +=1;
				list1.get(i).setMark();
				continue;
			}
			//比较两个节点
			if (list1.get(i).compareTo(list2.get(j)) < 0) {
				i++;
				if(k!=0){
					j = temp_j;
				}
				k = 0;
			} else if (list1.get(i).compareTo(list2.get(j)) > 0) {
				j++;
			} else {//节点相等
				SimRecord temp = new SimRecord();
				if(k==0){
					temp_j = j;
					k++;
				}
				temp.setSrcLine(list1.get(i).getBeginLine(), list1.get(i).getEndLine());
				temp.setTrgLine(list2.get(j).getBeginLine(), list2.get(j).getEndLine());
				simlist.add(temp);
				if(k == 1 && list1.get(i).getMark() == 0){
					//相同节点加1
					equalNodeNum +=list1.get(i).getNodeNum();
				}
				list1.get(i).setMark();
				k++;
				j++;
			}
		}
		float ans = equalNodeNum;
		ans /= (float) (list1.get(0).getNodeNum());
		if(ans>1)ans=1;

		/*
		 * 以下为新修改的代码，用以恢复SimpleNode中的mark变量
		 */
		for(i=0;i<list1.size();i++) {
			list1.get(i).reMark();
		}
		/*
		 * 以上为新修改的代码，用以恢复SimpleNode中的mark变量
		 */

		return new CompareResult(ans,simlist);
	}
	/**
	 * 比较两棵语法树，传入参数为两个文件
	 *
	 * @param file1
	 * @param file2
	 * @return 相似度值
	 * @throws ParseException
	 */
	public CompareResult compareTwoTree(DefaultCodeFile file1, DefaultCodeFile file2) throws Error, ParseException {

		List<SimRecord> simlist = new ArrayList<SimRecord>();

		List<StoreNode> list1 = file1.getTree();
		List<StoreNode> list2 = file2.getTree();

		if (list1 == null || list2 == null||list1.size()==0||list2.size()==0) {
			simlist = null;
			return new CompareResult((float) 0.0,simlist);
		}


		/**
		 * 控制比较的层数，让大型程序比较的时候忽略某些低层的结点而只关心比较高层的结点
		 */
		int limit = list1.get(0).getNodeNum() > list2.get(0).getNodeNum() ?
				list1.get(0).getNodeNum() : list2.get(0).getNodeNum();
		limit /= 30;

		int len1 = list1.size();
		int len2 = list2.size();

		int equalNodeNum = 0;

		/**
		 * 两树树根相同，则两树完全相同
		 */
		if (list1.get(0).compareTo(list2.get(0)) == 0) {
			SimRecord temp = new SimRecord();
			temp.setSrcLine(list1.get(0).getBeginLine(), list1.get(0).getEndLine());
			temp.setTrgLine(list2.get(0).getBeginLine(), list2.get(0).getEndLine());
			simlist.add(temp);
			return new CompareResult((float) 1.0,simlist);
		}

		int i = 0, j = 0;
		int k = 0;                  //临时变量，避免计算相似度时重复计算问题
		int temp_j = 0;             //存放j的临时变量
		for (; i < len1 && j < len2;) {
			/**
			 * 到达层数限制时停止比较，跳过叶子节点
			 */
			if (list1.get(i).getNodeNum() < limit
					|| list2.get(j).getNodeNum() < limit||list1.get(i).getNodeNum() <=1
					|| list2.get(j).getNodeNum() <=1) {
				break;
			}

			/**
			 * 过滤掉父结点已经相同的结点的比较，防止出现大量无意义的相同结点
			 */
			if (list1.get(i).getParentHashValue() != 0 && list2.get(j).getParentHashValue() != 0 && list1.get(i).getParentHashValue()==list2.get(j).getParentHashValue()
					&&list1.get(i).getParentNodeNum()==list2.get(j).getParentNodeNum()) {
				i++;
				j++;
				k = 0;
				//相同节点加1
//				equalNodeNum +=1;
				list1.get(i).setMark();
				continue;
			}
			//比较两个节点
			if (list1.get(i).compareTo(list2.get(j)) < 0) {
				i++;
				if(k!=0){
					j = temp_j;
				}
				k = 0;
			} else if (list1.get(i).compareTo(list2.get(j)) > 0) {
				j++;
			} else {//节点相等
				SimRecord temp = new SimRecord();
				if(k==0){
					temp_j = j;
					k++;
				}
				temp.setSrcLine(list1.get(i).getBeginLine(), list1.get(i).getEndLine());
				temp.setTrgLine(list2.get(j).getBeginLine(), list2.get(j).getEndLine());
				simlist.add(temp);
				if(k == 1 && list1.get(i).getMark() == 0){
					//相同节点加1
					equalNodeNum +=list1.get(i).getNodeNum();
				}
				list1.get(i).setMark();
				k++;
				j++;
			}
		}
		float ans = equalNodeNum;
		ans /= (float) (list1.get(0).getNodeNum());
		if(ans>1)ans=1;

		/*
		 * 以下为新修改的代码，用以恢复SimpleNode中的mark变量
		 */
		for(i=0;i<list1.size();i++) {
			list1.get(i).reMark();
		}
		/*
		 * 以上为新修改的代码，用以恢复SimpleNode中的mark变量
		 */

		return new CompareResult(ans,simlist);
	}


}
