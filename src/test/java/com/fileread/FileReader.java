package com.fileread;

import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.ModuleEntity;
import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.cmp.compare.*;
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
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FileReader {
//    public static List<File> fileList = Collections.synchronizedList(new LinkedList<File>());
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
                    String fileContent = IOAgent.getInstance().getFileText(file);
                    CharStream inputStream = CharStreams.fromString(fileContent);
                    JavaLexer lexer = new JavaLexer(inputStream);
                    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                    JavaParser parser = new JavaParser(tokenStream);
                    ParseTree tree = parser.compilationUnit();

                    int hashValue=0;
                    int tempHashValue=0;
                    int tempLineNum=1;
                    String lineString = new String();
                    List<LineStruct> linetokenlist = new LinkedList<>();
                    for (Token token:tokenStream.getTokens()){
                        int type = token.getType();
                        if(token.getChannel()==JavaLexer.HIDDEN){
                            continue;
                        }
                        if (token.getLine()==tempLineNum){
                            lineString = lineString+" "+type;
                        }else {
                            hashValue = lineString.hashCode();
                            if(hashValue!=tempHashValue){
                                LineStruct lineStruct=new LineStruct();
                                lineStruct.setLineNum(tempLineNum);
                                lineStruct.setHashValue(hashValue);
                                linetokenlist.add(lineStruct);
                                tempHashValue=hashValue;
                            }
                            lineString = " "+token.getText();;
                            tempLineNum = token.getLine();
                        }
                    }
                    ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
                    ChangeToLambdaExtractListener extractor = new ChangeToLambdaExtractListener(fileContent,tokenStream.getTokens());
                    walker.walk(extractor,tree);
                    if(extractor.isChanged()){
//                            log.info(extractor.getContentBefore());
                        String str = new String(extractor.getContent());
//                            log.info(str);
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
