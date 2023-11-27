package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class TimeAttackMode extends Board{
    private static final float TIME_LIMIT = 4.0f;
    private float remainingTime = TIME_LIMIT;
    private final Timer taModeTimer;

    public TimeAttackMode(Tetris tetris) {
        super(tetris, "Time Attack");
        taModeTimer = new Timer(100, e -> checkTimeOver());
        taModeTimer.start();
    }

    public void checkTimeOver() {
        if(isPaused) return;
        
        if(remainingTime <= 0) {
            gameOver();
            taModeTimer.stop();
            JOptionPane.showMessageDialog(null, numLinesRemoved + "줄 제거!", "Time Over!", JOptionPane.INFORMATION_MESSAGE, null);
            
        }
        else {
            remainingTime -= 0.1f;
            updateScorePanel();
        }
    }

    @Override
    protected void updateScorePanel() {
        scoreLabel.setText("제거한 줄 : " + numLinesRemoved);
        comboLabel.setText("남은 시간 : " + (int)remainingTime + "초");
        itemButton.setText(String.valueOf(itemCount));
		nextPieceLabel.setIcon(nextPiece.getImage());
		holdPieceLabel.setIcon(holdPiece.getImage());
		repaint();
    }
}
