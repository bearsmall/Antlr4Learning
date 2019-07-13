package com.fileread;

import com.algorithm.suffixarray.SuffixArrayCore;
import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.ModuleEntity;
import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.cmp.compare.CompareDirector;
import com.cmp.compare.CompareResult;
import com.cmp.compare.Granularity;
import com.cmp.compare.JavaCompareTest;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import com.cmp.listener.ChangeToLambdaExtractListener;
import com.cmp.listener.LambdaExtractListener;
import com.cmp.utils.IOAgent;
import com.util.FileExtractor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class SimiCompare {
    public static ICodeFactory icodeFactory;
    public static DefaultCodeFile df;
    CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);    //比对指示器2（Token比对）;
    static {
        icodeFactory = JavaCodeFactory.getInstance();
        String src1 = SimiCompare.class.getClassLoader().getResource("language/java/test/BeanDefinitionLoader.java").getPath();
        df =  icodeFactory.generateDefectCodeFile(new File(src1));
    }

    @Test
    public void testTraditional() {
//        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        int totalFiles = fileList.size();
        AtomicLong totalCompareTime = new AtomicLong(0);
        for (File file:fileList){
            executorService.execute(() -> {
                try {
                    DefaultCodeFile df2 = icodeFactory.generateDefectCodeFile(file);
                    long s = System.currentTimeMillis();
                    tokenmd.compare(df,df2);
                    long e = System.currentTimeMillis();
                    totalCompareTime.getAndAdd((e-s));
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        log.info("Parse Spend:"+(end-middle));
        log.info("Total File Numbers:"+totalFiles);
        log.info("Total Compare Spend:"+totalCompareTime);
    }

/**
 SimiCompare[spring-boot]
 19:33:48.139 [main] INFO com.fileread.SimiCompare - Parse Spend:96280
 19:33:48.139 [main] INFO com.fileread.SimiCompare - Total File Numbers:4673
 19:33:48.139 [main] INFO com.fileread.SimiCompare - Total Compare Spend:1471896

 BeanDefinitionLoader[spring-boot]
 19:57:57.916 [main] INFO com.fileread.SimiCompare - Parse Spend:45537
 19:57:57.916 [main] INFO com.fileread.SimiCompare - Total File Numbers:4673
 19:57:57.917 [main] INFO com.fileread.SimiCompare - Total Compare Spend:561013
 */

    @Test
    public void testNewSuffixArray() {
//        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        int totalFiles = fileList.size();
        AtomicLong totalCompareTime = new AtomicLong(0);
        for (File file:fileList){
            executorService.execute(() -> {
                try {
                    DefaultCodeFile df2 = icodeFactory.generateDefectCodeFile(file);
                    long s = System.currentTimeMillis();
                    if(file.getAbsolutePath().endsWith("BeanDefinitionLoader.java")){
                        System.out.println("hello");
                    }
                    SuffixArrayCore.calculateTokenSimi(df.getTokenList(),df2.getTokenList());
                    long e = System.currentTimeMillis();
                    totalCompareTime.getAndAdd((e-s));
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        log.info("Parse Spend:"+(end-middle));
        log.info("Total File Numbers:"+totalFiles);
        log.info("Total Compare Spend:"+totalCompareTime);
    }
/**
 SimiCompare[spring-boot]
 19:41:50.361 [main] INFO com.fileread.SimiCompare - Parse Spend:11987
 19:41:50.361 [main] INFO com.fileread.SimiCompare - Total File Numbers:4673
 19:41:50.361 [main] INFO com.fileread.SimiCompare - Total Compare Spend:36146

 BeanDefinitionLoader[spring-boot]
 20:00:10.962 [main] INFO com.fileread.SimiCompare - Parse Spend:16383
 20:00:10.962 [main] INFO com.fileread.SimiCompare - Total File Numbers:4673
 20:00:10.962 [main] INFO com.fileread.SimiCompare - Total Compare Spend:9944

 BeanDefinitionLoader[all]
 20:05:00.948 [main] INFO com.fileread.SimiCompare - Parse Spend:190949
 20:05:00.948 [main] INFO com.fileread.SimiCompare - Total File Numbers:50495
 20:05:00.948 [main] INFO com.fileread.SimiCompare - Total Compare Spend:60111
 */
}
