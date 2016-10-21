/**
 * 
 */
package com.game.kalah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.manager.KalahManager;
import com.game.kalah.manager.KalahManagerImpl;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Player;


public class KalahIntegrationTest {

	@Test
	public void testMove() throws WrongSelectionException, NoStoneFoundException {
		KalahManager kalahManager = new KalahManagerImpl();
		KalahBoard board = kalahManager.prepareBoard("player1", "player2", 1);
		Player firstPlayer = board.getNextPlayer();
		
		/**
		 *    1  1  1  1  1  1
		 * 0                   0
		 *    1  1  1  1  1  1  
		 */
		
		kalahManager.move(board, firstPlayer, 5);
		Player secondPlayer = board.getOpponentPlayer(firstPlayer);
		assertEquals(firstPlayer, board.getNextPlayer());
		assertEquals(0, firstPlayer.getPit(5).getCountOfStones());
		
		/**
		 *    1  1  1  1  1  1
		 * 0                   1
		 *    1  1  1  1  1  0  
		 */
		

		kalahManager.move(board, firstPlayer, 4);
		assertEquals(3, firstPlayer.getKalah().getCountOfStones());
		assertEquals(0, secondPlayer.getPit(5).getCountOfStones());
		assertEquals(secondPlayer, board.getNextPlayer());
		
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
		
		kalahManager.move(board, secondPlayer,12);
		assertEquals(1, secondPlayer.getKalah().getCountOfStones());
		assertEquals(secondPlayer, board.getNextPlayer());
		
		/**
		 *    0  1  1  1  1  0
		 * 1                   3
		 *    1  1  1  1  0  0  
		 */
		
		kalahManager.move(board, secondPlayer, 11);
		assertEquals(3, secondPlayer.getKalah().getCountOfStones());
		assertEquals(0, firstPlayer.getPit(0).getCountOfStones());
		assertEquals(firstPlayer, board.getNextPlayer());
		
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
		
		kalahManager.move(board, firstPlayer, 3);

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
		
		kalahManager.move(board, secondPlayer, 10);
		
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
		
		kalahManager.move(board, firstPlayer, 2);
		
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
		
		assertEquals(true, board.isFinished());
		assertEquals(firstPlayer.getId(), board.getWinner().getId());
	}
	
}
