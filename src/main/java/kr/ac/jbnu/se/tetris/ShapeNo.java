package kr.ac.jbnu.se.tetris;

import java.awt.Color;

public class ShapeNo extends Shape{
    public ShapeNo(){
        super();
        coords = new int[][]{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
        pieceShape = Tetrominoes.SHAPE_NO;
        color = new Color(0, 0, 0, 0);
    }
}
