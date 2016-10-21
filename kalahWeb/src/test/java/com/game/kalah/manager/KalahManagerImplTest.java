package com.game.kalah.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.game.kalah.exception.KalahException;
import com.game.kalah.exception.KalahSelectedException;
import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;

public class KalahManagerImplTest {

	KalahBoard board;

	KalahManagerImpl kalahManager;

	@Before
	public void setUp() {
		kalahManager = new KalahManagerImpl();
		board = kalahManager.prepareBoard("player1", "player2", 6);
	}

	@Test
	public void testPrepareBoardPlayers() {

		assertEquals("player1", board.getPlayer1().getName());
		assertEquals("player2", board.getPlayer2().getName());

		assertEquals(6, board.getPlayer1().getPits().size());
		assertEquals(6, board.getPlayer2().getPits().size());

		assertTrue(board.getPlayer1().getKalah() instanceof Kalah);
		assertTrue(board.getPlayer2().getKalah() instanceof Kalah);

		assertEquals(12, board.getPlayer2().getPits().get(0).getId());
		assertEquals(board.getPits().get(11), board.getPlayer2().getPits().get(1));
	}

	@Test
	public void testPrepareBoardPits() {
		assertEquals(14, board.getPits().size());
		assertEquals(6, board.getPits().get(0).getCountOfStones());

		assertTrue(board.getPits().get(6) instanceof Kalah);
		assertTrue(board.getPits().get(13) instanceof Kalah);

	}

	@Test
	public void testSowStones() throws KalahException {

		kalahManager.sowStones(board, board.getPlayer1(), 2);

		assertEquals(0, board.getPits().get(2).getCountOfStones()); // get all
																	// stones
																	// from pit2
		assertEquals(7, board.getPits().get(3).getCountOfStones());
		assertEquals(7, board.getPits().get(4).getCountOfStones());
		assertEquals(7, board.getPits().get(5).getCountOfStones());

		assertEquals(1, board.getPlayer1().getKalah().getCountOfStones()); // kalah

		assertEquals(7, board.getPlayer2().getPit(5).getCountOfStones());
		assertEquals(7, board.getPlayer2().getPit(4).getCountOfStones());
		assertEquals(6, board.getPlayer2().getPit(3).getCountOfStones());
		assertEquals(1, board.getPlayer1().getKalah().getCountOfStones());

	}

	@Test
	public void testSowStonesSkipKalah() throws KalahException {
		board = kalahManager.prepareBoard("player1", "player2", 9);

		kalahManager.sowStones(board, board.getPlayer1(), 5);

		assertEquals(0, board.getPits().get(5).getCountOfStones()); // get all
																	// stones
																	// from pit5
		assertEquals(10, board.getPits().get(7).getCountOfStones());

		assertEquals(0, board.getPlayer2().getKalah().getCountOfStones()); // opponent's
																			// kalah
		assertEquals(10, board.getPlayer2().getPit(0).getCountOfStones());

		assertEquals(1, board.getPlayer1().getKalah().getCountOfStones());
		assertEquals(10, board.getPlayer1().getPit(0).getCountOfStones());

	}

	@Test(expected = NoStoneFoundException.class)
	public void testSowStonesNoSelection() throws KalahException {
		kalahManager.sowStones(board, board.getPlayer1(), 2);
		kalahManager.sowStones(board, board.getPlayer1(), 2);
	}

	@Test(expected = WrongSelectionException.class)
	public void testSowStonesException() throws KalahException {
		kalahManager.sowStones(board, board.getPlayer2(), 2);
	}

	@Test(expected = KalahSelectedException.class)
	public void testSowStonesExceptionKalah() throws KalahException {
		kalahManager.sowStones(board, board.getPlayer2(), 13);
	}

	@Test
	public void testPutStonesToKalah() throws WrongSelectionException {
		kalahManager.putStonestoKalah(board, board.getPlayer2(), 9);
		assertEquals(0, board.getPits().get(9).getCountOfStones());
		assertEquals(0, board.getPits().get(3).getCountOfStones());
		assertEquals(12, board.getPlayer2().getKalah().getCountOfStones());

	}

	@Test(expected = WrongSelectionException.class)
	public void testPutStonesToKalahWrongSelection() throws KalahException {
		kalahManager.sowStones(board, board.getPlayer1(), 7);
	}

	@Test(expected = KalahSelectedException.class)
	public void testPutStonesWrongSelection() throws KalahException {
		kalahManager.sowStones(board, board.getPlayer1(), 6);
	}

	
	@Test
	public void testNextMovement() throws KalahException {
		KalahBoard board = kalahManager.prepareBoard("player1", "player2", 1);
		Player firstPlayer = board.getNextPlayer();
		Player secondPlayer = board.getOpponentPlayer(firstPlayer);
		
		/**
		 *    1  1  1  1  1  1
		 * 0                   0
		 *    1  1  1  1  1  1  
		 */
		
		int moveId= firstPlayer.getKalah().getId()-1;
		
		kalahManager.move(board, firstPlayer, moveId);
		
		assertEquals(firstPlayer, board.getNextPlayer());
		assertEquals(0, board.getPits().get(moveId).getCountOfStones());
		assertEquals(false, board.isGameOver());
		/**
		 *    1  1  1  1  1  1
		 * 0                   1
		 *    1  1  1  1  1  0  
		 */
		

		kalahManager.move(board, firstPlayer, moveId-1);
		assertEquals(3, firstPlayer.getKalah().getCountOfStones());
		assertEquals(0, board.getPits().get(moveId).getCountOfStones());
		assertEquals(secondPlayer, board.getNextPlayer());
		assertEquals(false, board.isGameOver());
	}
	
	@Test
	public void testGameOver() throws KalahException {
		KalahBoard board = kalahManager.prepareBoard("player1", "player2", 1);
		Player firstPlayer = board.getNextPlayer();
		Player secondPlayer = board.getOpponentPlayer(firstPlayer);

		for(Pit pit :firstPlayer.getPits()){
			pit.setCountOfStones(2);
		}
		for(Pit pit :secondPlayer.getPits()){
			pit.setCountOfStones(0);
		}

		/**
		 *    2  2  2  2  2  2
		 * 0                   0
		 *    0  0  0  0  0  0  
		 */
		
		int moveId= firstPlayer.getKalah().getId()-5;
		
		kalahManager.move(board, firstPlayer, moveId);
		

		assertEquals(12, firstPlayer.getKalah().getCountOfStones());
		assertEquals(0, secondPlayer.getKalah().getCountOfStones());
		assertEquals(true, board.isGameOver());
		assertEquals(firstPlayer, board.getWinner());
		
	}

}
