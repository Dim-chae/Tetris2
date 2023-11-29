package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class SprintMode extends Board{
    private static final int LINE_TO_CLEAR = 40;

    private final Timer sprintTimer;
    private float runningTime = 0;

    public SprintMode(Tetris tetris){
        super(tetris, "Sprint");
        sprintTimer = new Timer(100, e -> sprintTimerAction());
        sprintTimer.start();
    }

    private void sprintTimerAction(){
        if(isPaused) return;
        
        if(numLinesRemoved >= LINE_TO_CLEAR){
            sprintTimer.stop();
            JOptionPane.showMessageDialog(null, "Clear Time - " + (int)runningTime + " 초", "Game Clear!", JOptionPane.INFORMATION_MESSAGE);
            tetris.switchPanel(new MainMenu(tetris));
        } else {
            runningTime += 0.1f;
            updateScorePanel();
        }
    }

    @Override
    protected void updateScorePanel() {
        scoreLabel.setText("남은 줄 : " + (LINE_TO_CLEAR - numLinesRemoved));
        comboLabel.setText("시간 : " + (int)runningTime + "초");
        itemButton.setText(String.valueOf(itemCount));
		nextPieceLabel.setIcon(nextPiece.getImage());
		holdPieceLabel.setIcon(holdPiece.getImage());
		repaint();
    }
}