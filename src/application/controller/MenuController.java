package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The game menu controller enables navigation between views.
 */
public class MenuController {

	@FXML Button menu_Start;
	@FXML Button menu_Ranking;
	@FXML Button menu_Controls;
	@FXML Button menu_Close;

	/**
	 * Navigates back to the menu.
	 * @param e the button click action event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToMenu (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "../views/1_Menu.fxml");
	}

	/**
	 * Navigates back to the menu.
	 * @param scene the current scene
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToMenuFromScene(Scene scene) throws IOException {
		switchTo(scene, "../views/1_Menu.fxml");
	}

	/**
	 * Navigates to the name input.
	 * @param e the button click action event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToName (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "../views/2_Name.fxml");
	}

	/**
	 * Navigates to the controls view.
	 * @param e the button click action event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToControls (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "../views/4_Controls.fxml");
	}

	/**
	 * Navigates to the high scores.
	 * @param e the button click action event
	 * @throws IOException error if files in switchTo were not found
	 */
	public void switchToRanking (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "../views/5_Ranking.fxml");
	}

	/**
	 * Ends the game.
	 * @param e the button click action event
	 */
	public void endGame (ActionEvent e) {
		System.exit(0);
	}

	private Scene GetSceneFromEvent(ActionEvent actionEvent) {
		return ((Node)actionEvent.getSource()).getScene();
	}

	private void switchTo(Scene eventScene, String fxmlResource) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));
		Parent root = loader.load();
		Stage stage = (Stage)eventScene.getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
