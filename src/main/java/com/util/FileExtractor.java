package com.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件提取工具类
 * @author xiongyu
 */
@Slf4j
public class FileExtractor {

    private String suffix;

    /**
     * 工厂类
     * @param suffix 后缀
     * @return
     */
    public static FileExtractor build(String suffix){
        return new FileExtractor(suffix);
    }

    private FileExtractor(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 判断文件是否符合要求，符合则添加文件
     * @param result
     * @param file
     */
    private void addFile(List<File> result, File file){
        if(file.getPath().endsWith(suffix)){
            result.add(file);
        }
    }

    /**
     * 单线程extract（递归）
     * @param root 根路径
     */
    public List<File> iteratorExtract(String root){
        return iteratorExtract(new File(root));
    }

    /**
     * 单线程extract（递归）
     * @param root 根目录
     */
    public List<File> iteratorExtract(File root){
        List<File> fileList = new LinkedList<>();
        if(root.isDirectory()){
            File[] childrens = root.listFiles();
            for (File children:childrens){
                fileList.addAll(iteratorExtract(children));
            }
        }else if (root.isFile()){
            addFile(fileList, root);
        }
        return fileList;
    }

    /**
     * 单线程extract（非递归）
     * @param root 根路径
     */
    public List<File> extract(String root){
        return extract(new File(root));
    }

    /**
     * 单线程extract（非递归）
     * @param root 根目录
     */
    public List<File> extract(File root){
        List<File> result = new LinkedList<>();
        List<File> fileList = new LinkedList<>();
        if(root.isDirectory()){
            File[] files = root.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.add(file);
                } else {
                    addFile(result,file);
                }
            }
        }else {
            addFile(result,root);
        }
        while (!fileList.isEmpty()){
            File file = fileList.remove(0);
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if(f.isDirectory()){
                    fileList.add(f);
                }else{
                    addFile(result,f);
                }
            }
        }
        return result;
    }

    /**
     * 多线程extract（非递归）
     * @param root 根路径
     */
    public List<File> multiExtract(String root){
        return multiExtract(new File(root));
    }

    /**
     * 多线程extract（非递归）
     * @param root 根目录
     */
    public List<File> multiExtract(File root){
        List<File> result = Collections.synchronizedList(new LinkedList<>());
        List<File> fileList = new LinkedList<>();
        if(root.isDirectory()){
            File[] files = root.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.add(file);
                } else {
                    addFile(result, file);
                }
            }
        }else {
            addFile(result, root);
        }
        List<File> dirList = new LinkedList<>();
        for(File dir:fileList){
            File[] files = dir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    dirList.add(file);
                } else {
                    addFile(result, file);
                }
            }
        }
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ExecutorService executorService = new ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
                r -> new Thread(r, "提取文件子线程-"+atomicInteger.getAndAdd(1)), new ThreadPoolExecutor.AbortPolicy());

        int size = dirList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for(File dir:dirList){
            executorService.execute(() -> {
                try {
                    result.addAll(extract(dir));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
