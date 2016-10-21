package com.game.kalah.exception;

public class NoStoneFoundException extends KalahException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "There is no stones in the selected pit";
	}

}
