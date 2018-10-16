package com.bupt.cmp.pretreat.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 预处理-行信息类
 * Created by hxg on 2017/9/24.
 */
public class LineStruct implements Serializable {
    private static final long serialVersionUID = -4025333388653598042L;
    private int lineNum;// 行号
    private int hashValue;// 行hash值

    public LineStruct() {

    }

    public LineStruct(int linenum, int hashvalue) {
        this.lineNum = linenum;
        this.hashValue = hashvalue;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getHashValue() {
        return hashValue;
    }

    public void setHashValue(int hashValue) {
        this.hashValue = hashValue;
    }

    @Override
    public String toString() {
        return lineNum + ":" + hashValue;

    }

    /**
     * LineStruct转换为String
     * @param linestruct
     * @return
     */
    public static String LineStructToString(List<LineStruct> linestruct) {
        String arraystring = new String();
        for (int i = 0; i < linestruct.size(); i++) {
            arraystring = arraystring + "," + linestruct.get(i).toString();
        }
        return arraystring.replaceAll(" ", "");// 替换掉字符串中的空格
    }

    /**
     * String转换为LineStruct
     * @param linestruct
     * @return
     */
    public static List<LineStruct> StringToLineStruct(String linestruct) {
        List<LineStruct> retLinestruct = new ArrayList<LineStruct>();
        String[] array;
        if (linestruct != null)
            array = linestruct.split(",");// 行记录对数
        else
            return null;

        for (int i = 1; i < array.length; i++) {
            LineStruct temp = new LineStruct();
            String[] t = array[i].split(":");
            temp.setLineNum(Integer.parseInt(t[0]));
            temp.setHashValue(Integer.parseInt(t[1]));
            retLinestruct.add(temp);
        }
        return retLinestruct;
    }

}