package kr.ac.jbnu.se.tetris;

public enum Tetrominoes {
	NoShape { Shape getShape() { return new ShapeNo(); } },
	OneBlockShape { Shape getShape() { return new ShapeOneBlock(); } },
	ZShape { Shape getShape() { return new ShapeZ(); } },
    SShape { Shape getShape() { return new ShapeS(); } },
    LineShape { Shape getShape() { return new ShapeLine(); } },
    TShape { Shape getShape() { return new ShapeT(); } },
    SquareShape { Shape getShape() { return new ShapeSquare(); } },
    LShape { Shape getShape() { return new ShapeL(); } },
    MirroredLShape { Shape getShape() { return new ShapeMirroredL(); } },
    TrioLineShape { Shape getShape() { return new ShapeTrioLine(); } },
    ScytheShape { Shape getShape() { return new ShapeScythe(); } };

    abstract Shape getShape();
}