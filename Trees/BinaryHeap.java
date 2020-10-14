package Trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryHeap {

	private List<Integer> heap = null;

	public BinaryHeap() {
		this(1);
	}

	public BinaryHeap(int sz) {
		heap = new ArrayList<>(sz);
	}

	// Priority queue construction, O(n)
	public BinaryHeap(Collection<Integer> elements) {
		int heapSize = elements.size();
		heap = new ArrayList<>(heapSize);
		heap.addAll(elements);
		// Heapify process, O(n)
		for (int i = (heapSize - 1) / 2; i >= 0; i--)
			sink(i);
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public void clear() {
		heap.clear();
	}

	public int size() {
		return heap.size();
	}

	public int peek() {
		if (isEmpty())
			throw new NoSuchElementException();
		return heap.get(0);
	}

	public int poll() {
		return removeAt(0);
	}

	public int removeAt(int i) {
		if (isEmpty())
			throw new NoSuchElementException();
		int indexOfLastElement = size() - 1;
		int removedData = heap.get(i);
		swap(i, indexOfLastElement);
		// Remove the value
		heap.remove(indexOfLastElement);
		// Check if the last element was removed
		if (i == indexOfLastElement)
			return removedData;
		int elem = heap.get(i);
		// Try sinking element
		sink(i);
		// If sinking did not work try swimming
		if (i == elem)
			swim(i);
		return removedData;
	}

	public boolean remove(int ele) {
		if (isEmpty())
			throw new NoSuchElementException();
		for (int i = 0; i < size(); i++) {
			if (heap.get(i) == ele) {
				removeAt(i);
				return true;
			}
		}
		return false;
	}

	// Adds an element to the priority queue, the
	// element must not be null, O(log(n))
	public void add(int element) {
		heap.add(element);
		int indexOfLastElem = size() - 1;
		swim(indexOfLastElem);
	}

	// Perform bottom up node swim, O(log(n))
	private void swim(int i) {
		int parent = (i - 1) / 2;
		while (i > 0 && heap.get(i) < heap.get(parent)) {
			swap(i, parent);
			i = parent;
			parent = (i - 1) / 2;
		}
	}

	// Top down node sink, O(log(n))
	private void sink(int i) {
		int heapsize = size();
		while (true) {
			int left = 2 * i + 1;
			int right = 2 * i + 2;
			int smallest = left;
			if (right < heapsize && heap.get(right) < heap.get(smallest))
				smallest = right;
			if (left >= heapsize || heap.get(i) <= heap.get(smallest))
				break;
			swap(smallest, i);
			i = smallest;
		}
	}

	private void swap(int i, int j) {
		int elem_i = heap.get(i);
		int elem_j = heap.get(j);
		heap.set(i, elem_j);
		heap.set(j, elem_i);
	}

	@Override
	public String toString() {
		return heap.toString();
	}

	public static void main(String[] args) {
		List<Integer> ls = Arrays.asList(5, 4, 3, 2, 1, 0);
		System.out.println(new BinaryHeap(ls).poll());
	}

}
