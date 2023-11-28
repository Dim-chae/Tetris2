package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeOneBlock extends Shape {
    public ShapeOneBlock(){
        super();
        coords = new int[][]{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
        pieceShape = Tetrominoes.ONE_BLOCK_SHAPE;
        color = new Color(128, 128, 128);
    }    
}
