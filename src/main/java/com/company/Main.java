package com.company;

import com.company.metricsAndCsv.csvWriter.CsvWriter;
import com.company.metricsAndCsv.metrics.MetricsCollector;
import com.company.sorts.ClosestPair;
import com.company.sorts.DeterministicSelect;
import com.company.sorts.MergeSort;
import com.company.sorts.QuickSort;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int maxN = 20000;
        int step = 2000;
        int trials = 5;

        CsvWriter writer = null;

        try {
            writer = new CsvWriter("results.csv");
            writer.writeHeader("algorithm", "n", "trial", "timeMillis", "comparisons", "maxDepth");

            Random rnd = new Random(123);

            for (int n = step; n <= maxN; n += step) {
                for (int t = 0; t < trials; t++) {
                    int[] arr = rnd.ints(n, -100000, 100000).toArray();
                    int[] copy;
                    MetricsCollector m = new MetricsCollector();

                    // MergeSort
                    copy = Arrays.copyOf(arr, arr.length);
                    MergeSort.sort(copy, m);
                    writer.writeRow("MergeSort", n, t,
                            m.time().elapsedTimeInMillis(), m.comparisons().getComp(), m.depth().maxDepth());

                    // QuickSort
                    copy = Arrays.copyOf(arr, arr.length);
                    m.reset();
                    QuickSort.sort(copy, m);
                    writer.writeRow("QuickSort", n, t,
                            m.time().elapsedTimeInMillis(), m.comparisons().getComp(), m.depth().maxDepth());

                    // Deterministic Select (median element)
                    copy = Arrays.copyOf(arr, arr.length);
                    m.reset();
                    int median = DeterministicSelect.select(copy, copy.length / 2, m);
                    writer.writeRow("DeterministicSelect", n, t,
                            m.time().elapsedTimeInMillis(), m.comparisons().getComp(), m.depth().maxDepth());

                    // Closest Pair
                    Point[] pts = new Point[n];
                    for (int i = 0; i < n; i++) {
                        pts[i] = new Point((int) (rnd.nextDouble() * 1000), (int) (rnd.nextDouble() * 1000));
                    }
                    m.reset();
                    double d = ClosestPair.closestPairDistance(pts, m);
                    writer.writeRow("ClosestPair", n, t,
                            m.time().elapsedTimeInMillis(), m.comparisons().getComp(), m.depth().maxDepth());
                }
            }

            System.out.println("Benchmark finished. Results written to results.csv");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}