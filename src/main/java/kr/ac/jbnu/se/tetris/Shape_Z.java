package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_Z extends Shape {
    public Shape_Z() {
        super();
        coords = new int[][]{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } };
        pieceShape = Tetrominoes.ZShape;
        color = new Color(204, 102, 102);
        image = new ImageIcon("src\\main\\resources\\ZShape.png");
    }
}