package com.game.kalah.exception;

public class WrongSelectionException extends KalahException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "This is not your turn";
	}

}
