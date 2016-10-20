package com.game.kalah.exception;

public class WrongSelectionException extends KalahException {
	
	@Override
	public String getMessage() {
		return "This is not your turn";
	}

}
