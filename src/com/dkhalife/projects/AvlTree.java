package com.dkhalife.projects;

/**
 * This class represents an AVL Tree
 * 
 * @author Dany Khalife
 */
public class AvlTree {
	// The root of the tree
	private AvlNode root = null;

	/**
	 * This method inserts an integer into the tree
	 * 
	 * @param e
	 */
	public void insert(Integer e) {
		// If we don't have a root, we'll make one
		if (this.root == null) {
			this.root = new AvlNode(e);
			return;
		}

		// Otherwise we'll insert it recursively
		insert(this.root, e);
	}

	/**
	 * This method inserts an integer into the tree with respect to a given node
	 */
	private void insert(AvlNode n, Integer e) {
		// We'll get the current element
		Integer current = n.getElem();

		// Should we go left
		if (e.intValue() < current.intValue()) {
			// Is this a leaf?
			if (n.getLeft() != null) {
				insert(n.getLeft(), e);
			}
			else {
				AvlNode newNode = new AvlNode(e);
				newNode.setParent(n);
				n.setLeft(newNode);

				balance(newNode.getParent());
			}
		}
		// Or right?
		else if (e.intValue() > current.intValue())
			// Is this a leaf?
			if (n.getRight() != null) {
				insert(n.getRight(), e);
			}
			else {
				AvlNode newNode = new AvlNode(e);
				newNode.setParent(n);
				n.setRight(newNode);

				balance(newNode.getParent());
			}
	}

	/**
	 * This method balances the tree in order to respect the AVL propperties
	 * 
	 * @param p The node for which to consider balancing
	 */
	private void balance(AvlNode p) {
		// The right subtree
		AvlNode r = p.getRight();
		// The left subtree
		AvlNode l = p.getLeft();

		// The height difference
		int heightDiff = p.getHeightDiff();

		// Do we have a lean towords the right?
		if (heightDiff == -2) {
			// Is it straight?
			if (r.getHeightDiff() == -1) {
				rotateLeft(p);
			}

			// Or zig zag
			if (r.getHeightDiff() == 1) {
				rotateRight(r);

				rotateLeft(p);
			}
		}

		// Do we have a lean towards the left?
		if (heightDiff == 2) {
			// Is it straight?
			if (l.getHeightDiff() == 1) {
				rotateRight(p);
			}

			// Or zig zag
			if (l.getHeightDiff() == -1) {
				rotateLeft(l);

				rotateRight(p);
			}

		}

		// Lets bubble up
		if (p != this.root)
			balance(p.getParent());
	}

	/**
	 * This method rotates the tree around the given node
	 * 
	 * @param top The node on which to perform the rotation
	 */
	private void rotateLeft(AvlNode top) {
		// Lets get the ancestor
		AvlNode ancestor = top.getParent();

		// Are we its left or right child?
		boolean position = false;
		if (ancestor != null) {
			position = ancestor.getRight() == top;
		}

		// Adjust the links
		top.setParent(top.getRight());
		top.setRight(top.getParent().getLeft());
		top.getParent().setLeft(top);
		top.getParent().setParent(ancestor);

		// Did we affect the root?
		if (this.root == top) {
			this.root = top.getParent();
		}

		// Adjust the ancestor's links
		if (ancestor != null) {
			if (position) {
				ancestor.setRight(top.getParent());
			}
			else
				ancestor.setLeft(top.getParent());
		}
	}

	/**
	 * This method rotates the tree to the right on the given node
	 * 
	 * @param top The node on which to perform the rotation
	 */
	private void rotateRight(AvlNode top) {
		// Lets get the ancestor
		AvlNode ancestor = top.getParent();

		// Are we its left or right child?
		boolean direction = false;
		if (ancestor != null) {
			direction = ancestor.getRight() == top;
		}

		// Adjust links
		top.setParent(top.getLeft());
		top.setLeft(top.getParent().getRight());
		top.getParent().setRight(top);
		top.getParent().setParent(ancestor);

		// Did we affect the root?
		if (this.root == top) {
			this.root = top.getParent();
		}

		// Adjust the ancestor's links
		if (ancestor != null) {
			if (direction) {
				ancestor.setRight(top.getParent());
			}
			else
				ancestor.setLeft(top.getParent());
		}
	}

	/**
	 * Getter for the root
	 * 
	 * @return The root of the tree
	 */
	public AvlNode getRoot() {
		return this.root;
	}

	/**
	 * This method removes an element from the tree
	 * 
	 * @param e The integer to remove
	 */
	public void remove(Integer e) {
		remove(this.root, e);
	}

