package com.bupt.cmp.compare.init;

import com.bupt.cmp.factory.ICodeFactory;
import com.bupt.cmp.factory.JavaCodeFactory;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;

@Slf4j
public class JavaInitFile {

    public static ICodeFactory icodeFactory;
    static {
        icodeFactory = JavaCodeFactory.getInstance();
    }

    public static int sum = 0;
    public static long size = 0;

    public static int fileNum = 0;
    public static int folderNum = 0;

    @Test
    public void test1(){
        String folder = "D:\\test\\src";
        File file = new File(folder);
        long start = System.currentTimeMillis();
        //iteratorFolder(file);//递归方式
        traverseFolder(file);//非递归方式
        long end = System.currentTimeMillis();
        log.info("total:"+sum+" java files");
        log.info("total:"+(size/(1000.0*1000))+"M size spend "+(end-start)/1000.0+" seconds!");
    }

    /**
     * 非递归遍历文件夹下所有文件
     * @param file
     */
    public void traverseFolder(File file) {
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<>();
            if(file.isFile()){
                generateSpecialFile(file);
            }else if(file.isDirectory()){
                File[] files = file.listFiles();
                loopFiles(list, files);
                File temp_file;
                while (!list.isEmpty()) {
                    temp_file = list.removeFirst();
                    files = temp_file.listFiles();
                    loopFiles(list, files);
                }
            }else {
                log.info("不是文件也不是目录!");
            }
        } else {
            log.info("文件或文件夹不存在!");
        }
        log.info("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

    }

    /**
     * 遍历文件列表数组
     * @param list
     * @param files
     */
    private void loopFiles(LinkedList<File> list, File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                log.info("文件夹:" + file.getAbsolutePath());
                list.add(file);
                folderNum++;
            } else {
                generateSpecialFile(file);
            }
        }
    }

    /**
     * 处理特定文件的逻辑
     * @param file
     */
    private void generateSpecialFile(File file) {
        if(file.getName().endsWith(".java")) {
            fileNum++;
            size+=file.length();
            log.info(fileNum+"> current at:" + file.getAbsolutePath());
            DefaultCodeFile f = icodeFactory.generateDefectCodeFile(file);

            Long start = System.currentTimeMillis();
//            CheckUtils.serialize(DefaultSerializeFile.convertFrom(f),f.getFilePath()+".serialize");
//            DefaultSerializeFile defaultSerializeFile = CheckUtils.deSerialize(f.getFilePath()+".serialize");
            Long end = System.currentTimeMillis();
            log.info("serialize spend:{},path:{}",(end-start),f.getFilePath());
        }else {
            log.info("skip file at:" + file.getAbsolutePath());
        }
    }
    /**
     * 递归遍历文件夹下所有文件
     * @param file
     */
    private void iteratorFolder(File file) {
        if(file.isFile()){
            generateSpecialFile(file);
        }else if(file.isDirectory()){
            File[] childrens = file.listFiles();
            for(File f:childrens){
                iteratorFolder(f);
            }
        }
    }
}
