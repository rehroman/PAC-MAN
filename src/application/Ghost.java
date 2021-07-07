package application;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Thread implements GhostObservable {

	private ArrayList<GhostObserver> observers;

	Point2D ghostLocation;
	private GameModel gameModel;
	private int ghostID;
	private int direction = 0;

	public Ghost(GameModel gameModel, int ghostID, Point2D currentGhostLocation) {

		this.ghostLocation = new Point2D(currentGhostLocation.getX(), currentGhostLocation.getY());
		this.ghostID = ghostID;
		this.gameModel = gameModel;

		/**
		 *
		 * @param observers ArrayList for the registered observers
		 */
		observers = new ArrayList<GhostObserver>();

		// Using its own thread for every Ghost
		Thread t = new Thread(() -> {

			while (!gameModel.gameOver && !gameModel.gameWin) {

				try {
					sleep(500); // TODO adjust step time
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Thread Ghost of" + ghostID + " was interrupted, failed to complete operation");
					e.printStackTrace();
				}

				//System.out.println("\n\nGhost " + ghostID + " STEP"); //DEBUG
				//System.out.println("Current Position Ghost " + ghostID + " " + ghostLocation); //DEBUG

				moveGhost(ghostLocation);
				notifyObservers(); // notify Observer about changes
			}
			// TODO has to stop when new game starts
		});
		t.start();
	}

	public void moveGhost(Point2D currentGhostLocation) {
		Random randomInt = new Random();
		Point2D possibleLocation;

		/**
		 * @param turnDecision random number for moving straight or making turn in random direction
		 */
		int turnDecision = randomInt.nextInt(7);
		if (turnDecision == 6) {
			direction = randomInt.nextInt(4);
		}
		possibleLocation = gameModel.movePoint(direction, currentGhostLocation);

		// if new field would be a Border, try random new directions until free one found
		while(gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()].equals("BORDER") 
				|| gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()].equals("GHOST1")
				|| gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()].equals("GHOST2")
				|| gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()].equals("GHOST3")) {
			direction = randomInt.nextInt(4);
			possibleLocation = gameModel.movePoint(direction, currentGhostLocation);
		}

		// if next Location of Ghost is flagged as PACMAN, reduce one live and reset PACMAN
		if (possibleLocation.getX() == gameModel.currentPacmanLocation.getX()
				&& possibleLocation.getY() == gameModel.currentPacmanLocation.getY()) {
			gameModel.currentPacmanLocation = gameModel.startPacmanLocation;
			gameModel.positionState[(int) gameModel.currentPacmanLocation.getX()][(int) gameModel.currentPacmanLocation.getY()]= "PACMAN";
			// TODO Timos movePacManImage muss hier rein
			gameModel.lives -= 1;
			System.out.println("LIVE LOST TRIGGER from GhostClass! Location "+ possibleLocation  + " Lives: " + gameModel.lives); //DEBUG
		}

		// RESTART when GAME-OVER
		if (gameModel.lives <= 0) {
			gameModel.gameOver = true;
			gameModel.start(1);
		}

		ghostLocation = possibleLocation;

		//System.out.println("New Position Ghost " + ghostID + " " + ghostLocation); //DEBUG
	}

	/**
	 * 	add new Observer
	 */
	@Override
	public void register(GhostObserver newObserver) {

		observers.add(newObserver);

	}

	/**
	 * remove Observer
	 */
	@Override
	public void unregister(GhostObserver removeObserver) {
		
		observers.remove(removeObserver);
	}
	/*
	 * notify registered Observers
	 */
	@Override
	public void notifyObservers() {
		for(GhostObserver observer : observers){
			observer.update(ghostID, ghostLocation);
		}
	}
}