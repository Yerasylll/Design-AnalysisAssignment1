package com.sortTesting;

import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.QuickSort;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {

    @Test
    public void testSmallArray() {
        int[] arr = {9, 5, 1, 6, 2, 3};
        int[] expected = {1, 2, 3, 5, 6, 9};

        MetricsCollector metrics = new MetricsCollector();
        QuickSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("QuickSort small case: comparisons=%d, depth=%d time=%.3f ms%n",
                metrics.comparisons().getComp(),
                metrics.depth().maxDepth(),
                metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testRandomArray() {
        Random rand = new Random(99);
        int[] arr = rand.ints(10_000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        MetricsCollector metrics = new MetricsCollector();
        QuickSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("QuickSort large case: comparisons=%d, depth=%d, time=%.3f ms%n",
                metrics.comparisons().getComp(), metrics.depth().maxDepth(), metrics.time().elapsedTimeInMillis());
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = {5, 6, 7, 8, 9};
        int[] expected = arr.clone();

        MetricsCollector metrics = new MetricsCollector();
        QuickSort.sort(arr, metrics);

        Assert.assertArrayEquals(expected, arr);
        System.out.printf("QuickSort sorted case: comparisons=%d, depth=%d, time=%.3f ms%n",
                metrics.comparisons().getComp(), metrics.depth().maxDepth(), metrics.time().elapsedTimeInMillis());
    }
}
