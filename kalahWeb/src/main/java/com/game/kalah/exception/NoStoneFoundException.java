package com.game.kalah.exception;

public class NoStoneFoundException extends KalahException {

	@Override
	public String getMessage() {
		return "There is no stones in the selected pit";
	}

}
