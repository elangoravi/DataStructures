/**
 * Knutt-Morris-Pratt (KMP) algorithm for String - pattern matching
 * 
 * TC : O(N+M) , when N is the length of the string , M - length of pattern
 * since N>>>M , O(N)
 *
 * SC: O(M) extra space for LPS array
 * 
 * The logic behind this algorithm is to store the longest proper prefix that is
 * also a suffix of the pattern in an LPS array and that avoids brute-force
 * starting from first everytime on mismatch.
 * 
 * When it is said to be proper prefix, it cannot be equal to the string itself.
 */

public class KMP {

	public static java.util.List<Integer> kmp(String text, String pattern) {
		java.util.List<Integer> matches = new java.util.ArrayList<>();
		int N = text.length();
		int M = pattern.length();
		int[] lps = calculateLps(pattern, M);
		int i = 0, j = 0;
		while (i < N) {
			if (text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else {
				if (j != 0) // if mismatch occurs @ 1st pattern char, we cannot check lps[-1]
					j = lps[j - 1]; // We check for longest proper prefix in lps array and set it as current pattern
									// pointer
				else
					i++; // Mismatch @ first char, we move forward with incrementing text pointer
			}
			if (j == M) { // Pattern Matched !!!
				matches.add(i - j);
				j = lps[j - 1]; // reset pattern pointer again using lps array
			}
		}
		return matches;
	}

	// Returns Longest prefix suffix array
	private static int[] calculateLps(String pattern, int M) {
		int[] lps = new int[M];
		int len = 0;
		int i = 1;
		// lps[0] = 0 First character cannot be a prefix so it should be set to zero.
		// Array intialisation does it though
		while (i < M) {
			if (pattern.charAt(i) == pattern.charAt(len)) {
				lps[i] = len + 1; // As len is 0-based and lags behind by 1
				len++;
				i++;
			} else {
				if (len != 0) {
					len = lps[len - 1]; // we cannot assign to 0. Use the same login of lps which pre-computed the
										// prefix until this point
				} else {
					lps[i] = 0; // when len is still @ index 0, there is no prefix yet
					i++;
				}
			}
		}
		return lps;
	}

	public static void main(String[] args) {
		java.util.List<Integer> matches = kmp("abababa", "aba");
		System.out.println(matches); // [0, 2, 4]

		matches = kmp("abc", "abcdef");
		System.out.println(matches); // []

		matches = kmp("P@TTerNabcdefP@TTerNP@TTerNabcdefabcdefabcdefabcdefP@TTerN", "P@TTerN");
		System.out.println(matches); // [0, 13, 20, 51]
	}
}
