package kr.ac.jbnu.se.tetris;

import java.awt.Color;

public class Shape_No extends Shape{
    public Shape_No(){
        super();
        coords = new int[][]{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
        pieceShape = Tetrominoes.NoShape;
        color = new Color(0, 0, 0, 0);
    }
}
