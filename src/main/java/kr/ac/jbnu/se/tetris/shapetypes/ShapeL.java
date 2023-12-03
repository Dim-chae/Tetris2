package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.*;

public class ShapeL extends Shape {
    public ShapeL() {
        super();
        coords = new int[][]{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.L_SHAPE;
        color = new Color(102, 204, 204);
        image = new ImageIcon("src\\main\\resources\\LShape.png");
    }
}