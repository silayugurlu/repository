package com.game.kalah.manager;

import com.game.kalah.exception.NoStoneFoundException;
import com.game.kalah.exception.WrongSelectionException;
import com.game.kalah.model.KalahBoard;
import com.game.kalah.model.Pit;
import com.game.kalah.model.Player;

public interface KalahManager {

	KalahBoard prepareBoard(String playerName1, String playerName2, int countOfStones);

	Pit sowStones(KalahBoard board, Player own, Player oppponent, int pitId) throws WrongSelectionException, NoStoneFoundException;

	void putStonestoKalah(KalahBoard board, Player own, Player opponent, int pitId) throws WrongSelectionException;
	
	KalahBoard move(KalahBoard board,Player own, Player opponent, int pitId) throws WrongSelectionException, NoStoneFoundException;

}
