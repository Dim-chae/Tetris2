package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeL extends Shape {
    public ShapeL() {
        super();
        coords = new int[][]{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.LShape;
        color = new Color(102, 204, 204);
        image = new ImageIcon("src\\main\\resources\\LShape.png");
    }
}