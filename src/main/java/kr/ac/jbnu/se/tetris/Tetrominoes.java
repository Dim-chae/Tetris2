package kr.ac.jbnu.se.tetris;

public enum Tetrominoes {
	SHAPE_NO { Shape getShape() { return new ShapeNo(); } },
	SHAPE_ONE_BLOCK { Shape getShape() { return new ShapeOneBlock(); } },
	SHAPE_Z { Shape getShape() { return new ShapeZ(); } },
    SHAPE_S { Shape getShape() { return new ShapeS(); } },
    SHAPE_LINE { Shape getShape() { return new ShapeLine(); } },
    SHAPE_T { Shape getShape() { return new ShapeT(); } },
    SHAPE_SQUARE { Shape getShape() { return new ShapeSquare(); } },
    SHAPE_L { Shape getShape() { return new ShapeL(); } },
    SHAPE_MIRRORED_L { Shape getShape() { return new ShapeMirroredL(); } },
    SHAPE_TRIO_LINE { Shape getShape() { return new ShapeTrioLine(); } },
    SHAPE_SCYTHE { Shape getShape() { return new ShapeScythe(); } };

    abstract Shape getShape();
}