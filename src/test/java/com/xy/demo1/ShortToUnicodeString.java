package com.xy.demo1;

import com.xy.demo1.ArrayInitBaseListener;
import com.xy.demo1.ArrayInitParser;

public class ShortToUnicodeString extends ArrayInitBaseListener {
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        System.out.print('《');
    }

    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        System.out.print('》');
    }

    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        System.out.print("<");
        int value = Integer.valueOf(ctx.INT().getText());
        System.out.printf("\\u%04x",value);
    }

    @Override
    public void exitValue(ArrayInitParser.ValueContext ctx) {
        System.out.print(">");
    }
}
