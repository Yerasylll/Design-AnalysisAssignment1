package com.sortTesting;

import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.DeterministicSelect;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    @Test
    public void testSmallArray() {
        int[] arr = {7, 1, 6, 4, 5, 3, 2};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        int k = 2;
        MetricsCollector metrics = new MetricsCollector();
        int val = DeterministicSelect.select(arr, k, metrics);

        Assert.assertEquals(sorted[k], val);
        System.out.printf("Select small case: k=%d, v" +
                        "alue=%d, " +
                        "comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                k, val,
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testRandomArray() {
        Random rand = new Random(123);
        int[] arr = rand.ints(1000, -1000, 1000).toArray();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        int k = 500;
        MetricsCollector metrics = new MetricsCollector();
        int val = DeterministicSelect.select(arr, k, metrics);

        Assert.assertEquals(sorted[k], val);
        System.out.printf("Select random case: k=%d, " +
                        "value=%d, " +
                        "comparisons=%d, depth=%d, " +
                        "time=%.3f ms%n",
                k, val,
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }
}
