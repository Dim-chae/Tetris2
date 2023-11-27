package main.java.kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_MirroredL extends Shape {
    public Shape_MirroredL() {
        super();
        coords = new int[][]{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.MirroredLShape;
        color = new Color(218, 170, 0);
        image = new ImageIcon("src\\kr\\ac\\jbnu\\se\\tetris\\resources\\MirroredLShape.png");
    }
}
