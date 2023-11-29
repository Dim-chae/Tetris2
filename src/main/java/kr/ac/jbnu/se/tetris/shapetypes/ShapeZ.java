package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeZ extends Shape {
    public ShapeZ() {
        super();
        coords = new int[][]{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } };
        pieceShape = Tetrominoes.Z_SHAPE;
        color = new Color(204, 102, 102);
        image = new ImageIcon("src\\main\\resources\\ZShape.png");
    }
}