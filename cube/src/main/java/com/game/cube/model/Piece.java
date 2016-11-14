package com.game.cube.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author silay.ugurlu
 */
public class Piece {

    private PieceId id;
    private int[][] nodes;
    private List<Edge> edges;
    
    private boolean allEdgesSame;
    private boolean symmetric;
    private boolean mirrorSame;

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

        this.symmetric = edges.get(0).equals(edges.get(2)) && edges.get(1).equals(edges.get(3));
        this.allEdgesSame = edges.get(0).equals(edges.get(2)) && edges.get(1).equals(edges.get(3))
                && edges.get(0).equals(edges.get(1));

        int verticeSum = nodes[0][0] + nodes[0][4] + nodes[4][4] + nodes[4][0];
        this.mirrorSame = (edges.get(0).getValue() == 2 || edges.get(0).getValue() == 5)
                && (edges.get(1).getValue() == 2 || edges.get(1).getValue() == 5)
                && (edges.get(2).getValue() == 2 || edges.get(2).getValue() == 5)
                && (edges.get(3).getValue() == 2 || edges.get(3).getValue() == 5)
                && (verticeSum == 0 || verticeSum == 4);
    }

    public PieceId getId() {
        return id;
    }

    public void setId(PieceId id) {
        this.id = id;
    }

    public int[][] getNodes() {
        return this.nodes;
    }

    public void setNodes(int[][] nodes) {
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public Edge getEdge(int index) {
        return this.edges.get(index);
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


    /**
     * @return the allEdgesSame
     */
    public boolean isAllEdgesSame() {
        return allEdgesSame;
    }

    /**
     * @return the symmetric
     */
    public boolean isSymmetric() {
        return symmetric;
    }

    /**
     * @return the mirrorSame
     */
    public boolean isMirrorSame() {
        return mirrorSame;
    }

}
