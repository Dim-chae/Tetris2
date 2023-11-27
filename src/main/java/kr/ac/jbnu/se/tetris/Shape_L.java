package main.java.kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_L extends Shape {
    public Shape_L() {
        super();
        coords = new int[][]{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.LShape;
        color = new Color(102, 204, 204);
        image = new ImageIcon("src\\kr\\ac\\jbnu\\se\\tetris\\resources\\LShape.png");
    }
}