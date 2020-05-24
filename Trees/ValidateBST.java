
package Trees;

public class ValidateBST {

	static class Node {
		int data;
		Node left, right;

		Node(int data) {
			this.data = data;
			left = right = null;
		}
	}

	static Node root;

	public static Node insert(Node root, int val) {
		if (root == null) {
			return new Node(val);
		} else {
			if (val < root.data)
				root.left = insert(root.left, val);
			else
				root.right = insert(root.right, val);
		}
		return root;
	}

	public static boolean valBST(Node n, Node lower, Node higher) {
		if (n == null)
			return true;
		if (lower != null && n.data < lower.data)
			return false;
		if (higher != null && n.data > higher.data)
			return false;
		return valBST(n.left, lower, n) && valBST(n.right, n, higher);
	}

	public static void main(String[] args) {

		root = new Node(5);
		root.left = new Node(3);
		root.right = new Node(7);
		root.left.right = new Node(4);
		System.out.println(valBST(root, null, null));
	}

}
