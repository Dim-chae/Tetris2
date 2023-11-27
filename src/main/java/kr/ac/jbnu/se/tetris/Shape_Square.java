package main.java.kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_Square extends Shape {
    public Shape_Square() {
        super();
        coords = new int[][]{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } };
        pieceShape = Tetrominoes.SquareShape;
        color = new Color(204, 102, 204);
        image = new ImageIcon("src/kr/ac/jbnu/se/tetris/resources/SquareShape.png");
    }
}
