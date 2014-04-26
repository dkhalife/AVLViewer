package com.dkhalife.projects;

/**
 * This class identifies a vertex in the tree
 * 
 * @author Dany Khalife
 */
public class Vertex {
	// The X coordinate of the vertex
	private int x;
	// The Y coordinate of the vertex
	private int y;
	
	// The label to print for this vertex
	private Integer label;

	/**
	 * A vertex is created by using a set of coordinates and a label
	 * @param x
	 * @param y
	 * @param label
	 */
	public Vertex(int x, int y, Integer label) {
		this.x = x;
		this.y = y;
		this.label = label;
	}

	/** 
	 * Getter for the X
	 * @return The X coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Getter for the Y
	 * @return The Y coordinate
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Getter for the label
	 * @return The label of this vertex
	 */
	public Integer getName() {
		return this.label;
	}
}