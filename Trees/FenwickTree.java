package Trees;

/**
 * A Fenwick Tree or Binary Indexed tree(BIT) implementation which supports
 * point updates and sum range queries (Prefix Sum)
 *
 * TC : Construct - O(N) ; Query - O(logN) ; Update - O(logN)
 * 
 * PS: WilliamFiset DS
 */

public class FenwickTree {

	final int N;

	private long[] tree;

	public FenwickTree(int sz) {
		tree = new long[N = sz + 1];
	}

	public FenwickTree(long[] values) {
		if (values == null)
			throw new IllegalArgumentException("Values array cannot be null!");
		N = values.length;
		values[0] = 0L; // 1-based indexing

		// Make a clone of the values array since we manipulate
		// the array in place destroying all its original content.
		tree = values.clone();

		for (int i = 1; i < N; i++) { // Maximum we can traverse is N-1 , as we'll not be updating parent of N
			int parent = i + lsb(i);
			if (parent < N)
				tree[parent] += tree[i];
		}
	}

	// Returns the value of the least significant bit (LSB)
	private int lsb(int i) {
		return i & -i;
	}

	// Returns the sum of the interval [left, right], O(log(n))
	public long sum(int left, int right) {
		if (right < left)
			throw new IllegalArgumentException("Make sure right >= left");
		return prefixSum(right) - prefixSum(left - 1); // left-1 as we need to include the query left position
	}

	// Computes the prefix sum from [1, i], O(log(n))
	public long prefixSum(int i) {
		long sum = 0;
		while (i != 0) {
			sum += tree[i];
			i &= ~lsb(i); // Equivalently, i -= lsb(i);
		}
		return sum;
	}

	// Add value 'K' to index , O(log(n))
	public void add(int index, long K) {
		while (index < N) {
			tree[index] += K;
			index += lsb(index);
		}
	}

	// Get the value at index i
	public long get(int i) {
		return sum(i, i);
	}

	// Set index i to be equal to v, O(log(n))
	public void set(int i, long v) {
		add(i, v - sum(i, i));
	}

	@Override
	public String toString() {
		return java.util.Arrays.toString(tree);
	}

	public static void main(String[] args) {
		long[] values = { 0, 1, 2, 2, 4 }; // first element does not get used

		FenwickTree ft = new FenwickTree(values);

		System.out.println(ft.sum(1, 4)); // 9, sum all numbers in interval [1, 4] in O(log(n))
		ft.add(3, 1); // Adds +1 to index 3.

		System.out.println(ft.sum(1, 4)); // 10, sum all numbers in interval [1, 4]
		ft.set(4, 0); // Set index 4 to have value zero.

		System.out.println(ft.sum(1, 4)); // 6, sum all numbers in interval [1, 4]
		ft.get(2); // 2, Get the value at index 2, this is the same as .sum(2, 2)
	}

}
