package application;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Thread implements GhostObservable {

	private final ArrayList<GhostObserver> observers;
	Point2D ghostLocation;
	private final GameModel gameModel;
	private final int ghostID;
	int direction = 0;

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
					sleep(400);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					System.out.println("Thread Ghost of" + ghostID + " was interrupted, failed to complete operation");
					e.printStackTrace();
				}

				moveGhost(ghostLocation);

				// notify Observers about changes
				notifyObservers();
			}
		});
		t.start();
	}

	private void moveGhost(Point2D currentGhostLocation) {
		Random randomInt = new Random();
		Point2D possibleLocation;

		//turnDecision random number for moving straight or making turn in random direction
		int turnDecision = randomInt.nextInt(7);
		if (turnDecision == 6) {
			direction = randomInt.nextInt(4);
		}

		possibleLocation = gameModel.movePoint(direction, currentGhostLocation);
		String positionState = gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()];

		// if new field would be a Border, try random new directions until free one
		// found
		while (positionState.equals("BORDER") || positionState.contains("GHOST")) {
			direction = randomInt.nextInt(4);
			possibleLocation = gameModel.movePoint(direction, currentGhostLocation);
			positionState = gameModel.positionState[(int) possibleLocation.getX()][(int) possibleLocation.getY()];
		}

		// if next Location of Ghost is flagged as PACMAN, reduce one live and reset PACMAN
		if (possibleLocation.getX() == gameModel.currentPacmanLocation.getX()
				&& possibleLocation.getY() == gameModel.currentPacmanLocation.getY()) {
			gameModel.currentPacmanLocation = gameModel.startPacmanLocation;
			gameModel.positionState[(int) gameModel.currentPacmanLocation.getX()][(int) gameModel.currentPacmanLocation
					.getY()] = "PACMAN";
			gameModel.lives -= 1;
			System.out.println(
					"LIVE LOST TRIGGER from GhostClass! Location " + possibleLocation + " Lives: " + gameModel.lives); // DEBUG
		}

		checkGameState();

		ghostLocation = possibleLocation;
	}

	private void checkPacmanLocation(Point2D possibleLocation) {
		if (possibleLocation == gameModel.currentPacmanLocation) {
			gameModel.currentPacmanLocation = gameModel.startPacmanLocation;
			gameModel.positionState[(int) gameModel.currentPacmanLocation.getX()][(int) gameModel.currentPacmanLocation
					.getY()] = "PACMAN";
			gameModel.lives -= 1;
			System.out.println(
					"LIVE LOST TRIGGER from GhostClass! Location " + possibleLocation + " Lives: " + gameModel.lives); // DEBUG
		}
	}

	private void checkGameState() {
		if (gameModel.lives <= 0 && !gameModel.gameOver) {
			// prevent calling from every ghost thread
			gameModel.gameOver = true;
			// restart when game over
			gameModel.endLevel();
		}
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
	 * Notifies the registered observers.
	 */
	@Override
	public void notifyObservers() {
		for (GhostObserver observer : observers) {
			observer.update(ghostID, ghostLocation);
		}
	}
}
