package com.game.cube;

import java.util.ArrayList;
import java.util.List;

import com.game.cube.model.Edge;
import com.game.cube.model.PossiblePiece;
import com.game.cube.model.ResultPiece;
import com.game.cube.model.Piece;
import com.game.cube.model.PieceId;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/**
 *
 * @author silay.ugurlu
 */
public class CubeHelper {

    /**
     * Rotates and mirror piece *
     *
     * @param piece to change
     * @param rotation rotation count
     * @param mirror boolean value to mirror or not
     * @return changed Piece
     *
     */
    public Piece changePiece(Piece piece, int rotation, boolean mirror) {
        PieceId newId = new PieceId(piece.getId().getId(), rotation, mirror);
        int[][] nodes = piece.getNodes();
        if (newId.isMirror()) {
            nodes = mirrorPiece(piece.getNodes());
        }
        for (int i = 0; i < newId.getRotation(); i++) {
            nodes = rotatePiece(nodes);
        }

        Piece newPiece = new Piece(newId, nodes);
        return newPiece;
    }

    /**
     * Rotates nodes ones to right
     *
     * @param nodes to rotate
     * @return rotated nodes
     */
    public int[][] rotatePiece(int[][] nodes) {
        int[][] newnodes = new int[5][];

        newnodes[0] = new int[]{nodes[4][0], nodes[3][0], nodes[2][0], nodes[1][0], nodes[0][0]};
        newnodes[1] = new int[]{nodes[4][1], 1, 1, 1, nodes[0][1]};
        newnodes[2] = new int[]{nodes[4][2], 1, 1, 1, nodes[0][2]};
        newnodes[3] = new int[]{nodes[4][3], 1, 1, 1, nodes[0][3]};
        newnodes[4] = new int[]{nodes[4][4], nodes[3][4], nodes[2][4], nodes[1][4], nodes[0][4]};

        return newnodes;
    }

    /**
     * Mirrors node to up
     *
     * @param nodes to mirror
     * @return mirrored nodes
     */
    public int[][] mirrorPiece(int[][] nodes) {
        int[][] newnodes = new int[5][];

        newnodes[0] = nodes[4];
        newnodes[1] = nodes[3];
        newnodes[2] = nodes[2];
        newnodes[3] = nodes[1];
        newnodes[4] = nodes[0];
        return newnodes;
    }

