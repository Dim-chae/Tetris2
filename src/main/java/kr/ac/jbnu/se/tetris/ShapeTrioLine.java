package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ShapeTrioLine extends Shape {
    public ShapeTrioLine() {
        super();
        coords = new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 0 }, { 1, 0 }  };
        pieceShape = Tetrominoes.TrioLineShape;
        color = Color.PINK;
        image = new ImageIcon("src\\main\\resources\\TrioLine.png");
    }
}

//색깔 및 이미지 수정 필요
