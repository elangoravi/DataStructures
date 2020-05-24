
public class mergeSort {

	public void merger(int[] array) {
		int[] helper = new int[array.length];
		mergeS(array, helper, 0, array.length - 1);
		for (int i : array) {
			System.out.print(i + " ");
		}
	}

	public void mergeS(int[] a, int[] helper, int low, int high) {
		if (low < high) {
			int middle = (low + high) / 2;
			mergeS(a, helper, low, middle);
			mergeS(a, helper, middle + 1, high);
			merge(a, helper, low, middle, high);
		}
	}

	public void merge(int[] array, int[] helper, int low, int middle, int high) {
		for (int i = low; i <= high; i++) {
			helper[i] = array[i];
		}

		int helperleft = low;
		int helperright = middle + 1;
		int current = low;

		while (helperleft <= middle && helperright <= high) {
			if (helper[helperleft] <= helper[helperright]) {
				array[current] = helper[helperleft];
				helperleft++;
			} else {
				array[current] = helper[helperright];
				helperright++;
			}
			current++;
		}

		int rem = middle - helperleft;
		for (int i = 0; i <= rem; i++) {
			array[current + i] = helper[helperleft + i];
		}
	}

	public static void main(String[] args) {
		mergeSort obj = new mergeSort();
		int[] arr = { 8, 7, 6, 1, 0, 9, 2, 3 };
		obj.merger(arr);
	}

}
