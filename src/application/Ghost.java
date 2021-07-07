package application;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Thread implements GhostObservable {

	private final ArrayList<GhostObserver> observers;
	Point2D ghostLocation;
	private final GameModel gameModel; //needed?
	private final int ghostID;

	public Ghost(GameModel gameModel, int ghostID, Point2D currentGhostLocation) {

		this.ghostLocation = new Point2D(currentGhostLocation.getX(), currentGhostLocation.getY());
		this.ghostID = ghostID;
		this.gameModel = gameModel;

		//ArrayList for the registered observers
		observers = new ArrayList<>();

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

				moveGhost(ghostLocation);

				// notify Observers about changes
				notifyObservers();
			}
			// TODO has to stop when new game starts
		});
		t.start();
	}

	private void moveGhost(Point2D currentGhostLocation) {
		Random randomInt = new Random();
		Point2D possibleLocation;
		int direction = 0;

		//turnDecision random number for moving straight or making turn in random direction
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

		// RESTART when GAME-OVER (call only once)
		if (gameModel.lives <= 0 && !gameModel.gameOver) {
			gameModel.gameOver = true;
			gameModel.endLevel();
		}

		ghostLocation = possibleLocation;
	}

	/**
	 * Adds a new observer.
	 * @param newObserver the observer to add
	 */
	@Override
	public void register(GhostObserver newObserver) {
		observers.add(newObserver);
	}

	/**
	 * Removes an observer.
	 * @param removeObserver the observer to remove
	 */
	@Override
	public void unregister(GhostObserver removeObserver) {
		observers.remove(removeObserver);
	}

	/**
	 * Notifies the rgistered observers.
	 */
	@Override
	public void notifyObservers() {
		for(GhostObserver observer : observers){
			observer.update(ghostID, ghostLocation);
		}
	}
}