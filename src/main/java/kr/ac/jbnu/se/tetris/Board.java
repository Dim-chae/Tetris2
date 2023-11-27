package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Board extends JPanel implements ActionListener {
	protected Tetris tetris;

	protected final static int BOARD_WIDTH = 10;
	protected final static int BOARD_HEGHT = 20;
	private final int SQUARE_SIZE = 20;

	private Bgm bgm;
	private Timer timer;
	private Timer lineTimer;
	private Shape curPiece;
	protected Shape nextPiece;
	protected Shape holdPiece = new Shape();
	private Shape tmpPiece = new Shape();
	protected Tetrominoes[][] tetrisBoard = new Tetrominoes[BOARD_WIDTH][BOARD_HEGHT];
	protected int numLinesRemoved = 0;
	protected int itemCount = 3;
	protected boolean isFallingFinished = false;
	protected int combo = 0;
	protected int score = 0;
	private String modeName = "";
	protected JLabel scoreLabel = new JLabel("Score : " + score);
	protected JLabel statusLabel = new JLabel();
	protected JLabel comboLabel = new JLabel("Combo : " + combo);
	protected boolean isPaused = false;
	private boolean isUseHold = false;
	private JPanel rightPanel = new JPanel(new FlowLayout());
	private JPanel nextPiecePanel = new JPanel();
	private JPanel holdPiecePanel = new JPanel();
	private JPanel statusPanel = new JPanel();
	protected JLabel nextPieceLabel = new JLabel();
	protected JLabel holdPieceLabel = new JLabel();
	protected JButton itemButton = new JButton();
	private ImageIcon itemImage = new ImageIcon("src\\main\\resources\\itemIcon.png");
	private ImageIcon backGroundImage = new ImageIcon("src\\main\\resources\\backGround.jpg");
	private Font font = new Font("맑은 고딕", Font.BOLD, 13);
	private JPanel pausePanel = new JPanel();
	private JLabel pauseLabel = new JLabel("Pause");
	private JButton resumeButton = new JButton("Resume");
	private JButton restartButton = new JButton("Restart");
	private JButton mainMenuButton = new JButton("Main Menu");
	private JButton helpButton = new JButton("Help");
	private JLayeredPane layeredPane;

	public Board(Tetris tetris, String modeName) {
		this.tetris = tetris;
		this.modeName = modeName;

		initUI();
		initGame();
	}

	private void initUI(){
		statusLabel.setText(modeName + " Mode");

		setLayout(new BorderLayout());
		addRightPanel();
		addPausePanel();
	}

	private void initGame(){
		clearBoard();
		addKeyListener(new TAdapter());
		curPiece = new Shape().setRandomShape();
		curPiece.setX(BOARD_WIDTH / 2);
		curPiece.setY(0);
		nextPiece = new Shape().setRandomShape();
		timer = new Timer(setTimerDelay(modeName), this);
		timer.start();
		lineTimer = new Timer(20000, e -> makeOneRandomLine());
		lineTimer.start();
		bgm = new Bgm();
		bgm.setVolume(tetris.getBgmVolume());
		isFallingFinished = false;
	}

	private int setTimerDelay(String modeName) {
		switch (modeName) {
			case "Easy":
			case "Time Attack":
			case "Sprint":
			case "Ghost":
				return 500;
			case "Normal":
				return 300;
			case "Hard":
				return 200;
			case "Very Hard":
				return 80;
			case "God":
				return 30;
			default:
				return 400;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			createNewPiece(); 
		} else {
			oneLineDown();
		}
        repaint();
	}

	protected void pause(){
		if(isPaused) return;

		isPaused = true;
		timer.stop();
		lineTimer.stop();
		bgm.stop();
		statusLabel.setText("Paused");
		showPauseScreen();
	}

	private void resume(){
		if(!isPaused) return;

		isPaused = false;
		timer.start();
		lineTimer.start();
		bgm.play();
		statusLabel.setText(modeName + "Mode");
		requestFocusInWindow();
		hidePauseScreen();
	}

	private void restart(){
		if(!isPaused) return;

		int result = JOptionPane.showConfirmDialog(null, "Restart?", "Restart", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			hidePauseScreen();
			clearBoard();
			holdPiece.setShape(Tetrominoes.NoShape);
			nextPiece = new Shape().setRandomShape();
			createNewPiece();
			score = 0;
			combo = 0;
			itemCount = 3;
			isPaused = false;
			bgm.replay();
			timer.start();
			lineTimer.start();
			requestFocusInWindow();
		}
	}

	private boolean canMove(Shape newPiece, int newX, int newY){
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.getX(i);
			int y = newY + newPiece.getY(i); 
			if ((x < 0) && (y >= 0 || y <= BOARD_HEGHT))
				tryMove(newPiece, newX + 1, newY); 
			if ((x >= BOARD_WIDTH) && (y >= 0 || y <= BOARD_HEGHT)) 
				tryMove(newPiece, newX - 1, newY); 
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEGHT) 
				return false;
			if (tetrisBoard[x][y] != Tetrominoes.NoShape)
				return false;
		}
		return true; 
	}

	private boolean tryMove(Shape newPiece, int newX, int newY) {
		if(!canMove(newPiece, newX, newY)) return false;

		curPiece = newPiece; 
		curPiece.setX(newX);
		curPiece.setY(newY);
		repaint();
		return true;
	}

	private void createNewPiece() {
		curPiece = nextPiece;
		nextPiece = new Shape().setRandomShape();
		curPiece.setX(BOARD_WIDTH / 2);
		curPiece.setY(-curPiece.getMinY());
		isUseHold = false;

		if(!tryMove(curPiece, curPiece.getX(), curPiece.getY())) {
			gameOver();
		}
	}

	protected void gameOver(){
		pause();
		resumeButton.setVisible(false);
		pauseLabel.setText("Game Over");
		statusLabel.setText("Game Over");
		calcGameExp();
	}
	
	public void hardDrop() {
		int newY = curPiece.getY();
		while (newY < BOARD_HEGHT - 1) {
			if (!tryMove(curPiece, curPiece.getX(), newY + 1)) {
				break;
			}
			++newY;
		}
		putPieceOnBoard();
		repaint();
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curPiece.getX(), curPiece.getY() + 1)) {
			putPieceOnBoard();
		}
	}

    private void putPieceOnBoard() {
		int[][] pieceCoords = curPiece.getCoords();
		for (int i = 0; i < 4; i++) {
			int x = curPiece.getX() + pieceCoords[i][0];
			int y = curPiece.getY() + pieceCoords[i][1];
			tetrisBoard[x][y] = curPiece.getShape();
		}
	
		removeFullLines();

		if (!isFallingFinished) {
			createNewPiece();
		}
	}

	private void removeFullLines() {
		int numFullLines = 0;
		int comboScore = 0;

		for(int i = BOARD_HEGHT - 1; i >= 0; i--) {
			boolean lineIsFull = true;
			for(int j = 0; j < BOARD_WIDTH; j++) {
				if(tetrisBoard[j][i] == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}
			if(lineIsFull) {
				numFullLines++;
				for(int k = i; k > 0; k--) {
					for(int j = 0; j < BOARD_WIDTH; j++) {
						tetrisBoard[j][k] = tetrisBoard[j][k - 1];
					}
				}
				for(int j = 0; j < BOARD_WIDTH; j++) {
					tetrisBoard[j][0] = Tetrominoes.NoShape;
				}
				i++;
			}
		}

		if (numFullLines > 0) {
			combo += numFullLines;
			numLinesRemoved += numFullLines;
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		} else {
			combo = 0;
		}

		if (combo > 1) {
			comboScore = 50 * combo;
		}
		score += 100 * numFullLines + comboScore;
	}

	private void holdCurPiece(){
		if(isUseHold) return;

		if(holdPiece.getShape() == Tetrominoes.NoShape){
			holdPiece = curPiece;
			createNewPiece();
		} else {
			int x = curPiece.getX();
			int y = curPiece.getY();
			tmpPiece = curPiece;
			curPiece = holdPiece;
			holdPiece = tmpPiece;
			curPiece.setX(x);
			curPiece.setY(y);
		}

		isUseHold = true;
		repaint();
	}

	private void makeOneRandomLine(){
		for(int i = 0; i < BOARD_WIDTH; i++){
			for(int j = 0; j < BOARD_HEGHT - 1; j++){
				tetrisBoard[i][j] = tetrisBoard[i][j + 1];
			}
		}
		
		for (int i=0; i<BOARD_WIDTH; ++i){
			tetrisBoard[i][BOARD_HEGHT - 1] = Tetrominoes.OneBlockShape;
		}

		Random random = new Random();
		int randomX = random.nextInt(BOARD_WIDTH);
		tetrisBoard[randomX][BOARD_HEGHT - 1] = Tetrominoes.NoShape;

		repaint();
	}

	private void clearBoard(){
		for(int i = 0; i < BOARD_WIDTH; i++){
			for(int j = 0; j < BOARD_HEGHT; j++){
				tetrisBoard[i][j] = Tetrominoes.NoShape;
			}
		}
	}

    @Override
    public void paint(Graphics g){
        super.paint(g);
		// drawBackgroudImage(g);
		drawGridPattern(g);
		drawGhost(g);
        drawPiece(g);
		drawBoard(g);
		updateScorePanel();
    }

    private void drawPiece(Graphics g){
        if(curPiece.getShape() == Tetrominoes.NoShape){
			return;
		}
		for(int i = 0; i < 4; ++i){
			int x = curPiece.getX() + curPiece.getCoords()[i][0];
			int y = curPiece.getY() + curPiece.getCoords()[i][1];
			drawSquare(g, x * SQUARE_SIZE, y * SQUARE_SIZE, curPiece.getShape());
		}
    }

	private void drawBoard(Graphics g){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_HEGHT; j++){
                drawSquare(g, i * SQUARE_SIZE, j * SQUARE_SIZE, tetrisBoard[i][j]);
            }
        }
    }

	private void drawGhost(Graphics g){
		int curX = curPiece.getX();
		int curY = curPiece.getY();
		int newY = curY;
		g.setColor(Color.GRAY);
		while (newY < BOARD_HEGHT - 1) {
			if (!canMove(curPiece, curX, newY + 1)) {
				break;
			}
			++newY;
		}
		for(int i = 0; i < 4; ++i){
			int x = curX + curPiece.getCoords()[i][0];
			int y = newY + curPiece.getCoords()[i][1];
			g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		}
	}

	private void drawGridPattern(Graphics g){
		g.setColor(Color.WHITE);
		for(int i = 0; i <= BOARD_WIDTH; i++){
			g.drawLine(i * SQUARE_SIZE, 0, i * SQUARE_SIZE, BOARD_HEGHT * SQUARE_SIZE);
		}
		for(int i = 0; i <= BOARD_HEGHT; i++){
			g.drawLine(0, i * SQUARE_SIZE, BOARD_WIDTH * SQUARE_SIZE, i * SQUARE_SIZE);
		}
	}

    protected void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color color = shape.getShape().getColor();
		
		g.setColor(color);
		g.fillRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + SQUARE_SIZE - 1, x, y);
		g.drawLine(x, y, x + SQUARE_SIZE -1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1);
		g.drawLine(x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + 1);
	}

	private void drawBackgroudImage(Graphics g){
		g.drawImage(backGroundImage.getImage(), 0, 0, null);
	}

	private void calcGameExp(){
		int gameEXP = this.score / 10;
		tetris.addUserExp(gameEXP);
	}

	private void addRightPanel(){
		rightPanel.setPreferredSize(new Dimension(190, 400));
		add(rightPanel, BorderLayout.EAST);
		addNextPiecePanel();
		addHoldPiecePanel();
		addStatusPanel();
		addItemButton();
	}

	private void addNextPiecePanel(){
		nextPiecePanel.setPreferredSize(new Dimension(100, 100));
		nextPiecePanel.setBorder(BorderFactory.createTitledBorder(null, "Next Piece", TitledBorder.CENTER, TitledBorder.TOP, font, new Color(70, 130, 180)));
		nextPiecePanel.add(nextPieceLabel);
		rightPanel.add(nextPiecePanel);
	}

	private void addHoldPiecePanel(){
		holdPiecePanel.setPreferredSize(new Dimension(100, 100));
		holdPiecePanel.setBorder(BorderFactory.createTitledBorder(null, "Hold Piece", TitledBorder.CENTER, TitledBorder.TOP, font, new Color(70, 130, 180)));
		holdPiecePanel.add(holdPieceLabel);
		rightPanel.add(holdPiecePanel);
	}

	private void addStatusPanel(){
		statusPanel.setPreferredSize(new Dimension(100, 100));
		statusPanel.setBorder(BorderFactory.createTitledBorder(null, "Status", TitledBorder.CENTER, TitledBorder.TOP, font, new Color(70, 130, 180)));
		statusPanel.add(statusLabel);
		statusPanel.add(scoreLabel);
		statusPanel.add(comboLabel);
		rightPanel.add(statusPanel);
	}

	private void addItemButton(){
		itemButton.setPreferredSize(new Dimension(100, 40));
		itemButton.setIcon(itemImage);
		itemButton.setBackground(new Color(173, 216, 230));
		itemButton.addActionListener(e -> useItem());
		rightPanel.add(itemButton);
	}

	private void useItem(){
		if(isPaused) return;

		int blockCount = 0;
		for(int i = 0; i < BOARD_WIDTH; i++){
			for(int j = 0; j < BOARD_HEGHT; j++){
				if(tetrisBoard[i][j] != Tetrominoes.NoShape){
					blockCount++;
				}
			}
		}
		score += blockCount * 10;
		itemCount--;
		clearBoard();
		requestFocusInWindow();

		if(itemCount == 0 ){
			itemButton.setVisible(false);
		}
	}

	protected void updateScorePanel() {
		scoreLabel.setText("Score : " + score);
		comboLabel.setText("Combo : " + combo);
		itemButton.setText(String.valueOf(itemCount));
		nextPieceLabel.setIcon(nextPiece.getImage());
		holdPieceLabel.setIcon(holdPiece.getImage());
		repaint();
	}

	private void addPausePanel(){
		layeredPane = tetris.getLayeredPane();
		layeredPane.add(pausePanel, JLayeredPane.PALETTE_LAYER);
		pausePanel.setBounds(0, 0, 200, 450);
		pausePanel.setBackground(new Color(0, 0, 0, 140));
		pausePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

		pauseLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		pauseLabel.setForeground(Color.WHITE);
		pauseLabel.setHorizontalAlignment(JLabel.CENTER);
		pauseLabel.setVerticalAlignment(JLabel.CENTER);
		
		resumeButton.setPreferredSize(new Dimension(150, 40));
		resumeButton.addActionListener(e -> resume());

		restartButton.setPreferredSize(new Dimension(150, 40));
		restartButton.addActionListener(e -> restart());

		mainMenuButton.setPreferredSize(new Dimension(150, 40));
		mainMenuButton.addActionListener(e -> goMainMenu());

		helpButton.setPreferredSize(new Dimension(150, 40));
		helpButton.addActionListener(e -> showHelpScreen());

		pausePanel.add(pauseLabel);
		pausePanel.add(resumeButton);
		pausePanel.add(restartButton);
		pausePanel.add(mainMenuButton);
		pausePanel.add(helpButton);
		pausePanel.setVisible(false);
	}

	private void showPauseScreen(){
		pausePanel.setVisible(true);
	}

	private void hidePauseScreen(){
		pausePanel.setVisible(false);
	}

	protected void goMainMenu(){
		int result = JOptionPane.showConfirmDialog(null, "Go to Main Menu?", "Main Menu", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			hidePauseScreen();
			tetris.switchPanel(new MainMenu(tetris));
		}
	}

	private void showHelpScreen() {
		String msg = """
            다양한 난이도와 모드를 지원하는 테트리스 게임입니다.

            [모드 설명]
            스프린트: 40줄을 최대한 빠른 시간 안에 지우는 모드
            타임어택: 2분 동안 많은 줄을 제거하는 모드
            고스트: 고스트만 보이는 모드

            [아이템 설명 : 폭탄]
            I 버튼이나 폭탄 아이콘을 누르면 아이템을 사용할 수 있습니다.
            사용 시 해당 시점에 쌓인 블록의 수만큼 점수가 100점씩 추가됩니다.
            레벨이 1 올라갈 때마다 아이템을 1개씩 얻을 수 있습니다.

            [단축키]
            좌우 방향 키: 블록 이동
            상 방향 키: 블록 회전
            하 방향 키: 소프트 드롭
            Space: 하드 드롭
            C: 홀드
            ESC: 일시정지
            """;

		JOptionPane.showMessageDialog(this, msg, "도움말", JOptionPane.INFORMATION_MESSAGE);
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();

			if (keycode == KeyEvent.VK_ESCAPE) {
				if(!isPaused){
					pause();
				} else {
					resume();
					hidePauseScreen();
				}
			}

			if (isPaused) return;

			switch (keycode) {
				case KeyEvent.VK_LEFT:
                tryMove(curPiece, curPiece.getX() - 1, curPiece.getY());
                break;
            case KeyEvent.VK_RIGHT:
                tryMove(curPiece, curPiece.getX() + 1, curPiece.getY());
                break;
            case KeyEvent.VK_DOWN:
                tryMove(curPiece, curPiece.getX(), curPiece.getY() + 1);
                break;
            case KeyEvent.VK_UP:
                tryMove(curPiece.rotateRight(), curPiece.getX(), curPiece.getY());
                break;
            case KeyEvent.VK_SPACE:
                hardDrop();
                break;
            case 'c':
            case 'C':
				holdCurPiece();
                break;
			case 'i':
			case 'I':
				useItem();
				break;
			default:
			}
		}
	}
}
