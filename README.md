# Design and Analysis Assignment 1

## Project Overview
This project implements and analyzes several algorithms, focusing on their asymptotic behavior, recursion depth, and empirical performance. Both **theoretical recurrence analysis** and **experimental results** are included.

---

## Architecture Notes
- Algorithms are implemented in a **recursive divide-and-conquer** style (except deterministic select, which uses partition-based recursion).
- **Depth control:**
    - Base cases ensure recursion halts when subproblem size ≤ 1.
    - Recursion depth depends on the branching factor (`log n` for divide-and-conquer, linear in worst case for select/quick sort).
- **Memory allocation:**
    - Merge Sort allocates temporary arrays at each level.
    - Quick Sort and Deterministic Select partition in place.
    - Closest Pair uses auxiliary arrays for splitting and merging.

---

## Recurrence Analysis

### 1. Merge Sort
- **Recurrence:** `T(n) = 2T(n/2) + Θ(n)`
- Solved with **Master Theorem, Case 2** → `Θ(n log n)`.
- Depth: `log n`.
- Constant factors come from extra memory allocations at each merge.

---

### 2. Quick Sort
- **Average case recurrence:** `T(n) = 2T(n/2) + Θ(n)` → `Θ(n log n)`.
- **Worst case:** `T(n) = T(n-1) + Θ(n)` → `Θ(n^2)`.
- Master Theorem applies to the average case.
- Depth varies: `log n` (balanced partitions) vs. `n` (skewed).
- Pivot choice strongly affects runtime constants.

---

### 3. Deterministic Select (Median of Medians)
- **Recurrence:** `T(n) = T(n/5) + T(7n/10) + Θ(n)`
- Solved using **Akra–Bazzi Theorem** → `Θ(n)`.
- Depth: proportional to `log n` (shrinks by a constant fraction each step).
- Practical performance worse than Randomized Select due to large constant factors in partitioning.

---

### 4. Closest Pair of Points (Divide and Conquer)
- **Recurrence:** `T(n) = 2T(n/2) + Θ(n)`
- Solves to `Θ(n log n)` using Master Theorem.
- Depth: `log n`.
- Constants influenced by sorting and the merge step, but efficient in practice for large inputs.

---

## Plots and Experimental Results
- **Time vs Input Size (n):**
    - Merge Sort & Quick Sort (average) → `n log n` growth.
    - Deterministic Select → linear growth (`Θ(n)`) but with larger slope (higher constants).
    - Closest Pair → `n log n` growth, similar to Merge Sort.

- **Depth vs Input Size (n):**
    - Merge Sort & Closest Pair: `log n`.
    - Quick Sort: between `log n` and `n` depending on pivoting.
    - Deterministic Select: ~`log n`.

- **Constant-Factor Effects:**
    - Merge Sort benefits from predictable recursion but suffers from cache misses due to extra arrays.
    - Quick Sort’s constants are small when partitions are balanced, but large otherwise.
    - Deterministic Select has guaranteed linear time but heavy constants from median-of-medians grouping.
    - Closest Pair has efficient cache behavior but sorting steps add noticeable overhead.

---

## To sum up
- **Theory vs Practice Alignment:**
    - Merge Sort and Closest Pair strongly align with `Θ(n log n)` predictions.
    - Quick Sort average-case matches `Θ(n log n)` theory, but worst-case performance deviates significantly.
    - Deterministic Select demonstrates the predicted `Θ(n)` scaling, but constant factors make it slower in practice than randomized alternatives.

- **Key Takeaways:**
    - Theoretical asymptotics capture long-term scaling trends, but **constants matter** for practical performance.
    - Depth analysis provides insights into recursion safety and memory use.
    - Experimental plots confirm mismatches between guaranteed worst-case bounds (Deterministic Select) and real-world efficiency.
