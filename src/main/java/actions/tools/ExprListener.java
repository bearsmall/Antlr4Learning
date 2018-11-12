// Generated from D:/Users/bearsmalll/IdeaProjects/Antlr4Learning/src/main/java/actions/tools\Expr.g4 by ANTLR 4.7
package actions.tools;

import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(ExprParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(ExprParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#e}.
	 * @param ctx the parse tree
	 */
	void enterE(ExprParser.EContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#e}.
	 * @param ctx the parse tree
	 */
	void exitE(ExprParser.EContext ctx);
}