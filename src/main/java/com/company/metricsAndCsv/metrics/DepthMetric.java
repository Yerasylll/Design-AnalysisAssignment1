package com.company.metricsAndCsv.metrics;

public class DepthMetric {
    private int currentDepth = 0;
    private int maxDepth = 0;

    public void enter() {
        currentDepth++;
        if(currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }
    public void exit() {
        currentDepth--;
    }
    public int maxDepth() {
        return maxDepth;
    }
    public void reset() {
        currentDepth = 0; maxDepth = 0;
    }
}
