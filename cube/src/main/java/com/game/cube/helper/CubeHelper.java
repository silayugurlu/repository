package com.game.cube.helper;

import com.game.cube.model.MatchedPiece;
import com.game.cube.model.Piece;

public class CubeHelper {

	MatchedPiece rootPiece;
	
	/**
	 *       1
	 *   [][][][][]
	 *   [][][][][]
	 * 0 [][][][][] 2
	 *   [][][][][]
	 *   [][][][][]
	 *       3
	 * 
	 * @param piece
	 * @return
	 */

	public boolean checkPiecesMatched(Piece piece, Piece pieceToTry) {
		int vertice1 = piece.getNodes()[4][0] & pieceToTry.getNodes()[0][0];
		int vertice2 = piece.getNodes()[4][4] & pieceToTry.getNodes()[0][4];
		
		//check vertices
		if (vertice1 == 0 && vertice2 == 0) {
			
			//check edges
			for (int j = 1; j < 4; j++) {
				int result = piece.getNodes()[4][j] ^ pieceToTry.getNodes()[0][j];
				if(result != 1){
					return false;
				}
			}			
		}else{
			return false;
		}
		
		return true;

	}
	
	public boolean checkPiecesMatchedOn4Side(Piece pieceToTry, Piece piece1, Piece piece2, Piece piece3, Piece piece4) {
		int vertice1 = piece1.getNodes()[0][0] + piece4.getNodes()[4][0] + pieceToTry.getNodes()[0][4];
		int vertice2 = piece2.getNodes()[0][0] + piece1.getNodes()[4][0] + pieceToTry.getNodes()[4][4];
		int vertice3 = piece3.getNodes()[0][0] + piece2.getNodes()[4][0] + pieceToTry.getNodes()[4][0];
		int vertice4 = piece4.getNodes()[0][0] + piece3.getNodes()[4][0] + pieceToTry.getNodes()[0][0];
		
		//check vertices
		if (vertice1 == 1 && vertice2 == 1 && vertice3 == 1 && vertice4 == 1) {
			
			//check edges
			for (int j = 1; j < 4; j++) {
				int result = pieceToTry.getNodes()[j][4] ^ piece1.getNodes()[j][0];
				result &= pieceToTry.getNodes()[4][4-j] ^ piece2.getNodes()[j][0];
				result &= pieceToTry.getNodes()[4-j][0] ^ piece3.getNodes()[j][0];
				result &= pieceToTry.getNodes()[0][j] ^ piece4.getNodes()[j][0];
				if(result != 1){
					return false;
				}
			}			
		}else{
			return false;
		}
		
		return true;

	}
	
	public boolean checkPiecesMatchedOnOther4Side(Piece pieceToTry, Piece piece1, Piece piece2, Piece piece3, Piece piece4) {
		int vertice1 = piece1.getNodes()[0][4] + piece4.getNodes()[4][4] + pieceToTry.getNodes()[0][0];
		int vertice2 = piece2.getNodes()[0][4] + piece1.getNodes()[4][4] + pieceToTry.getNodes()[4][0];
		int vertice3 = piece3.getNodes()[0][4] + piece2.getNodes()[4][4] + pieceToTry.getNodes()[4][4];
		int vertice4 = piece4.getNodes()[0][4] + piece3.getNodes()[4][4] + pieceToTry.getNodes()[0][4];
		
		//check vertices
		if (vertice1 == 1 && vertice2 == 1 && vertice3 == 1 && vertice4 == 1) {
			
			//check edges
			for (int j = 1; j < 4; j++) {
				int result = pieceToTry.getNodes()[j][0] ^ piece1.getNodes()[j][4];
				result &= pieceToTry.getNodes()[4][j] ^ piece2.getNodes()[j][4];
				result &= pieceToTry.getNodes()[4-j][4] ^ piece3.getNodes()[j][4];
				result &= pieceToTry.getNodes()[0][4-j] ^ piece4.getNodes()[j][4];
				if(result != 1){
					return false;
				}
			}			
		}else{
			return false;
		}
		
		return true;

	}


	public int[][] rotatePiece(int[][] m) {
		int[][] nodes = new int[5][];

		nodes[0] = new int[] { m[4][0], m[3][0], m[2][0], m[1][0], m[0][0] };
		nodes[1] = new int[] { m[4][1], 1, 1, 1, m[0][1] };
		nodes[2] = new int[] { m[4][2], 1, 1, 1, m[0][2] };
		nodes[3] = new int[] { m[4][3], 1, 1, 1, m[0][3] };
		nodes[4] = new int[] { m[4][4], m[3][4], m[2][4], m[1][4], m[0][4] };

		return nodes;
	}
	
	public int[][] mirrorPiece(int[][] m) {
		int[][] nodes = new int[5][];

		nodes[0] = m[4];
		nodes[1] = m[3];
		nodes[2] = m[2];
		nodes[3] = m[1];
		nodes[4] = m[0];
		return nodes;
	}
	
	
	public boolean checkPiecesSame(Piece piece1, Piece piece2) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(piece1.getNodes()[i][j] != piece2.getNodes()[i][j]){
					return false;
				}
			}
		}
		
		return true;

	}
	
	

}
