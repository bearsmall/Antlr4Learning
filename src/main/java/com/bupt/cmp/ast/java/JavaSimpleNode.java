/*
 * 类名：		JavaSimpleNode
 * 创建日期：	2010/11/18
 * 最近修改：	2010/11/18
 * 作者：		徐犇
 */
package com.bupt.cmp.ast.java;


import com.bupt.cmp.ast.SimpleNodeJava;

/**
 * 将Node接口和SimpleNode类独立放到一个包里，不同语言的node类再继承自SimpleNode即可使语法树比对函数统一化
 * 
 * @author ben
 * 
 */
public class JavaSimpleNode extends SimpleNodeJava {

	protected JavaTreeParser parser;

	public JavaSimpleNode(int i) {
		super(i);
	}

	public JavaSimpleNode(JavaTreeParser p, int i) {
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
		return JavaTreeParserTreeConstants.jjtNodeName[id] + "(" + nodenum
				+ ", " + hashvalue + ")";
	}

	public String toString(String prefix) {
		return prefix + toString();
	}
}
