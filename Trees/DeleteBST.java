package Trees;

/**
 * Classical Deleting a Node in Binary Search Tree Time Complexity : O(h) , h =
 * height of the tree
 *
 */

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int val) {
		this.val = val;
	}

	TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}

public class DeleteBST {

	public TreeNode deleteNode(TreeNode root, int key) {
		if (root == null)
			return root;
		if (root.val > key) // key < root.val check in left sub-tree
			root.left = deleteNode(root.left, key);
		else if (root.val < key) // key > then root.val check in right sub-tree
			root.right = deleteNode(root.right, key);
		else { // key == root.val we found nodes need to delete
			if (root.left == null)
				return root.right;
			else if (root.right == null)
				return root.left;
			root.val = successor(root.right);
			root.right = deleteNode(root.right, root.val);
		}
		return root;
	}

	public int successor(TreeNode root) {
		return root.left == null ? root.val : successor(root.left);
	}
}
