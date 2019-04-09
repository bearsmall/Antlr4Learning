package com.cmp.antlr.java.statistic;

import com.algorithm.suffixarray.SuffixArrayCore;
import com.algorithm.suffixarray.result.SuffixResult;
import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.compare.CompareDirector;
import com.cmp.compare.CompareResult;
import com.cmp.compare.Granularity;
import com.cmp.compare.JavaCompareTest;
import com.cmp.factory.CCodeFactory;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LexicalParser {

    List<File> fileList = new LinkedList<File>();
    public static ICodeFactory icodeFactory = JavaCodeFactory.getInstance();
    public static Set<Integer> methodSet = new HashSet<>();

    @Test
    public void tes3() throws IOException {
        File root = new File("D:\\test\\simi\\repo");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicInteger fileBitSize = new AtomicInteger(0);
        AtomicInteger methodCount = new AtomicInteger(0);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        DefaultCodeFile defaultCodeFile = icodeFactory.generateDefectCodeFile(file);
                        fileBitSize.addAndGet((int) file.length());
                        if(defaultCodeFile!=null&&defaultCodeFile.getMethods()!=null&&defaultCodeFile.getMethods().size()>0) {
                            methodCount.addAndGet(defaultCodeFile.getMethods().size());
                            for (ModuleEntity moduleEntity : defaultCodeFile.getMethods()) {
                                methodSet.add(moduleEntity.getContent().hashCode());
                            }
                        }
                        defaultCodeFile = null;
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
        System.out.println("总耗时："+(end-start));
        System.out.println("源文件数量："+fileList.size());
        System.out.println("源文件大小：:"+fileBitSize);
        System.out.println("方法总个数：:"+methodCount);
        System.out.println("方法去重后：:"+methodSet.size());
    }


    public void render(File root) throws IOException {
        ArrayDeque<File> fileList = new ArrayDeque<>();
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
            File file = fileList.remove();
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
