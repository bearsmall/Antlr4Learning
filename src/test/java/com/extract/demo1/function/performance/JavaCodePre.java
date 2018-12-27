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

/**
 * 性能测试：
 * 1. 提取代码仓库中的源文件
 * 2. 提取出源文件中的函数
 * 3. 解析并分析函数（抽象化处理）
 * 4. 函数预处理结构映射
 * 5. 数据量统计、耗时分析
 * tips：
 * 1. 多线程
 * 2. 文件迭代 \ 非迭代遍历
 */
@Slf4j
public class JavaCodePre {
    List<File> fileList = new LinkedList<File>();//文件列表，size为源文件数量
    private AtomicLong line = new AtomicLong(0);//总代码行数
    private Multimap<Integer,Integer> multiMap = ArrayListMultimap.create(); //存储结构化map信息
    private long totalSize = 0; //文件总大小【字节为单位】

    @Test
    public void testMultiThreadPerformance() throws IOException {
        File root = new File("E:\\code");//样本源码文件路径
        Long start = System.currentTimeMillis();
        render(root);//提取路径下的源文件列表【递归or非递归】
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for (File file:fileList){//采用多线程方式加速处理速度
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        FunctionLevelCodeSimplifier functionLevelCodeSimplifier = new FunctionLevelCodeSimplifier();
                        String code = IOAgent.getInstance().getFileText(file);//源文件读取
                        int i = code.split("\\n").length;//代码行数
                        line.addAndGet(i);//总代码行数
                        List<String> functions = functionLevelCodeSimplifier.getSimplifiedMethods(code);//方法级别源码抽象
                        for(String function:functions){
                            multiMap.put(function.length(),function.hashCode());//将抽象出的函数块结构化存储【key -> value】{ key：函数长度；value：函数hash}
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
        //统计并输出
        log.info("total size:"+totalSize);
        log.info("total line:"+line.get());
        log.info("total spend: "+(end-start));
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
