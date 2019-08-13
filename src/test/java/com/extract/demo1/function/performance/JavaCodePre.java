package com.extract.demo1.function.performance;

import com.cmp.utils.IOAgent;
import com.extract.demo1.function.FunctionLevelCodeSimplifier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.tool.DirMultiThreadTasker;
import com.tool.SingleFileTask;
import com.tool.TaskResult;
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
        String[] suffix = new String[]{".java"};
        DirMultiThreadTasker dirMultiThreadTasker = new DirMultiThreadTasker(suffix);
        Multimap<Integer,Integer> multiMap = ArrayListMultimap.create(); //存储结构化map信息【必须是线程安全的对象】
        SingleFileTask singleFileTask = new SingleFileTask() {
            @Override
            protected void runTask(TaskResult beforeResult) {
                FunctionLevelCodeSimplifier functionLevelCodeSimplifier = new FunctionLevelCodeSimplifier();
                String code = beforeResult.getCode();//获取源文件内容
                List<String> functions = functionLevelCodeSimplifier.getSimplifiedMethods(code);//方法级别源码抽象
                for(String function:functions){
                    multiMap.put(function.length(),function.hashCode());//将抽象出的函数块结构化存储【key -> value】{ key：函数长度；value：函数hash}
                }
            }
        };
        dirMultiThreadTasker.runTask(new File("E:\\code"),singleFileTask);
        System.out.println(dirMultiThreadTasker);
    }
}
