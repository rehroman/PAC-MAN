package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NameController extends MenuController {

	public Button name_Back;
	public Button name_OK;
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private TextField name_NameField;
	
	public void switchToGame (Scene eventScene) throws IOException {
		String username = name_NameField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("3_Game.fxml"));
		root = loader.load();
		
		GameController gameController = loader.getController();
		gameController.displayName(username);
		
		stage = (Stage)eventScene.getWindow();
		scene = new Scene(root);
		
		scene.setOnKeyPressed(gameController);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		gameController.init(root);
	}

	public void switchToMenu (Scene eventScene) throws IOException {
		// call menu controller?
		FXMLLoader loader = new FXMLLoader(getClass().getResource("1_Menu.fxml"));
		root = loader.load();
		stage = (Stage)eventScene.getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void clickSwitchToGame(ActionEvent actionEvent) throws IOException {
		Scene eventScene = ((Node)actionEvent.getSource()).getScene();
		switchToGame(eventScene);
	}

	public void clickSwitchToMenu(ActionEvent actionEvent) throws IOException {
		Scene eventScene = ((Node)actionEvent.getSource()).getScene();
		switchToMenu(eventScene);
	}

	public void keyListener(KeyEvent keyEvent) throws IOException {
		Scene eventScene = ((Node)keyEvent.getSource()).getScene();
		if (keyEvent.getCode() == KeyCode.ENTER) {
			switchToGame(eventScene);
		}
		else if (keyEvent.getCode() == KeyCode.ESCAPE) {
			switchToMenu(eventScene);
		}
	}
}
