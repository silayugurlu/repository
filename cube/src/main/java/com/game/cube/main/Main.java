package com.game.cube.main;

import com.game.cube.CubeBuilder;

import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;
import java.util.Arrays;

public class Main {

    public static Piece[] prepareData() {
        int[][] nodes1 = {{0, 0, 1, 0, 0}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0},
        {0, 0, 1, 0, 0}};

        Piece piece1 = new Piece(new PieceId(1), nodes1);

        int[][] nodes2 = {{1, 0, 1, 0, 1}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1},
        {1, 0, 1, 0, 1}};

        Piece piece2 = new Piece(new PieceId(2), nodes2);

        int[][] nodes3 = {{0, 0, 1, 0, 0}, {0, 1, 1, 1, 1}, {1, 1, 1, 1, 0}, {0, 1, 1, 1, 1},
        {0, 0, 1, 0, 0}};

        Piece piece3 = new Piece(new PieceId(3), nodes3);

        int[][] nodes4 = {{0, 1, 0, 1, 0}, {1, 1, 1, 1, 0}, {0, 1, 1, 1, 1}, {1, 1, 1, 1, 0},
        {1, 1, 0, 1, 0}};

        Piece piece4 = new Piece(new PieceId(4), nodes4);

        int[][] nodes5 = {{0, 1, 0, 1, 0}, {1, 1, 1, 1, 1}, {0, 1, 1, 1, 0}, {1, 1, 1, 1, 1},
        {1, 0, 1, 0, 0}};

        Piece piece5 = new Piece(new PieceId(5), nodes5);

        int[][] nodes6 = {{0, 1, 0, 1, 0}, {0, 1, 1, 1, 1}, {1, 1, 1, 1, 0}, {0, 1, 1, 1, 1},
        {1, 1, 0, 1, 1}};

        Piece piece6 = new Piece(new PieceId(6), nodes6);

        Piece[] pieces = {piece1, piece2, piece3, piece4, piece5, piece6};
        return pieces;
    }

    public static void main(String[] args) {

        CubeBuilder builder = new CubeBuilder();
        Piece[] pieces = prepareData();
        builder.baseTask(Arrays.asList(pieces), "red");

        builder.additionalTask2(Arrays.asList(pieces), "red");
    }

}