    public boolean checkPiecesSame(Piece piece1, Piece piece2) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (piece1.getNodes()[i][j] != piece2.getNodes()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Builds cube, get pieces in puzzle and returns matchedPiece contains all
     * possible matched pieces
     *
     * @param pieces in puzzle
     * @return root matchedPiece contains all possible matched pieces
     */
    public List<ResultPiece> buildCube(List<Piece> pieces, boolean findFirst) {

        // first put pieces in line order that surround the cube
        PossiblePiece root = putPiecesInLine(pieces);

        // then put pieces to left and right
        List<ResultPiece> result = putPiecesLeftAndRight(pieces, root, findFirst);
        return result;
    }

    /**
     * Finds first 4 pieces putting in line as a circle finds pieces that one of
     * its edges match the bottom edge of the upper piece
     *
     * @param pieces all 6 pieces in puzzle
     * @return matched root piece that contains other possible 3 pieces
     */
    public PossiblePiece putPiecesInLine(List<Piece> pieces) {

        // put first piece as a root
        PossiblePiece root = new PossiblePiece(pieces.get(0));

        //put possible pieces as second piece
        Edge rootEdge = root.getEdges().get(2);
        for (Piece piece : pieces) {
            if (!piece.equals(root)) {
                root.getMatchedPieces().addAll(checkPieceOneSide(rootEdge, piece));
            }
        }

        //put possible pieces as third piece
        for (PossiblePiece pieceL1 : root.getMatchedPieces()) {
            for (Piece piece : pieces) {
                if (!piece.equals(root) && !piece.equals(pieceL1)) {
                    Edge edge = pieceL1.getEdges().get(2);
                    pieceL1.getMatchedPieces().addAll(checkPieceOneSide(edge, piece));
                }
            }

        }

        //put possible pieces as fourth piece that also matches with first piece
        for (PossiblePiece pieceL1 : root.getMatchedPieces()) {
            for (PossiblePiece pieceL2 : pieceL1.getMatchedPieces()) {
                for (Piece piece : pieces) {
                    if (!piece.equals(root) && !piece.equals(pieceL1) && !piece.equals(pieceL2)) {
                        Edge edge = pieceL2.getEdges().get(2);
                        pieceL2.getMatchedPieces().addAll(checkPieceOneSide(edge, piece, true, root.getEdges().get(0)));
                    }
                }
            }
        }
        return root;
    }

    /**
     * Finds pieces on left and right of the cube and adds to the matched piece
     *
     * @param pieces all pieces in puzzle
     * @param root first root matched piece contains all possible matched piece
     * @param findFirst cube and return
     * @return count of possible cubes
     */
    public List<ResultPiece> putPiecesLeftAndRight(List<Piece> pieces, PossiblePiece root, boolean findFirst) {

        List<ResultPiece> result = new ArrayList<ResultPiece>();

        List<PossiblePiece> matchedList1 = new ArrayList<PossiblePiece>();
        for (PossiblePiece pieceL1 : root.getMatchedPieces()) {

            List<PossiblePiece> matchedList2 = new ArrayList<PossiblePiece>();
            for (PossiblePiece pieceL2 : pieceL1.getMatchedPieces()) {
                List<PossiblePiece> matchedList3 = new ArrayList<PossiblePiece>();
                for (PossiblePiece pieceL3 : pieceL2.getMatchedPieces()) {
                    Piece lastPiece1 = null;
                    Piece lastPiece2 = null;
                    for (Piece piece : pieces) {
                        if (!piece.equals(root) && !piece.equals(pieceL1) && !piece.equals(pieceL2) && !piece.equals(pieceL3)) {
                            // two pieces left
                            if (lastPiece1 == null) {
                                lastPiece1 = piece;
                            } else {
                                lastPiece2 = piece;
                                break;
                            }
                        }

                    }
                    if (lastPiece1 != null && lastPiece2 != null) {
                        result
                                .addAll(checkPieceFourSide(lastPiece1, lastPiece2, root, pieceL1, pieceL2, pieceL3));
                        result
                                .addAll(checkPieceFourSide(lastPiece2, lastPiece1, root, pieceL1, pieceL2, pieceL3));

                        //check and put to list if piece matches at least one piece
                        if (!result.isEmpty()) {
                            matchedList3.add(pieceL3);
                            if (findFirst) {
                                break;
                            }
                        }
                    }

                }
                //remove pieces that can not matched any other piece
                pieceL2.getMatchedPieces().clear();
                pieceL2.getMatchedPieces().addAll(matchedList3);

                //check and put to list if piece matches at least one piece
                if (!pieceL2.getMatchedPieces().isEmpty()) {
                    matchedList2.add(pieceL2);
                    if (findFirst) {
                        break;
                    }
                }

            }
            //remove pieces that can not matched any other piece
            pieceL1.getMatchedPieces().clear();
            pieceL1.getMatchedPieces().addAll(matchedList2);

            //check and put to list if piece matches at least one piece
            if (!pieceL1.getMatchedPieces().isEmpty()) {
                matchedList1.add(pieceL1);
                if (findFirst) {
                    break;
                }
            }

        }
        //remove pieces that can not matched any other piece
        root.getMatchedPieces().clear();
        root.getMatchedPieces().addAll(matchedList1);
        return result;
    }

    public List<PossiblePiece> checkPieceOneSide(Edge matchedEdge, Piece piece) {
        return checkPieceOneSide(matchedEdge, piece, false, null);
    }

    /**
     * Checks if any edge of the piece is matched with the given edge by
     * rotating and mirroring the piece
     *
     * @param matchedEdge edge to be matched
     * @param piece is checked if it is mactched
     * @param checkOpposite if the edge of the piece is checked with the reverse
     * value
     * @param oppositeEdge the opposite edge of teh matched edge, is used to
     * check piece matches both bottom and up sides.
     * @return all possible pieces that matches
     */
    public List<PossiblePiece> checkPieceOneSide(Edge matchedEdge, Piece piece, boolean checkOpposite, Edge oppositeEdge) {

        List<PossiblePiece> matchedPieces = new ArrayList<PossiblePiece>();

        List<Edge> edges = piece.getEdges();

        Piece newPiece = piece;
        for (int rotate = 0; rotate < 4; rotate++) {
            if (checkTwoEdgesMatch(matchedEdge, newPiece.getEdge(0), true) && (!checkOpposite
                    || (checkOpposite && checkTwoEdgesMatch(edges.get((rotate + 2) % 4), oppositeEdge, true)))) {
                matchedPieces.add(new PossiblePiece(newPiece));
            }
            if (piece.isSymmetric() && rotate == 1) {
                break;
            }
            if (piece.isAllEdgesSame()) {
                break;
            }
            newPiece = changePiece(newPiece, 1, false);

        }
        if (!piece.isMirrorSame()) {

            newPiece = changePiece(piece, 0, true);
            for (int rotate = 0; rotate < 4; rotate++) {
                if (checkTwoEdgesMatch(matchedEdge, newPiece.getEdge(0), true) && (!checkOpposite
                        || (checkOpposite && checkTwoEdgesMatch(edges.get((rotate + 2) % 4), oppositeEdge, true)))) {

                    matchedPieces.add(new PossiblePiece(newPiece));
                }
                if (piece.isSymmetric() && rotate == 1) {
                    break;
                }
                if (piece.isAllEdgesSame()) {
                    break;
                }
                newPiece = changePiece(piece, rotate, false);
            }
        }
        return matchedPieces;
    }

    /**
     * Checks if edges of the last two pieces match edges on the left and right
     * of the incomplete cube
     *
     * @param lastPiece1 one of the last two pieces
     * @param lastPiece2 one of the last two pieces
     * @param pieces all in incomplete cube
     * @return all possible pieces that matched
     */
    public List<ResultPiece> checkPieceFourSide(Piece lastPiece1, Piece lastPiece2, PossiblePiece... pieces) {

        List<ResultPiece> result = new ArrayList<ResultPiece>();
        Piece newPiece1 = lastPiece1;

        int rotationCount1 = lastPiece1.isAllEdgesSame() ? 2 : (lastPiece1.isSymmetric() ? 4 : 8);
        int rotationCount2 = lastPiece2.isAllEdgesSame() ? 2 : (lastPiece2.isSymmetric() ? 4 : 8);

        for (int i = 1; i <= rotationCount1; i++) {
            if (checkFourEdgesMatchLeft(newPiece1, pieces[0].getEdge(3), pieces[1].getEdge(3), pieces[2].getEdge(3),
                    pieces[3].getEdge(3))) {
                Piece newPiece2 = lastPiece2;
                for (int j = 1; j <= rotationCount2; j++) {
                    if (checkFourEdgesMatchRight(newPiece2, pieces[0].getEdge(1), pieces[1].getEdge(1),
                            pieces[2].getEdge(1), pieces[3].getEdge(1))) {

                        result.add(prepareResult(newPiece1, newPiece2, pieces));
                        break;
                    } else if (j == rotationCount2 / 2) {
                        if (lastPiece2.isMirrorSame()) {
                            break;
                        }
                        newPiece2 = changePiece(lastPiece2, 0, true);
                    } else {
                        newPiece2 = changePiece(newPiece2, j % (rotationCount2 / 2), false);
                    }
                }
                if (!result.isEmpty()) {
                    break;
                }
            } else if (i == (rotationCount1 / 2)) {
                if (lastPiece1.isMirrorSame()) {
                    break;
                }
                newPiece1 = changePiece(lastPiece1, 0, true);
            } else {
                newPiece1 = changePiece(newPiece1, i % (rotationCount1 / 2), false);
            }
        }
        return result;
    }

    public ResultPiece prepareResult(Piece lastPiece1, Piece lastPiece2, PossiblePiece... pieces) {
        ResultPiece parent = new ResultPiece(pieces[0]);
        for (int i = 1; i < 4; i++) {
            Piece piece = pieces[i];
            ResultPiece resultPiece = new ResultPiece(piece);
            resultPiece.setParent(parent);
            parent = resultPiece;
        }
        ResultPiece resultPiece1 = new ResultPiece(lastPiece1);
        resultPiece1.setParent(parent);

        ResultPiece resultPiece2 = new ResultPiece(lastPiece2);
        resultPiece2.setParent(resultPiece1);

        return resultPiece2;
    }

    public boolean checkTwoVerticesMatch(Edge edge1, Edge edge2, boolean reverse) {
        if (reverse) {
            return (edge1.getStartVertice() + edge2.getEndVertice() <= 1)
                    && (edge1.getEndVertice() + edge2.getStartVertice() <= 1);
        } else {
            return (edge1.getStartVertice() + edge2.getStartVertice() <= 1)
                    && (edge1.getEndVertice() + edge2.getEndVertice() <= 1);
        }
    }

    public boolean checkTwoEdgesMatch(Edge edge1, Edge edge2, boolean reverse) {
        return (edge1.getValue() + edge2.getReverseValue() == 7) && checkTwoVerticesMatch(edge1, edge2, reverse);
    }

    /**
     * Checks four edges of the piece if they can be matched with the left side
     * of the matched pieces.
     *
     * @param piece to check if matches
     * @param edges on left side of the incomplete cube
     * @return the result, it is true if matched, false if not
     */
    public boolean checkFourEdgesMatchLeft(Piece piece, Edge... edges) {
        Edge possibleEdge = piece.getEdge(0);
        int vertice = possibleEdge.getStartVertice() + edges[3].getEndVertice() + edges[2].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[3].getReverseValue() == 7)) {

            return false;
        }

