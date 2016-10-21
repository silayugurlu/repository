package com.game.kalah.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;

@Service
public class KalahManagerImpl implements KalahManager {


	/**
	 * {@inheritDoc}
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
	 * following pit. returns pit id that the last stone lands.
	 * 
	 * @param board kalah board instance 
	 * 
	 * @param own   player
	 *    
	 * @param pitId id of the selected pit
	 * 
	 * @return id of the pit that last stone lands
	 * 
	 * @throws WrongSelectionException if selected pit belongs to other player
	 * 
	 * @throws NoStoneFoundException   if there is no stone in the selected pit
	 */
	public Pit sowStones(KalahBoard board, Player own,int pitId) throws WrongSelectionException, NoStoneFoundException {
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

		Player opponent = board.getOpponentPlayer(own);
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
	 * picks up the stones from own pit and opponent's pit then put to the own Kalah
	 * 
	 * @param board    kalah board instance
	 * 
	 * @param own      player
	 *            
	 * @param opponent opponent player
	 *            
	 * @param pitId    id of the own selected pit
	 * 
	 * @throws WrongSelectionException if selected pit belongs to other player
	 * 
	 */
	public void putStonestoKalah(KalahBoard board, Player own, int pitId) throws WrongSelectionException {
		
		
		List<Pit> pits = board.getPits();
		Pit pit = pits.get(pitId); // get the selected pit

		if (!own.hasPit(pit)) { // not allowed to select opponent's pit
			throw new WrongSelectionException();
		}

		int indexOnPlayer = own.getPitIndex(pit); // find index of pit on
														// player

		int ownPlayersStone = own.getPit(indexOnPlayer).pickUpStones(); // pick
																			// up
		Player opponent = board.getOpponentPlayer(own);																	// own
																			// stones
		int opponentPlayersStone = opponent.getPit(indexOnPlayer).pickUpStones(); // pick
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
	 * @param opponent  player is used to skip Kalah
	 *            
	 * @param pitId     id of the selected pit
	 *            
	 * @param pitsCount total count of the pits on board
	 *            
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


	/**
	 * {@inheritDoc}
	 */
	public KalahBoard move(KalahBoard board, Player own, int pitId) throws WrongSelectionException, NoStoneFoundException {
		Player opponent = board.getOpponentPlayer(own);
		Player nextPlayer = opponent;

		boolean gameOver = false;
		Player winner = null;

		//picks up all the stones in the selected own pit, and sows the stones
		Pit last = sowStones(board, own, pitId);

		// check if last stone lands in an own empty pit or in the kalah to find the
		// next move
		if (own.hasPit(last) && last.getCountOfStones() == 1) { // last stone lands in own empty pit
			putStonestoKalah(board, own, last.getId()); //captures last stone and all stones in the opposite pit and puts them in his own Kalah
		} else if (own.isKalah(last)) { // last stone lands in own kalah
			nextPlayer = own;  // he gets another turn
		}

		// check if one side is out of stones and game is over
		int stoneCountOwn = own.countStonesOnPits();
		if (stoneCountOwn == 0) {
			gameOver = true; // game over
			opponent.putAllStonesToKalah();
		} else {
			int stoneCountOpponent = opponent.countStonesOnPits();
			if (stoneCountOpponent == 0) {
				gameOver = true; // game over
				own.putAllStonesToKalah();
			}
		}

		// if game is over, find the winner
		if (gameOver) {
			int stoneCountKalahOwn = own.getKalah().getCountOfStones();

			int stoneCountKalahOpponent = opponent.getKalah().getCountOfStones();

			// The winner is the player who has the most stones in his Kalah.
			winner = stoneCountKalahOwn > stoneCountKalahOpponent ? own : opponent;
		}

		board.setNextPlayer(nextPlayer);
		board.setFinished(gameOver);
		board.setWinner(winner);

		return board;
	}

}
