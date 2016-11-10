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

	public boolean checkPiecesMatched(Piece piece1, Piece piece2) {
		int vertice1 = piece1.getNodes()[4][0] & piece2.getNodes()[0][0];
		int vertice2 = piece1.getNodes()[4][4] & piece2.getNodes()[0][4];
		
		//check vertices
		if (vertice1 == 0 && vertice2 == 0) {
			
			//check edges
			for (int j = 1; j < 4; j++) {
				int result = piece1.getNodes()[4][j] ^ piece2.getNodes()[0][j];
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
