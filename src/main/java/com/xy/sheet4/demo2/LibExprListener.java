// Generated from D:/Users/bearsmalll/IdeaProjects/Antlr4Learning/src/main/java/com/xy/demo3\LibExpr.g4 by ANTLR 4.7
package com.xy.sheet4.demo2;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LibExprParser}.
 */
public interface LibExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LibExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(LibExprParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link LibExprParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(LibExprParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link LibExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(LibExprParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link LibExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(LibExprParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link LibExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(LibExprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link LibExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(LibExprParser.ExprContext ctx);
}