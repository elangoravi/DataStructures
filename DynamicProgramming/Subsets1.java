import java.util.*;

public class SubSet1 {

	public static ArrayList<ArrayList<Integer>> getSubSets(ArrayList<Integer> incoming) {
		ArrayList<ArrayList<Integer>> subsets = new ArrayList<ArrayList<Integer>>();
		int size = (int) Math.pow(2, incoming.size());
		for (int i = 0; i < size; i++) {
			subsets.add(getEach(i, incoming));
		}
		return subsets;
	}

	private static ArrayList<Integer> getEach(int x, ArrayList<Integer> incoming) {
		int index = 0;
		ArrayList<Integer> ls = new ArrayList<Integer>(); // create empty list
		while (x > 0) {
			if ((x & 1) == 1)
				ls.add(incoming.get(index));
			index++;
			x >>= 1;
		}
		return ls;
	}

	public static void main(String[] args) {
		ArrayList<Integer> lt = new ArrayList<>();
		lt.add(3);
		lt.add(4);
		lt.add(2);
		lt.add(1);
		ArrayList<ArrayList<Integer>> received = SubSet1.getSubSets(lt);
		for (ArrayList<Integer> x : received)
			System.out.println(x);
	}

}
