package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SignUpPanel extends JPanel{
    private final Tetris tetris;
    private final JTextField signUpIdField = new JTextField(12);
    private final JPasswordField signUpPwField = new JPasswordField(12);
    private final JPasswordField signUpConfirmPwField = new JPasswordField(12);
    private final JPanel mainPanel = new JPanel();
    private final JLabel signUpId = new JLabel("ID : ");
    private final JLabel signUpPw = new JLabel("Password : ");
    private final JButton checkDuplicateButton = new JButton("ID 중복 확인");
    private final JButton submitButton = new JButton("Submit");
    private final JButton backButton = new JButton("Back");
    private static final Logger logger = Logger.getLogger(SignUpPanel.class.getName());


    public SignUpPanel(Tetris tetris){
        this.tetris = tetris;
        initUI();
    }

    private void initUI(){
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("회원가입"));
        mainPanel.add(signUpId);
        mainPanel.add(signUpIdField);
        mainPanel.add(new JLabel()); // 빈 라벨
        mainPanel.add(setStyledButton(checkDuplicateButton));
        mainPanel.add(signUpPw);
        mainPanel.add(signUpPwField);
        mainPanel.add(new JLabel("Password 확인 : "));
        mainPanel.add(signUpConfirmPwField);
        mainPanel.add(setStyledButton(submitButton));
        mainPanel.add(setStyledButton(backButton));
        add(mainPanel);

        checkDuplicateButton.addActionListener(e -> checkDuplicate());
        submitButton.addActionListener(e -> submitSignUp());
        backButton.addActionListener(e -> showLoginPanel());
    }

    // ID 중복 확인
    private void checkDuplicate() {
        String id = signUpIdField.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID를 입력해주세요.", "ID 중복 확인 실패", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean isDuplicate = checkDuplicateIdOnServer(id);
    
            if (isDuplicate) {
                JOptionPane.showMessageDialog(null, "이미 사용 중인 ID입니다.", "ID 중복 확인 실패", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.", "ID 중복 확인 성공", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // 회원가입 로직
    private void submitSignUp() {
        String id = signUpIdField.getText();
        char[] pw = signUpPwField.getPassword();
        char[] confirmPw = signUpConfirmPwField.getPassword();
        if(id.isEmpty() || pw.length == 0 || confirmPw.length == 0){
            JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
        } else if(!Arrays.equals(pw, confirmPw)){
            JOptionPane.showMessageDialog(null, "Password가 일치하지 않습니다.", "Password 불일치", JOptionPane.ERROR_MESSAGE);
        } else {
            // 회원가입 성공
            // 여기에서 서버로 회원가입 정보를 전송하고 응답을 처리해야 합니다.
            boolean registrationSuccess = sendRegistrationInfoToServer(id, new String(pw));
            
            if (registrationSuccess) {
                JOptionPane.showMessageDialog(null, "회원가입에 성공했습니다.", "회원가입 성공", JOptionPane.INFORMATION_MESSAGE);
                showLoginPanel();
            } else {
                JOptionPane.showMessageDialog(null, "회원가입 실패. 다시 시도하세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 서버로 회원가입 정보를 전송하는 함수
    private boolean sendRegistrationInfoToServer(String id, String password) {
        try {
            URL url = new URL("http://localhost:3000/signup"); // 백엔드 서버의 회원가입 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 회원가입 정보를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\", \"password\": \"" + password + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
                return true; // 회원가입 성공
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "회원가입 중 에러 발생", ex);
        }

        return false; // 회원가입 실패
    }
    
    // 서버에서 ID 중복을 확인하는 함수
    private boolean checkDuplicateIdOnServer(String id) {
        try {
            URL url = new URL("http://localhost:3000/checkDuplicate"); // 백엔드 서버의 중복 확인 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // ID를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // 중복된 ID가 있는 경우
                return true;
            } else if (responseCode == 204) {
                // 중복된 ID가 없는 경우
                return false;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "중복 ID 확인 중 에러 발생", ex);
        }

        return false;
    }

    // 로그인 패널 표시
    private void showLoginPanel() {
        tetris.switchPanel(new LoginPanel(tetris));
    }

    private JButton setStyledButton(JButton button){
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
