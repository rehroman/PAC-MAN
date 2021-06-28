package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

	public void switchToMenu (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "1_Menu.fxml");
	}

	public void switchToMenuFromScene(Scene scene) throws IOException {
		switchTo(scene, "1_Menu.fxml");
	}

	public void switchToName (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "2_Name.fxml");
	}

	public void switchToControls (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "4_Controls.fxml");
	}

	public void switchToRanking (ActionEvent e) throws IOException {
		switchTo(GetSceneFromEvent(e), "5_Ranking.fxml");
	}
	
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
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
