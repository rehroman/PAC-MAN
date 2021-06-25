package application;

import javafx.geometry.Point2D;

public class Ghost extends Thread {
	Point2D ghostLocation;
	private GameModel gameModel;
	private int ghostID;
	
	public Ghost(GameModel gameModel, int ghost, Point2D ghostLocation) {
		this.ghostLocation = ghostLocation;
		this.ghostID = ghostID;
	}
	
	public Point2D getGhostLocation() {
		return this.ghostLocation;
	}
	
	public void run() {
		while (gameModel.gameOver == false) {
			System.out.println();
			
		}
	}
	public void move(int direction) {
		
		
		//TODO check for walls before set ghostLocation	

		
	}

}
