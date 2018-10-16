package com.bupt.cmp.parser.c;

import java.util.ArrayList;

public class EG1Result {
	/**
	 * 存储代码文件的Token序列,以"\n"分隔
	 */
	protected String tokenSequence;

	/**
	 * 存储代码文件的类序列
	 */
	protected String[] classSequence;

	/**
	 * 存储代码文件(应该为一个类)的函数序列
	 */
	protected String[] funSequence;

	public String[] getFunSequence() {
		return this.funSequence;
	}

	/**
	 * 返回java文件的类
	 * 
	 * @return 字符串数组，数组中每个元素为一个类的代码文本
	 */
	public String[] getClassSequence() {
		return this.classSequence;
	}

	public EG1Result() {
		this.tokenSequence = new String();
		this.funSequence = null;
		this.classSequence = null;
	}

	public EG1Result(String tokenSequence) {
		this.tokenSequence = tokenSequence;
	}

	public EG1Result(String tokenSequence, ArrayList<String> classSequence, ArrayList<String> funSequence) {
		this.tokenSequence = tokenSequence;
		int clen = classSequence.size();
		if (clen > 0) {
			this.classSequence = new String[clen];
			for (int i = 0; i < clen; i++) {
				this.classSequence[i] = classSequence.get(i);
			}
		} else {
			this.classSequence = null;
		}
		
		int flen = funSequence.size();
		if(flen > 0){
			this.funSequence = new String[flen];
			for(int i = 0; i < flen; i++) {
				this.funSequence[i] = funSequence.get(i);
			}
		} else {
			this.funSequence = null;
		}
	}

	/**
	 * 
	 * @return 返回代码文件的Token序列
	 */
	public String getTokenSequence() {
		return this.tokenSequence;
	}

}
