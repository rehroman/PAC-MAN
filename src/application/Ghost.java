package application;

public class Ghost {
	int[] ghostLocation = new int[2];
	
	
	public int[] move(int direction) {
		int[] possibleLocation = ghostLocation;
		
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
		
		//TODO check for walls before set ghostLocation	
		return ghostLocation = possibleLocation;
		
	}

}
