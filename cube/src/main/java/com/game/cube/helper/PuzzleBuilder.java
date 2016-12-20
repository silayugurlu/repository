package com.game.cube.helper;

import com.game.cube.model.Edge;
import com.game.cube.model.Piece;

public class PuzzleBuilder {

	public void buildPuzzle() {

		for (int i = 1; i <= 6; i++) {
			for (int j = 1; j <= 6; j++) {
				for (int k = 1; k <= 6; k++) {
					for (int m = 1; m <= 6; m++) {

						Piece[] puzzleArr = new Piece[6];
						Piece[] cubeArr = new Piece[6];

						Piece p1 = new Piece();

						p1.addEdge(new Edge(0, m, 0));
						p1.addEdge(new Edge(0, k, 0));
						p1.addEdge(new Edge(0, j, 0));
						p1.addEdge(new Edge(0, i, 0));

						for (int r = 1; r <= 6; r++) {
							for (int s = 1; s <= 6; s++) {
								Piece p2 = new Piece();
								Edge edge1 = new Edge(0, n, 0);
								p2.addEdge(new Edge(0, 7 - p1.getEdge(2).getReverseValue(), 0));
								p2.addEdge(edge1);
								p2.addEdge(new Edge(0, p, 0));
								p2.addEdge(new Edge(0, 7-edge1.getReverseValue(), 0));
								
								for (int n = 1; n <= 6; n++) {
									for (int p = 1; p <= 6; p++) {
										Piece p2 = new Piece();
										Edge edge1 = new Edge(0, n, 0);
										p2.addEdge(new Edge(0, 7 - p1.getEdge(2).getReverseValue(), 0));
										p2.addEdge(edge1);
										p2.addEdge(new Edge(0, p, 0));
										p2.addEdge(new Edge(0, 7-edge1.getReverseValue(), 0));
										
										
									}
								}
							}
						}

						Piece p3 = new Piece();

						p3.addEdge(new Edge(0, m, 0));
						p3.addEdge(new Edge(0, k, 0));
						p3.addEdge(new Edge(0, j, 0));
						p3.addEdge(new Edge(0, i, 0));

						Piece p4 = new Piece();

						p4.addEdge(new Edge(0, m, 0));
						p4.addEdge(new Edge(0, k, 0));
						p4.addEdge(new Edge(0, j, 0));
						p4.addEdge(new Edge(0, i, 0));

						Piece p5 = new Piece();

						p5.addEdge(new Edge(0, m, 0));
						p5.addEdge(new Edge(0, k, 0));
						p5.addEdge(new Edge(0, j, 0));
						p5.addEdge(new Edge(0, i, 0));

						Piece p6 = new Piece();

						p6.addEdge(new Edge(0, m, 0));
						p6.addEdge(new Edge(0, k, 0));
						p6.addEdge(new Edge(0, j, 0));
						p6.addEdge(new Edge(0, i, 0));
					}
				}
			}
		}
	}

}
