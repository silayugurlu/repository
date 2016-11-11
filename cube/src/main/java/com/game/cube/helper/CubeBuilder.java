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

	public MatchedPiece buildCube(List<Piece> pieces) {
		root = new MatchedPiece(pieces.get(0));

		List<Piece> iterationList = new ArrayList<Piece>();
		iterationList.addAll(pieces);
		iterationList.remove(root);

		for (Piece pieceToTry : iterationList) {
			findPieceMatchInLine(root, pieceToTry);
		}

		List<MatchedPiece> matchedList1 = new ArrayList<MatchedPiece>();
		for (MatchedPiece piece : root.getMatchedPieces()) {
			for (Piece pieceToTry : iterationList) {
				if (!pieceToTry.equals(piece)) {
					if (findPieceMatchInLine(piece, pieceToTry))
						;
					{
						matchedList1.add(piece);
					}
				}
			}
		}
		root.setMatchedPieces(matchedList1);

		List<MatchedPiece> matchedListRoot = new ArrayList<MatchedPiece>();
		for (MatchedPiece piece1 : root.getMatchedPieces()) {
			List<MatchedPiece> matchedList2 = new ArrayList<MatchedPiece>();
			for (MatchedPiece piece2 : piece1.getMatchedPieces()) {
				for (Piece pieceToTry : iterationList) {
					if (!pieceToTry.equals(piece1) && !pieceToTry.equals(piece2)) {
						if (findPieceMatchInLine(piece2, pieceToTry, true)) {
							matchedList2.add(piece2);
						}
					}
				}
			}
			if (!matchedList2.isEmpty()) {
				piece1.setMatchedPieces(matchedList2);
				matchedListRoot.add(piece1);
			}
		}
		root.setMatchedPieces(matchedListRoot);

		List<MatchedPiece> matchedListRoot1 = new ArrayList<MatchedPiece>();
		for (MatchedPiece piece1 : root.getMatchedPieces()) {
			List<MatchedPiece> matchedList2 = new ArrayList<MatchedPiece>();

			for (MatchedPiece piece2 : piece1.getMatchedPieces()) {

				List<MatchedPiece> matchedList3 = new ArrayList<MatchedPiece>();
				for (MatchedPiece piece3 : piece2.getMatchedPieces()) {

					Piece firstPiece = null;
					Piece secondPiece = null;
					for (Piece pieceToTry : iterationList) {
						if (firstPiece == null && !pieceToTry.equals(piece1) && !pieceToTry.equals(piece2)
								&& !pieceToTry.equals(piece3)) {

							firstPiece = pieceToTry;

						} else if (firstPiece != null && !pieceToTry.equals(firstPiece) && !pieceToTry.equals(piece1)
								&& !pieceToTry.equals(piece2) && !pieceToTry.equals(piece3)) {

							secondPiece = pieceToTry;

						}

					}
					if (firstPiece != null && secondPiece != null) {
						if (findPieceMatchFourSide(firstPiece, root, piece1, piece2, piece3)) {
							if (!findPieceMatchOtherFourSide(secondPiece, root, piece1, piece2, piece3,
									piece3.getMatchedPieces().get(0))) {
								piece3.getMatchedPieces().clear();
							}
						}

						if (findPieceMatchFourSide(secondPiece, root, piece1, piece2, piece3)) {
							if (piece3.getMatchedPieces().size() > 1) {
								if (!findPieceMatchOtherFourSide(firstPiece, root, piece1, piece2, piece3,
										piece3.getMatchedPieces().get(1))) {
									piece3.getMatchedPieces().remove(1);
								}
							} else {
								if (!findPieceMatchOtherFourSide(firstPiece, root, piece1, piece2, piece3,
										piece3.getMatchedPieces().get(0))) {
									piece3.getMatchedPieces().clear();
								}
							}

						}

						if (piece3.getMatchedPieces() != null && !piece3.getMatchedPieces().isEmpty()
								&& piece3.getMatchedPieces().get(0) != null && piece3.getMatchedPieces().get(0).getMatchedPieces() != null &&!piece3.getMatchedPieces().get(0).getMatchedPieces().isEmpty()) {
							matchedList3.add(piece3);
						}
					}

				}
				if (!matchedList3.isEmpty()) {
					piece2.setMatchedPieces(matchedList3);
					matchedList2.add(piece2);
				}

			}
			if (!matchedList2.isEmpty()) {
				piece1.setMatchedPieces(matchedList2);
				matchedListRoot1.add(piece1);
			}
		}
		root.setMatchedPieces(matchedListRoot1);
		return root;
	}

	public boolean findPieceMatchFourSide(Piece subPiece, MatchedPiece root, MatchedPiece piece1, MatchedPiece piece2,
			MatchedPiece matchedPiece) {

		if (subPiece == null || helper.checkPiecesMatchedOn4Side(subPiece, root, piece1, piece2, matchedPiece)) {
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
			return (matchedPiece.getMatchedPieces() != null && !matchedPiece.getMatchedPieces().isEmpty());
		}

		newPiece.setId(newId);
		return findPieceMatchFourSide(newPiece, root, piece1, piece2, matchedPiece);

	}

	public boolean findPieceMatchOtherFourSide(Piece subPiece, MatchedPiece root, MatchedPiece piece1,
			MatchedPiece piece2, MatchedPiece piece3, MatchedPiece matchedPiece) {

		if (subPiece == null || helper.checkPiecesMatchedOnOther4Side(subPiece, root, piece1, piece2, piece3)) {
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
			return matchedPiece.getMatchedPieces() != null;
		}

		newPiece.setId(newId);
		return findPieceMatchOtherFourSide(newPiece, root, piece1, piece2, piece3, matchedPiece);

	}

	public boolean findPieceMatchInLine(MatchedPiece matchedPiece, Piece subPiece) {
		return findPieceMatchInLine(matchedPiece, subPiece, false);
	}

	public boolean findPieceMatchInLine(MatchedPiece matchedPiece, Piece subPiece, boolean checkRoot) {

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
			return matchedPiece.getMatchedPieces() != null;
		}

		newPiece.setId(newId);
		return findPieceMatchInLine(matchedPiece, newPiece, checkRoot);
	}

}
