package com.game.cube.model;

import java.util.ArrayList;
import java.util.List;

public class MatchedPieceId {
	PieceId id;
	List<MatchedPieceId> matchedPieces;
	
	
	
	public MatchedPieceId(PieceId id) {
		super();
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public PieceId getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(PieceId id) {
		this.id = id;
	}
	
	
	/**
	 * @return the matchedPieces
	 */
	public List<MatchedPieceId> getMatchedPieces() {
		return matchedPieces;
	}
	/**
	 * @param matchedPieces the matchedPieces to set
	 */
	public void setMatchedPieces(List<MatchedPieceId> matchedPieces) {
		this.matchedPieces = matchedPieces;
	}
	public void addMatchedPieces(MatchedPieceId matchedPiece) {
		if(this.matchedPieces == null){
			this.matchedPieces = new ArrayList<MatchedPieceId>();
		}
		this.matchedPieces.add(matchedPiece);
	}
}
