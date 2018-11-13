package com.algorithm.suffixarray.result;

public class SuffixResult {
    private int beginIndex1;
    private int beginIndex2;

    private int length1;
    private int length2;

    private int commonLength;

    private double simiValue;

    public int getBeginIndex1() {
        return beginIndex1;
    }

    public void setBeginIndex1(int beginIndex1) {
        this.beginIndex1 = beginIndex1;
    }

    public int getBeginIndex2() {
        return beginIndex2;
    }

    public void setBeginIndex2(int beginIndex2) {
        this.beginIndex2 = beginIndex2;
    }

    public int getLength1() {
        return length1;
    }

    public void setLength1(int length1) {
        this.length1 = length1;
    }

    public int getLength2() {
        return length2;
    }

    public void setLength2(int length2) {
        this.length2 = length2;
    }

    public int getCommonLength() {
        return commonLength;
    }

    public void setCommonLength(int commonLength) {
        this.commonLength = commonLength;
    }

    public double getSimiValue() {
        return commonLength*2.0/(length1+length2);
    }

    @Override
    public String toString() {
        return "SuffixResult{" +
                "beginIndex1=" + beginIndex1 +
                ", beginIndex2=" + beginIndex2 +
                ", length1=" + length1 +
                ", length2=" + length2 +
                ", commonLength=" + commonLength +
                ", simiValue=" + simiValue +
                '}';
    }
}
