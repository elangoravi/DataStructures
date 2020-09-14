package Trees;

public class MaxSubArraySumSegmentTree {

	static class Node {
		long sum, prefixSum, suffixSum, maxSum;
	}

	private int n;
	private Node[] t;

	public MaxSubArraySumSegmentTree(long[] values) {
		if (values == null) {
			throw new IllegalArgumentException("Segment tree values cannot be null.");
		}
		n = values.length;

		// The size of the segment tree `t`
		int N = 4 * n;
		t = new Node[N];

		for (int i = 0; i < N; i++)
			t[i] = new Node();

		buildSegmentTree(0, 0, n - 1, values);
	}

	private void combine(Node left, Node right, int updatePos) {
		Node n = t[updatePos];
		n.sum = left.sum + right.sum;
		n.prefixSum = Math.max(left.prefixSum, left.sum + right.prefixSum);
		n.suffixSum = Math.max(right.suffixSum, right.sum + left.suffixSum);
		n.maxSum = Math.max(Math.max(left.maxSum, right.maxSum), left.suffixSum + right.prefixSum);
	}

	private void updateLeaf(long val, int updatePos) {
		Node n = t[updatePos];
		n.sum = val;
		n.prefixSum = n.suffixSum = n.maxSum = Math.max(0, val);
	}

	private void buildSegmentTree(int i, int tl, int tr, long[] values) {
		if (tl == tr) {
			updateLeaf(values[tl], i);
			return;
		}

		int tm = (tl + tr) / 2;
		buildSegmentTree(2 * i + 1, tl, tm, values);
		buildSegmentTree(2 * i + 2, tm + 1, tr, values);
		combine(t[2 * i + 1], t[2 * i + 2], i);
	}

	public long rangeQuery(int l, int r) {
		return rangeQuery(0, 0, n - 1, l, r).maxSum;
	}

	private Node rangeQuery(int i, int tl, int tr, int l, int r) {
		if (l > r) {
			return new Node();
		}
		if (l == tl && r == tr)
			return t[i];
		int tm = (tl + tr) / 2;
		combine(rangeQuery(2 * i + 1, tl, tm, l, Math.min(tm, r)),
				rangeQuery(2 * i + 2, tm + 1, tr, Math.max(tm + 1, l), r), i);
		return t[i];
	}

	public void pointUpdate(int pos, long newValue) {
		pointUpdate(0, 0, n - 1, pos, newValue);
	}

	private void pointUpdate(int i, int tl, int tr, int pos, long newValue) {
		if (tl == tr) { // We've reached the position to be updated
			updateLeaf(newValue, i);
			return;
		}

		int tm = (tl + tr) / 2;
		if (pos <= tm)
			pointUpdate(2 * i + 1, tl, tm, pos, newValue);
		else
			pointUpdate(2 * i + 2, tm + 1, tr, pos, newValue);

		combine(t[2 * i + 1], t[2 * i + 2], i);
	}

	public static void main(String[] args) {
		// 0 1 2 3 4 5 6 7
		long[] values = { -2, -3, 4, -1, -2, 1, 5, -3 };
		MaxSubArraySumSegmentTree st = new MaxSubArraySumSegmentTree(values);

		int l = 4, r = 7;
		System.out.printf("Max SubArray sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
		st.pointUpdate(0, 10);
		l = 0;
		r = 2;
		System.out.printf("Max SubArray sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
	}

}
