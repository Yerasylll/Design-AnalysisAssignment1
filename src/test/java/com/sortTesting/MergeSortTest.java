package com.sortTesting;

import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.MergeSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class MergeSortTest {
    @Test
    public void randomAndReveredArrays() {
        Random rand = new Random(123);

        // Randomized test cases
        for(int i = 0; i < 10; i++) {
            int n = 1 + rand.nextInt(200);
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int[] expected = Arrays.copyOf(arr, arr.length);
            Arrays.sort(expected);

            MetricsCollector metrics = new MetricsCollector();
            MergeSort.sort(arr, metrics);

            assertArrayEquals(expected, arr);
            assertTrue(metrics.comparisons().getComp() > 0);
        }

        // Adversarial: Reversed order
        int[] rev = new int[200];
        for(int i = 0; i < rev.length; i++) {
            rev[i] = rev.length - i;
        }
        MetricsCollector metrics = new MetricsCollector();
        MergeSort.sort(rev, metrics);

        for(int i = 1; i < rev.length; i++) {
            assertTrue(rev[i - 1] <= rev[i]);
        }
    }
}
