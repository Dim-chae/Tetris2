package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeLine extends Shape {
    public ShapeLine() {
        super();
        coords = new int[][]{ { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } };
        pieceShape = Tetrominoes.LINE_SHAPE;
        color = new Color(102, 102, 204);
        image = new ImageIcon("src\\main\\resources\\LineShape.png");
    }
}
