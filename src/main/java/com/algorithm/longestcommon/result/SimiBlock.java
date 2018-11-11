package com.algorithm.longestcommon.result;

public class SimiBlock {
    int sbegin; //源开始坐标
    int send;   //源结束坐标

    int tbegin; //目标开始坐标
    int tend;   //目标结束坐标

    public SimiBlock(int sbegin, int send, int tbegin, int tend) {
        this.sbegin = sbegin;
        this.send = send;
        this.tbegin = tbegin;
        this.tend = tend;
    }

    public int getSbegin() {
        return sbegin;
    }

    public void setSbegin(int sbegin) {
        this.sbegin = sbegin;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public int getTbegin() {
        return tbegin;
    }

    public void setTbegin(int tbegin) {
        this.tbegin = tbegin;
    }

    public int getTend() {
        return tend;
    }

    public void setTend(int tend) {
        this.tend = tend;
    }
}
