package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NameController { // to be moved to MenuController?
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	public void switchToGame (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("3_Game.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToMenu (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("1_Menu.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}