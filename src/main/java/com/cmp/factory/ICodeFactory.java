package com.cmp.factory;

import com.cmp.DefaultCodeFile;
import com.cmp.LineStruct;
import com.cmp.lang.Language;
import com.cmp.pretreat.CommentReplace;
import com.cmp.pretreat.PretreatDirector;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 抽象代码工厂类
 * Created by bearsmall on 2017/9/25.
 */
@Slf4j
public abstract class ICodeFactory {
    public Language language;

    public ICodeFactory(Language language) {
        this.language = language;
    }

    protected abstract DefaultCodeFile init(File file);

    protected abstract DefaultCodeFile init(String code);

    /**
     * 构造默认源文件类
     *
     * @param file
     * @return
     */
    public DefaultCodeFile generateDefectCodeFile(File file){
        DefaultCodeFile defaultCodeFile = null;
        try {
            defaultCodeFile = init(file);
//            PretreatDirector.getInstance(PretreatDirector.COMMENT_REPLACE).pretreat(defaultCodeFile);
            Long start = System.currentTimeMillis();
//            log.debug("level 1:预处理文本:{}",file.getAbsolutePath());
            prepareTextLine(defaultCodeFile);
//            log.debug("level 2:预处理Token:{}",file.getAbsolutePath());
            prepareTokenLine(defaultCodeFile);
//            log.debug("level 3:预处理语法树:{}",file.getAbsolutePath());
            prepareSyntaxTree(defaultCodeFile);
            Long end = System.currentTimeMillis();
//            log.debug("预处理耗时:{}秒！filePath:{}",(end-start)/1000,file.getAbsolutePath());
            if((end-start)>10*1000){//如果预处理过程大于10 seconds，记一次异常日志！
                log.error("预处理时间过长(10 seconds)：{}",file.getAbsolutePath());
            }
        }catch (Error error){
            log.error("初始化defaulCodeFile出错！ !"+file.getAbsolutePath()+">"+error);
        }catch (Exception e){
            log.debug("generateDefectCodeFile error ,three steps something wrong!:{},{}",file.getAbsoluteFile(),e.getMessage());
        }
        return defaultCodeFile;
    }

    /**
     * 构造默认源文件类
     *
     * @param code
     * @return
     */
    public DefaultCodeFile generateDefectCodeFile(String code){
        DefaultCodeFile defaultCodeFile = null;
        try {
            defaultCodeFile = init(code);
            PretreatDirector.getInstance(PretreatDirector.COMMENT_REPLACE).pretreat(defaultCodeFile);
            log.debug("level 1:预处理文本");
            prepareTextLine(defaultCodeFile);
            log.debug("level 2:预处理Token");
            prepareTokenLine(defaultCodeFile);
//            log.debug("level 3:预处理语法树");
//            prepareSyntaxTree(defaultCodeFile);
        }catch (Error error){
            log.error("初始化defaulCodeFile出错！ !>"+error);
        }catch (Exception e){
            log.debug("generateDefectCodeFile error ,three steps something wrong!:{}-->{}",code.length()>10?code.substring(0,10):code,e);
        }
        return defaultCodeFile;
    }

    /**
     * 源文件文本预处理
     *
     * @param defaultCodeFile
     */
    public void prepareTextLine(DefaultCodeFile defaultCodeFile) {
        if (defaultCodeFile == null) {
            return;
        }
        List<LineStruct> txtLine = defaultCodeFile.getTextLine();
        String text = defaultCodeFile.getContent();
//        if (text != null) {
//            String[] lines = text.split("\n", -1);//split方法第二个参数设为负才能保证行数不错
//            defaultCodeFile.setLine(Arrays.asList(lines));
//        }
        text = CommentReplace.getInstance().replace(text);
        if (txtLine != null) {
            txtLine.clear();
        }
        if (text != null) {
            String[] lines = text.split("\n", -1);//split方法第二个参数设为负才能保证行数不错
            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].trim();
                if ((!"".equals(lines[i])) && (!"\t".equals(lines[i])) && (!"\t\t".equals(lines[i]))) {
                    LineStruct templine = new LineStruct(i + 1, lines[i].hashCode());
                    txtLine.add(templine);
                }
            }
        }
        defaultCodeFile.setTextLine(txtLine);
    }

    /**
     * 源文件Token预处理
     *
     * @param defaultCodeFile
     * @return
     */
    public boolean prepareTokenLine(DefaultCodeFile defaultCodeFile)throws Exception{
        boolean flag = false;
        try {
            flag = language.getLineTokenSequence(defaultCodeFile);
        }catch (Error error){
            log.error("prepareTokenLine!{},{}",defaultCodeFile.getFilePath(),error);
        } catch (Exception e) {
            log.error("prepareTokenLine error!");
            throw e;
        }
        return flag;
    }

    /**
     * 源文件抽象语法树预处理
     *
     * @param defaultCodeFile
     */
    public abstract void prepareSyntaxTree(DefaultCodeFile defaultCodeFile)throws Exception;

}