/*
 * 文件名：		SyntaxComparator.java
 * 类名：		SyntaxComparator
 * 创建日期：	2011-02-20
 * 最近修改：	2013-03-31
 * 作者：		徐犇
 */

package com.cmp.compare;

import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.StoreNode;

import java.util.List;

/**
 * 执行语法树比对操作的类
 * 
 * @author ben
 * 
 */
public class SyntaxComparator extends Comparator {

	/**
	 * 对Java语言代码文件进行语法树比对
	 *
	 * @param file1
	 * @param file2
	 * @return 相似度值
	 * @throws Exception
	 */
	@Override
	public CompareResult compareAndRecord(DefaultCodeFile file1, DefaultCodeFile file2)throws Exception {
		CompareMethod cmpTree = new CompareMethod();
		CompareResult result = cmpTree
				.compareTwoTree(file1, file2);
		return result;
	}

	/**
	 * 两个List进行比对
	 *
	 * @param list1
	 * @param list2
	 * @return
	 * @throws Exception
	 */
	@Override
	public CompareResult compareAndRecord(List<LineStruct> list1, List<LineStruct> list2) throws Exception {
		return null;
	}

	/**
	 * 对Java语言代码文件进行语法树比对
	 *
	 * @param file1
	 * @param file2
	 * @return 相似度值
	 * @throws Exception
	 */
	@Override
	public CompareResult compareAndRecordSyntax(List<StoreNode> file1, List<StoreNode> file2) throws Exception {
		CompareMethod cmpTree = new CompareMethod();
		CompareResult result = cmpTree.compareTwoTree(file1, file2);
		return result;
	}

	@Override
	public String toString() {
		return "语法树比对方法";
	}

}