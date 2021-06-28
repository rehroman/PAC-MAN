package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RankingController extends MenuController implements Initializable {

	public Button name_Back;
	@FXML
	TableView<Ranking> rankingTable = new TableView<>();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ShowRanking();
	}

	public void ShowRanking() {

		// ToDo: get columns from view
		// ToDo: Tabellenbefüllung im Model?
		TableColumn<Ranking, String> column1 = new TableColumn<>("Platz");
		column1.setCellValueFactory(new PropertyValueFactory<>("rank"));

		TableColumn<Ranking, String> column2 = new TableColumn<>("Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Ranking, String> column3 = new TableColumn<>("Punkte");
		column3.setCellValueFactory(new PropertyValueFactory<>("points"));

		rankingTable.getColumns().add(column1);
		rankingTable.getColumns().add(column2);
		rankingTable.getColumns().add(column3);

		// ToDo: get ranking from database
		rankingTable.getItems().add(new Ranking("1", "Spieler1", "2000"));
		rankingTable.getItems().add(new Ranking("2", "Spieler2", "1500"));
		rankingTable.getItems().add(new Ranking("3", "Spieler1", "1000"));

	}
}
