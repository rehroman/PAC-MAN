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
	private GridPane pane;

	public void init(Parent root) {
		GameModel = new GameModel();
		root.requestFocus();
		
		//Initialize the Grid

		pane = GameModel.getGridPane();
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
		if(code == KeyCode.DOWN) {
			GameModel.pacmanMove(0);
			/* deletes old grid and sets new one after pacman moved*/
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		} else if (code == KeyCode.RIGHT) {
			GameModel.pacmanMove(1);
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		} else if (code == KeyCode.UP) {
			GameModel.pacmanMove(2);
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		} else if (code == KeyCode.LEFT) {
			GameModel.pacmanMove(3);
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		}
		//pacMan.setLayoutX(Pacman.pacmanLocation[0]);
		//pacMan.setLayoutY(Pacman.pacmanLocation[1]);
	}
	
}
