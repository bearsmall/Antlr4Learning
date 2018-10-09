package com.xy.demo1;

import com.xy.demo1.ArrayInitBaseListener;
import com.xy.demo1.ArrayInitParser;

public class ShortToUnicodeString extends ArrayInitBaseListener {
    /**
     * 将原本的“{ ”翻译为“《”
     * @param ctx
     */
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        System.out.print('《');
    }

    /**
     * 将原本的“}”翻译为“》”
     * @param ctx
     */
    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        System.out.print('》');
    }

    /**
     * 将每个整数翻译为四位的十六进制形式，然后加前缀\u【在此基础上用尖括号包围】
     * @param ctx
     */
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
