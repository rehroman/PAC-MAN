package application;

public class Ranking {

    private String rank;
    private String name;
    private String points;

    public Ranking(String rank, String name, String points) {
        this.rank = rank;
        this.name = name;
        this.points = points;
    }

    // getters used by cellFactory
    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getPoints() {
        return points;
    }
}
