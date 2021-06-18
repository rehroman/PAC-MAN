package application;

import java.util.Arrays;

public class Pacman {
	int[] pacmanLocation = new int[2];
	
	int[] ghost1Location = new int[2];
	int[] ghost2Location = new int[2];
	int[] ghost3Location = new int[2];
	
	public void initMap(int map) {
		pacmanLocation[0] = 287 ;
		pacmanLocation[0] = 133 ;
		if(map == 0) {
			//draw map 1
		}
	}
	
	public int[] move(int direction) {
		int[] possibleLocation = pacmanLocation;
		
		//right
		if(direction == 0) {		
			possibleLocation[0] += 1;
		}
		//down, some wrong way TODO
		else if(direction == 1) {		
			possibleLocation[1] -= 1;
		}
		//left 
		else if(direction == 2) {		
			possibleLocation[0] -= 1;
		}
		//up
		else {		
			possibleLocation[1] += 1;
		}
		
		//TODO check for walls before set pacmanLocation	
		return pacmanLocation = possibleLocation;
		
	}
}
