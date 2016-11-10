package com.game.cube.model;

public class Cube {
	
	/**
	 * pieces[0] pieces[1] pieces[2]
	 *           pieces[3]
	 *           pieces[4]
	 *           pieces[5]
	 */
	

	private Piece[] pieces;
	
	
	public Cube(Piece[] pieces) {
		super();
		this.pieces = pieces;
	}  


	public Piece[] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[] pieces) {
		this.pieces = pieces;
	}


	
}
