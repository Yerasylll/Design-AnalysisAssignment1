package com.sortTesting;

import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.ClosestPair;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Random;

public class ClosestPairTest {
    @Test
    public void testSmallPoints() {
        Point[] pts = {
                new Point(0, 0),
                new Point(3, 4),
                new Point(7, 9),
                new Point(1, 1)
        };

        MetricsCollector metrics = new MetricsCollector();
        double dist = ClosestPair.closestPairDistance(pts, metrics);

        Assert.assertEquals(Math.sqrt(2), dist, 1e-9);
        System.out.printf("ClosestPair small case: distance=%.3f, " +
                        "comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                dist,
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testRandomPoints() {
        Random rand = new Random(42);
        Point[] pts = new Point[100];
        for(int i = 0; i < pts.length; i++) {
            pts[i] = new Point((int) (rand.nextDouble() * 1000), (int) (rand.nextDouble() * 1000));
        }

        MetricsCollector metrics = new MetricsCollector();
        double dist = ClosestPair.closestPairDistance(pts, metrics);

        Assert.assertTrue(dist > 0);
        System.out.printf("ClosestPair random case: " +
                        "distance=%.3f, " +
                        "comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                dist, metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }
}
