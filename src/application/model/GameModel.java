package application.model;

import application.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The GameModel holds most game logic.
 */
public class GameModel implements GhostObserver, MovementObservable {

	int points;
	int lives;
	int dotsCount;
	String username;
	int levelNo = 1;

	// Properties for GameState
	public Boolean gameOver;
	Boolean gameWin;

	private final ArrayList<MovementObserver> movementObservers = new ArrayList<>();

	// Properties for Ghosts
	Point2D currentGhost1Location;
	Point2D currentGhost2Location;
	Point2D currentGhost3Location;

	// Properties for PacMan
	Point2D startPacmanLocation = new Point2D(1, 1);
	Point2D currentPacmanLocation; // always holds PacMan location
	int currentPacManDirection;

	// Properties for Level
	String[][] positionState; // hold positions of all elements, needed to render grid
	GridPane world;
	String[] previousGhostState = new String[] { "DOT", "DOT", "DOT" };

	/* loading images */
	Image pacMan_Right = new Image("/Icons/PacMan_Right.png");
	Image ghost1 = new Image("/Icons/Ghost1.png");
	Image ghost2 = new Image("/Icons/Ghost2.png");
	Image ghost3 = new Image("/Icons/Ghost3.png");
	Image cherry = new Image("/Icons/Cherry.png");
	Image border = new Image("/Icons/brick-wall.png");

	/**
	 * GameModel constructor
	 */
	public GameModel(String username) {
		this.username = username;
		this.start(levelNo);
		initializeGhosts();
	}

	/* ==================NEWGAME=========================== */

	/**
	 * Starts the game.
	 */
	public void start(int level) {
		System.out.println("\n\n\n\n--------NEWGAME--------\n\n");// DEBUG;
		if (level == 1)
			points = 0;
		lives = 3;
		currentPacManDirection = 1;
		gameOver = false;
		gameWin = false;

		positionState = this.getLevel(level);

		currentPacmanLocation = this.setItemInWorld((int) startPacmanLocation.getX(),(int) startPacmanLocation.getY(), "PACMAN");
		currentGhost1Location = setItemInWorld(7,4, "GHOST1");
		currentGhost2Location = setItemInWorld(7,10, "GHOST2");
		currentGhost3Location = setItemInWorld(13,7, "GHOST3");

		this.renderLevel(positionState);
	}

	private Point2D setItemInWorld(int xCoordinate, int yCoordinate, String state) {
		positionState[xCoordinate][yCoordinate] = state;
		return new Point2D(xCoordinate, yCoordinate);
	}

	/* ==================GAMEWORLD========================== */

