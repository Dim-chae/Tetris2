package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeLine extends Shape {
    public ShapeLine() {
        super();
        coords = new int[][]{ { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } };
        pieceShape = Tetrominoes.SHAPE_LINE;
        color = new Color(102, 102, 204);
        image = new ImageIcon("src\\main\\resources\\LineShape.png");
    }
}
