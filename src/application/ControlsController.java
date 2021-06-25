package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlsController extends MenuController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
	public void switchToMenu (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("1_Menu.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	
}
}
