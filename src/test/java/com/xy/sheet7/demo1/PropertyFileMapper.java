package com.xy.sheet7.demo1;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyFileMapper extends PropertyFileParser {
    Map<String,String> props = new LinkedHashMap<>();
    public PropertyFileMapper(TokenStream input) {
        super(input);
    }

    @Override
    void defineProperty(Token name, Token value) {
        props.put(name.getText(),value.getText());
    }

    public String getProperty(String name) {
        return props.get(name);
    }
}
