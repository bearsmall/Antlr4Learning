package com.bupt.cmp.compare;

import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by bearsmall on 2017/9/25.
 */
@Slf4j
public class CompareDirector {
    /**
     * 文本比对
     */
    public static final int TEXT_COMPARE = 1;

    /**
     * 令牌序列比对
     */
    public static final int TOKEN_COMPARE = 2;


    /**
     * 语法树比对
     */
    public static final int SYNTAX_COMPARE = 8;


    /**
     * 比对器
     */
    private Comparator comparator = null;

    /**
     * 私有构造函数
     */
    private CompareDirector(Comparator comparator) {
        this.comparator = comparator;
    }

    /**
     * 比较两个代码文件
     *
     * @param f1
     *            参与比对的第一个文件
     * @param f2
     *            参与比对的第二个文件
     * @return 比对结果对象
     */
    public CompareResult compare(DefaultCodeFile f1, DefaultCodeFile f2) {
        try {
            return this.comparator.compareAndRecord(f1, f2);
        } catch (Error error){
            log.info("比对过程中出错！"+">"+f1.getFilePath()+">"+f2.getFilePath()+">"+error);
        }catch (Exception e) {
            log.error("compare error!",e);
        }
        return null;
    }

    /**
     * 比较两个代码文件
     * @param list1 参与比对的第一个文件
     * @param list2 参与比对的第二个文件
     * @return 比对结果对象
     */
    public CompareResult compare(List<LineStruct> list1, List<LineStruct> list2) {
        try {
            return this.comparator.compareAndRecord(list1, list2);
        } catch (Exception e) {
            log.error("compare error!",e);
            return null;
        }
    }

    /**
     * 比较两个代码文件
     * @param f1 参与比对的第一个文件
     * @param f2 参与比对的第二个文件
     * @return 比对结果对象
     */
    public CompareResult compareSyntax(List<StoreNode> f1, List<StoreNode> f2) {
        try {
            return this.comparator.compareAndRecordSyntax(f1, f2);

        } catch (Exception e) {
            log.error("compareSyntax error !",e);
            return null;
        }
    }

    /**
     * @param g 比对粒度设置
     * @param type 比对方法类型
     * @return CompareDirector对象
     */
    public static CompareDirector getInstance(Granularity g, int blocksize, int type) {
        CompareDirector cd = null;
        switch (type) {
            case TEXT_COMPARE:
                cd = new CompareDirector(new TextComparator(g, blocksize));
                break;
            case TOKEN_COMPARE:
                cd = new CompareDirector(new TokenComparator(g, blocksize));
                break;
            case SYNTAX_COMPARE:
                cd = new CompareDirector(new SyntaxComparator());
                break;
            default:
                break;
        }
        return cd;
    }

}
