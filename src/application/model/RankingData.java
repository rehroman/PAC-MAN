package application.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides high scores.
 */
public class RankingData {

    private static final RankingData instance = new RankingData();

    private RankingData() { }

    /**
     * Provides an instance of RankingData.
     * @return an instance of RankingData
     */
    public static RankingData getInstance() {
        return instance;
    }

    /**
     * Gets rankings from a data file.
     * @return the saved rankings
     * @throws IOException error if file not found
     */
    public ArrayList<Ranking> getRanking() throws IOException {
        ArrayList<Ranking> rankings = new ArrayList<>();

        File dataFile = getDataFile();
        BufferedReader br = new BufferedReader(new FileReader(dataFile));

        String row;
        int rank = 1;
        while ((row = br.readLine()) != null) {
            String[] cells = row.split(",");
            rankings.add(new Ranking(rank, cells[0], Integer.parseInt(cells[1])));
            rank++;
        }

        return rankings;
    }

    /**
     * Updates the ranking file with new data if a player reached the top ten.
     * @param newRanking the player's name and reached points
     * @throws IOException error if file not found
     */
    public void updateRanking(Ranking newRanking) throws IOException {
        ArrayList<Ranking> rankings = getRanking();
        // Bestenliste soll max. 10 Plätze haben
        if (rankings.size() >= 10) {
            // wenn es 10 sind, prüfe, ob die neue Punktzahl größer ist als die bisherige kleinste
            Ranking lastPlace = rankings.get(9);
            if (newRanking.getPoints() > lastPlace.getPoints()) {
                rankings.add(newRanking);
                Collections.sort(rankings);
                // reduziere wieder auf 10 Einträge
                rankings.remove(10);
            }
        }
        else {
            rankings.add(newRanking);
            Collections.sort(rankings);
        }
        // Speichere geänderte Liste
        saveRanking(rankings);
    }

    private void saveRanking(ArrayList<Ranking> rankings) throws IOException {
        String filepath = getDataFile().getPath();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false));
        for (Ranking ranking: rankings) {
            String row = ranking.getName() + "," + ranking.getPoints();
            writer.write(row);
            writer.newLine();
        }
        writer.close();
    }

    private File getDataFile() {
        return new File("./bin/Data/Ranking.txt");
    }
}
