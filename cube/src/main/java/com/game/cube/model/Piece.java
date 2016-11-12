package com.game.cube.model;

import java.util.ArrayList;
import java.util.List;

public class Piece {

	private PieceId id;
	private int[][] nodes;
	private List<Edge> edges;

	public Piece() {
		super();
	}

	public Piece(PieceId id) {
		super();
		this.id = id;
	}

	public Piece(PieceId id, int[][] nodes) {
		super();
		this.id = id;
		this.nodes = nodes;

		edges = new ArrayList<Edge>();
		edges.add(new Edge(nodes[0][0], nodes[0][1] + nodes[0][2] * 2 + nodes[0][3] * 4, nodes[0][4])); // 0--1
		edges.add(new Edge(nodes[0][4], nodes[1][4] + nodes[2][4] * 2 + nodes[3][4] * 4, nodes[4][4])); // 1--2
		edges.add(new Edge(nodes[4][4], nodes[4][3] + nodes[4][2] * 2 + nodes[4][1] * 4, nodes[4][0])); // 2--3
		edges.add(new Edge(nodes[4][0], nodes[3][0] + nodes[2][0] * 2 + nodes[1][0] * 4, nodes[0][0])); // 3--0
	}

	public PieceId getId() {
		return id;
	}

	public void setId(PieceId id) {
		this.id = id;
	}

	public int[][] getNodes() {
		if (id.isMirror()) {
			this.nodes = mirrorNodes(this.nodes);
		}
		for (int i = 0; i < id.getRotation(); i++) {
			this.nodes = rotateNodes(this.nodes);
		}

		return this.nodes;
	}

	public void setNodes(int[][] nodes) {
		this.nodes = nodes;
	}

	public String convertLineToString(int lineIndex) {
		String line = "";
		for (int j = 0; j < 5; j++) {
			line += (nodes[lineIndex][j] == 1 ? "[]" : "  ");
		}
		return line;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Piece param = (Piece) obj;
		return this.id.equals(param.getId());
	}

	public List<String> toStringList() {
		List<String> list = new ArrayList<String>();
		String node = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

				node += (nodes[i][j] == 1 ? "[]" : "  ");
			}

			list.add(node);
			node = "";
		}
		return list;
	}

	public String convertLineString(int index) {
		String node = "";
		for (int j = 0; j < 5; j++) {

			node += (nodes[index][j] == 1 ? "[]" : "  ");
		}
		return node;
	}

	public List<Edge> mirrorEdges() {
		List<Edge> edgesMirror = new ArrayList<Edge>();
		edgesMirror.add(
				new Edge(edges.get(0).getEndVertice(), edges.get(0).getReverseValue(), edges.get(0).getStartVertice())); // 0--1
		edgesMirror.add(
				new Edge(edges.get(3).getEndVertice(), edges.get(3).getReverseValue(), edges.get(3).getStartVertice())); // 1--2
		edgesMirror.add(
				new Edge(edges.get(2).getEndVertice(), edges.get(2).getReverseValue(), edges.get(2).getStartVertice())); // 2--3
		edgesMirror.add(
				new Edge(edges.get(1).getEndVertice(), edges.get(1).getReverseValue(), edges.get(1).getStartVertice())); // 3--0
		return edgesMirror;
	}

	private int[][] rotateNodes(int[][] m) {
		int[][] nodes = new int[5][];

		nodes[0] = new int[] { m[4][0], m[3][0], m[2][0], m[1][0], m[0][0] };
		nodes[1] = new int[] { m[4][1], 1, 1, 1, m[0][1] };
		nodes[2] = new int[] { m[4][2], 1, 1, 1, m[0][2] };
		nodes[3] = new int[] { m[4][3], 1, 1, 1, m[0][3] };
		nodes[4] = new int[] { m[4][4], m[3][4], m[2][4], m[1][4], m[0][4] };

		return nodes;
	}

	private int[][] mirrorNodes(int[][] m) {
		int[][] nodes = new int[5][];

		nodes[0] = m[4];
		nodes[1] = m[3];
		nodes[2] = m[2];
		nodes[3] = m[1];
		nodes[4] = m[0];
		return nodes;
	}

}