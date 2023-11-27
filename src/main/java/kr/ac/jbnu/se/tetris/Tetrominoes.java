package main.java.kr.ac.jbnu.se.tetris;

public enum Tetrominoes {
	NoShape { Shape getShape() { return new Shape_No(); } },
	OneBlockShape { Shape getShape() { return new Shape_OneBlock(); } },
	ZShape { Shape getShape() { return new Shape_Z(); } },
    SShape { Shape getShape() { return new Shape_S(); } },
    LineShape { Shape getShape() { return new Shape_Line(); } },
    TShape { Shape getShape() { return new Shape_T(); } },
    SquareShape { Shape getShape() { return new Shape_Square(); } },
    LShape { Shape getShape() { return new Shape_L(); } },
    MirroredLShape { Shape getShape() { return new Shape_MirroredL(); } };

    abstract Shape getShape();
}