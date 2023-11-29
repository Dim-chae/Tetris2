package kr.ac.jbnu.se.tetris.shapetypes;

import java.awt.Color;
import javax.swing.ImageIcon;

import kr.ac.jbnu.se.tetris.Shape;
import kr.ac.jbnu.se.tetris.Tetrominoes;

public class ShapeT extends Shape {
    public ShapeT(){
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } };
        pieceShape = Tetrominoes.T_SHAPE;
        color = new Color(204, 204, 102);
        image = new ImageIcon("src\\main\\resources\\TShape.png");
    }
}
