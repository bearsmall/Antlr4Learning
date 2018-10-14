package com.xy.sheet7.demo1.visitor;

import com.xy.sheet7.demo1.PropertyFileBaseVisitor;
import com.xy.sheet7.demo1.PropertyFileParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyFileVisitor extends PropertyFileBaseVisitor {
    Map<String,String> props = new LinkedHashMap<>();

    @Override
    public Object visitProp(PropertyFileParser.PropContext ctx) {
        String id = ctx.ID.getText();
        String value = ctx.STRING().getText();
        props.put(id,value);
        return null;//  注意，方法的返回值类型。
    }
    public Map<String, String> getProps() {
        return props;
    }
}
