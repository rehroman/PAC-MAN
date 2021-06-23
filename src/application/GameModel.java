package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.geometry.Point2D;

public class GameModel {
	Point2D startPacmanLocation;
	Point2D currentPacmanLocation;
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
		columnNumber = 8;
		rowNumber = 3;
		
		positionState = new String[columnNumber][rowNumber];
		
//		TODO Map import:
//		 File file = new File(map);
//		 Scanner sc = null;
//		 try {
//			sc = new Scanner(file);
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 while(sc.hasNext()) {     //TODO 
//		 System.out.println(sc.next()); //DEBUG
//		 }
//		 
		 //set initial Start Locations of Ghosts and Pacman according to MAP
		 startPacmanLocation = new Point2D(3,1);
		 currentPacmanLocation = startPacmanLocation;
		 currentGhost1Location = new Point2D(2,1);
		 
		 //set the States of of the fields in the grid
		 positionState[0][2]= "BORDER";
		 positionState[1][2]= "BORDER";
		 positionState[2][2]= "BORDER";
		 positionState[3][2]= "BORDER";
		 positionState[4][2]= "BORDER";
		 positionState[5][2]= "BORDER";
		 positionState[6][2]= "BORDER";
		 positionState[7][2]= "BORDER";
		 
		 positionState[0][1]= "BORDER";
		 positionState[1][1]= "EMPTY";
		 positionState[2][1]= "GHOST1";
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
		 positionState[7][0]= "BORDER";
	}
	
	public void pacmanMove(int direction) {
		
		//calculate new possible location
		Point2D possiblePacmanLocation = movePoint(direction, currentPacmanLocation);
		
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
			lives =- 1;
			
		} else if (positionState[possibleX][possibleY] == "GHOST2") {
			currentPacmanLocation = startPacmanLocation;
			lives =- 1;
			
		} else if (positionState[possibleX][possibleY] == "GHOST3") {
			currentPacmanLocation = startPacmanLocation;
			lives =- 1;
		} else { //DOT
			points += 100;
			positionState[currentX][currentY] = "EMPTY";
			positionState[possibleX][possibleY] = "PACMAN";
			currentPacmanLocation = possiblePacmanLocation;
		}
		
			System.out.println("Current Pacman" + currentPacmanLocation);//DEBUG
			System.out.println("Points " + points);//DEBUG
		
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
