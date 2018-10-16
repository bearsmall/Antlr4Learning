package com.xy.sheet7.demo1;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

public class PropertyFilePrinter extends PropertyFileParser {

    public PropertyFilePrinter(TokenStream input) {
        super(input);
    }

    @Override
    void defineProperty(Token name, Token value) {
        System.out.println(name.getText()+"="+value.getText());
    }

    @Override
    void startFile() {
        System.out.println("----startFile---");
    }

    @Override
    void finishFile() {
        System.out.println("-----finishFile----");
    }
}
