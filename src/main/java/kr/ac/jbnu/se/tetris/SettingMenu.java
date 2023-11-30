package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class SettingMenu extends JPanel {
    private final Tetris tetris;
    private final JButton saveButton = new JButton("Save");
    private final JSlider volumeSlider = new JSlider(SwingConstants.HORIZONTAL);
    private final JPanel mainPanel = new JPanel();

    public SettingMenu(Tetris tetris) {
        this.tetris = tetris;
        addVolumeSlider();
        addSaveButton();
        addLogoutButton();

        mainPanel.setLayout(new GridLayout(4, 1));
        add(mainPanel);
    }

    private void addVolumeSlider(){
        JLabel volumeLabel = new JLabel("볼륨", SwingConstants.CENTER);
        volumeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        volumeSlider.setValue(tetris.getBgmVolume()); // 슬라이더의 기본값 설정
        volumeSlider.setMajorTickSpacing(10); // 주요 눈금 간격 설정
        volumeSlider.setPaintTicks(true); // 눈금 표시
        volumeSlider.setPaintLabels(true); // 눈금 라벨 표시
        mainPanel.add(volumeLabel);
        mainPanel.add(volumeSlider);
    }

    private void addSaveButton(){
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.addActionListener(e -> setVolume());
        mainPanel.add(setStyledButton(saveButton));
    }

    private void setVolume(){
        int volume = volumeSlider.getValue(); // 슬라이더의 값 가져오기 (0에서 100 사이)
        tetris.setBgmVolume(volume); // 볼륨 조절
        tetris.switchPanel(new MainMenu(tetris)); // 메인 메뉴로 이동
    }

    private void addLogoutButton(){
        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(e -> tetris.switchPanel(new LoginPanel(tetris)));
        mainPanel.add(setStyledButton(logoutButton));
    }

    private JButton setStyledButton(JButton button){
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
