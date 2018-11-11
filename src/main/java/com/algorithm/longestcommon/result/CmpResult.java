package com.algorithm.longestcommon.result;

import java.util.List;

public class CmpResult {
    private List<SimiBlock> simiBlockList;
    private int srcLine;
    private int targetLine;

    private int srcCoverLine;
    private int targetCoverLine;

    private float simiValue;

    public List<SimiBlock> getSimiBlockList() {
        return simiBlockList;
    }

    public void setSimiBlockList(List<SimiBlock> simiBlockList) {
        this.simiBlockList = simiBlockList;
    }

    public float getSimiValue() {
        return simiValue;
    }

    public void setSimiValue(float simiValue) {
        this.simiValue = simiValue;
    }

    public int getSrcLine() {
        return srcLine;
    }

    public void setSrcLine(int srcLine) {
        this.srcLine = srcLine;
    }

    public int getTargetLine() {
        return targetLine;
    }

    public void setTargetLine(int targetLine) {
        this.targetLine = targetLine;
    }

    public int getSrcCoverLine() {
        return srcCoverLine;
    }

    public void setSrcCoverLine(int srcCoverLine) {
        this.srcCoverLine = srcCoverLine;
    }

    public int getTargetCoverLine() {
        return targetCoverLine;
    }

    public void setTargetCoverLine(int targetCoverLine) {
        this.targetCoverLine = targetCoverLine;
    }

    public CmpResult(List<SimiBlock> simiBlockList, int srcLine, int targetLine, int srcCoverLine, int targetCoverLine) {
        this.simiBlockList = simiBlockList;
        this.srcLine = srcLine;
        this.targetLine = targetLine;
        this.srcCoverLine = srcCoverLine;
        this.targetCoverLine = targetCoverLine;
        this.simiValue = 1.0f*(srcCoverLine+targetCoverLine)/(srcLine+targetLine);
    }

    @Override
    public String toString() {
        return "CmpResult{" +
                "simiBlockList=" + simiBlockList +
                ", srcLine=" + srcLine +
                ", targetLine=" + targetLine +
                ", srcCoverLine=" + srcCoverLine +
                ", targetCoverLine=" + targetCoverLine +
                ", simiValue=" + simiValue +
                '}';
    }
}
