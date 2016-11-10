package com.game.cube.helper;

import java.util.ArrayList;
import java.util.List;

import com.game.cube.model.MatchedPiece;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;

public class CubeBuilder {

	public CubeBuilder() {
		super();
	}

	private MatchedPiece root;
	private Piece[] pieces;

	private CubeHelper helper = new CubeHelper();

	public CubeBuilder(Piece[] pieces) {
		super();
		this.root = new MatchedPiece(pieces[0]);
		this.pieces = pieces;
	}

	public MatchedPiece findPiecesInLevel2() {

		for (int i = 1; i < 6; i++) {

			Piece piece = pieces[i];
			Piece result = findMatchedPieces(root.getPiece(),piece);
			if(result!=null){
				break;
			}
		}
		return null;
	}

	public Piece findMatchedPieces(Piece matchedPiece, Piece subPiece){

		if(subPiece == null || helper.checkPiecesMatched(matchedPiece, subPiece)){
			return subPiece;
		}else{
			PieceId pieceId = subPiece.getId();
			PieceId newId = null;
			Piece newPiece = null;
			
			//rotated 3 times can be mirrored
			if(pieceId.getRotation() == 3 && !pieceId.isMirror()){
				newId = new PieceId(subPiece.getId().getId(), 0, true);
				newPiece = new Piece(newId,helper.mirrorPiece(subPiece.getNodes()) );
				 
			}else if(pieceId.getRotation() < 3){
				newId = new PieceId(subPiece.getId().getId(), pieceId.getRotation()+1,  pieceId.isMirror());
				newPiece = new Piece(newId,helper.rotatePiece(subPiece.getNodes()) );
				
			}else if(pieceId.getRotation() == 3 && pieceId.isMirror()){
				return null;
			}
			
			newPiece.setId(newId);			
			return findMatchedPieces(matchedPiece, newPiece);
		}
	}

}
