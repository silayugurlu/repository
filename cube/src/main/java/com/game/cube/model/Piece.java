package com.game.cube.model;

public class Piece {

	private int id;
	private int[][] nodes;

	public Piece(int id) {
		super();
		this.id = id;
	}

	public Piece(int id, int[][] nodes) {
		super();
		this.id = id;
		this.nodes = nodes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[][] getNodes() {
		return nodes;
	}

	public void setNodes(int[][] nodes) {
		this.nodes = nodes;
	}

	public String convertLineToString(int lineIndex) {
		String line = "";
		for (int j = 0; j < 5; j++) {
			line += (nodes[lineIndex][j] == 1 ? "[]" : "  ");
		}
		return line;
	}
}