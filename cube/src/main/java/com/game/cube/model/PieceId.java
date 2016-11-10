package com.game.cube.model;

public class PieceId {

	private int id;

	private int rotation;

	private boolean mirror;

	public PieceId(int id) {
		this(id, 0, false);
	}

	public PieceId(int id, int rotation, boolean mirror) {
		super();
		this.id = id;
		this.rotation = rotation;
		this.mirror = mirror;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public boolean isMirror() {
		return mirror;
	}

	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}


	
	

}
