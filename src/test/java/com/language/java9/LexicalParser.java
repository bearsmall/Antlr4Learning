package com.language.java9;

import com.language.java8.Java8Lexer;
import com.language.java8.Java8Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexicalParser {

    @Test
    public void test() throws IOException {
        File root = new File("/home/xiongy/src");
        Long start = System.currentTimeMillis();
        render(root);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }


    public void render(File root) throws IOException {
        List<File> fileList = new ArrayList<>();
        if(root.isDirectory()){
            File[] files = root.listFiles();
            for(File f:files){
                if(f.isDirectory()){
                    fileList.add(f);
                }else {
                    runFile(f);
                }
            }
        }else {
            runFile(root);
        }
        while (!fileList.isEmpty()){
            File file = fileList.remove(0);
            for (File f : file.listFiles()) {
                if(f.isDirectory()){
                    fileList.add(f);
                }else{
                    runFile(f);
                }
            }
        }
    }

    public void iteratorFile(File root) throws IOException {
        if(root.isDirectory()){
            File[] childrens = root.listFiles();
            for (File children:childrens){
                iteratorFile(children);
            }
        }else if (root.isFile()){
//                Long start = System.currentTimeMillis();
            runFile(root);
//                Long end = System.currentTimeMillis();
//                System.out.println("parse file:"+root.getPath()+"\t spend "+(end-start));
        }
    }

    public void runFile(File file) throws IOException {
        if(file.getPath().endsWith(".java")){
            FileInputStream is = new FileInputStream(file);
            CharStream inputStream = CharStreams.fromStream(is);
            Java9Lexer lexer = new Java9Lexer(inputStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            Java9Parser parser = new Java9Parser(tokenStream);
            ParseTree tree = parser.compilationUnit();
        }
    }
}
