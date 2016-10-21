package com.game.kalah.exception;

public class KalahSelectedException extends KalahException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "You can not select Kalah";
	}
}
