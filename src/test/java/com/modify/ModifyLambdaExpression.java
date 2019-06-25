package com.modify;

import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.cmp.listener.ChangeToLambdaExtractListener;
import com.cmp.utils.IOAgent;
import com.util.FileExtractor;
import com.util.TokenizationModifier;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 匿名内部类修改为lambda表达式
 */
@Slf4j
public class ModifyLambdaExpression {


    @Test
    public void testChangeToLambda() {
//        List<DefaultCodeFile> queue = Collections.synchronizedList(new LinkedList<>());
        File root = new File("/Users/xiongyu/simi/src");
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle - start));
        ExecutorService executorService = new ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicLong atomicInteger = new AtomicLong(0);
        int totalFiles = fileList.size();
        AtomicLong totalChangedFiles = new AtomicLong(0);
        for (File file : fileList) {
            executorService.execute(() -> {
                try {
                    atomicInteger.getAndAdd(file.length());
                    String fileContent = IOAgent.getInstance().getFileText(file);
                    CharStream inputStream = CharStreams.fromString(fileContent);
                    JavaLexer lexer = new JavaLexer(inputStream);
                    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                    JavaParser parser = new JavaParser(tokenStream);
                    ParseTree tree = parser.compilationUnit();
                    TokenizationModifier.tokenToLines(tokenStream);
                    ParseTreeWalker walker = new ParseTreeWalker();//新建一个标准的遍历器
                    ChangeToLambdaExtractListener extractor = new ChangeToLambdaExtractListener(fileContent, tokenStream.getTokens());
                    walker.walk(extractor, tree);
                    if (extractor.isChanged()) {
                        totalChangedFiles.addAndGet(1);
                        System.out.println(file.getAbsolutePath());
//                     log.info(extractor.getContentBefore());
//                       String str = new String(extractor.getContent());
//                       log.info(str);
                    }
                } finally {
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
        log.info("lambdaCount:  "+ChangeToLambdaExtractListener.lambdaCount.get());
        log.info("innerCount:   "+ChangeToLambdaExtractListener.innerCount.get());
        log.info("Parse Spend:  " + (end - middle));
        log.info("Total :       "+atomicInteger+" B | "+atomicInteger.get()/1024+ " KB | "+atomicInteger.get()/(1024*1024)+" MB");
        log.info("Total File Numbers:" + totalFiles);
        log.info("Total Changed File Numbers:" + totalChangedFiles);
    }

/*
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - lambdaCount:  34304
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - innerCount:   8385
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - Parse Spend:  84402
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - Total :       327168665 B | 319500 KB | 312 MB
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - Total File Numbers:50495
17:48:03.491 [main] INFO com.modify.ModifyLambdaExpression - Total Changed File Numbers:2691
*/

}
