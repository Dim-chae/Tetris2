package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

public class KeySettingMenu extends JPanel {
    public static AtomicInteger moveLeftKey = new AtomicInteger(KeyEvent.VK_LEFT);
    public static AtomicInteger moveRightKey = new AtomicInteger(KeyEvent.VK_RIGHT);
    public static AtomicInteger softDropKey = new AtomicInteger(KeyEvent.VK_DOWN);
    public static AtomicInteger rotateRightKey = new AtomicInteger(KeyEvent.VK_UP);
    public static AtomicInteger hardDropKey = new AtomicInteger(KeyEvent.VK_SPACE);
    public static AtomicInteger holdBlockKey = new AtomicInteger(KeyEvent.VK_C);
    public static AtomicInteger useItemKey = new AtomicInteger(KeyEvent.VK_I);
    private JLabel key1, key2, key3, key4, key5, key6, key7;
    private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    private  JButton previousButton = null;
    public KeySettingMenu(Tetris tetris) {

        JButton saveButton = new JButton("Close");
        saveButton.setPreferredSize(new Dimension(200, 50));
        saveButton.setMinimumSize(new Dimension(200, 50));
        saveButton.setMaximumSize(new Dimension(200, 50));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 설정을 저장하고 설정 메뉴를 닫도록 구현
                tetris.switchPanel(new SettingMenu(tetris)); // 메인 메뉴로 이동
            }
        });

        JLabel keyLabel = new JLabel("조작키 변경", SwingConstants.CENTER);
        keyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 350));
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(7, 2));

        setKeyLabel();
        setKeyButton();
        addKeyButtonListener();

        keyPanel.add(key1);
        keyPanel.add(btn1);
        keyPanel.add(key2);
        keyPanel.add(btn2);
        keyPanel.add(key3);
        keyPanel.add(btn3);
        keyPanel.add(key4);
        keyPanel.add(btn4);
        keyPanel.add(key5);
        keyPanel.add(btn5);
        keyPanel.add(key6);
        keyPanel.add(btn6);
        keyPanel.add(key7);
        keyPanel.add(btn7);

        panel.setLayout(new BorderLayout());
        panel.add(keyLabel, BorderLayout.NORTH);
        panel.add(keyPanel, BorderLayout.CENTER);
        panel.add(setStyledButton(saveButton), BorderLayout.SOUTH);

        add(panel);
    }

    private JButton setStyledButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }

    private void setKeyLabel() {
        key1 = new JLabel("아이템 사용");
        key1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key2 = new JLabel("블록 홀드");
        key2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key3 = new JLabel("소프트 드롭");
        key3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key4 = new JLabel("하드 드롭");
        key4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key5 = new JLabel("왼쪽 이동");
        key5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key6 = new JLabel("오른쪽 이동");
        key6.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        key7 = new JLabel("오른쪽 회전");
        key7.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    }

    private void setKeyButton() {
        btn1 = new JButton(String.valueOf((char) useItemKey.get()));
        btn2 = new JButton(String.valueOf((char) holdBlockKey.get()));
        btn3 = new JButton(String.valueOf((char) softDropKey.get()));
        btn4 = new JButton(String.valueOf((char) hardDropKey.get()));
        btn5 = new JButton(String.valueOf((char) moveLeftKey.get()));
        btn6 = new JButton(String.valueOf((char) moveRightKey.get()));
        btn7 = new JButton(String.valueOf((char) rotateRightKey.get()));
    }
    private void addKeyButtonListener() {
        setButtonListener(btn1, useItemKey);
        setButtonListener(btn2, holdBlockKey);
        setButtonListener(btn3, softDropKey);
        setButtonListener(btn4, hardDropKey);
        setButtonListener(btn5, moveLeftKey);
        setButtonListener(btn6, moveRightKey);
        setButtonListener(btn7, rotateRightKey);
    }

    private void setButtonListener(JButton buttonName, AtomicInteger key) {
        buttonName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousButton != null) {
                    previousButton.setBackground(null);
                    previousButton.setText(previousButton.getText());
                }
                buttonName.setBackground(Color.GRAY);
                setKeyListener(buttonName, key);

                previousButton = buttonName;
            }

        });
    }
    private void setKeyListener(JButton buttonName, AtomicInteger key){
        buttonName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                buttonName.setBackground(null);
                key.set(e.getKeyCode());
                buttonName.setText(String.valueOf((char) key.get()));
                buttonName.removeKeyListener(this);
            }
        });
    }
}
