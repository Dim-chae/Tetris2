package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class Board extends JPanel implements ActionListener {
	protected Tetris tetris;

	protected final static int BoardWidth = 10; //게임 보드의 가로 칸 수
	protected final static int BoardHeight = 20; //게임 보드의 세로 칸 수
	private final int SQUARE_SIZE = 20;

	private Bgm bgm;
	private Timer timer; //게임의 속도를 조절하는 타이머
	private Shape curPiece; //현재 블록을 나타내는 객체
	private Shape nextPiece; //다음 블록을 나타내는 객체
	protected Tetrominoes[][] board = new Tetrominoes[BoardWidth][BoardHeight]; //게임 보드를 나타내는 배열 생성
	protected int numLinesRemoved = 0; //사용자가 제거한 줄의 수를 나타내는 변수
	protected boolean isFallingFinished = false; //블록이 떨어지는 것이 끝났는지를 나타내는 변수
	protected int combo = 0;
	protected int score = 0;
	private String modeName = "";
	protected JLabel scoreLabel = new JLabel("Score : " + score);
	protected JLabel statusLabel = new JLabel();
	protected JLabel comboLabel = new JLabel("Combo : " + combo);

	public Board(Tetris tetris, String modeName) {
		this.tetris = tetris;
		this.modeName = modeName;

		initUI();
		initGame();
	}

	private void initUI(){
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250, (BoardHeight + 8) * SQUARE_SIZE));
	}

	private void initGame(){
		clearBoard();
		addKeyListener(new TAdapter());
		curPiece = new Shape().setRandomShape();
		curPiece.setX(BoardWidth / 2);
		curPiece.setY(0);
		nextPiece = new Shape().setRandomShape();
		timer = new Timer(setTimerDelay(modeName), this);
		timer.start();
		bgm = new Bgm();
		bgm.setVolume(tetris.getBgmVolume());
		isFallingFinished = false;
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

	private boolean tryMove(Shape newPiece, int newX, int newY) { //새로운 위치(newX, newY)로 블록을 이동하려고 시도하는 메소드
		for (int i = 0; i < 4; ++i) { //새로운 블록의 모든 칸에 대해
			int x = newX + newPiece.getX(i); //새로운 블록의 x좌표
			int y = newY + newPiece.getY(i); //새로운 블록의 y좌표
			if ((x < 0) && (y >= 0 || y <= BoardHeight)) //새로운 위치가 왼쪽 벽을 넘어간다면
				tryMove(newPiece, newX + 1, newY); //새로운 블록을 오른쪽으로 한 칸 이동
			if ((x >= BoardWidth) && (y >= 0 || y <= BoardHeight)) //새로운 위치가 오른쪽 벽을 넘어간다면
				tryMove(newPiece, newX - 1, newY); //새로운 블록을 왼쪽으로 한 칸 이동
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) //새로운 블록이 게임 보드의 범위를 벗어난다면
				return false; //false 반환
			if (board[x][y] != Tetrominoes.NoShape) //새로운 블록이 게임 보드의 다른 블록과 겹친다면 = 새로운 x, y에 블록이 존재한다면
				return false; //false 반환
		}

		curPiece = newPiece; //새로운 블록을 현재 블록으로 설정
		curPiece.setX(newX);
		curPiece.setY(newY);
		repaint(); //게임 보드를 다시 그림
		return true; //true 반환
	}

	private void createNewPiece() {
		curPiece = nextPiece;
		nextPiece = new Shape().setRandomShape();
		curPiece.setX(BoardWidth / 2);
		curPiece.setY(-curPiece.getMinY());

		System.out.println(curPiece.getShape() + " " + curPiece.getMinY());
		if(!tryMove(curPiece, curPiece.getX(), curPiece.getY())) {
			gameOver();
		}
	}

	private void gameOver(){
		timer.stop();
		bgm.stop();
		statusLabel.setText("Game Over");
		System.out.println("Game Over");
	}
	
	public void hardDrop() {
		int newY = curPiece.getY();
		while (newY < BoardHeight - 1) {
			if (!tryMove(curPiece, curPiece.getX(), newY + 1)) {
				break;
			}
			++newY;
		}
		putPieceOnBoard();
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curPiece.getX(), curPiece.getY() + 1)) { //현재 블록을 한 칸 아래로 이동할 수 없다면
			putPieceOnBoard(); //현재 블록을 게임 보드에 놓음
		}
	}

    private void putPieceOnBoard() {
		int[][] pieceCoords = curPiece.getCoords();
		for (int i = 0; i < 4; i++) {
			int x = curPiece.getX() + pieceCoords[i][0];
			int y = curPiece.getY() + pieceCoords[i][1];
			board[x][y] = curPiece.getShape();
		}
	
		removeFullLines();

		if (!isFallingFinished) {
			createNewPiece();
		}
	}

	private void removeFullLines() {
		int numFullLines = 0;
		int comboScore = 0;

		for(int i = BoardHeight - 1; i >= 0; i--) {
			boolean lineIsFull = true;
			for(int j = 0; j < BoardWidth; j++) {
				if(board[j][i] == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}
			if(lineIsFull) {
				numFullLines++;
				for(int k = i; k > 0; k--) {
					for(int j = 0; j < BoardWidth; j++) {
						board[j][k] = board[j][k - 1];
					}
				}
				for(int j = 0; j < BoardWidth; j++) {
					board[j][0] = Tetrominoes.NoShape;
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

	public void clearBoard(){
		for(int i = 0; i < BoardWidth; i++){
			for(int j = 0; j < BoardHeight; j++){
				board[i][j] = Tetrominoes.NoShape;
			}
		}
	}

    @Override
    public void paint(Graphics g){
        super.paint(g);
		drawBackgroudImage(g);
		drawGridPattern(g);
		drawBoard(g);
        drawPiece(g);
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
        for(int i = 0; i < BoardWidth; i++){
            for(int j = 0; j < BoardHeight; j++){
                drawSquare(g, i * SQUARE_SIZE, j * SQUARE_SIZE, board[i][j]);
            }
        }
    }

	private void drawGridPattern(Graphics g){
		g.setColor(Color.WHITE);
		for(int i = 0; i <= BoardWidth; i++){
			g.drawLine(i * SQUARE_SIZE, 0, i * SQUARE_SIZE, BoardHeight * SQUARE_SIZE);
		}
		for(int i = 0; i <= BoardHeight; i++){
			g.drawLine(0, i * SQUARE_SIZE, BoardWidth * SQUARE_SIZE, i * SQUARE_SIZE);
		}
	}

    protected void drawSquare(Graphics g, int x, int y, Tetrominoes shape) { //x, y는 블록 왼쪽 상단의 좌표, shape는 블록의 모양
		Color color = shape.getShape().getColor();
		
		g.setColor(color);
		g.fillRect(x + 1, y + 1, SQUARE_SIZE - 2, SQUARE_SIZE - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + SQUARE_SIZE - 1, x, y);
		g.drawLine(x, y, x + SQUARE_SIZE -1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1); //블록의 아래쪽에 어두운 선을 그림(그림자)
		g.drawLine(x + SQUARE_SIZE - 1, y + SQUARE_SIZE - 1, x + SQUARE_SIZE - 1, y + 1); //블록의 오른쪽에 어두운 선을 그림
	}

	private void drawBackgroudImage(Graphics g){
		ImageIcon backGroundImage = new ImageIcon("src\\kr\\ac\\jbnu\\se\\tetris\\resources\\backGround.jpg");
		g.drawImage(backGroundImage.getImage(), 0, 0, null);
		g.setColor(new Color(255, 255, 255, 50));
		g.fillRect(0, 0, BoardWidth * SQUARE_SIZE, BoardHeight * SQUARE_SIZE);
	}


	private int setTimerDelay(String modeName) {
		switch (modeName) {
			case "Easy":
			case "스프린트 모드":
			case "타임어택 모드":
			case "그림자 모드":
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

	protected void updateScorePanel() {
		scoreLabel.setText("Score : " + score);
		comboLabel.setText("Combo : " + combo);
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();

			// if (keycode == KeyEvent.VK_ESCAPE) {
			// 	if (isPaused) {
			// 		return;
			// 	}
			// 	pause();
			// 	return;
			// }

			switch (keycode) {
				case KeyEvent.VK_LEFT:
                tryMove(curPiece, curPiece.getX() - 1, curPiece.getY());
                break; // 왼쪽으로 이동 (왼쪽 화살표)
            case KeyEvent.VK_RIGHT:
                tryMove(curPiece, curPiece.getX() + 1, curPiece.getY());
                break; // 오른쪽으로 이동 (오른쪽 화살표)
            case KeyEvent.VK_DOWN:
                if (tryMove(curPiece, curPiece.getX(), curPiece.getY() + 1)) {
                    // curPiece.moveDown();
                }
                repaint();
                break; // 아래쪽으로 이동 (아래쪽 화살표)
            case KeyEvent.VK_UP:
                tryMove(curPiece.rotateRight(), curPiece.getX(), curPiece.getY());
                repaint();
                break; // 반시계 방향 회전 (위쪽 화살표)
            case KeyEvent.VK_SPACE:
                hardDrop();
                repaint();
                break; // 하드 드롭 (Space)
            case 'c':
            case 'C':
                // holdCurPiece(); // 홀드 기능 (c)
                break;
				// case 'i':
				// case 'I':
				// 	item.useItem();
				// 	if(tetris.getUserItemReserves() == 0) itemReservesButton.setVisible(false);
				// 	break; // 아이템 사용 (I)
			}
		}
	}
}
