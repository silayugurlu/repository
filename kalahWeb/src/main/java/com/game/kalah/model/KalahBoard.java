package com.game.kalah.model;

import java.util.List;
import java.util.Random;

public class KalahBoard {

	List<Pit> pits;

	Player player1;

	Player player2;

	Player nextPlayer;

	boolean gameOver;

	Player winner;

	public KalahBoard(List<Pit> pits, Player player1, Player player2) {
		super();
		this.pits = pits;
		this.player1 = player1;
		this.player2 = player2;

		Random rand = new Random();
		if (rand.nextInt() % 2 == 0) {
			nextPlayer = player2;
		} else {
			nextPlayer = player1;
		}
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	/**
	 * @return the nextPlayer
	 */
	public Player getNextPlayer() {
		return nextPlayer;
	}

	/**
	 * @param nextPlayer
	 *            the nextPlayer to set
	 */
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	/**
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * @param winner
	 *            the winner to set
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}

	/**
	 * 
	 * @return gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * 
	 * @param gameOver
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Player getOpponentPlayer(Player own) {
		if (getPlayer1().getId() == own.getId()) {
			return getPlayer2();
		} else {
			return getPlayer1();
		}
	}

}
