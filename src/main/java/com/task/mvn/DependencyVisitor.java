// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/task/mvn\Dependency.g4 by ANTLR 4.7
package com.task.mvn;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DependencyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DependencyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DependencyParser#depend}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDepend(DependencyParser.DependContext ctx);
	/**
	 * Visit a parse tree produced by {@link DependencyParser#dependList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDependList(DependencyParser.DependListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DependencyParser#dependItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDependItem(DependencyParser.DependItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link DependencyParser#dependentUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDependentUnit(DependencyParser.DependentUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link DependencyParser#versionUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVersionUnit(DependencyParser.VersionUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link DependencyParser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(DependencyParser.PackageNameContext ctx);
}