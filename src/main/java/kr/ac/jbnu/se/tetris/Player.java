package kr.ac.jbnu.se.tetris;

public class Player {
    private String userId;
    private int maxScore;
    private int level;
    private int exp;
    private static final int LEVEL_UP_THRESHOLD = 300;

    public Player(String userId, int maxScore, int level, int exp) {
        this.userId = userId;
        this.maxScore = maxScore;
        this.level = level;
        this.exp = exp;
    }

    public String getUserId() {
        return userId;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int score) {
        if (score > maxScore) {
            maxScore = score;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLevel() {
        int calcLev = exp / LEVEL_UP_THRESHOLD;
        level = Math.min(calcLev + 1, 6);
    }

    public void addExp(int exp) {
        this.exp += exp;
        setLevel();
    }
}
