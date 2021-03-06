// Generated from D:/Users/bearsmalll/IdeaProjects/Antlr4Learning/src/main/java/com/xy/demo3\LibExpr.g4 by ANTLR 4.7
package com.xy.sheet4.demo2;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LibExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LibExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LibExprParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(LibExprParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link LibExprParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(LibExprParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LibExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(LibExprParser.ExprContext ctx);
}