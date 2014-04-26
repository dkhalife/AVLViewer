package com.dkhalife.projects;

/**
 * This class identifies a node in the tree
 * 
 * @author Dany Khalife
 */
public class AvlNode {
	// The node on the left of this node
	private AvlNode left = null;
	// The node on the right of this node
	private AvlNode right = null;
	// The parent of this node
	private AvlNode parent = null;

	// The value this node holds
	private Integer elem;

	/**
	 * An AVLNode can be created by specifying the integer it holds
	 * 
	 * @param e The integer to save in this node
	 */
	public AvlNode(Integer e) {
		this.elem = e;
	}

	/**
	 * Getter for the left node
	 * 
	 * @return The left node
	 */
	public AvlNode getLeft() {
		return this.left;
	}

	/**
	 * Setter for the left node
	 * 
	 * @param left The new left node
	 */
	public void setLeft(AvlNode left) {
		this.left = left;
	}

	/**
	 * Getter for the right node
	 * 
	 * @return The right node
	 */
	public AvlNode getRight() {
		return this.right;
	}

	/**
	 * Setter for the right node
	 * 
	 * @param right The new right node
	 */
	public void setRight(AvlNode right) {
		this.right = right;
	}

	/**
	 * Getter for the parent
	 * 
	 * @return The parent of this node
	 */
	public AvlNode getParent() {
		return this.parent;
	}

	/**
	 * Setter for the parent
	 * 
	 * @param parent The new parent of this node
	 */
	public void setParent(AvlNode parent) {
		this.parent = parent;
	}

	/**
	 * Getter for the data this node holds
	 * 
	 * @return The integer this node holds
	 */
	public Integer getElem() {
		return this.elem;
	}

	/**
	 * Setter for the data this node holds
	 * 
	 * @param elem The integer to hold in this node
	 */
	public void setElem(Integer elem) {
		this.elem = elem;
	}

	/**
	 * This method determines the height of this node
	 * 
	 * @return The height for this node
	 */
	public Integer height() {
		// Initialise both sides to 0
		int left = 0;
		int right = 0;

		// Recurse to the left leafs
		if (getLeft() != null) {
			left = getLeft().height().intValue();
		}

		// Recurse to the right leafs
		if (getRight() != null) {
			right = getRight().height().intValue();
		}

		// Add this node to the total height
		return Integer.valueOf(1 + Math.max(left, right));
	}

	/**
	 * This method determines the height difference for this node
	 * 
	 * @return The height difference between the left and right subtrees
	 */
	public int getHeightDiff() {
		// Start at 0
		int d = 0;

		// Recurse to the left
		if (getLeft() != null) {
			d = getLeft().height().intValue();
		}

		// Recurse to the right
		if (getRight() != null) {
			d -= getRight().height().intValue();
		}

		return d;
	}
}