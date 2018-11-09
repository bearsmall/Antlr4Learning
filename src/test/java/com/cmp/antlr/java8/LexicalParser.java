package com.cmp.antlr.java8;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class LexicalParser {

    List<File> fileList = new LinkedList<File>();

    @Test
    public void test() throws IOException {
        File root = new File("/home/xiongy/code/src");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(32,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    FileInputStream is = null;
                    try {
                        is = new FileInputStream(file);
                        CharStream inputStream = CharStreams.fromStream(is);
                        Java8Lexer lexer = new Java8Lexer(inputStream);
                        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                        Java8Parser parser = new Java8Parser(tokenStream);
                        ParseTree tree = parser.compilationUnit();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            runFile(root);
        }
    }

    public void runFile(File file) throws IOException {
        if(file.getPath().endsWith(".java")){
            fileList.add(file);
        }
    }
}
