package com.company.sorts;

import com.company.metricsAndCsv.metrics.ComparisonMetric;
import com.company.metricsAndCsv.metrics.MetricsCollector;

import java.util.concurrent.ThreadLocalRandom;


public class QuickSort {
    public static void sort(int[] arr, MetricsCollector metrics) {
        if (arr == null) return;
        metrics.reset();
        metrics.time().start();
        quickSort(arr, 0, arr.length - 1, metrics);
        metrics.time().stop();
    }

    private static void quickSort(int[] arr, int lo, int hi, MetricsCollector metrics) {
        while (lo < hi) {
            metrics.depth().enter();
            try {
                int pivotIndex = ThreadLocalRandom.current().nextInt(lo, hi + 1);
                int p = partition(arr, lo, hi, pivotIndex, metrics.comparisons());

                if (p - lo < hi - p) {
                    quickSort(arr, lo, p - 1, metrics);
                    lo = p + 1;
                } else {
                    quickSort(arr, p + 1, hi, metrics);
                    hi = p - 1;
                }
            } finally {
                metrics.depth().exit();
            }
        }
    }

    private static int partition(int[] arr, int lo, int hi, int pivotIndex, ComparisonMetric cm) {
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, hi);
        int i = lo - 1;
        for(int j = lo; j < hi; j++) {
            cm.inc();
            if(arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, hi);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
