package com.tool;

import com.cmp.utils.IOAgent;
import com.extract.demo1.function.FunctionLevelCodeSimplifier;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class DirMultiThreadTasker {
    private List<File> fileList = new LinkedList<File>();//文件列表，size为源文件数量
    private AtomicLong totalLine = new AtomicLong(0);//总代码行数
    private long totalFileSize = 0; //文件总大小【字节为单位】
    private String[] suffixFilter = new String[]{".java"};

    public DirMultiThreadTasker() {
    }

    public DirMultiThreadTasker(String[] suffixFilter) {
        if(suffixFilter!=null) {
            this.suffixFilter = suffixFilter;
        }
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public AtomicLong getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(AtomicLong totalLine) {
        this.totalLine = totalLine;
    }

    public long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public String[] getSuffixFilter() {
        return suffixFilter;
    }

    public void setSuffixFilter(String[] suffixFilter) {
        this.suffixFilter = suffixFilter;
    }

    public static Logger getLog() {
        return log;
    }

    public void runTask(File root, SingleFileTask singleFileTask) throws IOException {
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
                        TaskResult taskResult = singleFileTask.invoke(file);
                        String code = taskResult.getCode();//源文件内容
                        int i = code.split("\\n").length;//代码行数
                        totalLine.addAndGet(i);//总代码行数
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
        logResults(start, end);
    }

    private void logResults(Long start, Long end) {
        //统计并输出
        log.info("####################################+ summary result data +######################################");
        log.info("total size: 【"+totalFileSize +" bytes】/【"+totalFileSize/1024.0/1024+"MB】/【"+totalFileSize/1024.0/1024/1024+"GB】");
        log.info("total line: 【"+totalLine.get()+" line】/【"+totalLine.get()/1000.0+"K LOC】/【"+totalLine.get()/1000.0/1000+"M LOC】/【"+totalLine.get()/1000.0/1000/1000+"B LOC】");
        log.info("total spend:【"+(end-start)+" ms】/【"+(end-start)/1000.0+"seconds】/【"+(end-start)/1000.0/60+"minutes】/【"+(end-start)/1000.0/60/60+"hour】/【"+(end-start)/1000.0/60/60/24+"day】");
        log.info("####################################+ summary result data +######################################");
    }

    public void render(File root){
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

    public void runFile(File file){
        for(String suffix:suffixFilter) {
            if (file.getPath().endsWith(suffix)) {
                totalFileSize += file.length();
                fileList.add(file);
            }
        }
    }
}
