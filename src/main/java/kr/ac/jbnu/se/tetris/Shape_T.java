package main.java.kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Shape_T extends Shape{
    public Shape_T(){
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.TShape;
        color = new Color(204, 204, 102);
        image = new ImageIcon("src/kr/ac/jbnu/se/tetris/resources/TShape.png");
    }
}
