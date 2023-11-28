package kr.ac.jbnu.se.tetris;

import java.awt.Graphics;

public class GhostMode extends Board {

    public GhostMode(Tetris tetris) {
        super(tetris, "Ghost");
    }

    @Override
    protected void drawSquare(Graphics g, int x, int y, Shape shape) {
    }
}