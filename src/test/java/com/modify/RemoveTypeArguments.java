package com.modify;

import com.cmp.antlr.java.JavaLexer;
import com.cmp.antlr.java.JavaParser;
import com.cmp.listener.TypeArgumentsOrTypeParameters;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Java范型擦除
 */
@Slf4j
public class RemoveTypeArguments {

    @Test
    public void testTypeArguments(){
        String root = "/Users/xiongyu/IdeaProjects/Antlr4Learning/src/test/resources/language/java/typearguments";
        root = "/Users/xiongyu/simi/src";
        Long start = System.currentTimeMillis();
        List<File> fileList = FileExtractor.build(".java").extract(root);
        Long middle = System.currentTimeMillis();
        log.info(String.valueOf(middle-start));
        AtomicInteger index = new AtomicInteger(1);
        ExecutorService executorService = new ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
                r -> new Thread(r, "提取文件子线程-"+index.getAndAdd(1)), new ThreadPoolExecutor.AbortPolicy());
        int size = fileList.size();
        final CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicLong atomicInteger = new AtomicLong(0);
        int totalFiles = fileList.size();
        AtomicLong totalChangedFiles = new AtomicLong(0);
        for (File file:fileList){
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
                    TypeArgumentsOrTypeParameters extractor = new TypeArgumentsOrTypeParameters(fileContent,tokenStream.getTokens());
                    walker.walk(extractor,tree);
                    if(extractor.isChanged()){
                        totalChangedFiles.addAndGet(1);
                        System.out.println(file.getAbsolutePath());
//                        String changedContent = new String(extractor.getContent());
//                        System.out.println(changedContent);
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
        log.info("typeArgumentsCount:                   "+TypeArgumentsOrTypeParameters.typeArgumentsCount.get());
        log.info("typeArgumentsOrDiamondCount:          "+TypeArgumentsOrTypeParameters.typeArgumentsOrDiamondCount.get());
        log.info("typeParameters:                       "+TypeArgumentsOrTypeParameters.typeParameters.get());
        log.info("nonWildcardTypeArgumentsOrDiamond:    "+TypeArgumentsOrTypeParameters.nonWildcardTypeArgumentsOrDiamond.get());
        log.info("nonWildcardTypeArguments:             "+TypeArgumentsOrTypeParameters.nonWildcardTypeArguments.get());
        log.info("Parse Spend:"+(end-middle));
        log.info("Total :       "+atomicInteger+" B | "+atomicInteger.get()/1024+ " KB | "+atomicInteger.get()/(1024*1024)+" MB");
        log.info("Total File Numbers:"+totalFiles);
        log.info("Total Changed File Numbers:"+totalChangedFiles);
    }

/*
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - typeArgumentsCount:                   307967
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - typeArgumentsOrDiamondCount:          59708
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - typeParameters:                       15498
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - nonWildcardTypeArgumentsOrDiamond:    48
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - nonWildcardTypeArguments:             4368
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - Parse Spend:73462
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - Total :       327168665 B | 319500 KB | 312 MB
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - Total File Numbers:50495
17:49:50.168 [main] INFO com.modify.RemoveTypeArguments - Total Changed File Numbers:28714
*/

}
