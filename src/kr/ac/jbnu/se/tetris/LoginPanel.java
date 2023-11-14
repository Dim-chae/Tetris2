package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginPanel extends JPanel {
    private final Tetris tetris;
    private final JLabel loginId = new JLabel("ID : ");
    private final JLabel loginPw = new JLabel("Password : ");
    private final JButton loginButton = new JButton("Login");
    private final JButton signUpButton = new JButton("Sign Up");
    private final JTextField loginIdField = new JTextField(12);
    private final JPasswordField loginPwField = new JPasswordField(12);

    public LoginPanel(Tetris tetris){
        this.tetris = tetris;
        initUI();
    }

    private void initUI(){
        setPreferredSize(new Dimension(300, 300));
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("로그인"));
        add(loginId);
        add(loginIdField);
        add(loginPw);
        add(loginPwField);
        add(setStyledButton(loginButton));
        add(setStyledButton(signUpButton));

        loginButton.addActionListener(e -> login());
        signUpButton.addActionListener(e -> showSignUpPanel());
    }

     // 로그인 로직
    private void login() {
        String id = loginIdField.getText();
        char[] pw = loginPwField.getPassword();
        if (id.isEmpty() || pw.length == 0) {
            JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean loginSuccess = checkLoginOnServer(id, new String(pw));
            if (loginSuccess) {
                // 로그인 성공
                tetris.setUserId(id);
                tetris.switchPanel(new MainMenu(tetris));
            } else {
                // 로그인 실패
                JOptionPane.showMessageDialog(null, "로그인 실패 - 아이디 또는 비밀번호가 일치하지 않음", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 회원가입 패널 표시
    private void showSignUpPanel() {
        clearFields();
        tetris.switchPanel(new SignUpPanel(tetris));
    }

    // id, pw, confirmPw 필드 초기화
    private void clearFields() {
        loginIdField.setText("");
        loginPwField.setText("");
    }

    // 서버에서 로그인 확인
    private boolean checkLoginOnServer(String id, String password) {
        try {
            URL url = new URL("http://localhost:3000/login"); // 백엔드 서버의 로그인 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // ID와 Password를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\", \"password\": \"" + password + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true; // 로그인 성공
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false; // 로그인 실패
    }

    private JButton setStyledButton(JButton button){
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
