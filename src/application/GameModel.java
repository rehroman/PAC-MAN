package application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class GameModel implements GhostObserver {

	/*TODO points + lives vielleicht in UserClass*/
	int points;
	int lives;
	Boolean gameOver;
	

	//Properties for Ghosts
	Point2D currentGhost1Location;
	Point2D currentGhost2Location;
	Point2D currentGhost3Location;

	// Properties for PacMan
	Point2D startPacmanLocation = new Point2D(1,1);
	Point2D currentPacmanLocation; // always holds PacMan location
	int currentPacManDirection;

	// Properties for Level
	String[][] positionState; // hold positions of all elements, needed to render grid
	GridPane world;

	/*loading images*/
	Image pacMan_Right = new Image("/Icons/PacMan_Right.png");
	Image ghost1 = new Image("/Icons/Ghost1.png");
	Image cherry = new Image("/Icons/Cherry.png");
	Image border = new Image("/Icons/brick-wall.png");

	public GameModel() {
		this.start();
		moveGhosts();
	}
	

	public void start() {
		points = 0;
		lives = 3;
		currentPacManDirection = 1;
		gameOver = false;

		positionState = this.getLevel(1);

		currentPacmanLocation = this.setPacManInWorld((int) startPacmanLocation.getX(),(int) startPacmanLocation.getY());

		world = this.renderLevel(positionState);
		 //TODO set initial Start Locations of Ghosts and Pacman according to MAP
		 /*currentPacmanLocation = new Point2D(3,1);*/
		 currentGhost1Location = new Point2D(1,1);
		 currentGhost2Location = new Point2D(4,1);
	}

	public Point2D setPacManInWorld(int xCoordinate, int yCoordinate){
		positionState [xCoordinate][yCoordinate] = "PACMAN";
		return new Point2D(xCoordinate,yCoordinate);
	}


	/*==================GAMEWORLD==========================*/

	private String [][] getLevel(int selectedLevel){
		/*always use a 15 x 15 world because of the size; world[row][column]*/
		String [][] level = null;

		/*set different levels*/
		switch(selectedLevel) {
			case 1:
				level = new String[][]{
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 0
						{"BORDER", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "BORDER"}, // 1
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER"}, // 2
						{"BORDER", "CHERRY", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER"}, // 3
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER"}, // 4
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "BORDER", "DOT", "BORDER"}, // 5
						{"BORDER", "DOT", "DOT", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "DOT", "BORDER"}, // 6
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER"}, // 7
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER"}, // 8
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER"}, // 9
						{"BORDER", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "BORDER"}, // 10
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 11
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 12
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "BORDER"}, // 13
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 14
						//  0		  1			2		  3			4		  5			6		  7         8		  9			10		  11		12		  13        14
				};
				break;
			case 2:
				level = new String[][]{{"GHOST", "GHOST", "GHOST", "GHOST"},
						{"GHOST", "PACMAN", "PACMAN", "GHOST"},
						{"GHOST", "GHOST", "GHOST", "GHOST"},
				};
				break;
		}

		return level;
	}

	public GridPane renderLevel(String [][] world) {
		GridPane grid = new GridPane();

		/*needed to calculate the width and height of each cell (in %)*/
		int rowNumber = 0;
		int columnNumber = 0;

		/* set elements of world on GridPane
		1) first loops over the row of the selected world
		2) then for each element in that row the element is created and added to GridPane */
		for (rowNumber = 0; rowNumber < world.length; rowNumber++) { // 1)
			for (columnNumber = 0; columnNumber < world[rowNumber].length; columnNumber++) { // 2)
				/*for IMAGES --> creates and adds image to grid*/
				if (world[rowNumber][columnNumber].equals("PACMAN") || world[rowNumber][columnNumber].equals("BORDER") || world[rowNumber][columnNumber].equals("CHERRY") || world[rowNumber][columnNumber].equals("GHOST")){
					ImageView element = this.createGameElementImages(world[rowNumber][columnNumber]);
					grid.add(element, columnNumber, rowNumber);
				}
				/*for SHAPE --> creates and adds shape to grid*/
				if (world[rowNumber][columnNumber].equals("DOT")){
					Shape element = this.createGameElementShapes(world[rowNumber][columnNumber]);
					grid.add(element, columnNumber, rowNumber);
				}
				if (world[rowNumber][columnNumber].equals("EMPTY")){
					continue;
				}
			}
		}

		/* sets the width and height of single cells in % */
		setRowAndColumnHeight(rowNumber, columnNumber, grid);

		return grid;
	};

	public ImageView createGameElementImages(String gameElement){
		ImageView element = null;

		 switch(gameElement) {
			case "PACMAN":
				ImageView imageViewPacMan = new ImageView(this.pacMan_Right);


				imageViewPacMan = rotateImageInDirection(currentPacManDirection, imageViewPacMan);
				imageViewPacMan.setFitHeight(30);
				imageViewPacMan.setFitWidth(30);
				element = imageViewPacMan;
				break;
			case "GHOST":
				ImageView imageViewGhost = new ImageView(this.ghost1);
				imageViewGhost.setFitHeight(30);
				imageViewGhost.setFitWidth(30);
				element = imageViewGhost;
				break;
			case "CHERRY":
				ImageView imageCherry = new ImageView(this.cherry);
				imageCherry.setFitHeight(20);
				imageCherry.setFitWidth(20);
				element = imageCherry;
				break;
			 case "BORDER":
				 ImageView imageBorder = new ImageView(this.border);
				 imageBorder.setFitHeight(35);
				 imageBorder.setFitWidth(40);
				 element = imageBorder;
				 break;
		}
		return element;
	}

	public Shape createGameElementShapes(String gameElement){
		Shape element = null;

		switch(gameElement) {
			case "EMPTY":
				// code block
				break;
			case "DOT":
				Circle circle = new Circle();
				circle.setCenterX(100.0f);
				circle.setCenterY(100.0f);
				circle.setRadius(3.0f);
				element = circle;
				break;
		}
		return element;
	}

	private void setRowAndColumnHeight(int rowNumber, int columnNumber, GridPane grid){
		/*calculates the percentage (width & height) that each cell should occupy*/

		/*set COLUMN WIDTH of each column*/
		ColumnConstraints column1 = new ColumnConstraints(Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE,
				Priority.SOMETIMES,
				HPos.CENTER,
				true);

		column1.setPercentWidth(100d / columnNumber);
		double width = (100/columnNumber);

		/*apply it for every column*/
		for (int i = 0; i < columnNumber; i++) {
			grid.getColumnConstraints().add(column1);
		}

		/*set ROW HEIGHT of each row*/
		RowConstraints rc = new RowConstraints(Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE,
				Priority.SOMETIMES,
				VPos.CENTER,
				true);
		rc.setPercentHeight(100d / rowNumber);
		double height = (100/rowNumber);

		/*apply it for every row*/
		for (int i = 0; i < rowNumber; i++) {
			grid.getRowConstraints().add(rc);
		}
	}
	
		//instantiate ghosts
	public void moveGhosts (){

		Ghost ghost_1= new Ghost (this,0,currentGhost1Location);
		Ghost ghost_2= new Ghost (this,1,currentGhost2Location);
		//Register as Observer
		ghost_1.register(this);
		ghost_2.register(this);
	
		//new Ghost(this,1,currentGhost2Location);
	    //new Ghost(this,1,currentGhost2Location);

	}

	public void pacmanMove(int direction) {
		
		//calculate new possible x/y-coordinates
		Point2D possiblePacmanLocation = movePoint(direction, currentPacmanLocation);
		int possibleX = (int) possiblePacmanLocation.getX();
		int possibleY = (int) possiblePacmanLocation.getY();

		currentPacManDirection = direction;

		System.out.println("Possible Pacman" + possiblePacmanLocation);//DEBUG

		//current x/y-coordinates
		int currentX = (int) currentPacmanLocation.getX();		
		int currentY = (int) currentPacmanLocation.getY();
		
		//check for obstacles before set pacmanLocation
		if (positionState[possibleX][possibleY] == "EMPTY") {
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			movePacManImage(direction, possibleX, possibleY);
			currentPacmanLocation = possiblePacmanLocation;
			
		} else if (positionState[possibleX][possibleY] == "CHERRY") {
			points += 500;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			movePacManImage(direction ,possibleX, possibleY);
			currentPacmanLocation = possiblePacmanLocation;
			
		} else if (positionState[possibleX][possibleY] == "BORDER") {
			
		} else if  (positionState[possibleX][possibleY] == "DOT"){
			points += 100;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			movePacManImage(direction ,possibleX, possibleY);
			currentPacmanLocation = possiblePacmanLocation;

		} else if (positionState[possibleX][possibleY] == "PACMAN"){
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;

		} else {
			System.out.println("\n\nError in pacmanMove: positionState is not defined\n\n");
		}
		
			System.out.println("New Pacman Location" + currentPacmanLocation);//DEBUG
			System.out.println("Points " + points);//DEBUG
			System.out.println("Lives  " + lives);//DEBUG	
	}

	public Point2D movePoint(int direction, Point2D possibleLocation) {
		
		//right
		if(direction == 0) {		
			possibleLocation = possibleLocation.add(1,0);
			System.out.println("Direction" + direction);//DEBUG
		}
		//down
		else if(direction == 1) {	
			possibleLocation = possibleLocation.add(0,1);
			System.out.println("Direction" + direction);//DEBUG
		}
		//left 
		else if(direction == 2) {
			possibleLocation = possibleLocation.add(-1,0);
			System.out.println("Direction" + direction);//DEBUG
		}
		//up
		else {
			possibleLocation = possibleLocation.add(0,-1);
			System.out.println("Direction" + direction);//DEBUG
		}
		
		return possibleLocation;
	}

	public void movePacManImage(int direction, int xCoordinates, int yCoordinates){
		// rotate PacMan depending on direction

		/*position von pacman ändern*/
		positionState[xCoordinates][yCoordinates] = "PACMAN";
		/*welt neu rendern*/
		world = renderLevel(positionState);
	}
	
	public void moveGhostImage(int xCoordinates, int yCoordinates){

		positionState[(int)currentGhost1Location.getX()][(int)currentGhost1Location.getY()] = "EMPTY";

		/*position von ghost ändern*/
		positionState[xCoordinates][yCoordinates] = "GHOST";
		/*welt neu rendern*/
		world = renderLevel(positionState);
	}

	public ImageView rotateImageInDirection(int direction, ImageView imageToRotate){
		// rotates given image depending on direction
		switch(direction) {
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

	 public GridPane getGridPane(){
		return world;
	 }


	@Override
	public void update(int ghostID, Point2D ghostLocation) {
		System.out.println("Observe Update about to start currentGhost1Location OLD Value: "+ currentGhost1Location );
		switch(ghostID) {
			case 0:
				moveGhostImage((int)ghostLocation.getX(),(int)ghostLocation.getY());
				currentGhost1Location = ghostLocation;
				world = renderLevel(positionState);
				break;
			case 1:
				currentGhost2Location = ghostLocation;
				break;
		}
		
		System.out.println("Observe Update done currentGhost1Location NEW Value: "+ currentGhost1Location );
	}


}
