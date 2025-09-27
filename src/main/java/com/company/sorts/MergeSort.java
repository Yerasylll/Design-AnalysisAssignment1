package com.company.sorts;

import com.company.metricsAndCsv.metrics.ComparisonMetric;
import com.company.metricsAndCsv.metrics.MetricsCollector;

public class MergeSort {
    private static final int CUTOFF = 24;

    public static void sort(int[] arr, MetricsCollector metrics) {
        if (arr == null) return;
        metrics.reset();
        metrics.time().start();
        int[] buf = new int[arr.length];
        sortRange(arr, buf, 0, arr.length-1, metrics);
        metrics.time().stop();
    }

    private static void sortRange(int[] a, int[] buf, int lo, int hi, MetricsCollector metrics) {
        if (lo >= hi) return;
        metrics.depth().enter();
        try {
            if (hi - lo + 1 <= CUTOFF) {
                insertionSort(a, lo, hi, metrics.comparisons());
                return;
            }
            int mid = lo + (hi -lo) / 2;
            sortRange(a, buf, lo, mid, metrics);
            sortRange(a, buf, mid + 1, hi, metrics);
            merge(a, buf, lo, mid, hi, metrics.comparisons());
        } finally {
            metrics.depth().exit();
        }
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi, ComparisonMetric cm) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <=hi) {
            cm.inc();
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        while (i <= mid) buf[k++] = a[i++];
        while (j <= hi) buf[k++] = a[j++];
        for(int t = lo; t <= hi; t++) {
            a[t] = buf[t];
        }
    }

    private static void insertionSort(int[] a, int lo, int hi, ComparisonMetric cm) {
        for(int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while(j >= lo) {
                cm.inc();
                if(a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}
