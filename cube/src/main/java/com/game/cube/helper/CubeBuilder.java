package com.game.cube.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.game.cube.model.MatchedPiece;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;

public class CubeBuilder {

	private MatchedPiece root;
	// private Piece[] pieces;

	private CubeHelper helper = new CubeHelper();

	// public CubeBuilder(Piece[] pieces) {
	// super();
	//
	// this.pieces = pieces;
	// }

	public CubeBuilder() {
		super();
	}

	public void buildCube(List<Piece> pieces) {
		root = new MatchedPiece(pieces.get(0));

		List<Piece> iterationList = new ArrayList<Piece>();
		iterationList.addAll(pieces);
		iterationList.remove(root);

		for (Piece pieceToTry : iterationList) {
			findPieceMatch(root, pieceToTry);
		}

		for (MatchedPiece piece : root.getMatchedPieces()) {
			for (Piece pieceToTry : iterationList) {
				if (!pieceToTry.equals(piece)) {
					findPieceMatch(piece, pieceToTry);
				}
			}
		}

		for (MatchedPiece piece2 : root.getMatchedPieces()) {
			for (MatchedPiece piece1 : piece2.getMatchedPieces()) {
				for (Piece pieceToTry : iterationList) {
					if (!pieceToTry.equals(piece1) && !pieceToTry.equals(piece2)) {
						findPieceMatch(piece1, pieceToTry, true);
					}
				}
			}
		}
		
		
		

	}

	public MatchedPiece findPiecesInLine(List<Piece> pieces, MatchedPiece matchedPiece) {
		for (Piece piece : pieces) {
			MatchedPiece result = findPieceMatch(matchedPiece, piece);
		}
		return null;

	}

	public MatchedPiece findPieceMatch(MatchedPiece matchedPiece, Piece subPiece) {
		return findPieceMatch(matchedPiece, subPiece, false);
	}

	public MatchedPiece findPieceMatch(MatchedPiece matchedPiece, Piece subPiece, boolean checkRoot) {

		if ((subPiece == null || helper.checkPiecesMatched(matchedPiece, subPiece))
				&& (!checkRoot || helper.checkPiecesMatched(subPiece, root))) {
			matchedPiece.addMatchedPieces(new MatchedPiece(subPiece));
		}
		PieceId pieceId = subPiece.getId();
		PieceId newId = null;
		Piece newPiece = null;

		// rotated 3 times can be mirrored
		if (pieceId.getRotation() == 3 && !pieceId.isMirror()) {
			newId = new PieceId(subPiece.getId().getId(), 0, true);
			newPiece = new Piece(newId, helper.mirrorPiece(subPiece.getNodes()));

		} else if (pieceId.getRotation() < 3) {
			newId = new PieceId(subPiece.getId().getId(), pieceId.getRotation() + 1, pieceId.isMirror());
			newPiece = new Piece(newId, helper.rotatePiece(subPiece.getNodes()));

		} else if (pieceId.getRotation() == 3 && pieceId.isMirror()) {
			return matchedPiece;
		}

		newPiece.setId(newId);
		return findPieceMatch(matchedPiece, newPiece, checkRoot);

		// return matchedPiece;
	}

}
