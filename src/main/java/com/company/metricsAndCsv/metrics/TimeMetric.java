package com.company.metricsAndCsv.metrics;

public class TimeMetric {
    private double start = 0;
    private double elapsed = 0;

    public void start() {
        start = System.currentTimeMillis();
    }
    public void stop() {
        elapsed = System.currentTimeMillis() - start;
    }
    public double elapsedTimeInMillis() {
        return elapsed;
    }
    public void reset() {
        start = 0; elapsed = 0;
    }
}
