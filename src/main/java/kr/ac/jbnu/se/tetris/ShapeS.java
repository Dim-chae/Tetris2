package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeS extends Shape {
    public ShapeS() {
        super();
        coords = new int[][] { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } };
        pieceShape = Tetrominoes.SHAPE_S;
        color = new Color(102, 204, 102);
        image = new ImageIcon("src\\main\\resources\\SShape.png");
    }
}