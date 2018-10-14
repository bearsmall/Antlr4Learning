// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/xy/sheet7/demo1\PropertyFile.g4 by ANTLR 4.7
package com.xy.sheet7.demo1;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PropertyFileParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PropertyFileVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PropertyFileParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(PropertyFileParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link PropertyFileParser#prop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProp(PropertyFileParser.PropContext ctx);
}