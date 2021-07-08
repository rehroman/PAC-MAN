package application.controller;

import java.io.IOException;

import application.controller.GameController;
import application.controller.MenuController;
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

/**
 * Provides a name input before starting the game.
 */
public class NameController extends MenuController {

	@FXML Button name_Back;
	@FXML Button name_OK;
	@FXML TextField name_NameField;

	/**
	 * Starts the game.
	 * @param actionEvent the button click action event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToGame(ActionEvent actionEvent) throws IOException {
		Scene eventScene = ((Node)actionEvent.getSource()).getScene();
		switchToGameFromScene(eventScene);
	}

	/**
	 * Handles key input (enter to start the game, escape to go back).
	 * @param keyEvent the key pressed event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void keyListener(KeyEvent keyEvent) throws IOException {
		Scene eventScene = ((Node)keyEvent.getSource()).getScene();
		if (keyEvent.getCode() == KeyCode.ENTER) {
			switchToGameFromScene(eventScene);
		}
		else if (keyEvent.getCode() == KeyCode.ESCAPE) {
			switchToMenuFromScene(eventScene);
		}
	}

	private void switchToGameFromScene (Scene eventScene) throws IOException {
		String username = name_NameField.getText();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/3_Game.fxml"));
		Parent root = loader.load();

		GameController gameController = loader.getController();

		Stage stage = (Stage) eventScene.getWindow();
		Scene scene = new Scene(root);

		scene.setOnKeyPressed(gameController);

		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		gameController.init(root, username);
	}
}
