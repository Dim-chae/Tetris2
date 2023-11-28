package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeTrioLine extends Shape {
    public ShapeTrioLine() {
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 }  };
        pieceShape = Tetrominoes.TRIO_LINE_SHAPE;
        color = Color.PINK;
        image = new ImageIcon("src\\main\\resources\\TrioLineShape.png");
    }
}