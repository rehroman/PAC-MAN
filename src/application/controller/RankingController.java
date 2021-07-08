package application.controller;

import application.Ranking;
import application.RankingData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Provides ranking information.
 */
public class RankingController extends MenuController implements Initializable {

	@FXML Button name_Back;
	@FXML TableView<Ranking> rankingTable;
	@FXML TableColumn<Ranking, String> column1;
	@FXML TableColumn<Ranking, String> column2;
	@FXML TableColumn<Ranking, String> column3;
	private RankingData rankingData;

	/**
	 * Prepares content for the ranking view.
	 * @param url inherited from Initializable
	 * @param resourceBundle inherited from Initializable
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		prepareColumns();
		rankingData = RankingData.getInstance();
		showRanking();
	}

	private void prepareColumns() {
		column1.setCellValueFactory(new PropertyValueFactory<>("rank"));
		column2.setCellValueFactory(new PropertyValueFactory<>("name"));
		column3.setCellValueFactory(new PropertyValueFactory<>("points"));
	}

	private void showRanking() {
		try {
			ArrayList<Ranking> highScores = rankingData.getRanking();
			for (Ranking highScore : highScores) {
				rankingTable.getItems().add(highScore);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