	/**
	 * This method removes an element from the tree according to the given node
	 * 
	 * @param n The referenced node
	 * @param e The integer to remove
	 */
	private void remove(AvlNode n, Integer e) {
		// Lets get the current element
		Integer current = n.getElem();

		// Should we go left
		if ((e.intValue() < current.intValue()) && (n.getLeft() != null)) {
			remove(n.getLeft(), e);
		}
		// Or right?
		else if ((e.intValue() > current.intValue()) && (n.getRight() != null)) {
			remove(n.getRight(), e);
		}
		// Are we there yet?
		else if (e == current) {
			// Great lets get the parent
			AvlNode parent = n.getParent();

			// Are we at the root?
			boolean isRoot = n == this.root;

			// Is this a left child?
			boolean isLeftChild = (!isRoot) && (parent.getLeft() == n);

			// Is this a leaf?
			if ((n.getLeft() == null) && (n.getRight() == null)) {
				// Cleanup the ongoing link to this node then
				if (isRoot) {
					this.root = null;
				}
				else if (isLeftChild) {
					parent.setLeft(null);
				}
				else {
					parent.setRight(null);
				}

			}
			// Is this a half leaf?
			else if ((n.getLeft() == null) || (n.getRight() == null)) {
				// On the left?
				if (n.getLeft() != null) {
					// Adjust the incoming link
					if (!isRoot) {
						if (isLeftChild) {
							parent.setLeft(n.getLeft());
						}
						else {
							parent.setRight(n.getLeft());
						}
					}

					// Adjust the outgoing link
					n.getLeft().setParent(parent);

					// Did we affect the root
					if (isRoot) {
						this.root = n.getLeft();
					}
				}
				else {
					// Is this the root
					if (!isRoot) {
						// Adjust the outgoing links
						if (isLeftChild) {
							parent.setLeft(n.getRight());
						}
						else {
							parent.setRight(n.getRight());
						}
					}

					// Adjust the incoming link
					n.getRight().setParent(parent);

					// Did we affect the root?
					if (isRoot) {
						this.root = n.getRight();
					}
				}
			}
			else {
				// Lets get the maximum
				AvlNode m = findMax(n.getLeft());

				// Adjust its links
				if (m.getParent() != n) {
					if (m.getParent().getLeft() == m)
						m.getParent().setLeft(m.getLeft());
					else {
						m.getParent().setRight(m.getLeft());
					}
				}

				// Did we affect the root
				if (!isRoot) {
					// Adjust the incoming link
					if (isLeftChild) {
						parent.setLeft(m);
					}
					else {
						parent.setRight(m);
					}

				}
				else {
					this.root = m;
				}

				// Adjust the outgoing link
				m.setRight(n.getRight());

				// Adjust the other side
				if (n.getLeft() != m) {
					m.setLeft(n.getLeft());
					n.getLeft().setParent(m);
				}
				else {
					m.setLeft(null);
				}

				// And the descendant
				if (n.getRight() != null) {
					n.getRight().setParent(m);
				}

				// Then finally the parent
				m.setParent(parent);

				print();
			}

			// Now we need to rebalance the tree
			if (!isRoot)
				balance(parent);
		}
	}

	/**
	 * This method finds the minimum of a subtree starting at the given node
	 * 
	 * @param n The node from which to start the search
	 * @return The minimum node
	 */
	public AvlNode findMin(AvlNode n) {
		// Did we find anything?
		if (n == null) {
			return null;
		}

		// Recurse to the left
		while (n.getLeft() != null) {
			n = n.getLeft();
		}

		return n;
	}

	/**
	 * This method finds the maximum of a subtree starting at the given node
	 * 
	 * @param n The node from which to start the search
	 * @return The maximum node
	 */
	public AvlNode findMax(AvlNode n) {
		// Did we find anything
		if (n == null) {
			return null;
		}

		// Recurse to the right
		while (n.getRight() != null) {
			n = n.getRight();
		}

		return n;
	}

	/**
	 * This method prints the tree recursively
	 */
	public void print() {
		print(this.root, 0);
	}

	/**
	 * This method prints the subtree starting at the given node
	 * 
	 * @param n The node from which to start printing
	 * @param tabs The tabs to consider in the print
	 */
	private void print(AvlNode n, int tabs) {
		// Did we arrive at the end
		if (n == null) {
			return;
		}

		// Print the tabs
		for (int i = 0; i < tabs; i++) {
			System.out.print("\t");
		}

		// Print the element
		System.out.println(n.getElem());
		// Then the left side
		print(n.getLeft(), tabs + 1);
		// Then the right side
		print(n.getRight(), tabs + 1);
	}
}