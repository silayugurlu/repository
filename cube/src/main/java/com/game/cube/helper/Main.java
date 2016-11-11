package com.game.cube.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.game.cube.model.MatchedPiece;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;

public class Main {

	public static Piece[] prepareData() {
		int[][] nodes1 = { { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 },
				{ 0, 0, 1, 0, 0 } };

		Piece piece1 = new Piece(new PieceId(1), nodes1);

		int[][] nodes2 = { { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 0, 1 } };

		Piece piece2 = new Piece(new PieceId(2), nodes2);

		int[][] nodes3 = { { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 1 },
				{ 0, 0, 1, 0, 0 } };

		Piece piece3 = new Piece(new PieceId(3), nodes3);

		int[][] nodes4 = { { 0, 1, 0, 1, 0 }, { 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0 },
				{ 1, 1, 0, 1, 0 } };

		Piece piece4 = new Piece(new PieceId(4), nodes4);

		int[][] nodes5 = { { 0, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 0 }, { 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 0, 0 } };

		Piece piece5 = new Piece(new PieceId(5), nodes5);

		int[][] nodes6 = { { 0, 1, 0, 1, 0 }, { 0, 1, 1, 1, 1 }, { 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 1 },
				{ 1, 1, 0, 1, 1 } };

		Piece piece6 = new Piece(new PieceId(6), nodes6);

		Piece[] pieces = { piece1, piece2, piece3, piece4, piece5, piece6 };
		return pieces;
	}
	public static void main(String[] args) {
		CubeBuilder m = new CubeBuilder();

		Piece[] pieces = prepareData();

		MatchedPiece matchedPiece = m.buildCube(Arrays.asList(pieces));
		
		Piece[] result = new Piece[6];
		int counter = 0;
		int index = 0;
		while (matchedPiece != null) {
			putArray(matchedPiece, result, counter);
			if (matchedPiece.getMatchedPieces() != null && !matchedPiece.getMatchedPieces().isEmpty()) {
				matchedPiece = matchedPiece.getMatchedPieces().get(0);
			} else {

				matchedPiece = null;

			}
			counter++;
		}
		
		System.out.println(pieces[0].toStringList().get(0));

		List<String> lines = new ArrayList<String>();

		for (int i = 0; i < 5; i++) {
			String line = result[0].convertLineString(i) + result[1].convertLineString(i)
					+ result[2].convertLineString(i);
			
//			String line =  result[1].convertLineString(i);
					
			lines.add(line);
		}

		for (int i = 3; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				String line = "          " + result[i].convertLineString(j) + "          ";
				lines.add(line);
			}
		}

		Path file = Paths.get("the-file-name.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// // TODO Auto-generated method stub

	}
	
	private static void putArray(MatchedPiece matchedPiece, Piece[] result, int counter) {
		if (counter == 0) {
			result[1] = matchedPiece;
		} else if (counter == 1) {
			result[3] = matchedPiece;
		} else if (counter == 2) {
			result[4] = matchedPiece;
		} else if (counter == 3) {
			result[5] = matchedPiece;
		} else if (counter == 4) {
			result[0] = matchedPiece;
		} else if (counter == 5) {
			result[2] = matchedPiece;
		}
	}

}
