package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_Line extends Shape {
    public Shape_Line() {
        super();
        coords = new int[][]{ { -2, 0 }, { -1, 0 }, { 0, 0 }, { 1, 0 } };
        pieceShape = Tetrominoes.LineShape;
        color = new Color(102, 102, 204);
        image = new ImageIcon("src/kr/ac/jbnu/se/tetris/resources/LineShape.png");
    }
}
