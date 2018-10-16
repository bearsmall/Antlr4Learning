package com.bupt.cmp.compare;

import com.bupt.cmp.lang.file.DefaultCodeFile;
import com.bupt.cmp.pretreat.format.LineStruct;
import com.bupt.cmp.pretreat.format.StoreNode;

import java.util.List;

/**
 * 执行文本比对操作的类
 * @author ben
 */
public class TextComparator extends Comparator {

    /**
     * 粒度
     */
    private Granularity granularity = Granularity.LINE;

    /**
     * 分块大小
     */
    private int blocksize = 0;

    /**
     * @param granularity 粒度
     */
    TextComparator(Granularity granularity, int blocksize) {
        this.granularity = granularity;
        this.blocksize = blocksize;
    }

    /**
     * @return the granularity
     */
    public final Granularity getGranularity() {
        return granularity;
    }


    @Override
    public String toString() {
        return "纯文本比对方法";
    }

    /**
     * 对两个文件进行比较，并记录具体的相似信息。需要派生类实现具体功能
     *
     * @param file1 参与比对的第一个文件
     * @param file2 参与比对的第二个文件
     * @return 比对结果对象
     * @throws Exception
     */
    @Override
    /**
     * 传入参数为两个文件时进行的比对函数
     */
    public CompareResult compareAndRecord(DefaultCodeFile file1, DefaultCodeFile file2) {
        if(this.granularity == Granularity.BLOCK) {//分块比对
            List<LineStruct> first = file1.getTextLine();
            List<LineStruct> second = file2.getTextLine();
            List<SimRecord> recordResultList = super.getSimRecordBlock(first, second, blocksize);//对应相似行
            float simValue=super.getSimValue();//相似度
            return new CompareResult(simValue,recordResultList);

        }else if(this.granularity == Granularity.MLCS) {//匹配字符串比对
            List<LineStruct> first = file1.getTextLine();
            List<LineStruct> second = file2.getTextLine();
            List<SimRecord> recordResultList=super.getSimRecordMlcs(first,second);//对应相似行
            float simValue=super.getSimValue();//相似度
            return new CompareResult(simValue,recordResultList);

        }
        return null;
    }

    /**
     * 两个List进行比对
     *
     * @param list1 ，参与比对的第一个List<LineStruct>
     * @param list2 ，参与比对的第二个List<LineStruct>
     * @return
     * @throws Exception
     */
    @Override
    public CompareResult compareAndRecord(List<LineStruct> list1, List<LineStruct> list2) throws Exception {
        // TODO Auto-generated method stub
        if(this.granularity == Granularity.BLOCK) {//分块比对
            List<SimRecord> recordResultList = super.getSimRecordBlock(list1,list2,blocksize);//对应相似行
            float simValue = super.getSimValue();//相似度
            return new CompareResult(simValue,recordResultList);
        }else if(this.granularity == Granularity.MLCS) {//匹配字符串比对
            List<SimRecord> recordResultList=super.getSimRecordMlcs(list1,list2);//对应相似行
            float simValue=super.getSimValue();//相似度
            return new CompareResult(simValue,recordResultList);
        }
        return null;
    }

    /**
     * 两个List进行比对
     *
     * @param list1
     * @param list2
     * @return
     * @throws Exception
     */
    @Override
    public CompareResult compareAndRecordSyntax(List<StoreNode> list1, List<StoreNode> list2) throws Exception {
        return null;
    }

}
