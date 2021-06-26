package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static javafx.scene.paint.Color.BLACK;

public class GameModel {
	Boolean gameOver;
	Point2D startPacmanLocation;
	Point2D currentPacmanLocation;
	int currentpacmanDirection;
	Point2D currentGhost1Location;
	Point2D currentGhost2Location;
	Point2D currentGhost3Location;
	int columnNumber;
	int rowNumber;
	String[][] positionState;
	
	int points;
	int lives;

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
		currentpacmanDirection = 1;
		gameOver = false;
		 //TODO set initial Start Locations of Ghosts and Pacman according to MAP
		 startPacmanLocation = new Point2D(3,1);
		 currentPacmanLocation = startPacmanLocation;
		 currentGhost1Location = new Point2D(1,1);
		 currentGhost2Location = new Point2D(4,1);

	}

	/* 
	 * positionState Key:
	 * 0 means EMPTY
	 * 1 means CHERRY
	 * 2 means BORDER (solid-blue.png)
	 * 3 means PACMAN
	 * 4,5,6 means GHOSTS
	 * 7 means DOT
	 */
	
	public GridPane initMap(int map) {
		/*columnNumber = 8;
		rowNumber = 3;*/

		/* returns level/world */
		positionState = this.getWorld(map);
		/*fills grid with elements*/
		GridPane world = this.createGameWorld(positionState);

		/*TODO: noch drin lassen */
		 //set initial Start Locations of Ghosts and Pacman according to MAP
		 startPacmanLocation = new Point2D(3,1);
		 currentPacmanLocation = startPacmanLocation;
		 currentGhost1Location = new Point2D(2,1);

		return world;
	}

	/*==================GAMEWORLD==========================*/

	// GameWorld int [row][column]
	private String [][] getWorld (int selectedLevel){

		String [][] world = null;

		/*set different levels*/
		switch(selectedLevel) {
			/*always use a 15 x 15 world because of the size*/
			case 1:
				world = new String[][]{
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 0
						{"BORDER", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 1
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 2
						{"BORDER", "CHERRY", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 3
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 4
						{"BORDER", "DOT", "BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 5
						{"BORDER", "DOT", "DOT", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 6
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 7
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 8
						{"BORDER", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 9
						{"BORDER", "DOT", "DOT", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 10
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 11
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 12
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 13
						{"BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER", "BORDER"}, // 14
						//  0		  1			2		  3			4		  5			6		  7         8		  9			10		  11		12		  13        14
				};
				break;
			case 2:
				world = new String[][]{{"GHOST", "GHOST", "GHOST", "GHOST"},
						{"GHOST", "PACMAN", "PACMAN", "GHOST"},
						{"GHOST", "GHOST", "GHOST", "GHOST"},
				};
				break;
		}

		return world;
	}

	public GridPane createGameWorld(String [][] world) {

		GridPane grid = new GridPane();

		/*needed to calculate the width and height of each cell (in %)*/
		rowNumber = 0;
		columnNumber = 0;

		/* set elements of world on GridPane
			1) first loops over the row of the selected world
		   	2) then for each element in that row the element is created and added to GridPane */
		for (rowNumber = 0; rowNumber < world.length; rowNumber++) { // 1)
			for (columnNumber = 0; columnNumber < world[rowNumber].length; columnNumber++) { // 2)
				/*for images --> creates and adds image to grid*/
				if (world[rowNumber][columnNumber].equals("BORDER") || world[rowNumber][columnNumber].equals("CHERRY") || world[rowNumber][columnNumber].equals("GHOST")){
					ImageView element = this.createGameElementImages(world[rowNumber][columnNumber]);
					grid.add(element, columnNumber, rowNumber);
				}
				/*for shape --> creates and adds shape to grid*/
				if (world[rowNumber][columnNumber].equals("DOT")){
					Shape element = this.createGameElementShapes(world[rowNumber][columnNumber]);
					grid.add(element, columnNumber, rowNumber);
				}
			}
		}

		/* sets the width and height of single cells in % */
		this.setRowAndColumnHeight(rowNumber, columnNumber, grid);

		return grid;
	};

	public ImageView createGameElementImages(String gameElement){
		ImageView element = null;

		 switch(gameElement) {
			case "PACMAN":
				ImageView imageViewPacMan = new ImageView(this.pacMan_Right);
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

	/*TODO: Funktion die automatisch die Zelle in Grid setzt die verändert wurde am besten getriggert über binding*/

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
			 new Ghost(this,0,currentGhost1Location);
			 new Ghost(this,1,currentGhost2Location);

	}



	public void pacmanMove(int direction) {
		
		//calculate new possible location
		Point2D possiblePacmanLocation = movePoint(direction, currentPacmanLocation);
		currentpacmanDirection = direction;

		System.out.println("Possible Pacman" + possiblePacmanLocation);//DEBUG
		
		int possibleX = (int) possiblePacmanLocation.getX();		
		int possibleY = (int) possiblePacmanLocation.getY();
		
		int currentX = (int) currentPacmanLocation.getX();		
		int currentY = (int) currentPacmanLocation.getY();
		
		//check for obstacles before set pacmanLocation
		if (positionState[possibleX][possibleY] == "EMPTY") {
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
			
		} else if (positionState[possibleX][possibleY] == "CHERRY") {
			points += 500;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
			
		} else if (positionState[possibleX][possibleY] == "BORDER") {
			
		} else if  (positionState[possibleX][possibleY] == "DOT"){
			points += 100;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
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
	

}
