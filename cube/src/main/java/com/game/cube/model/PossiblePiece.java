package com.game.cube.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author silay.ugurlu
 */
public class PossiblePiece extends Piece {

    private List<PossiblePiece> matchedPieces;

    public PossiblePiece(Piece piece) {
        super(piece.getId(), piece.getNodes());
    }

    public PossiblePiece(Piece piece, List<PossiblePiece> matchedPieces) {
        this(piece);
        this.matchedPieces = matchedPieces;
    }

    public List<PossiblePiece> getMatchedPieces() {
        if (matchedPieces == null) {
            matchedPieces = new ArrayList<PossiblePiece>();
        }
        return matchedPieces;
    }

    public void setMatchedPieces(List<PossiblePiece> matchedPieces) {
        this.matchedPieces = matchedPieces;
    }

    public void addMatchedPieces(PossiblePiece matchedPiece) {
        if (this.matchedPieces == null) {
            this.matchedPieces = new ArrayList<PossiblePiece>();
        }
        this.matchedPieces.add(matchedPiece);
    }

}
