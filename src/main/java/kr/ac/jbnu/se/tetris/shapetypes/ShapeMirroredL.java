package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeMirroredL extends Shape {
    public ShapeMirroredL() {
        super();
        coords = new int[][]{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.MIRRORED_L_SHAPE;
        color = new Color(218, 170, 0);
        image = new ImageIcon("src\\main\\resources\\MirroredLShape.png");
    }
}
