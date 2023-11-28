package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeScythe extends Shape {
    public ShapeScythe() {
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.SCYTHE_SHAPE;
        color = Color.BLUE;
        image = new ImageIcon("src\\main\\resources\\ScytheShape.png");
    }
}