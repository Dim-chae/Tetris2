package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.util.Random;
import javax.swing.ImageIcon;

public class Shape {
    protected int[][] coords;
    protected Tetrominoes pieceShape;
    protected Color color;
    protected ImageIcon image;    

	private int x;
	private int y;

    public Shape(){
        x = 0;
        y = 0;
        setShape(Tetrominoes.NoShape);
		coords = new int[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}};
    }

    public Tetrominoes getShape(){
        return pieceShape;
    }

    public void setShape(Tetrominoes shape){
        pieceShape = shape;
    }

	public Shape setRandomShape(){
        Random r = new Random();
        int ran = Math.abs(r.nextInt()) % (Tetrominoes.values().length-2) + 2;
        return Tetrominoes.values()[ran].getShape();
    }
	
	public int[][] getCoords(){
		return coords;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getX(int index) {
		return coords[index][0];
	}

	public int getY(int index) {
		return coords[index][1];
	}

	public int getMinY(){
		int m = coords[0][1];
		for(int i = 0; i < coords.length; i++){
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public String getShapeToString(){
		return pieceShape.toString();
	}

	public Color getColor(){
        return color;
    }

    public ImageIcon getImage(){
        return image;
    }

	public void moveDown(){
		for(int i = 0; i < coords.length; i++){
            coords[i][1]++;
        }
	}

	public void moveLeft(){
		for(int i = 0; i < coords.length; i++){
			coords[i][0]--;
		}
	}

	public void moveRight(){
		for(int i = 0; i < coords.length; i++){
			coords[i][0]++;
		}
	}

	public Shape rotateRight() {
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.coords[i][0] = -coords[i][1];
			result.coords[i][1] = coords[i][0];
		}
		return result;
	}
}