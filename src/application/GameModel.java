package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameModel {
	int[] pacmanLocation = new int[2];
	int rowNumber;
	int columnNumber;
	int[] positionState = new int[2];
	
	public GameModel() {
		this.start();
	}
	

	public void start() {
		this.initMap("src/application/Map1.txt"); 
	}
	
	
	
	/* 
	 * Map import to build the grid
	 * 0 means empty
	 * 1 means Cherry
	 * 2 means Border(solid-blue.png)
	 * 3 means Pacman
	 * 4,5,6 means Ghosts
	 */
	
	public void initMap(String map) {
		 File file = new File(map);
		 Scanner sc = null;
		 try {
			sc = new Scanner(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 while(sc.hasNext()) {     //TODO 
		 System.out.println(sc.next());
		 }
		 
		 
	}
	
	public int[] pacmanMove(int direction) {
		int[] possibleLocation = pacmanLocation;
		
		//right
		if(direction == 0) {		
			possibleLocation[0] += 1;
		}
		//down
		else if(direction == 1) {		
			possibleLocation[1] += 1;
		}
		//left 
		else if(direction == 2) {		
			possibleLocation[0] -= 1;
		}
		//up
		else {		
			possibleLocation[1] -= 1;
		}
		
		//TODO check for walls before set pacmanLocation	
		return pacmanLocation = possibleLocation;
		
	}
}
