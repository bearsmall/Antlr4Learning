package com.bupt.cmp.compare.init;

import com.bupt.cmp.factory.CppCodeFactory;
import com.bupt.cmp.factory.ICodeFactory;
import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

@Slf4j
public class CppInitFile {

    public static ICodeFactory icodeFactory;
    static {
        icodeFactory = CppCodeFactory.getInstance();
    }

    public static int sum = 0;
    public static long size = 0;

    @Test
    public void test1(){
        String folder = "D:\\test\\poco-develop";
        File file = new File(folder);
        long start = System.currentTimeMillis();
        iteratorFolder(file);
        long end = System.currentTimeMillis();
        System.out.println("total:"+sum+" c/cpp files");
        System.out.println("total:"+(size/(1000.0*1000))+"M size spend "+(end-start)/1000.0+" seconds!");
    }

    private void iteratorFolder(File file) {
        if(file.isFile()){
            if(file.getName().endsWith(".java")||file.getName().endsWith(".c")||file.getName().endsWith(".cpp")||file.getName().endsWith(".h")||file.getName().endsWith(".dll")||file.getName().endsWith(".lib")||file.getName().endsWith(".o")) {
                sum++;
                size+=file.length();
                System.out.println(sum+"> current at:" + file.getAbsolutePath());
                DefaultCodeFile f = icodeFactory.generateDefectCodeFile(file);
                log.info(LineStruct.LineStructToString(f.getTextLine()));
                log.info(LineStruct.LineStructToString(f.getTokenLine()));
                log.info(StoreNode.StoreNodeToString(f.getTree()));
            }else {
                System.out.println("skip file at:" + file.getAbsolutePath());
            }
        }else if(file.isDirectory()){
            File[] childrens = file.listFiles();
            for(File f:childrens){
                iteratorFolder(f);
            }
        }
    }
}
