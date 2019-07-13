package com.fileread;

import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.ModuleEntity;
import com.cmp.antlr.java.JavaLexer;
import com.cmp.compare.CompareDirector;
import com.cmp.compare.CompareResult;
import com.cmp.compare.Granularity;
import com.cmp.factory.ICodeFactory;
import com.cmp.factory.JavaCodeFactory;
import com.cmp.listener.LambdaExtractListener;
import com.util.FileExtractor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
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
        String src1 = SimiCompare.class.getClassLoader().getResource("language/java/complex1/Demo1.java").getPath();
        df =  icodeFactory.generateDefectCodeFile(new File(src1));
    }

    @Test
    public void testChangeToLambda() {
//        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicLong atomicInteger = new AtomicLong(0);
        int totalFiles = fileList.size();
        AtomicLong totalMethods = new AtomicLong(0);
        AtomicLong totalLineOfMethods = new AtomicLong(0);
        for (File file:fileList){
            executorService.execute(() -> {
                try {
                    DefaultCodeFile df2 = icodeFactory.generateDefectCodeFile(file);
                    CompareResult compareResult = tokenmd.compare(df,df2);
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
        log.info(String.valueOf(LambdaExtractListener.lambdaCount));
        log.info(String.valueOf(LambdaExtractListener.innerCount));
        log.info("Parse Spend:"+(end-middle));
        log.info("Total Bytes:"+atomicInteger);
        log.info("Total File Numbers:"+totalFiles);
    }

    @Test
    public void testLambda() throws IOException {
//        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicLong atomicInteger = new AtomicLong(0);
        int totalFiles = fileList.size();
        AtomicLong totalMethods = new AtomicLong(0);
        AtomicLong totalLineOfMethods = new AtomicLong(0);
        for (File file:fileList){
            executorService.execute(() -> {
                try {
                    DefaultCodeFile defaultCodeFile = JavaCodeFactory.getInstance().generateDefectCodeFile(file);
                    if(defaultCodeFile.getMethods()!=null) {
                        totalMethods.addAndGet(defaultCodeFile.getMethods().size());
                        for (ModuleEntity moduleEntity : defaultCodeFile.getMethods()) {
                            int lines = moduleEntity.getLineEnd() - moduleEntity.getLineBegin() + 1;
                            totalLineOfMethods.addAndGet(lines);
                        }
                    }
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
        Long end = System.currentTimeMillis();
        log.info(String.valueOf(LambdaExtractListener.lambdaCount));
        log.info(String.valueOf(LambdaExtractListener.innerCount));
        log.info("Parse Spend:"+(end-middle));
        log.info("Total Bytes:"+atomicInteger);
        log.info("Total File Numbers:"+totalFiles);
    }

    @Test
    public void test() throws IOException {
        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicLong atomicInteger = new AtomicLong(0);
        AtomicLong K1 = new AtomicLong(0);
        AtomicLong K5 = new AtomicLong(0);
        AtomicLong K10 = new AtomicLong(0);
        AtomicLong K20 = new AtomicLong(0);
        AtomicLong K50 = new AtomicLong(0);
        AtomicLong K100 = new AtomicLong(0);
        AtomicLong K200 = new AtomicLong(0);
        AtomicLong K500 = new AtomicLong(0);
        AtomicLong K1024 = new AtomicLong(0);
        AtomicLong KK = new AtomicLong(0);

        AtomicInteger L1 = new AtomicInteger(0);
        AtomicInteger L2 = new AtomicInteger(0);
        AtomicInteger L3 = new AtomicInteger(0);
        AtomicInteger L4 = new AtomicInteger(0);
        AtomicInteger L5 = new AtomicInteger(0);
        AtomicInteger L6 = new AtomicInteger(0);
        AtomicInteger L7 = new AtomicInteger(0);
        AtomicInteger L8 = new AtomicInteger(0);
        AtomicInteger L9 = new AtomicInteger(0);
        AtomicInteger L10 = new AtomicInteger(0);
        AtomicInteger L20 = new AtomicInteger(0);
        AtomicInteger L50 = new AtomicInteger(0);
        AtomicInteger L100 = new AtomicInteger(0);
        AtomicInteger L200 = new AtomicInteger(0);
        AtomicInteger L500 = new AtomicInteger(0);
        AtomicInteger LL = new AtomicInteger(0);

        int totalFiles = fileList.size();
        AtomicLong totalMethods = new AtomicLong(0);
        AtomicLong totalLineOfMethods = new AtomicLong(0);
        for (File file:fileList){
            executorService.execute(() -> {
                try {
                    if(file.length()<=1024){
                        K1.addAndGet(1);
                    }else if(file.length()<=5*1024){
                        K5.addAndGet(1);
                    }else if(file.length()<=10*1024){
                        K10.addAndGet(1);
                    }else if(file.length()<=20*1024){
                        K20.addAndGet(1);
                    }else if(file.length()<=50*1024){
                        K50.addAndGet(1);
                    }else if(file.length()<=100*1024){
                        K100.addAndGet(1);
                    }else if(file.length()<=200*1024){
                        K200.addAndGet(1);
                    }else if(file.length()<=500*1024){
                        K500.addAndGet(1);
                    }else if(file.length()<=1024*1024){
                        K1024.addAndGet(1);
                        log.info(file.getAbsolutePath());
                    }else {
                        KK.addAndGet(1);
                        log.info(file.getAbsolutePath());
                    }
                    atomicInteger.addAndGet((int) file.length());
                    DefaultCodeFile defaultCodeFile = JavaCodeFactory.getInstance().generateDefectCodeFile(file);
                    queue.add(defaultCodeFile);
                    if(defaultCodeFile.getMethods()!=null) {
                        totalMethods.addAndGet(defaultCodeFile.getMethods().size());
                        for (ModuleEntity moduleEntity : defaultCodeFile.getMethods()) {
                            int lines = moduleEntity.getLineEnd() - moduleEntity.getLineBegin() + 1;
                            totalLineOfMethods.addAndGet(lines);
                            if (lines == 1) {
                                L1.addAndGet(1);
                            }else if(lines==2){
                                L2.addAndGet(1);
                            }else if(lines==3){
                                L3.addAndGet(1);
                            }else if(lines==4){
                                L4.addAndGet(1);
                            }else if(lines==5){
                                L5.addAndGet(1);
                            }else if(lines==6){
                                L6.addAndGet(1);
                            }else if(lines==7){
                                L7.addAndGet(1);
                            }else if(lines==8){
                                L8.addAndGet(1);
                            }else if(lines==9){
                                L9.addAndGet(1);
                            }else if(lines==10){
                                L10.addAndGet(1);
                            }else if(lines<=20){
                                L20.addAndGet(1);
                            }else if(lines<=50){
                                L50.addAndGet(1);
                            }else if(lines<=100){
                                L100.addAndGet(1);
                            }else if(lines<=200){
                                L200.addAndGet(1);
                            }else if(lines<=500){
                                L500.addAndGet(1);
                            }else {
                                LL.addAndGet(1);
                            }
                        }
                    }
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
        log.info("Total Bytes:"+atomicInteger);
        log.info("Total File Numbers:"+totalFiles);
        log.info("1K:"+K1+">"+K1.get()*1.0/totalFiles);
        log.info("5K:"+K5+">"+K5.get()*1.0/totalFiles);
        log.info("10K:"+K10+">"+K10.get()*1.0/totalFiles);
        log.info("20K:"+K20+">"+K20.get()*1.0/totalFiles);
        log.info("50K:"+K50+">"+K50.get()*1.0/totalFiles);
        log.info("100K:"+K100+">"+K100.get()*1.0/totalFiles);
        log.info("200K:"+K200+">"+K200.get()*1.0/totalFiles);
        log.info("500K:"+K500+">"+K500.get()*1.0/totalFiles);
        log.info("1024K:"+K1024+">"+K1024.get()*1.0/totalFiles);
        log.info("KK:"+KK+">"+KK.get()*1.0/totalFiles);
        log.info("------------------------");
        log.info("Total Line Of Methods:"+totalLineOfMethods);
        log.info("Total Method Numbers:"+totalMethods);
        log.info("L1:"+L1+">"+L1.get()*1.0/totalMethods.get());
        log.info("L2:"+L2+">"+L2.get()*1.0/totalMethods.get());
        log.info("L3:"+L3+">"+L3.get()*1.0/totalMethods.get());
        log.info("L4:"+L4+">"+L4.get()*1.0/totalMethods.get());
        log.info("L5:"+L5+">"+L5.get()*1.0/totalMethods.get());
        log.info("L6:"+L6+">"+L6.get()*1.0/totalMethods.get());
        log.info("L7:"+L7+">"+L7.get()*1.0/totalMethods.get());
        log.info("L8:"+L8+">"+L8.get()*1.0/totalMethods.get());
        log.info("L9:"+L9+">"+L9.get()*1.0/totalMethods.get());
        log.info("L10:"+L10+">"+L10.get()*1.0/totalMethods.get());
        log.info("L20:"+L20+">"+L20.get()*1.0/totalMethods.get());
        log.info("L50:"+L50+">"+L50.get()*1.0/totalMethods.get());
        log.info("L100:"+L100+">"+L100.get()*1.0/totalMethods.get());
        log.info("L200:"+L200+">"+L200.get()*1.0/totalMethods.get());
        log.info("L500:"+L500+">"+L500.get()*1.0/totalMethods.get());
        log.info("LL:"+LL+">"+LL.get()*1.0/totalMethods.get());
        CompareDirector tokenmd  = CompareDirector.getInstance(Granularity.MLCS, 0, CompareDirector.TOKEN_COMPARE);
        AtomicInteger simNumber = new AtomicInteger(0);
        for(DefaultCodeFile d1:queue){
            for(DefaultCodeFile d2:queue){
                if(d1!=d2){
                    if(d1.getMethods()!=null&&d2.getMethods()!=null){
                        for(ModuleEntity md1:d1.getMethods()){
                            for(ModuleEntity md2:d2.getMethods()){
                                CompareResult compareResult = tokenmd.compare(convert(md1.getTokenList()),convert(md2.getTokenList()));
                                if(compareResult.getSimvalue()>0.9){
                                    simNumber.addAndGet(1);
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("Sim Method Number:"+simNumber.get());
    }

    public List<LineStruct> convert(List<Token> tokens){
        int hashValue;
        int tempHashValue=0;
        int tempLineNum=1;
        StringBuilder lineString = new StringBuilder(new String());
        List<LineStruct> linetokenlist = new LinkedList<>();
        for (Token token:tokens){
            int type = token.getType();
            if(token.getChannel()==JavaLexer.HIDDEN){
                continue;
            }
            if (token.getLine()==tempLineNum){
                lineString.append(" ").append(type);
            }else {
                hashValue = lineString.toString().hashCode();
                if(hashValue!=tempHashValue){
                    LineStruct lineStruct=new LineStruct();
                    lineStruct.setLineNum(tempLineNum);
                    lineStruct.setHashValue(hashValue);
                    linetokenlist.add(lineStruct);
                    tempHashValue=hashValue;
                }
                lineString = new StringBuilder(" " + token.getText());;
                tempLineNum = token.getLine();
            }
        }
        return linetokenlist;
    }

    @Test
    public void test2() throws IOException {
        Long start = System.currentTimeMillis();
        File root = new File("E:\\User\\bearsmall\\html\\coding\\Java");
        multiThreadRender(root);
        Long end = System.currentTimeMillis();
        log.info(String.valueOf(end-start));
        log.info(String.valueOf(root));
    }

    /**
     * 单线程Render
     * @param root
     * @throws IOException
     */
    public static void render(File root) throws IOException {
        List<File> fileList = new LinkedList<>();
        if(root.isDirectory()){
            File[] files = root.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    fileList.add(files[i]);
                }else {
                    runFile(files[i]);
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

    /**
     * 多线程Render
     * @param root
     * @throws IOException
     */
    public static void multiThreadRender(File root) throws IOException {
        List<File> fileList = new LinkedList<>();
        if(root.isDirectory()){
            File[] files = root.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    fileList.add(files[i]);
                }else {
                    runFile(files[i]);
                }
            }
        }else {
            runFile(root);
        }
        List<File> dirList = new LinkedList<>();
        for(File dir:fileList){
            File[] files = root.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    dirList.add(files[i]);
                }else {
                    runFile(files[i]);
                }
            }
        }
        ExecutorService executorService = new ThreadPoolExecutor(16,32,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        int size = dirList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        for(File dir:dirList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        render(dir);
                    } catch (IOException e) {
                        e.printStackTrace();
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
        return;
    }

    public static void runFile(File file) throws IOException {
        if(file.getPath().endsWith(".java")){
//            fileList.add(file);
        }
    }

    public static void iteratorFile(File root) throws IOException {
        if(root.isDirectory()){
            File[] childrens = root.listFiles();
            for (File children:childrens){
                iteratorFile(children);
            }
        }else if (root.isFile()){
            runFile(root);
        }
    }

}
