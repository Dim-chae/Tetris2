package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeMirroredL extends Shape {
    public ShapeMirroredL() {
        super();
        coords = new int[][]{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.MirroredLShape;
        color = new Color(218, 170, 0);
        image = new ImageIcon("src\\main\\resources\\MirroredLShape.png");
    }
}
