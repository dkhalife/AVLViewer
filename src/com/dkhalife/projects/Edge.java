package com.dkhalife.projects;

/**
 * This class represents an Edge
 * 
 * @author Dany Khalife
 */
public class Edge {
	// The left vertex
	private Vertex v1 = null;
	// The right vertex
	private Vertex v2 = null;

	/**
	 * In order to construct an edge we need to know a couple of vertices it joins
	 * @param v1 The first vertex
	 * @param v2 The second vertex
	 */
	public Edge(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	/**
	 * Getter for the first vertex
	 * @return The first vertex
	 */
	public Vertex getV1() {
		return this.v1;
	}

	/**
	 * Getter for the second vertex
	 * @return The second vertex
	 */
	public Vertex getV2() {
		return this.v2;
	}
}