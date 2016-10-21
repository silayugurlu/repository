package com.game.kalah.manager;

import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Player;

public interface KalahManager {

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
	KalahBoard prepareBoard(String playerName1, String playerName2, int countOfStones);

	
	/**
	 * Makes move of player that gets as a parameter.
	 * 
	 * picks up all the stones in the selected own pit, and sows the stones on to
	 * the right, one in each of the following pits, including own Kalah. No stones are put in the	
	 * opponent's Kalah. 
	 * 
	 * If the players last stone lands in his own Kalah, he gets another turn. 
	 * When the last stone lands in an own empty pit, the player captures this stone and all stones in
     * the opposite pit (the other players' pit) and puts them in his own Kalah.
     * 
     * Checks one of the sides run out of stones and the game is over. The player who still has stones 
     * in his pits, puts them in his Kalah. The winner of the game is the player who has the most stones in his Kalah.
	 * 
	 * @param board   kalah board instance
	 * 
	 * @param player  own player
	 * 
	 * @param pitId   id of the selected pit
	 * 
	 * @return board  updated kalah board
	 * 
	 * @throws WrongSelectionException if selected pit belongs to other player
	 * @throws NoStoneFoundException   if there is no stone in the selected pit 
	 * 
	 */
	KalahBoard move(KalahBoard board,Player player, int pitId) throws WrongSelectionException, NoStoneFoundException;

}
