package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NameController extends MenuController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField name_NameField;
	
	public void switchToGame (ActionEvent e) throws IOException {
		String username = name_NameField.getText();
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("3_Game.fxml"));
		root = loader.load();
		
		GameController gameController = loader.getController();
		gameController.displayName(username);
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
