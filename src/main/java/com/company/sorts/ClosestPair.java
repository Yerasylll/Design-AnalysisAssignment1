package com.company.sorts;

import com.company.metricsAndCsv.metrics.ComparisonMetric;
import com.company.metricsAndCsv.metrics.MetricsCollector;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double closestPairDistance(Point[] pts, MetricsCollector metrics) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        metrics.reset();
        metrics.time().start();
        Point[] byX = pts.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] byY = pts.clone();
        Arrays.sort(byY, Comparator.comparingDouble(p -> p.y));
        double d = rec(byX, byY, 0, pts.length - 1, metrics);
        metrics.time().stop();
        return d;
    }


    private static double rec(Point[] byX, Point[] byY, int lo, int hi, MetricsCollector metrics) {
        int n = hi - lo + 1;
        metrics.depth().enter();
        try {
            if (n <=  3) return bruteForce(byX, lo, hi, metrics.comparisons());

            int mid = lo + n / 2;
            double midX = byX[mid].x;

            Point[] leftY = new Point[mid - lo + 1];
            Point[] rightY = new Point[hi - mid];
            int li = 0, ri = 0;
            for(Point p : byX) {
                if(p.x <= midX && li < leftY.length) {
                    leftY[li++] = p;
                } else if(ri < rightY.length) {
                    rightY[ri++] = p;
                }
            }

            double dl = rec(byX, leftY, lo, mid, metrics);
            double dr = rec(byX, rightY, mid + 1, hi, metrics);
            double d = Math.min(dl, dr);

            Point[] strip = new Point[n];
            int cnt = 0;
            for(Point p : byY) {
                if(Math.abs(p.x - midX) < d) {
                    strip[cnt++] = p;
                }
            }

            for(int i = 0; i < cnt; i++) {
                for(int j = i + 1; j < cnt && (strip[j].y - strip[i].y) < d; j++) {
                    metrics.comparisons().inc();
                    double dist = dist(strip[i], strip[j]);
                    if(dist < d) d = dist;
                }
            }

            return d;
        } finally {
            metrics.depth().exit();
        }
    }

    private static double bruteForce(Point[] arr, int lo, int hi, ComparisonMetric cm) {
        double best = Double.POSITIVE_INFINITY;
        for(int i =lo; i <= hi; i++) {
            for(int j = i + 1; j <= hi; j++) {
                cm.inc();
                double d = dist(arr[i], arr[j]);
                if(d < best) best = d;
            }
        }
        return best;
    }

    private static double dist(Point p, Point q) {
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.hypot(dx, dy);
    }
}
