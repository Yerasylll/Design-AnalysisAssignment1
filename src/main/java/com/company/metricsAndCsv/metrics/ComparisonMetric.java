package com.company.metricsAndCsv.metrics;

public class ComparisonMetric {
    private long comparisons = 0;

    public void inc() {
        comparisons++;
    }
    public void inc(long delta) {
        comparisons += delta;
    }
    public long getComp() {
        return comparisons;
    }
    public void reset() {
        comparisons = 0;
    }
}
