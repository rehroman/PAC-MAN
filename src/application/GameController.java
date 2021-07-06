package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Starts the game, takes user input and updates the game view.
 */
public class GameController implements EventHandler<KeyEvent>, MovementObserver {
	@FXML ImageView heart3;
	@FXML ImageView heart2;
	@FXML ImageView heart1;
	@FXML BorderPane gamePane;
	@FXML Label playerLabel;
	@FXML Label pointsLabel;
	
	private GameModel GameModel;
	private GridPane pane;

	public void init(Parent root, String username) {
		displayName(username);

		GameModel = new GameModel();
		root.requestFocus();
		
		//Initialize the Grid with GameLevel
		pane = GameModel.getGridPane();
		gamePane.setCenter(pane);

		// subscribe to movement updates
		movementSubscription();
	}
	
	public void displayName(String username) {
		playerLabel.setText("Spieler: " + username);
	}
	
	public void exitGame (ActionEvent e) {
		System.exit(0); //TODO something else?
	}

	private void movementSubscription() {
		GameModel.register(this);
	}

	@Override
	public void handle(KeyEvent e) {
		KeyCode code = e.getCode();
		if(code == KeyCode.DOWN) {
			GameModel.pacmanMove(0);
		} else if (code == KeyCode.RIGHT) {
			GameModel.pacmanMove(1);
		} else if (code == KeyCode.UP) {
			GameModel.pacmanMove(2);
		} else if (code == KeyCode.LEFT) {
			GameModel.pacmanMove(3);
		}

		setPoints(GameModel.getPoints());
		setLives(GameModel.getLives());
	}

	public void setPoints(int points) {
		pointsLabel.setText("Punkte: " + points);
	}

	public void setLives(int lives) {
		switch (lives) {
			case 2:
				heart3.setVisible(false);
				break;
			case 1:
				heart2.setVisible(false);
				break;
			case 0:
				heart1.setVisible(false);
				break;
		}
	}

	@Override
	public void updateMovement()
	{
		Platform.runLater(() -> {
			/* deletes old grid and sets new one after movement*/
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		});
	}
}
