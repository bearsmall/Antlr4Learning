package com.task.mvn;

public class MyDependencyVisitor extends DependencyBaseVisitor {
    @Override
    public Object visitDependentUnit(DependencyParser.DependentUnitContext ctx) {
        System.out.println(ctx.packageName().get(0).getText()+":"+ctx.packageName().get(1).getText()+":"+ctx.versionUnit().getText());
        return super.visitDependentUnit(ctx);
    }
}
