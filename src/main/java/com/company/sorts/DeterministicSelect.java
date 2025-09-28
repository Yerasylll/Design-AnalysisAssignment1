package com.company.sorts;

import com.company.metricsAndCsv.metrics.ComparisonMetric;
import com.company.metricsAndCsv.metrics.MetricsCollector;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] arr, int k, MetricsCollector metrics) {
        if(arr == null || k < 0 || k >= arr.length) throw new IllegalArgumentException();
        metrics.reset();
        metrics.time().start();
        int res = selectRange(arr, 0, arr.length - 1, k, metrics);
        metrics.time().stop();
        return res;
    }

    private static int selectRange(int[] arr, int lo, int hi, int k, MetricsCollector metrics) {
        while(lo <= hi) {
            metrics.depth().enter();
            try {
                if (lo == hi) return arr[lo];
                int pivotIndex = medianOfMedians(arr, lo, hi, metrics);
                int pivotFinal = partition(arr, lo, hi, pivotIndex, metrics.comparisons());
                if(k == pivotFinal) return arr[k];
                else if(k < pivotFinal) hi = pivotFinal - 1;
                else lo = pivotFinal + 1;
            } finally {
                metrics.depth().exit();
            }
        }
        throw new RuntimeException("Unreachable");

    }

    private static int medianOfMedians(int[] arr, int lo, int hi, MetricsCollector metrics) {
        int n = hi - lo + 1;
        if(n <= 5) {
            Arrays.sort(arr, lo, hi + 1);
            return lo + n / 2;
        }
        int numMedians = 0;
        for(int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            Arrays.sort(arr, i, subHi + 1);
            int medianIdx = i + (subHi - i) / 2;
            swap(arr, lo + numMedians, medianIdx);
            numMedians++;
        }
        return selectIndex(arr, lo, lo + numMedians -1, lo + numMedians/2, metrics);
    }

    private static int selectIndex(int[] arr, int lo, int hi, int kIndex, MetricsCollector metrics) {
        while(lo <= hi) {
            metrics.depth().enter();
            try {
                if (lo == hi) return lo;
                int pivotIndex = medianOfMedians(arr, lo, hi, metrics);
                int pivotFinal = partition(arr, lo, hi, pivotIndex, metrics.comparisons());
                if (kIndex == pivotFinal) return pivotFinal;
                else if (kIndex < pivotFinal) hi = pivotFinal - 1;
                else lo = pivotFinal + 1;
            } finally {
                metrics.depth().exit();
            }
        }
        return lo;
    }

    private static int partition(int[] arr, int lo, int hi, int pivotIndex, ComparisonMetric cm) {
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, hi);
        int store = lo;
        for(int i = lo; i < hi; i++) {
            cm.inc();
            if(arr[i] < pivot) {
                swap(arr, store, i);
                store++;
            }
        }
        swap(arr, store, hi);
        return store;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
