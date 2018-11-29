package com.cmp.antlr.java;

import com.algorithm.suffixarray.SuffixArrayCore;
import com.algorithm.suffixarray.result.SuffixResult;
import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.compare.CompareDirector;
import com.cmp.compare.CompareResult;
import com.cmp.compare.Granularity;
import com.cmp.compare.JavaCompareTest;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LexicalParser {

    List<File> fileList = new LinkedList<File>();
    public static ICodeFactory icodeFactory = JavaCodeFactory.getInstance();
    CompareDirector mlcsd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TEXT_COMPARE);         //比对指示器1（文本比对）;
    CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);    //比对指示器2（Token比对）;
    CompareDirector syntaxd = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.SYNTAX_COMPARE);     //比对指示器3（语法树比对）

    private static DefaultCodeFile defaultCodeFile1;

    static {
        String src1 = JavaCompareTest.class.getClassLoader().getResource("language/java/JavaParserTest.java").getPath();
        defaultCodeFile1 = icodeFactory.generateDefectCodeFile(new File(src1));
    }
    @Test
    public void test1() throws IOException {
        File root = new File("E:\\code");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        DefaultCodeFile defaultCodeFile2 = icodeFactory.generateDefectCodeFile(file);
                        ModuleEntity moduleEntity1 = defaultCodeFile1.getClassBlocks().get(0);
                        List<Token> tokens1 = moduleEntity1.getTokenList();
                        if(defaultCodeFile2.getClassBlocks()==null||defaultCodeFile2.getClassBlocks().size()<=0){
//                            System.out.println("skip");
                        }else {
                            List<ModuleEntity> moduleEntityList = defaultCodeFile2.getClassBlocks();
                            for(ModuleEntity moduleEntity2:moduleEntityList){
                                List<Token> tokens2 = moduleEntity2.getTokenList();
                                SuffixResult suffixResult = SuffixArrayCore.compare(tokens1, tokens2);
                                if (suffixResult.getSimiValue() > 0.8) {
                                    System.out.println(file.getAbsolutePath());
                                }
                            }
                            atomicInteger.addAndGet((int) file.length());
                        }
                    }finally {
                        countDownLatch.countDown();
                    }
//                    FileInputStream is = null;
//                    try {
//                        is = new FileInputStream(file);
//                        CharStream inputStream = CharStreams.fromStream(is);
//                        JavaLexer lexer = new JavaLexer(inputStream);
//                        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//                        JavaParser parser = new JavaParser(tokenStream);
//                        ParseTree tree = parser.compilationUnit();
//                        System.out.println("");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        countDownLatch.countDown();
//                    }
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
        System.out.println("Integer:"+atomicInteger);
    }

    @Test
    public void tes2() throws IOException {
        File root = new File("E:\\code");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        DefaultCodeFile defaultCodeFile2 = icodeFactory.generateDefectCodeFile(file);
                        ModuleEntity moduleEntity1 = defaultCodeFile1.getMethods().get(0);
                        List<Token> tokens1 = moduleEntity1.getTokenList();
                        if(defaultCodeFile2.getMethods()==null||defaultCodeFile2.getMethods().size()<=0){
//                            System.out.println("skip");
                        }else {
                            List<ModuleEntity> moduleEntityList = defaultCodeFile2.getMethods();
                            for(ModuleEntity moduleEntity2:moduleEntityList){
                                List<Token> tokens2 = moduleEntity2.getTokenList();
                                SuffixResult suffixResult = SuffixArrayCore.compare(tokens1, tokens2);
                                if (suffixResult.getSimiValue() > 0.8) {
                                    System.out.println(file.getAbsolutePath());
                                }
                            }
                            atomicInteger.addAndGet((int) file.length());
                        }
                    }finally {
                        countDownLatch.countDown();
                    }
//                    FileInputStream is = null;
//                    try {
//                        is = new FileInputStream(file);
//                        CharStream inputStream = CharStreams.fromStream(is);
//                        JavaLexer lexer = new JavaLexer(inputStream);
//                        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//                        JavaParser parser = new JavaParser(tokenStream);
//                        ParseTree tree = parser.compilationUnit();
//                        System.out.println("");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        countDownLatch.countDown();
//                    }
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
        System.out.println("Integer:"+atomicInteger);
    }


    @Test
    public void tes3() throws IOException {
        File root = new File("E:\\code");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        DefaultCodeFile defaultCodeFile2 = icodeFactory.generateDefectCodeFile(file);
                        CompareResult compareResult = tokenmd.compare(defaultCodeFile1,defaultCodeFile2);
                        if(compareResult!=null&&compareResult.getSimvalue()>0.8){
                            System.out.println(file.getAbsolutePath());
                        }
                        defaultCodeFile2 = null;
                        atomicInteger.addAndGet((int) file.length());
                    }finally {
                        countDownLatch.countDown();
                    }
//                    FileInputStream is = null;
//                    try {
//                        is = new FileInputStream(file);
//                        CharStream inputStream = CharStreams.fromStream(is);
//                        JavaLexer lexer = new JavaLexer(inputStream);
//                        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
//                        JavaParser parser = new JavaParser(tokenStream);
//                        ParseTree tree = parser.compilationUnit();
//                        System.out.println("");
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }finally {
//                        countDownLatch.countDown();
//                    }
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
        System.out.println("Integer:"+atomicInteger);
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
