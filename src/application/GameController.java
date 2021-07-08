package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.io.IOException;

/**
 * Starts the game, takes user input and updates the game view.
 */
public class GameController extends MenuController implements EventHandler<KeyEvent>, MovementObserver {
	@FXML Label levelLabel;
	@FXML Button gameExit;
	@FXML Button rankingButton;
	@FXML Button restartButton;
	@FXML ImageView heart3;
	@FXML ImageView heart2;
	@FXML ImageView heart1;
	@FXML BorderPane gamePane;
	@FXML Label playerLabel;
	@FXML Label pointsLabel;
	
	private GameModel GameModel;
	private GridPane pane;
	private Parent root;
	private String username;

	/**
	 * Initiates the game.
	 * @param root the game view's parent node.
	 * @param username the current user's name.
	 */
	public void init(Parent root, String username) {
		this.root = root;
		this.username = username;

		rankingButton.setVisible(false);
		restartButton.setVisible(false);

		displayName();

		GameModel = new GameModel(username);
		root.requestFocus();
		
		//Initialize the Grid with GameLevel
		pane = GameModel.getGridPane();
		gamePane.setCenter(pane);

		// subscribe to movement updates
		movementSubscription();
		setLevel(GameModel.getLevelNo());
	}
	
	private void displayName() {
		playerLabel.setText("Spieler: " + username);
	}

	private void movementSubscription() {
		GameModel.register(this);
	}

	/**
	 * Handles key inputs to control pacman.
	 * @param e the key pressed event
	 */
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

	private void setLevel(int level) {
		levelLabel.setText("Level " + level);
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
			if (GameModel.gameOver) {
				showGameEndDialog();
			}
			else if (GameModel.gameWin) {
				setLevel(GameModel.getLevelNo());
			}
			else {
				pane.getChildren().clear();
				gamePane.setCenter(GameModel.getGridPane());
			}
		});
	}

	private void showGameEndDialog() {
		rankingButton.setVisible(true);
		restartButton.setVisible(true);
		// stop observing
		GameModel.unregister(this);
	}

	/**
	 * Switches to the ranking view.
	 * @param e the button click event.
	 * @throws IOException error if files in switchTo were not found
	 */
	public void goToRanking(ActionEvent e) throws IOException {
		switchToRanking(e);
	}

	/**
	 * Restarts the game on button click.
	 */
	public void restartGame() {
		init(root, username);
	}
}
