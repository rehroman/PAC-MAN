package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	public void switchToName (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("2_Name.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToRanking (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("5_Ranking.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToControls (ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("4_Controls.fxml"));
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
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	
}
	
	public void endGame (ActionEvent e) {
		System.exit(0);
	}

}
