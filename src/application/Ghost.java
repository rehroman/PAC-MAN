package application;

import javafx.geometry.Point2D;
import java.util.Random;

public class Ghost extends Thread {

	Point2D ghostLocation;
	private GameModel gameModel;
	private int ghostID;

	public Ghost(GameModel gameModel, int ghostID, Point2D currentGhostLocation) {

		this.ghostLocation = new Point2D(currentGhostLocation.getX(), currentGhostLocation.getY());
		this.ghostID = ghostID;
		this.gameModel = gameModel;

		// Using its own thread for every Ghost
		Thread t = new Thread() {

			@Override
			public void run() {

				while (gameModel.gameOver == false) {

					try {
						sleep(3000); // TODO adjust step time
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						System.out.println("Thread " + ghostID + " was interrupted, Failed to complete operation");
						e.printStackTrace();
					}

					System.out.println("\n\nGhost " + ghostID + " STEP");
					System.out.println("Current Position Ghost " + ghostID + " " + ghostLocation);

					if (ghostID == 0) {
						gameModel.currentGhost1Location = moveGhost(ghostLocation);
					} else if (ghostID == 1) {
						gameModel.currentGhost2Location = moveGhost(ghostLocation);
					}
					
				}
				// TODO has to stop when new game starts
			}
		};
		t.start();
	}

	public Point2D moveGhost(Point2D currentGhostLocation) {
		Random randomDirection = new Random();
		
		Point2D possibleLocation = currentGhostLocation;
		
		// move Ghost in random direction
		possibleLocation = gameModel.movePoint(randomDirection.nextInt(4), currentGhostLocation);
		
		// if new field would be a Border, try random new directions until free one found
		while(gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()] == "BORDER") {
			possibleLocation = gameModel.movePoint(randomDirection.nextInt(4), currentGhostLocation);
		}
		
		//if next Location of Ghost is flagged as PACMAN, reduce by one live and reset PACMAN 
		if (possibleLocation.getX() == gameModel.currentPacmanLocation.getX() && possibleLocation.getY() == gameModel.currentPacmanLocation.getY()) {
			gameModel.currentPacmanLocation = gameModel.startPacmanLocation;
			gameModel.positionState[(int) gameModel.currentPacmanLocation.getX()][(int) gameModel.currentPacmanLocation.getY()]= "PACMAN";
			gameModel.lives -= 1;
			System.out.println("LIVE LOST TRIGGER! Location "+ possibleLocation  + " Lives: " + gameModel.lives); //DEBUG
		}
			
			if (gameModel.lives == 0) {
				gameModel.gameOver = true;
				gameModel.start();
			}
		
		ghostLocation = possibleLocation;
			
		System.out.println("New Position Ghost " + ghostID + " " + ghostLocation); //DEBUG
		
		return ghostLocation;
	}

}
