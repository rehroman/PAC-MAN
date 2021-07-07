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

import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent> {
	public ImageView heart3;
	public ImageView heart2;
	public ImageView heart1;
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

		startAutoRefreshWorld();
		/*pane.setGridLinesVisible(true);*/ //TODO remove later
	}
	
	public void displayName(String username) {
		playerLabel.setText("Spieler: " + username);
	}
	
	public void exitGame (ActionEvent e) {
		System.exit(0); //TODO something else?
	}

	private void movementSubscription() {
		//GameModel.register(this);
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

		/* deletes old grid and sets new one after pacman moved*/
		pane.getChildren().clear();
		gamePane.setCenter(GameModel.getGridPane());

		//pacMan.setLayoutX(Pacman.pacmanLocation[0]);
		//pacMan.setLayoutY(Pacman.pacmanLocation[1]);
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

	/*@Override
	public void updateMovement()
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				pane = GameModel.getGridPane();
				pane.getChildren().clear();
				gamePane.setCenter(GameModel.getGridPane());
			}
		});
	}*/

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