        possibleEdge = piece.getEdge(1);
        vertice = possibleEdge.getStartVertice() + edges[0].getEndVertice() + edges[3].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[0].getReverseValue() == 7)) {

            return false;
        }

        possibleEdge = piece.getEdge(2);
        vertice = possibleEdge.getStartVertice() + edges[1].getEndVertice() + edges[0].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[1].getReverseValue() == 7)) {

            return false;
        }

        possibleEdge = piece.getEdge(3);
        vertice = possibleEdge.getStartVertice() + edges[2].getEndVertice() + edges[1].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[2].getReverseValue() == 7)) {

            return false;
        }

        return true;
    }

    /**
     * Checks four edges of the piece if they can be matched with the right side
     * of the matched pieces.
     *
     * @param piece to check if matches
     * @param edges on right side of the incomplete cube
     * @return the result, it is true if matched, false if not
     */
    public boolean checkFourEdgesMatchRight(Piece piece, Edge... edges) {
        Edge possibleEdge = piece.getEdge(0);
        int vertice = possibleEdge.getStartVertice() + edges[3].getEndVertice() + edges[0].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[3].getReverseValue() == 7)) {
            return false;
        }

        possibleEdge = piece.getEdge(1);
        vertice = possibleEdge.getStartVertice() + edges[2].getEndVertice() + edges[3].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[2].getReverseValue() == 7)) {

            return false;
        }

        possibleEdge = piece.getEdge(2);
        vertice = possibleEdge.getStartVertice() + edges[1].getEndVertice() + edges[2].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[1].getReverseValue() == 7)) {

            return false;
        }

        possibleEdge = piece.getEdge(3);
        vertice = possibleEdge.getStartVertice() + edges[0].getEndVertice() + edges[1].getStartVertice();

        if (!(vertice == 1 && possibleEdge.getValue() + edges[0].getReverseValue() == 7)) {

            return false;
        }

        return true;
    }

}
