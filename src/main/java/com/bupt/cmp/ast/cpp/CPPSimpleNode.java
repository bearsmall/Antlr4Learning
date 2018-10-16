package com.bupt.cmp.ast.cpp;


import com.bupt.cmp.ast.SimpleNode;

/**
 * 将Node接口和SimpleNode类独立放到一个包里，不同语言的node类再继承自SimpleNode即可使语法树比对函数统一化
 * 
 * @author ben
 * 
 */
public class CPPSimpleNode extends SimpleNode {

	protected CPPTreeParser parser;

	public CPPSimpleNode(int i) {
		super(i);
	}

	public CPPSimpleNode(CPPTreeParser p, int i) {
		super(i);
		parser = p;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */


	public String toString() {
		return CPPTreeParserTreeConstants.jjtNodeName[id] + "(" + nodenum
				+ ", " + hashvalue + ")";
	}

	public String toString(String prefix) {
		return prefix + toString();
	}
}