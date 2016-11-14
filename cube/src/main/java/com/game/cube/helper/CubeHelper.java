package com.game.cube.helper;

import java.util.ArrayList;
import java.util.List;

import com.game.cube.model.Edge;
import com.game.cube.model.MatchedPiece;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;

public class CubeHelper {
	
	/**
	 * Rotates and mirror piece 	 * 
	 * 
	 * @param piece to change
	 * @param rotation  rotation count
	 * @param mirror boolean value to mirror or not
	 * @return changed Piece
	 * 
	 */
	public Piece changePiece(Piece piece, int rotation, boolean mirror) {
		PieceId newId = new PieceId(piece.getId().getId(), rotation, mirror);
		Piece newPiece = new Piece(newId, piece.getNodes());
		if (newId.isMirror()) {
			newPiece.setNodes(mirrorPiece(piece.getNodes()));
		}
		for (int i = 0; i < newId.getRotation(); i++) {
			newPiece.setNodes(rotatePiece(piece.getNodes()));
		}
		return newPiece;
	}

	/**
	 * Rotates nodes ones to right
	 * 
	 * @param nodes to rotate
	 * @return rotated nodes
	 */
	public int[][] rotatePiece(int[][] nodes) {
		int[][] newnodes = new int[5][];

		newnodes[0] = new int[] { nodes[4][0], nodes[3][0], nodes[2][0], nodes[1][0], nodes[0][0] };
		newnodes[1] = new int[] { nodes[4][1], 1, 1, 1, nodes[0][1] };
		newnodes[2] = new int[] { nodes[4][2], 1, 1, 1, nodes[0][2] };
		newnodes[3] = new int[] { nodes[4][3], 1, 1, 1, nodes[0][3] };
		newnodes[4] = new int[] { nodes[4][4], nodes[3][4], nodes[2][4], nodes[1][4], nodes[0][4] };

		return newnodes;
	}

	/**
	 * Mirrors node to up
	 * 
	 * @param nodes to mirror
	 * @return mirrored nodes
	 */
	public int[][] mirrorPiece(int[][] nodes) {
		int[][] newnodes = new int[5][];

		newnodes[0] = nodes[4];
		newnodes[1] = nodes[3];
		newnodes[2] = nodes[2];
		newnodes[3] = nodes[1];
		newnodes[4] = nodes[0];
		return newnodes;
	}

