package application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Starts the game, takes user input and updates the game view.
 */
public class GameController extends MenuController implements EventHandler<KeyEvent>, MovementObserver {
	@FXML Button gameExit;
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

		GameModel = new GameModel(username);
		root.requestFocus();
		
		//Initialize the Grid with GameLevel
		pane = GameModel.getGridPane();
		gamePane.setCenter(pane);

		// startAutoRefreshWorld();
		// alternative: subscribe to movement updates
		movementSubscription();
	}
	
	private void displayName(String username) {
		playerLabel.setText("Spieler: " + username);
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

	private void setPoints(int points) {
		pointsLabel.setText("Punkte: " + points);
	}

	private void setLives(int lives) {
		switch (lives) {
			case 3:
				heart1.setVisible(true);
				heart2.setVisible(true);
				heart3.setVisible(true);
				break;
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

	/**
	 * Updates the game pane on movement
	 */
	@Override
	public void updateMovement() {
		Platform.runLater(() -> {
			pane.getChildren().clear();
			gamePane.setCenter(GameModel.getGridPane());
		});
	}

	private void startAutoRefreshWorld(){
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				//what you want to do
				Platform.runLater(() -> {
					pane.getChildren().clear();
					gamePane.setCenter(GameModel.getGridPane());
				});
			}
		}, 0, 1000);
	}
}
