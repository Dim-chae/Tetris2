package kr.ac.jbnu.se.tetris;

import java.awt.Graphics;

public class GhostMode extends Board {
    private boolean isGameOver = false;

    public GhostMode(Tetris tetris) {
        super(tetris, "Ghost");
    }

    @Override
    protected void drawSquare(Graphics g, int x, int y, Shape shape) {
        // 게임 오버 시 블록을 그림
		if(isGameOver){
            super.drawSquare(g, x, y, shape);
        }
        // 게임 오버가 아닐 시 원 블록을 제외한 블록을 그리지 않음
        else{
            if(shape.getShape() != Tetrominoes.ONE_BLOCK_SHAPE) return;
            super.drawSquare(g, x, y, shape);
        }
	}

    @Override
    protected void gameOver(){
        isGameOver = true;
        super.gameOver();
    }
}