/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.cube.model;

/**
 *
 * @author silay.ugurlu
 */
public class ResultPiece extends Piece{
    
    ResultPiece parent;
    
     public ResultPiece(Piece piece) {
        super(piece.getId(), piece.getNodes());
    }

    public ResultPiece getParent() {
        return parent;
    }

    public void setParent(ResultPiece parent) {
        this.parent = parent;
    }
    
}
