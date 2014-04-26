package com.dkhalife.projects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class represents the panel where the avl tree will be drawn
 * 
 * @author Dany Khalife
 * 
 */
class Panel extends JPanel {
	/**
	 * Eclipse generated serial UID
	 */
	private static final long serialVersionUID = 6064467111215011132L;

	// The width of the panel
	private int width;
	// The height in pixels
	private int pHeight;
	// The width in pixels
	private int pWidth;

	// The tree we want to represent
	private AvlTree tree = new AvlTree();

	// The list of vertices
	private Vector<Vertex> V = new Vector<Vertex>();

	// The list of edges
	private Vector<Edge> E = new Vector<Edge>();

	// The offset in X for the drag n drop
	private int offsetX = 0;
	// The offset in Y for the drag n drop
	private int offsetY = 0;

	// The drag start X coordinate
	private int dragStartX = 0;
	// The drag start Y coordinate
	private int dragStartY = 0;

	// Are we dragging
	private boolean dragging = false;

	// The previous X offset
	private int previousOffsetX = 0;
	// The previous Y offset
	private int previousOffsetY = 0;

	/**
	 * In order to construct a panel we need to know its width and height
	 * 
	 * @param w The width of the panel
	 * @param h The height of the panel
	 */
	public Panel(int w, int h) {
		this.width = w;
		// Calculate the width and height in pixels
		this.pWidth = (w * 20);
		this.pHeight = (h * 20);

		// Set a thick border
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		// Change the cursor
		setCursor(new Cursor(13));

		/**
		 * Listen for mouse events
		 */
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// Start dragging
				Panel.this.dragStartX = Panel.this.snapToGrid(e.getX(), 20);
				Panel.this.dragStartY = Panel.this.snapToGrid(e.getY(), 20);

				Panel.this.dragging = true;
			}

			public void mouseReleased(MouseEvent e) {
				// Stop dragging
				Panel.this.dragging = false;

				Panel.this.previousOffsetX = Panel.this.offsetX;
				Panel.this.previousOffsetY = Panel.this.offsetY;
			}
		});

		/**
		 * Listen for drag events
		 */
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!Panel.this.dragging) {
					return;
				}

				// Move the panel
				Panel.this.offsetX = (Panel.this.previousOffsetX + (Panel.this.snapToGrid(e.getX(), 20) - Panel.this.dragStartX) / 20);
				Panel.this.offsetY = (Panel.this.previousOffsetY + (Panel.this.snapToGrid(e.getY(), 20) - Panel.this.dragStartY) / 20);

				// Repaint
				Panel.this.repaint();
			}
		});
	}

	/**
	 * This method overrides the super method in order to specify the preferred
	 * size
	 */
	public Dimension getPreferredSize() {
		return new Dimension(this.pWidth, this.pHeight);
	}

	/**
	 * This method snaps a coordinate to the grid
	 * 
	 * @param d The coordinate to snap to the grid
	 * @param res The resolution on the axis
	 * @return The resulting coordinate
	 */
	private int snapToGrid(int d, int res) {
		int snapDown = res * Math.round(d / res);

		int snapUp = snapDown + res;

		if (d - snapDown < snapUp - d) {
			return snapDown;
		}

		return snapUp;
	}

	/**
	 * This method paints the panel
	 */
	protected void paintComponent(Graphics h) {
		super.paintComponent(h);
		Graphics2D g = (Graphics2D) h;

		// Set the stroke
		g.setStroke(new BasicStroke(1.0F, 2, 1, 0.0F, new float[] { 3.0F }, 0.0F));
		// Gray color
		g.setColor(Color.GRAY);

		// Draw the grid
		for (int i = 20; i < this.pWidth; i += 20) {
			g.drawLine(i, 0, i, pHeight);
		}
		for (int i = 20; i < this.pHeight; i += 20) {
			g.drawLine(0, i, pWidth, i);
		}

		// Do we have a root yet?
		if (this.tree.getRoot() == null) {
			return;
		}

		// Sart with an empty set
		this.V = new Vector<Vertex>();
		this.E = new Vector<Edge>();

		// Construct the graph for the tree
		constructGraph(this.tree.getRoot(), null);

		// Reset the stroke
		g.setStroke(new BasicStroke(3.0F));

		// Now with a blue color
		g.setColor(Color.BLUE);

		// Draw the edges
		for (Edge k : this.E) {
			g.drawLine(k.getV1().getX() * 20, k.getV1().getY() * 20, k.getV2().getX() * 20, k.getV2().getY() * 20);
		}

		// Set the font
		g.setFont(new Font("Verdana", 1, 16));
		for (Vertex k : this.V) {
			g.setColor(Color.BLACK);

			// Draw the vertices
			g.fillOval((k.getX() - 1) * 20, (k.getY() - 1) * 20, 40, 40);

			// Coordinates for the label
			int x = k.getX() * 20 - 5;
			int y = k.getY() * 20 + 5;

			// In case we have 2 caracters
			String s = String.valueOf(k.getName());
			if (s.length() == 2) {
				x -= 7;
			}

			// Draw the label
			g.setColor(Color.WHITE);
			g.drawString(s, x, y);
		}
	}

	/**
	 * This method constructs a graph recursively
	 * 
	 * @param n The current node
	 * @param parent The node's parent
	 */
	private void constructGraph(AvlNode n, Vertex parent) {
		Vertex v = null;

		if (parent == null) {
			// Add the vertex
			this.V.add(v = new Vertex(this.width / 2 + this.offsetX, 2 + this.offsetY, n.getElem()));
		}
		else {
			// Is this the left node
			if (n.getParent().getLeft() == n) {
				this.V.add(v = new Vertex(parent.getX() - (int) Math.pow(2.0D, n.height().intValue()), parent.getY() + 2, n.getElem()));
			}
			// Or the right node
			else {
				this.V.add(v = new Vertex(parent.getX() + (int) Math.pow(2.0D, n.height().intValue()), parent.getY() + 2, n.getElem()));
			}

			// Add an edge with its parent
			this.E.add(new Edge(v, parent));
		}

		// Recurse left
		if (n.getLeft() != null)
			constructGraph(n.getLeft(), v);

		// Recurse right
		if (n.getRight() != null)
			constructGraph(n.getRight(), v);
	}

	/**
	 * This method shows an alert box
	 * 
	 * @param msg The message to show
	 */
	protected void alert(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	/**
	 * This methode clears the tree
	 */
	public void clear() {
		this.tree = new AvlTree();
		repaint();
	}

	/**
	 * This method adds a value to the tree
	 * 
	 * @param num The value to add
	 */
	public void addVertex(int num) {
		this.tree.insert(Integer.valueOf(num));
		repaint();
	}

	/**
	 * This method deletes a value from the tree
	 * 
	 * @param num The value to delete
	 */
	public void deleteVertex(int num) {
		this.tree.remove(Integer.valueOf(num));
		repaint();
	}
}