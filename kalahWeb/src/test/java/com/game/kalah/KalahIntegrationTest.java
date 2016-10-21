/**
 * 
 */
package com.game.kalah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.game.kalah.exception.KalahSelectedException;
import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.manager.KalahManager;
import com.game.kalah.manager.KalahManagerImpl;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Player;


public class KalahIntegrationTest {

	@Test
	public void testMove() throws WrongSelectionException, NoStoneFoundException, KalahSelectedException {
		KalahManager kalahManager = new KalahManagerImpl();
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
		/**
		 *    1  1  1  1  1  1
		 * 0                   1
		 *    1  1  1  1  0  1  
		 */
		
		/**
		 *    1  1  1  1  1  0
		 * 0                   3
		 *    1  1  1  1  0  0  
		 */
		
		moveId= secondPlayer.getKalah().getId()-1;
		kalahManager.move(board, secondPlayer,moveId);
		assertEquals(1, secondPlayer.getKalah().getCountOfStones());
		assertEquals(secondPlayer, board.getNextPlayer());
		assertEquals(false, board.isGameOver());
		/**
		 *    0  1  1  1  1  0
		 * 1                   3
		 *    1  1  1  1  0  0  
		 */
		
		kalahManager.move(board, secondPlayer, moveId-1);
		assertEquals(3, secondPlayer.getKalah().getCountOfStones());
		assertEquals(0, board.getPits().get(moveId).getCountOfStones());
		assertEquals(firstPlayer, board.getNextPlayer());
		assertEquals(false, board.isGameOver());
		/**
		 *    1  0  1  1  1  0
		 * 1                   3
		 *    1  1  1  1  0  0  
		 */
		
		/**
		 *    0  0  1  1  1  0
		 * 3                   3
		 *    0  1  1  1  0  0  
		 */
		
		moveId= firstPlayer.getKalah().getId()-3;
		kalahManager.move(board, firstPlayer, moveId);
		assertEquals(false, board.isGameOver());
		/**
		 *    0  0  1  1  1  0
		 * 3                   3
		 *    0  1  1  0  1  0  
		 */
		
		/**
		 *    0  0  1  1  0  0
		 * 3                   5
		 *    0  1  1  0  0  0  
		 */
		
		moveId= secondPlayer.getKalah().getId()-3;
		kalahManager.move(board, secondPlayer, moveId);
		assertEquals(false, board.isGameOver());
		/**
		 *    0  1  0  1  0  0
		 * 3                   5
		 *    0  1  1  0  0  0  
		 */
		
		/**
		 *    0  0  0  1  0  0
		 * 5                   5
		 *    0  0  1  0  0  0  
		 */
		
		moveId= firstPlayer.getKalah().getId()-4;
		kalahManager.move(board, firstPlayer, moveId);

		/**
		 *    0  0  0  1  0  0
		 * 5                   5
		 *    0  0  0  1  0  0  
		 */
		
		/**
		 *    0  0  0  0  0  0
		 * 5                   7
		 *    0  0  0  0  0  0  
		 */
		
		assertEquals(true, board.isGameOver());
		assertEquals(firstPlayer.getId(), board.getWinner().getId());
	}
	
}
