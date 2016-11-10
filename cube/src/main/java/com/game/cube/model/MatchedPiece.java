package com.game.cube.model;

import java.util.List;

public class MatchedPiece {
	
	private Piece piece;
	
	private List<MatchedPiece> matchedPieces;
	
	public MatchedPiece(Piece piece) {
		super();
		this.piece = piece;
	}
	

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public List<MatchedPiece> getMatchedPieces() {
		return matchedPieces;
	}

	public void setMatchedPieces(List<MatchedPiece> matchedPieces) {
		this.matchedPieces = matchedPieces;
	}


}
