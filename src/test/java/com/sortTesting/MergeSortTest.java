package com.sortTesting;

import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.MergeSort;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class MergeSortTest {
    @Test
    public void testSmallArray() {
        int[] arr = {5, 1, 8, 2, 9, 3, 6, 4, 7};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        MetricsCollector metrics = new MetricsCollector();
        MergeSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("MergeSort small case: comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testRandomLargeArray() {
        Random rand = new Random(42);
        int[] arr = rand.ints(10_000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        MetricsCollector metrics = new MetricsCollector();
        MergeSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("MergeSort large case: comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        int[] expected = arr.clone();

        MetricsCollector metrics = new MetricsCollector();
        MergeSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("MergeSort sorted case: comparisons=%d, " +
                        "depth=%d, " +
                        "time=%.3f ms%n",
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }
}
