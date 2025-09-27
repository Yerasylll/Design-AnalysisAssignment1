package com.company.metricsAndCsv.metrics;

public class MetricsCollector {
    private final TimeMetric time = new TimeMetric();
    private final ComparisonMetric comparisons = new ComparisonMetric();
    private final DepthMetric depth = new DepthMetric();

    public TimeMetric time() {
        return time;
    }
    public ComparisonMetric comparisons() {
        return comparisons;
    }
    public DepthMetric depth() {
        return depth;
    }

    public void reset() {
        time.reset();
        comparisons.reset();
        depth.reset();
    }
}
