package com.cmp.lang;

import com.cmp.DefaultCodeFile;

public interface Language{
    /**
     * @return 本语言的所有关键字(按字典序排序后的数组)
     */
    public String[] getKeyWordsArray();
    /**
     * @return 本语言的名称
     */
    public String getName();
    /**
     * 对给定的一个代码文件进行分析，提取出其令牌序列，并以hash值数组的形式返回
     * @param defaultCodeFile 需要进行分析的代码文件
     * @return 表示代码文件token序列的hash值数组
     * @throws Exception
     */
    public int[] getTokenSequence(DefaultCodeFile defaultCodeFile) throws Exception;
    /**
     * 对给定的一个代码文件进行分析，提取出其令牌序列，并以hash值数组的形式返回
     * @param defaultCodeFile 需要进行分析的代码文件
     * @return 表示代码文件token序列的hash值数组
     * @throws Exception
     */
    public boolean getLineTokenSequence(DefaultCodeFile defaultCodeFile) throws Exception;
}