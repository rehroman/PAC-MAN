package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.geometry.Point2D;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

public class GameModel {
	Boolean gameOver;
	Point2D startPacmanLocation;
	Point2D currentPacmanLocation;
	int currentpacmanDirection;
	Point2D currentGhost1Location;
	Point2D currentGhost2Location;
	int columnNumber;
	int rowNumber;
	String[][] positionState;
	
	int points;
	int lives;
	
	public GameModel() {
		this.start();
	}
	

	public void start() {
		points = 0;
		lives = 3;
		currentpacmanDirection = 1;
		gameOver = false;
		moveGhosts();
		this.initMap("src/application/Map1.txt");
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
	
	public void initMap(String map) {
		/*columnNumber = 8;
		rowNumber = 3;*/
		
		positionState = gameWorld1;

		 //set initial Start Locations of Ghosts and Pacman according to MAP
		 startPacmanLocation = new Point2D(3,1);
		 currentPacmanLocation = startPacmanLocation;
		 currentGhost1Location = new Point2D(2,1);
		 
		 //set the States of of the fields in the grid
		 /*positionState[0][2]= "BORDER";
		 positionState[1][2]= "BORDER";
		 positionState[2][2]= "BORDER";
		 positionState[3][2]= "BORDER";
		 positionState[4][2]= "BORDER";
		 positionState[5][2]= "BORDER";
		 positionState[6][2]= "BORDER";
		 positionState[7][2]= "BORDER";
		 
		 positionState[0][1]= "BORDER";
		 positionState[1][1]= "GHOST1";
		 positionState[2][1]= "EMPTY";
		 positionState[3][1]= "PACMAN";
		 positionState[4][1]= "EMPTY";
		 positionState[5][1]= "DOT";
		 positionState[6][1]= "EMPTY";
		 positionState[7][1]= "BORDER";
		 
		 positionState[0][0]= "BORDER";
		 positionState[1][0]= "BORDER";
		 positionState[2][0]= "BORDER";
		 positionState[3][0]= "BORDER";
		 positionState[4][0]= "BORDER";
		 positionState[5][0]= "BORDER";
		 positionState[6][0]= "BORDER";
		 positionState[7][0]= "BORDER";*/
	}

	/*==================GAMEWORLD==========================*/

	// GameWorld int [row][column]
	String [][] gameWorld1 = {
			{"PACMAN", "PACMAN", "PACMAN", "PACMAN"}, // [0] --> row; single elements of this row [0][1], [0][2] ...
			{"PACMAN","GHOST", "GHOST", "PACMAN"}, // [1]
			{"PACMAN", "PACMAN", "PACMAN", "PACMAN"} // [2]
	};

	public GridPane createGameWorld(int selectedLevel) {

		String [][] world = null;
		GridPane grid = new GridPane();

		/*needed to calculate the width and height of each cell (in %)*/
		rowNumber = 0;
		columnNumber = 0;

		/*set different levels*/
		switch(selectedLevel) {
			case 1:
				world = new String[][]{{"PACMAN", "PACMAN", "PACMAN", "PACMAN"},
						{"PACMAN", "GHOST", "GHOST", "PACMAN"},
						{"PACMAN", "PACMAN", "PACMAN", "PACMAN"},
				};
				break;
			case 2:
				world = new String[][]{{"GHOST", "GHOST", "GHOST", "GHOST"},
						{"GHOST", "PACMAN", "PACMAN", "GHOST"},
						{"GHOST", "GHOST", "GHOST", "GHOST"},
				};
				break;
		}

		/* set elements of world on GridPane
			1) first loops over the row of the selected world
		   	2) then for each element in that row the element is created and added to GridPane */
		for (rowNumber = 0; rowNumber < world.length; rowNumber++) { // 1)
			System.out.println(world[rowNumber]);
			for (columnNumber = 0; columnNumber < world[rowNumber].length; columnNumber++) { // 2)
				System.out.println(world[rowNumber][columnNumber]);
				ImageView element = this.createGameElement(world[rowNumber][columnNumber]);
				grid.add(element, columnNumber, rowNumber);
			}
		}
		this.setRowAndColumnHeight(rowNumber, columnNumber, grid);

		return grid;
	};

	public ImageView createGameElement(String gameElement){
		ImageView element = null;
		 switch(gameElement) {
			case "EMPTY":
				// code block
				break;
			case "PACMAN":
				Image pacMan_Right = new Image(getClass().getResourceAsStream("/Icons/PacMan_Right.png"));
				ImageView imageViewPacMan = new ImageView(pacMan_Right);
				element = imageViewPacMan;
				break;
			case "GHOST":
				Image ghost1 = new Image(getClass().getResourceAsStream("/Icons/Ghost1.png"));
				ImageView imageViewGhost = new ImageView(ghost1);
				element = imageViewGhost;
				break;
			case "CHERRY":
				break;
			default:
				// code block
		}
		return element;
	}

	private void setRowAndColumnHeight(int rowNumber, int columnNumber, GridPane grid){
		/*calculates the percentage (width & height) that each cell should occupy*/

		/*set COLUMN WIDTH of each column*/
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(100d / columnNumber);
		double width = (100/columnNumber);

		/*apply it for every column*/
		for (int i = 0; i < columnNumber; i++) {
			grid.getColumnConstraints().add(column1);
		}

		/*set ROW HEIGHT of each row*/
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(100d / rowNumber);
		double height = (100/rowNumber);

		/*apply it for every row*/
		for (int i = 0; i < rowNumber; i++) {
			grid.getRowConstraints().add(rc);
		}

	}
	
	//instantiate ghosts
	public void moveGhosts (){
			 new Ghost(this,1,currentGhost1Location);
			 new Ghost(this,2,currentGhost2Location);
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
			
		} else if (positionState[possibleX][possibleY] == "GHOST1") {
			currentPacmanLocation = startPacmanLocation;
			lives -= 1;
			
		} else if (positionState[possibleX][possibleY] == "GHOST2") {
			currentPacmanLocation = startPacmanLocation;
			lives -= 1;
			
		} else if (positionState[possibleX][possibleY] == "GHOST3") {
			currentPacmanLocation = startPacmanLocation;
			lives -= 1;
		} else { 											//DOT
			points += 100;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
		}
		
		if (lives == 0) {
			gameOver = true;
			start();
		}
		
			System.out.println("Current Pacman" + currentPacmanLocation);//DEBUG
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
