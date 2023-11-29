package kr.ac.jbnu.se.tetris;

import java.awt.Graphics;

public class GhostMode extends Board {

    public GhostMode(Tetris tetris) {
        super(tetris, "Ghost");
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawBackgroundImage(g);
        drawGridPattern(g);
        drawGhost(g);
        drawPiece(g);
        drawBoard(g);
        updateScorePanel();
    }
}