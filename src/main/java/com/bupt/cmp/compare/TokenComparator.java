/*
 * 文件名：		TokenComparator.java
 * 类名：		TokenComparator
 * 创建日期：	2011-02-20
 * 最近修改：	2013-05-04
 * 作者：		徐犇
 */

package com.bupt.cmp.compare;


import com.bupt.cmp.lang.Language;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;

import java.util.List;

/**
 * 执行令牌序列比对的类
 * 
 * @author ben
 */
public class TokenComparator extends Comparator {
	/**
	 * 对两个文件进行令牌序列比对，返回其相似度的值<br>
	 * 注意：参与比对的两个文件必须是采用本系统支持的编程语言编写，并能通过编译，否则会抛出异常； 暂不支持不同编程语言代码文件的比较(会返回相似度为0)
	 */
	/**
	 * 粒度
	 */
	private Granularity granularity = Granularity.LINE;

	/**
	 * 分块大小
	 */
	private int blocksize = 0;

	public TokenComparator() {

	}

	public TokenComparator(Granularity granularity, int blocksize) {
		this.granularity = granularity;
		this.blocksize = blocksize;
	}

	@Override
	public String toString() {
		return "令牌序列比对方法";
	}

	@Override
	/**
	 * 文件与文件比较
	 */
	public CompareResult compareAndRecord(DefaultCodeFile file1, DefaultCodeFile file2) throws Exception {
		// TODO Auto-generated method stub
		Language lang = file1.getLang();
		if (file2.getLang() != lang) {
			return null;
		}
		if (this.granularity == Granularity.BLOCK) {// 分块比对
			lang.getLineTokenSequence(file1);
			lang.getLineTokenSequence(file2);
			List<SimRecord> recordResultList = super.getSimRecordBlock(file1.getTokenLine(), file2.getTokenLine(),
					blocksize);// 对应相似行
			float simValue = super.getSimValue();// 相似度
			return new CompareResult(simValue,recordResultList);
		} else if (this.granularity == Granularity.MLCS) {// 匹配字符串比对
			lang.getLineTokenSequence(file1);
			lang.getLineTokenSequence(file2);
			List<SimRecord> recordResultList = super.getSimRecordMlcs(file1.getTokenLine(), file2.getTokenLine());// 对应相似行
			float simValue = super.getSimValue();// 相似度
			return new CompareResult(simValue,recordResultList);
		}
		return null;
	}


	/**
	 * 传入两个List比较
	 */
	public CompareResult compareAndRecord(List<LineStruct> first, List<LineStruct> second) throws Exception {
		// TODO Auto-generated method stub
		if (this.granularity == Granularity.BLOCK) {// 分块比对
			List<SimRecord> recordResultList = super.getSimRecordBlock(first, second, blocksize);// 对应相似行
			float simValue = super.getSimValue();// 相似度
			return new CompareResult(simValue,recordResultList);
		} else if (this.granularity == Granularity.MLCS) {// 匹配字符串比对
			List<SimRecord> recordResultList = super.getSimRecordMlcs(first, second);// 对应相似行
			float simValue = super.getSimValue();// 相似度
			return new CompareResult(simValue,recordResultList);
		}
		return null;
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
	public CompareResult compareAndRecordSyntax(List<StoreNode> list1, List<StoreNode> list2) throws Exception {
		return null;
	}

}
