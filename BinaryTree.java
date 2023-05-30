import java.util.ArrayList;
/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *	
 *	@author	Rishabh Goel
 *	@since	5/18/23
 */
public class BinaryTree<E extends Comparable<E>> {

	private TreeNode<E> root;		// the root of the tree
	
	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()
	
	/**	constructor for BinaryTree */
	public BinaryTree() { 
		root = null;
	}

	public BinaryTree(E value) {
		root = new TreeNode<E>(value);
	}
	
	/**	Field accessors and modifiers */
	
	/**	Add a node to the tree
	 *	@param value		the value to put into the tree
	 */
	public void add(E value) { 
		root = addRecursive(root, value);
	}

	public TreeNode<E> addRecursive(TreeNode<E> start, E val){
		if (start == null) {
			return new TreeNode<E>(val);
		}

		if (val.compareTo(start.getValue()) < 0) {
			start.setLeft(addRecursive(start.getLeft(), val));
		} else if (val.compareTo(start.getValue()) > 0) {
			start.setRight(addRecursive(start.getRight(), val));
		}

		return start;
	}
	
	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder() { 
		printInorderRecurse(root);
	}

	/**
	 * Recursive method to print the tree using the order: in-order.
	 * 
	 * @param node The current node
	 */
	private void printInorderRecurse(TreeNode<E> node) {
		if (node == null) return;
		printInorderRecurse(node.getLeft());
		System.out.print(node.getValue() + " ");
		printInorderRecurse(node.getRight());
	}
	
	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder() { 
		printPreorderRecurse(root);
	}

	/**
	 * Recursive method to print the tree using the order: pre-order.
	 * 
	 * @param node The current node
	 */
	private void printPreorderRecurse(TreeNode<E> node) {
		if (node == null) return;
		System.out.print(node.getValue() + " ");
		printPreorderRecurse(node.getLeft());
		printPreorderRecurse(node.getRight());
	}
	
	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder() { 
		printPostorderRecurse(root);
	}

	/**
	 * Recursive method to print the tree using the order: post-order.
	 * 
	 * @param node The current node
	 */
	private void printPostorderRecurse(TreeNode<E> node) {
		if (node == null) return;
		printPostorderRecurse(node.getLeft());
		printPostorderRecurse(node.getRight());
		System.out.print(node.getValue() + " ");
	}
		
	/**	Return a balanced version of this binary tree
	 *	@return		the balanced tree
	 */
	public BinaryTree<E> makeBalancedTree() {
		BinaryTree<E> balancedTree = new BinaryTree<E>();
		ArrayList<E> list = getInorderList(root);
		balancedTree = makeBalancedTreeRecurse(list);
		return balancedTree;
	}

	/**
	 * Recursive method to make a balanced tree.
	 * 
	 * @param list	The list of values to add to the tree
	 * @return		The balanced tree
	 */
	private BinaryTree<E> makeBalancedTreeRecurse(ArrayList<E> list) {
		BinaryTree<E> balancedTree = new BinaryTree<E>();
		if (list.size() == 0) return balancedTree;
		int mid = list.size() / 2;
		balancedTree.add(list.get(mid));
		ArrayList<E> leftList = new ArrayList<E>();
		for (int a = 0; a < mid; a++) {
			leftList.add(list.get(a));
		}
		ArrayList<E> rightList = new ArrayList<E>();
		for (int a = mid + 1; a < list.size(); a++) {
			rightList.add(list.get(a));
		}
		balancedTree.root.setLeft(makeBalancedTreeRecurse(leftList).root);
		balancedTree.root.setRight(makeBalancedTreeRecurse(rightList).root);
		return balancedTree;
	}

	/**
	 * Recursive method to get the tree using the order: in-order.
	 * 
	 * @param val	The current node
	 * @return 		The list in order
	 */
	private ArrayList<E> getInorderList(TreeNode<E> node) {
		ArrayList<E> list = new ArrayList<E>();
		if (node == null) return list;
		if(node.getLeft() != null) {
			list.addAll(getInorderList(node.getLeft()));
		}
		list.add(node.getValue());
		if(node.getRight() != null) {
			list.addAll(getInorderList(node.getRight()));
		}
		return list;
	}
	
	/**
	 *	Remove value from Binary Tree
	 *	@param value		the value to remove from the tree
	 *	Precondition: value exists in the tree
	 */
	public void remove(E value) {
		root = remove(root, value);
	}
	/**
	 *	Remove value from Binary Tree
	 *	@param node			the root of the subtree
	 *	@param value		the value to remove from the subtree
	 *	@return				TreeNode that connects to parent
	 */
	public TreeNode<E> remove(TreeNode<E> node, E value) {
		if (node == null) return null;
		if (value.compareTo(node.getValue()) < 0) {
			node.setLeft(remove(node.getLeft(), value));
			return node;
		} else if (value.compareTo(node.getValue()) > 0) {
			node.setRight(remove(node.getRight(), value));
			return node;
		} else {
			// node is the node to remove
			if (node.getLeft() == null) return node.getRight();
			else if (node.getRight() == null) return node.getLeft();
			else {
				// node has two children
				// find the successor (leftmost node in right subtree)
				TreeNode<E> successor = node.getRight();
				while (successor.getLeft() != null)
					successor = successor.getLeft();
				// replace node's value with successor's value
				node.setValue(successor.getValue());
				// remove successor from right subtree
				node.setRight(remove(node.getRight(), successor.getValue()));
				return node;
			}
		}
	}
	

	/*******************************************************************************/	
	/********************************* Utilities ***********************************/	
	/*******************************************************************************/	
	/**
	 *	Print binary tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree() {
		printLevel(root, 0);
	}
	
	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level) {
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
	
	
}