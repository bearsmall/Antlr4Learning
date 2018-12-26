package com.cmp.extract.demo1.function;

import com.cmp.utils.IOAgent;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class FunctionLevelCodeSimplifierTest {

    @Test
    public void test1(){
        FunctionLevelCodeSimplifier functionLevelCodeSimplifier = new FunctionLevelCodeSimplifier();
         File file = new File("src/test/resources/ExtractInterfaceListenerTest.java");
        String code = IOAgent.getInstance().getFileText(file);
        List<String> functions = functionLevelCodeSimplifier.getSimplifiedMethods(code);
        System.out.println(functions);
    }

}