package application;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameController implements EventHandler<KeyEvent> {
	@FXML BorderPane gamePane;
	@FXML Label nameLabel;
	@FXML Label pointsLabel;

	int rowNumber;
	int columnNumber;

	private Image pacMan_Right;
	private Image ghost1;
	
	private GameModel GameModel;

	public void init(Parent root) {
		GameModel = new GameModel();
		root.requestFocus();
		
		//Initialize the Grid
		GridPane pane = GameModel.initMap(1); //

		/*pane.setGridLinesVisible(true);*/ //TODO remove later
		gamePane.setCenter(pane);
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
			GameModel.pacmanMove(0);	
		} else if (code == KeyCode.DOWN) {
			GameModel.pacmanMove(1);	
		} else if (code == KeyCode.LEFT) {
			GameModel.pacmanMove(2);	
		} else if (code == KeyCode.UP) {
			GameModel.pacmanMove(3);	
		}
		//pacMan.setLayoutX(Pacman.pacmanLocation[0]);
		//pacMan.setLayoutY(Pacman.pacmanLocation[1]);
	}
	
}
