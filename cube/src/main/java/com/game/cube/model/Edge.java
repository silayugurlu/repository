package com.game.cube.model;

public class Edge {

	private int startVertice;
	
	private int value;
	
	private int endVertice;
	
	private int reverseValue;
	

	public Edge() {
		super();
	}

	public Edge(int startVertice, int value, int endVertice) {
		super();
		this.startVertice = startVertice;
		this.value = value;
		this.endVertice = endVertice;
		if(value>3){
			this.reverseValue = value-3;
		}else{
			this.reverseValue = value+3;
		}
	}

	/**
	 * @return the startVertice
	 */
	public int getStartVertice() {
		return startVertice;
	}

	/**
	 * @param startVertice the startVertice to set
	 */
	public void setStartVertice(int startVertice) {
		this.startVertice = startVertice;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the endVertice
	 */
	public int getEndVertice() {
		return endVertice;
	}

	/**
	 * @param endVertice the endVertice to set
	 */
	public void setEndVertice(int endVertice) {
		this.endVertice = endVertice;
	}

	/**
	 * @return the reverseValue
	 */
	public int getReverseValue() {
		return reverseValue;
	}

	/**
	 * @param reverseValue the reverseValue to set
	 */
	public void setReverseValue(int reverseValue) {
		this.reverseValue = reverseValue;
	}
	
	
	
}