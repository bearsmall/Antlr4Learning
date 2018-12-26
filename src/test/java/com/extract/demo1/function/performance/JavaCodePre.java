package com.extract.demo1.function.performance;

import com.cmp.utils.IOAgent;
import com.extract.demo1.function.FunctionLevelCodeSimplifier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class JavaCodePre {
    List<File> fileList = new LinkedList<File>();
    private AtomicLong line = new AtomicLong(0);
    private Multimap<Integer,Integer> multiMap = ArrayListMultimap.create();
    private long totalSize = 0;

    @Test
    public void test() throws IOException {
        File root = new File("E:\\code");
        Long start = System.currentTimeMillis();
        render(root);
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        FunctionLevelCodeSimplifier functionLevelCodeSimplifier = new FunctionLevelCodeSimplifier();
                        String code = IOAgent.getInstance().getFileText(file);
                        int i = code.split("\\n").length;
                        line.addAndGet(i);
                        List<String> functions = functionLevelCodeSimplifier.getSimplifiedMethods(code);
                        for(String function:functions){
                            log.info(function);
                            multiMap.put(function.length(),function.hashCode());
                        }
                    } finally {
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
        System.out.println(totalSize);
        for (Integer key : multiMap.keySet()) {
            System.out.println(multiMap.get(key));
        }
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
            totalSize+=file.length();
            fileList.add(file);
        }
    }
}