	public boolean checkPiecesSame(Piece piece1, Piece piece2) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (piece1.getNodes()[i][j] != piece2.getNodes()[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Builds cube, get pieces in puzzle and returns 
	 * matchedPiece contains all possible matched pieces
	 * 
	 * @param pieces in puzzle
	 * @return root matchedPiece contains all possible matched pieces 
	 */
	public MatchedPiece buildCube(List<Piece> pieces) {
		
		// first put pieces in line order that surround the cube
		MatchedPiece root = putPiecesInLine(pieces); 
		
		// then put pieces to left and right
		putPiecesLeftAndRight(pieces, root);
		return root;
	}

	/**
	 * Finds first 4 pieces putting in line as a circle
	 * finds pieces that one of its edges match the bottom edge of the upper piece
	 * 
	 * @param pieces all 6 pieces in puzzle
	 * @return matched root piece that contains other possible 3 pieces
	 */
	public MatchedPiece putPiecesInLine(List<Piece> pieces) {
		
		// put first piece as a root
		MatchedPiece root = new MatchedPiece(pieces.get(0));
		
		//put possible pieces as second piece
		Edge rootEdge = root.getEdges().get(2);
		for (Piece piece : pieces) {
			if (!piece.equals(root)) {
				root.getMatchedPieces().addAll(checkPieceOneSide(rootEdge, piece));
			}
		}

		//put possible pieces as third piece
		List<Piece> piecesToIterate = new ArrayList<Piece>();
		piecesToIterate.addAll(pieces);
		piecesToIterate.remove(root);

		List<MatchedPiece> matchedList1 = new ArrayList<MatchedPiece>();
		for (MatchedPiece pieceL1 : root.getMatchedPieces()) {
			piecesToIterate.remove(pieceL1);
			for (Piece piece : piecesToIterate) {
				Edge edge = pieceL1.getEdges().get(2);
				pieceL1.getMatchedPieces().addAll(checkPieceOneSide(edge, piece));
			}
			if (!pieceL1.getMatchedPieces().isEmpty()) {
				matchedList1.add(pieceL1);
			}
			piecesToIterate.add(pieceL1);
		}
		//remove pieces that can not matched any other piece
		root.getMatchedPieces().retainAll(matchedList1);

		
		//put possible pieces as fourth piece that also matches with first piece
		List<MatchedPiece> matchedList = new ArrayList<MatchedPiece>();
		for (MatchedPiece pieceL1 : root.getMatchedPieces()) {
			piecesToIterate.remove(pieceL1);

			List<MatchedPiece> matchedList2 = new ArrayList<MatchedPiece>();
			for (MatchedPiece pieceL2 : pieceL1.getMatchedPieces()) {
				piecesToIterate.remove(pieceL2);
				for (Piece piece : piecesToIterate) {
					Edge edge = pieceL2.getEdges().get(2);
					pieceL2.getMatchedPieces().addAll(checkPieceOneSide(edge, piece, true, root.getEdges().get(0)));
				}
				//check and put to list if piece matches at least one piece
				if (!pieceL2.getMatchedPieces().isEmpty()) {
					matchedList2.add(pieceL2);
				}
				piecesToIterate.add(pieceL2);
			}
			//remove pieces that can not matched any other piece
			pieceL1.getMatchedPieces().retainAll(matchedList2);
			
			//check and put to list if piece matches at least one piece
			if (!pieceL1.getMatchedPieces().isEmpty()) {
				matchedList.add(pieceL1);
			}
			//remove pieces that can not matched any other piece
			root.getMatchedPieces().retainAll(matchedList);
			piecesToIterate.add(pieceL1);
		}
		return root;
	}

	
	/**
	 * Finds pieces on left and right of the cube and adds to the matched piece
	 * 
	 * @param pieces all pieces in puzzle
	 * @param root first root matched piece contains all possible matched piece
	 */
	public void putPiecesLeftAndRight(List<Piece> pieces, MatchedPiece root) {
		List<Piece> piecesToIterate = new ArrayList<Piece>();
		piecesToIterate.addAll(pieces);
		piecesToIterate.remove(root);

		List<MatchedPiece> matchedList1 = new ArrayList<MatchedPiece>();
		for (MatchedPiece pieceL1 : root.getMatchedPieces()) {
			piecesToIterate.remove(pieceL1);

			List<MatchedPiece> matchedList2 = new ArrayList<MatchedPiece>();
			for (MatchedPiece pieceL2 : pieceL1.getMatchedPieces()) {
				piecesToIterate.remove(pieceL2);
				List<MatchedPiece> matchedList3 = new ArrayList<MatchedPiece>();
				for (MatchedPiece pieceL3 : pieceL2.getMatchedPieces()) {
					piecesToIterate.remove(pieceL3);
					
					// two pieces left
					Piece lastPiece1 = piecesToIterate.get(0);
					Piece lastPiece2 = piecesToIterate.get(1);

					pieceL3.getMatchedPieces()
							.addAll(checkPieceFourSide(lastPiece1, lastPiece2, root, pieceL1, pieceL2, pieceL3));
					pieceL3.getMatchedPieces()
							.addAll(checkPieceFourSide(lastPiece2, lastPiece1, root, pieceL1, pieceL2, pieceL3));

					//check and put to list if piece matches at least one piece
					if (!pieceL3.getMatchedPieces().isEmpty()) {
						matchedList3.add(pieceL3);
					}
					piecesToIterate.add(pieceL3);
				}
				//remove pieces that can not matched any other piece
				pieceL2.getMatchedPieces().retainAll(matchedList3);
				
				//check and put to list if piece matches at least one piece
				if (!pieceL2.getMatchedPieces().isEmpty()) {
					matchedList2.add(pieceL2);
				}
				piecesToIterate.add(pieceL2);
			}
			//remove pieces that can not matched any other piece
			pieceL1.getMatchedPieces().retainAll(matchedList2);
			
			//check and put to list if piece matches at least one piece
			if (!pieceL1.getMatchedPieces().isEmpty()) {
				matchedList1.add(pieceL1);
			}
			piecesToIterate.add(pieceL1);
		}
		//remove pieces that can not matched any other piece
		root.getMatchedPieces().retainAll(matchedList1);
	}

	public List<MatchedPiece> checkPieceOneSide(Edge matchedEdge, Piece piece) {
		return checkPieceOneSide(matchedEdge, piece, false, null);
	}

	/**
	 * Checks if any edge of the piece is matched with the given edge by rotating and mirroring the piece
	 * 
	 * @param matchedEdge edge to be matched
	 * @param piece is checked if it is mactched
	 * @param checkOpposite  if the edge of the piece is checked with the reverse value
	 * @param oppositeEdge the opposite edge of teh matched edge, is used to check piece matches both bottom and up sides.
	 * @return all possible pieces that matches
	 */
	public List<MatchedPiece> checkPieceOneSide(Edge matchedEdge, Piece piece, boolean checkOpposite, Edge oppositeEdge) {

		List<MatchedPiece> matchedPieces = new ArrayList<MatchedPiece>();

		List<Edge> edges = piece.getEdges();

		int rotate = 0;
		for (Edge edge : edges) {
			if (checkTwoEdgesMatch(matchedEdge, edge, true) && (!checkOpposite
					|| (checkOpposite && checkTwoEdgesMatch(edges.get((rotate + 2) % 4), oppositeEdge, true)))) {

				Piece newPiece = changePiece(piece, rotate, false);
				matchedPieces.add(new MatchedPiece(newPiece));

			}
			if (piece.isSymmetric() && rotate == 1) {
				break;
			}
			if (piece.isAllEdgesSame()) {
				break;
			}
			rotate++;
		}
		if (!piece.isMirrorSame()) {
			rotate = 0;
			edges = piece.getMirrorEdges();

			for (Edge edge : edges) {
				if (checkTwoEdgesMatch(matchedEdge, edge, true) && (!checkOpposite
						|| (checkOpposite && checkTwoEdgesMatch(edges.get((rotate + 2) % 4), oppositeEdge, true)))) {
					Piece newPiece = changePiece(piece, rotate, true);
					matchedPieces.add(new MatchedPiece(newPiece));
				}
				if (piece.isSymmetric() && rotate == 1) {
					break;
				}
				if (piece.isAllEdgesSame()) {
					break;
				}
				rotate++;
			}
		}
		return matchedPieces;
	}

	
	/**
	 * Checks if edges of the last two pieces match edges on the left and right of the incomplete cube
	 * 
	 * @param lastPiece1 one of the last two pieces
	 * @param lastPiece2 one of the last two pieces
	 * @param pieces all in incomplete cube
	 * @return all possible pieces that matched
	 */
	public List<MatchedPiece> checkPieceFourSide(Piece lastPiece1, Piece lastPiece2, MatchedPiece... pieces) {

		List<MatchedPiece> matchedPieces = new ArrayList<MatchedPiece>();
		Piece newPiece1 = lastPiece1;
		Piece newPiece2 = lastPiece2;

		int rotationCount1 = lastPiece1.isAllEdgesSame() ? 2 : (lastPiece1.isSymmetric() ? 4 : 8);
		int rotationCount2 = lastPiece2.isAllEdgesSame() ? 2 : (lastPiece2.isSymmetric() ? 4 : 8);

		for (int i = 1; i <= rotationCount1; i++) {
			if (checkFourEdgesMatchLeft(newPiece1, pieces[0].getEdge(3), pieces[1].getEdge(3), pieces[2].getEdge(3),
					pieces[3].getEdge(3))) {
				for (int j = 1; j <= rotationCount2; j++) {
					if (checkFourEdgesMatchRight(newPiece2, pieces[0].getEdge(1), pieces[1].getEdge(1),
							pieces[2].getEdge(1), pieces[3].getEdge(1))) {
						MatchedPiece matchedPiece = new MatchedPiece(newPiece1);
						matchedPiece.addMatchedPieces(new MatchedPiece(newPiece2));
						matchedPieces.add(matchedPiece);
						break;
					} else {
						if (j == rotationCount2 / 2) {
							if (lastPiece2.isMirrorSame()) {
								break;
							}
							newPiece2 = changePiece(lastPiece2, 0, true);
						} else {
							newPiece2 = changePiece(newPiece2, j % (rotationCount2 / 2), false);
						}
					}
				}
				break;
			} else {
				if (i == (rotationCount1 / 2)) {
					if (lastPiece1.isMirrorSame()) {
						break;
					}
					newPiece1 = changePiece(lastPiece1, 0, true);
				} else {
					newPiece1 = changePiece(newPiece1, i % (rotationCount1 / 2), false);
				}
			}
		}
		return matchedPieces;
	}

	public boolean checkTwoVerticesMatch(Edge edge1, Edge edge2, boolean reverse) {
		if (reverse) {
			return (edge1.getStartVertice() + edge2.getEndVertice() <= 1)
					&& (edge1.getEndVertice() + edge2.getStartVertice() <= 1);
		} else {
			return (edge1.getStartVertice() + edge2.getStartVertice() <= 1)
					&& (edge1.getEndVertice() + edge2.getEndVertice() <= 1);
		}
	}

	public boolean checkTwoEdgesMatch(Edge edge1, Edge edge2, boolean reverse) {
		return (edge1.getValue() + edge2.getReverseValue() == 7) && checkTwoVerticesMatch(edge1, edge2, reverse);
	}

	/**
	 * Checks four edges of the piece if they can be matched with the left side of the matched pieces.
	 * 
	 * @param piece to check if matches
	 * @param edges on left side of the incomplete cube
	 * @return the result, it is true if matched, false if not
	 */
	public boolean checkFourEdgesMatchLeft(Piece piece, Edge... edges) {
		Edge possibleEdge = piece.getEdge(0);
		int vertice = possibleEdge.getStartVertice() + edges[3].getEndVertice() + edges[2].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[3].getReverseValue() == 7)) {

			return false;
		}

		possibleEdge = piece.getEdge(1);
		vertice = possibleEdge.getStartVertice() + edges[0].getEndVertice() + edges[3].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[0].getReverseValue() == 7)) {

			return false;
		}

		possibleEdge = piece.getEdge(2);
		vertice = possibleEdge.getStartVertice() + edges[1].getEndVertice() + edges[0].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[1].getReverseValue() == 7)) {

			return false;
		}

		possibleEdge = piece.getEdge(3);
		vertice = possibleEdge.getStartVertice() + edges[2].getEndVertice() + edges[1].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[2].getReverseValue() == 7)) {

			return false;
		}

		return true;
	}

	/**
	 * Checks four edges of the piece if they can be matched with the right side of the matched pieces.
	 * 
	 * @param piece to check if matches
	 * @param edges on right side of the incomplete cube
	 * @return the result, it is true if matched, false if not
	 */
	public boolean checkFourEdgesMatchRight(Piece piece, Edge... edges) {
		Edge possibleEdge = piece.getEdge(0);
		int vertice = possibleEdge.getStartVertice() + edges[3].getEndVertice() + edges[0].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[3].getReverseValue() == 7)) {
			return false;
		}

		possibleEdge = piece.getEdge(1);
		vertice = possibleEdge.getStartVertice() + edges[2].getEndVertice() + edges[3].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[2].getReverseValue() == 7)) {

			return false;
		}

		possibleEdge = piece.getEdge(2);
		vertice = possibleEdge.getStartVertice() + edges[1].getEndVertice() + edges[2].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[1].getReverseValue() == 7)) {

			return false;
		}

		possibleEdge = piece.getEdge(3);
		vertice = possibleEdge.getStartVertice() + edges[0].getEndVertice() + edges[1].getStartVertice();

		if (!(vertice == 1 && possibleEdge.getValue() + edges[0].getReverseValue() == 7)) {

			return false;
		}

		return true;
	}

}
