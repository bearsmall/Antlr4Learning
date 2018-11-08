package com.language.java;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.*;

public class LexicalParser {

    @Test
    public void test() throws IOException {
        File root = new File("E:\\src");
        Long start = System.currentTimeMillis();
        iteratorFile(root);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public void iteratorFile(File root) throws IOException {
        if(root.isDirectory()){
            File[] childrens = root.listFiles();
            for (File children:childrens){
                iteratorFile(children);
            }
        }else if (root.isFile()){
            if(root.getPath().endsWith(".java")){
//                Long start = System.currentTimeMillis();
                runFile(new FileInputStream(root));
//                Long end = System.currentTimeMillis();
//                System.out.println("parse file:"+root.getPath()+"\t spend "+(end-start));
            }
        }
    }

    public void runFile(InputStream is) throws IOException {
        CharStream inputStream = CharStreams.fromStream(is);
        JavaLexer lexer = new JavaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();
    }
}
