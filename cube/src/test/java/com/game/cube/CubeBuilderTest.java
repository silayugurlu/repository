package com.game.cube;

import org.junit.Test;
import com.game.cube.helper.CubeBuilder;
import com.game.cube.helper.CubeHelper;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;

import static org.junit.Assert.*;

public class CubeBuilderTest {
	CubeBuilder builder = new CubeBuilder();
	CubeHelper helper =new CubeHelper();

	@Test
	public void testFindMatchedPiece() {

		Piece piece1 = new Piece(new PieceId(1), new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 } });
		Piece piece2 = new Piece(new PieceId(2), new int[][] { { 0, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } });
		Piece result = builder.findMatchedPieces(piece1, piece2);
		
		
		assertEquals(2 , result.getId().getId());
		assertEquals(0 , result.getId().getRotation());
		assertTrue(!result.getId().isMirror());
		
		assertTrue(helper.checkPiecesSame(result, piece2));
	}
	
	@Test
	public void testFindMatchedPieceRotated() {

		Piece piece1 = new Piece(new PieceId(1), new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 } });
		Piece piece2 = new Piece(new PieceId(2),helper.rotatePiece(new int[][] { { 0, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } }));
		
		Piece piece3 = new Piece(new PieceId(3), new int[][] { { 0, 1, 0, 1, 0 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } });
		Piece result = builder.findMatchedPieces(piece1, piece2);
		
		
		assertEquals(2 , result.getId().getId());
		assertEquals(3 , result.getId().getRotation());
		assertTrue(!result.getId().isMirror());
		
		assertTrue(helper.checkPiecesSame(result, piece3));
	}
	
	@Test
	public void testFindMatchedPieceMirrored() {

		Piece piece1 = new Piece(new PieceId(1), new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 0, 0, 1, 0 } });
		Piece piece2 = new Piece(new PieceId(2),helper.mirrorPiece(new int[][] { { 0, 1, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } }));
		
		Piece piece3 = new Piece(new PieceId(3), new int[][] { { 0, 1, 1, 0, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } });
		
		Piece result = builder.findMatchedPieces(piece1, piece2);
		
		
		assertEquals(2 , result.getId().getId());
		assertEquals(3 , result.getId().getRotation());
		assertTrue(result.getId().isMirror());
		
		assertTrue(helper.checkPiecesSame(result, piece3));
	}
	
	@Test
	public void testFindNotMatchedPiece() {

		Piece piece1 = new Piece(new PieceId(1), new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 0, 0, 1, 0 } });
		Piece piece2 = new Piece(new PieceId(2),new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 } });
		

		
		Piece result = builder.findMatchedPieces(piece1, piece2);
		
		assertNull(result);
	}


}
