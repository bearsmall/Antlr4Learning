package com.xy.sheet7.demo1.listener;

import com.xy.sheet7.demo1.PropertyFileBaseListener;
import com.xy.sheet7.demo1.PropertyFileParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyFileLoader extends PropertyFileBaseListener {

    Map<String,String> props = new LinkedHashMap<>();

    @Override
    public void exitProp(PropertyFileParser.PropContext ctx) {
        String id = ctx.ID.getText();
        String value = ctx.STRING.getText();
        props.put(id,value);
    }

    public Map<String, String> getProps() {
        return props;
    }
}
