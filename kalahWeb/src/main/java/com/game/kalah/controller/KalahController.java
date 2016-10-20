package com.game.kalah.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.manager.KalahManager;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;



public class KalahController {

	private static final Logger logger = LogManager.getLogger(KalahController.class);

	
	private KalahManager kalahManager;

	private KalahBoard board;


	public void prepareBoard(String playerName1, String playerName2) {
		board = kalahManager.prepareBoard(playerName1, playerName2, 6);
//		model.addAttribute("board", board);
	}
	
	
//	public void prepareBoard(Model model) {
//		model.addAttribute("start", "hello");
//	}

	public void move(Player own, Player opponent, int selectedID) {
		Player nextPlayer = opponent;

		boolean finished = false;
		Player winner = null;

		try {
			Pit last = kalahManager.sowStones(board, own, opponent, selectedID);

			// check if last stone is in empty pit or in the kalah to find the
			// next move
			if (own.hasPit(last) && last.getCountOfStones() == 1) {
				kalahManager.putStonestoKalah(board, own, opponent, last.getId());
			} else if (own.isKalah(last)) {
				nextPlayer = own;
			}

			// check if one side is out of stones
			int stoneCountOwn = own.countStonesOnPits();
			if (stoneCountOwn == 0) {
				finished = true; // game over
				opponent.putAllStonesToKalah();
			} else {
				int stoneCountOpponent = opponent.countStonesOnPits();
				if (stoneCountOwn == 0) {
					finished = true; // game over
					own.putAllStonesToKalah();
				}
			}

			// if game is over, find the winner
			if (finished) {
				int stoneCountKalahOwn = own.getKalah().getCountOfStones();

				int stoneCountKalahOpponent = opponent.getKalah().getCountOfStones();

				winner = stoneCountKalahOwn > stoneCountKalahOpponent ? own : opponent;
			}

			board.setNextPlayer(nextPlayer);
			board.setFinished(finished);
			board.setWinner(winner);

		} catch (WrongSelectionException e) {
			logger.error(e);
		} catch (NoStoneFoundException e) {
			logger.error(e);
		}

	}

}
