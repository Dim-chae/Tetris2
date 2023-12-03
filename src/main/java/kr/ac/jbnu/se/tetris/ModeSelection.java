package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class ModeSelection extends JPanel {
    private final Tetris tetris;
    private static final String FONT_NAME = "맑은 고딕";

    public ModeSelection(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());

        addTitleLabel();
        addCenterPanel();
        addBackButton();
    }

    private void addTitleLabel(){
        JLabel titleLabel = new JLabel("모드를 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void addCenterPanel(){
        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        String[] modes = {"Easy", "Normal", "Hard", "Very Hard", "God"};
        for (String mode : modes) {
            JButton modeButton = new JButton(mode);
            modeButton.addActionListener(e -> tetris.switchPanel(new Ranking(tetris, mode)));
            centerPanel.add(setStyledButton(modeButton));
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addBackButton(){
        JButton backButton = new JButton("뒤로 가기");
        backButton.addActionListener(e ->  tetris.switchPanel(new MainMenu(tetris)));
        add(setStyledButton(backButton), BorderLayout.SOUTH);
    }

    private JButton setStyledButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
