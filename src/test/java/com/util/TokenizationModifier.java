package com.util;

import com.cmp.LineStruct;
import com.cmp.antlr.java.JavaLexer;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.LinkedList;
import java.util.List;

public class TokenizationModifier {

    public static void tokenToLines(CommonTokenStream tokenStream) {
        int hashValue = 0;
        int tempHashValue = 0;
        int tempLineNum = 1;
        String lineString = new String();
        List<LineStruct> linetokenlist = new LinkedList<>();
        for (Token token : tokenStream.getTokens()) {
            int type = token.getType();
            if (token.getChannel() == JavaLexer.HIDDEN) {
                continue;
            }
            if (token.getLine() == tempLineNum) {
                lineString = lineString + " " + type;
            } else {
                hashValue = lineString.hashCode();
                if (hashValue != tempHashValue) {
                    LineStruct lineStruct = new LineStruct();
                    lineStruct.setLineNum(tempLineNum);
                    lineStruct.setHashValue(hashValue);
                    linetokenlist.add(lineStruct);
                    tempHashValue = hashValue;
                }
                lineString = " " + token.getText();
                tempLineNum = token.getLine();
            }
        }
    }
}
