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
	
	private GameModel Pacman;

	public void init(Parent root) {
		Pacman = new GameModel();
		Pacman.start();
		root.requestFocus();
		
		//Init the Grid
		
		this.pacMan_Right = new Image(getClass().getResourceAsStream("/Icons/PacMan_Right.png"));
		this.ghost1 = new Image(getClass().getResourceAsStream("/Icons/Ghost1.png"));
		GridPane pane = new GridPane();
		ImageView imageView = new ImageView(pacMan_Right);
		ImageView imageView2 = new ImageView(ghost1);
		
		pane.add(imageView, 1, 0);
		pane.add(imageView2, 2, 0);

		
		pane.setGridLinesVisible(true); //TODO remove later
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
			Pacman.pacmanMove(0);	
		} else if (code == KeyCode.DOWN) {
			Pacman.pacmanMove(1);	
		} else if (code == KeyCode.LEFT) {
			Pacman.pacmanMove(2);	
		} else if (code == KeyCode.UP) {
			Pacman.pacmanMove(3);	
		}
		//pacMan.setLayoutX(Pacman.pacmanLocation[0]);
		//pacMan.setLayoutY(Pacman.pacmanLocation[1]);
	}
	
}