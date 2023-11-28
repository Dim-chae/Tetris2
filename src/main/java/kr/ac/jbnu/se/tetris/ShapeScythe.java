package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeScythe extends Shape {
    public ShapeScythe() {
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.SHAPE_SCYTHE;
        color = Color.BLUE;
        image = new ImageIcon("src\\main\\resources\\LShape.png");
    }
}