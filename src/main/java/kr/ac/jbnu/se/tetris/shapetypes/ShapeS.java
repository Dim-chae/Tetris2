package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeS extends Shape {
    public ShapeS() {
        super();
        coords = new int[][] { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } };
        pieceShape = Tetrominoes.S_SHAPE;
        color = new Color(102, 204, 102);
        image = new ImageIcon("src\\main\\resources\\SShape.png");
    }
}
