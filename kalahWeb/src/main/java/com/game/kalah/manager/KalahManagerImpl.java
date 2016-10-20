package com.game.kalah.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.kalah.exception.KalahException;
import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;

@Service
public class KalahManagerImpl implements KalahManager {

//	KalahBoard board;

	/**
	 * prepare Kalah board, initialize players and pits set pits to board and
	 * sow to players with same index
	 * 
	 * @param playerName1
	 *            name of the first player
	 * @param playerName2
	 *            name of the second player
	 * @param countOfStones
	 *            count of stones to put each pit
	 * 
	 * @return KalahBoard initial board contains players, pits and stones
	 */
	public KalahBoard prepareBoard(String playerName1, String playerName2, int countOfStones) {

		/* create pits and put stones */
		List<Pit> pits = new ArrayList<Pit>();

		for (int i = 0; i < 14; i++) { // create pits and players
			Pit pit;

			if (i == 6 || i == 13) { // kalah
				pit = new Kalah(0, i);
			} else {
				pit = new Pit(countOfStones, i);
			}
			pits.add(pit);
		}

		List<Pit> pitsTmp;
		pitsTmp = new ArrayList<Pit>(pits.subList(7, 13));
		Collections.reverse(pitsTmp);
		
		/* create players */
		Player player1 = new Player(1,playerName1, pits.subList(0, 6), pits.get(6));

		Player player2 = new Player(2,playerName2, pitsTmp, pits.get(13)); 
																						

		/* create board */
		KalahBoard board = new KalahBoard(pits, player1, player2);

		return board;

	}

	/**
	 * sows stones to pits , starting from following one. use id to find the
	 * following pit. returns pit id that the last stone sowed.
	 * 
	 * @param own
	 *            player
	 * @param opponent
	 *            player
	 * @param pitId
	 *            id of the selected pit
	 * @return id of the pit that last stone lands
	 * 
	 * @throws WrongSelectionException
	 * @throws NoStoneFoundException
	 */
	public Pit sowStones(KalahBoard board, Player own, Player opponent, int pitId) throws WrongSelectionException, NoStoneFoundException {
		List<Pit> pits = board.getPits();

		Pit pit = pits.get(pitId); // get the selected pit

		if (!own.hasPit(pit)) { // opponent's pit is selected
			throw new WrongSelectionException();
		}

		// pick up the stones in the selected pit
		int countOfStones = pit.pickUpStones();
		if (countOfStones == 0) {
			throw new NoStoneFoundException();
		}

		Pit nextPit = null;
		int pitsCount = pits.size();
		for (int i = 0; i < countOfStones; i++) { // sow the stones

			// find id of the next available pit
			pitId = findNextPit(opponent, pitId, pitsCount);

			nextPit = pits.get(pitId);
			nextPit.addStone();
		}
		return nextPit; // id of pit that last stone lands

	}

	/**
	 * pick up the stones from pit and opponent's pit then put to the Kalah
	 * 
	 * @param own
	 *            player
	 * @param opponent
	 *            opponent player
	 * @param pitId
	 *            id of the own selected pit
	 * @throws WrongSelectionException
	 * 
	 */
	public void putStonestoKalah(KalahBoard board, Player own, Player opponent, int pitId) throws WrongSelectionException {
		List<Pit> pits = board.getPits();
		Pit pit = pits.get(pitId); // get the selected pit

		if (!own.hasPit(pit)) { // not allowed to select opponent's pit
			throw new WrongSelectionException();
		}

		int indexOnOwnPlayer = own.getPitIndex(pit); // find index of pit on
														// player
//		int indexOnOpponentPlayer = 5 - indexOnOwnPlayer; // find index of pit
															// on player

		int ownPlayersStone = own.getPit(indexOnOwnPlayer).pickUpStones(); // pick
																			// up
																			// own
																			// stones
		int opponentPlayersStone = opponent.getPit(indexOnOwnPlayer).pickUpStones(); // pick
																							// up
																							// opponent's
																							// stones

		own.getKalah().addStone(ownPlayersStone + opponentPlayersStone); // put
																			// stones
																			// to
																			// kalah
	}

	/**
	 * finds id of the next pit, skipping opponent's kalah
	 * 
	 * @param opponent
	 *            player is used to skip Kalah
	 * @param pitId
	 *            id of the selected pit
	 * @param pitsCount
	 *            total count of the pits
	 * @return next pit id
	 */
	private int findNextPit(Player opponent, int pitId, int pitsCount) {
		pitId = (++pitId) % pitsCount;
		if (opponent.getKalah().getId() == pitId) { // do not use opponent's
													// Kalah
			pitId = findNextPit(opponent, pitId, pitsCount);
		}
		return pitId;
	}

	public KalahBoard move(KalahBoard board, Player own, Player opponent, int pitId) throws WrongSelectionException, NoStoneFoundException {
		Player nextPlayer = opponent;

		boolean finished = false;
		Player winner = null;

		Pit last = sowStones(board, own, opponent, pitId);

		// check if last stone is in empty pit or in the kalah to find the
		// next move
		if (own.hasPit(last) && last.getCountOfStones() == 1) {
			putStonestoKalah(board, own, opponent, last.getId());
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

		return board;
	}

}
