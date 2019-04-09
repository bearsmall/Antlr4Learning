package com.fileread;

import com.cmp.DefaultCodeFile;
import com.cmp.ModuleEntity;
import com.cmp.factory.JavaCodeFactory;
import com.cmp.utils.IOAgent;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FileReader {
    public static List<File> fileList = Collections.synchronizedList(new LinkedList<File>());
    //9s
    public static void read(File f){
        IOAgent ioagent = IOAgent.getInstance();
        try {
            String filePath = f.getCanonicalPath();
            String name = f.getName();
            String content = ioagent.getFileText(f);
            char[] ca = content.toCharArray();
            int charNum = ca.length;
            int lineNum = 1;
            for(int i = 0; i < charNum; i++) {
                if(ca[i] == '\n') {
                    lineNum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //6s
    public static void read2(File f){
        BufferedInputStream bis = null;
        try {
            String filePath = f.getCanonicalPath();
            bis = new BufferedInputStream(new FileInputStream(f));
            byte[] bytes = new byte[bis.available()];
            bis.read(bytes);
            String content = new String(bytes);
            char[] ca = content.toCharArray();
            int charNum = ca.length;
            int lineNum = 1;
            for(int i = 0; i < charNum; i++) {
                if(ca[i] == '\n') {
                    lineNum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //1.5s
    public static void read3(File f){
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(f);
            channel = fis.getChannel();
            int capacity = fis.available();
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            channel.read(bf);
            byte[] bytes = bf.array();
            bf.clear();
            String str = new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    //8s
    public static void read4(File f){
        //为空、不存在、不是文件、不可读
        if (f == null || !f.exists() || !f.isFile() || !f.canRead()) {
            return ;
        }
        if(f==null||!f.exists()||!f.isFile()||!f.canRead()){
            return;
        }
        if (f.length() <= 0) {
            return ;
        }
        //得到该文件的编码格式字符串
        String type = IOAgent.getCodeType(f);
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(f);
            channel = fis.getChannel();
            int capacity = 4096;
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;
            StringBuilder sb = new StringBuilder();
            while ((length = channel.read(bf)) != -1) {
                bf.clear();
                byte[] bytes = bf.array();
                sb.append(new String(bytes,0,length,type));
            }
            bf.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    //1.8s
    public static void read5(File f){
        //为空、不存在、不是文件、不可读
        //        if (f == null || !f.exists() || !f.isFile() || !f.canRead()) {
        //            return ;
        //        }
        //        if(f==null||!f.exists()||!f.isFile()||!f.canRead()){
        //            return;
        //        }
        //        if (f.length() <= 0) {
        //            return ;
        //        }
        //得到该文件的编码格式字符串
        String type = IOAgent.getCodeType(f);
        FileInputStream fis = null;
        FileChannel channel = null;
        try {
            fis = new FileInputStream(f);
            channel = fis.getChannel();
            int capacity = 4096;
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;
            StringBuilder sb = new StringBuilder();
            while ((length = channel.read(bf)) != -1) {
                bf.clear();
                byte[] bytes = bf.array();
                sb.append(new String(bytes,0,length,type));
            }
            bf.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void test() throws IOException {
        File root = new File("D:\\test\\simi\\repo");
        Long start = System.currentTimeMillis();
        render(root);
        Long middle = System.currentTimeMillis();
        System.out.println(middle-start);
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
        for (File file:fileList){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
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
                            System.out.println(file.getAbsolutePath());
                        }else {
                            KK.addAndGet(1);
                            System.out.println(file.getAbsolutePath());
                        }
                        atomicInteger.addAndGet((int) file.length());
                        DefaultCodeFile defaultCodeFile = JavaCodeFactory.getInstance().generateDefectCodeFile(file);
                        if(defaultCodeFile.getMethods()!=null) {
                            totalMethods.addAndGet(defaultCodeFile.getMethods().size());
                            for (ModuleEntity moduleEntity : defaultCodeFile.getMethods()) {
                                int lines = moduleEntity.getLineEnd() - moduleEntity.getLineBegin() + 1;
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
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("Parse Spend:"+(end-middle));
        System.out.println("Total Bytes:"+atomicInteger);
        System.out.println("Total File Numbers:"+totalFiles);
        System.out.println("1K:"+K1+">"+K1.get()*1.0/totalFiles);
        System.out.println("5K:"+K5+">"+K5.get()*1.0/totalFiles);
        System.out.println("10K:"+K10+">"+K10.get()*1.0/totalFiles);
        System.out.println("20K:"+K20+">"+K20.get()*1.0/totalFiles);
        System.out.println("50K:"+K50+">"+K50.get()*1.0/totalFiles);
        System.out.println("100K:"+K100+">"+K100.get()*1.0/totalFiles);
        System.out.println("200K:"+K200+">"+K200.get()*1.0/totalFiles);
        System.out.println("500K:"+K500+">"+K500.get()*1.0/totalFiles);
        System.out.println("1024K:"+K1024+">"+K1024.get()*1.0/totalFiles);
        System.out.println("KK:"+KK+">"+KK.get()*1.0/totalFiles);
        System.out.println("------------------------");
        System.out.println("Total Method Numbers:"+totalMethods);
        System.out.println("L1:"+L1+">"+L1.get()*1.0/totalMethods.get());
        System.out.println("L2:"+L2+">"+L2.get()*1.0/totalMethods.get());
        System.out.println("L3:"+L3+">"+L3.get()*1.0/totalMethods.get());
        System.out.println("L4:"+L4+">"+L4.get()*1.0/totalMethods.get());
        System.out.println("L5:"+L5+">"+L5.get()*1.0/totalMethods.get());
        System.out.println("L6:"+L6+">"+L6.get()*1.0/totalMethods.get());
        System.out.println("L7:"+L7+">"+L7.get()*1.0/totalMethods.get());
        System.out.println("L8:"+L8+">"+L8.get()*1.0/totalMethods.get());
        System.out.println("L9:"+L9+">"+L9.get()*1.0/totalMethods.get());
        System.out.println("L10:"+L10+">"+L10.get()*1.0/totalMethods.get());
        System.out.println("L20:"+L20+">"+L20.get()*1.0/totalMethods.get());
        System.out.println("L50:"+L50+">"+L50.get()*1.0/totalMethods.get());
        System.out.println("L100:"+L100+">"+L100.get()*1.0/totalMethods.get());
        System.out.println("L200:"+L200+">"+L200.get()*1.0/totalMethods.get());
        System.out.println("L500:"+L500+">"+L500.get()*1.0/totalMethods.get());
        System.out.println("LL:"+LL+">"+LL.get()*1.0/totalMethods.get());
    }

    @Test
    public void test2() throws IOException {
        Long start = System.currentTimeMillis();
        File root = new File("E:\\User\\bearsmall\\html\\coding\\Java");
        multiThreadRender(root);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(root);
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
            fileList.add(file);
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
