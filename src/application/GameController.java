package application;



import java.awt.event.ActionListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameController implements ActionListener {
	@FXML
	Label nameLabel;
	@FXML
	Label pointsLabel;

	
	public void displayName(String username) {
		nameLabel.setText("Spieler: " + username);
	}
	
	public void setPoints(int points) {
		nameLabel.setText("Punkte: " + points);
	}
	
	@FXML
	public void exitGame (ActionEvent e) {
		System.exit(0); //TODO something else?
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}