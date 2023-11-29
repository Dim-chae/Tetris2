package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeNo extends Shape {
    public ShapeNo(){
        super();
        coords = new int[][]{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
        pieceShape = Tetrominoes.NO_SHAPE;
        color = new Color(0, 0, 0, 0);
    }
}
