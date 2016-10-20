package com.game.kalah.model;

public class Pit {

	int countOfStones;
	int id;

	/**
	 * create Pit, gets id and count of stones to put in.
	 * 
	 * @param countOfStones count of the stones to put in the pit
	 * @param id            id of the pit
	 */
	public Pit(int countOfStones, int id) {
		super();
		this.countOfStones = countOfStones;
		this.id = id;
	}

	public void addStone() {
		++countOfStones;

	}

	public void addStone(int count) {
		countOfStones = count + countOfStones;

	}
	/**
	 * clear pit and return count of removed stones
	 * 
	 * @return count of removed stones
	 */
	public int pickUpStones() { // get count of stones and clear pit
		if (getCountOfStones() == 0) {
			return 0;
		}
		int tmpCount = getCountOfStones();
		countOfStones = 0;
		return tmpCount;
	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Pit))
			return false;

		Pit other = (Pit) o;
		return this.id == other.id;
	}

	public int hashCode() {
		return id;
	}

	public int getCountOfStones() {
		return countOfStones;
	}

	public void setCountOfStones(int countOfStones) {
		this.countOfStones = countOfStones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
