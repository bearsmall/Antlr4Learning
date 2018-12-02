// Generated from D:/Users/bearsmall/IdeaProjects/Antlr4Learning/src/main/java/com/task/mvn\Dependency.g4 by ANTLR 4.7
package com.task.mvn;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DependencyParser}.
 */
public interface DependencyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DependencyParser#depend}.
	 * @param ctx the parse tree
	 */
	void enterDepend(DependencyParser.DependContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#depend}.
	 * @param ctx the parse tree
	 */
	void exitDepend(DependencyParser.DependContext ctx);
	/**
	 * Enter a parse tree produced by {@link DependencyParser#dependList}.
	 * @param ctx the parse tree
	 */
	void enterDependList(DependencyParser.DependListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#dependList}.
	 * @param ctx the parse tree
	 */
	void exitDependList(DependencyParser.DependListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DependencyParser#dependItem}.
	 * @param ctx the parse tree
	 */
	void enterDependItem(DependencyParser.DependItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#dependItem}.
	 * @param ctx the parse tree
	 */
	void exitDependItem(DependencyParser.DependItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link DependencyParser#dependentUnit}.
	 * @param ctx the parse tree
	 */
	void enterDependentUnit(DependencyParser.DependentUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#dependentUnit}.
	 * @param ctx the parse tree
	 */
	void exitDependentUnit(DependencyParser.DependentUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link DependencyParser#versionUnit}.
	 * @param ctx the parse tree
	 */
	void enterVersionUnit(DependencyParser.VersionUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#versionUnit}.
	 * @param ctx the parse tree
	 */
	void exitVersionUnit(DependencyParser.VersionUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link DependencyParser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(DependencyParser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DependencyParser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(DependencyParser.PackageNameContext ctx);
}