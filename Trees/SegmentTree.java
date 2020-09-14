/**
 * Simple Segment Tree Implementation for SUM , MIN , MAX operations
 * 
 * PS: https://cp-algorithms.com/data_structures/segment_tree.html , WilliamFiset
 * 
 * TC : Build - O(N) ; Query - O(logN) ; Update - O(logN)
 * SC : O(4N)
 */
package Trees;

import java.util.function.BinaryOperator;

public class SegmentTree {

	// The type of segment combination function to use
	public static enum SegmentCombineFn {
		SUM, MIN, MAX
	}

	private SegmentCombineFn segmentCombineFn;

	// The chosen range combination function
	private BinaryOperator<Long> combinationFn;

	private BinaryOperator<Long> sumFn = (a, b) -> a + b;
	private BinaryOperator<Long> minFn = (a, b) -> Math.min(a, b);
	private BinaryOperator<Long> maxFn = (a, b) -> Math.max(a, b);

	// The number of elements in the original input values array.
	private int n;

	// The segment tree represented as a binary tree of ranges where t[0] is the
	// root node and the left and right children of node i are i*2+1 and i*2+2.
	private long[] t;

	public SegmentTree(long[] values, SegmentCombineFn combineFn) {
		if (values == null) {
			throw new IllegalArgumentException("Segment tree values cannot be null.");
		}
		if (combineFn == null) {
			throw new IllegalArgumentException("Please specify a valid segment combination function.");
		}

		n = values.length;
		this.segmentCombineFn = combineFn;

		switch (this.segmentCombineFn) {
		case SUM:
			combinationFn = sumFn;
			break;
		case MIN:
			combinationFn = minFn;
			break;
		case MAX:
			combinationFn = maxFn;
			break;

		}

		// The size of the segment tree `t`
		int N = 4 * n;
		t = new long[N];

		buildSegmentTree(0, 0, n - 1, values);
	}

	/**
	 * Builds a segment tree by starting with the leaf nodes and combining segment
	 * values on callback.
	 *
	 * @param i      the index of the segment in the segment tree
	 * @param tl     the left index (inclusive) of the segment range
	 * @param tr     the right index (inclusive) of the segment range
	 * @param values the initial values array
	 */
	private void buildSegmentTree(int i, int tl, int tr, long[] values) {
		if (tl == tr) {
			t[i] = values[tl];
			return;
		}

		int tm = (tl + tr) / 2;
		buildSegmentTree(2 * i + 1, tl, tm, values);
		buildSegmentTree(2 * i + 2, tm + 1, tr, values);
		t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
	}

	/**
	 * Returns the query of the range [l, r] on the original `values` array (+ any
	 * updates made to it)
	 *
	 * @param l the left endpoint of the range query (inclusive)
	 * @param r the right endpoint of the range query (inclusive)
	 */
	public long rangeQuery(int l, int r) {
		return rangeQuery(0, 0, n - 1, l, r);
	}

	/**
	 * Returns the range query value of the range [l, r]
	 *
	 * @param i  the index of the current segment in the tree
	 * @param tl the left endpoint (inclusive) of the current segment
	 * @param tr the right endpoint (inclusive) of the current segment
	 * @param l  the target left endpoint (inclusive) for the range query
	 * @param r  the target right endpoint (inclusive) for the range query
	 */
	private long rangeQuery(int i, int tl, int tr, int l, int r) {
		if (l > r) {
			// Different segment tree types have different base cases:
			if (segmentCombineFn == segmentCombineFn.SUM) {
				return 0;
			} else if (segmentCombineFn == segmentCombineFn.MIN) {
				return Long.MAX_VALUE;
			} else if (segmentCombineFn == segmentCombineFn.MAX) {
				return Long.MIN_VALUE;
			}
		}
		if (l == tl && r == tr)
			return t[i];
		int tm = (tl + tr) / 2;
		// Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
		// [l, r], simply recurse on both segments and let the base case return the
		// default value for invalid intervals.
		return combinationFn.apply(rangeQuery(2 * i + 1, tl, tm, l, Math.min(tm, r)),
				rangeQuery(2 * i + 2, tm + 1, tr, Math.max(tm + 1, l), r));
	}

	/**
	 * Updates the value at index `i` in the original `values` array to be
	 * `newValue`.
	 * 
	 * @param pos      the target position to update
	 * @param newValue the new value to update
	 */
	public void pointUpdate(int pos, long newValue) {
		pointUpdate(0, 0, n - 1, pos, newValue);
	}

	/**
	 * Update a point value to a new value and update all affected segments,
	 * O(log(n))
	 *
	 * <p>
	 * Do this by performing a binary search to find the interval containing the
	 * point, then update the leaf segment with the new value, and re-compute all
	 * affected segment values on the callback.
	 *
	 * @param i        the index of the current segment in the tree
	 * @param pos      the target position to update
	 * @param tl       the left segment endpoint (inclusive)
	 * @param tr       the right segment endpoint (inclusive)
	 * @param newValue the new value to update
	 */
	private void pointUpdate(int i, int tl, int tr, int pos, long newValue) {
		if (tl == tr) { // We've reached the position to be updated
			t[i] = newValue;
			return;
		}

		int tm = (tl + tr) / 2;
		if (pos <= tm) // The point index `pos` is contained within the left segment [tl, tm]
			pointUpdate(2 * i + 1, tl, tm, pos, newValue);
		else // The point index `pos` is contained within the right segment [tm+1, tr]
			pointUpdate(2 * i + 2, tm + 1, tr, pos, newValue);

		// Re-compute the segment value of the current segment on the callback
		t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
	}

	////////////////////////////////////////////////////
	// Example usage: //
	////////////////////////////////////////////////////

	public static void main(String[] args) {
		rangeSumQuery();
		rangeMinQuery();
		rangeMaxQuery();
	}

	private static void rangeSumQuery() {
		// 0 1 2 3 4 5 6 7
		long[] values = { 1, 2, 3, 2, 4, 5, 1, 2 };
		SegmentTree st = new SegmentTree(values, SegmentCombineFn.SUM);

		int l = 0, r = 3;
		System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
		st.pointUpdate(2, 5);
		System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
	}

	private static void rangeMinQuery() {
		// 0 1 2 3 4 5 6 7
		long[] values = { 1, 2, 3, 2, 4, 5, 1, 2 };
		SegmentTree st = new SegmentTree(values, SegmentCombineFn.MIN);

		int l = 0, r = 3;
		System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
		st.pointUpdate(2, 5);
		System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
	}

	private static void rangeMaxQuery() {
		// 0 1 2 3 4 5 6 7
		long[] values = { 1, 2, 3, 2, 4, 5, 1, 2 };
		SegmentTree st = new SegmentTree(values, SegmentCombineFn.MAX);

		int l = 0, r = 3;
		System.out.printf("The max between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
		st.pointUpdate(2, 5);
		System.out.printf("The max between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
	}

}
