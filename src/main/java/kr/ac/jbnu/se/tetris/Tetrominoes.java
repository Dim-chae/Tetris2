package kr.ac.jbnu.se.tetris;

import kr.ac.jbnu.se.tetris.shapetypes.*;

public enum Tetrominoes {
    NO_SHAPE { public Shape getShape() { return new ShapeNo(); } },
    ONE_BLOCK_SHAPE { public Shape getShape() { return new ShapeOneBlock(); } },
    Z_SHAPE { public Shape getShape() { return new ShapeZ(); } },
    S_SHAPE { public Shape getShape() { return new ShapeS(); } },
    LINE_SHAPE { public Shape getShape() { return new ShapeLine(); } },
    T_SHAPE { public Shape getShape() { return new ShapeT(); } },
    SQUARE_SHAPE { public Shape getShape() { return new ShapeSquare(); } },
    L_SHAPE { public Shape getShape() { return new ShapeL(); } },
    MIRRORED_L_SHAPE { public Shape getShape() { return new ShapeMirroredL(); } },
    TRIO_LINE_SHAPE { public Shape getShape() { return new ShapeTrioLine(); } },
    SCYTHE_SHAPE { public Shape getShape() { return new ShapeScythe(); } };

    public abstract Shape getShape();
}
