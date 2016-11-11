package com.game.cube.model;

public class Piece {

	private PieceId id;
	private int[][] nodes;
	
	public Piece() {
		super();
	}

	public Piece(PieceId id) {
		super();
		this.id = id;
	}

	public Piece(PieceId id, int[][] nodes) {
		super();
		this.id = id;
		this.nodes = nodes;
	}

	public PieceId getId() {
		return id;
	}

	public void setId(PieceId id) {
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
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		Piece param =(Piece)obj;
		return this.id.equals(param.getId());
	}
}