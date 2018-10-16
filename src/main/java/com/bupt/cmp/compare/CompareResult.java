package com.bupt.cmp.compare;

import java.util.List;

/**
 * Created by bearsmall on 2017/9/25.
 */
public class CompareResult {
    private float simvalue;
    private List<SimRecord> simRecords;

    public CompareResult(float simvalue, List<SimRecord> simRecords) {
        this.simvalue = simvalue;
        this.simRecords = simRecords;
    }

    @Override
    public String toString() {
        return "CompareResult{" +
                "simvalue=" + simvalue +
                ", simRecords=" + simRecords +
                '}';
    }

    public float getSimvalue() {
        return simvalue;
    }

    public void setSimvalue(float simvalue) {
        this.simvalue = simvalue;
    }

    public List<SimRecord> getSimRecords() {
        return simRecords;
    }

    public void setSimRecords(List<SimRecord> simRecords) {
        this.simRecords = simRecords;
    }
}
