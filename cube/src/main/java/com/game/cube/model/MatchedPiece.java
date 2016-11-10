package com.game.cube.model;

import java.util.ArrayList;
import java.util.List;

public class MatchedPiece {

	private Piece piece;

	private List<MatchedPiece> matchedPieces;

	public MatchedPiece(Piece piece) {
		super();
		this.piece = piece;
	}

	public MatchedPiece(Piece piece, List<MatchedPiece> matchedPieces) {
		super();
		this.piece = piece;
		this.matchedPieces = matchedPieces;
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
	
	public void addMatchedPieces(MatchedPiece matchedPiece) {
		if(this.matchedPieces == null){
			this.matchedPieces = new ArrayList<MatchedPiece>();
		}
		this.matchedPieces.add(matchedPiece);
	}


}