	private String[][] getLevel(int selectedLevel) {
		/* always use a 15 x 15 world because of the size; world[row][column] */
		String[][] level = null;

		/*set different levels*/
		switch(selectedLevel) {
			case 1:
				level = new String[][]{
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 0
						{"BORDER", "DOT",     "DOT",    "DOT",   "DOT",     "DOT",   "DOT",    "BORDER",  "DOT",    "DOT",     "DOT",   "DOT",    "DOT",    "DOT",   "BORDER"}, // 1
						{"BORDER", "DOT",    "BORDER",  "DOT",   "BORDER", "BORDER", "DOT",    "BORDER",  "DOT",   "BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER"}, // 2
						{"BORDER", "CHERRY",  "DOT",    "DOT",   "DOT",    "DOT",    "DOT",     "DOT",    "DOT",    "DOT",     "DOT",   "DOT",    "DOT",   "CHERRY", "BORDER"}, // 3
						{"BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER"}, // 4
						{"BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "DOT",    "DOT",     "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER"}, // 5
						{"BORDER", "DOT",    "DOT",     "DOT",   "BORDER", "DOT",    "BORDER", "BORDER",  "BORDER", "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 6
						{"BORDER", "DOT",    "BORDER",  "DOT",   "DOT",    "DOT",    "BORDER", "BORDER",  "BORDER", "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",   "BORDER"}, // 7
						{"BORDER", "DOT",    "BORDER",  "DOT",   "BORDER", "BORDER",  "CHERRY",  "DOT",   "CHERRY","BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER"}, // 8
						{"BORDER", "DOT",     "DOT",    "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 9
						{"BORDER", "DOT",    "BORDER",  "DOT",    "DOT",   "DOT",     "DOT",    "DOT",    "DOT",    "DOT",     "DOT",   "DOT",   "BORDER",  "DOT",   "BORDER"}, // 10
						{"BORDER", "DOT",    "DOT",     "DOT",   "BORDER", "DOT",    "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 11
						{"BORDER", "DOT",    "BORDER", "BORDER","BORDER",  "DOT",    "BORDER",  "BORDER","BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER"}, // 12
						{"BORDER", "DOT",    "DOT",     "DOT",    "DOT",   "DOT",     "DOT",    "DOT",   "DOT",     "DOT",    "DOT",    "DOT",    "DOT",    "DOT",   "BORDER"}, // 13
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 14
						//  0		  1			2		  3			4		  5			6		  7         8		  9			10		  11		12		  13        14
				};
				break;
			case 2:
				level = new String[][]{
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 0
						{"BORDER",  "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",     "DOT",   "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 1
						{"BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER"}, // 2
						{"BORDER",  "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",   "BORDER"}, // 3
						{"BORDER",  "DOT",   "BORDER", "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER"}, // 4
						{"BORDER",  "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",    "DOT",     "DOT",   "DOT",    "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",   "BORDER"}, // 5
						{"BORDER",  "DOT",    "DOT",    "DOT",   "BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER", "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 6
						{"BORDER", "BORDER", "BORDER",  "DOT",    "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",    "DOT",   "BORDER", "BORDER", "BORDER"}, // 7
						{"BORDER",  "DOT",   "CHERRY",  "DOT",   "BORDER", "BORDER",  "DOT",     "DOT",   "DOT",   "BORDER", "BORDER",  "DOT",   "CHERRY",  "DOT",   "BORDER"}, // 8
						{"BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER",  "DOT",   "BORDER"}, // 9
						{"BORDER",  "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",   "CHERRY",  "DOT",    "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER"}, // 10
						{"BORDER",  "DOT",   "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",    "DOT",    "DOT",   "BORDER",  "DOT",   "BORDER"}, // 11
						{"BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER", "BORDER", "BORDER",  "DOT",   "BORDER"}, // 12
						{"BORDER",  "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",    "DOT",      "DOT",   "BORDER"}, // 13
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 14
						//  0		  1			2		  3			4		  5			6		  7         8		  9			10		  11		12		  13        14
				};
				break;
		}

		return level;
	}

	/**
	 * counts the number of dots in the current loaded Level
	 * @param levelWorld the current level's world
	 */
	private void countDots(String[][] levelWorld) {
		dotsCount = 0;
		for (int rowNumber = 0; rowNumber < levelWorld.length; rowNumber++) { // 1)
			for (int columnNumber = 0; columnNumber < levelWorld[rowNumber].length; columnNumber++) { // 2)
				if (levelWorld[rowNumber][columnNumber].equals("DOT")) {
					dotsCount++;
				}
			}
		}
	}

	private void renderLevel(String[][] worldElements) {
		GridPane grid = new GridPane();

		//needed to calculate the width and height of each cell (in %)
		int rowNumber;
		int columnNumber = 0;

		/*
		 * set elements of world on GridPane 1) first loops over the row of the selected
		 * world 2) then for each element in that row the element is created and added
		 * to GridPane
		 */
		for (rowNumber = 0; rowNumber < worldElements.length; rowNumber++) { // 1)
			for (columnNumber = 0; columnNumber < worldElements[rowNumber].length; columnNumber++) { // 2)
				/* for IMAGES --> creates and adds image to grid */
				if (worldElements[rowNumber][columnNumber].equals("PACMAN")
						|| worldElements[rowNumber][columnNumber].equals("BORDER")
						|| worldElements[rowNumber][columnNumber].equals("CHERRY")
						|| worldElements[rowNumber][columnNumber].contains("GHOST")) {
					ImageView element = this.createGameElementImages(worldElements[rowNumber][columnNumber]);
					grid.add(element, columnNumber, rowNumber);
				}
				/*for SHAPE --> creates and adds shape to grid*/
				if (worldElements[rowNumber][columnNumber].equals("DOT")){
					Shape element = this.createDot();
					grid.add(element, columnNumber, rowNumber);
				}
			}
		}
		
		this.countDots(positionState);

		/* sets the width and height of single cells in % */
		setRowAndColumnHeight(rowNumber, columnNumber, grid);

		// set world pane to updated world grid pane
		world = grid;

		notifyMovementObservers();
	}

	private ImageView createGameElementImages(String gameElement) {
		ImageView element = null;

		switch (gameElement) {
			case "PACMAN":
				ImageView imageViewPacMan = setImageView(this.pacMan_Right, 30, 30);
				element = rotateImageInDirection(currentPacManDirection, imageViewPacMan);
				break;
			case "GHOST1":
				element = setImageView(this.ghost1, 30, 30);
				break;
			case "GHOST2":
				element = setImageView(this.ghost2, 30, 30);
				break;
			case "GHOST3":
				element = setImageView(this.ghost3, 30, 30);
				break;
			case "CHERRY":
				element = setImageView(this.cherry, 20, 20);
				break;
			case "BORDER":
				element = setImageView(this.border, 35, 40);
				break;
		}
		return element;
	}

	private ImageView setImageView(Image image, double height, double width) {
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);
		return imageView;
	}

	private Shape createDot(){
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(3.0f);
		return circle;
	}

	private void setRowAndColumnHeight(int rowNumber, int columnNumber, GridPane grid) {
		/* calculates the percentage (width & height) that each cell should occupy */

		/* set COLUMN WIDTH of each column */
		ColumnConstraints column1 = new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, HPos.CENTER, true);

		column1.setPercentWidth(100d / columnNumber);

		/* apply it for every column */
		for (int i = 0; i < columnNumber; i++) {
			grid.getColumnConstraints().add(column1);
		}

		/* set ROW HEIGHT of each row */
		RowConstraints rc = new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE, Priority.SOMETIMES, VPos.CENTER, true);
		rc.setPercentHeight(100d / rowNumber);

		/* apply it for every row */
		for (int i = 0; i < rowNumber; i++) {
			grid.getRowConstraints().add(rc);
		}
	}

	// instantiate ghosts
	private void initializeGhosts() {
		Ghost ghost_1 = new Ghost(this, 0, currentGhost1Location);
		Ghost ghost_2 = new Ghost(this, 1, currentGhost2Location);
		Ghost ghost_3 = new Ghost(this, 2, currentGhost3Location);

		// Register as Observer
		ghost_1.register(this);
		ghost_2.register(this);
		ghost_3.register(this);
	}

	/**
	 * Processes user input to move pacman.
	 * @param direction the direction of movement
	 */
	public void pacmanMove(int direction) {
		Point2D possiblePacmanLocation = movePoint(direction, currentPacmanLocation);
		int possibleX = (int) possiblePacmanLocation.getX();
		int possibleY = (int) possiblePacmanLocation.getY();

		currentPacManDirection = direction;

		//current x/y-coordinates
		int currentX = (int) currentPacmanLocation.getX();
		int currentY = (int) currentPacmanLocation.getY();

		//check for obstacles before set pacmanLocation
		String positionInhabitant = positionState[possibleX][possibleY];
		// no obstacles
		if (positionInhabitant.equals("EMPTY") || positionInhabitant.equals("CHERRY") || positionInhabitant.equals("DOT"))
		{
			if (positionInhabitant.equals("CHERRY"))
			{
				points += 500;
			}
			else if (positionInhabitant.equals("DOT"))
			{
				points += 100;
				dotsCount -= 1;
				if (dotsCount == 0) {
					gameWin = true;
					endLevel();
					return;
				}
			}
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
			renderLevel(positionState);
		}
		else if (positionInhabitant.contains("GHOST")) {
			positionState[currentX][currentY] = "EMPTY";
			currentPacmanLocation = startPacmanLocation;
			positionState[(int) currentPacmanLocation.getX()][(int) currentPacmanLocation.getY()]= "PACMAN";
			lives -= 1;
			System.out.println("LIVE LOST TRIGGER from GameModelClass! Location " + " Lives: " + lives); //DEBUG
		}

		// bugfix
		if (lives < 0) lives = 0;
	}

	/**
	 * Handles the end of a level: starts another level on win,
	 * ends game on game over or last level reached
	 */
	public void endLevel() {
		if (gameWin) {
			if (levelNo < 2) {
				// start new level
				levelNo++;
				start(levelNo);
			}
			else {
				// end game
				gameOver = true;
			}
		}

		if (gameOver) {
			//update ranking
			RankingData rankingData = RankingData.getInstance();
			Ranking ranking = new Ranking(0, username, points);
			try {
				rankingData.updateRanking(ranking);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculates a new position in the direction of movement.
	 * @param direction the direction of movement
	 * @param possibleLocation the possible location
	 * @return the new location
	 */
	public Point2D movePoint(int direction, Point2D possibleLocation) {
		// right
		if (direction == 0) {
			possibleLocation = possibleLocation.add(1, 0);
		}
		// down
		else if (direction == 1) {
			possibleLocation = possibleLocation.add(0, 1);
		}
		// left
		else if (direction == 2) {
			possibleLocation = possibleLocation.add(-1, 0);
		}
		// up
		else {
			possibleLocation = possibleLocation.add(0, -1);
		}

		return possibleLocation;
	}

	private ImageView rotateImageInDirection(int direction, ImageView imageToRotate) {
		// rotates given image depending on direction
		switch (direction) {
		case 0: // DOWN
			imageToRotate.setRotate(90);
			break;
		case 1: // RIGHT --> default
			break;
		case 2: // UP
			imageToRotate.setRotate(-90);
			break;
		case 3: // LEFT
			imageToRotate.setRotate(180);
			break;
		}

		return imageToRotate;
	}

	/**
	 * Gets the "world" grid pane.
	 * @return the "world" grid pane
	 */
	public GridPane getGridPane() {
		return world;
	}

	/**
	 * Gets the player's current points.
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Gets the player's remaining lives.
	 * @return lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Gets the currently played level.
	 * @return level number
	 */
	public int getLevelNo() {
		return levelNo;
	}

	/**
	 * Adds a new observer.
	 * @param observer the observer to add
	 */
	@Override
	public void register(MovementObserver observer) {
		movementObservers.add(observer);
	}

	/**
	 * Removes an observer.
	 * @param observer the observer to remove
	 */
	@Override
	public void unregister(MovementObserver observer) {
		movementObservers.remove(observer);
	}

	/**
	 * Informs observers about a ghost or pacman movement.
	 */
	@Override
	public void notifyMovementObservers() {
		for (MovementObserver movementObserver : movementObservers) {
			movementObserver.updateMovement();
		}
	}

	/**
	 * Updates observers about a new ghost location.
	 * @param ghostID the moving ghost
	 * @param ghostLocation New location for this ghost.
	 */
	@Override
	public void update(int ghostID, Point2D ghostLocation) {
		Point2D ghostOldLocation;
		String ghostState;
		switch (ghostID) {
			case 0:
				ghostOldLocation = currentGhost1Location;
				currentGhost1Location = ghostLocation;
				ghostState = "GHOST1";
				break;
			case 1:
				ghostOldLocation = currentGhost2Location;
				currentGhost2Location = ghostLocation;
				ghostState = "GHOST2";
				break;
			default:
				ghostOldLocation = currentGhost3Location;
				currentGhost3Location = ghostLocation;
				ghostState = "GHOST3";
				break;
		}

		// set old ghost position to previous state
		int oldX = (int) ghostOldLocation.getX();
		int oldY = (int) ghostOldLocation.getY();
		if (previousGhostState[ghostID].equals("DOT") || previousGhostState[ghostID].equals("EMPTY")) {
			positionState[oldX][oldY] = previousGhostState[ghostID];
		} else {
			positionState[oldX][oldY] = "EMPTY";
		}

		// set ghost to new position
		int xCoordinates = (int) ghostLocation.getX();
		int yCoordinates = (int) ghostLocation.getY();

		// save content of previous ghost position
		previousGhostState[ghostID] = positionState[xCoordinates][yCoordinates];
		positionState[xCoordinates][yCoordinates] = ghostState;

		/* welt neu rendern */
		renderLevel(positionState);
	}
}
