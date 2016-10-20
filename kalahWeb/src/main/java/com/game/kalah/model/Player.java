package com.game.kalah.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;

	private List<Pit> pits;

	private Pit kalah;

	private Integer id;

	public Player(Integer id, String name, List<Pit> pits, Pit kalah) {
		this.id = id;
		this.name = name;
		this.pits = pits;
		this.kalah = kalah;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pit getKalah() {
		return kalah;
	}

	public void setKalah(Pit kalah) {
		this.kalah = kalah;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void addPit(Pit pit) {
		pits.add(pit);
	}

	public Pit getPit(int index) {
		return pits.get(index);
	}

	public boolean hasPit(Pit pit) {
		return pits.contains(pit);
	}

	public int getPitIndex(Pit pit) {
		return pits.indexOf(pit);
	}

	public boolean isKalah(Pit pit) {
		return kalah.equals(pit);
	}

	public int countStonesOnPits() {
		int count = 0;
		for (Pit pit : pits) {
			count += pit.getCountOfStones();
		}
		return count;
	}

	public void putAllStonesToKalah() {
		int count = 0;
		for (Pit pit : pits) {
			count += pit.pickUpStones();
		}
		kalah.addStone(count);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
