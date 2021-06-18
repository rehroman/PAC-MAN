package application;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;

public class GameController implements EventHandler<KeyEvent> {
	@FXML
	Pane gamePane;
	@FXML 
	Label nameLabel;
	@FXML
	Label pointsLabel;
	@FXML
	Arc pacMan;
	
	private Pacman Pacman;

	public void init(Parent root) {
		Pacman = new Pacman();
		Pacman.initMap(0);
		root.setFocusTraversable(true);
		root.requestFocus();
	}
	
	public void displayName(String username) {
		nameLabel.setText("Spieler: " + username);
	}
	
	public void setPoints(int points) {
		nameLabel.setText("Punkte: " + points);
	}
	
	public void exitGame (ActionEvent e) {
		System.exit(0); //TODO something else?
	}
        

@Override
	public void handle(KeyEvent e) {
		  KeyCode code = e.getCode();
		if(code == KeyCode.RIGHT) {
			Pacman.move(0);	
		} else if (code == KeyCode.DOWN) {
			Pacman.move(1);	
		} else if (code == KeyCode.LEFT) {
			Pacman.move(2);	
		} else if (code == KeyCode.UP) {
			Pacman.move(3);	
		}
		pacMan.setLayoutX(Pacman.pacmanLocation[0]);
		pacMan.setLayoutY(Pacman.pacmanLocation[1]);
	}
	
}