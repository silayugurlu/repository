/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.cube;

import com.game.cube.model.Piece;
import com.game.cube.model.ResultPiece;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author silay.ugurlu
 */
public class CubeBuilder {

    CubeHelper helper = new CubeHelper();

    /**
     * Gets pieces in puzzle, builds first possible cube and prints it to the
     * file named {color}_base.text
     * 
     * @param pieces
     * @param color 
     */
    public void baseTask(List<Piece> pieces, String color) {
        List<ResultPiece> cubes = helper.buildCube(pieces, true);

        ResultPiece matchedPiece = cubes.get(0);
        Piece[] result = new Piece[6];
        int counter = 0;
        while (matchedPiece != null) {
            putArray(matchedPiece, result, counter);
            if (matchedPiece.getParent() != null) {
                matchedPiece = matchedPiece.getParent();
            } else {
                matchedPiece = null;
            }
            counter++;
        }
        printFile(convertPieceToString(result), color + "_base");
    }

    /**
     * Gets pieces in puzzle, builds all possible cubes and prints them to the
     * file named {color}_task2.txt
     *
     * @param pieces pieces in puzzle
     * @param color name of the file
     */
    public void additionalTask2(List<Piece> pieces, String color) {

        List<String> lines = new ArrayList<String>();
        List<ResultPiece> cubes = helper.buildCube(pieces, false);

        for (ResultPiece matchedPiece : cubes) {
            Piece[] result = new Piece[6];
            int counter = 0;
            while (matchedPiece != null) {
                putArray(matchedPiece, result, counter);
                if (matchedPiece.getParent() != null) {
                    matchedPiece = matchedPiece.getParent();
                } else {
                    matchedPiece = null;
                }
                counter++;
            }

            lines.addAll(convertPieceToString(result));
            lines.add("----------------------------------------");
        }

        printFile(lines, color + "_task2");
    }

    private List<String> convertPieceToString(Piece[] result) {
        List<String> lines = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            String line = result[0].convertLineString(i) + result[1].convertLineString(i)
                    + result[2].convertLineString(i);
            lines.add(line);
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String line = "          " + result[i].convertLineString(j) + "          ";
                lines.add(line);
            }
        }

        return lines;

    }

    /**
     * Write cube to a file
     * 
     * @param lines
     * @param name file name
     */
    private void printFile(List<String> lines, String name) {
        Path file = Paths.get(name + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Puts pieces to array in printed order
     * 
     * @param matchedPiece piece to put in array
     * @param result array contains pieces formatted
     * @param index level in matched results
     */
    private void putArray(Piece matchedPiece, Piece[] result, int index) {
        
        if (index == 0) {
            result[2] = matchedPiece;
        } else if (index == 1) {
            result[0] = matchedPiece;
        } else if (index == 2) {
            result[5] = matchedPiece;
        } else if (index == 3) {
            result[4] = matchedPiece;
        } else if (index == 4) {
            result[3] = matchedPiece;
        } else if (index == 5) {
            result[1] = matchedPiece;
        }
    }

}
