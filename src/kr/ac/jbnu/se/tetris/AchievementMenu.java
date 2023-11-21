package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementMenu extends JPanel {
    private final Tetris tetris;
    private final transient List<AchievementItem> achievementList = new ArrayList<>();
    private final JPanel centerPanel = new JPanel(new GridLayout(10, 1, 10, 10));
    private static final String FONT_NAME = "맑은 고딕";

    public AchievementMenu(Tetris tetris) {
        this.tetris = tetris;
        checkAchievement();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        addTitleLabel();
        addCenterPanel();
        addScrollPane();
        addBackButton();
    }

    private void addTitleLabel(){
        JLabel titleLabel = new JLabel("업적", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void addCenterPanel(){
        addAchievementListToScrollPane();
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addBackButton(){
        JButton backButton = new JButton("뒤로 가기");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> backButtonActionPerformed());
    }

    private void backButtonActionPerformed() {
        tetris.switchPanel(new MainMenu(tetris));
    }

    private void addAchievementListToScrollPane(){
        for (AchievementItem item : achievementList) {
            JLabel itemLabel = new JLabel(item.name() + " : " + item.description());
            itemLabel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
            itemLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
            itemLabel.setForeground(new Color(70, 130, 180));
            centerPanel.add(itemLabel);
        }
    }

    private void addScrollPane(){
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    // 업적 목록 생성
    private void checkAchievement() {
        int userMaxscore = tetris.getUserMaxScore();
        if (userMaxscore >= 5000)
            achievementList.add(new AchievementItem("초보자", "최고 점수 5000점 달성"));
        if (userMaxscore >= 10000)
            achievementList.add(new AchievementItem("중수", "최고 점수 10000점 달성"));
        if (userMaxscore >= 15000)
            achievementList.add(new AchievementItem("고수", "최고 점수 50000점 달성"));
        if (userMaxscore >= 20000)
            achievementList.add(new AchievementItem("마스터", "최고 점수 100000점 달성"));
        if (userMaxscore >= 30000)
            achievementList.add(new AchievementItem("전설", "최고 점수 250000점 달성"));
    }

    // 업적 아이템 클래스
        private record AchievementItem(String name, String description) {
    }
}
